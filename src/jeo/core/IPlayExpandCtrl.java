// CatfoOD 2008-6-29 ����07:17:19

package jeo.core;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

interface IPlayExpandCtrl {
	
	/** �������ֹͣ������ */
	void addStopListener(IStopListener listen);
	/** ��������ֹͣ������ */
	void removeStopListener(IStopListener listen);
	/** ���û�������С */
	void setBuff(int size);
	/** ȡ�õ�ǰ��������С */
	int getBuff();
	/** ȡ�ÿ��õĻ�Ƶ�� */
	Mixer[] getCanUserMixer();
	/** ȡ�õ�ǰʹ�õĻ�Ƶ�� */
	Mixer getCurrentMixer();
	
	/** 
	 * �ı䵱ǰʹ�õĻ�Ƶ�� 
	 * @throws LineUnavailableException 
	 * */
	void useThisMixer(Mixer m) throws LineUnavailableException;
}
