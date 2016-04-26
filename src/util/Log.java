/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Classe responsavel pelo Log's.
 *
 * @author jeanderson
 */
public class Log {

	/**
	 * Gera um arquivo log dentro da Pasta Logger que fica na Pasta principal do
	 * Usuario.
	 *
	 * @param className
	 * @param ex
	 */
	public static void relatarExcecao(String className, Exception ex) {
		try {
			/*
			 * Informamos qual o nome do Logger, que no caso vai ser o nome da
			 * Classe que acontecerá a exceção
			 */
			Logger log = Logger.getLogger(className);
			/*
			 * Variavel que vai conter qual a pasta do sistema que liga ao
			 * usuario, por padrão será do sistema operacional Windows
			 */
			String systemPath = "/Users/";
			/* Se for outro sistema operacional */
			if (System.getProperty("os.name").startsWith("Linux")) {
				systemPath = "/home/";
			}
			/* Pasta onde vamos colocar os Logs */
			File pastaLog = new File(systemPath + System.getProperty("user.name") + "/Logger");
			if (!pastaLog.exists()) {
				pastaLog.mkdir();
			}
			String arquivoDir = pastaLog.getAbsolutePath() + "/LOG_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM_HH-mm-ss")) +".log";
			/* Classe responsavel por escrever o arquivo */
			FileHandler escrever = new FileHandler(arquivoDir, true);
			/*
			 * Precisamos informar como será escrito(formato) as exceções, Vamos
			 * Utilizar uma Classe já pronta para isso a Classe SimpleFormatter
			 */
			escrever.setFormatter(new SimpleFormatter());
			/*
			 * Adicionamos ao nosso log a Classe que vai escrever a exceção que
			 * for gerada
			 */
			log.addHandler(escrever);

			/*
			 * Geramos o Log, passamos que será de Nivel Severe(Alto), e
			 * passamos a exceção para ele
			 */
			log.log(Level.SEVERE, null, ex);

			/* Finalizamos a escrita */
			escrever.flush();
			escrever.close();
			/*Envia por email a exceção*/
			Log.relatarExceptionEmail(className, ex.getMessage(),arquivoDir);

		} catch (IOException | SecurityException e) {
			Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public static void relatarExceptionEmail(String className, String exception, String logPath) {
		/*
		 * Para compreender melhor acesse esse site:
		 * http://www.botecodigital.info/java/enviando-e-mail-em-java-com-api-
		 * commons-email-da-apache/
		 */
		HtmlEmail email = new HtmlEmail();
		email.setSSLOnConnect(true);
		email.setHostName("smtp.gmail.com");
		email.setSslSmtpPort("465");
		email.setAuthenticator(new DefaultAuthenticator("jjsoftwares10@gmail.com", "jean1420"));
		try {
			email.setFrom("jjsoftwares10@gmail.com", "Software da clinica");
			email.setSubject("Exceção ocorrida no app da clinica");
			StringBuilder msg = new StringBuilder();
			msg.append("<h1 style=\"text-align: center;\">Excecao Ocorrida</h1>");
			msg.append("<p><strong>Na Classe: " + className + " </strong></p>");
			msg.append("<p><strong>Data e Horario do ocorrido: "
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:s")) + "</strong></p>");
			msg.append("<h2 style=\"text-align: center;\"><strong>Excecao</strong></h2>");
			msg.append("<p><span style=\"color: #ff0000;\">" + exception + "</span></p>");
			msg.append("<p><strong>Segue anexo com detalhes</strong></p>");
			/*Enviando o anexo com detalhes da exceção*/
			File arqLog = new File(logPath);
			if(arqLog.exists()){
				EmailAttachment anexo = new EmailAttachment();
				anexo.setPath(logPath);
				anexo.setDisposition(EmailAttachment.ATTACHMENT);
				anexo.setName(arqLog.getName());
				email.attach(anexo);
			}
			/*enviando*/
			email.setHtmlMsg(msg.toString());
			email.addTo("jeandersonfju@gmail.com");
			email.addTo("jeff-assis@hotmail.com");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();

		}
	}

}
