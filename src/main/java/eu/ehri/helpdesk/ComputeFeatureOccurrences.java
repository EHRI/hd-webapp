package eu.ehri.helpdesk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComputeFeatureOccurrences {

	InputStream features = getClass()
			.getResourceAsStream("/Model/features.txt");

	public HashMap<String, Integer> computeFeatOccurrences(List<String> tagged) {

		// Read files features.txt and store the features in a list
		List<String> featuresList = new ArrayList<String>();
		String currentLine;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(features));
			while ((currentLine = br.readLine()) != null) {
				featuresList.add(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}

		List<String> keywords = new ArrayList<String>();

		for (int i = 0; i < tagged.size(); i++) {
			String[] splittedLine = tagged.get(i).split("\t");

			if (splittedLine[2].equals("<unknown>")) {
				if (featuresList.contains(splittedLine[0].toLowerCase())) {
					keywords.add(splittedLine[0].toLowerCase());
				}
			} else {
				if (featuresList.contains(splittedLine[2].toLowerCase())) {
					keywords.add(splittedLine[2].toLowerCase());
				}
			}
		}

		HashMap<String, Integer> featureOccurrence = new HashMap<String, Integer>();

	//TO DO
	//Implement here synonyms dictionary used for feature extraction from data
	//during model building
		
		for (int i = 0; i < keywords.size(); i++) {
			if (featureOccurrence.containsKey(keywords.get(i))) {
				int value = featureOccurrence.get(keywords.get(i));
				featureOccurrence.put(keywords.get(i), value + 1);
			} else {
				featureOccurrence.put(keywords.get(i), 1);
			}
		}

		return featureOccurrence;
	}

}
