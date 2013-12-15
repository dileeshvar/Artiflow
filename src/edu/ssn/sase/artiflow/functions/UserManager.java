package edu.ssn.sase.artiflow.functions;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ssn.sase.artiflow.dal.UserDal;
import edu.ssn.sase.artiflow.models.User;

public class UserManager {
	private UserDal dal = new UserDal();

	public UserManager(String sqlServerIP, String databaseName) {
		super();
		try {
			dal.initiateParams(sqlServerIP, databaseName);
		} catch (SQLException e) {
			System.out.println("Failure Happened!!!");
		}
	}

	public int getUserId(String user_name) throws SQLException {
		ResultSet rs = dal.getUserIdFromUserName(user_name);
		int userId = 0;
		while (rs.next()) {
			userId = rs.getInt(1);
		}
		return userId;
	}

	public String getUserName(int user_id) throws SQLException {
		ResultSet rs = dal.getUserNameFromUserId(user_id);
		String userName = "";
		while (rs.next()) {
			userName = rs.getString(1);
		}
		return userName;
	}

	public int getReviewerId() throws SQLException {
		ResultSet rs = dal.getTotalReviewers();
		int nextId = 0;
		while (rs.next()) {
			int maxId = rs.getInt(1);
			nextId = ++maxId;
		}
		return nextId;
	}

	public User checkLogin(String userName, String password)
			throws SQLException {
		ResultSet rs = dal.validateUser(userName, password);
		User user = null;
		while (rs.next()) {
			user = new User();
			user.setUserId(rs.getInt(1));
			user.setUserName(userName);
			user.setEmail(rs.getString(2));
		}
		return user;
	}
}
