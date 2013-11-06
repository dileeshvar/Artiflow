package com.ssn.artiflow.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	
	public static Connection getConnection(String SQLServerIP, String dbName) {
        String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/" + dbName;
        Boolean connectError = false;       // Error flag
        Connection DBConn = null;           // MySQL connection handle
        try {

            Class.forName("com.mysql.jdbc.Driver");
            DBConn = DriverManager.getConnection(sourceURL, "root", "root");
        } catch (Exception e) {
            System.out.println("\nProblem connecting to database:: " + e);
            connectError = true;
        } // end try-catch
        return DBConn;
    }
}
