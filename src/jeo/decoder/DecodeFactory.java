// CatfoOD 2008-6-30 下午01:53:56

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import jeo.assistant.Tools;
import jeo.core.PlayerMachine;


public class DecodeFactory {
	private DecodeFactory() {}
	
	private static IDecode[] ds = new IDecode[] {
			new MP3decode(),
			new FlacDecode(),
			new APEDecode(),
			new VorbisDecode(),
			new SpeexDecode(),
			new SystemDecode(),
	};
	
	public static IDecode[] getDecodes() {
		return ds;
	}
	
	/**
	 * 返回可以解压缩指定文件的解码器<br>
	 * 如果找不到解码器返回null
	 */
	public static IDecode getDecode(File f) {
		try {
			return checkDecode(f);
		} catch (Exception e) {
			Tools.waring(e.getMessage()+" "+f);
		}
		return null;
	}

	/**
	 * 返回可以解压缩指定流的解码器<br>
	 * 如果找不到解码器返回null
	 */
	public static IDecode getDecode(InputStream in) {
		try {
			return checkDecode(AudioSystem.getAudioFileFormat(in).getType());
		} catch (Exception e) {
			Tools.waring(e.getMessage()+" "+in);
		}
		return null;
	}
	
	/**
	 * 创建有缓冲音频流的便捷方法
	 * 从文件file创建有缓冲的音频输入流
	 * 返回的流可以直接放入pm播放
	 * 
	 * @throws IOException - 如果文件读取错误，或者文件不存在抛出这个异常
	 * @throws UnsupportedAudioFileException  - 如果解码错误，或者文件格式不支持抛出这个异常
	 */
	public static AudioInputStream creatBufferStream(PlayerMachine pm, File file) 
	throws UnsupportedAudioFileException, IOException 
	{
		IDecode id = getDecode(file);
		if (id==null) throw new UnsupportedAudioFileException(file.getName());
		InputStream in = pm.creatBufferedInputStream(file);
		return id.decode(in);
	}
	
	private static IDecode checkDecode(Object o) {
		for (int i=0; i<ds.length; ++i) {
			try {
				if (o instanceof File) {
					if (ds[i].canDecode( (File)o ) ) {
						return ds[i];
					}
					o = AudioSystem.getAudioFileFormat( (File)o ).getType();
				}
				
				if (o instanceof AudioFileFormat.Type) {
					if (ds[i].canDecode( (AudioFileFormat.Type)o )) {
						return ds[i];
					} 
				}
				
				// Tools.pl("Decodefactory. unknow format.: "+o);
				
			} catch(Exception e1) {
				// Tools.waring("ID1: "+e1);
				
			} catch(Throwable e) {
				Tools.waring("ID2: "+e);
				remove(ds[i]);
			}
		}
		return null;
	}
	
	private static void remove(IDecode code) {
		IDecode[] newcode = new IDecode[ds.length-1];
		for (int i=0; i<ds.length; ++i) {
			if (ds[i]==code) continue;
			newcode[i] = ds[i];
		}
		ds = newcode;
	}
	
	public static boolean canDecode(File f) {
		return getDecode(f)!=null;
	}
}
