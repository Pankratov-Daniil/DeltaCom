package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.repository.api.ClientLocationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Repository("ClientLocation")
public class ClientLocationRepositoryImpl extends HibernateRepository<ClientLocation, Integer> implements ClientLocationRepository{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets client locations by client id
     * @param id client id
     * @return list of client locations
     */
    @Override
    public List<ClientLocation> getClientLocationsByClientId(int id) {
        try {
            return (List<ClientLocation>) entityManager.createNativeQuery("SELECT * FROM client_location " +
                    "WHERE client_location.client_id = :clientId", ClientLocation.class)
                    .setParameter("clientId", id)
                    .getResultList();
        } catch (PersistenceException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets last client location
     * @param clientId client id
     * @return client location or null
     */
    @Override
    public ClientLocation getLastClientLocation(int clientId) {
        try {
            List<ClientLocation> locations = entityManager.createNativeQuery("SELECT * FROM client_location " +
                    "WHERE client_location.client_id = :clientId ORDER BY client_location.entered_date DESC", ClientLocation.class)
                    .setParameter("clientId", clientId)
                    .getResultList();
            return !locations.isEmpty() ? locations.get(0) : null;
        } catch (PersistenceException e) {
            return null;
        }
    }


}
