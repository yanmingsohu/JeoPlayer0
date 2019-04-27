// CatfoOD 2008-7-2 下午03:45:28

package jeo.playlist;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JScrollBar;

import jeo.assistant.Color2;
import jeo.assistant.IExit;
import jeo.assistant.Parabola;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;
import jeo.infomation.InfoFactory;
import jeo.ui.IndicatorWindow;
import jeo.usefuhelp.UserHelper;


public class PlaylistWindowCtrl implements IPlaylistCtrl, ActionListener, IExit {
	
	private PlaylistWindow win;
	private PlaylistListener listener;
	private ButtonItemGroup itemList;
	/** 保持按钮的状态 */
	private boolean holding = false;
	/** 如果垂直滚动条被选中为true */
	private boolean isSelected = false;
	
	protected PlaylistWindowCtrl(PlaylistWindow p) {
		win = p;
		creatIndicator();
		win.setLocation(-PlaylistWindow.W, 0);
		itemList = new ButtonItemGroup(this, win.getItemPanel());
		setScrollBarMouseListener();
		SystemExit.registerExit(this);
	}
	
	/** 逻辑宽度,窗口在屏幕外面的宽度 */
	public int getWidth() {
		return win.getWidth() + win.getX();
	}
	
	/** 保持状态开关 */
	protected void hold() {
		holding = !holding;
		JButton b = win.getJButtonHold();
		if (holding) {
			b.setText("< 隐藏");
			b.setForeground(Color2.red);
		} else {
			b.setText("锁定 >");
			b.setForeground(Color2.LIGHT_GRAY);
		}
	}
	
	protected void removeAll() {
		itemList.removeAll();
	}

	@Override
	public void display() {
		new showThread();
	}
	
	@Override
	public void switchCurrentPlayer() {
		if (listener!=null) {
			listener.removeAll();
			SystemExit.unregisterExit(listener);
		}
		saveAutoPlay();
		itemList.removeAll();
		win.refurbishList();
	}
	
	@Override
	public IListItem moveOffset(int n) {
		return itemList.changeIndex(n);
	}
	
	@Override
	public void setList(IListItem[] items) {
		itemList.addList(items);
	}
	
	@Override
	public IListItem[] getList() {
		return itemList.getList();
	}

	@Override
	public IListItem random() {
		return itemList.random();
	}
	
	@Override
	public void autoPlayListSelect() {
		if (listener!=null) {
			String last = Tools.getConfig(listener.getClass().getName());
			if (last!=null) {
				itemList.setSelected(last);
			}
		}
	}
	
	/** 保存当前的选择项目到配置文件*/
	private void saveAutoPlay() {
		IListItem cri = itemList.changeIndex(0);
		if (cri!=null && listener!=null) {
			Tools.setConfig(listener.getClass().getName(), cri.getObject().toString());
		}
	}

	@Override
	public void addPlaylistListener(PlaylistListener listen) {
		listener = listen;
	}

	@Override
	public void removePlaylistListener(PlaylistListener listen) {
		listener = null;
	}
	

	/**
	 * 播放列表要求添加多个项目
	 * @return - 返回列表项目的数组
	 */
	protected void addItems() {
		if (listener!=null) {
			// 建立一个新的线程，防止消息被阻塞，对用户透明
			new Thread() {
				public void run() {
					setList(listener.requireItems());
					win.validate();
				}
			}.start();
		}
	}
	
	/**
	 * 从列表中移出指定的项目
	 */
	protected void removeSelect() {
		itemList.removeSelect();
	}

	// 项目列表被单击时执行这个方法
	public void actionPerformed(ActionEvent e) {
		FileButtonItem item = (FileButtonItem)e.getSource();
		itemList.setSelected(item);
		Tools.waitExit(autoMoveCenter());
		// 通知监听器一个项目被选择
		// 把选择的项目放入播放进度监听器中
		if (listener!=null) {
			listener.addPositionListener(item);
			if (e.getActionCommand()!=ButtonItemGroup.command) {
				listener.selectChanged(item.getItem());
			}
		}
	}
	
	/**
	 *  居中当前选择的项目,如果需要等待操作完成，用返回的参数
	 */
	private Thread autoMoveCenter() {
		Thread t = new AutoMoveCenterThread();
		t.start();
		return t;
	}
	
	// 限制超过一个线程的更新
	private boolean automoved = false;
	
