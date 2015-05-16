package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsBagbirimKullanimTuru implements BaseEnum  {

    TICARET("Ticaret"),
    KONUT("Konut"),
    DEPO("Depo"),
    RESMI_KURUM("Resmi Kurum"),
    EGITIM("Eğitim"),
    SAGLIK("Sağlık"),
    DINI("Dini"),
    ENDUSTRI("Endüstri"),
    DERNEK("Vakıf / Dernek"),
    MUZE("Müze"),
    SOSYO_KULTUREL("Sosyo Kültürel"),
    BOS("Boş");

    EnumCbsBagbirimKullanimTuru(String turu) {
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
