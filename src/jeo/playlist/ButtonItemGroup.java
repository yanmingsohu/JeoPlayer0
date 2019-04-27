// CatfoOD 2008-7-2 ����08:47:51

package jeo.playlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;


public class ButtonItemGroup {
	private ArrayList<FileButtonItem> itemList;
	private ActionListener al;
	private JPanel parent;
	private int index = -1;
	
	public ButtonItemGroup(ActionListener al, JPanel p) {
		itemList = new ArrayList<FileButtonItem>();
		this.al = al;
		parent = p;
	}
	
	/**
	 * ��������ͬ��Ԫ��
	 */
	public void addItem(IListItem i) {
		if (i==null) return;
		
		FileButtonItem item = new FileButtonItem(i, al, parent);
		if (itemList.contains(item)) return;
		
		item.addMouseListener(REMOVEML);
		itemList.add(item);
		parent.add(item);
		parent.validate();
		parent.repaint();
	}

	public void addList(IListItem[] items) {
		if (items==null) return;
		for (int i=0; i<items.length; ++i) {
			addItem(items[i]);
		}
	}

	/**
	 * ����б���û����Ŀ����null
	 */
	public IListItem[] getList() {
		IListItem[] items = new IListItem[itemList.size()];
		for (int i=0; i<items.length; ++i) {
			items[i] = itemList.get(i).getItem();
		}
		return items;
	}

	/**
	 * ���ص�ǰ��ѡ�����i��ƫ��
	 * ����б���û����Ŀ����null,�����0��Ϊ�������򷵻ص�ǰ��ѡ����
	 */
	public IListItem changeIndex(int i) {
		if (itemList.size()==0) return null;
		index += i;
		if (index<0) index = itemList.size()-1;
		if (index>=itemList.size()) index=0;
		informListener();
		return itemList.get(index).getItem();
	}
	
	/**
	 * ����б���û����Ŀ����null
	 */
	public IListItem random() {
		index = (int) (itemList.size()*Math.random());
		informListener();
		return itemList.get(index).getItem();
	}
	
	/** �Ƴ�����ͨ��������� */
	private void remove(FileButtonItem i) {
		if (itemList.remove(i)) {
			i.remove();
		}
		parent.validate();
		parent.repaint();
	}
	
	public void removeSelect() {
		if (index<0) return;
		remove(itemList.get(index));
	}
	
	public void removeAll() {
		for (int i=itemList.size(); i>0; --i) {
			((FileButtonItem)itemList.remove(0)).remove();
		}
		index = -1;
		parent.validate();
		parent.repaint();
	}
	
	/**
	 * �������ֻ��������ʾ���ò�����Ϊ��ǰ���㣬���Ⲣ�������κ��¼�
	 * @param select
	 */
	public void setSelected(FileButtonItem select) {
		FileButtonItem fbi;
		for (int i=0; i<itemList.size(); ++i) {
			fbi = itemList.get(i);
			if (fbi==select) {
				fbi.setSelect(true);
				index = i;
			} else {
				fbi.setSelect(false);
			}
		}
	}
	
	/**
	 * ���ҷ���name����Ŀ��������Ϊѡ��״̬
	 * ��������ᴥ��һ��ѡ���¼�
	 * @param name
	 */
	public void setSelected(String name) {
		if (name==null) return;
		for (int i=0; i<itemList.size(); ++i) {
			String packags = itemList.get(i).getItem().getObject().toString();
			if (packags.equals(name)) {
				index = i;
				al.actionPerformed(
						new ActionEvent(itemList.get(index), 0, inform));
				return;
			}
		}
	}
	
	/**
	 * ���ص�ǰѡ����Ŀ���б��е�λ��
	 */
	public int getSelectIndex() {
		return index;
	}
	
	public int getSize() {
		return itemList.size();
	}
	
	/** �Ҽ�˫��ɾ����Ŀ */
	private MouseAdapter REMOVEML = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount()>1 && e.getButton()==MouseEvent.BUTTON3) {
				remove((FileButtonItem) e.getSource()); 
			}
		}
	};
	
	/**
	 * ֪ͨ��ť����������ť״̬�������޸�
	 * Ӧ��ͨ�����������ͨ�����������°�ť
	 */
	private void informListener() {
		al.actionPerformed(
				new ActionEvent(itemList.get(index), 0, command));
	}
	
	/**
	 * ActionEventΪcommand˵����ͨ�����򼤷����¼�����Ҫ֪ͨPlaylistListener
	 */
	public final static String command = ButtonItemGroup.class.toString();
	/**
	 * Ӧ��֪ͨplayListener������ָ������Ŀ
	 */
	public final static String inform = "inform playListener.";
}
