package com.ccet.backend.api.v1.services.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender emailSender;
    private TaskExecutor taskExecutor;

    @Autowired
    public MailService(JavaMailSender emailSender, @Qualifier("mail") TaskExecutor taskExecutor) {
        this.emailSender = emailSender;
        this.taskExecutor = taskExecutor;
    }

    private void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        taskExecutor.execute(() -> emailSender.send(message));
    }


    public void sendOtp(String mailTo, String otp) {
        sendSimpleMessage(mailTo, "One Time Password", "Your OTP is " + otp);
    }

}
