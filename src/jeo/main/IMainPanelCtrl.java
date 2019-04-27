// CatfoOD 2008-6-30 上午08:43:54

package jeo.main;

/**
 * 加入中央控制器的类，必须实现这个接口
 * 每个方法对应主面板上的按钮
 */
public interface IMainPanelCtrl extends IMenus{
	void upKey();
	void downKey();
	void leftKey();
	void rightKey();
	
	/** 音量提升 */
	void volumeup();
	/** 音量降低 */
	void volumedown();
	
	/** 播放/暂停 切换 */
	void play();
	/** 暂停播放*/
	void stop();
	/** 静音切换*/
	void mute();
}
