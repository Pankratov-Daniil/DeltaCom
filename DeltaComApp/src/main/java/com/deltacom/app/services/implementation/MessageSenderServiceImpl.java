package com.deltacom.app.services.implementation;

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
     * @param email
     */
    @Override
    public void sendResetPasswordEmail(String email) {
        String token = UUID.randomUUID().toString();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("no-reply@deltacomapp.com");
        message.setSubject("Password reset");
        message.setText("Dear client!\n\nFollow the link to reset your password: https://deltacomapp.com/forgotPassword?token="+token);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            return;
        }
        clientService.updateForgottenPassToken(token, email);
    }
}

