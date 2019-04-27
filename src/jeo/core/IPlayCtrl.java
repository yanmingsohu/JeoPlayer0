// CatfoOD 2008-6-28 ÉÏÎç10:10:15

package jeo.core;

import javax.sound.sampled.AudioInputStream;

public interface IPlayCtrl {
	void play(AudioInputStream f);
	void play();
	void stop();
	void pause();
	
	/** ¾²ÒôÇĞ»» */
	void mute();
	/** ´óÉù */
	void volumeHoist();
	/** Ğ¡Éù */
	void volumeFall();
}
