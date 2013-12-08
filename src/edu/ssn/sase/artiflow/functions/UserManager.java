package edu.ssn.sase.artiflow.functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ssn.sase.artiflow.models.User;
import edu.ssn.sase.artiflow.utils.ConnectDB;

public class UserManager {
	private String SQLServerIP, databaseName;
	
	public UserManager(String sqlServerIP, String databaseName) {
		super();
		SQLServerIP = sqlServerIP;
		this.databaseName = databaseName;
	}

	public int getUserId(String user_name) throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select user_id from user where user_name='"+ user_name+"'");
		int userId = 0;
		while(rs.next()) {
			userId = rs.getInt(1);
		}
		return userId;
	}
	
	public String getUserName(int user_id) throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select user_name from user where user_id='"+ user_id+"'");
		String userName = "";
		while(rs.next()) {
			userName = rs.getString(1);
		}
		return userName;
	}
	
	public int getReviewerId() throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from reviewers");
		int nextId = 0;
		while(rs.next()) {
			int maxId = rs.getInt(1);
			nextId = ++maxId;
		}
		return nextId;
	}
	
	public User checkLogin(String userName, String password) throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select user_id, email_id from user where user_name='"+ userName+"' and password = '"+password+"'");
		User user = null;
		while(rs.next()) {
			user = new User();
			user.setUserId(rs.getInt(1));
			user.setUserName(userName);
			user.setEmail(rs.getString(2));
		}
		return user;
	}
}
