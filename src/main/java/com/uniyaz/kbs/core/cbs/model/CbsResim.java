package com.uniyaz.kbs.core.cbs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Created by Ali on 15.2.2015.
 */

@Entity
@XmlRootElement
@Table(name="CBS_RESIM")
public class CbsResim implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "CBS_RESIM_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private Long id;

    @Version
    private Long version;
      
	/**
	 * Bağlı olduğu bina
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CBS_BINA", nullable = false)
	@ForeignKey(name = "FK_CBS_BINA_RESIM")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private CbsBina cbsBina;    
   
    @Column(length = 25)
    private String turu;
    
    @Column
    private String dosyaAdi;
    
    @Column
    private String path;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date kayitTarihi;
    
    @Lob
    private byte[] blobDosya;

	public CbsBina getCbsBina() {
		return cbsBina;
	}

	public void setCbsBina(CbsBina cbsBina) {
		this.cbsBina = cbsBina;
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

	public String getTuru() {
		return turu;
	}

	public void setTuru(String turu) {
		this.turu = turu;
	}

	public String getDosyaAdi() {
		return dosyaAdi; 
	}

	public void setDosyaAdi(String dosyaAdi) {
		this.dosyaAdi = dosyaAdi;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getKayitTarihi() {
		return kayitTarihi;
	}

	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}

	public byte[] getBlobDosya() {
		return blobDosya;
	}

	public void setBlobDosya(byte[] blobDosya) {
		this.blobDosya = blobDosya;
	}
    
}
