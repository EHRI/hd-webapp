package eu.ehri.helpdesk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ComputeFeatureOccurrences {

	static GetProperties property = new GetProperties();
	final static String modelFolder = property.getModelFolder();
	final File featuresFile = new File("features.txt");

	
	
	public HashMap<String, Integer> computeFeatOccurrences(List<String> tagged) {
		
		// Read files features.txt and store the features in a list
		List<String> featuresList = new ArrayList<String>();
		String currentLine;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(modelFolder
					+ featuresFile));
			while ((currentLine = br.readLine()) != null) {
				featuresList.add(currentLine);
				// System.out.println(currentLine);
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

		for (int i = 0; i < keywords.size(); i++) {
			if (featureOccurrence.containsKey(keywords.get(i))) {
				int value = featureOccurrence.get(keywords.get(i));
				featureOccurrence.put(keywords.get(i), value + 1);
			} else {
				featureOccurrence.put(keywords.get(i), 1);
			}
		}

		/*
		 * Iterator it = featureOccurrence.entrySet().iterator(); while
		 * (it.hasNext()) { Map.Entry pairs = (Map.Entry) it.next();
		 * System.out.println(pairs.getKey() + "  " + pairs.getValue());
		 * it.remove(); }
		 */
		return featureOccurrence;
	}

}
