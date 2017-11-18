package com.deltacom.app.services.api;

public interface LoginService {
    public String sendPreAuthCode(String email);
    public boolean isEnteredPreAuthCodeValid(String email, String preAuthCode);
    public void grantAuthorities(String email);
    public void saveClientLocation(String ip, String clientEmail);
}
