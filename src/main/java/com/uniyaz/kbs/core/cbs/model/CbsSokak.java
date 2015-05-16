package com.uniyaz.kbs.core.cbs.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import com.uniyaz.kbs.core.cbs.enums.EnumCbsSokakKaplamaTuru;
import com.uniyaz.kbs.core.cbs.enums.EnumCbsSokakTuru;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * Created by Ali on 15.2.2015.
 */
@Entity
@XmlRootElement
@Table(name="CBS_SOKAK")
public class CbsSokak implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "CBS_SOKAK_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private Long id;

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

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public EnumCbsSokakTuru getTuru() {
        return turu;
    }

    public void setTuru(EnumCbsSokakTuru turu) {
        this.turu = turu;
    }

    public EnumCbsSokakKaplamaTuru getKaplamaTuru() {
        return kaplamaTuru;
    }

    public void setKaplamaTuru(EnumCbsSokakKaplamaTuru kaplamaTuru) {
        this.kaplamaTuru = kaplamaTuru;
    }

    public String getStNo() {
        return stNo;
    }

    public void setStNo(String stNo) {
        this.stNo = stNo;
    }

    public Long getUavtNo() {
        return uavtNo;
    }

    public void setUavtNo(Long uavtNo) {
        this.uavtNo = uavtNo;
    }

    public CbsMahalle getCbsMahalle() {
        return cbsMahalle;
    }

    public void setCbsMahalle(CbsMahalle cbsMahalle) {
        this.cbsMahalle = cbsMahalle;
    }

    @Version
    private Long version;

    @Size(max = 50)
    @Column(length = 50)
    private String adi;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsSokakTuru turu;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EnumCbsSokakKaplamaTuru kaplamaTuru;

    @Size(max = 16)
    @Column(length = 16)
    private String stNo;

    @Column
    @Digits(integer = 16, fraction = 0)
    private Long uavtNo;

    @Index(name = "IX_CBS_SOKAK_MAHALLE")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CBS_MAHALLE", nullable = false)
    @ForeignKey(name = "FX_CBS_SOKAK_MAHALLE")
    private CbsMahalle cbsMahalle;
}

