package com.ssn.artiflow.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ssn.artiflow.models.ArtifactType;
import com.ssn.artiflow.utils.ConnectDB;

public class ArtifactManager {

	private String SQLServerIP, databaseName;
	
	public ArtifactManager(String sqlServerIP, String databaseName) {
		super();
		SQLServerIP = sqlServerIP;
		this.databaseName = databaseName;
	}
	
	public int getArtifactId() throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from artifact");
		int nextId = 0;
		while(rs.next()) {
			int maxId = rs.getInt(1);
			nextId = ++maxId;
		}
		return nextId;
	}

	public ArtifactType getArtifactType(String s) throws SQLException {
		Connection DBConn = null;
		DBConn = ConnectDB.getConnection(SQLServerIP,databaseName);
		Statement statement = DBConn.createStatement();
		ResultSet rs = statement.executeQuery("select artifact_type_id from artifact_type where artifact_type_name='"+s+"'");
		ArtifactType artType = new ArtifactType();
		artType.setArtifactType(s);
		while(rs.next()) {
			int artTypeId = rs.getInt(1);
			artType.setArtifactTypeId(artTypeId);
		}
		return artType;
	}
}
