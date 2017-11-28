package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.exceptions.ClientException;
import com.deltacom.app.exceptions.MessageSenderException;
import com.deltacom.app.repository.api.ClientRepository;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.ContractService;
import com.deltacom.app.services.api.MessageSenderService;
import com.deltacom.app.utils.PasswordEncrypter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Operations with repository for Client entities.
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LogManager.getLogger(ClientService.class);
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
            try {
                messageSenderService.sendResetPasswordEmail(client.getEmail());
            } catch(MessageSenderException ex) {
                logger.error("Can't send email to new user: " + ex.getMessage());
            }
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
     * Confirms number for two factor auth: adds sms code to DB and send sms to number
     * @param email client email
     * @param number client number
     * @return "" if ok, "time" if sms was send less than 5 minutes ago, "Error" if there was error with adding sms code to DB
     */
    @Override
    @Transactional
    public String confirmNumberFor2FA(String email, String number) {
        String smsCode;
        try {
            smsCode = addSmsCode(email);
        } catch (PersistenceException ex) {
            throw new ClientException("Error with adding sms code to DB: ", ex);
        }
        if (smsCode.equals("time")) {
            return smsCode;
        }
        return messageSenderService.sendSms("79313438188", "Your DeltaCom verification code: " + smsCode);
//        return messageSenderService.sendSms(number, "Your DeltaCom verification code: " + smsCode);
    }

    /**
     * Generate and add sms code to client
     * @param email client email
     * @return generated sms code
     */
    @Override
    @Transactional
    public String addSmsCode(String email) {
        int waitTime = 5; // time between sms
        int secondsInMinute = 60;
        int millisecondsInSecond = 1000;
        Date smsDate = clientRepository.getSmsSendDate(email);
        if(smsDate != null) {
            Date now = new Date();
            if((((now.getTime() - smsDate.getTime())/(secondsInMinute * millisecondsInSecond)) < waitTime)) {
                return "time";
            }
        }

        String smsCode = Integer.toString(ThreadLocalRandom.current().nextInt(1000, 9998));
        try {
            Client client = clientRepository.getClientByEmail(email);
            client.setSmsSendDate(new Date());
            client.setSmsCode(smsCode);
            clientRepository.update(client);
        } catch (PersistenceException ex) {
            throw new ClientException("Can't add sms code for client with email: " + email, ex);
        }
        return smsCode;
    }

    /**
     * Changes status of two factor authorization
     * @param email client email
     * @param number client number
     * @param smsCode sms code
     */
    @Override
    @Transactional
    public void updateTwoFactorAuth(String email, String number, String smsCode) {
        Client checkClient = getClientByNumber(number);
        if(checkClient == null || !checkClient.getEmail().equals(email)) {
            throw new ClientException("Client with email " + email +" doesn't have number " + number, new RuntimeException());
        } else if(!smsCode.equals(checkClient.getSmsCode())) {
            throw new ClientException("Entered wrong sms code.", new RuntimeException());
        } else {
            boolean enable = !checkClient.getTwoFactorAuth();
            checkClient.setSmsCode(null);
            checkClient.setSmsSendDate(null);
            checkClient.setTwoFactorAuth(enable);
            checkClient.setTwoFactorAuthNumber(enable ? number : null);
            try {
                updateClient(checkClient);
            } catch (PersistenceException ex) {
                throw new ClientException("Two factor auth wasn't " + (enable ? "enabled" : "disabled") + " for client: " + email, ex);
            }
        }
    }

    /**
     * Gets two factor auth status
     * @param email client email
     */
    @Override
    @Transactional
    public String getTwoFactorAuthStatus(String email) {
        try {
            return clientRepository.getTwoFactorAuthNumber(email);
        } catch (PersistenceException ex) {
            throw new ClientException("Can't get two factor auth status: ", ex);
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

    /**
     * Gets client contracts numbers
     * @param email client email
     * @return list of client numbers or null
     */
    @Override
    @Transactional
    public List<String> getClientNumbers(String email) {
        List<Contract> contracts;
        try {
            contracts = clientRepository.getClientContracts(email);
        } catch (PersistenceException ex) {
            throw new ClientException("Can't get contracts for client with email: " + email, ex);
        }
        try {
            List<String> numbers = new ArrayList<>();
            for(Contract contract : contracts) {
                numbers.add(contract.getNumbersPool().getNumber());
            }
            return numbers;
        } catch (PersistenceException ex) {
            throw new ClientException("Can't get contracts numbers for client with email: " + email, ex);
        }
    }
}
