package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsBagbirimKapiTuru implements BaseEnum {

    ANA("Bağımsız Bölüm Ana Giriş"),
    TALI("Bağımsız Bölüm Tali Giriş"),
    DIGER("Diğer");

    EnumCbsBagbirimKapiTuru(String turu) {
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
