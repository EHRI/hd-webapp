package eu.ehri.helpdesk;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OutputJSON {

	
	public static JSONObject produceJSONoutput (List<String> relevancerank) throws JSONException{
		
		JSONObject response = new JSONObject();
		JSONArray orderedList = new JSONArray();
		
		for (int i = 0; i < relevancerank.size(); i++){
			String institution = relevancerank.get(i);
			
			orderedList.put(institution);
		}
	
		response.put("response", orderedList);
		
		return response;
	}
	
}
