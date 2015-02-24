package eu.ehri.helpdesk.nlp;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Workflow {
    public static Map<String, Double> processQuery(String query, Set<String> institutions) throws IOException,
            XMLStreamException {

        String processedText = NLPProcessing.processMessage(query);

        ComputeFeatureOccurrences compOccurrences = new ComputeFeatureOccurrences();
        HashMap<String, Integer> featureOccurrences = compOccurrences
                .computeFeatOccurrences(processedText);

        RepresentUserQuery userQuery = new RepresentUserQuery();
        HashMap<String, Double> userQueryTF = userQuery.computeTF(featureOccurrences);

        ComputeSimilarity compSimilarity = new ComputeSimilarity();
        return compSimilarity.computeSimilarity(userQueryTF, institutions);
    }
}
