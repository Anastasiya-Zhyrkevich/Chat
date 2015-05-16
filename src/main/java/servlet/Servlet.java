package servlet;

import database.DatabaseHelper;
import instances.Message;
import instances.ProtocolObject;
import managers.RequestManager;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by User on 29.03.15.
 */
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type.compareTo("GET_UPDATE") == 0)
            RequestManager.updateRequest(req, resp);
        if (type.compareTo("BASE_REQUEST") == 0)
            RequestManager.baseRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message msg = new Message(req.getParameter("messageText"),
                Integer.parseInt(req.getParameter("userId")), (int)(System.currentTimeMillis()));
        DatabaseHelper.addNewChange(new ProtocolObject("sendMess", msg));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            BufferedReader br = req.getReader();
            JSONParser parser = new JSONParser();
            try {
                JSONArray jsonArray = (JSONArray)parser.parse(br.readLine());
                for (Object s: jsonArray) {
                    DatabaseHelper.addNewChange(new ProtocolObject("deleteMess", Integer.parseInt((String)s)));
                }
    }










}
