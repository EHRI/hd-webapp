package eu.ehri.helpdesk.nlp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Helpers {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, String> getInstitutions() throws IOException {
        InputStream inputStream = Helpers.class.getResourceAsStream("/institutions.json");
        TypeReference<HashMap<String,String>> typeReference = new TypeReference<HashMap<String, String>>() {};
        try {
            return mapper.readValue(inputStream, typeReference);
        } finally {
            inputStream.close();
        }
    }

	/*
     * sorts a hash by value
	 */

    public static Map<String, Double> sortByValue(HashMap<String, Double> map) {
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

            public int compare(Map.Entry<String, Double> m1, Map.Entry<String, Double> m2) {
                int out = 0;
                if (m2.getValue() < m1.getValue()) {
                    out = -1;
                }
                if (m2.getValue() > m1.getValue()) {
                    out = 1;
                }
                if (m2.getValue().equals(m1.getValue())) {
                    out = 0;
                }
                return out;
            }
        });

        HashMap<String, Double> result = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
