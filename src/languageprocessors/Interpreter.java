package languageprocessors;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import main.Util;

/**
 * 
 * Interprets words spoken in audio files and returns the words as a string
 *
 */
public class Interpreter {

	//interprets audio and returns a string containing words spoken in the audio clip
	public String interpret(String language, boolean user) {
		try {
			//Get connection for interpreting the flac file
			HttpsURLConnection connection = getConnection(language);
			
			//sends the query with post
			query(connection, Util.getUser(user));
			
			//return result from query
			return getResult(connection);
			
		} catch (Exception e) {
			return "Oops. Something went wrong.";
		}
	}

	private String getResult(HttpsURLConnection connection) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		//first line of input unnecessary
		reader.readLine();
		
		String result = reader.readLine();
		
		if(result==null){
			return "No distinct words were heard. Please try again.";
		}
		
		reader.close();
		
		//parse result into useful strings
		result = result.substring(result.indexOf("{\"transcript\":\""),result.indexOf("],\"final\":"));
		result = result.replaceAll("\\{\"transcript\":|\\}|\"", "");

		return result.split(",")[0];
	}
	
	private void query(HttpsURLConnection connection, String fileName) throws IOException {
		
		File file = new File(fileName + ".flac");
		
		byte[] buffer = new byte[(int) file.length()];
		
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(buffer);
		fileInputStream.close();
		
		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		writer.write(buffer);
		writer.flush();
		writer.close();
	}
	
	//sends API call to Google speech services
	private HttpsURLConnection getConnection(String language) throws IOException {
		URL url = new URL(
				"https://www.google.com/speech-api/v2/recognize?output=json&lang="
						+ Util.getLanguageAbbreviation(language)
						+ "&key=" + Util.getGoogleApiKey());
		
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		
		//set properties on the connection
		connection.setRequestProperty("Content-Type", "audio/x-flac; rate=44100");
		connection.setDoOutput(true);
		connection.setAllowUserInteraction(false);
		
		return connection;
	}

}
