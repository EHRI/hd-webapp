package eu.ehri.helpdesk;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CosineSimilarity {

	public static double computeCosine(HashMap<String, Double> vector1,
			HashMap<String, Double> vector2) {
		// vector 1 is the e-mail and vector 2 the representation of the
		// institution

		double numerator = 0;
		double v1denominator = 0;
		double v2denominator = 0;
		double denominator = 0;
		double cosine = 0;

		for (String key : vector1.keySet()) {
			v1denominator = v1denominator
					+ (vector1.get(key) * vector1.get(key));
		}

		for (String key : vector2.keySet()) {
			v2denominator = v2denominator
					+ (vector2.get(key) * vector2.get(key));
		}

		for (String key : vector1.keySet()) {
			if (vector2.containsKey(key)) {
				numerator = numerator + (vector1.get(key) * vector2.get(key));
			}
		}
		denominator = (Math.sqrt(v1denominator) * Math.sqrt(v2denominator));

		if ((v1denominator * v2denominator) == 0) {
			cosine = 0;
		} else {
			cosine = numerator
					/ (Math.sqrt(v1denominator) * Math.sqrt(v2denominator));
		}

		return cosine;

	}

}
