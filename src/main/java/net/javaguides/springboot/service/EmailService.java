package net.javaguides.springboot.service;

public interface EmailService {

	void sendEmail(String email, String subject, String message);

}