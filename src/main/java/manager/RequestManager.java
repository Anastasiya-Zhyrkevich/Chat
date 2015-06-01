package manager;

import exception.DatabaseException;
import exception.ManagerException;
import util.JSONConverter;
import database.DatabaseHelper;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by User on 15.05.15.
 */
public class RequestManager {
    public static void baseRequest(HttpServletRequest req, HttpServletResponse resp) throws ManagerException {
        String userName = req.getParameter("username");
        String passWord = transformPass(req.getParameter("password") + (userName+"aaaaaa").substring(0,6));
        JSONObject jsonObject = new JSONObject();
        HttpSession session;
        int currentId = -1;
        try{
            currentId = DatabaseHelper.registerUser(userName, passWord);
            if (currentId < 0)
                resp.setStatus(401);
            else {
                session = req.getSession(true);
                session.setMaxInactiveInterval(30*60);
                session.setAttribute("username", userName);
                jsonObject.put("currentUserId", currentId);
                jsonObject.put("token", 0);
            }
            sendResponse(resp, jsonObject.toJSONString());
        } catch (DatabaseException e) {
            throw new ManagerException("Can't make base request " + e.getMessage(), e);
        }
    }

    public static void updateRequest(HttpServletRequest req, HttpServletResponse resp) throws ManagerException {
        HttpSession session = req.getSession(false);
        if (session != null){
            try {
                int firstToken = Integer.parseInt(req.getParameter("token"));
                int lastToken = DatabaseHelper.getNextFreeLine("protocol");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", lastToken);
                jsonObject.put("messages", JSONConverter.getMessages(DatabaseHelper.getMessages(firstToken, lastToken - 1)));
                jsonObject.put("editedMessages", JSONConverter.getEditedMessages(DatabaseHelper.getEditedMessages(firstToken, lastToken - 1)));
                jsonObject.put("deletedMessagesIds", JSONConverter.getDeletedMessages(DatabaseHelper.getDeleted(firstToken, lastToken - 1)));
                jsonObject.put("users", JSONConverter.getUsers(DatabaseHelper.getUsers(firstToken, lastToken - 1, "newUser")));
                jsonObject.put("changedUsers", JSONConverter.getUsers(DatabaseHelper.getUsers(firstToken, lastToken - 1, "editUser")));

                sendResponse(resp, jsonObject.toJSONString());
            } catch (DatabaseException e) {
                throw new ManagerException("Can't make update request " + e.getMessage(), e);
            }
        }
        else{
            resp.setStatus(401);
            System.out.println("Error with session");
        }
    }

    public static void registerRequest(HttpServletRequest req, HttpServletResponse resp) throws ManagerException{
        String userName = req.getParameter("username");
        String passWord = transformPass(req.getParameter("password") + (userName+"aaaaaa").substring(0,6));
        int currentId = -1;
        try{
            currentId = DatabaseHelper.registerNewUser(userName, passWord);
            if (currentId < 0)
                resp.setStatus(401);
            sendResponse(resp, userName);
        } catch (DatabaseException e) {
            throw new ManagerException("Can't make base request " + e.getMessage(), e);
        }
    }

    public static void sendResponse(HttpServletResponse resp, String jsonString) throws ManagerException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.println(jsonString);
        } catch (IOException e) {
            throw new ManagerException("Fail send response", e);
        } finally {
            out.flush();
            out.close();
        }
    }
    public static String transformPass(String password){
        MessageDigest md = null;
        StringBuffer sb = new StringBuffer(password);
        try {
            md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            sb.setLength(0);
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
