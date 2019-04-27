// CatfoOD 2008-6-30 ����11:40:17

package jeo.assistant;

import jeo.main.IMainPanelCtrl;
import jeo.player.mediaplayer.MediaPlayerDigest;
import jeo.player.radio.RadioPlayerDigest;


public class PlayerFactory {
	public static final String configName = "config/players.conf";
	
	private static IPlayerDigest[] ipds = new IPlayerDigest[]{
			new MediaPlayerDigest(),
			new RadioPlayerDigest(),
	};
	
	public static final IPlayerDigest[] getPlayers() {
		return ipds;
	}
	
	/**
	 * Ѱ��ָ�����ֵĲ��������ҵ����ز�������ժҪ
	 * @param name - �����������
	 * @return �ҵ��Ĳ������ժҪ
	 */
	public static final IPlayerDigest getPlayer(String name) {
		Class<?> c;
		try {
			c = Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
		for (int i=0; i<ipds.length; ++i) {
			if (ipds[i].getClass().equals(c)) {
				return ipds[i];
			}
		}
		return null;
	}
	
	public static final IMainPanelCtrl 
	getPlayerInstance(IPlayerDigest i) {
		return i.getInstance();
	}
	
//	// not use.
//	private static void initFromFile() {
//		String[] name = Tools.readConfig(configName);
//		if (name!=null && name.length>0) {
//			for (int i=0; i<name.length; ++i) {
//				try {
//					IPlayerDigest pd = (IPlayerDigest) 
//							Class.forName(name[i]).newInstance();
//					idigs.add(pd);
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	private static ArrayList<IPlayerDigest> 
//				idigs = new ArrayList<IPlayerDigest>();
	
	private PlayerFactory() {}
}
