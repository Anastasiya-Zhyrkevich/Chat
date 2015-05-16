package servlet;

import converter.JSONConverter;
import database.DatabaseHelper;
import instances.ProtocolObject;
import javafx.collections.ListChangeListener;
import listener.AppAsyncListener;
import manager.ChangeManager;
import manager.RequestManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by User on 29.03.15.
 */
@WebServlet(asyncSupported = true)
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type.compareTo("GET_UPDATE") == 0 && Integer.parseInt(req.getParameter("token")) != 0)
        {
            req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
            AsyncContext ac = req.startAsync();
            ac.setTimeout(10);
            ac.addListener(new AppAsyncListener());
            AsyncRequestProcessor.addContext(ac);
        }
        if (type.compareTo("GET_UPDATE") == 0 && Integer.parseInt(req.getParameter("token")) == 0)
        {
            RequestManager.updateRequest(req, resp);
        }
        if (type.compareTo("BASE_REQUEST") == 0)
            RequestManager.baseRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangeManager.sendMessageRequest(req, resp);
        System.out.println("doPost");
        AsyncRequestProcessor.notifyAllUsers();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = JSONConverter.getParameter(req);
        String type = (String)jsonObject.get("type");
        if (type.compareTo("CHANGE_MESSAGE") == 0)
            ChangeManager.changeMessageRequest(req, resp, jsonObject);
        if (type.compareTo("CHANGE_USERNAME") == 0)
            ChangeManager.changeUserRequest(req, resp, jsonObject);
        AsyncRequestProcessor.notifyAllUsers();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangeManager.deleteMessageRequest(req, resp);
        AsyncRequestProcessor.notifyAllUsers();
    }










}
