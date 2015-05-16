package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsSokakTuru implements BaseEnum {
    CADDE("Cadde"),
    SOKAK("Sokak"),
    MEYDAN("Meydan"),
    BULVAR("Bulvar"),
    KUMEEVLER("Küme Evler"),
    KOYSOKAGI("Köy Sokağı");

    EnumCbsSokakTuru(String turu) {
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
