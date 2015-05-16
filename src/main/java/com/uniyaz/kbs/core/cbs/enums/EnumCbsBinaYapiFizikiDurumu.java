package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsBinaYapiFizikiDurumu implements BaseEnum {
    IYI("İyi"),
    ORTA("Orta"),
    HARAP("Harap");

    EnumCbsBinaYapiFizikiDurumu(String turu) {
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
