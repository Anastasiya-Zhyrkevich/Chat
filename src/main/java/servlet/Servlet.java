package servlet;

import database.DatabaseHelper;
import instances.ProtocolObject;
import javafx.collections.ListChangeListener;
import manager.ChangeManager;
import manager.RequestManager;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

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
        ChangeManager.sendMessageRequest(req, resp);
        System.out.println("doPost");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type.compareTo("CHANGE_MESSAGE") == 0)
            ChangeManager.changeMessageRequest(req, resp);
        if (type.compareTo("CHANGE_USERNAME") == 0)
            ChangeManager.changeUserRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangeManager.deleteMessageRequest(req, resp);
    }










}
