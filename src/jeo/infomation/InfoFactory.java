// CatfoOD 2008-7-2 ����11:11:12

package jeo.infomation;

import javax.swing.JPanel;

public class InfoFactory {
	private static InfoWindow info = new InfoWindow();
	
	/**
	 * ��ʾinfo���ڣ�����in�ӵ��ڲ�������
	 * @param in
	 */
	public static IHide showInfo(JPanel in) {
		return info.setInfoPanel(in);
	}
	
	/**
	 * ����info�����Ƿ�ɼ�
	 */
	public static boolean isVisible() {
		return info.isVisible();
	}
	
	/**
	 * �ر�InfoWindow�ı�ݷ���
	 */
	public static void hideInfo() {
		info.closeInfoPanle();
	}
	
}
