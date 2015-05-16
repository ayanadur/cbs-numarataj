package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsBinaZeminKullanimTuru implements BaseEnum {
    TICARET("Ticaret"),
    KONUT("Konut"),
    DEPO("Depo"),
    RESMI_KURUM("Resmi Kurum"),
    EGITIM("Eğitim"),
    SAGLIK("Sağlık"),
    DINI("Dini"),
    ENDUSTRI("Endüstri"),
    VAKIF("Vakıf / Dernek"),
    MUZE("Müze"),
    SOSYO_KULTUREL("Sosyo Kültürel"),
    BOS("Boş");

    EnumCbsBinaZeminKullanimTuru(String turu) {
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
