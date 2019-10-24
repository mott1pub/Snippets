package com.travelport;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.VersionInfo;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class httpComp {

	private static Logger log = Logger.getLogger(httpComp.class);
	
	@BeforeClass
	public static void setup() {
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
       	
        }
        log.info("*** END CLASSPATHS ***");
        
        VersionInfo vi = VersionInfo.loadVersionInfo("org.apache.http", httpComp.class.getClass().getClassLoader()); 
        String version = vi.getRelease();
        log.info("Version:"+ version);
	}

	@Test
	public void testClient() {
		
		// PoolingClientConnectionManager mngr = new PoolingClientConnectionManager();
		// ThreadSafeClientConnManager mngr = new ThreadSafeClientConnManager(null, null);
		
        DefaultHttpClient httpclient = new DefaultHttpClient();

        // HttpGet httpget = null;
        HttpPost httpget = null;
		// Version 4.0-alpha1.jar
        // try {
			httpget = new HttpPost("http://localhost:6575/examples/index.html");
		//} catch (URISyntaxException e3) {
		//	log.error("", e3);
		//}
			
        HttpResponse response = null;
        try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e3) {
			// TODO Auto-generated catch block
			log.error("", e3);
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			log.error("", e3);
		}
		// Version 4.0.1 alpha
        // try {
		//	response = httpclient.execute(httpget);
		//} catch (HttpException e2) {
		//	log.error("", e2);
		//} catch (IOException e2) {
		//	log.error("", e2);
		//}
        HttpEntity entity = response.getEntity();

        log.info("Login form get: " + response.getStatusLine());
        if (entity != null) {
            try {
				entity.consumeContent();
			} catch (IOException e) {
				log.error("", e);
			}
        }
        log.info("Initial set of cookies:");
        List<String> cookies = httpclient.getCookieSpecs().getSpecNames();
        if (cookies.isEmpty()) {
            log.warn("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                log.info("- " + cookies.get(i));
            }
        }

        HttpPost httpost = null;
		httpost = new HttpPost("http://localhost:8080/examples/index.html");

        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("IDToken1", "username"));
        nvps.add(new BasicNameValuePair("IDToken2", "password"));
        
        //4.0.1 NameValuePair[] nvp;
        //4.0.1 nvp = nvps.toArray(new NameValuePair[nvps.size()]);
        
        UrlEncodedFormEntity uefe = null;
		try {
			uefe = new UrlEncodedFormEntity( nvps );
		} catch (UnsupportedEncodingException e1) {
			log.info("", e1);
		}
        
        httpost.setEntity(uefe);

        try {
			response = httpclient.execute(httpost);
		} catch (IOException e) {
			log.error("", e);
		}
        entity = response.getEntity();

        log.info("Login form get: " + response.getStatusLine());
        if (entity != null) {
            try {
				entity.consumeContent();
			} catch (IOException e) {
				log.error("", e);
			}
        }

        log.info("Post logon cookies:");
        cookies = httpclient.getCookieSpecs().getSpecNames();
        if (cookies.isEmpty()) {
            log.info("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                log.info("- " + cookies.get(i).toString());
            }
        }
    }

}	// end of class httpComp