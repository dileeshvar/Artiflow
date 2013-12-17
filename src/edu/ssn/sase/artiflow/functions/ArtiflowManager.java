package edu.ssn.sase.artiflow.functions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ssn.sase.artiflow.dal.ArtiflowDal;

public class ArtiflowManager {

	ArtiflowDal dal = new ArtiflowDal();

	public ArtiflowManager(String sqlServerIP, String databaseName) {
		super();
		try {
			dal.initiateParams(sqlServerIP, databaseName);
		} catch (SQLException e) {
			System.out.println("Failure Happened!!!");
		}
	}

	public List<String> getProjects(List<Integer> projIds) throws SQLException {
		List<String> projectList = new ArrayList<String>();
		for (int id : projIds) {
			ResultSet rs = dal.getProjects(id);
			while (rs.next()) {
				String projectName = rs.getString(1);
				projectList.add(projectName);
			}
		}
		return projectList;
	}

	public List<String> getUsersOfProject(List<Integer> projIds)
			throws SQLException {
		List<Integer> userIds = getUserIds(projIds);
		List<String> otherUsers = new ArrayList<String>();
		for (int uId : userIds) {
			ResultSet rs = dal.getUsersFromUserId(uId);
			while (rs.next()) {
				String userName = rs.getString(1);
				otherUsers.add(userName);
			}
		}
		return otherUsers;
	}

	private List<Integer> getUserIds(List<Integer> projectIds)
			throws SQLException {
		List<Integer> userIdList = new ArrayList<Integer>();
		for (int projId : projectIds) {
			ResultSet rs = dal.getUsersOfProject(projId);
			while (rs.next()) {
				int userId = rs.getInt(1);
				userIdList.add(userId);
			}
		}
		return userIdList;
	}

	public List<Integer> getProjectId(int userId) throws SQLException {
		List<Integer> projectList = new ArrayList<Integer>();
		ResultSet rs = dal.getProjectOfUser(userId);
		while (rs.next()) {
			int projectId = rs.getInt(1);
			projectList.add(projectId);
		}
		return projectList;
	}

	public List<String> getArtifactType() throws SQLException {
		List<String> artiList = new ArrayList<String>();
		ResultSet rs = dal.getAllArtifactTypes();
		while (rs.next()) {
			String artiType = rs.getString(1);
			artiList.add(artiType);
		}
		return artiList;
	}
}
