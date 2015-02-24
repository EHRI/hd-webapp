package eu.ehri.helpdesk.nlp;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class NLPProcessing {

    public static String processMessage(String message) throws IOException {

        Properties props = new Properties();

        props.put("annotators", "tokenize, ssplit,pos, lemma");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(message);


        pipeline.annotate(document);

        ByteArrayOutputStream ab = new ByteArrayOutputStream();

        pipeline.xmlPrint(document, ab);

        return ab.toString();
    }
}
