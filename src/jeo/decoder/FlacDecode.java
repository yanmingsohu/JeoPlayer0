// CatfoOD 2008-7-1 ÉÏÎç11:03:47

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat.Type;

import jeo.core.Mixers;

import org.kc7bfi.jflac.sound.spi.Flac2PcmAudioInputStream;
import org.kc7bfi.jflac.sound.spi.FlacAudioFileReader;


public class FlacDecode implements IDecode {

	public AudioInputStream decode(InputStream inf)
			throws UnsupportedAudioFileException, IOException {
		AudioInputStream in = 
			new FlacAudioFileReader().getAudioInputStream(inf);
		AudioFormat format1 = in.getFormat();
		
		AudioFormat destFormat = Mixers.getTargetAudioFormat(format1);;
		
		//return AudioSystem.getAudioInputStream(destFormat, in);
		return new Flac2PcmAudioInputStream(inf, destFormat, 4);
	}

	@Override
	public String makeKey(File f) {
		return null;
	}

	@Override
	public boolean canDecode(File f) {
		String name = f.getName();
		if (name!=null) {
			String sub = name.substring(name.length()-4);
			//if (sub.equalsIgnoreCase("flac")) return true;
		}
		return false;
	}

	@Override
	public boolean canDecode(Type f) {
		return false;
	}

}
