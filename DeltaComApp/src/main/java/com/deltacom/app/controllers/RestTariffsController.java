package com.deltacom.app.controllers;

import com.deltacom.app.entities.Tariff;
import com.deltacom.app.utils.DTOConverter;
import com.deltacom.app.services.api.TariffService;
import com.deltacom.dto.TariffDTOwOpts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for processing requests from rest client
 */
@RestController
public class RestTariffsController {
    @Autowired
    TariffService tariffService;

    /**
     * Processing request for getting tariffs for stand
     * @return list of tariffs DTOs
     */
    @RequestMapping("/getTariffsForStand")
    public List<TariffDTOwOpts> getTariffsForStand() {
        List<Tariff> tariffs = tariffService.getAllTariffs();
        List<TariffDTOwOpts> dtos = new ArrayList<>();
        for(Tariff tariff : tariffs) {
            dtos.add(DTOConverter.TariffToTariffDTOwOpts(tariff));
        }
        return dtos;
    }
}
