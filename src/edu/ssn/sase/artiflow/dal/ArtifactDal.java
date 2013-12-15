package edu.ssn.sase.artiflow.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ArtifactDal {

	Connection DBConn = null;
	Statement statement = null;

	public void initiateParams(String SQLServerIP, String databaseName)
			throws SQLException {
		DBConn = ConnectDB.getConnection(SQLServerIP, databaseName);
		statement = DBConn.createStatement();
	}

	public ResultSet getArtifactType(String artTypeName) throws SQLException {
		ResultSet rs = statement
				.executeQuery("select artifact_type_id from artifact_type where artifact_type_name='"
						+ artTypeName + "'");
		return rs;
	}

	public ResultSet getCurrentArtifactCount() throws SQLException {
		ResultSet rs = statement.executeQuery("select count(*) from artifact");
		return rs;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		DBConn.close();
	}
}
