// CatfoOD 2008-7-1 обнГ08:44:53

package jeo.ui;


import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicLabelUI;

import jeo.assistant.Color2;


public class BeoLabelUI extends BasicLabelUI {
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setBackground(Color2.black);
		c.setForeground(Color2.white);
//		UIFactory.setBorder(c);
	}
}
