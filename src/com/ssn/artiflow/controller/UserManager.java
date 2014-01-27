package com.ssn.artiflow.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ssn.artiflow.models.User;
import com.ssn.artiflow.utils.ConnectDB;

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
		ResultSet rs = statement.executeQuery("select user_id from user where user_name='"+ userName+"' and password = '"+password+"'");
		boolean isValid = false;
		User user = null;
		while(rs.next()) {
			int i = rs.getInt(1);
			user = new User();
			user.setUserId(i);
			user.setUserName(userName);
		}
		return user;
	}
}
