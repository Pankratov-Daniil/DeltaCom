package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Tariff;

/**
 * SqlRepository for Tariff entity
 */
public class TariffSqlRepository extends SqlRepository {
    public TariffSqlRepository() {
        super(Tariff.class);
    }
}
