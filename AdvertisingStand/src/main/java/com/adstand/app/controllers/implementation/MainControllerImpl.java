package com.adstand.app.controllers.implementation;

import com.adstand.app.controllers.api.MainController;
import com.adstand.app.services.api.TariffsLoader;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/")
public class MainControllerImpl implements MainController {
    @EJB(beanName = "tariffsLoader")
    private TariffsLoader tariffsLoader;

    @GET
    @Path("stand")
    public String adStand() {
        return tariffsLoader.getTariffsStr();
    }
}
