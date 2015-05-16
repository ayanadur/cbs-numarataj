package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsSokakKaplamaTuru implements BaseEnum {

    PARKE("Parke"),
    ASFALT("Asfalt"),
    STABLIZE("Stablize"),
    TOPRAK("Toprak"),
    CAKIL("Çakıl"),
    BETON("Beton"),
    TAS("Taş Döşeme"),
    DIGER("Diğer");

    EnumCbsSokakKaplamaTuru(String kaplamaTuru) {
        this.kaplamaTuru = kaplamaTuru;
    }

    private final String kaplamaTuru;

    public String getKaplamaTuru() {
        return kaplamaTuru;
    }

    @Override
    public String getDisplayValue() {
        return this.kaplamaTuru;
    }

    @Override
    public String toString() {
        return this.kaplamaTuru;
    }
}
