package com.deltacom.app.services.api;

import com.deltacom.app.entities.Client;

import java.util.List;

/**
 * Interface for Client service.
 */
public interface ClientService extends RepositoryService<Client, Integer> {
    public Client getClientByEmail(String email);
    public boolean addNewClient(Client client, String[] accessLevelsIds);
    public List<Client> getClientsForSummaryTable(int startId, int countEntries);
}
