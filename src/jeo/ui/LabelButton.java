// CatfoOD 2008-6-29 ÏÂÎç12:57:28

package jeo.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;

import jeo.assistant.Color2;
import jeo.main.ButtonGroup;
import jeo.main.PlayerWindow;


public class LabelButton extends JButton {
	private static final long serialVersionUID = -4994001648114333L;
	private Color backColor = Color2.black;
	private Color fontColor = Color2.gray;
	private BeoDohuaLine line;
	
	private Color fontc;
	
	public LabelButton() {
		this("", new Color(150,150,150));
	}
	
	public LabelButton(Color font) {
		this("", font);
	}
	
	public LabelButton(String text) {
		this(text, new Color(150,150,150));
	}
	
	public LabelButton(String text, Color font) {
		this.setText(text);
		this.addMouseListener(new ML());
		this.getModel().addChangeListener(new CL());
		this.setThisUI();
		this.fontc = font;
		fontColor = font;
		line = new BeoDohuaLine();
	}
	
	public static Color getDefaultColor() {
		return new Color(150,150,150);
	}
	
	public void setFontColor(Color fc) {
		fontc = fc;
	}
	
	private void setThisUI() {
		this.setUI(new BasicButtonUI() {});
		this.setBorder(new EmptyBorder(0,0,0,0));
		this.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(backColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setFont(this.getFont());
		g.setColor(fontColor);
		FontMetrics fm = g.getFontMetrics();
		String text = getText();
		int fw = fm.charsWidth(text.toCharArray(), 0, text.length());
		int fh = fm.getDescent()+2;
		g.drawString(getText(), (getWidth()-fw)/2, getHeight()/2+fh);
		line.paintLine(g, this);
		
		Icon icon = getIcon();
		if (icon == null) return;
		icon.paintIcon(this, g, 
				(getWidth()-icon.getIconWidth())/2,
				(getHeight()-icon.getIconHeight())/2);
	}
	
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, 15);
	}
	
	public Dimension getMinimumSize() {
		return new Dimension(PlayerWindow.WIDTH, 15);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(PlayerWindow.WIDTH, 15);
	}
	
	
	private class CL implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			if (!getModel().isRollover()) {
				resumeState();
			}
		}
	}
	
	
	private class ML extends MouseAdapter {
		public void mouseExited(MouseEvent e) {
			resumeState();
		}
		
		public void mouseEntered(MouseEvent e) {
			fontColor = new Color(230,230,230);
			if (group!=null) group.mouseEnter(); 
			mouseEnter();
			line.startPaint(LabelButton.this);
		}
		
		public void mousePressed(MouseEvent e) {
			fontColor = Color2.white;
			repaint();
			line.startPaint(LabelButton.this);
		}
		
		public void mouseReleased(MouseEvent e) {
			fontColor = fontc;
			repaint();
		}
	}
	
	
	protected void resumeState() {
		fontColor = fontc;
		if (group!=null) group.mouseExit(); 
		mouseExit();
	}
	
	public void mouseExit() {
		backColor = Color2.black;
		repaint();
	}
	
	public void mouseEnter() {
		backColor = Color2.GRAY;
		repaint();
	}
	
	public void addGroup(ButtonGroup g) {
		group = g;
	}
	
	private ButtonGroup group = null;
}
