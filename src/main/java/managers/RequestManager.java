package managers;

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
        int lastToken = Integer.parseInt(req.getParameter("token"));
        JSONObject jsonObject = new JSONObject();

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
