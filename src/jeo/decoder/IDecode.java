// CatfoOD 2008-6-30 下午01:23:49

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 流媒体解码器接口
 */
public interface IDecode {
	/**
	 * 如果当前的解码器可以解压缩当前类型返回true
	 * @param f - 流的类型
	 */
	boolean canDecode(AudioFileFormat.Type f);
	
	boolean canDecode(File f);
	
	/**
	 * 用当前解码器解压缩数据流，并返回解压后的AudioInputStream
	 * @param in - 待解码的数据流
	 * @return PCM格式解压缩的数据流
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	AudioInputStream decode(InputStream in)
		throws UnsupportedAudioFileException, IOException;
	
	/**
	 * 返回相关文件的关键字
	 * 文件的信息无效返回null
	 */
	String makeKey(File f);
}
