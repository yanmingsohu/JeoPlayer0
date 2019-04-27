// CatfoOD 2009-6-1 下午12:23:36

package jeo.core;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;

import jeo.assistant.IExit;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;



public final class VSTFactory implements IVSTPlus, IExit {
	
	private final static String VSTConf = "vstturnon";
	private final int BUFFERSIZE = 24 * 1024;
	private static IVSTPlus ins = null;
	
	/** 不会返回null */
	public static final IVSTPlus get() {
		if (ins==null) {
			try {
				System.loadLibrary("nativesvs");
				ins = new VSTFactory();
			} catch(Throwable e) {
				ins = new NullVst();
			}
		}
		return ins;
	}
	
	private VSTFactory() {
		loadconf();
		vstIsOpen();
		SystemExit.registerExit(this);
	}
	
	private void loadconf() {
		String set = Tools.getConfig(VSTConf);
		if (set!=null) {
			if (set.equalsIgnoreCase("true")) {
				openAllVst();
			} else {
				closeAllVst();
			}
		}
	}

	@Override
	public void openEditor() {
		new Thread() {
			public void run() {
				openEdit();
			}
		}.start();
	}
	
	private native static final byte[] process(byte[] b, int size);
	
	@Override
	public native void closeAllVst();

	@Override
	public native void openAllVst();

	
	public native void openEdit();

	@Override
	public native void setBufferSize(int size);

	@Override
	public native void setSampleRate(float rate);

	@Override
	public native boolean vstIsOpen();
	
	
	@Override
	public void exit() {
		Tools.setConfig(VSTConf, vstIsOpen()+"");
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public AudioInputStream getOutput(AudioInputStream in) {
		return new AIS(in);
	}
	
	private class AIS extends AudioInputStream {
		private InputStream ina;
		
		private byte[] buffer = new byte[BUFFERSIZE];
		private int point;
		private int length;
		private boolean vstisopen = false;
		
		public AIS(AudioInputStream in) {
			super( in, in.getFormat(), in.getFrameLength());
			ina = in;
			setBufferSize(BUFFERSIZE);
			setSampleRate( in.getFormat().getSampleRate() );
		}
		
		public int read() throws IOException {	
			if (point>=0) {
				process();
				if (point>=0)
					return buffer[point++];
			}
			
			return -1;
		}
		
		public int read(byte[] b) throws IOException {
			return read(b, 0, b.length);
		}
		
		public int read(byte[] b, int off, int len) throws IOException {
			if (point<0) return -1;
			
			len = len - (len % frameSize);
			int readlen = 0;
			for (int i=off; i<len; ++i) {
				
				b[i] = (byte) read();

				if (point<0) break;
				readlen++;
			}
			return readlen;
		}
		
		public void mark(int readlimit) {
			vstisopen = false;
			ina.mark(readlimit);
		}
		
		public void reset() throws IOException {
			vstisopen = true;
			ina.reset();
		}
		
		private void process() throws IOException {
			if (point>=length) {
				int len = ina.read(buffer);
				if (len>0) {
					vstisopen = vstIsOpen(); 
					if (vstisopen) { 
						buffer = VSTFactory.process(buffer, len);
					}
					
					point = 0;
					length = len;
				} else {
					point = -1;
					length = -1;
				}
			}
		}
	}

}


class NullVst implements IVSTPlus {

	@Override
	public void closeAllVst() {
	}

	@Override
	public AudioInputStream getOutput(AudioInputStream in) {
		return in;
	}

	@Override
	public void openAllVst() {
	}

	@Override
	public void openEditor() {
		Tools.showError("VST初始化错误，不能使用VST插件");
	}

	@Override
	public void setBufferSize(int size) {
	}

	@Override
	public void setSampleRate(float rate) {
	}

	@Override
	public boolean vstIsOpen() {
		return false;
	}
}