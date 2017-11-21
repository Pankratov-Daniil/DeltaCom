package com.adstand.app.services.implementation;

import com.adstand.app.controllers.AdminPanelBean;
import com.adstand.app.entity.TariffsToShow;
import com.adstand.app.services.api.TariffsLoader;
import com.deltacom.dto.TariffDTOwOpts;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for a class that loads tariffs
 */
@Singleton(name = "tariffsLoader")
public class TariffsLoaderImpl implements TariffsLoader {
    private static final Logger logger = LogManager.getLogger(TariffsLoader.class);
    private static final String GET_TARIFFS_URI = "https://deltacomapp.com/DeltaCom/getTariffsForStand";
    private List<TariffDTOwOpts> tariffs;
    private List<TariffsToShow> tariffsToShow;

    /**
     * Calls after bean created
     */
    @PostConstruct
    private void onBeanCreate() {
        getTariffsFromServer();
    }

    /**
     * Gets tariffs from server and saves it to tariffs list
     */
    @Override
    public void getTariffsFromServer() {
        logger.info("Started getting tariffs and options!");
        ResteasyClient client = new ResteasyClientBuilder().build();
        Response response = client.target(GET_TARIFFS_URI).request().get();
        if (response.getStatus() != 200) {
            RuntimeException exception = new RuntimeException("Failed while got tariffs: HTTP error code : "
                    + response.getStatus());
            logger.error(exception);
            throw exception;
        }
        String responseStr = response.readEntity(String.class);
        response.close();
        try {
            tariffs = new ObjectMapper().readValue(responseStr,
                            new TypeReference<List<TariffDTOwOpts>>() {});
            tariffsToShow = new ArrayList<>();
            for(TariffDTOwOpts tariff : tariffs) {
                tariffsToShow.add(new TariffsToShow(true, tariff));
            }
            WebSocketService.sendMessage(getTariffsForStand());
            logger.info("From server got tariffs: " + tariffs);
        } catch (IOException e) {
            logger.error("Cannot read tariffs!");
        }
    }

    @Override
    public List<TariffDTOwOpts> getTariffs() {
        return tariffs;
    }

    @Override
    public List<TariffDTOwOpts> getTariffsForStand() {
        List<TariffDTOwOpts> tariffsForStand = new ArrayList<>();
        for(TariffsToShow tariff : tariffsToShow) {
            if(tariff.isShown()) {
                tariffsForStand.add(tariff.getTariff());
            }
        }
        return tariffsForStand;
    }

    @Override
    public void setTariffsToShow(List<TariffsToShow> tariffsToShows) {
        this.tariffsToShow = tariffsToShows;
        WebSocketService.sendMessage(getTariffsForStand());
    }
}
