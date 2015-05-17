package listener;


import exception.ManagerException;
import manager.RequestManager;
import org.apache.log4j.Logger;
import util.AsyncRequestProcessor;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 10.05.15.
 */
@WebListener
public class AppAsyncListener implements AsyncListener {

    private static final Logger logger = Logger.getRootLogger();


    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
       logger.debug("AppAsyncListener onComplete");
        // we can do resource cleanup activity here
    }

    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        logger.debug("AppAsyncListener onError");
        //we can return error response to client
    }

    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        logger.debug("AppAsyncListener onStartAsync");
        //we can log the event here
    }

    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        logger.debug("AppAsyncListener onTimeout");
        AsyncContext ac = asyncEvent.getAsyncContext();
        try {
            RequestManager.updateRequest((HttpServletRequest) ac.getRequest(),
                    (HttpServletResponse) ac.getResponse());
        } catch (ManagerException e) {
            logger.error(e);
        }
        AsyncRequestProcessor.removeContext(ac);
        //we can send appropriate response to client
    }

}
