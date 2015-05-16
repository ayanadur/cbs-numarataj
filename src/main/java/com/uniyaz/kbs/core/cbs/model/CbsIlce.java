package com.uniyaz.kbs.core.cbs.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * CbsIlce
 */
@Entity
@XmlRootElement
@Table(name="CBS_ILCE")
public class CbsIlce  implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
  
	@Id
 	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "CBS_ILCE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")  
    private Long id;
 	
    @Size(max = 50)
    @Column(length = 50)
    private String adi;
 	
    @Version
    private Long version;
 	   
    @Index(name = "IX_CBS_ILCE_IL")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_CBS_IL", nullable=false)
    @ForeignKey(name = "FK_CBS_ILCE_IL")
    private CbsIl cbsIl;
    
    @Column
    @Digits(integer = 11, fraction = 0)
    private Long stNo;  

    @Column
    @Digits(integer = 11, fraction = 0)
    private Long uavtNo; 
    
    //getter & setter
 	public Long getId() {
        return id;
    }   
    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }
    
    public void setAdi(String adi) {
        this.adi = adi;
    }

    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }

    public CbsIl getCbsIl() {
        return cbsIl;
    }

    public void setCbsIl(CbsIl cbsIl) {
        this.cbsIl = cbsIl;
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

}


