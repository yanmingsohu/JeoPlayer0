// CatfoOD 2008-7-2 下午03:48:47

package jeo.playlist;

/**
 * 播放列表的操作接口，播放列表工厂返回这个接口
 */
public interface IPlaylistCtrl {	
	/**
	 * 让改变当前播放项目的偏移
	 * @param n - 如果>0则向下n个项目移动，<0向前n个项目,如果==0返回当前的项目
	 * @return - 返回列表移动后当前的项目，如果没有返回null
	 */
	IListItem moveOffset(int n);
	
	/**
	 * 随机返回下一首曲目
	 * 如果列表中没有曲目返回null
	 */
	IListItem random();
	
	/**
	 * 清除所有列表项目.实现者应该适当保存当前的播放列表
	 * 这个方法被调用的原因是：控制器要求切换播放器
	 */
	void switchCurrentPlayer();
	
	/**
	 * 设置列表项目，这个方法初始化时使用
	 * 这个方法的行为是‘添加’而不是设置，并不删除原先的列表！
	 */
	void setList(IListItem[] items);
	
	/**
	 * 返回项目列表，用于保存
	 */
	IListItem[] getList();
	
	/**
	 * 添加播放列表事件监听器
	 * @param listen
	 */
	void addPlaylistListener(PlaylistListener listen);
	void removePlaylistListener(PlaylistListener listen);
	
	/**
	 * 立即弹出播放列表窗口
	 */
	void display();
	
	/**
	 * 自动播放上一次关闭时选择的项目
	 * 前提：<br>
	 * <b>已经用addPlaylistListener()初始化了监听器<br>
	 * <b>已经用setList()设置了列表
	 */
	void autoPlayListSelect();
}
