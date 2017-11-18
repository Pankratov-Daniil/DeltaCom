package com.deltacom.app.services.api;

import com.deltacom.app.entities.ClientLocation;

/**
 * Interface for sending message
 */
public interface MessageSenderService {
    public void sendResetPasswordEmail(String email);
    public void sendEmail(String email, String text, String subject);
    public void sendSecurityAlertEmail(String email, ClientLocation location);
    public String sendSms(String number, String smsText);
}
