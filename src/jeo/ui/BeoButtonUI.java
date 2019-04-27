// CatfoOD 2008-7-1 ÏÂÎç09:32:12

package jeo.ui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import jeo.assistant.Color2;


public class BeoButtonUI extends BasicButtonUI {
	private BeoDohuaLine line;
	
	public void installUI(JComponent c) {
		c.setBorder(new EmptyBorder(0,0,0,0));
		c.setBackground(Color2.black);
		c.setForeground(Color2.LIGHT_GRAY);
		line = new BeoDohuaLine();
		super.installUI(c);
	}
	

	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		g.setColor(b.getForeground().darker());
//		g.fillRect(0, 0, b.getWidth(), b.getHeight());
		int y = b.getHeight() -3;
		g.drawLine(0, y, b.getWidth(), y);
	}
	
//	protected void paintFocus(Graphics g, AbstractButton b, 
//			Rectangle viewRect,Rectangle textRect, Rectangle iconRect) 
//	{
////		g.setColor(Color2.GRAY);
////		g.fillRect(0,0, viewRect.width, viewRect.height);
//	}
	
	protected void paintText(Graphics g,
            AbstractButton b, Rectangle textRect, String text)
	{
		ButtonModel mode = b.getModel();
		int y = textRect.height/2+ g.getFontMetrics().getDescent();
		if (mode.isRollover()) {
//			g.setColor(Color2.GRAY);
//			g.fillRect(0, 0, textRect.width, textRect.height);
			g.setColor(b.getForeground());
			g.drawString(text, textRect.x, y);
			line.startPaint(b);
		} else {
			g.setColor(b.getBackground());
			g.fillRect(0, 0, b.getWidth(), b.getHeight());
			g.setColor(b.getForeground().darker());
			g.drawString(text, textRect.x, y);
		}
		line.paintLine(g, b);
	}
	
	protected void paintIcon(Graphics g, JComponent c, Rectangle r) {
		g.setColor(Color2.LIGHT_GRAY);
		((JButton)c).getIcon().paintIcon(c, g, r.x, r.y);
	}
}
