package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.api.ClientRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Client repository implementation
 */
@Repository("Client")
public class ClientRepositoryImpl extends HibernateRepository<Client> implements ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets client by email from database.
     * @param email user email
     * @return Client or null if nothing found
     */
    @Override
    public Client getClientByEmail(String email) {
        try {
            return (Client) entityManager.createQuery("select client from Client client where client.email = :emailStr")
                    .setParameter("emailStr", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
