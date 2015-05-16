package com.uniyaz.kbs.core.cbs.service;

import java.util.Hashtable;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.uniyaz.kbs.core.cbs.dto.UserDto;
import com.uniyaz.kbs.core.cbs.dto.LdapUser;
import com.uniyaz.kbs.core.cbs.util.PropertiesHelper;

@Stateless
@Path("/api/authenticate")
public class AuthenticationService {
	
	private static Logger log = Logger.getLogger(AuthenticationService.class);
	
	private static String ldapUrl = PropertiesHelper.getLdapUrl();
	private static String ldapDc = PropertiesHelper.getLdapDc();
	private static String ldapSearchDc = PropertiesHelper.getLdapSearchDc();
	
	@POST
	@Consumes("application/json")
	public Response getUserByName(UserDto entity) {
		
		log.info("application.properties : " + ldapUrl + " " + ldapDc);
		
		InitialDirContext ctx = getLdapContext(ldapUrl, entity.getUsername(), entity.getPassword());
	      if (ctx == null)
	      {
	    	  log.info("ctx null");;
	         return Response.status(Status.NOT_FOUND).build();
	      }
	      
		SearchControls searchControls = getSearchControls();
		
		LdapUser user = getUserInfo(entity.getUsername(), ctx, searchControls);
		
		if (!user.isLogged())
		{
			return Response.status(Status.NOT_FOUND).build();
		}
	      
	    try {
	    	if(ctx != null)    ctx.close();
	    }catch (Exception e) {
	    	  
	    }
        
		return Response.ok(user).build();
	}
	
	private static InitialDirContext getLdapContext(String url, String userName, String userPassword) {
		InitialDirContext ctx = null;
		 try {
			 Hashtable<String, String> env = new Hashtable<String, String>();
			 env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			 env.put(Context.SECURITY_AUTHENTICATION, "Simple");
			 env.put(Context.SECURITY_PRINCIPAL, ldapDc + "\\" + userName);
			 env.put(Context.SECURITY_CREDENTIALS, userPassword);
			 env.put(Context.PROVIDER_URL, url);
			 ctx = new InitialLdapContext(env, null);
			 System.out.println("LDAP Connection: COMPLETE");
		 } catch (NamingException nex) {
			 System.out.println("LDAP Connection: FAILED");
		 }
		 	return ctx;
	} 
	
	 private static SearchControls getSearchControls() {
		 SearchControls cons = new SearchControls();
		 cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
		 String[] attrIDs = {"distinguishedName", "sn", "givenname", "mail", "telephonenumber", "thumbnailPhoto"};
		 cons.setReturningAttributes(attrIDs);
		 return cons;
	 } 

	 private static LdapUser getUserInfo(String userName, InitialDirContext ctx, SearchControls searchControls) {
		
		 LdapUser ldapUser = new LdapUser();
		 ldapUser.setLogged(false);
		 
		 try {
			 
			 NamingEnumeration<SearchResult> answer = ctx.search(ldapSearchDc, "sAMAccountName=" + userName, searchControls);
			 
			 if (answer.hasMore()) {
				 
				 Attributes attrs = answer.next().getAttributes();
				 
				 /*
				 System.out.println(attrs.get("distinguishedName"));
				 System.out.println(attrs.get("givenname"));
				 System.out.println(attrs.get("sn"));
				 System.out.println(attrs.get("mail"));
				 System.out.println(attrs.get("telephonenumber"));
				 */
				 
				 String ls_givenname = (attrs.get("givenname") == null ? "-" : attrs.get("givenname").get().toString());
				 String ls_sn = (attrs.get("sn") == null ? "-" : attrs.get("sn").get().toString());
				 String ls_mail = (attrs.get("mail") == null ? "-" : attrs.get("mail").get().toString());
				 
				 ldapUser.setGivenname(ls_givenname);
				 ldapUser.setSn(ls_sn);
				 ldapUser.setMail(ls_mail);
				 
				 //byte[] photo = (byte[])attrs.get("thumbnailPhoto").get();
				 //savePhoto(userName, photo);
				 
				 ldapUser.setLogged(true);
				 
				 
			 } else {
				 System.out.println("user not found.");
			 }
			 
		 } catch (Exception ex) {
			 System.out.println("user not found.");
		 }
		 
		 return ldapUser;
	}

}
