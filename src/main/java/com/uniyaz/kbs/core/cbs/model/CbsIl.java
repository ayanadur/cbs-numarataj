package com.uniyaz.kbs.core.cbs.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

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
@Table(name="CBS_IL")
public class CbsIl implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "CBS_IL_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private Long id;

    @Version
    private Long version;

    @Size(max = 50)
    @Column(length = 50)
    private String adi;

    @Column
    @Digits(integer = 11, fraction = 0)
    private Long stNo;  

    @Column
    @Digits(integer = 11, fraction = 0)
    private Long uavtNo;    
    
    @Index(name = "IX_CBS_IL_ULKE")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CBS_ULKE", nullable = true)
    @ForeignKey(name = "FK_CBS_IL_ULKE")
    private CbsUlke cbsUlke;    

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

    public CbsUlke getCbsUlke() {
        return cbsUlke;
    }

    public void setCbsUlke(CbsUlke cbsUlke) {
        this.cbsUlke = cbsUlke;
    }

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

}
