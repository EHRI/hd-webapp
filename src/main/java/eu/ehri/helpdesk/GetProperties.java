package eu.ehri.helpdesk;

import java.io.IOException;
import java.util.Properties;

 // Only relevant property is the TreeTagger Folder

public class GetProperties {

	public String getTreeTaggerFolder(){
		Properties prop = new Properties();	
		
		try {
			prop.load(getClass().getResourceAsStream("config.properties"));
		}catch (IOException ex) {
			ex.printStackTrace();
	    }
		final String DESCRIPTIONS_FOLDER = prop.getProperty("treetagger_folder");
		
		return DESCRIPTIONS_FOLDER;
	}
	
}
