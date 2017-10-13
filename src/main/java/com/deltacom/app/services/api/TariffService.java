package com.deltacom.app.services.api;

import com.deltacom.app.entities.Tariff;

import java.util.List;

/**
 * Interface for Tariff service.
 */
public interface TariffService {
    public List<Tariff> getAllTariffs();
    public Tariff getTariffById(Integer id);
    public void createTariff(Tariff tariff, String[] tariffOptionsIds);
    public void deleteTariff(int id);
}
