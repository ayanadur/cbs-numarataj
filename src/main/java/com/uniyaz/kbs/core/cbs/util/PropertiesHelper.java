package com.uniyaz.kbs.core.cbs.util;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.jboss.logging.Logger;

public final class PropertiesHelper {
	
	private static Logger log = Logger.getLogger(PropertiesHelper.class);
	private static String docBase;
	private static String ldapUrl;
	private static String ldapDc;
	private static String ldapSearchDc;
	
	static {
		log.info("PropertiesHelper static ...");
		Properties properties=new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResource("application.properties").openStream());
			
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			
			if (OS.indexOf("win") >= 0) {
				setDocBase(properties.getProperty("docBaseW"));
			}else{
				setDocBase(properties.getProperty("docBaseU"));
			}
			
			setLdapUrl(properties.getProperty("ldapUrl"));
			setLdapDc(properties.getProperty("ldapDc"));
			setLdapSearchDc(properties.getProperty("ldapSearchDc"));
			
		}catch(IOException ex) {
			
		}
		
	}

	public static String getDocBase() {
		return docBase;
	}

	public static void setDocBase(String docBase) {
		PropertiesHelper.docBase = docBase;
	}

	public static String getLdapUrl() {
		return ldapUrl;
	}

	public static void setLdapUrl(String ldapUrl) {
		PropertiesHelper.ldapUrl = ldapUrl;
	}

	public static String getLdapDc() {
		return ldapDc;
	}

	public static void setLdapDc(String ldapDc) {
		PropertiesHelper.ldapDc = ldapDc;
	}

	public static String getLdapSearchDc() {
		return ldapSearchDc;
	}

	public static void setLdapSearchDc(String ldapSearchDc) {
		PropertiesHelper.ldapSearchDc = ldapSearchDc;
	}
	
}
