package servlet;

import exception.ManagerException;
import org.apache.log4j.Logger;
import util.JSONConverter;
import listener.AppAsyncListener;
import manager.ChangeManager;
import manager.RequestManager;
import org.json.simple.JSONObject;
import util.AsyncRequestProcessor;

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

    private static final Logger logger = Logger.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String type = req.getParameter("type");

        if (type.compareTo("GET_UPDATE") == 0 && Integer.parseInt(req.getParameter("token")) != 0) {
            req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
            AsyncContext ac = req.startAsync();
            ac.setTimeout(10000);
            ac.addListener(new AppAsyncListener());
            AsyncRequestProcessor.addContext(ac);
        }
        try {
            if (type.compareTo("GET_UPDATE") == 0 && Integer.parseInt(req.getParameter("token")) == 0) {
                RequestManager.updateRequest(req, resp);
            }
            if (type.compareTo("BASE_REQUEST") == 0)
                RequestManager.baseRequest(req, resp);
        } catch (ManagerException e) {
            logger.error(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ChangeManager.sendMessageRequest(req, resp);
            AsyncRequestProcessor.notifyAllUsers();
        } catch (ManagerException e) {
            logger.error(e);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = JSONConverter.getParameter(req);
        String type = (String) jsonObject.get("type");
        try {
            if (type.compareTo("CHANGE_MESSAGE") == 0)
                ChangeManager.changeMessageRequest(req, resp, jsonObject);
            if (type.compareTo("CHANGE_USERNAME") == 0)
                ChangeManager.changeUserRequest(req, resp, jsonObject);
                AsyncRequestProcessor.notifyAllUsers();
        } catch (ManagerException e) {
            logger.error(e);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ChangeManager.deleteMessageRequest(req, resp);
            AsyncRequestProcessor.notifyAllUsers();
        } catch (ManagerException e) {
            logger.error(e);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }


}
