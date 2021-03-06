package util;

import entity.Message;
import entity.User;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by User on 16.05.15.
 */
public class JSONConverter {

    private static final Logger logger = Logger.getRootLogger();

    public static String getMessages(List<Message> messages){
        JSONArray jsonArray = new JSONArray();
        for (Message msg: messages) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("userId", msg.getUserId());
            tempObject.put("messageId",  msg.getId());
            tempObject.put("messageText", msg.getText());
            tempObject.put("messageTime", msg.getTime());
            tempObject.put("isDeleted", 0);                  //temporary
            jsonArray.add(tempObject);
        }
        return jsonArray.toJSONString();
    }
    public static String getEditedMessages(List<Message> messages){
        JSONArray jsonArray = new JSONArray();
        for (Message msg: messages) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("messageId", msg.getId());
            tempObject.put("messageText", msg.getText());
            jsonArray.add(tempObject);
        }
        return jsonArray.toJSONString();
    }

    public static String getDeletedMessages(List<Long> ids){
        JSONArray jsonArray = new JSONArray();
        for (Long id: ids) {
            jsonArray.add(id);
        }
        return jsonArray.toJSONString();
    }

    public static String getUsers(List<User> users){
        JSONArray jsonArray = new JSONArray();
        for (User user: users) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("userId", user.getId());
            tempObject.put("username", user.getUserName());
            tempObject.put("userImage", "d://Time/UP/Chat/img/images2");
            jsonArray.add(tempObject);
        }
        return jsonArray.toJSONString();
    }

    public static JSONObject getParameter(HttpServletRequest req){
        JSONObject jsonObject = null;
        try{
            BufferedReader br = req.getReader();
            JSONParser parser = new JSONParser();
            try {
                jsonObject = (JSONObject) parser.parse(br.readLine());
            } catch (ParseException e) {
                logger.error(e);
            }
            br.close();
        } catch (IOException e) {
            logger.error(e);
        }
        return jsonObject;
    }

}
