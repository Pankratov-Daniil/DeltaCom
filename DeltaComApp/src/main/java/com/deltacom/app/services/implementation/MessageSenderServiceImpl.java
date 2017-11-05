package com.deltacom.app.services.implementation;

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
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

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
        sendTariffChangedMessage();
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
     * Method for sending info about changed tariffs to message queue
     */
    @Override
    public void sendTariffChangedMessage() {
        try {
            messagingTemplate.convertAndSend("/topic/tariffs", tariffService.getAllTariffs());
            jmsTemplate.convertAndSend("Tariffs changed.");
            logger.info("Sent message about tariff changes.");
        } catch(Exception e) {
            return;
        }
    }
}

