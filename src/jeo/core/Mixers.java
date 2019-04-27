// CatfoOD 2008-6-28 上午08:15:09

package jeo.core;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import jeo.assistant.Tools;


public class Mixers {
	private ArrayList<Mixer> list = new ArrayList<Mixer>(10);
	
	protected Mixers() {
		filtrate( getAllMixers() );
	}
	
	public SourceDataLine getMixerLine(Mixer m) throws 
		LineUnavailableException 
	{
		return (SourceDataLine)
			AudioSystem.getLine(isSourceDataLine(m));
	}
	
	public Mixer[] getCanUserMixer() {
		return list.toArray(new Mixer[0]); 
	}
	
	public Mixer getDefaultMixer() {
		if (list.size()<1) return null;
		return list.get(0);
	}
	
	// 初始化可用混频器列表
	private void filtrate(Mixer[] src) {
		list.clear();
		for (int i=0; i<src.length; ++i) {
			if (isSourceDataLine(src[i])!=null) {
				list.add(src[i]);
			}
		}
	}
	
	private Mixer[] getAllMixers() {
		Mixer.Info ts[] = AudioSystem.getMixerInfo();
		Mixer[] mixs = new Mixer[ts.length];
		for (int i=0; i<mixs.length; ++i) {
			mixs[i] = AudioSystem.getMixer(ts[i]);
		}
		return mixs;
	}
	
	/**
	 * 如果指定的混频器有可写入的数据行，则返回行的Info，否则返回null
	 */
	private static final Line.Info isSourceDataLine(Mixer o) {
		Line.Info[] lis = o.getSourceLineInfo();
		for (int i=0; i<lis.length; ++i) {
			if (lis[i].getLineClass() == SourceDataLine.class) {
				return lis[i];
			}
		}
		return null;
	}
	
	/**
	 * 解码元数据流为系统PCM数据流
	 * @param srcFormat
	 * @return
	 */
	public static final AudioFormat 
	getTargetAudioFormat(AudioFormat srcFormat) 
	{
//		float frameRate = 38.28125f; // 27.777779
//		float sampleRate = 44100.0f;
//		int frameSize = 4;
		
		Tools.waring(Mixers.class, "srcFormat: "+srcFormat);
		
		return new AudioFormat(
				 AudioFormat.Encoding.PCM_SIGNED,
				 srcFormat.getSampleRate(),
				 srcFormat.getSampleSizeInBits(),
				 srcFormat.getChannels(),
				 srcFormat.getFrameSize(),
				 srcFormat.getFrameRate(),
				 false
			);
	}
	
	public static final int sampleSizeInBits = 16;
	public static final float sampleRate = 44100.0f;
}
