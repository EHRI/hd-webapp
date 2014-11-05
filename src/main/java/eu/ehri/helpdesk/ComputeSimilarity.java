package eu.ehri.helpdesk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComputeSimilarity {

	// Read vector of each institution
	// Add new institutions here
	InputStream wiener = getClass().getResourceAsStream("/Model/wiener.tfidf");
	InputStream niod = getClass().getResourceAsStream("/Model/niod.tfidf");
	InputStream yadvashem = getClass().getResourceAsStream(
			"/Model/yadvashem.tfidf");
	InputStream jewishmuseumprag = getClass().getResourceAsStream(
			"/Model/jewishmuseumprag.tfidf");
	InputStream bundesarchiv = getClass().getResourceAsStream(
			"/Model/bundesarchiv.tfidf");
	InputStream ushmm = getClass().getResourceAsStream("/Model/ushmm.tfidf");
	InputStream its = getClass().getResourceAsStream("/Model/its.tfidf");
	InputStream ifz = getClass().getResourceAsStream("/Model/cegesoma.tfidf");
	InputStream cegesoma = getClass().getResourceAsStream("/Model/cegesoma.tfidf");

	// List of vectors representing archives
	List<InputStream> vectors = new ArrayList<InputStream>();

	// provisional change to list for display
	public List<String> computeSimilarity(HashMap<String, Double> userquery) {

		vectors.add(niod);
		vectors.add(yadvashem);
		vectors.add(wiener);
		vectors.add(jewishmuseumprag);
		vectors.add(bundesarchiv);
		vectors.add(ushmm);
		vectors.add(its);
		vectors.add(ifz);
		vectors.add(cegesoma);

		HashMap<String, Double> institutionsRelevance = new HashMap<String, Double>();

		for (int i = 0; i < vectors.size(); i++) {
			List<String> fileLines = new ArrayList<String>();
			HashMap<String, Double> institutionFeatures = new HashMap<String, Double>();
			String currentLine;
			BufferedReader br = null;

			try {
				br = new BufferedReader(new InputStreamReader(vectors.get(i)));
				while ((currentLine = br.readLine()) != null) {
					fileLines.add(currentLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}

			for (int idx = 0; idx < fileLines.size(); idx++) {
				String[] featureWeight = fileLines.get(idx).split("\t");
				double weight = Double.parseDouble(featureWeight[1]);
				institutionFeatures.put(featureWeight[0], weight);
			}

			Double similarity = CosineSimilarity.computeCosine(userquery,
					institutionFeatures);
			System.out
					.println(vectors.get(i).toString() + " --> " + similarity);

			if (similarity > 0.005) {
				if (vectors.get(i).equals(yadvashem)) {
					institutionsRelevance.put("Yad Vashem", similarity);
				}
				if (vectors.get(i).equals(niod)) {
					institutionsRelevance.put("NIOD", similarity);
				}
				if (vectors.get(i).equals(wiener)) {
					institutionsRelevance.put("Wiener Library", similarity);
				}
				if (vectors.get(i).equals(jewishmuseumprag)) {
					institutionsRelevance.put("Jewish Museum in Prague", similarity);
				}
				if (vectors.get(i).equals(bundesarchiv)) {
					institutionsRelevance.put("Bundesarchiv", similarity);
				}
				if (vectors.get(i).equals(ushmm)) {
					institutionsRelevance.put("United States Holocaust Memorial Museum", similarity);
				}
				if (vectors.get(i).equals(its)) {
					institutionsRelevance.put("International Tracing Service", similarity);
				}
				if (vectors.get(i).equals(ifz)) {
					institutionsRelevance.put("Institut für Zeitgeschichte Munich", similarity);
				}
				if (vectors.get(i).equals(cegesoma)) {
					institutionsRelevance.put("CEGESOMA", similarity);
				}
			}
		}

		HashMap<String, Double> newHashMap = AuxiliaryMethods
				.sortByValue(institutionsRelevance);
		AuxiliaryMethods.sortByValue(institutionsRelevance);

		List<String> relevance = new ArrayList<String>();
		List<String> ranking = new ArrayList<String>();

		for (String key : newHashMap.keySet()) {
			String item = key + "	-->	"
					+ institutionsRelevance.get(key).toString();
			relevance.add(item);
		}

		if (relevance.size() == 0) {
			ranking.add("No relevant institution found. Please reformulate your query and add more details.");
		} else {
			if (relevance.size() > 0 && relevance.size() <= 3) {
				ranking = relevance;
			} else {
				for (int i = 0; i < 3; i++) {
					ranking.add(relevance.get(i));
				}
			}
		}

		return ranking;

	}
}
