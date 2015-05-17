package listener;

import database.ConnectionPool;
import exception.DatabaseException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 10.05.15.
 */

@WebListener
public class AppListener implements ServletContextListener {

    private static final Logger logger = Logger.getRootLogger();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance();
        } catch (DatabaseException e) {
            logger.error(e);
        }

        // create the thread pool
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 50000L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        servletContextEvent.getServletContext().setAttribute("executor",
                executor);

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            try {
                ConnectionPool.getInstance().destroy();
            } catch (DatabaseException e) {
                logger.error(e);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) servletContextEvent
                .getServletContext().getAttribute("executor");
        executor.shutdown();
    }

}