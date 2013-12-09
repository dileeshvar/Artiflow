package edu.ssn.sase.artiflow.functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ArtiflowManager {

	private String SQLServerIP, databaseName;

	public ArtiflowManager(String sqlServerIP, String databaseName) {
		super();
		SQLServerIP = sqlServerIP;
		this.databaseName = databaseName;
	}

	public List<String> getProjects(List<Integer> projIds) throws SQLException {
		Connection DBConn = null;
		List<String> projectList = new ArrayList<String>();
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		for(int id : projIds) {
			ResultSet rs = statement.executeQuery("select project_name from project where project_id = "+id);
			while(rs.next()) {
				String projectName = rs.getString(1);
				projectList.add(projectName);
			}
		}
		return projectList;
	}

	public List<String> getUsersOfProject(List<Integer> projIds) throws SQLException {
		Connection DBConn = null;
		List<Integer> userIds = getUserIds(projIds);
		List<String> otherUsers = new ArrayList<String>();
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		for(int uId : userIds) {
			ResultSet rs = statement.executeQuery("select user_name from user where user_id=" + uId);
			while(rs.next()) {
				String userName = rs.getString(1);
				otherUsers.add(userName);
			}
		}
		return otherUsers;
	}

	private List<Integer> getUserIds(List<Integer> projectIds) throws SQLException {
		List<Integer> userIdList = new ArrayList<Integer>();
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		for (int projId : projectIds) {
			ResultSet rs = statement.executeQuery("select user_id from user_project where project_id=" + projId);
			while(rs.next()) {
				int userId = rs.getInt(1);
				userIdList.add(userId);
			}
		}
		return userIdList;
	}

	public List<Integer> getProjectId(int userId) throws SQLException {
		Connection DBConn = null;
		List<Integer> projectList = new ArrayList<Integer>();
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select project_id from user_project where user_id=" + userId);
		while(rs.next()) {
			int projectId = rs.getInt(1);
			projectList.add(projectId);
		}
		return projectList;
	}

	public List<String> getArtifactType() throws SQLException {
		Connection DBConn = null;
		List<String> artiList = new ArrayList<String>();
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select artifact_type_name from artifact_type");
		while(rs.next()) {
			String artiType = rs.getString(1);
			artiList.add(artiType);
		}
		return artiList;
	}
}
