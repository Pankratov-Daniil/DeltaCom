package com.adstand.app.services.api;

import com.deltacom.dto.TariffDTOwOpts;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TariffsLoader {
    public void getTariffsFromServer();
    public List<TariffDTOwOpts> getTariffs();
    public int getDataVersion();
}
