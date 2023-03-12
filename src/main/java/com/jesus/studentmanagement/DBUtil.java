package com.jesus.studentmanagement;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {
    private static final String DB_NAME = "students.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\jesus\\Documents\\JavaApplications\\StudentManagement\\"
            + DB_NAME;
    private static Connection conn = null;

    // Connect to DB
    public static void dbConnect() throws SQLException {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("Cannot connect" + e);
            e.printStackTrace();
            throw e;
        }
    }

    // Disconnect from DB
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Disconnected");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    // Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
        // Declare statement, resultSet
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try {
            // Connect to database
            dbConnect();
            // Create statement
            stmt = conn.createStatement();
            // Get resultset from executing query
            resultSet = stmt.executeQuery(queryStmt);
            // Use CachedRowSetImpl so that there is no error when we close db in finally block
            //crs = new CachedRowSetImpl();
            //crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred in dbExecuteQuery" + e);
            throw e;
        }/* finally {
            // Close resultset, statement, and database
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }*/
        return resultSet;
    }

    // Execute update query
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
        Statement stmt = null;
        try {
            // Execute update
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e ) {
            System.out.println("Something went wrong with dbExecuteUpdate" + e);
            throw e;
        } finally {
            // Close everything
            if(stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

}
