// CatfoOD 2008-7-2 下午04:00:43

package jeo.playlist;

/**
 * 播放列表中每个对象的包装类
 */
public interface IListItem {
	/**
	 * 返回主名字，在播放列表的主区域显示
	 */
	String getName();
	
	/**
	 * 返回标示，在播放列表的扩展区域显示
	 * 返回null会导致程序异常！
	 * 可接受的返回类型Image, Component, String, Object
	 */
	Object getType();
	
	/**
	 * 返回封装的对象
	 */
	Object getObject();
	
	/**
	 * 返回一个描述当前对象的字符串，用于保存
	 */
	String getSaveString();
	
	/**
	 * 返回扩展信息用于显示在项目的右下角，红色
	 * 返回null,则不显示
	 */
	String getExtendInfo();
	
	/**
	 * 用于设置扩展信息，允许null
	 */
	void setExtendInfo(String s);
	
	/**
	 * 返回关键字用来在网络上搜索图片
	 * 返回以http://开始的String,会直接下载这个图片
	 */
	String getKey();
}
