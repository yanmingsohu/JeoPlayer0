// CatfoOD 2008-7-2 ÏÂÎç11:13:40

package jeo.infomation;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import jeo.assistant.Color2;
import jeo.assistant.Tools;
import jeo.assistant.VersionControl;


public final class DefaultInfo extends JPanel {
	private static final long serialVersionUID = 7469711804109911810L;
	private final Font f = new Font(Font.SERIF, Font.BOLD, 30);
	private final Font f2 = new Font(Font.DIALOG_INPUT, Font.BOLD, 15);
	
	public DefaultInfo() {
		setBackground(Color2.black);
		showme();
	}
	
	private void showme() {
		new Thread() {
			public void run() {
				InfoFactory.showInfo(DefaultInfo.this);
			}
		}.start();
	}
	
	public void paint(Graphics g) {
		int x = this.getWidth()/2+ 220;
		int y = 300;
		
		g.setColor(Color2.gray);
		g.setFont(f);
		
		drawString(g, VersionControl.programNameEN, x,y);
		y+=20;
		g.setFont(f2);
		drawString(g, VersionControl.version+
				" lastedit:"+VersionControl.lastEdit, x,y);
		y+=200;
		drawString(g, "CatfoOD 2008-2009", x,y);
		y+=20;
		drawString(g, "Java Project", x,y);
		y+=100;
		drawString(g, "QQ:412475540", x,y);
		y+=20;
		drawString(g, "yanming-sohu@sohu.com", x,y);
		
		drawLine(g, x);
	}
	
	private void drawLine(Graphics g, int x) {
		x+=5;
		g.drawLine(x, 0, x, Tools.getScreenSize().height);
	}
	
	private void drawString(Graphics g, String text, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		int w = fm.charsWidth(text.toCharArray(), 0, text.length());
		g.drawString(text, x-w, y);
	}
}
