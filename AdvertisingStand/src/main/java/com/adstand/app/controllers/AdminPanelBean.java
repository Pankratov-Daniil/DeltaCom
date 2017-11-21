package com.adstand.app.controllers;

import com.adstand.app.entity.TariffsToShow;
import com.adstand.app.services.api.TariffsLoader;
import com.deltacom.dto.TariffDTOwOpts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "adminPanelBean")
@ApplicationScoped
public class AdminPanelBean {
    private static final Logger logger = LogManager.getLogger(AdminPanelBean.class);

    @EJB(beanName = "tariffsLoader")
    private TariffsLoader tariffsLoader;
    private List<TariffsToShow> tariffsToShows;

    @PostConstruct
    public void onCreate() {
        reloadTariffs();
    }

    public void reloadTariffs() {
        List<TariffDTOwOpts> tariffs = tariffsLoader.getTariffs();
        tariffsToShows = new ArrayList<>();
        for(TariffDTOwOpts tariff : tariffs) {
            tariffsToShows.add(new TariffsToShow(true, tariff));
        }
    }

    public List<TariffsToShow> getTariffsToShows() {
        return tariffsToShows;
    }

    public void setTariffsToShows(List<TariffsToShow> tariffsToShows) {
        this.tariffsToShows = tariffsToShows;
    }

    public void sendMessage() {
        logger.info("Set new tariffs to show:" + tariffsToShows);
        tariffsLoader.setTariffsToShow(tariffsToShows);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tariffs updated."));
    }
}
