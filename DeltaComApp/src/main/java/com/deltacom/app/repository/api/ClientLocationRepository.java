package com.deltacom.app.repository.api;

import com.deltacom.app.entities.ClientLocation;

import java.util.List;

/**
 * Repository for Client Locations
 */
public interface ClientLocationRepository extends GenericRepository<ClientLocation, Integer>{
    public List<ClientLocation> getClientLocationsByClientId(int id);
    public ClientLocation getLastClientLocation(int clientId);
}
