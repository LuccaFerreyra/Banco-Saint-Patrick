package com.banco.Saint_Patrik.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * MÉTODO PARA PODER ENVIAR UN MAIL
     *
     * METHOD TO BE ABLE TO SEND AN EMAIL
     *
     * @param from
     * @param to
     * @param subject
     * @param body
     */
    public void sendMail(String from, String to, String subject, String body) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }
}
