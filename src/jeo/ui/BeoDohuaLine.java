// CatfoOD 2009-5-12 ����12:20:10

package jeo.ui;

import java.awt.Graphics;

import javax.swing.AbstractButton;

import jeo.assistant.Tools;


/**
 * ��ʾ��ť����
 */
public class BeoDohuaLine {
	/**
	 * �̶����������
	 */
	public void paintLine(Graphics g, AbstractButton b) {
		if (!started) return;

		java.awt.Color oc = g.getColor();
		g.setColor(b.getForeground());
		g.drawLine(linex, 0, linex, b.getHeight());
		g.setColor(oc);
	}
	
	/**
	 * ��ʼ���ƶ���
	 */
	public synchronized void startPaint(AbstractButton b) {
		if (started) return;
		started = true;
		new PaintThread(b);
	}

	private int linex = 0;
	private boolean started = false;
	
	private class PaintThread extends Thread {
		int stopx = 0;
		AbstractButton bu;
		int sleeptime = 30;
		int step = (int)(Math.random()*3)+1;
		
		PaintThread(AbstractButton b) {
			stopx = b.getWidth();
			bu = b;
			this.setDaemon(true);
			this.setPriority(MIN_PRIORITY);
			this.start();
		}
		
		public void run() {
			linex = 0;
			while (linex<stopx) {
				linex+=step;
				bu.repaint();
				Tools.sleep(sleeptime);
			}
			started = false;
		}
	}
}
