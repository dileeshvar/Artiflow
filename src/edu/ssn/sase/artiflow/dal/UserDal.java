package edu.ssn.sase.artiflow.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ssn.sase.artiflow.utils.ConnectDB;

public class UserDal {
	Connection DBConn = null;
	Statement statement = null;
	
	public void initiateParams(String SQLServerIP, String databaseName)
			throws SQLException {
		DBConn = ConnectDB.getConnection(SQLServerIP, databaseName);
		statement = DBConn.createStatement();
	}

	public ResultSet getUserIdFromUserName(String user_name)
			throws SQLException {
		ResultSet rs = statement
				.executeQuery("select user_id from user where user_name='"
						+ user_name + "'");
		return rs;
	}

	public ResultSet getUserNameFromUserId(int uId) throws SQLException {
		ResultSet rs = statement
				.executeQuery("select user_name from user where user_id='"
						+ uId + "'");
		return rs;
	}

	public ResultSet getTotalReviewers() throws SQLException {
		ResultSet rs = statement.executeQuery("select count(*) from reviewers");
		return rs;
	}

	public ResultSet validateUser(String userName, String password)
			throws SQLException {
		ResultSet rs = statement
				.executeQuery("select user_id, email_id from user where user_name='"
						+ userName + "' and password = '" + password + "'");
		return rs;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		DBConn.close();
	}
}
