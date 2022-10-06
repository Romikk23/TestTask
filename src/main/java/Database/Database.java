package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    private static final String url = "jdbc:mysql://db4free.net:3306/tzerrszfs";
    private static final String user = "tzerrszfs";
    private static final String password = "password";

    /* ***************  Main Database  *************** */

    public static Connection Database() throws ClassNotFoundException, SQLException {
        conn = null;
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        statmt.close();
        resSet.close();
    }

    /* ***************  User  *************** */

    public static void addExpression(String expression, String result) throws ClassNotFoundException {
        String sql = "INSERT INTO Math(expression ,result) VALUES(?,?)";
        try (Connection conn = Database();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, expression);
            pstmt.setString(2, result);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static List<String> getExpressions() throws SQLException, ClassNotFoundException {
        List<String> expressions = new ArrayList();
        Connection conn = Database();
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM Math");
        while(resSet.next())
        {
            String expression = resSet.getString("expression");
            String result = resSet.getString("result");
            expressions.add(expression + " = " + result);
        }
        return expressions;
    }
    public static void updateExpression(String oldExpression, String expression, String result) throws ClassNotFoundException {
        String sql = "UPDATE Math SET expression = ?, result = ? WHERE expression = ?";
        try (Connection conn = Database();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, expression);
            pstmt.setString(2, result);
            pstmt.setString(3, oldExpression);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void clear() throws ClassNotFoundException {
        String sql = "DELETE FROM Math";
        try (Connection conn = Database();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> getExpressionsWithParamether(String operator, String num) throws SQLException, ClassNotFoundException {
        List<String> expressions = new ArrayList();
        Connection conn = Database();
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM Math WHERE result "+operator + " " + num);
        while(resSet.next())
        {
            String expression = resSet.getString("expression");
            String result = resSet.getString("result");
            expressions.add(expression + " = " + result);
        }
        return expressions;
    }

}

