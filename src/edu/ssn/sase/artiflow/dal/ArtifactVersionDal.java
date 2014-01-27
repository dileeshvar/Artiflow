package edu.ssn.sase.artiflow.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.ssn.sase.artiflow.models.ArtifactVersion;
import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ArtifactVersionDal {
	
	Connection DBConn = null;
	Statement statement = null;

	public void initiateParams(String SQLServerIP, String databaseName)
			throws SQLException {
		DBConn = ConnectDB.getConnection(SQLServerIP, databaseName);
		statement = DBConn.createStatement();
	}
	
	public void initiateParams()
			throws SQLException {
		DBConn = ConnectDB.getConnection("localhost", "artiflow");
		statement = DBConn.createStatement();
	}
	
	public ArrayList<ArtifactVersion> getArtifactData(int artifactId){
		ArrayList<ArtifactVersion> artifactVersions = new ArrayList<ArtifactVersion>();
		try {
			ResultSet rs = statement
					.executeQuery("select * from artifact_version where artifact_id in "
							+ "(select artifact_id from artifact where artifact_map_id = "
							+ "(select artifact_map_id from artifact where artifact_id = "+artifactId+"));");
			while (rs.next()) {
				ArtifactVersion artifactVersion = new ArtifactVersion();
				artifactVersion.setArtifactVersionId(rs.getInt(1));
				artifactVersion.setArtifactId(rs.getInt(3));
				artifactVersion.setVersionNo(rs.getInt(2));
				artifactVersions.add(artifactVersion);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artifactVersions;		
	}
	

}
