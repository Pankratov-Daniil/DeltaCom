package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.exceptions.ClientException;
import com.deltacom.app.repository.api.ClientRepository;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.ContractService;
import com.deltacom.app.services.api.MessageSenderService;
import com.deltacom.app.utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Operations with repository for Client entities.
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService{
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ContractService contractService;
    @Autowired
    private MessageSenderService messageSenderService;

    /**
     * Gets Client entity by its id from database.
     * @param id id of Client entity to be found
     * @return founded Client entity
     */
    @Override
    @Transactional
    public Client getClientById(int id) {
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
            if(email == null) {
                throw new PersistenceException("Email cannot be null.");
            }
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
        Set<AccessLevel> accessLevels = new HashSet<>();
        try {
            if(client == null) {
                throw new PersistenceException("Client cannot be null");
            }
            if(accessLevelsIds == null || accessLevelsIds.length == 0) {
                accessLevels.add(new AccessLevel(1));
            }
            else {
                for (String accessLevelId : accessLevelsIds) {
                    accessLevels.add(new AccessLevel(Integer.parseInt(accessLevelId)));
                }
            }
            client.setAccessLevels(accessLevels);
            clientRepository.add(client);
            messageSenderService.sendResetPasswordEmail(client.getEmail());
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
            if(number == null) {
                throw new PersistenceException("Number cannot be null.");
            }
            return clientRepository.getClientByNumber(number);
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't gotten by number: ", ex);
        }
    }

    /**
     * Gets client by forgotten pass token
     * @param token unique token
     * @return client
     */
    @Override
    @Transactional
    public Client getClientByForgottenPassToken(String token) {
        try {
            if(token == null || token.isEmpty()) {
                throw new PersistenceException("Invalid token!");
            } else {
                return clientRepository.getClientByForgottenPassToken(token);
            }
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't gotten by token: ", ex);
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
     * Changes client password
     * @param client client
     * @param oldPassword entered old password
     * @param newPassword new password
     */
    @Override
    @Transactional
    public void changePassword(Client client, String oldPassword, String newPassword) {
        if(!PasswordEncrypter.passwordsEquals(oldPassword, client.getPassword())) {
            throw new ClientException("Entered old password doesn't equal to current password", new RuntimeException());
        }

        String newEncryptedPassword = PasswordEncrypter.encryptPassword(newPassword);
        client.setPassword(newEncryptedPassword);
        try {
            updateClient(client);
        } catch (PersistenceException ex) {
            throw new ClientException("Can't change password: ", ex);
        }
    }

    /**
     * Updates client forgotten pass token
     * @param token unique token
     * @param email clients email
     */
    @Override
    @Transactional
    public void updateForgottenPassToken(String token, String email) {
        try {
            Client client = getClientByEmail(email);
            client.setForgottenPassToken(token);
            updateClient(client);
        } catch (PersistenceException ex) {
            throw new ClientException("Forgotten password token wasn't updated: ", ex);
        }
    }

    /**
     * Updates client
     * @param client new client data
     */
    @Override
    @Transactional
    public void updateClient(Client client) {
        try {
            clientRepository.update(client);
        } catch (PersistenceException ex) {
            throw new ClientException("Client wasn't updated: ", ex);
        }
    }

    /**
     * Removes client
     * @param clientId id of client to be removed
     */
    @Override
    @Transactional
    public void deleteClient(int clientId) {
        try {
            Client client = getClientById(clientId);
            if(client == null) {
                throw new PersistenceException("Client wasn't found");
            }
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
