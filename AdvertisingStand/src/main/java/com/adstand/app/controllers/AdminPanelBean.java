package com.adstand.app.controllers;

import com.adstand.app.entity.TariffsToShow;
import com.adstand.app.services.TariffsLoader;
import com.deltacom.dto.TariffDTOwOpts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin bean for JSF pages
 */
@ManagedBean(name = "adminPanelBean")
@SessionScoped
public class AdminPanelBean {
    private static final Logger logger = LogManager.getLogger(AdminPanelBean.class);

    @EJB(beanName = "tariffsLoader")
    private TariffsLoader tariffsLoader;
    private List<TariffsToShow> tariffsToShows;

    /**
     * Load tariffs on bean create
     */
    @PostConstruct
    public void onCreate() {
        reloadTariffs();
    }

    /**
     * Loads tariffs from tariffsLoader
     */
    private void reloadTariffs() {
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

    /**
     * Send message with new tariffs to advertising stand
     */
    public void sendMessage() {
        logger.info("Set new tariffs to show:" + tariffsToShows);
        tariffsLoader.setTariffsToShow(tariffsToShows);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tariffs updated."));
    }
}
