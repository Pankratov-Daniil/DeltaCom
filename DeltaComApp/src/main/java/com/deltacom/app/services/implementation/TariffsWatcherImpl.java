package com.deltacom.app.services.implementation;

import com.deltacom.app.services.api.MessageSenderService;
import com.deltacom.app.services.api.OptionService;
import com.deltacom.app.services.api.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service("TariffsWatcher")
public class TariffsWatcherImpl {
    private static final Logger logger = LogManager.getLogger(TariffsWatcherImpl.class);
    @Autowired
    private MessageSenderService messageSenderService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private TariffService tariffService;

    /**
     * Method for catching successful return from add, update, delete methods of TariffService
     */
    @AfterReturning("execution(void com.deltacom.app.services.api.TariffService.*(..))")
    public void afterTariffChanged() {
        try {
            messageSenderService.sendToTopic("/topic/tariffs", tariffService.getAllTariffs());
        } catch(RuntimeException e) {
            logger.error("Can't send message about changing tariffs to topic: " + e.getMessage());
        }
        try {
            messageSenderService.sendToQueue("Tariffs changed.");
        } catch(RuntimeException e) {
            logger.error("Can't send message about changing tariffs to MQ: " + e.getMessage());
        }
    }

    /**
     * Method for catching successful return from add, update, delete methods of OptionService
     */
    @AfterReturning("execution(void com.deltacom.app.services.api.OptionService.*(..))")
    public void afterOptionChanged() {
        try {
            messageSenderService.sendToTopic("/topic/options", optionService.getAllOptions());
        } catch(RuntimeException e) {
            logger.error("Can't send message about changing options: " + e.getMessage());
        }
    }
}
