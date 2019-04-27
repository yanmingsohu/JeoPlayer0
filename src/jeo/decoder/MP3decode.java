// CatfoOD 2008-6-30 ÏÂÎç01:22:08

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat.Type;


import javazoom.spi.mpeg.sampled.file.MpegAudioFileFormat;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import javazoom.spi.mpeg.sampled.file.MpegFileFormatType;
import jeo.assistant.Tools;
import jeo.core.Mixers;

public class MP3decode implements IDecode {

	public AudioInputStream decode(InputStream inf) 
	throws UnsupportedAudioFileException, IOException 
	{
		AudioInputStream in = 
			new MpegAudioFileReader().getAudioInputStream(inf);
		AudioFormat format1 = in.getFormat();
		
		AudioFormat destFormat = 
			Mixers.getTargetAudioFormat(format1);

		return AudioSystem.getAudioInputStream(destFormat, in);
	}

	public boolean canDecode(final Type f) {
		return (f.equals(MpegFileFormatType.MP3) || 
				f.equals(MpegFileFormatType.MPEG) );
	}

	@Override
	public String makeKey(File f) {
		MpegAudioFileReader mp3 = new MpegAudioFileReader();
		try {
			MpegAudioFileFormat format = 
				(MpegAudioFileFormat)mp3.getAudioFileFormat(f);
			
			Map m = format.properties();
			String al = (String) m.get("album");
			String au = (String) m.get("author");
			return (al!=null? al: "") +" "+ (au!=null? au: "");
		} catch (Exception e) {
			Tools.waring(e);
		}
		return null;
	}

	@Override
	public boolean canDecode(File f) {
		return false;
	}
	
}
