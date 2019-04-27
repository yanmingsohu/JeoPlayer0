// CatfoOD 2008-7-1 ÏÂÎç08:36:52

package jeo.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Mixer;
import javax.swing.JComboBox;

import jeo.assistant.Tools;
import jeo.core.PlayerMachine;


public class ConfigDialogCtrl implements ActionListener {
	private ConfigDialog win;
	private PlayerMachine pm;
	
	protected ConfigDialogCtrl(ConfigDialog c, PlayerMachine p) {
		win = c;
		pm = p;
		initComboBox();
		initBufferSize();
		initListener();
	}
	
	private void initComboBox() {
		JComboBox box = win.getJComboBox();
		Mixer[] ms = pm.getCanUserMixer();
		for (int i=0; i<ms.length; ++i) {
			box.addItem(new MixerPackage(ms[i]));
		}
		box.setSelectedItem(new MixerPackage(pm.getCurrentMixer()) );
	}
	
	private void initBufferSize() {
		win.getJTextField().setText( pm.getBuff()+" bytes" );
	}
	
	private void initListener() {
		win.getJButton().addActionListener(this);
		win.getJButton1().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==win.getJButton1()) {
			confirm();
		}
		win.setVisible(false);
		win.dispose();
	}
	
	private void confirm() {
		try {
			pm.useThisMixer( ((MixerPackage)
					win.getJComboBox().getSelectedItem()).getMixer());
			int buff = MemUnit.getSize(win.getJTextField().getText());
			if (buff>0) {
				pm.setBuff(buff);
			}
		} catch (Exception e) {
			Tools.showError(e);
		}
	}
	
	private class MixerPackage {
		private Mixer m;
		public MixerPackage(Mixer m) {
			this.m = m;
		}
		public Mixer getMixer() {
			return m;
		}
		public String toString() {
			return m.getMixerInfo().getName();
		}
		public boolean equals(Object obj) {
			if (obj instanceof MixerPackage) {
				return ((MixerPackage)obj).m == m;
			}
			return obj == m;
		}
	}
}

class MemUnit {
	private static MemUnit[] mus = new MemUnit[] {
		new MemUnit("", 1),
		new MemUnit("b", 1),
		new MemUnit("byte", 1),
		new MemUnit("bytes", 1),
		
		new MemUnit("kb", 1024),
		new MemUnit("kbyte", 1024),
		new MemUnit("kbytes", 1024),
		
		new MemUnit("m", 1024*1024),
		new MemUnit("mbyte", 1024*1024),
		new MemUnit("mbytes", 1024*1024),
	};
	
	public static int getSize(String s) {
		if (s==null || s.trim().length()<1) return -1;
		s = s.toLowerCase();
		for (int i=0; i<mus.length; ++i) {
			if (s.matches("[0-9]+[\\s]{0,}"+mus[i].unit)) {
				s = s.replaceAll(mus[i].unit, "").trim();
				return Integer.parseInt(s)* mus[i].size;
			}
		}
		return -1;
	}
	
	// ---------------------- instance --------------------------
	private String unit;
	private int size;
	private MemUnit(String unit, int size) {
		this.unit = unit;
		this.size = size;
	}
}