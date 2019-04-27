// CatfoOD 2008-6-30 ÉÏÎç08:41:15

package jeo.main;

import java.awt.Rectangle;

import javax.swing.SwingConstants;

import jeo.assistant.IExit;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;
import jeo.ui.LabelButton;


public class CenterPanelCtrl implements IMainPanelCtrl, IsetPlayer, IExit {
	private CenterPanel win;
	private IMainPanelCtrl currentPlayer;
	
	protected CenterPanelCtrl(CenterPanel cp) {
		win = cp;
		currentPlayer = new DefaultPlayer();
		win.getMenuPane().setCenterPanelCtrl(this);
		win.getMenuPane().insertMenus(this);
		SystemExit.registerExit(this);
	}
	
	public void setCurrentPlayer(IMainPanelCtrl player) {
		currentPlayer = player;
		win.getMenuPane().setRootMenus(player);
		checkExitButton();
	}

	
	public void downKey() {
		currentPlayer.downKey();
	}

	
	public void leftKey() {
		currentPlayer.leftKey();
	}

	
	public void mute() {
		currentPlayer.mute();
	}

	
	public void rightKey() {
		currentPlayer.rightKey();
	}

	
	public void stop() {
		currentPlayer.stop();
	}

	
	public void upKey() {
		currentPlayer.upKey();
	}
	
	private boolean stopd = false;
	private Thread cvt = null;
	protected void changeVolume(int i) {
		if (i==0) {
			stopd = true;
			return;
		}
		if (cvt!=null) Tools.waitExit(cvt);
		stopd = false;
		cvt = new CV(i);
	}
	
	private class CV extends Thread {
		boolean vd;
		CV(int i) {
			vd = i<0;
			start();
		}
		public void run() {
			while (!stopd) {
				if (vd) {
					volumedown();
				} else {
					volumeup();
				}
				Tools.sleep(160);
			}
		}
	};
	
	public void volumedown() {
		currentPlayer.volumedown();
	}

	
	public void volumeup() {
		currentPlayer.volumeup();
	}

	public void play() {
		currentPlayer.play();
	}
	
	public LabelButton[] getMenus(IinsertMenu i) {
		LabelButton jLabel10 = new LabelButton();
		jLabel10.setBounds(new Rectangle(0, 248, 70, 15));
		jLabel10.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel10.setText("setup");
		jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				win.getMenuPane().insertMenus(currentPlayer);
				checkExitButton();
			}
		});
		return new LabelButton[]{jLabel10};
	}
	
	
	public void exitbutton() {
		MenuPaneln menu = win.getMenuPane();
		if (menu.onlyOne()) {
			Tools.exit();
		} else {
			menu.returnMenu();
		}
		checkExitButton();
	}
	
	public void checkExitButton() {
		LabelButton ex = win.getExitButton();
		MenuPaneln menu = win.getMenuPane();
		if (menu.onlyOne()) {
			ex.setText("");
			ex.setIcon(new RedDot());
		} else {
			ex.setText("exit");
			ex.setIcon(null);
		}
	}

	public int getPriority() {
		return 5;
	}

	public void exit() {
		while(win.getMenuPane().returnMenu()) {
			Tools.sleep(200);
		}
		//Tools.sleep(700);
	}

}

class DefaultPlayer implements IMainPanelCtrl {
	public void downKey() {}
	public void leftKey() {}
	public void mute() {}
	public void play() {}
	public void rightKey() {}
	public void stop() {}
	public void upKey() {}
	public void volumedown() {}
	public void volumeup() {}
	
	public LabelButton[] getMenus(IinsertMenu i) {
		LabelButton[] jls = new LabelButton[] {
			new LabelButton("Please"), 
			new LabelButton("Choose"), 
			new LabelButton("a Player"),
			new LabelButton(""),
			new LabelButton("Catfood"),
		};
		return jls;
	}
}