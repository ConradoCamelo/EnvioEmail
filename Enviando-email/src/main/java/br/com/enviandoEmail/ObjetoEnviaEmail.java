package br.com.enviandoEmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String username = "javaprojetoemail@gmail.com"; 
	private String senha = "pgkpknjscyvudgnb";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
	
	public void enviarEmail(boolean envioHtml) throws Exception{
		
		//Properties properties = new Properties();
	    
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");/* Autorização */
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.quitwait", "false");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.host", "smtp.gmail.com");

		props.setProperty("mail.transport.protocol", "smtp");

		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.ssl.trust", "*");
		props.setProperty("mail.smtp.starttls.enable", String.valueOf("True"));// True or False
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.setProperty("mail.smtp.timeout", "300000");
		props.setProperty("mail.smtp.connectiontimeout", "300000");
		props.setProperty("mail.smtp.writetimeout", "300000");
		
		//properties.setProperty("mail.smtp.ssl.trust", "*");
		//properties.put("mail.smtp.auth", "true");//autorização
		//properties.put("mail.smtp.startls", "true");//autenticação
		//properties.put("mail.smtp.host", "smtp.gmail.com");//servidor gmail Google
		//properties.put("mail.smtp.port", "465");//porta do servidor
		//properties.put("mail.smtp.socketFactory,port", "465");//especifica a porta a ser comunicada pelo socket
		//properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//Classe de conexão ap SMTP
		
		Session session = Session.getInstance(props, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(username, senha);
			}
			
		});
		
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, nomeRemetente )); // Quem estar enviando
		message.setRecipients(Message.RecipientType.TO , toUser); //Email de destino
		message.setSubject(assuntoEmail);//Assunto do email
		
		if (envioHtml) {
			message.setContent(textoEmail, "text/html;charset = utf-8");
		}else {
		message.setText(textoEmail);
		}
		
		Transport.send(message);
	}
	
	public void enviarEmailAnexo(boolean envioHtml) throws Exception{
		
		//Properties properties = new Properties();
	    
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");/* Autorização */
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.quitwait", "false");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.host", "smtp.gmail.com");

		props.setProperty("mail.transport.protocol", "smtp");

		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.ssl.trust", "*");
		props.setProperty("mail.smtp.starttls.enable", String.valueOf("True"));// True or False
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.setProperty("mail.smtp.timeout", "300000");
		props.setProperty("mail.smtp.connectiontimeout", "300000");
		props.setProperty("mail.smtp.writetimeout", "300000");
		
		//properties.setProperty("mail.smtp.ssl.trust", "*");
		//properties.put("mail.smtp.auth", "true");//autorização
		//properties.put("mail.smtp.startls", "true");//autenticação
		//properties.put("mail.smtp.host", "smtp.gmail.com");//servidor gmail Google
		//properties.put("mail.smtp.port", "465");//porta do servidor
		//properties.put("mail.smtp.socketFactory,port", "465");//especifica a porta a ser comunicada pelo socket
		//properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//Classe de conexão ap SMTP
		
		Session session = Session.getInstance(props, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(username, senha);
			}
			
		});
		
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, nomeRemetente )); // Quem estar enviando
		message.setRecipients(Message.RecipientType.TO , toUser); //Email de destino
		message.setSubject(assuntoEmail);//Assunto do email
		
		//Parte 01 do Email que é texto e a descrição do Email 
		
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		
		if (envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html;charset = utf-8");
		}else {
		corpoEmail.setText(textoEmail);
		}
		
		//Parte 02 do email que são os anexos 
		MimeBodyPart anexoEmail = new MimeBodyPart();
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladroDePdf(), "application/pdf")));
		anexoEmail.setFileName("anexoemail.pdf");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);
		multipart.addBodyPart(anexoEmail);
		
		message.setContent(multipart);
		
		
		Transport.send(message);
	}
	
	//Esse método simula o PDF ou qualquer arquivo que possa ser enviado por anexo no e-mail. 
	private FileInputStream  simuladroDePdf() throws Exception{
	
		Document document = new Document();
		File file = new File("fileane+++++++++++++++xo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do anexo PDF Javamail"));//Esse Texto fica dentro do E-mail
		document.close();
		
		return new FileInputStream(file);	
		
	}
	
	
}
