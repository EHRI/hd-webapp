package eu.ehri.helpdesk;

import java.text.BreakIterator;
import static java.util.Arrays.asList;
import java.util.ArrayList;
import java.util.List;

/* 
 * implementation in Java of the tokenizer provided
 * by TreeTagger
 */

public class EnglishTokenizer {

	public List<String> tokenize(String toTokenize) {
		toTokenize = toTokenize.replace("'", " '");
		
		List<String> tokens = new ArrayList<String>();
		BreakIterator bi = BreakIterator.getWordInstance();
		bi.setText(toTokenize);
		int begin = bi.first();
		int end;
		for (end = bi.next(); end != BreakIterator.DONE; end = bi.next()) {
			String t = toTokenize.substring(begin, end);
			if (t.trim().length() > 0) {
				tokens.add(toTokenize.substring(begin, end));
			}
			begin = end;
		}
		if (end != -1) {
			tokens.add(toTokenize.substring(end));
		}


		String tokenlist = AuxiliaryMethods.listToString(tokens);
		tokenlist = tokenlist.replace("' s ", "'s ");
		tokenlist = tokenlist.replace("' ll ", "'ll ");
		tokenlist = tokenlist.replace("' m ", "'m ");
		tokenlist = tokenlist.replace("' are ", "'are ");
		tokenlist = tokenlist.replace("' re ", "'re ");
		tokenlist = tokenlist.replace("dr .", "dr.");
		tokenlist = tokenlist.replace("mr .", "mr.");
		tokenlist = tokenlist.replace("mrs .", "mrs.");
		tokenlist = tokenlist.replace("ms .", "ms.");
		tokenlist = tokenlist.replace("Dr .", "Dr.");
		tokenlist = tokenlist.replace("Mr .", "Mr.");
		tokenlist = tokenlist.replace("Mrs .", "Mrs.");
		tokenlist = tokenlist.replace("Ms .", "Ms.");
		// System.out.println(tokenlist);

		// for problems in pre-processing
		tokenlist = tokenlist.replace("national sozialist", "nazi");
		tokenlist = tokenlist.replace("National Sozialist", "Nazi");
		tokenlist = tokenlist.replace("National sozialist", "Nazi");
		tokenlist = tokenlist.replace("anti - ", "anti");
		tokenlist = tokenlist.replace("anti-", "anti");		
		tokenlist = tokenlist.replace("Anti - ", "Anti");
		tokenlist = tokenlist.replace("Anti-", "Anti");	
	/*	tokenlist = tokenlist.replace("concentration camps", "KZ");
		tokenlist = tokenlist.replace("Concentration camps", "KZ");
		tokenlist = tokenlist.replace("Concentration Camps", "KZ");
		tokenlist = tokenlist.replace("concentrations camps", "KZ");
		tokenlist = tokenlist.replace("Concentrations camps", "KZ");
		tokenlist = tokenlist.replace("Concentrations Camps", "KZ");
		tokenlist = tokenlist.replace("concentration camp", "KZ");
		tokenlist = tokenlist.replace("Concentration camp", "KZ");
		tokenlist = tokenlist.replace("Concentration Camp", "KZ");
		tokenlist = tokenlist.replace("concentrations camp", "KZ");
		tokenlist = tokenlist.replace("Concentrations camp", "KZ");
		tokenlist = tokenlist.replace("Concentrations Camp", "KZ");
		*/
		
		
		
		// Later in dictionary: replace in list of features
		tokenlist = tokenlist.replace("Aliyah", "aliya");
		tokenlist = tokenlist.replace("aliyah", "aliya");
		tokenlist = tokenlist.replace("nationalsozialist", "nazi");
		tokenlist = tokenlist.replace("Nationalsozialist", "nazi");
		
		List<String> tokenList = asList(tokenlist.split(" "));
		return tokenList;
	}
}
