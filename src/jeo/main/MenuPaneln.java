// CatfoOD 2008-6-30 ����09:26:05

package jeo.main;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;

import jeo.assistant.Tools;
import jeo.ui.JPanel2;
import jeo.ui.LabelButton;


public class MenuPaneln extends JPanel2 implements IinsertMenu{

	private static final long serialVersionUID = 1L;
	/** �˵��������ӳ�ʱ��*/
	private static final int waitTime = 70;
	
	private ArrayList<LabelButton[]> 
			menulist = new ArrayList<LabelButton[]>();  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public MenuPaneln() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(70, 80);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	/**
	 * ����һ���˵������ұ��浱ǰ�Ĳ˵����˵�����1~5(����)
	 */
	private void setMenu(final LabelButton[] menu) {
		new Thread() {
			public void run() {
				removeAll();				
				for (int i=0; i<menu.length; ++i) {
					add(menu[i]);
					validate();
					repaint();
					MenuPaneln.sleep(waitTime);
				}
				if (cpc!=null) {
					cpc.checkExitButton();
				}
			}
		}.start();
	}
	
	public void insertMenus(IMenus i) {
		if (i==null) return;
		insertMenus(i.getMenus(this));
	}
	
	public void insertMenus(LabelButton[] j) {
		if (j.length<1) return;
		if (j.length>5) Tools.waring("menu item >5.");
		menulist.add(j);
		setMenu(j);
	}
	
	/**
	 * ���ø�Ŀ¼��ɾ��������ǰ������Ŀ¼�㼶
	 */
	public void setRootMenus(final IMenus i) {
		if (i==null) return;
		LabelButton j[] = i.getMenus(this);
		if (j.length<1) return;
		new Thread() {
			public void run() {
			removeAll();
			menulist.clear();
			insertMenus(i);
			}
		}.start();
	}
	
	/**
	 * ������һ���˵�
	 * @return �������һ���˵�����true
	 */
	public boolean returnMenu() {
		final int index = menulist.size()-1;
		if (index>0) {
			menulist.remove(index);
//			new Thread() {
//				public void run() {
					removeAll();
					validate();
					setMenu(menulist.get(index-1));
//				}
//			}.start();
			return true;
		} else {
			return false;
		}
	}

	public void removeAll() {
		Component[] cts = getComponents();
		for (int i=cts.length-1; i>=0; --i) {
			remove(cts[i]);
			validate();
			repaint();
			MenuPaneln.sleep(waitTime);
		}
		MenuPaneln.super.removeAll();
	}
	
	private final static void sleep(int t) {
		try {
			Thread.sleep(t);
		} catch (Exception e) {}
	}
	
	public boolean onlyOne() {
		return menulist.size()<2;
	}
	
	public void setCenterPanelCtrl(CenterPanelCtrl cpc) {
		this.cpc = cpc;
	}
	
	private CenterPanelCtrl cpc = null;

}
