// CatfoOD 2008-6-30 ����01:23:49

package jeo.decoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * ��ý��������ӿ�
 */
public interface IDecode {
	/**
	 * �����ǰ�Ľ��������Խ�ѹ����ǰ���ͷ���true
	 * @param f - ��������
	 */
	boolean canDecode(AudioFileFormat.Type f);
	
	boolean canDecode(File f);
	
	/**
	 * �õ�ǰ��������ѹ���������������ؽ�ѹ���AudioInputStream
	 * @param in - �������������
	 * @return PCM��ʽ��ѹ����������
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	AudioInputStream decode(InputStream in)
		throws UnsupportedAudioFileException, IOException;
	
	/**
	 * ��������ļ��Ĺؼ���
	 * �ļ�����Ϣ��Ч����null
	 */
	String makeKey(File f);
}
