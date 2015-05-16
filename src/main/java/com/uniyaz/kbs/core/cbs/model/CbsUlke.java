package com.uniyaz.kbs.core.cbs.model;
// Generated Feb 18, 2015 11:23:28 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="CBS_ULKE")
public class CbsUlke  implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "CBS_ULKE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	private Long id;
	
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }	
	
    @Version
    private Long version;
    
    public Long getVersion() {
        return this.version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }
    
     @Size(max = 50)
     @Column(length=50)
     private String adi;
     
     public String getAdi() {
         return this.adi;
     }
     
     public void setAdi(String adi) {
         this.adi = adi;
     }
     
     @Column(precision = 15, scale = 0)
     private Long stNo;
     
     public Long getStNo() {
         return this.stNo;
     }
     
     public void setStNo(Long stNo) {
         this.stNo = stNo;
     }
     
     @Column(precision = 15, scale = 0)
     private Long uavtNo;
     
     public Long getUavtNo() {
         return this.uavtNo;
     }
     
     public void setUavtNo(Long uavtNo) {
         this.uavtNo = uavtNo;
     }
        
}


