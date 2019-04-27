// CatfoOD 2008-7-3 ÉÏÎç10:13:03

package jeo.ui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicFileChooserUI;

import jeo.assistant.Color2;


public class BeoFileChooserUI extends BasicFileChooserUI {
	public BeoFileChooserUI(JFileChooser b) {
		super(b);
	}
	
	public JPanel getAccessoryPanel() {
		System.out.println(" rPanel.");
		
		JPanel p = new JPanel();
		p.setBackground(Color2.black);
		return p;
	}
	
	protected JButton getApproveButton(JFileChooser fc) {
		System.out.println(fc+" rButton.");
		
		JButton b = new JButton();
		b.setUI(UIFactory.getButtonUI());
		return b;
	}
}
