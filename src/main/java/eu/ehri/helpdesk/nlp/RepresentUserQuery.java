package eu.ehri.helpdesk.nlp;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;

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
        HashMap<String, Double> termFrequency = Maps.newHashMap();

        // Compute TF
        for (String key : termOccurrence.keySet()) {
            int value = termOccurrence.get(key);
            double max = (double) Collections.max(termOccurrence.values());

            //double frequency = value / lengthQuery;
            double frequency = value / max;

            termFrequency.put(key, frequency);
        }

        return termFrequency;
    }
}
