// CatfoOD 2008-7-2 下午02:15:13

package jeo.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import jeo.assistant.Tools;
import jeo.main.IconFactory;


public class BeoScrollBarUI extends BasicScrollBarUI {
	private  JScrollBar jbar;
	
	public void installUI(JComponent c) {
		jbar = ((JScrollBar)c);
		jbar.addMouseListener(ML);
		
		super.installUI(c);
	}
	
	// 增长
	protected JButton createIncreaseButton(int orientation) {
		JButton b = new JButton(
				IconFactory.creatFactTriangle(IconFactory.DOWN));
		b.setUI(UIFactory.getButtonUI());
		b.addActionListener(AL);
		return b;
	}
	
	// 减少
	protected JButton createDecreaseButton(int orientation) {
		JButton b = new JButton(
				IconFactory.creatFactTriangle(IconFactory.UP));
		b.setUI(UIFactory.getButtonUI());
		b.addActionListener(AL);
		return b;
	}
	
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) 
	{
		g.setColor(thumbc);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) 
	{
		g.setColor(trankc);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	private MouseAdapter ML = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			new showThread().start();
		}
		public void mouseExited(MouseEvent e) {
			new hideThread().start();
		}
	};
	
	private ActionListener AL = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new hideThread().start();
		}
	};
	
	private class showThread extends Thread {
		public void run() {
		//synchronized(lock) {
			boolean f = S<E;
			for (tc=S; f?tc<E:tc>E; tc+=step) {
				trankc = new Color(tc,tc,tc+variety);
				if (jbar!=null) jbar.repaint();
				Tools.sleep(2);
			}
		//	}
		}
	}
	
	private class hideThread extends Thread {
		public void run() {
		//synchronized(lock) {
			boolean f = S<E;
			for (tc=E; f?tc>S:tc<S; tc-=step) {
				trankc = new Color(tc,tc,tc+variety);
				if (jbar!=null) jbar.repaint();
				Tools.sleep(2);
			}
		//	}
		}
	}
	
	private Object lock = new Object(){};
	
	static {
		if (Tools.STYLE) {
			variety = 5;
			S = 250;
			E = 170;
			step = -1;
		} else {
			variety = 50;
			S = 40;
			E = 150;
			step = 1;
		}
	}
	
	private static final int variety;
	private static final int S, E;
	private static final int step;
	private int tc = S;
	private Color trankc = new Color(S,S,S);
	private Color thumbc = new Color(S+variety,S,S);
}
