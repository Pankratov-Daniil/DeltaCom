package com.deltacom.app.services.api;

import com.deltacom.app.entities.Client;

import java.util.List;

/**
 * Interface for Client service.
 */
public interface ClientService {
    public Client getClientById(Integer id);
    public Client getClientByEmail(String email);
    public boolean addNewClient(Client client, String[] accessLevelsIds);
    public List<Client> getClientsByIds(int startId, int amount);
}
