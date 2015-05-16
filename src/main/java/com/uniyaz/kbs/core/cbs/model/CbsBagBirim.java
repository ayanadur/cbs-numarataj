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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import com.uniyaz.kbs.core.cbs.enums.EnumCbsBagbirimKapiTuru;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsBagbirimKullanimTuru;

/**
 * Created by Ali on 15.2.2015.
 */

@Entity
@XmlRootElement
@Table(name="CBS_BAGBIRIM")
public class CbsBagBirim implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "CBS_BAGBIRIM_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    @Version
    private Long version;
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
    /**
     * İç Kapı No
     */
    @Size(max = 10)
    @Column(length = 10)
    private String icKapiNo;
	public String getIcKapiNo() {
		return icKapiNo;
	}
	public void setIcKapiNo(String icKapiNo) {
		this.icKapiNo = icKapiNo;
	}
	
    /**
     * Tapu Bağımsız Bölüm No
     */
    @Size(max = 10)
    @Column(length = 10)
    private String tapuBagimsizBolumNo;
	public String getTapuBagimsizBolumNo() {
		return tapuBagimsizBolumNo;
	}
	public void setTapuBagimsizBolumNo(String tapuBagimsizBolumNo) {
		this.tapuBagimsizBolumNo = tapuBagimsizBolumNo;
	}
	
    /**
     * Kullanım Türü
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBagbirimKullanimTuru kullanimTuru;
	public EnumCbsBagbirimKullanimTuru getKullanimTuru() {
		return kullanimTuru;
	}
	public void setKullanimTuru(EnumCbsBagbirimKullanimTuru kullanimTuru) {
		this.kullanimTuru = kullanimTuru;
	}
	
    /**
     * Kapı Türü
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsBagbirimKapiTuru kapiTuru;
	public EnumCbsBagbirimKapiTuru getKapiTuru() {
		return kapiTuru;
	}
	public void setKapiTuru(EnumCbsBagbirimKapiTuru kapiTuru) {
		this.kapiTuru = kapiTuru;
	}
	
    /**
     * Kat No
     */
    @Column
    private Long katNo;
	public Long getKatNo() {
		return katNo;
	}
	public void setKatNo(Long katNo) {
		this.katNo = katNo;
	} 
	
    /**
     * Adres No
     */
    @Column
    private Long adresNo;
	public Long getAdresNo() {
		return adresNo;
	}
	public void setAdresNo(Long adresNo) {
		this.adresNo = adresNo;
	} 
	
    /**
     * Bağlı olduğu bina
     */
    @Index(name = "IX_CBS_BAGBIRIM_BINA")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CBS_BINA", nullable = false)
    @ForeignKey(name = "FX_CBS_BAGBIRIM_BINA")
    private CbsBina cbsBina;
	public CbsBina getCbsBina() {
		return cbsBina;
	}
	public void setCbsBina(CbsBina cbsBina) {
		this.cbsBina = cbsBina;
	}

}
