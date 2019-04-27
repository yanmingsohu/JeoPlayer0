// CatfoOD 2008-6-30 обнГ10:02:00

package jeo.main;

import java.util.ArrayList;

import jeo.ui.LabelButton;


public class ButtonGroup {
	private ArrayList<LabelButton> list;
	
	public ButtonGroup() {
		list = new ArrayList<LabelButton>();
	}
	
	public void add(LabelButton b) {
		b.addGroup(this);
		list.add(b);
	}
	
	public void mouseExit() {
		for (int i=0; i<list.size(); ++i) {
			list.get(i).mouseExit();
		}
	}
	
	public void mouseEnter() {
		for (int i=0; i<list.size(); ++i) {
			list.get(i).mouseEnter();
		}
	}
}
