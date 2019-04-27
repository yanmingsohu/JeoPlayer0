// CatfoOD 2008-6-30 上午10:01:06

package jeo.main;

import jeo.ui.LabelButton;

public interface IinsertMenu {
	/**
	 * 插入一组菜单
	 */
	void insertMenus(IMenus i);
	void insertMenus(LabelButton[] jls);
	
	/**
	 * 返回上一级菜单
	 * 成功返回true
	 */
	boolean returnMenu();
}
