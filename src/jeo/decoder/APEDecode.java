// CatfoOD 2008-7-1 ÉÏÎç11:15:50

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


import davaguine.jmac.spi.APEAudioFileFormatType;
import davaguine.jmac.spi.tritonus.APEAudioFileReader;

public class APEDecode implements IDecode {

	public boolean canDecode(Type f) {
		return (f.equals(APEAudioFileFormatType.APE) ||
				f.equals(APEAudioFileFormatType.MAC) );
	}

	public AudioInputStream decode(InputStream inf)
			throws UnsupportedAudioFileException, IOException 
	{
		AudioInputStream in = 
			new APEAudioFileReader().getAudioInputStream(inf);
		AudioFormat format1 = in.getFormat();
		
		AudioFormat destFormat = 
			Mixers.getTargetAudioFormat(format1);
		
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
