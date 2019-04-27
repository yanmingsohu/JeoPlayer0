// CatfoOD 2008-6-29 下午02:31:33

package jeo.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * 可以控制上下左右是否有编框
 */
public class LineBorder2 extends AbstractBorder {
	private boolean up,down,left,right;
	private Color borderColor = new Color(96,96,96);
	private int thickness = 1;
	
	public LineBorder2(boolean up, boolean down, boolean left, boolean right) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	public void paintBorder(Component c,
            Graphics g,
            int x,
            int y,
            int width,
            int height) 
	{
		g.setColor(borderColor);
		if (up) {
			g.drawLine(x, y, x+width, y);
		}
		if (down) {
			g.drawLine(x, y+height-1, x+width, y+height-1);
		}
		if (left) {
			g.drawLine(x, y, x, y+height);
		}
		if (right) {
			g.drawLine(width-1, y, width-1, y+height);
		}
	}
	
	public Insets getBorderInsets(Component c)       {
		return new Insets(thickness, thickness, thickness, thickness);
	}
}
