package com.iktpreobuka.elektronski_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImplementation implements EmailService{
	@Autowired
    private JavaMailSender emailSender;
	
	@Override
	public void sendTemplateMessage(String to, String subject, String text){
		MimeMessage message = emailSender.createMimeMessage();//klasa za slanje mejlova
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true); //pomocna klasa da bih postavila atribute to,subject,text; message-mime, true-html
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            emailSender.send(message);//za slanje mime
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}

}
