package com.redfire.nutrieduc.restclient;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class responsible to Read the JSON content given a BufferedReader
 * @author thiagolenz
 * @since 25/08/2014
 */
public class ContentReader {
	public String getJSONContent (BufferedReader reader) throws IOException{
		StringBuffer buffer = new StringBuffer();
		String line = null;
		do {
			line = reader.readLine();
			if (line != null)	
				buffer.append(line.trim());
		} while (line != null);
		
		return buffer.toString();
	}
}
