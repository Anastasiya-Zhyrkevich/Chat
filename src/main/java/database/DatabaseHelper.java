package database;

import entity.Message;
import entity.ClientProtocol;
import entity.User;
import exception.DatabaseException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 14.05.15.
 */
public final class DatabaseHelper {

    private final static String SQL_SELECT_USER_QUERY = "SELECT user_id FROM users WHERE username = ?";
    private final static String SQL_INSERT_USER_QUERY = "INSERT INTO users (user_id, username) VALUES (?,?)";
    private final static String SQL_UPDATE_USER_QUERY = "UPDATE users SET username = ? WHERE user_id = ? ";
    private final static String SQL_SELECT_COUNT_QUERY = "SELECT COUNT(*) FROM ";
    private final static String SQL_INSERT_PROTOCOL_QUERY = "INSERT INTO protocol (id, type, user_id, text, mess_id)" +
            " VALUES (?,?,?,?,?)";
    private final static String SQL_SELECT_MESSAGES_QUERY = "SELECT * FROM protocol WHERE ((type = 'sendMess')" +
            " AND (id BETWEEN ? AND ?)) ORDER BY mess_id";
    private final static String SQL_SELECT_EDITED_MESSAGES_QUERY = "SELECT * FROM protocol WHERE ((type = 'editMess')" +
            " AND (id BETWEEN ? AND ?)) ";
    private final static String SQL_SELECT_DELETED_MESSAGES_QUERY = "SELECT * FROM protocol WHERE ((type = 'deleteMess')" +
            " AND (id BETWEEN ? AND ?)) ";
    private final static String SQL_SELECT_PROTOCOL_USERS_QUERY = "SELECT * FROM protocol WHERE ((type = ?)" +
            " AND (id BETWEEN ? AND ?)) ";


    private DatabaseHelper() {
    }

    public static int registerUser(String userName) throws DatabaseException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = 0;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_USER_QUERY);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userId = Integer.parseInt(rs.getString(1));
            }
            if (userId == 0) {
                userId = registerNewUser(userName);
            }
        } catch (SQLException se) {
            throw new DatabaseException("Can't register user. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
        return userId;
    }


    public static int registerNewUser(String userName) throws DatabaseException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int userId = 0;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            userId = getNextFreeLine("users");
            stmt = conn.prepareStatement(SQL_INSERT_USER_QUERY);
            stmt.setInt(1, userId);
            stmt.setString(2, userName);
            stmt.executeUpdate();
            addNewChange(new ClientProtocol("newUser", userId, userName));
        } catch (SQLException se) {
            throw new DatabaseException("Can't register new user. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(stmt, conn);
        }
        return userId;
    }

    public static void editUser(String userName, int userId) throws DatabaseException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            userId = getNextFreeLine("users");
            stmt = conn.prepareStatement(SQL_UPDATE_USER_QUERY);
            stmt.setString(1, userName);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            throw new DatabaseException("Can't edit user. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(stmt, conn);
        }
    }

    public static int getNextFreeLine(String tableName) throws DatabaseException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int nextFree = 0;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            String sql = SQL_SELECT_COUNT_QUERY + tableName;
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            rs.next();
            nextFree = Integer.parseInt(rs.getString(1)) + 1;
        } catch (SQLException se) {
            throw new DatabaseException("Can't get next free line. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
        return nextFree;
    }

    public static void addNewChange(ClientProtocol obj) throws DatabaseException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_PROTOCOL_QUERY);
            stmt.setInt(1, getNextFreeLine("protocol"));
            stmt.setString(2, obj.getType());
            stmt.setInt(3, obj.getUserId());
            stmt.setString(4, obj.getText());
            stmt.setInt(5, obj.getAddInfo());
            stmt.executeUpdate();
        } catch (SQLException se) {
            throw new DatabaseException("Can't add new change. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(stmt, conn);
        }

    }

    public static List<Message> getMessages(int firstToken, int lastToken) throws DatabaseException {
        List<Message> result = new LinkedList<Message>();
        if (lastToken < firstToken)
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_MESSAGES_QUERY);
            stmt.setInt(1, firstToken);
            stmt.setInt(2, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message(Integer.parseInt(rs.getString("id")),
                        rs.getString("text"), Integer.parseInt(rs.getString("user_id")),
                        Integer.parseInt(rs.getString("mess_id")));
                result.add(msg);
            }
        } catch (SQLException se) {
            throw new DatabaseException("Can't get messages. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
        return result;
    }

    public static List<Message> getEditedMessages(int firstToken, int lastToken) throws DatabaseException {
        List<Message> result = new LinkedList<Message>();
        if (lastToken < firstToken)
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_EDITED_MESSAGES_QUERY);
            stmt.setInt(1, firstToken);
            stmt.setInt(2, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message(Integer.parseInt(rs.getString("mess_id")),
                        rs.getString("text"), Integer.parseInt(rs.getString("user_id")));
                result.add(msg);
            }
        } catch (SQLException se) {
            throw new DatabaseException("Can't get edited messages. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
        return result;
    }

    public static List<Integer> getDeleted(int firstToken, int lastToken) throws DatabaseException {
        List<Integer> result = new LinkedList<Integer>();
        if (lastToken < firstToken)
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_DELETED_MESSAGES_QUERY);
            stmt.setInt(1, firstToken);
            stmt.setInt(2, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer messId = Integer.parseInt(rs.getString("mess_id"));
                result.add(messId);
            }
        } catch (SQLException se) {
            throw new DatabaseException("Can't get deleted messages. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
        return result;
    }

    public static List<User> getUsers(int firstToken, int lastToken, String type) throws DatabaseException {
        List<User> result = new LinkedList<User>();
        if (lastToken < firstToken)
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PROTOCOL_USERS_QUERY);
            stmt.setString(1, type);
            stmt.setInt(2, firstToken);
            stmt.setInt(3, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(Integer.parseInt(rs.getString("user_id")),
                        rs.getString("text"));
                result.add(user);
            }
        } catch (SQLException se) {
            throw new DatabaseException("Can't get users. " + se.getMessage(), se);
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
        return result;
    }

}
