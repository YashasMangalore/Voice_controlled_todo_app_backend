package com.todo.voice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailServiceImpl {
    JavaMailSender javaMailSender;

    @Value("")
    String fromEmail;

    public void sendTaskNotification(String to, String subject ,String body){
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setFrom(fromEmail);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }
}
