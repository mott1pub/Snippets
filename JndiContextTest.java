package com.travelport.myContext;


import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Creating a context in a file system means it stays in the file..
 * 
 * @author Monte.Ott
 *
 */
public class JndiContextTest {
	
	private static Logger log = LogManager.getLogger(jndiContextTest.class.getName());
	private JndiContext ctx = null;
	
	@Before
	public void Setup() {
 
		File mypath = new File(".");
		log.info("MyPath:" + mypath.getAbsolutePath());
 
        ClassLoader cl = ClassLoader.getSystemClassLoader();
 
        URL[] urls = ((URLClassLoader)cl).getURLs();
 
        log.info("*** CLASSPATHS ***");
        for(URL url: urls){
        	URI uri = null;
        	log.info("URL From File:" + url.getFile());
			try {
				uri = url.toURI();
			} catch (URISyntaxException e) {
				log.info("", e);
			}

			//mapOfParams.put("<", "PG");
        }	
		
        /* 
         * Create the Context for all of the Class
         */
		try {
			ctx = new JndiContext();
		} catch (NamingException e) {
			log.error("",e);
			fail();
		}	
	}
	
	/**
	 * 
	 */
	@After
	public void TearDown() {
		log.info("Teardowning!");

		// Remove all local context's
		NamingEnumeration<NameClassPair> theList = ctx.listContextOnly();
		
		while (theList.hasMoreElements()) {
			NameClassPair nameClassPair = (NameClassPair) theList.nextElement();
			try {
				String name = nameClassPair.getName();
				log.info("Destroying[" + name + "]");
				ctx.destroySubcontext(name);
			} catch (NamingException e) {
				log.info("Context 'localjava' not found this may be ok!");
			}
		}
		
	}

	@Test
	public void testSubContext() {
		
		try {
			ctx.createaJNDISubContext("localjava");
		} catch (NamingException e) {
			log.error("",e);
			fail();
		}
		
		// Build a generic Context..
		try {
			ctx.lookup("localjava");
		} catch (NamingException e) {
			log.error("", e);
			fail();
		}
		
		
		try {
			ctx.createaJNDISubContext("localajava");
		} catch (NamingException e) {
			log.error("",e);
			fail();
		}
		// Build a generic Context..
		try {
			ctx.lookup("localajava");
		} catch (NamingException e) {
			log.error("", e);
			fail();
		}
	}

	@Test
	public void testJDBCContext() {
		
		JndiContext ctx = null;
		try {
			ctx = new JndiContext();
		} catch (NamingException e) {
			log.error("",e);
			fail();
		}
		
		try {
			ctx.createJDBCContext();
		} catch (NamingException e) {
			log.error("",e);
			fail();
		}
	}
	
	@Test
	public void printJNDI() {
		JndiContext ctx = null;
		try {
			ctx = new JndiContext();
		} catch (NamingException e) {
			log.error("",e);
			fail();
		}
		
		NamingEnumeration<NameClassPair> list;
		try {

			list = ctx.list("");
			
			log.info("Printing JNDI!");

			while (list.hasMore()) {
			 log.info("Name:" + list.next().getName());
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
