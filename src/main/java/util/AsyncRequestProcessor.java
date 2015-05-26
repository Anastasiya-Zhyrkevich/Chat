package util;

import exception.ManagerException;
import manager.RequestManager;
import org.apache.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by User on 10.05.15.
 */

public class AsyncRequestProcessor {

    private static final Logger logger = Logger.getRootLogger();
    //private static LinkedBlockingQueue<AsyncContext> storage = new LinkedBlockingQueue<AsyncContext>();
    private static List<AsyncContext> storage = Collections.synchronizedList(new ArrayList<AsyncContext>());
    private static List<AsyncContext> tempstorage = Collections.synchronizedList(new ArrayList<AsyncContext>());
    private static boolean inProcess = false;



    public static void addContext(AsyncContext ac)
    {
            if (inProcess == false){
                storage.add(ac);
            }
            else{
                storage.add(ac);
            }

    }

    public static void removeContext(AsyncContext ac)
    {
        storage.remove(ac);
    }

    /*public static void notifyAllUsers() throws ManagerException {
        while (!storage.isEmpty()){
            try {
                AsyncContext ac = null;
                ac = storage.take();
                RequestManager.updateRequest( (HttpServletRequest) ac.getRequest(),
                    (HttpServletResponse) ac.getResponse());
                ac.complete();
            } catch (InterruptedException e) {
               logger.error(e);
            }
        }

    }*/

    public static void clearContainer() throws ManagerException{
        storage.clear();
        for (AsyncContext ac: tempstorage){
            storage.add(ac);
        }
        tempstorage.clear();
    }

    public static void notifyAllUsers() throws ManagerException {
        inProcess = true;
        for (AsyncContext ac: storage) {
            RequestManager.updateRequest((HttpServletRequest) ac.getRequest(),
                    (HttpServletResponse) ac.getResponse());
            ac.complete();
        }
        clearContainer();
        inProcess = false;
    }

}
