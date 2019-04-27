// CatfoOD 2008-7-1 ÉÏÎç10:09:15

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat.Type;

public class SystemDecode implements IDecode {
	private final static AudioFileFormat.Type[] types = 
		new AudioFileFormat.Type[] {
			AudioFileFormat.Type.AIFC,
			AudioFileFormat.Type.AIFF,
			AudioFileFormat.Type.AU,
			AudioFileFormat.Type.SND,
			AudioFileFormat.Type.WAVE,
	};

	public boolean canDecode(Type f) {
		for (int i=0; i<types.length; ++i) {
			if (f.equals(types[i])) return true;
		}
		return false;
	}

	public AudioInputStream decode(InputStream inStream)
			throws UnsupportedAudioFileException, IOException 
	{
		return AudioSystem.getAudioInputStream(inStream);
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
