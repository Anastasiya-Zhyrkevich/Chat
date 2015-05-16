package servlet;

import manager.RequestManager;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by User on 10.05.15.
 */

public class AsyncRequestProcessor {

    private static LinkedBlockingQueue<AsyncContext> storage = new LinkedBlockingQueue<AsyncContext>();

    public static void addContext(AsyncContext ac)
    {
        try {
            storage.put(ac);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void removeContext(AsyncContext ac)
    {
        storage.remove(ac);
    }

    public static void notifyAllUsers(){
        System.out.println(storage.size());
        while (!storage.isEmpty()){
            try {
                AsyncContext ac = null;
                ac = storage.take();
                RequestManager.updateRequest( (HttpServletRequest) ac.getRequest(),
                    (HttpServletResponse) ac.getResponse());
                ac.complete();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }

    }

}
