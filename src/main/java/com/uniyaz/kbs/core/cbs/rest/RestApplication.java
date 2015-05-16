package com.uniyaz.kbs.core.cbs.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.logging.Logger;

@ApplicationPath("/rest")
public class RestApplication extends Application
{
	 Logger log = Logger.getLogger(RestApplication.class);
	 
	public RestApplication() {
		log.info("RestApplication ...");
	}
}