// CatfoOD 2008-6-30 ����09:07:55

package jeo.main;

import jeo.ui.LabelButton;

/**
 * ���������ڵ����������壬����ʵ������ӿ�
 */
public interface IMenus {
	/**
	 * ���û����setup��ť��������Ч��setup�˵����˵�����Ч������1~5(����)
	 * <br><b>*�û���Ӧ��*</b>ֱ�ӵ������������������IinsertMenu�ӿ�ȥ����
	 * 
	 * @param i - ��������Ӳ˵����ӿ�������Ӧ�ķ���,ʵ�ֵ��������Ҫ�����
	 * 				�˵����Ա����������������򵥵������Ϳ���
	 * @return JLabel[]�˵�
	 */
	LabelButton[] getMenus(IinsertMenu i);
}
