package smt.deliverable.com;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;

public class SendMail {

	static Logger log = Logger.getLogger(SendMail.class.getName());

	static final String PDTRM = "SMTRM@zycus.com";
	static final String FROM = "no.reply@zycus.com";
	
	public SendMail() {
		super();
	}

	/**
	 * 
	 * @author rishu.goenka
	 * @param sendTO
	 * @param sendFrom
	 * @param filePath
	 * @return
	 */
	public static boolean mailReport(String sendTO, String filePath, String sourcePath) {

		// Recipient's email ID needs to be mentioned.
		String to = sendTO;

		// Sender's email ID needs to be mentioned
		String from = FROM;

		// SMTP Local Host ADDRESS
		String host = "inmail.zycus.net";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.addRecipient(RecipientType.CC, new InternetAddress(PDTRM));

			if ("NotValid".equalsIgnoreCase(filePath)) {
				// Set Subject: header field
				message.setSubject("Deliverable Copy Failed");

				// message body if no other thing to attach
				message.setContent("<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\"><style>body {font-family: Segoe UI;font-size: 16px;background: #F2F2F2 none repeat scroll 0 0;	font-weight: lighter;}h1 {color: red;font-family: consolas;font-size: 22px;}table {width: 100%;border: 1px solid #e6e6e6;border-collapse: collapse;background: #ffffff none repeat scroll 0 0;}td {border: 1px solid #e6e6e6;text-align: left;padding-bottom: 10px;padding-top: 10px;padding-left: 10px;}h3 {background: #ffffff none repeat scroll 0 0;border: 1px solid #776565;color: #444;font-size: 24px;font-style: normal;padding-left: 10px;font-weight: initial;}h4 {color: #0d1716;font-size: 16px;font-style: normal;font-weight: initial;}.zycusLogo {height: 33px;margin-bottom: -20px;margin-left: 0;margin-top: 0;width: 120px;}</style></head><body><div>Hi Team ,</div><div><h4>The provided Deliverable Path is : " 
						+ sourcePath 
						+ "</h4></div><div>The path does not follow the standard. Hence, [automation] unable to copy the Deliverable.</div><div>Kindly follow and provide the standard path for successful copy of the Deliverable.</div><div><h2>NOTE :</h2></div><div>The Standard source path format is as mentioned below.</div><table><tr><td>Release Path :</td><td>R:\\SMT_Release_For_RM_Review\\productName\\release_YY.MM.X.x</td></tr><tr><td>Patch Path :</td><td>R:\\SMT_Release_For_RM_Review\\productName\\release_YY.MM.X.x\\patches\\patch_NN</td></tr><tr><td>CSCR Path :</td><td>R:\\SMT_Release_For_RM_Review\\productName\\release_YY.MM.X.x\\customer_specific_change_requests\\CSCR_NN</td></tr><tr><td>Utility Path :</td><td>R:\\SMT_Release_For_RM_Review\\productName\\release_YY.MM.X.x\\utilities\\utility_title</td></tr></table><h3>Kindly refer this location for more clarification in document standardization : Y:\\Release_Management\\release_artifacts</h3><div><h5>Thanks and Regards,<br>PDT|Release Management<br>Zycus Infotech Pvt. Ltd.</h5></div></body></html>"
						, "text/html");
			} else {
				// Set Subject: header field
				message.setSubject("Deliverable Copy Log");

				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();

				// Now set the actual message
				messageBodyPart.setContent("<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\"><style>body {font-family: Segoe UI;font-size: 16px;background: #F2F2F2 none repeat scroll 0 0;	font-weight: lighter;}h1 {color: red;font-family: consolas;font-size: 22px;}h3 {background: #ffffff none repeat scroll 0 0;border: 1px solid #776565;color: #444;font-size: 24px;font-style: normal;padding-left: 10px;font-weight: initial;}h4 {color: #0d1716;font-size: 16px;font-style: normal;font-weight: initial;}.zycusLogo {height: 33px;margin-bottom: -20px;margin-left: 0;margin-top: 0;width: 120px;}</style></head><body><div>Hi Team ,</div><div><h4>The provided Deliverable Path is : " 
						+ sourcePath 
						+ "</h4></div><div>Successfully copied the Deliverable. [automation]</div><div>Kindly find attached log for verification.</div><div><h2>NOTE :</h2></div><h3>Kindly refer this location for more clarification in document standardization : Y:\\Release_Management\\release_artifacts</h3><div><h5>Thanks and Regards,<br>PDT|Release Management<br>Zycus Infotech Pvt. Ltd.</h5></div></body></html>"
						, "text/html");

				// Create a multipart message
				Multipart multipart = new MimeMultipart();

				// Part one is text message
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				String filename = filePath;
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				multipart.addBodyPart(messageBodyPart);

				// Send the complete message parts
				message.setContent(multipart);
			}
			// Send message
			Transport.send(message);
			return true;

		} catch (Exception e) {
			log.info("Error while sending mail", e);
			return false;
		}
	}
}