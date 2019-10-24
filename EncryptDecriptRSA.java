package com.travelport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URLClassLoader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class createAsymmetricCipher {
	
	private static Log log = LogFactory.getLog(EncryptDecript.class.getName());

	private String publicKeyFilename = null;
    private String privateKeyFilename = null;
	
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
        
	}
	
	/**
	 * *notes: 
	 * 4096 Keys size took 5 seconds to encript Max 501
	 * 4096 key size took 10 sec with 378 bytes of data Max 
	 * 2048 Kyes size took 1 second to encript	Max 245
	 * 1024  key size took less than 1 sec to encript. Max 117 
	 * 512  key size took less than 1 sec to encript. Max 53
	 */
	@Test
	public void testEncrypt() {
		UserKeys ukeys = null;
		String DataToEncript = "This is some data that we need to decript! It has lots of data in it..";
		DataToEncript += "2nd This is some data that we need to decript! It has lots of data in it..2nd";
		DataToEncript += "3nd This is some data that we need to decript! It has lots of data in it..3nd";
		DataToEncript += "4nd This is some data that we need to decript! It has lots of data in it..4nd";
		DataToEncript += "5nd This is some data that we need to decript! It has lots of data in it..5nd";
		DataToEncript += "6nd This is some data that we need to decript! It has lots of data in it..6nd";
		DataToEncript += "7nd This is some data that we need to decript! It has lots of data in it..7nd";
		DataToEncript += "8nd This is some data that we need to decript! It has lots of data in it..8nd";
		log.info("Length of Data:" + DataToEncript.length());
		
		try {
			log.info("Start of Encription!");
			ukeys = CreatePublicPrivate(4096);
			Encript(ukeys, DataToEncript.getBytes());
			log.info("End of Encription!");
			
		} catch (NoSuchAlgorithmException e) {
			log.error("", e);
			fail();
		} catch (InvalidKeyException | NoSuchProviderException | NoSuchPaddingException  
				| InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException e) {
			log.error("", e);
			fail();
		}
	
	}
	
	@Test
	public void testEncryptDecript() {
		UserKeys ukeys = null;
		String DataToEncript = "This is some data that we need to decript! It has lots of data in it..";
		
		try {
			ukeys = CreatePublicPrivate(2048);
			byte[] myencripted = Encript(ukeys, DataToEncript.getBytes());
			byte[] mydecripted = Decript(ukeys, myencripted);
			
			String work = new String(mydecripted);
			
			assertEquals("Data did not Decript as it should!", DataToEncript, new String(mydecripted));
			
			log.info("Decripted:" + work);
			
		} catch (NoSuchAlgorithmException e) {
			log.error("", e);
			fail();
		} catch (InvalidKeyException | NoSuchProviderException | NoSuchPaddingException  
				| InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException e) {
			log.error("", e);
			fail();
		}
	
	}
	
	@Test
	public void testSaveKeyPair() {
		UserKeys ukeys = null;
		
		try {
			ukeys = CreatePublicPrivate(1024);
		} catch (NoSuchAlgorithmException e) {
			log.error("", e);
			fail();
		}
		
		File privateKeyFile = new File("Testing Resources\\Encription\\private.bin");
		File publicKeyFile = new File("Testing Resources\\Encription\\public.bin");
		
		try {
			FileOutputStream fos = new FileOutputStream(privateKeyFile);

			fos.write(ukeys.getPrivateKey());
			
			fos = new FileOutputStream(publicKeyFile);
			fos.write(ukeys.getPublicKey());

		} catch (IOException e ) {
			log.error("", e);
			fail();
		} 

	}
	
	@Test
	public void testCreateKeys() {
		UserKeys ukeys = null;
		
		try {
			ukeys = CreatePublicPrivate(1024);
		} catch (NoSuchAlgorithmException e) {
			log.error("", e);
			fail();
		}
		
		log.info("Private Key:" + new String(ukeys.getPrivateKey()));		
		log.info("Public Key:" + new String(ukeys.getPublicKey()));		
	}

	private UserKeys CreatePublicPrivate(int keysize) throws NoSuchAlgorithmException {
		UserKeys userkeys = new UserKeys();
		
	    // Generate a key-pair
	    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
	    kpg.initialize(keysize); // 1024 is the keysize.
	    KeyPair kp = kpg.generateKeyPair();
	    PublicKey pubk = kp.getPublic();
	    PrivateKey prvk = kp.getPrivate();
	    
	    byte[] bytes = prvk.getEncoded();
	    userkeys.setPrivateKey(bytes);

	    bytes = pubk.getEncoded();
	    userkeys.setPublicKey(bytes);
	    
		return userkeys;
	}

	/**
	 * Encripts the Data using Private Key stored as Byte array in Userkeys
	 * 
	 * @param uk	UserKey set
	 * @param data	Data to be encripted. (Max Data lenght 2048)
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private byte[] Encript(UserKeys uk, byte[] data) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encripted = null;
		
		if (data.length > 2048)
			return null;
		
		Cipher cipher = Cipher.getInstance("RSA");
		
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(uk.getPublicKey());
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pk = kf.generatePublic(publicKeySpec);
		
		cipher.init(Cipher.ENCRYPT_MODE, pk);

		encripted = cipher.doFinal(data);
		
		return encripted;
	}

	private byte[] Decript(UserKeys uk, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] decripted = null;
		
		Cipher cipher = Cipher.getInstance("RSA");
		
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(uk.getPrivateKey()); 
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pk = kf.generatePrivate(privateKeySpec);
		
		cipher.init(Cipher.DECRYPT_MODE, pk);
		
		decripted = cipher.doFinal(data);
		
		return decripted;
	}
	
	private class UserKeys {
		private byte[] publicKey;
		public byte[] getPublicKey() {
			return publicKey;
		}
		public void setPublicKey(byte[] publicKey) {
			this.publicKey = publicKey;
		}
		private byte[] privateKey;
		public byte[] getPrivateKey() {
			return privateKey;
		}
		public void setPrivateKey(byte[] privateKey) {
			this.privateKey = privateKey;
		}
		
	}
	
	
}
