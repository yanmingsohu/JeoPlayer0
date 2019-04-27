// CatfoOD 2009-5-11 ����04:28:53

package jeo.infomation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import jeo.assistant.Color2;
import jeo.ui.UIFactory;


// �ڲ�����ʾ����ȴ����Ĵ���
public class WaitPanel extends JPanel {
	private boolean initfinish = false;
	
	public WaitPanel() {
		this.setUI(UIFactory.getPanelUI());
		this.setLayout(new BorderLayout());
	}
	
	public Component add(Component comp) {
		initfinish = true;
		return super.add(comp);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (initfinish) return;
		
		final Font f = new Font(Font.DIALOG, Font.BOLD, 20);
		g.setColor(Color2.gray);
		g.setFont(f);
		final String t = "���ڶ�ȡ ...";
		final FontMetrics fm = g.getFontMetrics();
		
		int w = fm.charsWidth(t.toCharArray(), 0, t.length());
		int x = this.getWidth()/2;
		int y = this.getHeight()/2;
		
		g.drawString(t, x-w, y);
	}
}