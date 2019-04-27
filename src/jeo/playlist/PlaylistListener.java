// CatfoOD 2008-7-2 下午04:13:33

package jeo.playlist;

import jeo.core.IPositionListener;

/**
 * 播放列表监听器
 */
public interface PlaylistListener {
	/**
	 * 播放列表要求添加多个项目,实现的方法应该返回一个项目的列表
	 * @return - 返回列表项目的数组
	 */
	IListItem[] requireItems();
	
	/**
	 * 播放列表要移除所有项目,因为当前的播放器要被切出
	 * 实现者应该保存列表，并作相应的善后工作
	 */
	void removeAll();
	
	/**
	 * 新的项目被选择，这个方法通知实现者，用户单机了一个项目，
	 * 需要执行相关操作
	 * @param i - 被选择的项目
	 */
	void selectChanged(IListItem i);
	
	/**
	 * 添加播放进度监听器，实现者需要把请求的监听器添加入某个
	 * 播放核心
	 */
	void addPositionListener(IPositionListener i);
	
	void removePositionListener(IPositionListener i);
	
}
