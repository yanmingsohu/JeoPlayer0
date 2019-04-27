// CatfoOD 2008-6-28 ����10:35:32

package jeo.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JLabel;

import jeo.assistant.Tools;
import jeo.ui.VolumeFactory;
import jeo.ui.VolumeLabel;



public class PlayerMachine implements 
		IPlayCtrl, IPlayExpandCtrl, LineListener 
{
	private final int MAXVOLUME = 74;
	
	private Mixers mix = new Mixers();
	private Mixer currentMix;
	private Core core;
	private FloatControl volume;
	private BooleanControl mute;
	
	private float volumeStep = -1;
	
	/** �������ϵ�������ǩ */
	private VolumeLabel vl = VolumeFactory.get();
	
	public PlayerMachine() throws LineUnavailableException {
		currentMix = mix.getDefaultMixer();
		
		if (currentMix!=null) {
			SourceDataLine src = mix.getMixerLine(currentMix);
			src.addLineListener(this);
			core = new Core(src);
		} else {
			throw new LineUnavailableException("û�п��õ��豸");
		}
	}
	
	private void initControl(SourceDataLine src) {
		try {
			src.isControlSupported(FloatControl.Type.MASTER_GAIN);
			volume = (FloatControl) 
					src.getControl(FloatControl.Type.MASTER_GAIN);
			mute = (BooleanControl) 
					src.getControl(BooleanControl.Type.MUTE);
			volumeStep = -1;
//			changeVolume(0);
		} catch(IllegalArgumentException e) {
			volume = null;
			mute = null;
			e.printStackTrace();
		}
		System.out.println("\n"+ src.getLineInfo()+ 
				"\nControl: \n\t"+ volume +"\n\t"+ mute+ "\n\t");
	}

	public void update(LineEvent event) {
		if (event.getType()==LineEvent.Type.OPEN) {
			SourceDataLine line = (SourceDataLine)event.getSource();
			initControl(line);
		}
	}

	public void play(final AudioInputStream in) {
		stop();
		core.play(in);
	}

	public void setBuff(int size) {
		core.setBufferSize(size);
	}

	/** ֹͣ�����ͷ��ļ�����,���� */
	public void stop() {
		core.gradeoutAndStop();
	}
	
	/** ��ͣ���ţ����� */
	public void pause() {
		if (core.isPause()){
			core.resume();
		} else if (core.isPlay()) {
			core.pause();
		}
	}
	
	/** �Ƿ���ͣ���� */
	public boolean isPause() {
		return core.isPause();
	}
	
	/** �Ƿ����ڲ��ţ���ͣ״̬Ҳ���ڲ��� */
	public boolean isPlay() {
		return core.isPlay();
	}

	/** ����ͣ״̬�ָ����� */
	public void play() {
		if (core.isPause()) {
			core.resume();
		}
	}
	
	public void volumeHoist() {
		changeVolume(1);
	}
	
	public void volumeFall() {
		changeVolume(-1);
	}
	
	private void changeVolume(int add) {
		if (volume==null) {
			vl.setVolume(-1);
			return;
		}
		if (muted) return;

		if (volumeStep<0) {
			volumeStep = ( volume.getMaximum()- volume.getMinimum() )/MAXVOLUME;
		}
		
		float currc = volume.getValue() + (add * volumeStep);
		if ( currc<volume.getMaximum() && currc>volume.getMinimum() ) {
			volume.setValue( currc );
			vl.setVolume(getVolume() + add);
		}
	}
	
	/**
	 * ���ص�ǰ��������С 0~74;
	 * �ο� MAXVOLUME
	 */
	public int getVolume() {
		float base = Math.abs( volume.getMinimum() );
		return (int)Math.abs( MAXVOLUME * (
				( volume.getValue() + base ) / ( volume.getMaximum() + base )
						) );
	}
	
	private boolean muted = false;
	
	public void mute() {
		if (mute==null) {
			vl.setVolume(-1);
			return;
		}
		muted = !muted;
		mute.setValue(muted);
		vl.setMute(muted);
	}

	public void removeStopListener(IStopListener listen) {
		core.removeStopListener(listen);
	}
	public void addStopListener(IStopListener listen) {
		core.addStopListener(listen);
	}
	
	public void addPositionListener(IPositionListener listen) {
		core.addPositionListener(listen);
	}
	
	public void removePositionListener(IPositionListener listen) {
		core.removePositionListener(listen);
	}

	public int getBuff() {
		return core.getBuffferSize();
	}
	
	public JLabel getVolumeLabel() {
		return this.vl;
	}

	public Mixer[] getCanUserMixer() {
		return mix.getCanUserMixer();
	}

	public void useThisMixer(Mixer m) throws LineUnavailableException {
		Tools.checkNull(m);
		currentMix = m;
		SourceDataLine src = mix.getMixerLine(m);
		src.addLineListener(this);
		core.setSourceDataLine(src);
	}

	public Mixer getCurrentMixer() {
		return currentMix;
	}
	
	/**
	 * �ͷ��ڴ�
	 */
	public void release() {
		core.release();
	}
	
	/**
	 * ����һ����ָʾ�����ļ�����
	 */
	public InputStream creatBufferedInputStream(File music)
		throws FileNotFoundException 
	{
		return core.creatBufferedInputStream(music);
	}
}
