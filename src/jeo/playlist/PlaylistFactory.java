// CatfoOD 2008-7-2 ����04:06:01

package jeo.playlist;

public class PlaylistFactory {
	private PlaylistFactory() {}
	
	private static PlaylistWindowCtrl  ctrl = new PlaylistWindow().getCtrl();
	
	public static IPlaylistCtrl getPlaylist() {
		return ctrl;
	}
	
	/** �����б��ڿɼ����ֵĿ��*/
	public static int getPlaylistWindowWidth() {
		return ctrl.getWidth();
	}
}
