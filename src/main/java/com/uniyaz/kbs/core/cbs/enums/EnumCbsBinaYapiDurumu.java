package com.uniyaz.kbs.core.cbs.enums;

/**
 * Created by Ali on 15.2.2015.
 */
public enum EnumCbsBinaYapiDurumu implements BaseEnum {

    KULLANIMDA("Kullanımda"),
    KULLANIM_DISI("Kullanım Dışı"),
    YAPIM_ASAMASI("Yapım Aşaması"),
    YIKILMIS("Yıkılmış");

    EnumCbsBinaYapiDurumu(String turu) {
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
