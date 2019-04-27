// CatfoOD 2009-5-16 上午08:09:12

package jeo.ui;


/**
 * 全部进程只维护一个VolumeLabel
 */
public class VolumeFactory {
	private static VolumeLabel vol = new VolumeLabel();
	
	public static VolumeLabel get() {
		return vol;
	}
}
