package manager;

import converter.JSONConverter;
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
    public static void baseRequest(HttpServletRequest req, HttpServletResponse resp){
        String userName = req.getParameter("username");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currentUserId", DatabaseHelper.registerUser(userName));
        jsonObject.put("messageToken", 0);
        jsonObject.put("messageEditToken", 0);
        jsonObject.put("messageDeleteToken", 0);
        jsonObject.put("userToken", 0);
        jsonObject.put("userChangeToken", 0);
        sendResponse(resp, jsonObject.toJSONString());
    }
    public static void updateRequest(HttpServletRequest req, HttpServletResponse resp){
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

    }

    public static void sendResponse(HttpServletResponse resp, String jsonString){
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.println(jsonString);
        } catch (IOException e) {
            System.out.println("Sending response error.");
            e.printStackTrace();
        }
        finally {
            out.flush();
            out.close();
        }

    }
}
