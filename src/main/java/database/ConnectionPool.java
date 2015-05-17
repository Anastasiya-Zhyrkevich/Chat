package database;

import exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by user on 17.05.2015.
 */
public class ConnectionPool {

    private static volatile ConnectionPool instance;
    private BlockingQueue<Connection> freeConnection;
    private BlockingQueue<Connection> busyConnection;

    private final static int MAX_CONNECTION_COUNT = 5;
    private final static String CONFIG = "jdbc";
    private final static String DRIVER = "driver";
    private final static String URL = "url";
    private final static String USER = "user";
    private final static String PASSWORD = "password";

    private ConnectionPool() throws DatabaseException {
        freeConnection = new LinkedBlockingQueue(MAX_CONNECTION_COUNT);
        busyConnection = new ArrayBlockingQueue(MAX_CONNECTION_COUNT);
        ResourceBundle config = ResourceBundle.getBundle(CONFIG);
        try {
            Class.forName(config.getString(DRIVER));
            for (int i = 0; i < MAX_CONNECTION_COUNT; i++) {
                Connection connection = DriverManager.getConnection(
                        config.getString(URL), config.getString(USER),
                        config.getString(PASSWORD));
                freeConnection.add(connection);
            }
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Class not found, can't create pool" + e.getMessage(), e);
        } catch (SQLException e) {
            throw new DatabaseException("Sql exception, can't create pool" + e.getMessage(), e);
        }
    }

    public static ConnectionPool getInstance() throws DatabaseException {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws DatabaseException {
        Connection connection;
        try {
            connection = freeConnection.take();
            busyConnection.add(connection);
        } catch (InterruptedException e) {
            throw new DatabaseException("Can't take pool connection " + e.getMessage(), e);
        }
        return connection;
    }

    public void returnConnection(Connection connection) throws DatabaseException {

        if (busyConnection.contains(connection)) {
            freeConnection.add(connection);
            busyConnection.remove(connection);
        } else {
            throw new DatabaseException("Try to return not pool connection");
        }
    }

    public void destroy() throws SQLException {
        for (Connection connection : freeConnection) {
            connection.close();
        }
        for (Connection connection : busyConnection) {
            connection.close();
        }
    }

}
