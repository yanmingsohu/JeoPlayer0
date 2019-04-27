// CatfoOD 2008-7-2 下午11:11:12

package jeo.infomation;

import javax.swing.JPanel;

public class InfoFactory {
	private static InfoWindow info = new InfoWindow();
	
	/**
	 * 显示info窗口，并把in加到内部窗口中
	 * @param in
	 */
	public static IHide showInfo(JPanel in) {
		return info.setInfoPanel(in);
	}
	
	/**
	 * 返回info窗口是否可见
	 */
	public static boolean isVisible() {
		return info.isVisible();
	}
	
	/**
	 * 关闭InfoWindow的便捷方法
	 */
	public static void hideInfo() {
		info.closeInfoPanle();
	}
	
}
