package manager;

import exception.DatabaseException;
import exception.ManagerException;
import util.JSONConverter;
import database.DatabaseHelper;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by User on 15.05.15.
 */
public class RequestManager {
    public static void baseRequest(HttpServletRequest req, HttpServletResponse resp) throws ManagerException {
        String userName = req.getParameter("username");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("currentUserId", DatabaseHelper.registerUser(userName));
            jsonObject.put("token", 0);
        } catch (DatabaseException e) {
            throw new ManagerException("Can't make base request " + e.getMessage(), e);
        }
        sendResponse(resp, jsonObject.toJSONString());
    }

    public static void updateRequest(HttpServletRequest req, HttpServletResponse resp) throws ManagerException {
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
}
