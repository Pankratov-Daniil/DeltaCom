package com.deltacom.app.services.api;

import com.deltacom.dto.CredentialsDTO;

import java.util.List;

public interface LoginService {
    public String sendPreAuthCode(String email);
    public boolean isEnteredPreAuthCodeValid(String email, String preAuthCode);
    public void grantAuthorities(String email);
    public void saveClientLocation(String ip, String clientEmail);
    public List<String> remoteLogin(CredentialsDTO credentialsDTO);
}
