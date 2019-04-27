// CatfoOD 2008-6-28 下午08:04:09

package jeo.core;

/**
 * 播放位置监听器
 */
public interface IPositionListener {
	/**
	 * 通知监听器当前的播放位置
	 * @param i - 当前的播放位置 0~100
	 */
	void positionChange(int i);
}
