package languageprocessors;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

import main.Util;

import com.memetix.mst.language.SpokenDialect;
import com.memetix.mst.speak.Speak;

/**
 * 
 * Speaks a given string
 *
 */
public class Speaker {
	
	private static URL previous = null;

	// Retrieves URL for the specific string in the specified language
	private static URL getURL(String target, SpokenDialect dialect)
			throws Exception {
		return new URL(Speak.execute(target, dialect));
	}

	// Make HTTP Connection to get the InputStream
	private static BufferedInputStream getInputStream(URL url)
			throws IOException {
		return new BufferedInputStream(
				((HttpURLConnection) url.openConnection()).getInputStream());
	}

	// plays audio of given input stream
	private static void playClip(InputStream input) throws Exception {

		AudioListener listener = new AudioListener();

		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(input);

		try {
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(listener);
			clip.open(audioInputStream);
			try {
				clip.start();
				listener.waitUntilDone();
			} finally {
				clip.close();
			}
		} finally {
			audioInputStream.close();
		}
	}

	// ties all the methods together into one easy to call method
	public void speak(String text, String dialect) {
		try {
			previous = getURL(text,Util.getDialect(dialect));
			playClip(new BufferedInputStream(getInputStream(previous)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//repeats the last played audioclip
	public static void repeat() {
		try {
			if (previous == null) {
				playClip(new BufferedInputStream(getInputStream(getURL(
						"Try translating a message first!", Util.getDialect("English")))));
			} else {
				playClip(new BufferedInputStream(getInputStream(previous)));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}

//utility class for speaking
class AudioListener implements LineListener {
	private boolean done = false;

	public synchronized void update(LineEvent event) {
		Type eventType = event.getType();
		if (eventType == Type.STOP || eventType == Type.CLOSE) {
			done = true;
			notifyAll();
		}
	}

	public synchronized void waitUntilDone() throws InterruptedException {
		while (!done) {
			wait();
		}
	}
}
