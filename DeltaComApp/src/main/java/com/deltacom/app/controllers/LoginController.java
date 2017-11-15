package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.services.api.ClientLocationService;
import com.deltacom.app.services.api.ClientService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

/**
 * Controller for processing requests to login page
 */
@Controller
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientLocationService clientLocationService;

    @RequestMapping(value = "/")
    public ModelAndView rootProcessing() {
        return processLogin();
    }

    @RequestMapping(value = "/process-login")
    public ModelAndView processLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // if user has already logged in - redirect him to index page
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            for(GrantedAuthority authority : auth.getAuthorities()) {
                logger.info("User with email: " + auth.getName() + " and authorities: " + auth.getAuthorities() + " logged in.");
                if (authority.getAuthority().contains("ADMIN"))
                    return new ModelAndView("redirect:/admin/index");
                else if (authority.getAuthority().contains("MANAGER"))
                    return new ModelAndView("redirect:/manager/index");
                else if (authority.getAuthority().contains("USER"))
                    return new ModelAndView("redirect:/user/index");
            }
        }
        return new ModelAndView("loginPage");
    }

    /**
     * Processing request to getLocation page
     * @return redirect to getLocation page
     */
    @RequestMapping(value = "/getClientLocation")
    public ModelAndView getLocation() {
        return new ModelAndView("getLocation");
    }

    /**
     * Saves client location to DB
     */
    @RequestMapping(value = "/setLocation", method = RequestMethod.POST)
    public void saveClientLocation(@RequestParam float latitude,
                                    @RequestParam float longitude,
                                    @RequestParam String city,
                                    @RequestParam String country,
                                    @RequestParam String ip,
                                    Principal principal) {
        String clientEmail = principal.getName();
        Client client = clientService.getClientByEmail(clientEmail);
        if(client == null) {
            throw new RuntimeException("Can't get client with email: " + clientEmail + " .");
        }
        ClientLocation clientLocation = new ClientLocation(client, latitude, longitude, city, country, ip, null);
        clientLocationService.addClientLocations(clientLocation);
    }
}
