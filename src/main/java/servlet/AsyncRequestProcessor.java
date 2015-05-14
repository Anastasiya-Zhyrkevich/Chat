package servlet;

import requests.UpdateRequest;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by User on 10.05.15.
 */
public class AsyncRequestProcessor {

    private static LinkedList <AsyncContext> storage = new LinkedList<AsyncContext>();

    public static void addContext(AsyncContext ac)
    {
        storage.add(ac);
    }

    public static void notifyAllUsers(){
        System.out.println(storage.size());
        for (AsyncContext ac: storage){
            try {
                UpdateRequest.proceedUpdateRequest(
                        (HttpServletRequest) ac.getRequest(),
                        (HttpServletResponse) ac.getResponse());
                ac.complete();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        storage.clear();
    }

}
