package main;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;

import languageprocessors.Interpreter;
import languageprocessors.Recorder;
import languageprocessors.Speaker;
import languageprocessors.Translator;

import com.memetix.mst.speak.Speak;

/**
 * 
 * Starts application and calls relevant methods
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		//use UTF-8 encoding so foreign characters will register
		System.setProperty("file.encoding","UTF-8");
		try {
			Field charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null,null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {}
		
		//set the Client ID / Client Secret for access to Microsoft's services
		Speak.setClientId("TransMate007");
		Speak.setClientSecret("CmYDaT6gdP7yqAdZBb9+xjEbfeJ3ztGEzHyIJ0W/XPY=");

		//initialize variables
		GUI TransMateGUI = new GUI();
		Translator translator = new Translator();
		Interpreter interpreter = new Interpreter();
		Speaker speaker = new Speaker();
		Recorder recorder = new Recorder();
		String translated;
		
		TransMateGUI.init();

		//program will automatically close when GUI is closed, thus an infinite while loop is used
		while (true) {
			
			//obtain relevant information from the GUI
			ArrayList<Object> information = Util.getInformationFromGUI();
			String firstLanguage = (String) information.get(0), secondLanguage = (String) information.get(2);
			boolean recordingFirst = (Boolean) information.get(1), recordingSecond = (Boolean) information.get(3);
			
			//check if user pressed either record button
			if ((recordingFirst || recordingSecond) && !information.contains(null)) {
				
				//update GUI
				TransMateGUI.queueMessage(translator.translate("Recording...", "English", firstLanguage), translator.translate("Recording...", "English", secondLanguage));
				
				//record sound
				recorder.record(recordingFirst);
				
				//update GUI
				TransMateGUI.queueMessage(translator.translate("Processing...", "English", firstLanguage), translator.translate("Processing...", "English", secondLanguage));
				
				//convert the recorded WAV file to a FLAC file
				Util.WAVToFLAC(recordingFirst);
				
				//interpret words from the recorded sound
				String target = interpreter.interpret((recordingFirst) ? firstLanguage : secondLanguage, recordingFirst);
				
				if (recordingFirst) {
					
					//translate the words from firstLanguage to secondLanguage
					translated = translator.translate(target, firstLanguage, secondLanguage);
					
					//update GUI
					TransMateGUI.queueMessage(target, translated);
					
					//speak the translated message
					speaker.speak(translated, secondLanguage);
					
				} else {
					
					//translate the words from secondLanguage to firstLanguage
					translated = translator.translate(target, secondLanguage, firstLanguage);
					
					//update GUI
					TransMateGUI.queueMessage(translated, target);
					
					//speak the translated message
					speaker.speak(translated, firstLanguage);
				}
			}
		}
	}
	

}
