package eu.ehri.helpdesk;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.annolab.tt4j.TreeTaggerException;
import org.springframework.stereotype.Service;

@Service
public class Workflow {

	public GetInput getUserInput(GetInput userinput) {
		return userinput;
	}

	public List<String> processQuery(GetInput input) throws IOException,
			TreeTaggerException {

		/*
		 * 1 - tokenize input 2 - POS tagging 3 - create vector of features 4 -
		 * compute symilarity
		 */
		EnglishTokenizer tokenizeInput = new EnglishTokenizer();
		List<String> tokens = tokenizeInput.tokenize(input.getInput());

		PartOfSpeechTagging annotatePOS = new PartOfSpeechTagging();
		List<String> tagged = annotatePOS.annotatePOSpeech(tokens);

		ComputeFeatureOccurrences compOccurrences = new ComputeFeatureOccurrences();
		HashMap<String, Integer> featureOcurrences = compOccurrences
				.computeFeatOccurrences(tagged);

		RepresentUserQuery userQuery = new RepresentUserQuery();
		HashMap<String, Double> userQueryTF = userQuery
				.computeTF(featureOcurrences);

		ComputeSimilarity compSimilarity = new ComputeSimilarity();
//		HashMap<String, Double> similarity = ComputeSimilarity
//				.computeSimilarity(userQueryTF);

		List<String> similarity = ComputeSimilarity.computeSimilarity(userQueryTF);
//		return null;
		return similarity;
	}

}
