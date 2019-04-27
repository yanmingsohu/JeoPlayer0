// CatfoOD 2008-6-29 上午08:01:26

package jeo.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jeo.assistant.Color2;
import jeo.usefuhelp.UserHelper;



public class IndicatorWindow extends Window {
	private static final long serialVersionUID = -8070261528724045546L;
	
	public static final boolean LIFT = true;
	public static final boolean RIGHT= false;
	
	private final int W=14,H=14;
	private final boolean position;
	private boolean isShow = false;
	private boolean hiden = false;
	
	/**
	 * @param post - 出现的位置由 LIFT RIGHT
	 */
	public IndicatorWindow(boolean post) {
		super(null);
		position = post;
//		if (post==LIFT) {
//			icon = IconFactory.creatFactTriangle(IconFactory.RIGHT);
//		} else {
//			icon = IconFactory.creatFactTriangle(IconFactory.LEFT);
//		}
		this.setAlwaysOnTop(true);
		this.setSize(W, H);
		this.setLocation(computerLocation());
		this.setVisible(true);
		this.setBackground(Color2.black);
		setMouseListener();
		new MousePointer().start();
	}
	
	/**
	 * 设置是否隐藏
	 * @param h - 隐藏不再显示
	 */
	public void setHide(boolean h) {
		hiden = h;
	}
	
	private void setMouseListener() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				rectColor = Color2.GREEN;
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
				rectColor = Color2.blue;
				repaint();
			}
		});
	}
	
	private Point computerLocation() {
		Dimension d = getScreenSize();
		Point p = new Point();
		p.y = d.height/2;
		if (position==LIFT) {
			p.x = 0-W;
		} else {
			p.x = d.width;
		}
		return p;
	}
	
	private Rectangle computerPopupRectangle() {
		final int WID = 5;
		final int offset = 100;
		if (position==LIFT) {
			return new Rectangle(0, offset, 
					WID, getScreenSize().height-offset-offset);
		} else {
			return new Rectangle(getScreenSize().width-WID, 
					offset, WID, getScreenSize().height-offset-offset);
		}
	}
	
	private Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	private class MousePointer extends Thread {
		public MousePointer() {
			this.setDaemon(true);
			this.setPriority(MIN_PRIORITY);
		}
		public void run() {
			Rectangle rv;
			Point p;
			while (true) {
				p = MouseInfo.getPointerInfo().getLocation();
				rv = computerPopupRectangle();
				if (rv.contains(p) && !hiden) {
					mouseY = p.y-H/2;
					pop(mouseY);
				}
				IndicatorWindow.sleep(200);
			}
		}
	}

	private int mouseY =0;
	
	private class HideThread extends Thread {
		public void run() {
			Rectangle rv = new Rectangle();
			Point p;
			while (isShow) {
				p = MouseInfo.getPointerInfo().getLocation();
				IndicatorWindow.this.getBounds(rv);
				if (!rv.contains(p)) {
					hiden(mouseY);
				}
				IndicatorWindow.sleep(200);
			}
		}
	}
	
	private void pop(int y) {
		if (isShow) return;
		this.setVisible(true);
		Point p = computerLocation();
		if (position==LIFT) {
			grade(-W, 0, y);
			UserHelper.show(3, "<< 点击打开播放列表 ", 25, y);
		} else {
			grade(p.x, p.x-W, y);
			UserHelper.show("点击，打开主面板 >>", -25, y);
		}
		isShow = true;
		new HideThread().start();
	}
	
	private void hiden(int y) {
		if (!isShow) return;
		Point p = computerLocation();
		if (position==LIFT) {
			grade(0, -W, y);
		} else {
			grade(p.x-W, p.x, y);
		}
		this.setVisible(false);
		isShow = false;
	}
	
	private void grade(int sx, int dx, int y) {
		int step = sx>dx? -1: 1;
		while (sx!=dx) {
			this.setLocation(sx, y);
			sx+=step;
			sleep(13);
		}
	}
	
	private Color rectColor = Color2.BLUE;
	private final int ow=5, oh=5;
	
	public void paint(Graphics g) {
		g.setColor(rectColor);
		g.drawRect(0, 0, W-1, H-1);
		g.setColor(Color2.red);
		g.fillOval((W-ow)/2+1, 1+(H-oh)/2, ow, oh);
//		icon.paintIcon(this, g, (W-icon.getIconWidth())/2, 
//								(H-icon.getIconHeight())/2);
	}
	
	private static final void sleep(int t) {
		try {
			Thread.sleep(t);
		} catch (Exception e) {}
	}
	
//	private Icon icon;
}
