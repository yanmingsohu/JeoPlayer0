// CatfoOD 2008-7-1 обнГ09:07:04

package jeo.ui;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxUI;

import jeo.assistant.Color2;


public class BeoComboBoxUI extends BasicComboBoxUI {
	public void installUI(JComponent c)  {
		super.installUI(c);
		c.setBackground(Color2.black);
		c.setForeground(Color2.LIGHT_GRAY);
		UIFactory.setBorder(c);
//		JComboBox box = (JComboBox)c;
	}
}
