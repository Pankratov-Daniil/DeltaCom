package com.deltacom.app.services.api;

import com.deltacom.app.entities.Client;

import java.util.List;

/**
 * Interface for Client service.
 */
public interface ClientService {
    public Client getClientById(int id);
    public Client getClientByEmail(String email);
    public boolean addNewClient(Client client, String[] accessLevelsIds);
    public List<Client> getClientsFromIndex(int startIndex, int amount);
    public Client getClientByNumber(String number);
    public Client getClientByForgottenPassToken(String token);
    public long getClientsCount();
    public void changePassword(Client client, String oldPassword, String newPassword);
    public void updateForgottenPassToken(String token, String email);
    public String confirmNumberFor2FA(String email, String number);
    public String addSmsCode(String email);
    public void updateTwoFactorAuth(String email, String number, String smsCode);
    public String getTwoFactorAuthStatus(String email);
    public void updateClient(Client client);
    public void deleteClient(int clientId);
    public List<String> getClientNumbers(String email);
}
