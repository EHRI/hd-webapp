package eu.ehri.helpdesk;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OutputJSON {

	
	public static JSONObject produceJSONoutput (List<String> relevancerank) throws JSONException{
		
		JSONObject response = new JSONObject();
		JSONArray orderedList = new JSONArray();
		
		
		//create new JSONArray containing
		//ehri ID, name of institution, relevance
		
		for (int i = 0; i < relevancerank.size(); i++){
			String institution = relevancerank.get(i).split("\t-->\t")[0];
			JSONObject archive = new JSONObject();
			String score = relevancerank.get(i).split("\t-->\t")[1];
			String id = IDOfInstitutions.consultDataOfInstitution(relevancerank.get(i).split("\t-->\t")[0]);

			
			archive.put("id", id);
			archive.put("name", institution);
			archive.put("score", score);
					
			orderedList.put(archive);
		}
	
		response.put("response", orderedList);
		
		return response;
	}
	
}
