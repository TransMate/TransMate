package languageprocessors;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import main.Util;

/**
 * 
 * Records audio from microphone in WAV format
 *
 */
public class Recorder implements Runnable {
 
    File wavFile;
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
 
    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
 
    public void run() {
        try {
        	
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.exit(0);
            }
            
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            
            // start capturing
            line.start();   
            AudioInputStream audioInputStream = new AudioInputStream(line);
            
            // start recording
            AudioSystem.write(audioInputStream, fileType, wavFile);
 
        } catch (LineUnavailableException |IOException e) {
            e.printStackTrace();
        }
    }
    
    //end recording
    private void finish() {
        line.stop();
        line.close();
    }
 
    public void record(boolean user) {
    	
    	//set appropriate name for the WAV file
    	wavFile = new File(Util.getUser(user)+".wav");
    	
    	//start recording
        new Thread(this).start();
        
        //check if record was pressed again
        while((user)?(Boolean)Util.getInformationFromGUI().get(1):(Boolean)Util.getInformationFromGUI().get(3));
        
        //stop recording
        this.finish();
    }
}