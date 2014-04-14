package edu.ssn.sase.artiflow.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;

import edu.ssn.sase.artiflow.utils.ConnectDB;

public class ChartDataBuilder {
	Connection DBConn = null;
	PreparedStatement statement = null;
	
	public ChartDataBuilder(String sqlServerIP, String databaseName) {
		super();
		try {
			initiateParams(sqlServerIP, databaseName);
		} catch (SQLException e) {
			System.out.println("Failure Happened!!!");
		}
	}

	public void initiateParams(String SQLServerIP, String databaseName)
			throws SQLException {
		DBConn = ConnectDB.getConnection(SQLServerIP, databaseName);
	}
	
	public void initiateParams()
			throws SQLException {
		DBConn = ConnectDB.getConnection("localhost", "artiflow");
	}
	
	private ResultSet artifactReview(Timestamp start, Timestamp End) throws SQLException{
		statement = DBConn.prepareStatement("select artifact_type_id, min(date_created) start,  max(date_last_modified) end, "
				+ " datediff(max(date_last_modified), min(date_created)) difference from artifact"
				+ " where date_created > '2009-01-30 00:00:01' and date_last_modified < '2015-01-30 00:00:01' group by review_id order by artifact_type_id;");
		ResultSet rs = statement.executeQuery();
		return rs;
	}
	
	public String artifactTypeReview(Timestamp start, Timestamp End) throws SQLException{
		String content = "[ ";
		statement = DBConn.prepareStatement("select * from artifact_type order by artifact_type_id;");
		ResultSet artifactTypeResult = statement.executeQuery();
		ResultSet artifactReviewResult = artifactReview(start, End);
		while(artifactTypeResult.next()){
			content+= "{ \"key\": \""+artifactTypeResult.getString(2)+"\", \n \"values\":[";
			while(artifactReviewResult.next()){
			}
		}
		return content;
	}
		
