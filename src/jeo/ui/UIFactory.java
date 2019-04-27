// CatfoOD 2008-7-1 ÏÂÎç08:28:43

package jeo.ui;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import jeo.assistant.Color2;


public class UIFactory {
	private void UIFactory() {}
	
	public static BeoPanelUI getPanelUI() {
		return new BeoPanelUI();
	}
	
	public static BeoLabelUI getLabelUI() {
		return new BeoLabelUI();
	}
	
	public static BeoComboBoxUI getComboBoxUI() {
		return new BeoComboBoxUI();
	}
	
	public static BeoTextFieldUI getTextFieldUI() {
		return new BeoTextFieldUI();
	}
	
	public static BeoButtonUI getButtonUI() {
		return new BeoButtonUI();
	}
	
	public static void setBorder(JComponent c) {
		c.setBorder(new LineBorder(Color2.white, 1));
	}
	
	public static BeoScrollBarUI getBeoScrollBarUI() {
		return new BeoScrollBarUI();
	}
	
	public static BeoTableUI getTableUI() {
		return new BeoTableUI();
	}
	
	public static void setJScrollPane(JScrollPane jScrollPane) {
		jScrollPane.getViewport().setBackground(Color2.black);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color2.lightGray));
		jScrollPane.getVerticalScrollBar().setUI(getBeoScrollBarUI());
	}
}
