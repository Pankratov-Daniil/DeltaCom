package com.adstand.app.services;

import com.adstand.app.entity.Credentials;
import com.adstand.app.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Login bean
 */
@SessionScoped
@Named
public class LoginBean implements Serializable {
    private static final Logger logger = LogManager.getLogger(LoginBean.class);
    private static final String REMOTE_LOGIN_URI = "https://deltacomapp.com/DeltaCom/remoteLogin";
    private User user;
    @Inject
    private Credentials credentials;

    /**
     * Send request to main app for get user authorities,
     * checks if user have manager or admin rights and if have, save user
     * @return redirect to login page
     */
    public String login() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(REMOTE_LOGIN_URI);
        Response response  = target
                .queryParam("email", credentials.getEmail())
                .queryParam("password", credentials.getPassword())
                .request()
                .get();
        if (response.getStatus() != 200) {
            RuntimeException exception = new RuntimeException("Failed while remote login: HTTP error code : "
                    + response.getStatus());
            logger.error(exception);
            throw exception;
        }
        String responseStr = response.readEntity(String.class);
        response.close();
        List<String> roles = new ArrayList<>();
        try {
            roles = new ObjectMapper().readValue(responseStr,
                            new TypeReference<List<String>>() {});
        } catch (IOException e) {
            logger.error("Cannot read user roles!");
        }

        if(roles.contains("ROLE_MANAGER") || roles.contains("ROLE_ADMIN")) {
            user = new User(credentials.getEmail(), roles);
            logger.info("User with email: " + credentials.getEmail() + " logged in.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are successfully logged in."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email or password are incorrect."));
        }
        return "login?faces-redirect=true";
    }

    /**
     * Logout
     * @return redirect to login page
     */
    public String logout() throws IOException {
        logger.info("User with email: " + user.getEmail() + " logged out.");
        user = null;
        return "login?faces-redirect=true";
    }

    /**
     * Checks if user have manager or admin rights
     * @return true if user have manager or admin rights, false otherwise
     */
    public boolean isHaveManagerRights() {
        return user != null && (user.getRoles().contains("ROLE_MANAGER") || user.getRoles().contains("ROLE_ADMIN"));
    }

    public User getUser() {
        return user;
    }
}
