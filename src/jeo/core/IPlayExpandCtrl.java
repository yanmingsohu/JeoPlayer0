// CatfoOD 2008-6-29 上午07:17:19

package jeo.core;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

interface IPlayExpandCtrl {
	
	/** 添加音乐停止监听器 */
	void addStopListener(IStopListener listen);
	/** 依除音乐停止监听器 */
	void removeStopListener(IStopListener listen);
	/** 设置缓冲区大小 */
	void setBuff(int size);
	/** 取得当前缓冲区大小 */
	int getBuff();
	/** 取得可用的混频器 */
	Mixer[] getCanUserMixer();
	/** 取得当前使用的混频器 */
	Mixer getCurrentMixer();
	
	/** 
	 * 改变当前使用的混频器 
	 * @throws LineUnavailableException 
	 * */
	void useThisMixer(Mixer m) throws LineUnavailableException;
}
