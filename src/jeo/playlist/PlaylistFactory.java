// CatfoOD 2008-7-2 下午04:06:01

package jeo.playlist;

public class PlaylistFactory {
	private PlaylistFactory() {}
	
	private static PlaylistWindowCtrl  ctrl = new PlaylistWindow().getCtrl();
	
	public static IPlaylistCtrl getPlaylist() {
		return ctrl;
	}
	
	/** 播放列表窗口可见部分的宽度*/
	public static int getPlaylistWindowWidth() {
		return ctrl.getWidth();
	}
}
