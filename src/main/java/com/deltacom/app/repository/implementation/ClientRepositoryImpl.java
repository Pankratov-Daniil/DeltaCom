package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.api.ClientRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("Client")
public class ClientRepositoryImpl extends HibernateRepository<Client> implements ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Client getClientByNumber(String number) {
        return (Client)entityManager.createQuery("select contract.client from Contract contract where contract.number="+number);
    }
}
