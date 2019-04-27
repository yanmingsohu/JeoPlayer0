// CatfoOD 2008-6-29 下午01:56:47

package jeo.main;

import javax.swing.JPanel;

import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JWindow;


import javax.swing.BoxLayout;

import jeo.assistant.Color2;
import jeo.assistant.IExit;

public class PlayerWindow extends JWindow {
	private PlayerWindowCtrl ctrl;
	
	/** 当前主窗口的宽度 */
	public static final int WIDTH = 70;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private CenterPanel centerPanel = null;

	private TopPanel topPanel = null;

	/**
	 * @param owner
	 */
	public PlayerWindow() {
		super((Frame)null);
		initialize();
		this.setAlwaysOnTop(true);
		ctrl = new PlayerWindowCtrl(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(70, 445);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			
			jContentPane = new JPanel();
			jContentPane.setBackground(Color2.black);
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
			jContentPane.add(gettopPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.X_AXIS));
			jPanel.setBackground(Color2.black);
			jPanel.add(getCenterPanel(), null);
		}
		return jPanel;
	}

	/**
	 * 生成中央控制器
	 * @return beo.main.CenterPanel	
	 */
	private CenterPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new CenterPanel();
			centerPanel.setName(null);
		}
		return centerPanel;
	}

	/**
	 * This method initializes topPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private TopPanel gettopPanel() {
		if (topPanel == null) {
			topPanel = new TopPanel(centerPanel.getIsetPlayer());
		}
		return topPanel;
	}
	
	public IExit getCloseMenuCtrl() {
		return centerPanel.getCloseMenuCtrl();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
