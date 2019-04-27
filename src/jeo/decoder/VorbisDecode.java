// CatfoOD 2008-7-1 ÉÏÎç10:55:47

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat.Type;


import javazoom.spi.vorbis.sampled.file.VorbisAudioFileReader;
import javazoom.spi.vorbis.sampled.file.VorbisFileFormatType;
import jeo.core.Mixers;

public class VorbisDecode implements IDecode {

	public boolean canDecode(Type f) {
		return (f.equals(VorbisFileFormatType.VORBIS) ||
				f.equals(VorbisFileFormatType.OGG) );
	}

	public AudioInputStream decode(InputStream inf)
			throws UnsupportedAudioFileException, IOException 
	{
		AudioInputStream in = new VorbisAudioFileReader().getAudioInputStream(inf);
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
