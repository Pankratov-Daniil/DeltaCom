package com.adstand.app.services.implementation;

import com.adstand.app.services.api.TariffAndOptionsLoader;
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
                @ActivationConfigProperty(propertyName="destination", propertyValue="DeltaCom.topic"),
                @ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge")
        })
@ResourceAdapter("activemq-ra.rar")
public class MainServiceImpl implements MessageListener {
    @EJB(beanName = "tariffAndOptionsLoader")
    private TariffAndOptionsLoader tariffAndOptionsLoader;

   @Override
    public void onMessage(Message msg) {
       try {
           TextMessage message = (TextMessage)msg;
           System.out.println("MESSAGE " + message.getText());
           tariffAndOptionsLoader.getTariffsAndOptions();
       } catch (JMSException ex) {
           ex.printStackTrace();
       }
    }
}
