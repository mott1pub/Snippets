package com.travelport.myContext;

import java.io.File;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JndiContext extends InitialContext {
	
	private static Logger log = LogManager.getLogger(JndiContext.class.getName());
	
	private static String fscontext = 
			"com.sun.jndi.fscontext.RefFSContextFactory";
	private static String url = "file:test/resources";	// to test location for JNDI Structure
	private static String myjndicontext = "myjndicontext";
	
	/**
	 * Create a File System JNDI Context
	 * @param ctx
	 * @throws NamingException
	 */
	public JndiContext() throws NamingException {
		
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, fscontext);
		props.put(Context.PROVIDER_URL, url + File.separatorChar + myjndicontext);
		
		try {
			// Super Class create context in InitialContext
			init(props);			
		} catch (NamingException e) {
			log.error("Is this Directory Created?");
			throw e;
		}

	}

	/**
	 * Create a JDBC SubContext to hold DataSources 
	 * 
	 * @throws NamingException
	 */
	public void createJDBCContext() throws NamingException {
		createSubcontext("jdbc");
	}

	/**
	 * Creates a SubContext if needed..
	 * @param name
	 * @throws NamingException
	 */
	public void createaJNDISubContext(String name) throws NamingException {
		createSubcontext(name);
	}
	
	public NamingEnumeration<NameClassPair> listContextOnly() {
		NamingEnumeration<NameClassPair> alist = null;
		
		try {
			alist = list(url + File.separatorChar + myjndicontext);
		} catch (NamingException e) {
			log.error("", e);
		}
		
		return alist;
	}
}
