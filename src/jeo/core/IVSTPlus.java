// CatfoOD 2009-5-31 ����09:18:44

package jeo.core;

import javax.sound.sampled.AudioInputStream;

public interface IVSTPlus {	
	/** ��VST���,ʵ����Ҫ�����µ��߳� */
	void openEditor();
	void openAllVst();
	void closeAllVst();
	boolean vstIsOpen();
	
	void setBufferSize(int size);
	void setSampleRate(float rate);
	
	/**
	 * �ض�һ�������������������������Ϊ��������ؾ���VSTЧ���������
	 * ���Է��� null
	 */
	AudioInputStream getOutput(AudioInputStream in);
}
