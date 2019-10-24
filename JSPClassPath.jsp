<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.util.*"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLClassLoader"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ClassPath Display</title>
</head>
<body>
	ClassPath:
		<br>
		<% 
		String cp = new String();
		URL[] urls = null;
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
        
        ClassLoader acl = this.getClass().getClassLoader();
        
        if (acl == null)
        	urls = ((URLClassLoader)cl).getURLs();
        else
        	urls = ((URLClassLoader)acl).getURLs();
 
        for(URL url: urls){
        	cp += url.getFile() + "<br>";
        }
        
	 %>
	<%= cp %>

</body>
</html>
