// CatfoOD 2008-7-2 ����03:48:47

package jeo.playlist;

/**
 * �����б�Ĳ����ӿڣ������б�����������ӿ�
 */
public interface IPlaylistCtrl {	
	/**
	 * �øı䵱ǰ������Ŀ��ƫ��
	 * @param n - ���>0������n����Ŀ�ƶ���<0��ǰn����Ŀ,���==0���ص�ǰ����Ŀ
	 * @return - �����б��ƶ���ǰ����Ŀ�����û�з���null
	 */
	IListItem moveOffset(int n);
	
	/**
	 * ���������һ����Ŀ
	 * ����б���û����Ŀ����null
	 */
	IListItem random();
	
	/**
	 * ��������б���Ŀ.ʵ����Ӧ���ʵ����浱ǰ�Ĳ����б�
	 * ������������õ�ԭ���ǣ�������Ҫ���л�������
	 */
	void switchCurrentPlayer();
	
	/**
	 * �����б���Ŀ�����������ʼ��ʱʹ��
	 * �����������Ϊ�ǡ���ӡ����������ã�����ɾ��ԭ�ȵ��б�
	 */
	void setList(IListItem[] items);
	
	/**
	 * ������Ŀ�б����ڱ���
	 */
	IListItem[] getList();
	
	/**
	 * ��Ӳ����б��¼�������
	 * @param listen
	 */
	void addPlaylistListener(PlaylistListener listen);
	void removePlaylistListener(PlaylistListener listen);
	
	/**
	 * �������������б���
	 */
	void display();
	
	/**
	 * �Զ�������һ�ιر�ʱѡ�����Ŀ
	 * ǰ�᣺<br>
	 * <b>�Ѿ���addPlaylistListener()��ʼ���˼�����<br>
	 * <b>�Ѿ���setList()�������б�
	 */
	void autoPlayListSelect();
}
