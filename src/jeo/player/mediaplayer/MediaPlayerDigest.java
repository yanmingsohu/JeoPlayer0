// CatfoOD 2008-6-30 ÏÂÎç02:42:31

package jeo.player.mediaplayer;

import jeo.assistant.IPlayerDigest;
import jeo.main.IMainPanelCtrl;

/**
 * ÒôÀÖ²¥·ÅÆ÷µÄÕªÒª
 */
public class MediaPlayerDigest implements IPlayerDigest {

	public IMainPanelCtrl getInstance() {
		return new MediaPlayer();
	}

	public String getName() {
		return "ÒôÀÖ";
	}

}
