package eu.ehri.helpdesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

public class PartOfSpeechTagging {

	GetProperties property = new GetProperties();
	final String treeTaggerFolder = property.getTreeTaggerFolder();
	public List<String> annotatePOSpeech(List<String> tokenized)
			throws IOException, TreeTaggerException {
		
		System.out.println("Tree Tager Folder in " + treeTaggerFolder);
			
		System.setProperty("treetagger.home", treeTaggerFolder);
		TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
		final List<String> outputTreeTagger = new ArrayList<String>();

		try {
			tt.setModel(treeTaggerFolder + "lib/english.par:utf8");
			tt.setHandler(new TokenHandler<String>() {
				public void token(String token, String pos, String lemma) {
					outputTreeTagger.add(token + "\t" + pos + "\t" + lemma);
				}
			});
			tt.process(tokenized);
		} finally {
			tt.destroy();
		}
		
		return outputTreeTagger;
	}

}
