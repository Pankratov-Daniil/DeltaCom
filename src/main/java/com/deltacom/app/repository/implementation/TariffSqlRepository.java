package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Tariff;
import org.springframework.stereotype.Repository;

/**
 * SqlRepository for Tariff entity
 */
@Repository("Tariff")
public class TariffSqlRepository extends SqlRepository {
    public TariffSqlRepository() {
        super(Tariff.class);
    }
}
