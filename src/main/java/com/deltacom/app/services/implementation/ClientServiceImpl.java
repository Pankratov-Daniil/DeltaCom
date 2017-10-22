package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.exceptions.ClientException;
import com.deltacom.app.repository.implementation.ClientRepositoryImpl;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.ContractService;
import com.deltacom.app.utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Operations with repository for Client entities.
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService{
    @Autowired
    private ClientRepositoryImpl clientRepository;
    @Autowired
    private ContractService contractService;

    /**
     * Gets Client entity by its id from database.
     * @param id id of Client entity to be found
     * @return founded Client entity
     */
    @Transactional
    public Client getClientById(Integer id) {
        try {
            return clientRepository.getById(id);
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't gotten by id: ", ex);
        }
    }

    /**
     * Gets client by his email
     * @param email client email
     * @return client
     */
    @Override
    @Transactional
    public Client getClientByEmail(String email) {
        try {
            return clientRepository.getClientByEmail(email);
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't gotten by email: ", ex);
        }
    }

    /**
     * Adds new client
     * @param client client without accessLevels and hash password
     * @param accessLevelsIds ids of access levels
     * @return true if addition successful, false otherwise
     */
    @Override
    @Transactional
    public boolean addNewClient(Client client, String[] accessLevelsIds) {
        List<AccessLevel> accessLevels = new ArrayList<>();

        if(accessLevelsIds == null || accessLevelsIds.length == 0) {
            accessLevels.add(new AccessLevel(1));
        }
        else {
            for (String accessLevelId : accessLevelsIds) {
                accessLevels.add(new AccessLevel(Integer.parseInt(accessLevelId)));
            }
        }
        client.setPassword(PasswordEncrypter.encryptPassword(client.getPassword()));
        client.setAccessLevels(accessLevels);

        try {
            clientRepository.add(client);
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't added: ", ex);
        }

        return true;
    }

    /**
     * Gets clients for summary table
     * @param startIndex start index of client in database
     * @param amount how many clients need to be returned
     * @return list of client
     */
    @Override
    @Transactional
    public List<Client> getClientsFromIndex(int startIndex, int amount) {
        try {
            if(amount < 0) {
                throw new PersistenceException("Amount cannot be less than 0");
            }
            if(startIndex < 0) {
                throw new PersistenceException("Start index cannot be less than 0");
            }
            return clientRepository.getClientsFromIndex(startIndex, amount);
        } catch (PersistenceException ex) {
            throw new ClientException("Clients wasn't gotten from index: ", ex);
        }
    }

    /**
     * Gets client by his number
     * @param number number of client
     * @return found client
     */
    @Override
    @Transactional
    public Client getClientByNumber(String number) {
        try {
            return clientRepository.getClientByNumber(number);
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't gotten by number: ", ex);
        }
    }

    /**
     * Gets clients count
     * @return clients count
     */
    @Override
    @Transactional
    public long getClientsCount() {
        try {
            return clientRepository.getClientsCount();
        } catch (PersistenceException ex) {
            throw new ClientException("Clients count wasn't gotten: ", ex);
        }
    }

    /**
     * Removes client
     * @param clientId id of client to be removed
     */
    @Override
    @Transactional
    public void removeClient(int clientId) {
        try {
            Client client = getClientById(clientId);
            for(Contract contract : client.getContracts()) {
                contractService.deleteContract(contract.getId());
            }
            client.setContracts(null);
            clientRepository.remove(client);
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't deleted: ", ex);
        }
    }
}
