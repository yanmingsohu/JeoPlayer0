// CatfoOD 2008-7-2 ����04:13:33

package jeo.playlist;

import jeo.core.IPositionListener;

/**
 * �����б������
 */
public interface PlaylistListener {
	/**
	 * �����б�Ҫ����Ӷ����Ŀ,ʵ�ֵķ���Ӧ�÷���һ����Ŀ���б�
	 * @return - �����б���Ŀ������
	 */
	IListItem[] requireItems();
	
	/**
	 * �����б�Ҫ�Ƴ�������Ŀ,��Ϊ��ǰ�Ĳ�����Ҫ���г�
	 * ʵ����Ӧ�ñ����б�������Ӧ���ƺ���
	 */
	void removeAll();
	
	/**
	 * �µ���Ŀ��ѡ���������֪ͨʵ���ߣ��û�������һ����Ŀ��
	 * ��Ҫִ����ز���
	 * @param i - ��ѡ�����Ŀ
	 */
	void selectChanged(IListItem i);
	
	/**
	 * ��Ӳ��Ž��ȼ�������ʵ������Ҫ������ļ����������ĳ��
	 * ���ź���
	 */
	void addPositionListener(IPositionListener i);
	
	void removePositionListener(IPositionListener i);
	
}
