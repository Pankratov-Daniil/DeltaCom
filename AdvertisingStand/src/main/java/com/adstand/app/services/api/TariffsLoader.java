package com.adstand.app.services.api;

import javax.ejb.Local;

@Local
public interface TariffsLoader {
    public void getTariffsFromServer();
    public String getTariffsStr();
}
