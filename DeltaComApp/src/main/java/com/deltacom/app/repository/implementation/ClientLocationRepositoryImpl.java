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
}
