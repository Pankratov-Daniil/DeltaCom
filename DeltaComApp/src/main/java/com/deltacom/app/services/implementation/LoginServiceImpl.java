package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.exceptions.LoginException;
import com.deltacom.app.services.api.ClientLocationService;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.LoginService;
import com.deltacom.app.services.api.MessageSenderService;
import com.deltacom.app.utils.PasswordEncrypter;
import com.deltacom.dto.ClientDTO;
import com.deltacom.dto.CredentialsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LogManager.getLogger(LoginService.class);

    @Autowired
    ClientService clientService;
    @Autowired
    private ClientLocationService clientLocationService;
    @Autowired
    private MessageSenderService messageSenderService;

    /**
     * Sends sms with two factor auth code
     * @param email client email
     */
    @Override
    @Transactional
    public String sendPreAuthCode(String email) {
        String smsCode;
        try {
            smsCode = clientService.addSmsCode(email);
        } catch (PersistenceException ex) {
            throw new LoginException("Error with adding sms code to DB: ", ex);
        }
        if (smsCode.equals("time")) {
            return smsCode;
        }
        //return messageSenderService.sendSms(client.getTwoFactorAuthNumber(), "Your DeltaCom verification code: " + smsCode);
        return messageSenderService.sendSms("79313438188", "Your DeltaCom verification code: " + smsCode);
    }

    /**
     * Checks if entered code is valid
     * @param email client email
     * @param preAuthCode entered pre auth code
     * @return true if code is valid, false if not
     */
    @Override
    @Transactional
    public boolean isEnteredPreAuthCodeValid(String email, String preAuthCode) {
        Client client = clientService.getClientByEmail(email);
        boolean codeValid = client.getSmsCode().equals(preAuthCode);
        if(codeValid) {
            client.setSmsCode(null);
            client.setSmsSendDate(null);
            try {
                clientService.updateClient(client);
            } catch (PersistenceException ex) {
                throw new LoginException("Can't update sms code and sms send date for client: " + email, ex);
            }
        }
        return codeValid;
    }

    /**
     * Grants authorities to client
     * @param email client email
     */
    @Override
    @Transactional
    public void grantAuthorities(String email) {
        Client client = clientService.getClientByEmail(email);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (AccessLevel accessLevel : client.getAccessLevels()) {
            authorities.add(new SimpleGrantedAuthority(accessLevel.getName()));
        }
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    /**
     * Save client location
     * @param ip client ip
     * @param clientEmail client email
     */
    @Override
    @Transactional
    public void saveClientLocation(String ip, String clientEmail) {
        Client client = clientService.getClientByEmail(clientEmail);
        if(client == null) {
            throw new RuntimeException("Can't get client with email: " + clientEmail + " .");
        }
        RestTemplate restTemplate = new RestTemplate();
        String getIpURL = "https://freegeoip.net/json/" + ip;
        ResponseEntity<String> response = restTemplate.getForEntity(getIpURL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
        } catch (IOException e) {
            logger.warn("Can't read response from freegeoip.");
            return;
        }

        ClientLocation clientLocation = new ClientLocation(client, Float.parseFloat(root.path("latitude").asText()),
                Float.parseFloat(root.path("longitude").asText()), root.path("city").asText(),
                root.path("country_name").asText(), root.path("ip").asText(), null);
        checkLastLocation(client, clientLocation);
        clientLocationService.addClientLocations(clientLocation);
    }

    /**
     * Checks if email and password from clientDTO are correct and returns accessLevels or null
     * @param credentialsDTO client dto
     * @return null if email and password are incorrect or list of access levels
     */
    @Override
    public List<String> remoteLogin(CredentialsDTO credentialsDTO) {
        Client client = clientService.getClientByEmail(credentialsDTO.getEmail());
        if(client == null) {
            return null;
        }
        if(!PasswordEncrypter.passwordsEquals(credentialsDTO.getPassword(), client.getPassword())) {
            return null;
        }
        List<String> accessLevelsList = new ArrayList<>();
        for(AccessLevel accessLevel :  client.getAccessLevels()) {
            accessLevelsList.add(accessLevel.getName());
        }
        return accessLevelsList;
    }

    /**
     * Compare last and current client locations. If city or country doesn't equals, sends email to client.
     * @param client logged client
     * @param currentClientLocation logged client location
     */
    private void checkLastLocation(Client client, ClientLocation currentClientLocation) {
        ClientLocation lastClientLocation = clientLocationService.getLastClientLocation(client.getId());
        if(lastClientLocation != null && (!lastClientLocation.getCity().equals(currentClientLocation.getCity()) ||
                !lastClientLocation.getCountry().equals(currentClientLocation.getCountry()))) {
            messageSenderService.sendSecurityAlertEmail(client.getEmail(), currentClientLocation);
        }
    }
}
