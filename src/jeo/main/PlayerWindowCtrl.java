// CatfoOD 2008-6-29 下午01:59:18

package jeo.main;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jeo.assistant.IExit;
import jeo.assistant.Parabola;
import jeo.assistant.SystemExit;
import jeo.assistant.SystemIcon;
import jeo.assistant.Tools;
import jeo.ui.IndicatorWindow;
import jeo.usefuhelp.UserHelper;


public class PlayerWindowCtrl implements IExit {
	private PlayerWindow win;
	private IndicatorWindow indicat;
	
	protected PlayerWindowCtrl(PlayerWindow pw) {
		win = pw;
		Dimension d = Tools.getScreenSize();
		win.setSize(PlayerWindow.WIDTH, d.height);
		win.setLocation(d.width-PlayerWindow.WIDTH, 0);
		win.setVisible(true);
		creatIndicator();
		new hidenThread();
		SystemExit.registerExit(this);
		SystemIcon.setShowCtrl(this);
	}
	
	private void creatIndicator() {
		indicat = new IndicatorWindow(IndicatorWindow.RIGHT);
		indicat.setHide(true);
		indicat.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				new showThread();
			}
		});
	}
	
	public void popup() {
		win.setVisible(true);
		indicat.setHide(true);
		Dimension d = Tools.getScreenSize();
		win.setSize(PlayerWindow.WIDTH, d.height);
		move(win.getX(), d.width-PlayerWindow.WIDTH);
		new hidenThread();
	}
	
	public void hide() {
		win.getCloseMenuCtrl().exit();	
		Dimension d = Tools.getScreenSize();
		win.setSize(PlayerWindow.WIDTH, d.height);
		if (move(win.getX(), d.width)) {
			win.setVisible(false);
			indicat.setHide(false);
		}
		hiding = false;
	}
	
	private boolean movebreak = false;
	private boolean hiding = false;
	private int stepSize = 3;
	
	
	/**
	 * @return - 如果move被中断返回 false;
	 */
	private boolean move(int s, int d) {
		int step = s>d? -stepSize: stepSize;
		int base = s>d? -1: 1;
		Parabola para = new Parabola(s, d, step);
		while (Math.abs(s-d)>stepSize && !movebreak) {
			s+=base+para.get(s);
			win.setLocation(s, 0);
			Tools.sleep(10);
		}
		if (!movebreak) {
			win.setLocation(d, 0);
		}
		boolean mb = !movebreak;
		movebreak = false;
		return mb;
	}
	
	private void breakHiden() {
		if (!hiding) return;
		movebreak = true;
		while (movebreak) {
			Tools.sleep(50);
		}
		new showThread();
	}
	
	private class hidenThread extends Thread {
		public hidenThread() {
			this.setDaemon(true);
			start();
		}
		
		public void run() {
			Tools.sleep(2000);
			Rectangle rv = win.getBounds();
			while (rv.contains(MouseInfo.getPointerInfo().getLocation())) {
				Tools.sleep(200);
			}
			hiding = true;
			new breakHideThread();
			hide();
			UserHelper.show("把鼠标移动到屏幕边缘 >>", -20, 300);
		}
	}
	
	private class showThread extends Thread {
		public showThread() {
			this.setDaemon(true);
			start();
		}
		
		public void run() {
			popup();
			UserHelper.show("选择一个播放类型（音乐／收音机）>>", -70, 50);
		}
	}
	
	private class breakHideThread extends Thread {
		public breakHideThread() {
			this.setDaemon(true);
			start();
		}
		
		public void run() {
			Rectangle rv = win.getBounds();
			while (hiding) {
				rv = win.getBounds();
				Tools.sleep(200);
				if (rv.contains(MouseInfo.getPointerInfo().getLocation())) {
					breakHiden();
					break;
				}
			}
		}
	}

	public void exit() {
		hide();
	}

	public int getPriority() {
		return 10;
	}
}
