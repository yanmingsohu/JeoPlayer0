// CatfoOD 2008-6-28 上午10:32:21

package jeo.core;

/**
 * 当前的音乐停止时触发这个事件
 * 这个事件的触发者是Core,可以通过PM把这个监听器加入Core
 */
public interface IStopListener {
	
	/**
	 * 当前的音乐<b>自然</b>停止时触发这个方法,<br>
	 * 告诉监听者，音乐已经停止，需要进一步操作,<br>
	 * 参数一般为null或是触发者的Class
	 */
	public void stop(Object o);
}
