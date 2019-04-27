// CatfoOD 2008-6-28 上午07:27:45

package jeo.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Control;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import jeo.assistant.Tools;


public class Core {
	private SourceDataLine sourceline = null;
	private final int BUFFSIZE = 2000 ;
	private volatile boolean stop = true;
	private volatile boolean pause = false;
	
	private int cacheSize = 2*1024*1024;
	
	public Core(SourceDataLine out) {
		if (out!=null) {
			setSourceDataLine(out);
			positionThread.start();
		} else {
			throw new NullPointerException("SourceDataLine is null");
		}
	}
	
	/**
	 * 重新设置用来播放音乐的行
	 */
	public void setSourceDataLine(SourceDataLine src) {
		if (src!=null) {
			sourceline = src;
		}
	}
	
	/**
	 * 设置新的缓冲区大小(字节)，在播放下一首文件时启用，默认2MB
	 * @param size - size>128时缓冲区被设置，否则不进行任何操作
	 */
	public void setBufferSize(int size) {
		if (size>128) {
			cacheSize = size;
		}
	}
	
	public int getBuffferSize() {
		return cacheSize;
	}
	
	public void play(final AudioInputStream music) 
	{
		playThread = new Thread() { 
			public void run() {
				try {
					rawplay(sourceline, music);
				} catch (Exception e) {
					e.printStackTrace();
					//Tools.showError(e);
				} 
			}
		};
		playThread.start();
	}
	
	private Thread playThread = null;
	
	/**
	 * 当方法完成时，line并没有被关闭
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void rawplay(SourceDataLine line, AudioInputStream din) 
			throws IOException, LineUnavailableException
	{
	  byte[] data = new byte[BUFFSIZE];
	  line.open();
	  line.start();
	  int nBytesRead = 0;
	  nBytesRead = din.read(data, 0, data.length);
	  stop = false;
	  pause = false;
	  
	  while (nBytesRead != -1 && !stop)
	  {
		line.write(data, 0, nBytesRead);
		nBytesRead = din.read(data, 0, data.length);
		datafilter(data, nBytesRead);
		
		while (pause) {
			line.drain();
			line.flush();
			sleep(200);
		}
	  }
	  line.drain();
	  line.flush();
	  line.stop();
	  din.close();
	  if (!stop) {
		  playThread = null;
		  informLinstener();
	  }
	}
	
	private float outd = 1;
	private boolean gradeout = false;
	
	private void datafilter(byte[] bs, int len) {
		if (gradeout) {
			for (int i=0; i<len; ++i) {
				bs[i] *= outd;
			}
		}
	}
	
	public void gradeoutAndStop() {
		gradeout = true;
		while (outd>0.01) {
			outd -= 0.02;
			Tools.sleep(10);
		}
		stop();
		outd = 1;
		gradeout = false;
	}
	
	public void stop() {
		if (stop) return;
		stop = true;
		pause = false;
		
		if (playThread!=null) {
			Tools.waitExit(playThread);
			playThread = null;
		}
	}
	
	public void pause() {
		pause = true;
	}
	
	public void resume() {
		pause = false;
	}
	
	public boolean isPlay() {
		return !stop;
	}
	
	public boolean isPause() {
		return pause;
	}
	
	public Control[] getControls() {
		return sourceline.getControls();
	}
	
	private static final void sleep(long l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {}
	}
	
	/**
	 * 通知StopListener当前的音乐停止了
	 */
	public void informLinstener() {
		if (listen!=null) {
			listen.stop(this.getClass());
		}
	}
	
	public void removeStopListener(IStopListener listen) {
		if (listen==this.listen) {
			this.listen = null;
		}
	}
	
	public void addStopListener(IStopListener listen) {
		this.listen = listen;
	}
	
	public void addPositionListener(IPositionListener listen) {
		posListen = listen;
	}
	
	public void removePositionListener(IPositionListener listen) {
		posListen = null;
	}
	
	private IStopListener listen = null;
	private IPositionListener posListen = null;
	
	private PositionThread positionThread = new PositionThread();
	
	private class PositionThread extends Thread {
		private BufferAudioStream bas;
		
		public PositionThread() {
			this.setDaemon(true);
			this.setPriority(NORM_PRIORITY-2);
		}
		public void run() {
			int p;
			while (!released) {
				if (posListen!=null && bas!=null) {
					try {
						p = bas.computerPosition();
						posListen.positionChange(p);
					} catch (IOException e) {
						bas = null;
						posListen.positionChange(0);
					}
				}
				Core.sleep(1000);
			}
		}
		public void setPositionChanged(BufferAudioStream bas) {
			this.bas = bas;
		}
	}
	
	private class BufferAudioStream extends BufferedInputStream {
		private long size;
		
		public BufferAudioStream(File f) throws FileNotFoundException {
			super( new FileInputStream(f), cacheSize );
			size = f.length();
		}
		
//		public int read() throws IOException {
////			computerPosition();
//			return super.read();
//		}
//		
//		public int read(byte[] b,int off, int len) throws IOException {
////			computerPosition();
//			return super.read(b, off, len);
//		}
		
		private int computerPosition() throws IOException {
			return (int)( ((float)size-available()) /size*100);
		}
	}
	
	/**
	 * 创建一个带指示器的文件缓冲
	 */
	public InputStream creatBufferedInputStream(File music)
		throws FileNotFoundException 
	{
		BufferAudioStream bas = new BufferAudioStream(music);
		positionThread.setPositionChanged(bas);
		return bas;
	}
	
	/**
	 * 释放内存
	 */
	protected void release() {
		released = true;
		stop = true;
		pause = false;
//		sourceline = null;
//		positionThread = null;
//		listen = null;
//		posListen = null;
	}
	
	private boolean released = false;
}
