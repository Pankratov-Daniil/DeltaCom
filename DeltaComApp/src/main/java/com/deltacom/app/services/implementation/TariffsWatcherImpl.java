package com.deltacom.app.services.implementation;

import com.deltacom.app.exceptions.TariffsWatcherException;
import com.deltacom.app.services.api.MessageSenderService;
import com.deltacom.app.services.api.OptionService;
import com.deltacom.app.services.api.TariffService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service("TariffsWatcher")
public class TariffsWatcherImpl {
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
            messageSenderService.sendToQueue("Tariffs changed.");
        } catch(Exception e) {
            throw new TariffsWatcherException("Can't send message about changing tariffs: ", e);
        }
    }

    /**
     * Method for catching successful return from add, update, delete methods of OptionService
     */
    @AfterReturning("execution(void com.deltacom.app.services.api.OptionService.*(..))")
    public void afterOptionChanged() {
        try {
            messageSenderService.sendToTopic("/topic/options", optionService.getAllOptions());
        } catch(Exception e) {
            throw new TariffsWatcherException("Can't send message about changing options: ", e);
        }
    }
}
