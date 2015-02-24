package eu.ehri.helpdesk.nlp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComputeSimilarity {

    private static final Logger logger = LoggerFactory.getLogger(ComputeSimilarity.class);

    // provisional change to list for display
    public Map<String, Double> computeSimilarity(HashMap<String, Double> userQuery, Set<String> institutionIds)
            throws IOException {

        HashMap<String, Double> institutionsRelevance = Maps.newHashMap();

        for (String id : institutionIds) {
            List<String> fileLines = Lists.newArrayList();
            HashMap<String, Double> institutionFeatures = Maps.newHashMap();

            String resourceName = "/Model/" + id + ".tfidf";
            InputStream resource = getClass().getResourceAsStream(resourceName);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(resource));
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    fileLines.add(currentLine);
                }
            } finally {
                resource.close();
            }

            for (String fileLine : fileLines) {
                String[] featureWeight = fileLine.split("\t");
                double weight = Double.parseDouble(featureWeight[1]);
                institutionFeatures.put(featureWeight[0], weight);
            }

            Double similarity = CosineSimilarity.computeCosine(userQuery,
                    institutionFeatures);

            logger.debug("{} --? {}", id, similarity);

            if (similarity > 0.005) {
                institutionsRelevance.put(id, similarity);
            }
        }

        return Helpers.sortByValue(institutionsRelevance);
    }
}
