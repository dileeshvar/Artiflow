package com.ssn.artiflow.functions;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringEscapeUtils;

import com.ssn.artiflow.utils.ConnectDB;
import com.ssn.artiflow.utils.Data;
import com.ssn.artiflow.utils.ObjectFactory;

public class TimeLineXMLBuilder {
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<String> comments = new ArrayList<String>();
	private String artifactName = null;
	
	public ArrayList<String> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public String buildTimelineXML(File file, String URL){
		String xmlPath = "";
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(Data.class);
			javax.xml.bind.Marshaller marshaller = jc.createMarshaller();
			Data data = codePerspectiveData(URL);
			marshaller.marshal(data, file);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return xmlPath;
	}
	
	private Data codePerspectiveData(String URL){
		java.sql.Connection dbcon = null;
		dbcon = ConnectDB.getConnection("localhost", "artiflow");
		java.sql.Statement s = null;
		Data data = null;
		try {
			s = dbcon.createStatement();
//			String query = "select review.review_id, review.story_name, review.objective, review.date_created, "
//					+ "review.end_date, artifact.artifact_id, artifact.artifact_name from review left outer "
//					+ "join artifact on artifact.review_id = review.review_id where review.end_date is not null";
			String query1 = "select review.review_id, review.story_name, review.objective, review.date_created, "
					+ "(select max(r.end_date) from review r where r.story_name = review.story_name) as end_date, "
					+ "artifact.artifact_id , artifact.artifact_name from review"
					+ " left outer join artifact on artifact.review_id = review.review_id "
					+ "where review.end_date is not null;";
			ResultSet resultSet = s.executeQuery(query1);
			data = buildData(resultSet, URL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	private Data buildData(ResultSet resultset, String URL){
		ObjectFactory objectFactory = new ObjectFactory();
		Data data = objectFactory.createData();
		Data.Event event = null;
		String storyName = "";
		String value = "";
		String objective = "";
		int index = -1;
		int count = 1;
		try {
			while(resultset.next()){
				if(!resultset.getString("story_name").equals(storyName)){
					count = 1;
					index++;
					objective ="";
					value = "";
					event = objectFactory.createDataEvent();
					storyName = resultset.getString("story_name");
					event.setTitle(storyName);
					event.setIsDuration("true");
					event.setStart(dateConvertor(resultset.getTimestamp("date_created")));
					event.setEnd(dateConvertor(resultset.getTimestamp("end_date")));
					data.getEvent().add(event);
				}
				if(resultset.getString("story_name").equals(storyName)){
					if(objective.equals("") || !objective.equals(resultset.getString("objective"))){
						objective = resultset.getString("objective");
						value+=encodeHTML("<strong>Review "+count+++" Objective :</strong> "+objective+"<br/>");
						value+=encodeHTML("<br/><strong>File Names: </strong><br/>");
					}
//					value+=encodeHTML("<strong><a onclick=\"window.open(\'http://localhost:8080/Test_Artiflow/TimeLineCode?r_id="
//							+resultset.getInt("review_id")+"&a_id="
//							+resultset.getInt("artifact_id")
//							+"', '_blank', 'location=yes,height=570,width=520,scrollbars=yes,status=yes');\">"+resultset.getString("artifact_name")+"</a></stron><br/>");
					value+=encodeHTML("<a target = \"_blank\" href= \""+URL+"?r_id="
					+resultset.getInt("review_id")+"&a_id="
					+resultset.getInt("artifact_id")+"\">"+new File(resultset.getString("artifact_name")).getName()+"</a><br/>\n");
					event.setValue(value);
					data.getEvent().remove(index);
					data.getEvent().add(index, event);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	private String encodeHTML(String s)
	{
		s = StringEscapeUtils.escapeHtml3(s);
	    return s;
	}
	
	private String dateConvertor(Date dateValue){
		DateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));	
		return  dateFormat.format(dateValue)+" GMT";
	}
	
	public void artifactComments(String reviewID, String artifactID){
		java.sql.Connection dbcon = null;
		dbcon = ConnectDB.getConnection("localhost", "artiflow");
		java.sql.PreparedStatement s = null;
		try {
			String query = "select user.user_name , comments.comments from comments "
					+ " inner join user on user.user_id = comments.user_id"
					+ " where review_id = ? and artifact_id = ?";
			s = dbcon.prepareStatement(query);
			s.setString(1, reviewID);
			s.setString(2, artifactID);
			ResultSet rs = s.executeQuery();
			while(rs.next()){
				users.add(rs.getString("user_name"));
				comments.add(rs.getString("comments"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String artifactName(String artifactID){
		java.sql.Connection dbcon = null;
		dbcon = ConnectDB.getConnection("localhost", "artiflow");
		java.sql.PreparedStatement s = null;
		try {
			String query = "select artifact.artifact_name from artifact"
					+ " where artifact.artifact_id = ?";
			s = dbcon.prepareStatement(query);
			s.setString(1, artifactID);
			ResultSet rs = s.executeQuery();
			while(rs.next()){
				artifactName = rs.getString("artifact_name");
				artifactName = new File(artifactName).getName();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artifactName;
	}

}
