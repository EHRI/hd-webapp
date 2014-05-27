package eu.ehri.helpdesk;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

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
		} finally {
		}

		// Convert string into InputStream
		InputStream is = new ByteArrayInputStream(tagged.getBytes());

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
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
					String lemma = event.asCharacters().getData().toString();
					if (featuresList.contains(lemma)) {
						keywords.add(lemma);
					}
				}

			}

		}

		HashMap<String, Integer> featureOccurrence = new HashMap<String, Integer>();

		// TO DO
		// Implement here synonyms dictionary used for feature extraction from
		// data
		// during model building

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
