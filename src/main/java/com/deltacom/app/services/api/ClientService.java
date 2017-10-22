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
    public List<Client> getClientsFromIndex(int startIndex, int amount);
    public Client getClientByNumber(String number);
    public long getClientsCount();
    public void removeClient(int clientId);
}
