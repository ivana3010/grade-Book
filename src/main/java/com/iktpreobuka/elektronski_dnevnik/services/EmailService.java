package com.iktpreobuka.elektronski_dnevnik.services;

public interface EmailService {
	void sendTemplateMessage (String to, String subject, String text);
}
