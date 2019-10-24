import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

public class TestMain {

	public static void main(String[] args) {

		final Logger log = Logger.getLogger(TestMain.class.getName());
		
		File mypath = new File(".");
		log.info("My Path:" + mypath.getAbsolutePath());
		
		URI uri = null;
		try {
			uri = new URI("file:/C:/temp/New%20Folder/test.txt");
		} catch (URISyntaxException e1) {
			log.fatal("", e1);
		}

		File file = new File(uri);

		log.info("File at:" + file.getAbsolutePath());
		
	  	log.info("Default Charset=" + Charset.defaultCharset());
    	System.setProperty("file.encoding", "Latin-1");
    	log.info("file.encoding=" + System.getProperty("file.encoding"));
    	log.info("Default Charset=" + Charset.defaultCharset());
    	log.info("Default Charset in Use=" + getDefaultCharSet());
		
		boolean result = file.canRead();
		
		log.info("Result:" + result);
		
		FileInputStream instr = null;
		try {
			instr = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			log.info("Error:",e);
		}
		
		int c;
		try {
			while ((c = instr.read()) != -1) {
				log.info("" + c);
			}
		} catch (IOException e) {
			log.info("", e);
		}
	   

	}

	private static String getDefaultCharSet() {
    	OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
    	String enc = writer.getEncoding();
    	return enc;
    }

}
