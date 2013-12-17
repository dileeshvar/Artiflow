package edu.ssn.sase.artiflow.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ArtiflowDal {

	Connection DBConn = null;
	Statement statement = null;

	public void initiateParams(String SQLServerIP, String databaseName)
			throws SQLException {
		DBConn = ConnectDB.getConnection(SQLServerIP, databaseName);
		statement = DBConn.createStatement();
	}

	public ResultSet getProjects(int id) throws SQLException {
		ResultSet rs = statement
				.executeQuery("select project_name from project where project_id = "
						+ id);
		return rs;
	}

	public ResultSet getUsersFromUserId(int uId) throws SQLException {
		ResultSet rs = statement
				.executeQuery("select user_name from user where user_id=" + uId);
		return rs;
	}

	public ResultSet getUsersOfProject(int projId) throws SQLException {
		ResultSet rs = statement
				.executeQuery("select user_id from user_project where project_id="
						+ projId);
		return rs;
	}

	public ResultSet getProjectOfUser(int uId) throws SQLException {
		ResultSet rs = statement
				.executeQuery("select project_id from user_project where user_id="
						+ uId);
		return rs;
	}

	public ResultSet getAllArtifactTypes() throws SQLException {
		ResultSet rs = statement
				.executeQuery("select artifact_type_name from artifact_type");
		return rs;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		DBConn.close();
	}
}
