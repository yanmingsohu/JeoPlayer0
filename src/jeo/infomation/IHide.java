// CatfoOD 2008-7-3 ����08:12:43

package jeo.infomation;

/**
 * IHide�ӿ������ڲ�����ͨ������ر�InfoWindow
 */
public interface IHide {
	/**
	 * �ڲ�����Ҫ�ر�����InfoWindow�����������
	 * ���ص�Thread�������ڵȴ�
	 */
	Thread hide();
	
	/**
	 * �ȴ�������ȫ��ʾ
	 * �������������������ֱ��������ʾ���
	 * ���ǿ�ѡ�Ĳ�������������ڲ����ڵĲ���
	 * ��Ӱ�쵽������ʾ�����������ԣ�Ӧ���ڽ�
	 * �к�ʱ�Ĳ���ǰ�ö�����ɣ������Ӱ��
	 * �û���������顣
	 */
	void waitingForShow();
	
	/**
	 * �ȴ����ڹرգ��û������ȷ�����رգ�ȡ�� ��
	 * ��������Ż᷵�أ�������������
	 */
	void waitForOK();
}