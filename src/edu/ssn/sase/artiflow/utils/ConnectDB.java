package edu.ssn.sase.artiflow.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	
	public static Connection getConnection(String SQLServerIP, String dbName) {
        String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/" + dbName;
        Connection DBConn = null;           // MySQL connection handle
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("");
            DBConn = DriverManager.getConnection(sourceURL, "root", "root");
        } catch (Exception e) {
            System.out.println("\nProblem connecting to database:: " + e);
        } // end try-catch
        return DBConn;
    }
}
