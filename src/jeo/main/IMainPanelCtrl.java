// CatfoOD 2008-6-30 ����08:43:54

package jeo.main;

/**
 * ����������������࣬����ʵ������ӿ�
 * ÿ��������Ӧ������ϵİ�ť
 */
public interface IMainPanelCtrl extends IMenus{
	void upKey();
	void downKey();
	void leftKey();
	void rightKey();
	
	/** �������� */
	void volumeup();
	/** �������� */
	void volumedown();
	
	/** ����/��ͣ �л� */
	void play();
	/** ��ͣ����*/
	void stop();
	/** �����л�*/
	void mute();
}
