package com.redfire.nutrieduc.router;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.redfire.nutrieduc.restclient.RestClientException;

/**
 * Class that writes the error response in JSON format
 * @author thiagolenz
 * @since Aug 25, 2014
 */
public class ResponseErrorWriter {

	public int writeErrorResponse (PrintWriter writer, RestClientException e) throws IOException  {
		writer.write(convertFailureBeanToJson(createFailureBean(e)));
		return e.getStatusCode();
	}
	
	private FailureBean createFailureBean (RestClientException e) {
		return new FailureBean(e.getMessage());
	}
	
	private String convertFailureBeanToJson (FailureBean failureBean) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(failureBean);
		return json;
	}
}
