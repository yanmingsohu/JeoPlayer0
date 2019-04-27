// CatfoOD 2008-6-30 上午09:07:55

package jeo.main;

import jeo.ui.LabelButton;

/**
 * 加入主窗口的中央控制面板，必须实现这个接口
 */
public interface IMenus {
	/**
	 * 当用户点击setup按钮，返回有效的setup菜单，菜单的有效数量在1~5(包含)
	 * <br><b>*用户不应该*</b>直接调用这个方法，而是由IinsertMenu接口去调用
	 * 
	 * @param i - 允许添加子菜单，接口中有相应的方法,实现的类如果需要添加子
	 * 				菜单可以保留这个参数，否则简单的抛弃就可以
	 * @return JLabel[]菜单
	 */
	LabelButton[] getMenus(IinsertMenu i);
}
