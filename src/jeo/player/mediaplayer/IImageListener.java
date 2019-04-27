// CatfoOD 2008-7-15 下午11:41:39

package jeo.player.mediaplayer;

import java.awt.Image;

public interface IImageListener {
	/**
	 * 回调方法，用于设置一个有效的图片，img一定不为null
	 */
	void setImage(Image img);
	String getKey();
}
