package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsBinaKapiTuru implements BaseEnum {

    ANA("Ana Giriş"),
    TALI("Tali Giriş"),
    TAHSIS("Tahsisli Arsa / Parsel"),
    DIGER("Diğer");

    EnumCbsBinaKapiTuru(String turu) {
        this.turu = turu;
    }

    private final String turu;

    public String getTuru() {
        return turu;
    }

    @Override
    public String getDisplayValue() {
        return this.turu;
    }

    @Override
    public String toString() {
        return this.turu;
    }
}
