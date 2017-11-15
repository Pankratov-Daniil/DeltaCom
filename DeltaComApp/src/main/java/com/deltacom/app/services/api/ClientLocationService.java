package com.deltacom.app.services.api;

import com.deltacom.app.entities.ClientLocation;

import java.util.List;

/**
 * Interface for ClientLocation service.
 */
public interface ClientLocationService {
    public List<ClientLocation> getClientLocationsByClientId(int id);
    public void addClientLocations(ClientLocation clientLocation);
    public ClientLocation getLastClientLocation(int clientId);
}
