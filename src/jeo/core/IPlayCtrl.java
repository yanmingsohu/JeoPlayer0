// CatfoOD 2008-6-28 ����10:10:15

package jeo.core;

import javax.sound.sampled.AudioInputStream;

public interface IPlayCtrl {
	void play(AudioInputStream f);
	void play();
	void stop();
	void pause();
	
	/** �����л� */
	void mute();
	/** ���� */
	void volumeHoist();
	/** С�� */
	void volumeFall();
}
