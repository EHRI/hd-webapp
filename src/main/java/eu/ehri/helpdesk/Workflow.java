package eu.ehri.helpdesk;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class Workflow {

	public GetInput getUserInput(GetInput userinput) {
		return userinput;
	}

	public List<String> processQuery(GetInput input) throws IOException, XMLStreamException {

		String processedText = NLPProcessing.processMessage(input.getInput());
		

		ComputeFeatureOccurrences compOccurrences = new ComputeFeatureOccurrences();
		HashMap<String, Integer> featureOcurrences = compOccurrences
				.computeFeatOccurrences(processedText);

		RepresentUserQuery userQuery = new RepresentUserQuery();
		HashMap<String, Double> userQueryTF = userQuery
				.computeTF(featureOcurrences);

		ComputeSimilarity compSimilarity = new ComputeSimilarity();
		List<String> similarity = compSimilarity
				.computeSimilarity(userQueryTF);
		
		return similarity;
	}

	
	public JSONObject outputJSON(GetInput input) throws JSONException, IOException, XMLStreamException{
		
		JSONObject jsonresult = OutputJSON.produceJSONoutput(processQuery(input));
		
		return jsonresult;		
	}
	
}
