package com.redfire.nutrieduc.router;

import java.io.IOException;
import java.util.Properties;

/**
 * Class that receive a URL coming from the App Client and transform to the module URL
 * @author thiagolenz
 * @since Aug 25, 2014
 *
 */
public class URLDecoder {
	/**
	 * Contains the modules aliases names 
	 */
	public static Properties proxyProperties;
	
	/**
	 * Contains the server DNS names of the modules, based on the aliases names
	 */
	public static Properties urlProperties;
	
	/**
	 * Load the files that contains the modules alias configuration 
	 */
	static {
		proxyProperties = new Properties();
		urlProperties = new Properties();

		try {
			proxyProperties.load(URLDecoder.class.getClassLoader().getResourceAsStream("modulesProxy.properties"));
			String fileName = "modulesURL_"+ getAppEnv() + ".properties";
			urlProperties.load(URLDecoder.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getAppEnv () {
		return System.getProperty("app.env");
	}
	
	/**
	 * Decodify the URL like this 
	 * @param url /moduleAlias/feature/..?..
	 * @return https://svc01:8080/module-name-svc/feature/..?..
	 */
	public String decodeURL (String url) {
		url = url.substring(1, url.length());
		int indexSeparator = url.indexOf("/");
		
		String module = url.substring(0, indexSeparator);
		String restPart = url.substring(indexSeparator, url.length());

		StringBuffer buf = new StringBuffer();
		buf.append(urlProperties.get(module));
		buf.append(proxyProperties.get(module));
		buf.append(restPart);

		return buf.toString();
	}
}
