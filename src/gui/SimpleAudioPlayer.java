package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

/*import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;*/

public class SimpleAudioPlayer implements ActionListener{
	
	// Much of this code is derived from: https://www.geeksforgeeks.org/play-audio-file-using-java/

	/*
	 * This creates the audio player
	 */
	
	// to store current position
	Long currentFrame;
	Clip clip;

	// current status of clip
	String status;

	AudioInputStream audioInputStream;
	static String filePath;

	private Timer timer;
	private int delay = 3000;

	// constructor to initialize streams and clip
	public SimpleAudioPlayer(String filePath, Boolean loop)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		// create AudioInputStream object
		InputStream audioSrc = getClass().getResourceAsStream(filePath);
		// add buffer for mark/reset support
		InputStream bufferedIn = new BufferedInputStream(audioSrc);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
		audioInputStream = audioStream;

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);

		if (loop == true) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			timer = new Timer(delay, this);
			timer.start();
		}
	}

	public static void playSound(String inputtedFilePath) {
		try {
			SimpleAudioPlayer audioPlayer;
			if (inputtedFilePath.contains("/")) {
				audioPlayer = new SimpleAudioPlayer(inputtedFilePath, false);
			} else {
				audioPlayer = new SimpleAudioPlayer("/" + inputtedFilePath, false);
			}
			audioPlayer.play();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	// Method to play the audio
	public void play() {
		// start the clip
		clip.start();

		status = "play";
	}

	// Method to pause the audio
	public void pause() {
		if (status.equals("paused")) {
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	// Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (status.equals("play")) {
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}

	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}

	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}

	// Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (c > 0 && c < clip.getMicrosecondLength()) {
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}

	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(filePath));
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			stop();
			timer.stop();
			timer = null;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}