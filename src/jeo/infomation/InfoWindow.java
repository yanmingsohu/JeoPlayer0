// CatfoOD 2008-7-2 下午10:38:24

package jeo.infomation;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import javax.swing.JButton;


import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import jeo.assistant.Color2;
import jeo.assistant.Tools;
import jeo.ui.UIFactory;
import jeo.usefuhelp.UserHelper;

/** 原本应该使用JWindow,但是JWindow的事件似乎有问题改为JFrame */
class InfoWindow extends JFrame {
	
	private static final long 
	serialVersionUID = -1070064388535151955L;
	
	private InfoWindowCtrl ctrl;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JPanel jPanel2 = null;

	/**
	 * This method initializes 
	 * 
	 */
	public InfoWindow() {
		super("Beo Player!");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		initialize();
		ctrl = new InfoWindowCtrl(this);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setBounds(new Rectangle(0, 0, 200, 200));
        this.setContentPane(getJPanel());
        this.setBackground(Color2.black);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setBackground(Color2.black);
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
			jPanel.add(getJPanel2(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(15);
			flowLayout.setVgap(15);
			jPanel1 = new JPanel();
			jPanel1.setBackground(Color2.black);
			jPanel1.setLayout(flowLayout);
			jPanel1.add(getJButton(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			
			jButton.setSize(150, 40);
			jButton.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
			jButton.setUI(UIFactory.getButtonUI());
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ctrl.hide();
					UserHelper.show(4, "在图标上点右键，选择 ‘退出’", -100, -30);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBackground(Color2.black);
			jPanel2.setLayout(new BoxLayout(getJPanel2(), BoxLayout.X_AXIS));
		}
		return jPanel2;
	}
	
	/**
	 * 把in加入到内部窗口，
	 * 并保证会返回一个有效的关闭方法
	 */
	public IHide setInfoPanel(JPanel in) {
		Tools.waitExit(ctrl.hide());
		jPanel2.removeAll();
		jPanel2.validate();
		jPanel2.add(in);
		validate();
		Tools.waitExit( ctrl.show() );
		
		return ctrl;
	}
	
	public void closeInfoPanle() {
		Tools.waitExit( ctrl.hide() );
	}
}
