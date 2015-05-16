package com.uniyaz.kbs.core.cbs.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadForm {

	public FileUploadForm() {
    }
 
    private byte[] fileData;
    
    private String fileName;
    private Long binaId;
    private String turu;
    private String path;
    private Date kayitTarihi;
    
    public String getFileName() {
        return fileName;
    }
 
    @FormParam("dosya-adi")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public byte[] getFileData() {
        return fileData;
    }
 
    @FormParam("upload-file")
    @PartType("application/octet-stream")
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

	public Long getBinaId() {
		return binaId;
	}

	@FormParam("bina-id")
	public void setBinaId(Long binaId) {
		this.binaId = binaId;
	}

	public String getTuru() {
		return turu;
	}

	@FormParam("dosya-turu")
	public void setTuru(String turu) {
		this.turu = turu;
	}

	public String getPath() {
		return path;
	}

	@FormParam("dosya-path")
	public void setPath(String path) {
		this.path = path;
	}

	public Date getKayitTarihi() {
		return kayitTarihi;
	}

	@FormParam("kayit-tarihi")
	public void setKayitTarihi(String kayitTarihi) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			this.kayitTarihi = df.parse(kayitTarihi);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
