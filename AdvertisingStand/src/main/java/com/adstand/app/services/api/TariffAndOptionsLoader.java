package com.adstand.app.services.api;

import javax.ejb.Local;

@Local
public interface TariffAndOptionsLoader {
    public void getTariffsAndOptions();
}
