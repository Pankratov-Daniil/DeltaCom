package com.adstand.app.controllers;

import com.adstand.app.services.TariffsLoader;
import com.adstand.app.services.LoginBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class for processing requests from view
 */
@Path("/")
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);

    @EJB
    private TariffsLoader tariffsLoader;
    @Inject
    private LoginBean loginBean;

    @GET
    @Path("/")
    public Response tariffsPage() throws URISyntaxException {
        if(loginBean.isHaveManagerRights()) {
            return Response.temporaryRedirect(new URI("../view/advStand.html")).build();
        } else {
            return Response.temporaryRedirect(new URI("../login.xhtml")).build();
        }
    }

    /**
     * Gets tariffs from tariffs loader and returns it
     * @return tariffs
     */
    @POST
    @Path("/getTariffs")
    public String getTariffs() {
        try {
            if(loginBean.isHaveManagerRights()) {
                return new ObjectMapper().writeValueAsString(tariffsLoader.getTariffsForStand());
            }
        } catch (JsonProcessingException e) {
            logger.error("Can't write tariffs as JSON: " + e);
        }
        return "";
    }
}
