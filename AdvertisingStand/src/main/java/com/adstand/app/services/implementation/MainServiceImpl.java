package com.adstand.app.services.implementation;

import com.adstand.app.services.api.MessageListenerService;
import com.adstand.app.services.api.TariffsLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.ejb3.annotation.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig =
        {
                @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
                @ActivationConfigProperty(propertyName="destination", propertyValue="deltacom/topic"),
                @ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge")
        })
@ResourceAdapter("activemq-rar")
public class MainServiceImpl implements MessageListener {
    private static final Logger logger = LogManager.getLogger(MainServiceImpl.class);

    @EJB(beanName = "tariffsLoader")
    private TariffsLoader tariffsLoader;

   @Override
    public void onMessage(Message msg) {
       try {
           TextMessage message = (TextMessage)msg;
           logger.info("GOT MESSAGE " + message.getText());
           tariffsLoader.getTariffsFromServer();
       } catch (JMSException ex) {
		   logger.error("CAN'T READ MESSAGE!");
           ex.printStackTrace();
       }
    }
}
