package eu.ehri.helpdesk;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * Only tree tagger is necessary
 * 
 */

public class GetProperties {

	InputStream propertiesFile = getClass().getResourceAsStream("/config.properties");
	public String getTreeTaggerFolder(){

		
		Properties prop = new Properties();	
		
		try {
//			prop.load(getClass().getResourceAsStream("config.properties"));
			prop.load(propertiesFile);
		}catch (IOException ex) {
			ex.printStackTrace();
	    }
		final String TREETAGGER_FOLDER = prop.getProperty("treetagger_folder");
		
		return TREETAGGER_FOLDER;
	}
	
}
