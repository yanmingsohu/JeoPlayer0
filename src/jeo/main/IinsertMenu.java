// CatfoOD 2008-6-30 ����10:01:06

package jeo.main;

import jeo.ui.LabelButton;

public interface IinsertMenu {
	/**
	 * ����һ��˵�
	 */
	void insertMenus(IMenus i);
	void insertMenus(LabelButton[] jls);
	
	/**
	 * ������һ���˵�
	 * �ɹ�����true
	 */
	boolean returnMenu();
}
