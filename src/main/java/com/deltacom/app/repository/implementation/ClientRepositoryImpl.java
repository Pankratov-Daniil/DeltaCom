package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.api.ClientRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Client repository implementation
 */
@Repository("Client")
public class ClientRepositoryImpl extends HibernateRepository<Client, Integer> implements ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets client by email from database.
     * @param email user email
     * @return Client or null if there was exception
     */
    @Override
    public Client getClientByEmail(String email) {
        try {
            return (Client) entityManager.createQuery("select client from Client client where client.email = :emailStr")
                    .setParameter("emailStr", email).getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    /**
     * Gets 'amount' clients from 'startIndex'
     * @param startIndex start index
     * @param amount how many clients need to be returned
     * @return list of clients
     */
    public List<Client> getClientsFromIndex(int startIndex, int amount) {
        try {
            return (List<Client>) entityManager.createQuery("select client from Client client, AccessLevel accessLevel where accessLevel.name = :roleName and accessLevel in elements(client.accessLevels)")
                    .setParameter("roleName", "ROLE_USER")
                    .setFirstResult(startIndex)
                    .setMaxResults(amount)
                    .getResultList();
        } catch (PersistenceException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets client by number
     * @param number number of client
     * @return found client or null if exception was thrown
     */
    @Override
    public Client getClientByNumber(String number) {
        try {
            return (Client) entityManager.createQuery("select client from Client client, Contract contract " +
                    "where contract.client.id = client.id and contract.numbersPool.number = :number")
                    .setParameter("number", number).getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    /**
     * Gets clients count
     * @return client count
     */
    @Override
    public long getClientsCount() {
        try {
            return (long) entityManager.createQuery("select count(*) from Client").getSingleResult();
        } catch (PersistenceException e) {
            return 0;
        }
    }
}
