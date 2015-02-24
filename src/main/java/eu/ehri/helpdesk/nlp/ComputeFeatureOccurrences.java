package eu.ehri.helpdesk.nlp;

import com.google.common.collect.Maps;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComputeFeatureOccurrences {

    InputStream features = getClass()
            .getResourceAsStream("/Model/features.txt");

    public HashMap<String, Integer> computeFeatOccurrences(String tagged) throws XMLStreamException {
        // String tagged is XML-string

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
        }

        // Convert string into InputStream
        InputStream is = new ByteArrayInputStream(tagged.getBytes());

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        @SuppressWarnings("unused")
        XMLStreamReader xmlStreamReader = inputFactory
                .createXMLStreamReader(is);

        List<String> keywords = new ArrayList<String>();

        XMLEventReader xmlEventReader = XMLInputFactory.newInstance()
                .createXMLEventReader(is);

        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement()) {
                if (event.asStartElement().getName().getLocalPart()
                        .equals("lemma")) {
                    event = xmlEventReader.nextEvent();
                    String lemma = event.asCharacters().getData().toLowerCase();
                    if (featuresList.contains(lemma)) {
                        keywords.add(lemma);
                    }
                }
            }

        }

        HashMap<String, Integer> featureOccurrence = Maps.newHashMap();

        for (String keyword : keywords) {
            if (featureOccurrence.containsKey(keyword)) {
                int value = featureOccurrence.get(keyword);
                featureOccurrence.put(keyword, value + 1);
            } else {
                featureOccurrence.put(keyword, 1);
            }
        }

        return featureOccurrence;
    }
}
