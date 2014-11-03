package eu.ehri.helpdesk;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IDOfInstitutions {


	
	public static String consultDataOfInstitution(String name){
		
		String institution = "";


		HashMap<String,String> archives = new HashMap<String,String>();
		archives.put("Wiener Library", "gb-003348");
		archives.put("NIOD", "nl-002896");
		archives.put("Yad Vashem", "il-002798");
		archives.put("Jewish Museum in Prague", "cz-002279");
		archives.put("CEGESOMA", "be-002112");
		archives.put("Bundesarchiv", "de-002429");
		archives.put("International Tracing Service", "de-002409");
		archives.put("United States Holocaust Memorial Museum", "us-005578");
		archives.put("Institut für Zeitgeschichte Munich", "de-002624");


		institution = archives.get(name);
		
		
		
		
		
		
		 
		return institution;

		
		
	}
	
	
}
