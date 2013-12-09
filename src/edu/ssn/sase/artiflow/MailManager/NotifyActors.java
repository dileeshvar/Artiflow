package edu.ssn.sase.artiflow.MailManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class NotifyActors extends Thread
{
	private Map<String,String> serverDetails;
	private List<String> mailIds;
	String projectName;
	String d_email = "",
	d_password = "",
	d_host = "",
	d_port = "",
	m_subject = "Notification for review",
	m_text = "Welcome to Artiflow. Check our product evolution feature also to get the maximum out of our tool. ",
	m_text1= "\n A review has been initiated for the project, '",
	m_text2= "You are a part of it. ",
	m_footer="\n\n\n Tune into Artfilow to have a look at the entire review and be done with it";
	List<String> m_to = new ArrayList<String>();

	public NotifyActors(Map<String,String> serverDetails, List<String> mailIds, String projectName) 
	{
		this.serverDetails = serverDetails;
		this.mailIds = mailIds;
		this.projectName = projectName;
	}
	
	private class SMTPAuthenticator extends javax.mail.Authenticator 
	{
		public PasswordAuthentication getPasswordAuthentication() 
		{
			return new PasswordAuthentication(d_email, d_password);
		}
	}

	public void run() {
		d_email = serverDetails.get("FromEmail");
		d_password = serverDetails.get("FromPassword");
		d_host = serverDetails.get("MailServerHost");
		d_port = serverDetails.get("MailServerPort");
		String proxyNeeded = serverDetails.get("ProxyNeeded");

		Properties props = new Properties();
		if(proxyNeeded != null && Boolean.valueOf(proxyNeeded)) {
			String proxyHost = serverDetails.get("ProxyHostURL");
			String proxyPort = serverDetails.get("ProxyPort");
			props.setProperty("proxySet",String.valueOf(proxyNeeded));
			props.setProperty("mail.smtp.socks.host",proxyHost);
			props.setProperty("mail.smtp.socks.port",proxyPort);
			props.setProperty("socksProxyHost",proxyHost);
			props.setProperty("socksProxyPort",proxyPort);
		} 
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		try
		{
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			MimeBodyPart mb = new MimeBodyPart();
			mb.setText(m_text+m_text1+" "+ projectName + "' and " + m_text2 +" "+m_footer);
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mb);
			MimeMessage msg = new MimeMessage(session);
			msg.setContent(mp);
			msg.setSubject(m_subject);
			m_to = mailIds;
			msg.setFrom(new InternetAddress(d_email));
			for(String st : m_to)
			{				
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(st));
				Transport.send(msg);
				System.out.println("Mail send");
			}
		}
		catch (Exception mex) 
		{
			mex.printStackTrace();
		}
	}
	
/*	public static void main(String[] args)
	{
		NotifyActors sendmail=new NotifyActors();
	}*/	
}