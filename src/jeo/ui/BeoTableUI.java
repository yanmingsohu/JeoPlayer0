// CatfoOD 2008-7-4 обнГ03:18:41

package jeo.ui;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;

import jeo.assistant.Color2;


public class BeoTableUI extends BasicTableUI {
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setBackground(Color2.black);
		c.setForeground(Color2.white);
	}
}
