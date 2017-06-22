package com.jenkins.database;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;

public class MailingService {
	private String from;
	private String host;
	private String userName;
	private String password;
	private String to;
	private String port;
	private String messageBodyContent;
	private String subject;
	private String[] screenShotPath;
	static Logger log = Logger.getLogger(MailingService.class.getName());

	public MailingService(String from, String host, String userName, String password, String to, String port,
			String messageBodyContent, String subject, String[] screenShotPath) {
		super();
		this.from = from;
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.to = to;
		this.port = port;
		this.messageBodyContent = messageBodyContent;
		this.subject = subject;
		this.screenShotPath = screenShotPath;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMessageBodyContent() {
		return messageBodyContent;
	}

	public void setMessageBodyContent(String messageBodyContent) {
		this.messageBodyContent = messageBodyContent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getScreenShotPath() {
		return screenShotPath;
	}

	public void setScreenShotPath(String[] screenShotPath) {
		this.screenShotPath = screenShotPath;
	}

	public MailingService() {
		this.setFrom("no.reply@bdt.net");
		this.setHost("inmail.zycus.net");
		this.setPort("25");
		this.setUserName("rdp");
		this.setPassword("pass@123");
	}

	public void sendEmailNotification(File file, String receipient) {
		try {
			ArrayList<String> receipientList = new ArrayList<>();
			String[] receipientArray = receipient.split(",");
			int receipientListSize = receipientArray.length;
			for (int i = 0; i < receipientListSize; i++)
				receipientList.add(receipientArray[i]);
			String from = this.from;
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "false");
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(prop);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			InternetAddress[] addressTo = new InternetAddress[receipientList.size()];

			for (int i = 0; i < receipientList.size(); i++)
				addressTo[i] = new InternetAddress(receipientList.get(i));

			message.setRecipients(MimeMessage.RecipientType.TO, addressTo);
			message.setRecipients(MimeMessage.RecipientType.CC, "SMTRM@zycus.com");
			message.setSubject(this.subject);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(this.messageBodyContent, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String filename = file.getAbsolutePath();
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(file.getName());
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			log.debug("sendEmailNotification :" + e);
			System.out.println("sendEmailNotification : Somthing is wrong");
		}
	}
}
