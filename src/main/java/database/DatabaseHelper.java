package database;

import instances.Message;
import instances.ProtocolObject;
import instances.User;

import java.sql.*;
import java.util.LinkedList;

/**
 * Created by User on 14.05.15.
 */
public final class DatabaseHelper {
    private static StringBuilder builder = new StringBuilder();
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/chat";
    static final String USER = "root";
    static final String PASS = "123456";

    private DatabaseHelper() {
    }

    public static int registerUser(String userName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT used_id FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()){
                userId = Integer.parseInt(rs.getString(1));
            }
            if (userId == 0){
                userId = registerNewUser(userName);
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userId;
    }


    public static int registerNewUser(String userName){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            userId = getNextFreeLine("users");
            String sql = "INSERT INTO users (user_id, username) VALUES (?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, userName);
            stmt.executeUpdate();
            addNewChange(new ProtocolObject("newUser", userId, userName));
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userId;


    }
    public static void editUser(String userName, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            userId = getNextFreeLine("users");
            String sql = "UPDATE users SET username = ? WHERE user_id = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getNextFreeLine(String tableName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int nextFree = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT COUNT(*) FROM " + tableName;
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            rs.next();
            nextFree = Integer.parseInt(rs.getString(1)) + 1;
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nextFree;
    }
    public static void addNewChange(ProtocolObject obj){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO protocol (id, type, user_id, text, mess_id) VALUES (?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, getNextFreeLine("protocol"));
            stmt.setString(2,obj.getType());
            stmt.setInt(3, obj.getUserId());
            stmt.setString(4,obj.getText());
            stmt.setInt(5, obj.getAddInfo());
            stmt.executeUpdate();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static LinkedList<Message> getMessages(int firstToken, int lastToken){
        LinkedList <Message> result = new LinkedList();
        if (lastToken < firstToken )
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM protocol WHERE ((type = 'sendMess') AND (id BETWEEN ? AND ?)) ORDER BY mess_id";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, firstToken);
            stmt.setInt(2, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()){
                Message msg = new Message(Integer.parseInt(rs.getString("id")),
                        rs.getString("text"), Integer.parseInt(rs.getString("user_id")),
                        Integer.parseInt(rs.getString("mess_id")));
                result.add(msg);
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static LinkedList<Message> getEditedMessages(int firstToken, int lastToken){
        LinkedList <Message> result = new LinkedList();
        if (lastToken < firstToken )
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM protocol WHERE ((type = 'editMess') AND (id BETWEEN ? AND ?)) ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, firstToken);
            stmt.setInt(2, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()){
                Message msg = new Message(Integer.parseInt(rs.getString("mess_id")),
                        rs.getString("text"), Integer.parseInt(rs.getString("user_id")));
                result.add(msg);
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static LinkedList<Integer> getDeleted(int firstToken, int lastToken){
        LinkedList <Integer> result = new LinkedList();
        if (lastToken < firstToken )
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM protocol WHERE ((type = 'deleteMess') AND (id BETWEEN ? AND ?)) ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, firstToken);
            stmt.setInt(2, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()){
                Integer messId = Integer.parseInt(rs.getString("mess_id"));
                result.add(messId);
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static LinkedList<User> getUsers(int firstToken, int lastToken, String type){
        LinkedList <User> result = new LinkedList();
        if (lastToken < firstToken )
            return result;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM protocol WHERE ((type = ?) AND (id BETWEEN ? AND ?)) ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setInt(2, firstToken);
            stmt.setInt(3, lastToken);
            rs = stmt.executeQuery();
            while (rs.next()){
                User user = new User(Integer.parseInt(rs.getString("mess_id")),
                        rs.getString("text"));
                result.add(user);
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
