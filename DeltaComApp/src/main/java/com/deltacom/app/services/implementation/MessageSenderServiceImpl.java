package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.MessageSenderService;
import com.deltacom.app.services.api.OptionService;
import com.deltacom.app.services.api.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Operations with sending messages to message queue
 */
@Aspect
@Service("MessageSenderService")
public class MessageSenderServiceImpl implements MessageSenderService {
    private static final Logger logger = LogManager.getLogger(MessageSenderService.class);
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private OptionService optionService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Pointcut for add, update, delete methods of TariffService
     */
    @Pointcut("execution(void com.deltacom.app.services.api.TariffService.*(..))")
    public void tariffChanged(){ };

    /**
     * Method for catching successful return from add, update, delete methods of TariffService
     */
    @AfterReturning("tariffChanged()")
    public void afterTariffChanged() {
        try {
            messagingTemplate.convertAndSend("/topic/tariffs", tariffService.getAllTariffs());
            jmsTemplate.convertAndSend("Tariffs changed.");
            logger.info("Sent message about tariff changes.");
        } catch(Exception e) {
            return;
        }
    }

    /**
     * Pointcut for add, update, delete methods of OptionService
     */
    @Pointcut("execution(void com.deltacom.app.services.api.OptionService.*(..))")
    public void optionChanged(){ };

    /**
     * Method for catching successful return from add, update, delete methods of OptionService
     */
    @AfterReturning("optionChanged()")
    public void afterOptionChanged() {
        try {
            messagingTemplate.convertAndSend("/topic/options", optionService.getAllOptions());
        } catch(Exception e) {
            return;
        }
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
            return;
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
        sendEmail(email, "Security alert", emailBody);
    }

}

