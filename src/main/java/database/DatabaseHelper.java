package database;

import instances.Message;
import instances.ProtocolObject;

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
                userId = getNextFreeLine("users");
                sql = "INSERT INTO users (user_id, username) VALUES (?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
                stmt.setString(2, userName);
                rs = stmt.executeQuery();
                addNewChange(new ProtocolObject("newUser", userId, userName));
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

    public static int getNextFreeLine(String tableName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int nextFree = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT COUNT(*) FROM ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tableName);
            rs = stmt.executeQuery();
            rs.next();
            nextFree = Integer.parseInt(rs.getString(1));
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
            rs = stmt.executeQuery();
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
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM protocol WHERE ((type = 'sendMess') AND (id BETWEEN ? AND ?)) ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, firstToken);
            stmt.setInt(2, firstToken);
            rs = stmt.executeQuery();

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


}
