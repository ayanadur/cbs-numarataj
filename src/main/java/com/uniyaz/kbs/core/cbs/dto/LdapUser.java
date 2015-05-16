package com.uniyaz.kbs.core.cbs.dto;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LdapUser {
	
	private String givenname;
	private String sn;
	private String mail;
	private boolean isLogged;
	
	public String getGivenname() {
		return givenname;
	}
	
	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}
	
	public String getSn() {
		return sn;
	}
	
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

}
