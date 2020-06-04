package com.codegym.controller.admin.rest;

import com.codegym.model.EmailCfg;
import com.codegym.model.FeedBack;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/feedback")
public class FeedBackRestController {
    private EmailCfg emailCfg;
    public FeedBackRestController(EmailCfg emailCfg) {
        this.emailCfg = emailCfg;
    }
    @PostMapping
    public void sendFeedback(@RequestBody FeedBack feedBack, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException("Feedback is not valid");
        }
        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());
        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedBack.getEmail());
        mailMessage.setTo("caoquang811994@gmail.com");
        mailMessage.setSubject("New message from " + feedBack.getName());
        mailMessage.setText(feedBack.getTitle());
        mailMessage.setText(feedBack.getDescription());
        // Send mail
        mailSender.send(mailMessage);
    }
}
