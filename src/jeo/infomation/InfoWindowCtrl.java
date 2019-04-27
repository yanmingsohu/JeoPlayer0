// CatfoOD 2008-7-2 下午10:52:49

package jeo.infomation;

import java.awt.Dimension;

import jeo.assistant.IExit;
import jeo.assistant.Parabola;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;
import jeo.playlist.PlaylistFactory;
import jeo.usefuhelp.UserHelper;


public class InfoWindowCtrl implements IExit, IHide {
	private InfoWindow win;
	private boolean hiding = false;
	private boolean action = false;
	private Thread showThreadRefers = null;
	
	protected InfoWindowCtrl(InfoWindow i) {
		win = i;
		win.setAlwaysOnTop(true);
		SystemExit.registerExit(this);
	}
	
	public Thread show() {
		hiding = false;
		win.setSize(Tools.getScreenSize());
		win.setLocation(0, -win.getHeight());
		win.setVisible(true);
		
		showThreadRefers = new showThread();
		showThreadRefers.start();
		return showThreadRefers;
	}
	
	public void waitingForShow() {
		Tools.waitExit(showThreadRefers);
	}
	
	public Thread hide() {
		Thread t = new hideThread();
		t.start();
		return t;
	}
	
	private final static int step = 45;
	
	private class showThread extends Thread {
		public void run() {
			if (action) return;
			action = true;
			Dimension screen = Tools.getScreenSize();
			int x=0,w=screen.width,h=screen.height;
			Parabola para = new Parabola(-h, 0, step);
			
			for (int y=-h; y<0; y+=(4+para.get(y))) {
				x = PlaylistFactory.getPlaylistWindowWidth();
				w = screen.width - x;
				win.setBounds(x, y, w, h);
				Tools.sleep(10);
			}
			win.setLocation(win.getX(),0);
			new widthThread().start();
			
			UserHelper.show(3, "按下 ‘关闭’ 会取消所作的选择", 9999, -200);
			action = false;
		}
	}
	
	private class hideThread extends Thread {
		public void run() {
			if (action) return;
			action = true;
			hiding = true;
			Dimension screen = Tools.getScreenSize();
			int x=0,w=screen.width,h=screen.height;
			Parabola para = new Parabola(-h, 0, step);
			
			for (int y=0; y>-h; y-=(4+para.get(y))) {
				x = PlaylistFactory.getPlaylistWindowWidth();
				w = screen.width - x;
				win.setBounds(x, y, w, h);
				Tools.sleep(10);
			}
			win.setVisible(false);
			action = false;
		}
	}
	
	private final int gap = 1;
	
	private class widthThread extends Thread {
		public void run() {
			int x = PlaylistFactory.getPlaylistWindowWidth();
			int w = Tools.getScreenSize().width;

			while (!hiding) {
				w = Tools.getScreenSize().width;
				x = PlaylistFactory.getPlaylistWindowWidth();
				win.setBounds(x-gap, win.getY(), w-x+gap+gap, win.getHeight());
				Tools.sleep(20);
			}
		}
	}

	@Override
	public void exit() {
		Tools.waitExit(hide());
	}

	@Override
	public int getPriority() {
		return 11;
	}

	@Override
	public void waitForOK() {
		while (win.isVisible()) {
			Tools.sleep(100);
		}
	}
}
