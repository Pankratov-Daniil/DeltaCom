package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository for Client
 */
public interface ClientRepository extends GenericRepository<Client, Integer> {
    public Client getClientByEmail(String email);
    public List<Client> getClientsFromIndex(int startIndex, int amount);
    public Client getClientByNumber(String number);
    public long getClientsCount();
    public Client getClientByForgottenPassToken(String token);
    public List<Contract> getClientContracts(String email);
    public Date getSmsSendDate(String email);
    public String getTwoFactorAuthNumber(String email);
}
