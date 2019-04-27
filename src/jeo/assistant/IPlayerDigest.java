// CatfoOD 2008-6-30 下午02:34:59

package jeo.assistant;

import jeo.main.IMainPanelCtrl;


/**
 * 每个播放器的插件实现这个接口
 */
public interface IPlayerDigest {
	/**
	 * 现示播放器的名字
	 */
	String getName();
	
	/**
	 * 取得播放器的实例
	 */
	IMainPanelCtrl getInstance();
}
