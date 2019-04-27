// CatfoOD 2009-5-13 上午09:29:28

package jeo.usefuhelp;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import jeo.assistant.Tools;


public final class UserHelper extends JDialog implements ActionListener {
	private final static int W = 260;
	private final static int H = 100;
	private final static UserHelper myself = new UserHelper();
	private InPanel inp = null;
	

	private final static String CloseFile = "closehelp";
	private static int crrentpriority = 0;
	private static boolean close = false;
	
	static {
		close = CloseFile.equals(Tools.getConfig(CloseFile));
	}
	
	private UserHelper () {
		if (!close) {
			init();
		}
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setModalityType(ModalityType.MODELESS);
		this.setSize(W, H);
		Tools.ComponentMoveCenter(this);
		initButtonLinster();
	}
	
	private void init() {
		close = false;
		if (inp==null) {
			inp = new InPanel();
			this.setContentPane(inp);
		}
		crrentpriority = 0;
	}
	
	private void initButtonLinster() {
		inp.getJButton().addActionListener(this);
		inp.getJButton1().addActionListener(this);
	}

	/**
	 * 强制启动帮助
	 */
	public static void manulShow() {
		myself.init();
		Tools.setConfig(CloseFile, "false");
		showCenter("CatfoD java BeoPlayer");
	}
	
	/**
	 * 使用默认优先级=1
	 */
	public static void show(String text, int x, int y) {
		show(1, text, x, y);
	}
	
	/**
	 * 在屏幕x,y位置显示对话框
	 * 如果x<0从屏幕的右面算起, x=9999
	 * 如果y<0从屏幕的下面算起, y=9999
	 * @param priority - 帮助优先级，如果优先级低于当前的优先级则不予执行
	 * 					如果<0则设置优先级为abs(priority);
	 */
	public static void show(int priority, String text, int x, int y) {
		if (close) return;
		
		if (priority<0) crrentpriority = -priority;
		else if (priority<crrentpriority) return;
		else crrentpriority = priority;

		myself.setText(text);
		myself.setVisible(true);
		myself.setAlwaysOnTop(true);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if (x<0) {
			x = d.width+x -W;
		} else if (x==9999) {
			x = (d.width-W) / 2;
		}
		if (y<0) {
			y = d.height+y -H;
		} else if (y==9999) {
			y = (d.height-H) /2;
		}
		myself.moveto(x,y);
	}
	
	public static void showCenter(String text) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width-W) / 2;
		int y = (d.height-H) /2;
		show(text, x, y);
	}
	
	public void moveto(int x, int y) {
		movebreak = true;
		while (!moveover) Tools.sleep(100);
		movee(x, true);
		movee(y, false);
	}
	
	private boolean movebreak = false;
	private boolean moveover = true;
	private int stepSize = 20;
	
	/**
	 * @return - 如果move被中断返回 false;
	 */
	private void movee(int d, boolean isx) {
		int x = this.getX();
		int y = this.getY();
		int s;
		if (isx) s = x;
		else s = y;
		
		int step = s>d? -1: 1;
		step = step * stepSize;
		movebreak = false;
		moveover = false;
		while (Math.abs(s-d)>stepSize && !movebreak) {
			if (isx)
				this.setLocation(s, y);
			else 
				this.setLocation(x, s);
			s+=step;
			Tools.sleep(10);
		}
		if (!movebreak) {
			if (isx)
				this.setLocation(d, y);
			else 
				this.setLocation(x, d);
		}
		movebreak = false;
		moveover = true;
	}
	
	public void setText(String text) {
		inp.setText(text);
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false);

		if ( inp.isCloseButton(e.getSource()) ) {
			close = true;
			Tools.setConfig(CloseFile, CloseFile);
		}
	}
	
	public static void main(String[] a) {
		Tools.p("start");
		show("aaaa", 1,1);
		Tools.sleep(600);
		show("bbbb", 300, 300);
	}
}
