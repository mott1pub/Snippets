${:import(java.io.File, java.net.URI, java.net.URISyntaxException, java.net.URL, java.net.URLClassLoader)}	
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
 
        }
        log.info("*** END CLASSPATHS ***");
        
        log.info("END of Method Setup!");
	}