	// 居中的线程
	private class AutoMoveCenterThread extends Thread {
		public void run() {
			if (automoved || itemList.getSize()==0) return;
			automoved = true;
			// 屏幕高度/2 /每个项目的高度 -1
			float index = itemList.getSelectIndex()- Tools.getScreenSize().height/100+ 1;
			float vol = index* win.getBarMax()/ itemList.getSize();
			float min = 0;
			float max = win.getBarMax() - win.getExtent();
			
			int current = win.getBarPosition();
			int step = 10;
			int way = (current>vol? -1: 1);
			int sleeptime = 15;
			
			if (way>0)
				vol = vol<max? vol: max;
			else
				vol = vol>min? vol: min;
			
			while (way*current < way*vol) {
				current += step*way;
				win.setBarPosition(current);
				step = (int) ((current>vol? current-vol: vol-current)/11 )+1;
				Tools.sleep( sleeptime );
			}
			
			win.setBarPosition((int)vol);
			automoved = false;
		}
	}

	// ----------------------auto hide---------------------------
	private IndicatorWindow indicat;
	private final int stepSize = 23;
	
	private void creatIndicator() {
		indicat = new IndicatorWindow(IndicatorWindow.LIFT);
		indicat.setHide(false);
		indicat.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				new showThread();
			}
		});
	}
	
	// 需要弹出应该直接启动线程
	public void popup() {
		win.setVisible(true);
		indicat.setHide(true);
		Dimension d = Tools.getScreenSize();
		win.setSize(PlaylistWindow.W, d.height);
		move(win.getX(), 0);
		win.setLocation(0, 0);
		new hidenThread();
	}
	
	public void hide() {
		Tools.waitExit(autoMoveCenter());
		Tools.sleep(300);
		Dimension d = Tools.getScreenSize();
		win.setSize(PlaylistWindow.W, d.height);
		if (move(win.getX(), -PlaylistWindow.W)) {
			win.setVisible(false);
			indicat.setHide(false);
		}
		hiding = false;
	}
	
	private boolean movebreak = false;
	private boolean hiding = false;
	
	/**
	 * @return - 如果move被中断返回 false;
	 */
	private boolean move(int s, int d) {
		int step = s>d? -stepSize: stepSize;
		int base = s>d? -1: 1;
		Parabola para = new Parabola(s, d, step);
		while (Math.abs(s-d)>stepSize && !movebreak) {
			s+=base+para.get(s);
			win.setLocation(s, 0);
			Tools.sleep(10);
		}
		if (!movebreak) {
			win.setLocation(d, 0);
		}
		boolean mb = !movebreak;
		movebreak = false;
		return mb;
	}
	
	private void breakHiden() {
		if (!hiding) return;
		movebreak = true;
		while (movebreak) {
			Tools.sleep(50);
		}
		new showThread();
	}
	
	private class hidenThread extends Thread {
		public hidenThread() {
			this.setDaemon(true);
			start();
		}
		
		public void run() {
			Tools.sleep(2000);
			Rectangle rv = win.getBounds();
			while ( InfoFactory.isVisible() || holding || isSelected ||
					rv.contains(MouseInfo.getPointerInfo().getLocation())) {
				Tools.sleep(200);
			}
			hiding = true;
			new breakHideThread();
			hide();
			
			UserHelper.show(3, "<< 把鼠标移动到屏幕边缘", 20, 300);
		}
	}

	private boolean showing = false;
	
	private class showThread extends Thread {
		public showThread() {
			if (showing) return;
			showing = true;
			this.setDaemon(true);
			start();
		}
		
		public void run() {
			popup();
			UserHelper.show(3, "<< 按下 ‘添加’ 添加项目", win.W, -1);
			showing = false;
		}
	}
	
	private class breakHideThread extends Thread {
		public breakHideThread() {
			this.setDaemon(true);
			start();
		}
		
		public void run() {
			Rectangle rv = win.getBounds();
			while (hiding) {
				rv = win.getBounds();
				Tools.sleep(200);
				if (rv.contains(MouseInfo.getPointerInfo().getLocation())) {
					breakHiden();
					break;
				}
			}
		}
	}

	@Override
	public void exit() {
		holding = false;
		hide();
		saveAutoPlay();
	}

	@Override
	public int getPriority() {
		return 12;
	}
	
	/** 垂直滚动条鼠标监听器*/
	protected void setScrollBarMouseListener() {
		JScrollBar bar = win.getJScrollPane().getVerticalScrollBar();
		bar.addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				isSelected = true;
			}
			
			public void mouseReleased(MouseEvent e) {
				isSelected = false;
			}
		});
	}
}
