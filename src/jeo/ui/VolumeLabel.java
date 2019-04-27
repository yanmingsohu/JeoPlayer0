// CatfoOD 2008-6-30 下午08:58:07

package jeo.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;

import jeo.assistant.Color2;
import jeo.assistant.Tools;


public class VolumeLabel extends JLabel {
	private static final long serialVersionUID = 8670314426210069961L;
	private static final String NOSUPPORT = "nosupport";
	private static final String VOL = "vol ";
	private static final String MUTE = "mute";
	
	private boolean muted = false;
	private Color fontColor = Color2.black;
	private int volume;
	private String v = VOL;
	
	protected VolumeLabel() {
		this.setBackground(Color2.black);
		this.setText("");
		this.setBorder(new LineBorder2(true,true,false,false));
		this.setBounds(new Rectangle(0, 26, 70, 18));
	}
	
	/** 
	 * 不通过界面直接修改音量属性
	 * */
	protected void changeVolume(int i) {
		volume = i;
	}
	
	/**
	 * 设置为新的音量,同时取消静音显示
	 * @param v - 如果v<0显示nosupport.
	 */
	public void setVolume(int v) {
		muted = false;
		volumeChanged = true;
		fontColor = Color2.red.darker();
		repaint();
		new ChangeNumThread(v);
		new hidedThread();
	}
	
	public void setMute(final boolean m) {
		muted = m;
		v = MUTE;
		num1 = num2 = "";
		fontColor = Color2.red.darker();
		if (!muted) {
			new ChangeNumThread(volume);
		}
		repaint();
	}
	
	
	protected void paintComponent(final Graphics g) {
		g.setColor(Color2.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(fontColor);
		
		FontMetrics fm = g.getFontMetrics();
		final int numW = fm.charWidth('0')* num1.length();
		numH = fm.getAscent();
		final int vlen = fm.charsWidth(v.toCharArray(), 0, v.length());
		final int x = ( getWidth()- vlen - numW*2 )/2;
		final int y = getHeight()/ 2+ fm.getDescent()+2;
		
		g.drawString(v, x, y);
		g.drawString(num1, x+vlen, y1+y);
		g.drawString(num2, x+vlen+numW, y2+y);
	}
	
	private int y1;
	private int y2;
	private int numH;
	private String num1="",num2="";
	
	private class ChangeNumThread extends Thread {
		private int newvol;
		public ChangeNumThread(int newVol) {
			if (newVol>=0) {
				v = VOL;
				newvol = newVol;
				start();
			} else {
				v = NOSUPPORT;
				num1 = num2 = "";
				repaint();
			}
		}
		
		public void run() {
			num1 = newvol/10+"";
			num2 = newvol%10+"";
			boolean move2 = newvol/10 != volume/10;
			
			if (newvol>volume) {
				num1 = volume/10+"";
				num2 = volume%10+"";
				move(0, -numH, move2);
				
				num1 = newvol/10+"";
				num2 = newvol%10+"";
				move(getHeight(), 0, move2);
			}
			else if (newvol<volume) {
				num1 = volume/10+"";
				num2 = volume%10+"";
				move(0, getHeight(), move2);
				
				num1 = newvol/10+"";
				num2 = newvol%10+"";
				move(-numH, 0, move2);
			}
			volume = newvol;
			repaint();
		}
		
		private void move(int s, int d, boolean move2) {
			int step = s>d? -1: 1;
			while (s!=d) {
				s+=step;
				y2 = s;
				if (move2) y1 = s;
				repaint();
				Tools.sleep(9);
			}
		}
	}
	
	// 限制hidedThread只有唯一一个
	private boolean hidedStart = false;
	// 起到延时的作用
	private boolean volumeChanged = false;
	
	private class hidedThread extends Thread {
		public hidedThread() {
			if (hidedStart) return;
			hidedStart = true;
			this.setDaemon(true);
			this.setPriority(MIN_PRIORITY);
			start();
		}
		public void run() {
			while (volumeChanged) {
				volumeChanged = false;
				Tools.sleep(1500);
			}
			if (!muted) {
				fontColor = Color2.black;
				repaint();
			}
			hidedStart = false;
		}
	}
}
