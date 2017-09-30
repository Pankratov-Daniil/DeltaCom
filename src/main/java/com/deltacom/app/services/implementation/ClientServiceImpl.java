package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.implementation.ClientSqlRepository;
import com.deltacom.app.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Operations with repository for Client entities.
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientSqlRepository clientSqlRepository;

    /**
     * Creates new Client entity in database.
     * @param entity Client entity to be created
     */
    @Override
    @Transactional
    public void createEntity(Client entity) {
        clientSqlRepository.add(entity);
    }

    /**
     * Updates Client entity in database.
     * @param entity Client entity to be updated
     */
    @Override
    @Transactional
    public void updateEntity(Client entity) {
        clientSqlRepository.update(entity);
    }

    /**
     * Deletes Client entity in database.
     * @param entity Client entity to be deleted
     */
    @Override
    @Transactional
    public void deleteEntity(Client entity) {
        clientSqlRepository.remove(entity);
    }

    /**
     * Gets Client entity by its id from database.
     * @param id id of Client entity to be found
     * @return founded Client entity
     */
    @Override
    @Transactional
    public Client getEntityById(int id) {
        return (Client) clientSqlRepository.getById(id);
    }

    /**
     * Gets all Client entities from database.
     * @return List of Client entities from database
     */
    @Override
    @Transactional
    public List<Client> getAllEntities() {
        return clientSqlRepository.getAll();
    }
}
