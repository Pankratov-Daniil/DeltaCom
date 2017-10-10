package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.repository.implementation.ClientRepositoryImpl;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.utils.PasswordEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Operations with repository for Client entities.
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService{
    @Autowired
    private ClientRepositoryImpl clientRepository;

    /**
     * Gets Client entity by its id from database.
     * @param id id of Client entity to be found
     * @return founded Client entity
     */
    @Transactional
    public Client getClientById(Integer id) {
        return (Client) clientRepository.getById(id);
    }

    /**
     * Gets client by his email
     * @param email client email
     * @return client
     */
    @Override
    @Transactional
    public Client getClientByEmail(String email) {
        return clientRepository.getClientByEmail(email);
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

        client.setPassword(PasswordEncrypt.encryptPassword(client.getPassword()));
        client.setAccessLevels(accessLevels);

        clientRepository.add(client);

        return true;
    }

    /**
     * Gets clients for summary table
     * @param startId start id of client in database
     * @param amount how many clients need to be returned
     * @return list of client
     */
    @Override
    @Transactional
    public List<Client> getClientsByIds(int startId, int amount) {
        return clientRepository.getClientsByIds(startId, amount);
    }

    /**
     * Gets client by his number
     * @param number number of client
     * @return found client
     */
    @Override
    @Transactional
    public Client getClientByNumber(String number) {
        return clientRepository.getClientByNumber(number);
    }
}
