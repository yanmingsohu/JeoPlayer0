// CatfoOD 2008-7-1 ÏÂÎç01:24:48

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat.Type;

import jeo.core.Mixers;

import org.xiph.speex.spi.SpeexAudioFileReader;
import org.xiph.speex.spi.SpeexFileFormatType;


public class SpeexDecode implements IDecode {

	public boolean canDecode(Type f) {
		return f.equals(SpeexFileFormatType.SPEEX);
	}

	public AudioInputStream decode(InputStream inf)
			throws UnsupportedAudioFileException, IOException 
	{
		AudioInputStream in = 
			new SpeexAudioFileReader().getAudioInputStream(inf);
		AudioFormat format1 = in.getFormat();
		
		AudioFormat destFormat = Mixers.getTargetAudioFormat(format1);
		
		return AudioSystem.getAudioInputStream(destFormat, in);
	}

	@Override
	public String makeKey(File f) {
		return null;
	}

	@Override
	public boolean canDecode(File f) {
		return false;
	}

}
