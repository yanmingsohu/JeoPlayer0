// CatfoOD 2008-6-29 下午04:00:43

package jeo.main;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

import jeo.assistant.Color2;


public class IconFactory {
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	private IconFactory() {
		// 暂时不使用这个方法
	}
	
	public static FactTriangle creatFactTriangle(int i) {
		return new FactTriangle(i);
	}
}

/**
 * 有四个方向的实心图标
 */
class FactTriangle implements Icon {
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	private int size = 5;
	private int x[], y[];
	
	public FactTriangle(final int i) {
		x = new int[0];
		y = new int[0];
		int size2 = size*2;
		if (i==UP) {
			x=new int[]{0,size,size2};
			y=new int[]{size,0,size};
		}
		if (i==DOWN) {
			x=new int[]{0,size,size2};
			y=new int[]{size,size2,size};
		}
		if (i==LEFT) {
			x=new int[]{size,0,size};
			y=new int[]{0,size,size2};
		}
		if (i==RIGHT) {
			x=new int[]{size,size2,size};
			y=new int[]{0,size,size2};
		}
	}
	
	public int getIconHeight() {
		return size+size;
	}
  
	public int getIconWidth() {
		return size+size;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		reCount(x,y);
//		g.setColor(Color2.LIGHT_GRAY);
		g.fillPolygon(this.x, this.y, this.x.length);
	}
	
	private void reCount(int x, int y) {
		if (reCounter) return;
		reCounter = true;
		for (int i=0; i<this.x.length; ++i) {
			this.x[i] += x;
			this.y[i] += y;
		}
	}
	
	private boolean reCounter = false;
}

/**
 * 有两个方向的空心图标
 */
class EmptyTriangle implements Icon {
	public static final boolean UP = true;
	public static final boolean DOWN = false;
	
	private final int thickness = 2;
	private final int size = 5;
	private int x1,y1, x2,y2, y3;
	
	public EmptyTriangle(final boolean way) {
		x1 = 0; y1 = 5;
		x2 = 10; y2 = 5;
		
		if (way==UP) {
			y3 = 0;
		} else {
			y3 = 10;
		}
	}
	
	public int getIconHeight() {
		return size+size;
	}

	public int getIconWidth() {
		return size+size;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		int x3 = x+size;
		for (int i=0; i<thickness; ++i) {
			g.drawLine(x3-i, y3+y, x1+x-i, y1+y);
			g.drawLine(x3+i, y3+y, x2+x+i, y2+y);
		}
	}
}

class RedDot implements Icon {
	public static final int size = 4;
	
	public int getIconHeight() {
		return size;
	}

	public int getIconWidth() {
		return size;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color2.RED);
		g.fillOval(x, y, size, size);
	}
}