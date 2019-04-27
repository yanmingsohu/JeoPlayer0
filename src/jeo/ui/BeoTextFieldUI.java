// CatfoOD 2008-7-1 обнГ09:15:46

package jeo.ui;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;

import jeo.assistant.Color2;



public class BeoTextFieldUI extends BasicTextFieldUI {
	public void installUI(JComponent c) {
		super.installUI(c);
		JTextField textf = (JTextField)c;
		textf.setBackground(Color2.black);
		textf.setForeground(Color2.LIGHT_GRAY);
		textf.setCaretColor(Color2.LIGHT_GRAY);
		textf.setSelectionColor(Color2.gray);
		textf.setSelectedTextColor(Color2.black);
		UIFactory.setBorder(c);
	}
}
