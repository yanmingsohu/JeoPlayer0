package jeo.decoder;

import jeo.assistant.IExit;
import jeo.assistant.SystemExit;

// CatfoOD 2009-5-11 ÏÂÎç12:58:27

public class WmaPlayer implements IExit {
	private String dllname = "nativesvs";
	public  final static int MAXVOLUME = 100;
	
	public WmaPlayer() throws DecoderException {
		initDll();
		SystemExit.registerExit(this);
	}
	
	private void initDll() throws DecoderException {
		try {
			System.loadLibrary(dllname);
		} catch (Throwable e) {
			throw new DecoderException("wma player init error: "+e.getMessage());
		}
	}
	
	/** vol: 0~100 */
	public native void setVolume(int vol);
	
	public native boolean play(String url);
	
	public native void pause();
	
	public native void resume();
	
	public native void stop();

	@Override
	public void exit() {
		stop();
	}

	@Override
	public int getPriority() {
		return 1;
	}
}


