// CatfoOD 2008-7-1 обнГ08:29:33

package jeo.ui;


import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicPanelUI;

import jeo.assistant.Color2;


public class BeoPanelUI extends BasicPanelUI {
	public void installUI(JComponent c) {
		super.installUI(c);
//		UIFactory.setBorder(c);
		c.setBackground(Color2.black);
		c.setForeground(Color2.white);
	}
}
