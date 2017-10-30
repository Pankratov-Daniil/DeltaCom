package com.adstand.app.controllers.implementation;

import com.adstand.app.controllers.api.MainController;
import com.adstand.app.services.api.TariffAndOptionsLoader;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/")
public class MainControllerImpl implements MainController {
    @EJB(beanName = "tariffAndOptionsLoader")
    private TariffAndOptionsLoader tariffAndOptionsLoader;

    @GET
    @Path("stand")
    public String adStand() {
        tariffAndOptionsLoader.getTariffsAndOptions();
        return "index.html";
    }
}
