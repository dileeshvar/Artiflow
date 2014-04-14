package edu.ssn.sase.artiflow.functions;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ssn.sase.artiflow.dal.ArtifactDal;
import edu.ssn.sase.artiflow.models.Artifact;
import edu.ssn.sase.artiflow.models.ArtifactType;

public class ArtifactManager {

	ArtifactDal dal = new ArtifactDal();

	public ArtifactManager(String sqlServerIP, String databaseName) {
		super();
		try {
			dal.initiateParams(sqlServerIP, databaseName);
		} catch (SQLException e) {
			System.out.println("Failure Happened!!!");
		}
	}

	public int getArtifactId() throws SQLException {
		ResultSet rs = dal.getCurrentArtifactCount();
		int nextId = 0;
		while (rs.next()) {
			int maxId = rs.getInt(1);
			nextId = ++maxId;
		}
		return nextId;
	}

	public ArtifactType getArtifactType(String s) throws SQLException {
		ResultSet rs = dal.getArtifactType(s);
		ArtifactType artType = new ArtifactType();
		artType.setArtifactType(s);
		while (rs.next()) {
			int artTypeId = rs.getInt(1);
			artType.setArtifactTypeId(artTypeId);
		}
		return artType;
	}
	
	public Artifact getArtifact(int artiId) {
		return dal.getArtifact(artiId);
	}

	public void uploadNewArtifact(Artifact newArtifact) {
		try {
			dal.uploadArtifact(newArtifact);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateOldArtifact(Artifact oldArtifact) {
		try {
			dal.updateArtifact(oldArtifact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
