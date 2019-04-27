// CatfoOD 2008-7-2 ����04:00:43

package jeo.playlist;

/**
 * �����б���ÿ������İ�װ��
 */
public interface IListItem {
	/**
	 * ���������֣��ڲ����б����������ʾ
	 */
	String getName();
	
	/**
	 * ���ر�ʾ���ڲ����б����չ������ʾ
	 * ����null�ᵼ�³����쳣��
	 * �ɽ��ܵķ�������Image, Component, String, Object
	 */
	Object getType();
	
	/**
	 * ���ط�װ�Ķ���
	 */
	Object getObject();
	
	/**
	 * ����һ��������ǰ������ַ��������ڱ���
	 */
	String getSaveString();
	
	/**
	 * ������չ��Ϣ������ʾ����Ŀ�����½ǣ���ɫ
	 * ����null,����ʾ
	 */
	String getExtendInfo();
	
	/**
	 * ����������չ��Ϣ������null
	 */
	void setExtendInfo(String s);
	
	/**
	 * ���عؼ�������������������ͼƬ
	 * ������http://��ʼ��String,��ֱ���������ͼƬ
	 */
	String getKey();
}
