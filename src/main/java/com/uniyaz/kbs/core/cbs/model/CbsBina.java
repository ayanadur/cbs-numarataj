package com.uniyaz.kbs.core.cbs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaAsansor;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaCatiDurumu;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaCepheMalzemesi;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaKapiTuru;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaOtoparkTuru;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaSiginak;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaYanginMerdiveni;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaYapiDurumu;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaYapiFizikiDurumu;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaYapiTipi;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBinaZeminKullanimTuru;

@Entity
@XmlRootElement
@Table(name="CBS_BINA")
public class CbsBina implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "CBS_BINA_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private Long id;

    @Version
    private Long version;

    @Size(max = 50)
    @Column(length = 50)
    private String binaAdi;

    /**
     * Tuik No
     */
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long stNo;

    /**
     * Uavt No
     */
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long uavtNo;

    /**
     * Dış Kapı Türü
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaKapiTuru kapiTuru;

    /**
     * Dış Kapı No
     */
    @Size(max = 16)
    @Column(length = 16)
    private String kapiNo;

    /**
     * Dış Kapı No Diğer
     */
    @Size(max = 10)
    @Column(length = 10)
    private String kapiNoDiger;

    @Column
    @Digits(integer = 14, fraction = 0)
    private Long formNo;

    /**
     * Zemin Kullanım Durumu
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaZeminKullanimTuru zeminKullanimTuru;

    /**
     * Yapı Tipi
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaYapiTipi yapiTipi;

    /**
     * Yapı Durumu
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaYapiDurumu yapiDurumu;

    /**
     * Yapı Fiziki Durumu
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaYapiFizikiDurumu yapiFizikiDurumu;

    /**
     * Kat Adedi
     */
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long katSayisi;

    /**
     * Yol Altı Kat Sayısı
     */
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long yolAltiKatSayisi;

    /**
     * Yol Üstü Kat Sayısı
     */
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long yolUstuKatSayisi;

    /**
     * Bağımsız Bölüm Sayısı
     */
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long bagimsizBolumSayisi;

    /**
     * Çatı Durumu
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaCatiDurumu catiDurumu;

    /**
     * Cephe malzemesi
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaCepheMalzemesi cepheMalzemesi;

    /**
     * Otopark Tipi
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaOtoparkTuru otoparkTuru;

    /**
     * Asansör
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaAsansor asansor;

    /**
     * Asansör Sayısı
     */
    @Column
    @Digits(integer = 2, fraction = 0)
    private Long asansorSayisi;

    /**
     * Yangın Merdiveni
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaYanginMerdiveni yanginMerdiveni;

    /**
     * Sığınak
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBinaSiginak siginak;

    /**
     * Bağlı olduğu sokak
     */
    @Index(name = "IX_CBS_BINA_SOKAK")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CBS_SOKAK", nullable = false)
    @ForeignKey(name = "FX_CBS_BINA_SOKAK")
    private CbsSokak cbsSokak;
    
	@Size(max = 50)
    @Column(length = 50)
    private String siteAdi;
    
    @Size(max = 50)
    @Column(length = 50)
    private String blokAdi;
    
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long csbmKodu;
    
    @Column
    @Digits(integer = 14, fraction = 0)
    private Long esBinaKimlikNo;

    @Column
    @Digits(integer = 14, fraction = 0)
    private Long esBinaKodu;
    
    @Column(length = 250)
    private String aciklama;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public Long getStNo() {
		return stNo;
	}

	public void setStNo(Long stNo) {
		this.stNo = stNo;
	}

	public Long getUavtNo() {
		return uavtNo;
	}

	public void setUavtNo(Long uavtNo) {
		this.uavtNo = uavtNo;
	}

	public EnumCbsBinaKapiTuru getKapiTuru() {
		return kapiTuru;
	}

	public void setKapiTuru(EnumCbsBinaKapiTuru kapiTuru) {
		this.kapiTuru = kapiTuru;
	}

	public String getKapiNo() {
		return kapiNo;
	}

	public void setKapiNo(String kapiNo) {
		this.kapiNo = kapiNo;
	}

	public String getKapiNoDiger() {
		return kapiNoDiger;
	}

	public void setKapiNoDiger(String kapiNoDiger) {
		this.kapiNoDiger = kapiNoDiger;
	}

	public Long getFormNo() {
		return formNo;
	}

	public void setFormNo(Long formNo) {
		this.formNo = formNo;
	}

	public EnumCbsBinaZeminKullanimTuru getZeminKullanimTuru() {
		return zeminKullanimTuru;
	}

	public void setZeminKullanimTuru(EnumCbsBinaZeminKullanimTuru zeminKullanimTuru) {
		this.zeminKullanimTuru = zeminKullanimTuru;
	}

	public EnumCbsBinaYapiTipi getYapiTipi() {
		return yapiTipi;
	}

	public void setYapiTipi(EnumCbsBinaYapiTipi yapiTipi) {
		this.yapiTipi = yapiTipi;
	}

	public EnumCbsBinaYapiDurumu getYapiDurumu() {
		return yapiDurumu;
	}

	public void setYapiDurumu(EnumCbsBinaYapiDurumu yapiDurumu) {
		this.yapiDurumu = yapiDurumu;
	}

	public EnumCbsBinaYapiFizikiDurumu getYapiFizikiDurumu() {
		return yapiFizikiDurumu;
	}

	public void setYapiFizikiDurumu(EnumCbsBinaYapiFizikiDurumu yapiFizikiDurumu) {
		this.yapiFizikiDurumu = yapiFizikiDurumu;
	}

	public Long getYolAltiKatSayisi() {
		return yolAltiKatSayisi;
	}

	public void setYolAltiKatSayisi(Long yolAltiKaySayisi) {
		this.yolAltiKatSayisi = yolAltiKaySayisi;
	}

	public Long getYolUstuKatSayisi() {
		return yolUstuKatSayisi;
	}

	public void setYolUstuKatSayisi(Long yolUstuKatSayisi) {
		this.yolUstuKatSayisi = yolUstuKatSayisi;
	}

	public Long getBagimsizBolumSayisi() {
		return bagimsizBolumSayisi;
	}

	public void setBagimsizBolumSayisi(Long bagimsizBolumSayisi) {
		this.bagimsizBolumSayisi = bagimsizBolumSayisi;
	}

	public EnumCbsBinaCatiDurumu getCatiDurumu() {
		return catiDurumu;
	}

	public void setCatiDurumu(EnumCbsBinaCatiDurumu catiDurumu) {
		this.catiDurumu = catiDurumu;
	}

	public EnumCbsBinaCepheMalzemesi getCepheMalzemesi() {
		return cepheMalzemesi;
	}

	public void setCepheMalzemesi(EnumCbsBinaCepheMalzemesi cepheMalzemesi) {
		this.cepheMalzemesi = cepheMalzemesi;
	}

	public EnumCbsBinaOtoparkTuru getOtoparkTuru() {
		return otoparkTuru;
	}

	public void setOtoparkTuru(EnumCbsBinaOtoparkTuru otoparkTuru) {
		this.otoparkTuru = otoparkTuru;
	}

	public EnumCbsBinaAsansor getAsansor() {
		return asansor;
	}

	public void setAsansor(EnumCbsBinaAsansor asansor) {
		this.asansor = asansor;
	}

	public Long getAsansorSayisi() {
		return asansorSayisi;
	}

	public void setAsansorSayisi(Long asansorSayisi) {
		this.asansorSayisi = asansorSayisi;
	}

	public EnumCbsBinaYanginMerdiveni getYanginMerdiveni() {
		return yanginMerdiveni;
	}

	public void setYanginMerdiveni(EnumCbsBinaYanginMerdiveni yanginMerdiveni) {
		this.yanginMerdiveni = yanginMerdiveni;
	}

	public EnumCbsBinaSiginak getSiginak() {
		return siginak;
	}

	public void setSiginak(EnumCbsBinaSiginak siginak) {
		this.siginak = siginak;
	}

	public CbsSokak getCbsSokak() {
		return cbsSokak;
	}

	public void setCbsSokak(CbsSokak cbsSokak) {
		this.cbsSokak = cbsSokak;
	}

	public Long getKatSayisi() {
		return katSayisi;
	}

	public void setKatSayisi(Long katSayisi) {
		this.katSayisi = katSayisi;
	}

	public String getSiteAdi() {
		return siteAdi;
	}

	public void setSiteAdi(String siteAdi) {
		this.siteAdi = siteAdi;
	}

	public String getBlokAdi() {
		return blokAdi;
	}

	public void setBlokAdi(String blokAdi) {
		this.blokAdi = blokAdi;
	}

	public Long getCsbmKodu() {
		return csbmKodu;
	}

	public void setCsbmKodu(Long csbmKodu) {
		this.csbmKodu = csbmKodu;
	}

	public Long getEsBinaKimlikNo() {
		return esBinaKimlikNo;
	}

	public void setEsBinaKimlikNo(Long esBinaKimlikNo) {
		this.esBinaKimlikNo = esBinaKimlikNo;
	}

	public Long getEsBinaKodu() {
		return esBinaKodu;
	}

	public void setEsBinaKodu(Long esBinaKodu) {
		this.esBinaKodu = esBinaKodu;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}


}

