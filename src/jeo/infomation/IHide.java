// CatfoOD 2008-7-3 上午08:12:43

package jeo.infomation;

/**
 * IHide接口允许内部窗口通过程序关闭InfoWindow
 */
public interface IHide {
	/**
	 * 内部窗口要关闭外层的InfoWindow调用这个方法
	 * 返回的Thread可以用于等待
	 */
	Thread hide();
	
	/**
	 * 等待窗口完全显示
	 * 这个方法会阻塞操作，直到窗口显示完成
	 * 这是可选的操作，如果插入内部窗口的操作
	 * 会影响到窗口显示动画的流畅性，应该在进
	 * 行耗时的操作前让动画完成，否则会影响
	 * 用户界面的体验。
	 */
	void waitingForShow();
	
	/**
	 * 等待窗口关闭，用户点击：确定，关闭，取消 等
	 * 这个方法才会返回，否则阻塞操作
	 */
	void waitForOK();
}
