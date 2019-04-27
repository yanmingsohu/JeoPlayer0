// CatfoOD 2008-7-2 下午08:47:51

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
	 * 不接受相同的元素
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
	 * 如果列表中没有项目返回null
	 */
	public IListItem[] getList() {
		IListItem[] items = new IListItem[itemList.size()];
		for (int i=0; i<items.length; ++i) {
			items[i] = itemList.get(i).getItem();
		}
		return items;
	}

	/**
	 * 返回当前的选择加上i的偏移
	 * 如果列表中没有项目返回null,如果用0作为参数，则返回当前的选择项
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
	 * 如果列表中没有项目返回null
	 */
	public IListItem random() {
		index = (int) (itemList.size()*Math.random());
		informListener();
		return itemList.get(index).getItem();
	}
	
	/** 移除必须通过这个方法 */
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
	 * 这个方法只能用来显示，让参数变为当前焦点，此外并不触发任何事件
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
	 * 查找符合name的项目，并设置为选择状态
	 * 这个方法会触发一个选择事件
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
	 * 返回当前选择项目在列表中的位置
	 */
	public int getSelectIndex() {
		return index;
	}
	
	public int getSize() {
		return itemList.size();
	}
	
	/** 右键双击删除项目 */
	private MouseAdapter REMOVEML = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount()>1 && e.getButton()==MouseEvent.BUTTON3) {
				remove((FileButtonItem) e.getSource()); 
			}
		}
	};
	
	/**
	 * 通知按钮监听器，按钮状态被程序修改
	 * 应该通过这个方法，通过程序来按下按钮
	 */
	private void informListener() {
		al.actionPerformed(
				new ActionEvent(itemList.get(index), 0, command));
	}
	
	/**
	 * ActionEvent为command说明是通过程序激发的事件不需要通知PlaylistListener
	 */
	public final static String command = ButtonItemGroup.class.toString();
	/**
	 * 应该通知playListener，播放指定的项目
	 */
	public final static String inform = "inform playListener.";
}
