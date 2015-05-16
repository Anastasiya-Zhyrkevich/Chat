package converter;

import instances.Message;
import instances.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by User on 16.05.15.
 */
public class JSONConverter {
    public static String getMessages(List<Message> messages){
        JSONArray jsonArray = new JSONArray();
        for (Message msg: messages) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("userId", msg.getUserId());
            tempObject.put("messageId",  msg.getId());
            tempObject.put("messageText", msg.getText());
            tempObject.put("messageTime", msg.getTime());
            tempObject.put("isDeleted", "false");           //temporary
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

    public static String getDeletedMessages(List<Integer> ids){
        JSONArray jsonArray = new JSONArray();
        for (Integer id: ids) {
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
            jsonArray.add(tempObject);
        }
        return jsonArray.toJSONString();
    }

}
