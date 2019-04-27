// CatfoOD 2008-6-29 上午09:55:45

package jeo.assistant;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jeo.infomation.DefaultInfo;
import jeo.main.PlayerWindowCtrl;


public class SystemIcon implements ActionListener, IExit {
	private TrayIcon ticon;	
	private MenuItem exit;
	private MenuItem open;
	private MenuItem info;
	private static PlayerWindowCtrl pwc = null;
	
	public SystemIcon() {
		ticon = new TrayIcon(getImage());
		ticon.setImageAutoSize(true);
		ticon.setToolTip(
				VersionControl.programNameEN+
				" "+VersionControl.version);
		ticon.setPopupMenu(getPopupMenu());
		ticon.addActionListener(this);
		SystemExit.registerExit(this);
		try {
			SystemTray.getSystemTray().add(ticon);
		} catch (AWTException e) {
			Tools.showError(e);
		}
	}
	
	private Image getImage() {
		return Tools.creatImage(beo_gif.getImage());
	}
	
	private PopupMenu getPopupMenu() {
		PopupMenu pm = new PopupMenu();
		
		exit = new MenuItem("退出");
		exit.addActionListener(this);
		open = new MenuItem("更新版本...");
		open.addActionListener(this);
		info = new MenuItem("关于...");
		info.addActionListener(this);
		
		pm.add(info);
		pm.addSeparator();
		pm.add(open);
		pm.addSeparator();
		pm.add(exit);
		return pm;
	}

	public void actionPerformed(ActionEvent e) {
		final Object source = e.getSource();
		if (source==exit) {
			Tools.exit();
		} 
		else if (source==ticon) {
			if (pwc!=null) {
				pwc.popup();
			}
		}
		else if (source==open) {
			Tools.openWeb();
		}
		else if (source==info) {
			new DefaultInfo();
		}
	}
	
	public static final void removeIcon() {
		try {
			TrayIcon[] tis = SystemTray.getSystemTray().getTrayIcons();
			for (TrayIcon t: tis) {
				SystemTray.getSystemTray().remove(t);
			}
		} catch (Exception e1) {}
	}
	
	public static final void setShowCtrl(PlayerWindowCtrl pwc) {
		SystemIcon.pwc = pwc;
	}

	public void exit() {
		removeIcon();
	}

	public int getPriority() {
		return -100;
	}
}
