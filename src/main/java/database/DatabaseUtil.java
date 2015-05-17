package database;

import exception.DatabaseException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by user on 17.05.2015.
 */
public class DatabaseUtil {

    private static final Logger logger = Logger.getLogger(DatabaseUtil.class);

    private DatabaseUtil() {

    }

    public static void close(Statement statement, Connection connection) throws DatabaseException {

        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Can't close statement " + e.getMessage(), e);
            }
        }

        ConnectionPool.getInstance().returnConnection(connection);
    }

    public static void close(ResultSet resultSet, Statement statement,
                             Connection connection) throws DatabaseException {

        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Can't close result set " + e.getMessage(), e);
            }
        }

        close(statement, connection);
    }
}
