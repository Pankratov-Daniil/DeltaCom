package com.adstand.app.controllers;

import com.adstand.app.services.api.TariffsLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Class for processing requests from view
 */
@Path("/")
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);

    @EJB(beanName = "tariffsLoader")
    private TariffsLoader tariffsLoader;

    /**
     * Gets tariffs from tariffs loader and returns it
     * @return tariffs
     */
    @POST
    @Path("/getTariffs")
    public String getTariffs() {
        try {
            return new ObjectMapper().writeValueAsString(tariffsLoader.getTariffs());
        } catch (JsonProcessingException e) {
            logger.error("Can't write tariffs as JSON: " + e);
        }
        return "";
    }
}
