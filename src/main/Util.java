package main;

import java.io.File;
import java.util.ArrayList;

import javaFlacEncoder.FLAC_FileEncoder;

import com.memetix.mst.language.Language;
import com.memetix.mst.language.SpokenDialect;

/**
 * 
 * A class with static, utility-based methods
 *
 */
public class Util {

	//converts WAV files to FLAC files for compatibility with google translation services
	public static void WAVToFLAC(boolean user) {
		String fileName = Util.getUser(user);
		FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();
		flacEncoder.encode(new File(fileName + ".wav"), new File(fileName
				+ ".flac"));
	}
	
	//gets input from GUI
	public static ArrayList<Object> getInformationFromGUI() {
		return GUI.getInformation();
	}
	
	//convert boolean to relevant string
	public static String getUser(boolean user) {
		return (user) ? "firstPerson" : "secondPerson";
	}
	
	//since Google only allows 50 API calls a day, we randomize the API key for effectively 250 calls
	public static String getGoogleApiKey() {
		switch ((int) Math.random() * 4) {
			case 0:
				return "AIzaSyBOsfo4yo5qMRawoGrce8hXwUi_dmIEp3I";
			case 1:
				return "AIzaSyBaYxWPwv1OkWOHPRbwADBsvcCJOgJmkAM";
			case 2:
				return "AIzaSyBoTsqsF11owLIPJ4Jm7u56rbAzcxaRnBU";
			case 3:
				return "AIzaSyA48GnVNN2reqNz2ht0vx4jjAh4MwXByd8";
			default:
				return "AIzaSyAE_3JJFx6wF5RyCn32JIpPfwt9ab4J8Ok";
		}
	}
	
	//return corresponding SpokenDialect object given the language as a string
	public static SpokenDialect getDialect(String language) {
		switch (language) {
		case "Catalan":
			return SpokenDialect.CATALAN_SPAIN;
		case "Chinese":
			return SpokenDialect.CHINESE_SIMPLIFIED_PEOPLES_REPUBLIC_OF_CHINA;
		case "Danish":
			return SpokenDialect.DANISH_DENMARK;
		case "Dutch":
			return SpokenDialect.DUTCH_NETHERLANDS;
		case "English":
			return SpokenDialect.ENGLISH_UNITED_STATES;
		case "Finnish":
			return SpokenDialect.FINNISH_FINLAND;
		case "French":
			return SpokenDialect.FRENCH_FRANCE;
		case "German":
			return SpokenDialect.GERMAN_GERMANY;
		case "Italian":
			return SpokenDialect.ITALIAN_ITALY;
		case "Japanese":
			return SpokenDialect.JAPANESE_JAPAN;
		case "Korean":
			return SpokenDialect.KOREAN_KOREA;
		case "Norwegian":
			return SpokenDialect.NORWEGIAN_NORWAY;
		case "Polish":
			return SpokenDialect.POLISH_POLAND;
		case "Portuguese":
			return SpokenDialect.PORTUGUESE_PORTUGAL;
		case "Russian":
			return SpokenDialect.RUSSIAN_RUSSIA;
		case "Spanish":
			return SpokenDialect.SPANISH_MEXICO;
		default:
			return SpokenDialect.SWEDISH_SWEDEN;
		}
	}
	
	//return corresponding language abbreviation given the language as a string
	public static String getLanguageAbbreviation(String language) {
		switch (language) {
		case "Catalan":
			return "ca";
		case "Chinese":
			return "zh-CN";
		case "Danish":
			return "da";
		case "Dutch":
			return "nl";
		case "English":
			return "en";
		case "Finnish":
			return "fi";
		case "French":
			return "fr";
		case "German":
			return "de";
		case "Italian":
			return "it";
		case "Japanese":
			return "ja";
		case "Korean":
			return "ko";
		case "Norwegian":
			return "no";
		case "Polish":
			return "pl";
		case "Portuguese":
			return "pt";
		case "Russian":
			return "ru";
		case "Spanish":
			return "es";
		default:
			return "sv";

		}
	}

	//return corresponding language object given the language as a string
	public static Language getLanguage(String language) {
		switch (language) {
		case "Catalan":
			return Language.CATALAN;
		case "Chinese":
			return Language.CHINESE_SIMPLIFIED;
		case "Danish":
			return Language.DANISH;
		case "Dutch":
			return Language.DUTCH;
		case "English":
			return Language.ENGLISH;
		case "Finnish":
			return Language.FINNISH;
		case "French":
			return Language.FRENCH;
		case "German":
			return Language.GERMAN;
		case "Italian":
			return Language.ITALIAN;
		case "Japanese":
			return Language.JAPANESE;
		case "Korean":
			return Language.KOREAN;
		case "Norwegian":
			return Language.NORWEGIAN;
		case "Polish":
			return Language.POLISH;
		case "Portuguese":
			return Language.PORTUGUESE;
		case "Russian":
			return Language.RUSSIAN;
		case "Spanish":
			return Language.SPANISH;
		default:
			return Language.SWEDISH;
		}
	}
}
