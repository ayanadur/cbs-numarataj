package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsBinaCepheMalzemesi implements BaseEnum{
    BOYALI("Boyalı"),
    CAM("Cam"),
    SIVALI("Sıvalı"),
    SIVASIZ("Sıvasız"),
    MOZAIK("Mozaik"),
    TAS_KAPLAMA("Taş Kaplama");

    EnumCbsBinaCepheMalzemesi(String turu) {
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
