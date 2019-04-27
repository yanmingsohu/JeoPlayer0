// CatfoOD 2008-6-30 обнГ01:02:00

package jeo.test;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import jeo.core.PlayerMachine;
import jeo.player.mediaplayer.MediaPlayer;


public class testAutioFormat {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		File f = new File("test.mp3");
		
		MediaPlayer mp = new MediaPlayer();
		
		mp.play(f);
	}

	static void p(Object o) {
		System.out.println(o);
	}
}
