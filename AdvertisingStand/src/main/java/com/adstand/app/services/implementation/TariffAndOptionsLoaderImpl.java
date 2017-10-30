package com.adstand.app.services.implementation;

import com.adstand.app.entities.OptionDTO;
import com.adstand.app.entities.TariffDTO;
import com.adstand.app.services.api.TariffAndOptionsLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import java.util.List;

@Stateful(name = "tariffAndOptionsLoader")
public class TariffAndOptionsLoaderImpl implements TariffAndOptionsLoader {
    private static final Logger logger = LogManager.getLogger(TariffAndOptionsLoader.class);
    private List<TariffDTO> tariffs;
    private List<OptionDTO> option;

    @PostConstruct
    private void onBeanCreate() {
        getTariffsAndOptions();
    }

    @Override
    public void getTariffsAndOptions() {
        logger.info("TARIFFS");
        System.out.println("Getting tariffs and options!");
    }
}
