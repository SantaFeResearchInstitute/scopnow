package jp.co.scop.common.util;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * @author ncxxsl-koseki
 *
 */
@Component
public class LogUtility {
	

	public String array2JsonLogging(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    try {
	        String script = mapper.writeValueAsString(o);
	        return script;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return "array2JsonLogging";
		
	}

}
