package eu.ehri.helpdesk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RepresentUserQuery {
	// Term frequency of user query

	public HashMap<String, Double> computeTF(
			HashMap<String, Integer> termOccurrence) {

		// compute length of query as number of terms/keywords
		double lengthQuery = 0;
		for (int value : termOccurrence.values()) {
			lengthQuery = lengthQuery + value;
		}

		// Declaration of TF representation hashmap
		HashMap<String, Double> termFrequency = new HashMap<String, Double>();

		// Compute TF
		for (String key : termOccurrence.keySet()) {
			int value = termOccurrence.get(key);
			double max = (double) Collections.max(termOccurrence.values());
			
			//double frequency = value / lengthQuery;
			double frequency = value / max;
			
			termFrequency.put(key, frequency);
			//System.out.println("FREQUENCY " + key + "\t" + value + "\t" + lengthQuery + "\t" + frequency);
		}

		Iterator it = termFrequency.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			//System.out.println(pairs.getKey() + "  " + pairs.getValue());
		}

		return termFrequency;

	}

}
