// CatfoOD 2008-6-30 ����07:40:54

package jeo.player.radio;

import jeo.assistant.IPlayerDigest;
import jeo.main.IMainPanelCtrl;

public class RadioPlayerDigest implements IPlayerDigest {
	public IMainPanelCtrl getInstance() {
		return new RadioPlayer();
	}

	public String getName() {
		return "������";
	}
}
