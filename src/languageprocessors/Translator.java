package languageprocessors;

import main.Util;

import com.memetix.mst.translate.Translate;

/**
 * 
 * Translates target from sourceLanguage to destinationLanguage
 *
 */
public class Translator {

	//translates given string, target, from sourceLanguage to destinationLanguage
	public String translate(String target, String sourceLanguage, String destinationLanguage) {
		try {
			return Translate.execute(target, Util.getLanguage(sourceLanguage),
					Util.getLanguage(destinationLanguage));
		} catch (Exception e) {
			return "Oops. Something went wrong.";
		}
	}
}