	@SuppressWarnings({ "unchecked", "resource" })
	public String LineBarChartJson(int leftOpt, int rightOpt) throws SQLException{
		String content = null;
		JSONArray mainList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();
		ResultSet artifactTypeResult =  null;
		ResultSetMetaData rsmd = null;
		JSONArray subList = new JSONArray();
		JSONArray subList2 = new JSONArray();
		switch(leftOpt){
		case 1:
			statement = DBConn.prepareStatement("select unix_timestamp(min(date_created))*1000 start, count(review_id) No_of_Reviews_per_story from review group by story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();			
			obj.put("key", rsmd.getColumnName(2));
			obj.put("bar", "true");			
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList.add(newlist);
			}
			obj.put("values", subList);
			mainList.add(obj);
			break;
		case 2:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start, count(comments.comments_id) 'No. Of Comments per User Story' from review inner join comments on comments.review_id = review.review_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();			
			obj.put("key", rsmd.getColumnName(2));
			obj.put("bar", "true");
			subList = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList.add(newlist);
			}
			obj.put("values", subList);
			mainList.add(obj);
			break;
		case 3:
			statement = DBConn.prepareStatement("select unix_timestamp(min(date_created))*1000 start, datediff(max(end_date), min(date_created)) duration_in_days from review group by story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();
			obj.put("key", rsmd.getColumnName(2));
			obj.put("bar", "true");
			subList = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList2.add(newlist);
			}
			obj.put("values", subList2);
			mainList.add(obj);
			break;
		case 4:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start, "
					+ "count(distinct artifact_version.version_no) 'No. Of Artifact Versions per User Story'"
					+ " from review inner join artifact on artifact.review_id = review.review_id"
					+ " inner join artifact_version on artifact_version.artifact_id = artifact.artifact_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();			
			obj.put("key", rsmd.getColumnName(2));
			obj.put("bar", "true");
			subList = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList.add(newlist);
			}
			obj.put("values", subList);
			mainList.add(obj);
			break;
		case 5:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start, "
					+ " count(artifact.artifact_id) 'No. Of Artifacts per User Story' from review "
					+ " inner join artifact on artifact.review_id = review.review_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();			
			obj.put("key", rsmd.getColumnName(2));
			obj.put("bar", "true");
			subList = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList.add(newlist);
			}
			obj.put("values", subList);
			mainList.add(obj);
			break;
		case 6:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start,"
					+ " count(distinct artifact_type.artifact_type_id) 'No. Of Artifact Types per User Story'"
					+ " from review inner join artifact on artifact.review_id = review.review_id"
					+ " inner join artifact_type on artifact_type.artifact_type_id = artifact.artifact_type_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();			
			obj.put("key", rsmd.getColumnName(2));
			obj.put("bar", "true");
			subList = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList.add(newlist);
			}
			obj.put("values", subList);
			mainList.add(obj);
			break;
		}
		switch(rightOpt){
		case 1:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start, count(comments.comments_id) 'No. Of Comments per User Story' from review inner join comments on comments.review_id = review.review_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();
			obj1.put("key", rsmd.getColumnName(2));
			subList2 = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList2.add(newlist);
			}
			obj1.put("values", subList2);
			mainList.add(obj1);			
			break;
		case 2:
			statement = DBConn.prepareStatement("select unix_timestamp(min(date_created))*1000 start, count(review_id) No_of_Reviews_per_story from review group by story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();
			obj1.put("key", rsmd.getColumnName(2));
			subList2 = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList2.add(newlist);
			}
			obj1.put("values", subList2);
			mainList.add(obj1);
			break;
		case 3:
			statement = DBConn.prepareStatement("select unix_timestamp(min(date_created))*1000 start, datediff(max(end_date), min(date_created)) duration_in_days from review group by story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();
			obj1.put("key", rsmd.getColumnName(2));
			subList2 = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList2.add(newlist);
			}
			obj1.put("values", subList2);
			mainList.add(obj1);
			break;
		case 4:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start,"
					+ " count(distinct artifact_version.version_no) 'No. Of Artifact Versions per User Story'"
					+ " from review inner join artifact on artifact.review_id = review.review_id"
					+ " inner join artifact_version on artifact_version.artifact_id = artifact.artifact_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();
			obj1.put("key", rsmd.getColumnName(2));
			subList2 = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList2.add(newlist);
			}
			obj1.put("values", subList2);
			mainList.add(obj1);
			break;
		case 5:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start, "
					+ " count(artifact.artifact_id) 'No. Of Artifacts per User Story' from review "
					+ " inner join artifact on artifact.review_id = review.review_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();
			obj1.put("key", rsmd.getColumnName(2));
			subList2 = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList2.add(newlist);
			}
			obj1.put("values", subList2);
			mainList.add(obj1);
			break;
		case 6:
			statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start,"
					+ " count(distinct artifact_type.artifact_type_id) 'No. Of Artifact Types per User Story'"
					+ " from review inner join artifact on artifact.review_id = review.review_id"
					+ " inner join artifact_type on artifact_type.artifact_type_id = artifact.artifact_type_id group by review.story_name;");
			artifactTypeResult = statement.executeQuery();
			rsmd = (ResultSetMetaData) artifactTypeResult.getMetaData();
			obj1.put("key", rsmd.getColumnName(2));
			subList2 = new JSONArray();
			while(artifactTypeResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactTypeResult.getLong(1));
				newlist.add(artifactTypeResult.getInt(2));
				subList2.add(newlist);
			}
			obj1.put("values", subList2);
			mainList.add(obj1);
			break;
		}
		content = mainList.toJSONString();
		return content;
	}
	
	public String stackedAreaChartJson(int yValue) throws SQLException{
		String content =  null;
		statement = DBConn.prepareStatement("select * from artifact_type order by artifact_type_id;");
		ResultSet artifactTypeResult = statement.executeQuery();
		JSONArray mainList = new JSONArray();
		while(artifactTypeResult.next()){
			JSONObject obj = new JSONObject();
			switch(yValue){
			case 1:
				statement = DBConn.prepareStatement("select unix_timestamp(min(artifact.date_created))*1000 start,"
						+ " datediff(max(artifact.date_last_modified),"
						+ " min(artifact.date_created)) difference from review inner join artifact on artifact.review_id = review.review_id"
						+ " where artifact.artifact_type_id = ? group by review.story_name;");
				break;
			case 2:
				statement =  DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start,"
						+ " count(distinct comments.comments_id) 'No. Of Comments per User Story' from artifact"
						+ " inner join comments on comments.artifact_id = artifact.artifact_id"
						+ " inner join review on review.review_id = artifact.review_id where artifact.artifact_type_id = ?"
						+ " group by review.story_name;");
				break;
			case 3:
				statement = DBConn.prepareStatement("select unix_timestamp(min(review.date_created))*1000 start,"
						+ " count(distinct comments.comments_id) 'No. Of Versions per User Story' from artifact"
						+ " inner join comments on comments.artifact_id = artifact.artifact_id"
						+ " inner join review on review.review_id = artifact.review_id  where artifact.artifact_type_id = ?"
						+ "  group by review.story_name;");
				break;
			}
			statement.setInt(1, artifactTypeResult.getInt(1));
			ResultSet artifactResult = statement.executeQuery();
			JSONArray subArray = new JSONArray();
			while(artifactResult.next()){
				JSONArray newlist = new JSONArray();
				newlist.add(artifactResult.getLong(1));
				newlist.add(artifactResult.getInt(2));
				subArray.add(newlist);
			}
			obj.put("values", subArray);
			obj.put("key", artifactTypeResult.getString(2));
			mainList.add(obj);
		}
		content = mainList.toJSONString();
		return content;
	}
	
	public static void main(String[] args) throws IOException{
		 ChartDataBuilder c =  new ChartDataBuilder("localhost", "artiflow");
		 try {
			System.out.println(c.stackedAreaChartJson(3));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
