package gui;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundBoard {

	/*
	 * This manages the audio palyers
	 */

	private static SimpleAudioPlayer musicPlayer;
	private static String currentMusic = "Medieval_Feast2.wav";
	private static boolean isMusicMuted;
	private static boolean isSoundsMuted;

	public SoundBoard() {
		// set up music player
		Music("Medieval_Feast2.wav");
	}

	public static void Music(String fileName) {
		if (isMusicMuted == false) {
			try {
				musicPlayer = new SimpleAudioPlayer("/" + fileName, true);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void StopMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		musicPlayer.stop();
	}

	public static void MuteMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		StopMusic();
		isMusicMuted = true;
	}

	public static void MuteSounds() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		isSoundsMuted = true;
	}

	public static void UnmuteMusic() {
		isMusicMuted = false;
		UpdateMusic(currentMusic, true);
	}

	public static void UnmuteSounds() {
		isSoundsMuted = false;
	}

	public static void UpdateMusic(String fileName, boolean justUnmuted) {

		// Check if the game is muted
		if (isMusicMuted == true) {
			return;
		}
		// Check if the music is the same as the one currently playing
		if (fileName == currentMusic) {
			// Check if the game was just unmuted
			if (justUnmuted == false) {
				return;
			}
		}

		// Update the music
		currentMusic = fileName;
		try {
			StopMusic();
			Music(fileName);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void playSound(String inputtedFilePath) {
		if (isSoundsMuted == true) {
			return;
		}
		SimpleAudioPlayer.playSound(inputtedFilePath);
	}
}
