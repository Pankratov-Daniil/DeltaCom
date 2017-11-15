package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.exceptions.ClientLocationException;
import com.deltacom.app.repository.api.ClientLocationRepository;
import com.deltacom.app.services.api.ClientLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Operations with repository for ClientLocation entities.
 */
@Service("ClientLocationService")
public class ClientLocationServiceImpl implements ClientLocationService {
    @Autowired
    private ClientLocationRepository clientLocationRepository;

    /**
     * Gets all client locations by his id
     * @param id client id
     * @return list of client locations
     */
    @Override
    @Transactional
    public List<ClientLocation> getClientLocationsByClientId(int id) {
        try {
            return clientLocationRepository.getClientLocationsByClientId(id);
        } catch (PersistenceException ex) {
            throw new ClientLocationException("Can't get client location by client id: ", ex);
        }
    }

    /**
     * Adds new client location
     * @param clientLocation client location
     */
    @Override
    @Transactional
    public void addClientLocations(ClientLocation clientLocation) {
        try {
            clientLocationRepository.add(clientLocation);
        } catch (PersistenceException ex) {
            throw new ClientLocationException("Can't add client location for client with id: " + clientLocation.getClient().getId(), ex);
        }
    }
}
