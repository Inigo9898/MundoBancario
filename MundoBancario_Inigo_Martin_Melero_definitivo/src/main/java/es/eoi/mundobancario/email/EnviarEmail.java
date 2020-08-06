package es.eoi.mundobancario.email;

import com.itextpdf.layout.Document;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EnviarEmail {

	public static void main(Document documento, String tipo) {
		final String username = "pruebacorreomayo2020@gmail.com";
		final String password = "BecaOnline2020";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("pruebacorreomayo2020@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("pruebacorreomayo2020@gmail.com, pruebacorreomayo2020@gmail.com"));
			message.setSubject("Mundo Bancario te va a arruinar");
			String filename;
			if (tipo.equals("CLIENTE")) {
				filename = "EOI_BANK_CLIENTE_000.pdf";
			} else {
				filename = "EOI_BANK_PRESTAMO_000.pdf";
			}

			Multipart multipart = new MimeMultipart();
			MimeBodyPart att = new MimeBodyPart();
			DataSource source = new FileDataSource("C:\\Users\\inigo\\Desktop\\".concat(filename));
			att.setDataHandler(new DataHandler(source));
			att.setFileName(filename);
			multipart.addBodyPart(att);
			message.setContent(multipart);
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
