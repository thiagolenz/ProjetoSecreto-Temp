package com.redire.nutrieduc.nutreducadmin.servlet;

import java.io.IOException;
import java.util.Properties;

public class ContextPropertyReader {
	private static Properties prop;
	
	private ContextPropertyReader () {
		
	}
	
	public static String getValue(String key) throws IOException {
		if (prop == null){
			prop = new Properties();
			String fileName = "configurations_"+ System.getProperty("app.env") + ".properties";
			prop.load(ContextPropertyReader.class.getClassLoader().getResourceAsStream(fileName));
		}
		return prop.getProperty(key);
	}
}
