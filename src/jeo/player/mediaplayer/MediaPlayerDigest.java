// CatfoOD 2008-6-30 ����02:42:31

package jeo.player.mediaplayer;

import jeo.assistant.IPlayerDigest;
import jeo.main.IMainPanelCtrl;

/**
 * ���ֲ�������ժҪ
 */
public class MediaPlayerDigest implements IPlayerDigest {

	public IMainPanelCtrl getInstance() {
		return new MediaPlayer();
	}

	public String getName() {
		return "����";
	}

}
