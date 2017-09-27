package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.implementation.ClientSqlRepository;
import com.deltacom.app.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ClientService")
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientSqlRepository clientSqlRepository;

    @Override
    @Transactional
    public void createEntity(Client entity) {
        clientSqlRepository.add(entity);
    }

    @Override
    @Transactional
    public void updateEntity(Client entity) {
        clientSqlRepository.update(entity);
    }

    @Override
    @Transactional
    public void deleteEntity(Client entity) {
        clientSqlRepository.remove(entity);
    }

    @Override
    @Transactional
    public Client getEntityById(int id) {
        return (Client)clientSqlRepository.getById(id);
    }

    @Override
    @Transactional
    public List<Client> getAllEntities() {
        return clientSqlRepository.getAll();
    }
}
