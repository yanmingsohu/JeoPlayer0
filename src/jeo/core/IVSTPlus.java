// CatfoOD 2009-5-31 上午09:18:44

package jeo.core;

import javax.sound.sampled.AudioInputStream;

public interface IVSTPlus {	
	/** 打开VST面板,实现需要启动新的线程 */
	void openEditor();
	void openAllVst();
	void closeAllVst();
	boolean vstIsOpen();
	
	void setBufferSize(int size);
	void setSampleRate(float rate);
	
	/**
	 * 截断一个输入流，并把这个输入流作为输出，返回经过VST效果后的数据
	 * 可以返回 null
	 */
	AudioInputStream getOutput(AudioInputStream in);
}
