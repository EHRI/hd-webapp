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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputeSimilarity {


	static GetProperties property = new GetProperties();
	final static String modelFolder = property.getModelFolder();
	final static File files = new File(modelFolder);

	//provisional change to list for display
//	public static HashMap<String, Double> computeSimilarity(
	public static List<String> computeSimilarity(		
			HashMap<String, Double> userquery) {
		
		Pattern tfidfFile = Pattern.compile(".*tfidf");
		List<String> fileList = new ArrayList<String>();
		//HashMap<String, HashMap<String, Double>> institutions = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> institutionsRelevance = new HashMap<String, Double>();
		
		for (final File fileEntry : files.listFiles()) {
			Matcher matcherText = tfidfFile.matcher(fileEntry.toString());
			if (matcherText.find()) {
				fileList.add(fileEntry.getName());
				System.out.println(fileEntry.getName());
			}
		}

		for (int i = 0; i < fileList.size(); i++) {
			List<String> fileLines = new ArrayList<String>();
			HashMap<String, Double> institutionFeatures = new HashMap<String, Double>();
			String currentLine;
			BufferedReader br = null;

			try {
				br = new BufferedReader(new FileReader(modelFolder
						+ fileList.get(i)));
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
			System.out.println(fileList.get(i) + " --> " + similarity);
			
			if (fileList.get(i).equals("yadvashem.tfidf")){
				institutionsRelevance.put("Yad Vashem", similarity);
			}
			if (fileList.get(i).equals("niod.tfidf")){
				institutionsRelevance.put("NIOD", similarity);
			}
			if (fileList.get(i).equals("wiener.tfidf")){
				institutionsRelevance.put("Wiener Library", similarity);
			}
			if (fileList.get(i).equals("jewishmuseumprag.tfidf")){
				institutionsRelevance.put("Jewish Museum Prag", similarity);
			}
		}


		HashMap<String,Double> newHashMap = AuxiliaryMethods.sortByValue(institutionsRelevance);
		AuxiliaryMethods.sortByValue(institutionsRelevance);
		
		List<String> relevance = new ArrayList<String>();
		for (String key : newHashMap.keySet()){
			String item = key + " --> " + institutionsRelevance.get(key).toString();
			relevance.add(item);
		}
		
		return relevance;		
//		return null;
	}
}
