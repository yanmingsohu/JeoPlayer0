// CatfoOD 2008-7-15 下午10:34:27

package jeo.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileFormat;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import jeo.assistant.Tools;


public class testMp3idv3 {

	/**
	 * album 唱片标题
	 * author 艺术家
	 */
	public static void main(String[] args) {
		MpegAudioFileReader mp3 = new MpegAudioFileReader();
		try {
			File f = new File("H:\\ipod music\\日文\\日文杂歌\\hiro - 愛が泣いてる.mp3");
			MpegAudioFileFormat format = 
				(MpegAudioFileFormat)mp3.getAudioFileFormat(f);
			
			Map m = format.properties();
			Iterator iter = m.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				Tools.waring(key+ " \t\t"+ m.get(key));
			}
		} catch (Exception e) {
			Tools.waring(e);
		}
	}

}
