// CatfoOD 2008-7-2 下午03:24:23

package jeo.playlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import jeo.assistant.Color2;
import jeo.core.IPositionListener;



public class FileButtonItem extends JButton implements IPositionListener {
	
	private static final long serialVersionUID = -4994001648114333L;
	private Color backColor = Color2.black;
	private Color fontColor = Color2.gray;
	
	private Color fontc;
	private IListItem item;
	private JPanel parent;
	private int currentPosi;
	
	/**
	 * 列表对象
	 * @param item - 播放对象的封装
	 * @param l - 需要一个监听器通知按钮被按下
	 */
	public FileButtonItem(IListItem item, ActionListener l, JPanel parent) {
		this(item.getName(), new Color(150,150,150));
		this.item = item;
		this.parent = parent;
		this.addActionListener(l);	
	}
	
	private FileButtonItem(String text, Color font) {
		this.setText(text);
		this.addMouseListener(new ML());
		this.setThisUI();
		this.fontc = font;
		fontColor = font;
	}
	
	private void setThisUI() {
		this.setUI(new BasicButtonUI() {});
		this.setBorder(new EmptyBorder(0,0,0,0));
		this.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
	}

	public void setSelect(boolean selected) {
		if (selected) {
			fontColor = fontc = Color2.white;
			// 选择为true时，组件不需要重新绘制，由父容器完成
		} else {
			fontColor = fontc = new Color(150,150,150);
			repaint();
		}
	}
	
	/**
	 * 从列表中移出项目
	 */
	public void remove() {
		parent.remove(this);
	}
	
	/**
	 * 取得包装的对象
	 */
	public IListItem getItem() {
		return item;
	}
	
	protected void paintComponent(Graphics g) {
		g.setFont(this.getFont());
		FontMetrics fm = g.getFontMetrics();
		g.setColor(backColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		paintRectType(g, fm);
		paintNameText(g, fm);
		paintPositionLine(g);
	}
	
	protected void paintRectType(Graphics g, FontMetrics fm) {
		g.setColor(fontColor);
		g.drawRect(2, 2, post-2, post-2);
		
		Object t = item.getType();
		if (t instanceof Image) {
			Image img = (Image)t;
			g.drawImage(img, 4, 4, post-6, post-6, this);
		} 
		else if (t instanceof Component) {
			Component com = (Component)t;
			com.setBounds(4, 4, post-6, post-6);
			if (!isAncestorOf(com)) {
				this.removeAll();
				this.add(com);
			}
		}
		else {
			String type = t.toString();
			int x = (48-fm.charsWidth(type.toCharArray(), 0, type.length()))/2;
			int y = (48+fm.getDescent())/2;
			g.drawString(type, x, y);
		}
	}
	
	public static final Image formatImageSize(Image i) {
		return i.getScaledInstance(post-6, post-6, Image.SCALE_SMOOTH);
	}
	
	/** 返回内部图片大小 */
	public static final int getSidecar() {
		return post- 6;
	}

	// 指示器的长度
	final int linesize = 300;
	final int stepsize = linesize/100;
	static final int post = 48;
	final int trigonSize = 5;
	
	protected void paintPositionLine(Graphics g) {
		if (fontc!=Color2.white) return;
		g.setColor(Color2.LIGHT_GRAY);
		g.drawLine(post, post, post+linesize, post);
		
		g.drawLine(post+linesize, post, 
				post+linesize-trigonSize, post-trigonSize);
		
		g.drawLine(post+linesize-trigonSize, post-trigonSize,
				post+linesize-trigonSize, post);
		
		g.setColor(Color2.white);
		g.drawLine(post, post, post+currentPosi*stepsize, post);
	}
	
	protected void paintNameText(Graphics g, FontMetrics fm) {
		String text = item.getName();
		int x = 53;
		int y = 23 + fm.getDescent();
		g.setColor(fontColor);
		g.setFont(getFont());
		g.drawString(text, x, y);
		
		String exInfo = item.getExtendInfo();
		if (exInfo!=null) {
			g.setColor(Color2.RED);
			g.drawString(exInfo, x, y+fm.getHeight());
		}
	}
	
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, 50);
	}
	
	public Dimension getMinimumSize() {
		return new Dimension(PlaylistWindow.W, 50);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(PlaylistWindow.W, 50);
	}

	private class ML extends MouseAdapter {
		public void mouseExited(MouseEvent e) {
			fontColor = fontc;
			backColor = Color2.black;
			repaint();
		}
		
		public void mouseEntered(MouseEvent e) {
			fontColor = Color.white;
			backColor = Color2.GRAY;
			repaint();
		}
		
		public void mousePressed(MouseEvent e) {
			fontColor = Color2.white;
			repaint();
		}
		
		public void mouseReleased(MouseEvent e) {
			fontColor = fontc;
			repaint();
		}
	}

	@Override
	public void positionChange(int i) {
		currentPosi = i;
		repaint();
	}
	
	public boolean equals(Object o) {
		if (o==null) return false;
		if (o instanceof FileButtonItem) {
			return item.equals(((FileButtonItem) o).item);
		}
		if (o instanceof IListItem) {
			return item.equals(o);
		}
		return false;
	}
	
//	/**
//	 * 注意这个方法影响数组的比较方法
//	 */
//	public final String toString() {
//		return item.getObject().toString();
//	}
//	
//	public final boolean equals(Object o) {
//		if (this==o) return true;
//		return toString().equals(o.toString());
//	}
}
