package com.adstand.app.services.api;

import com.deltacom.dto.TariffDTOwOpts;

import javax.ejb.Local;
import java.util.List;

/**
 * Class for loading tariffs from DeltaCom server
 */
@Local
public interface TariffsLoader {
    public void getTariffsFromServer();
    public List<TariffDTOwOpts> getTariffs();
    public int getDataVersion();
}
