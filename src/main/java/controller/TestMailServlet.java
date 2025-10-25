package controller;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/testmail")
public class TestMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		final String fromEmail = "thanhnever2222@gmail.com";
		final String password = "app-password"; // Mật khẩu ứng dụng Gmail
		final String toEmail = "receiver@gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, "Bookstore"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Test gửi email thành công trên Tomcat 9 / JDK 21!");
			message.setText("Xin chào!\n\nGửi mail từ servlet thành công.");

			Transport.send(message);
			resp.getWriter().println("✅ Email đã gửi thành công đến: " + toEmail);

		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().println("❌ Lỗi gửi mail: " + e.getMessage());
		}
	}
}
