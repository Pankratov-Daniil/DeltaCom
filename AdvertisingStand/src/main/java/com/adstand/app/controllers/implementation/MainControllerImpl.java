package com.adstand.app.controllers.implementation;

import com.adstand.app.controllers.api.MainController;
import com.adstand.app.services.api.TariffAndOptionsLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/")
public class MainControllerImpl implements MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);

    @EJB(beanName = "tariffAndOptionsLoader")
    private TariffAndOptionsLoader tariffAndOptionsLoader;

    @GET
    @Path("stand")
    public String adStand() {
        logger.info("adStand started");
        tariffAndOptionsLoader.getTariffsAndOptions();
        return "index.html";
    }
}
