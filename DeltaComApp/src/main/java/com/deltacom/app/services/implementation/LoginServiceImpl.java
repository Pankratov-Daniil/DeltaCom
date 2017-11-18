package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.services.api.ClientLocationService;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.LoginService;
import com.deltacom.app.services.api.MessageSenderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
     * Save client location
     * @param ip client ip
     * @param clientEmail client email
     */
    @Override
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
