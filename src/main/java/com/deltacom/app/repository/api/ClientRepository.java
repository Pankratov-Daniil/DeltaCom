package com.deltacom.app.repository.api;

import com.deltacom.app.entities.Client;

import java.util.List;

/**
 * Repository for Client
 */
public interface ClientRepository {
    public Client getClientByEmail(String email);
    public List<Client> getClientsByIds(int startId, int amount);
}
