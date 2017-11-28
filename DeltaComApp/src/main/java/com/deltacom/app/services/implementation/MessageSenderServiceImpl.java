package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.exceptions.MessageSenderException;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.MessageSenderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

/**
 * Operations with sending messages to message queue
 */
@Service("MessageSenderService")
public class MessageSenderServiceImpl implements MessageSenderService {
    private static final Logger logger = LogManager.getLogger(MessageSenderService.class);
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private ClientService clientService;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends object to topic
     * @param topicName name of topic
     * @param object object to send
     */
    @Override
    public void sendToTopic(String topicName, Object object) {
        messagingTemplate.convertAndSend(topicName, object);
    }

    /**
     * Sends message to queue
     * @param message message to send
     */
    @Override
    public void sendToQueue(String message) {
        jmsTemplate.convertAndSend("Tariffs changed.");
    }

    /**
     * Send email to client with generated token
     * @param email client email
     */
    @Override
    public void sendResetPasswordEmail(String email) {
        String token = UUID.randomUUID().toString();
        String emailBody = "Dear client!\n\nFollow the link to reset your password: https://deltacomapp.com/forgotPassword?token="+token;
        sendEmail(email, "Password reset", emailBody);
        clientService.updateForgottenPassToken(token, email);
    }

    /**
     * Sends email to client
     * @param email client email
     * @param text text of message
     * @param subject
     */
    @Override
    public void sendEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("no-reply@deltacomapp.com");
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new MessageSenderException("Can't send email: ", e);
        }
    }

    /**
     * Sends security alert email
     * @param email client email
     * @param location client location
     */
    @Override
    public void sendSecurityAlertEmail(String email, ClientLocation location) {
        String emailBody = "Dear client!\n\nWe noticed that someone entered to your account from city: " + location.getCity() +
                ", country: " + location.getCountry() + ", with ip: " + location.getIpAddress() +
                ".\n\nIf it wasn't you, please change your password as fast as you can!";
        try {
            sendEmail(email, "Security alert", emailBody);
        } catch (Exception e) {
            throw new MessageSenderException("Can't send security alert email: ", e);
        }
    }

    /**
     * Sends sms
     * @param number number for sending sms
     * @param smsText sms text
     * @return response from sms server or null if sms successfully sent
     */
    @Override
    public String sendSms(String number, String smsText) {
        RestTemplate restTemplate = new RestTemplate();
        String sendSmsURL = "http://smsc.ru/sys/send.php?login=Ko0LeR&psw=13572468qQ&phones=" + number + "&mes=" + smsText + "&fmt=3";
        ResponseEntity<String> response = restTemplate.getForEntity(sendSmsURL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
        } catch (IOException e) {
            logger.error("Can't read response from smsc.");
            return "Error.";
        }
        if(root.has("error")) {
            String error = root.path("error").asText();
            logger.error(error);
            return "Error.";
        }
        return null;
    }

}

