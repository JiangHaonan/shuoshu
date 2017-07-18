package ss.common.message.email;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 邮件发送 注册改为邮箱注册，未实现
 * @author mutou
 * @date 2017年7月17日
 */
public class MailContent {
	public static void main(String[] args) throws MessagingException, GeneralSecurityException {
		//总算放成功了，QQ邮箱要用授权码登陆而不是密码 我也是醉了。。。
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.host", "smtp.qq.com");
		prop.put("mail.smtp.port", "465");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();  
		sf.setTrustAllHosts(true); 
		prop.put("mail.smtp.starttls.enable","true");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);
		prop.put("mail.imap.ssl.socketFactory.fallback", "false");
		prop.put("mail.smtp.socketFactory.port", "465");
		
		
		Session sessions = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("357953710@qq.com","***");
			}
		});
		sessions.setDebug(true);
		Message message = new MimeMessage(sessions);
		message.setFrom(new InternetAddress("357953710@qq.com"));
		message.setSubject("no title");
		message.setContent("test", "text/html");
		message.addRecipient(RecipientType.TO, new InternetAddress("357953710@qq.com"));
		Transport.send(message);
		
		/*//新浪可以成功
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.host", "smtp.sina.com");
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		//prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//prop.setProperty("mail.smtp.port", "465");
		//prop.setProperty("mail.smtp.socketFactory.port", "465");
		
		
		Session sessions = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("userName","password");
			}
		});
		sessions.setDebug(true);
		Message message = new MimeMessage(sessions);
		message.setFrom(new InternetAddress("******"));
		message.setSubject("no title");
		message.setContent("my telephone number *****", "text/html");
		message.addRecipient(RecipientType.TO, new InternetAddress("*******"));
		Transport.send(message);*/
	}
}
