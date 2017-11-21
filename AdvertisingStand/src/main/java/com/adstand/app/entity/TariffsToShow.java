package com.adstand.app.entity;

import com.deltacom.dto.TariffDTOwOpts;

public class TariffsToShow {
    private boolean isShown;
    private TariffDTOwOpts tariff;

    public TariffsToShow() {
    }

    public TariffsToShow(boolean isShown, TariffDTOwOpts tariff) {
        this.isShown = isShown;
        this.tariff = tariff;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public TariffDTOwOpts getTariff() {
        return tariff;
    }

    public void setTariff(TariffDTOwOpts tariff) {
        this.tariff = tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TariffsToShow that = (TariffsToShow) o;

        if (isShown != that.isShown) return false;
        return tariff != null ? tariff.equals(that.tariff) : that.tariff == null;
    }

    @Override
    public int hashCode() {
        int result = (isShown ? 1 : 0);
        result = 31 * result + (tariff != null ? tariff.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TariffsToShow{" +
                "isShown=" + isShown +
                ", tariff=" + tariff +
                '}';
    }
}
