package manager;

import converter.JSONConverter;
import database.DatabaseHelper;
import instances.Message;
import instances.ProtocolObject;
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

    public static void changeMessageRequest(HttpServletRequest req, HttpServletResponse resp, JSONObject jsonObject ){
        if (jsonObject != null) {
            int messId = ((Long) ((JSONObject) jsonObject.get("message")).get("messageId")).intValue();
            String text = (String) ((JSONObject) jsonObject.get("message")).get("messageText");
            DatabaseHelper.addNewChange(new ProtocolObject("editMess", text, messId));
        }
    }

    public static void changeUserRequest(HttpServletRequest req, HttpServletResponse resp, JSONObject jsonObject ){
        if (jsonObject != null) {
            int userId = ((Long) ((JSONObject) jsonObject.get("user")).get("userId")).intValue();
            String username = (String) ((JSONObject) jsonObject.get("user")).get("username");

            DatabaseHelper.editUser(username, userId);
            DatabaseHelper.addNewChange(new ProtocolObject("editUser", userId, username));
        }
    }
    public static void deleteMessageRequest(HttpServletRequest req, HttpServletResponse resp){
        try {
            BufferedReader br = req.getReader();
            try {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(br.readLine());
                for (Object s : jsonArray) {
                    DatabaseHelper.addNewChange(new ProtocolObject("deleteMess", Integer.parseInt((String) s)));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendMessageRequest(HttpServletRequest req, HttpServletResponse resp){
        JSONObject jsonObject = JSONConverter.getParameter(req);
        Message msg = new Message((String)jsonObject.get("messageText"),
                ((Long)jsonObject.get("userId")).intValue(), (int)(System.currentTimeMillis()));
        DatabaseHelper.addNewChange(new ProtocolObject("sendMess", msg));
    }

}
