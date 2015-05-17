package manager;

import exception.DatabaseException;
import exception.ManagerException;
import util.JSONConverter;
import database.DatabaseHelper;
import entity.Message;
import entity.ClientProtocol;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by User on 15.05.15.
 */
public class ChangeManager {

    public static void changeMessageRequest(HttpServletRequest req, HttpServletResponse resp, JSONObject jsonObject) throws ManagerException {
        if (jsonObject != null) {
            int messId = Integer.parseInt((String) ((JSONObject) jsonObject.get("message")).get("messageId"));
            String text = (String) ((JSONObject) jsonObject.get("message")).get("messageText");
            try {
                DatabaseHelper.addNewChange(new ClientProtocol("editMess", text, messId));
            } catch (DatabaseException e) {
                throw new ManagerException("Can't change message request " + e.getMessage(), e);
            }
        }
    }

    public static void changeUserRequest(HttpServletRequest req, HttpServletResponse resp, JSONObject jsonObject) throws ManagerException {
        if (jsonObject != null) {
            int userId = ((Long) ((JSONObject) jsonObject.get("user")).get("userId")).intValue();
            String username = (String) ((JSONObject) jsonObject.get("user")).get("username");

            try {
                DatabaseHelper.editUser(username, userId);
                DatabaseHelper.addNewChange(new ClientProtocol("editUser", userId, username));
            } catch (DatabaseException e) {
                throw new ManagerException("Can't change user request " + e.getMessage(), e);
            }
        }
    }

    public static void deleteMessageRequest(HttpServletRequest req, HttpServletResponse resp) throws ManagerException {
        try {
            BufferedReader br = req.getReader();
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(br.readLine());
            for (Object s : jsonArray) {
                DatabaseHelper.addNewChange(new ClientProtocol("deleteMess", Integer.parseInt((String) s)));
            }
            br.close();
        } catch (IOException e) {
            throw new ManagerException("Can't delete message request " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new ManagerException("Can't delete message request " + e.getMessage(), e);
        } catch (DatabaseException e) {
            throw new ManagerException("Can't delete message request " + e.getMessage(), e);
        }
    }

    public static void sendMessageRequest(HttpServletRequest req, HttpServletResponse resp) throws ManagerException {
        JSONObject jsonObject = JSONConverter.getParameter(req);
        Message msg = new Message((String) jsonObject.get("messageText"),
                ((Long) jsonObject.get("userId")).intValue(), (int) (System.currentTimeMillis()));
        try {
            DatabaseHelper.addNewChange(new ClientProtocol("sendMess", msg));
        } catch (DatabaseException e) {
            throw new ManagerException("Can't send message request " + e.getMessage(), e);
        }
    }

}
