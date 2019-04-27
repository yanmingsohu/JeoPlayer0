// CatfoOD 2008-7-1 下午08:09:07

package jeo.config;

import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.*;


import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import jeo.assistant.Color2;
import jeo.assistant.Tools;
import jeo.core.PlayerMachine;
import jeo.player.mediaplayer.NetImage;
import jeo.ui.LabelButton;
import jeo.ui.UIFactory;

public class ConfigDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private ConfigDialogCtrl ctrl;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	/**
	 * @param owner
	 */
	public ConfigDialog(PlayerMachine pm) {
		Tools.checkNull(pm);
		initialize();
		Tools.ComponentMoveCenter(this);
		ctrl = new ConfigDialogCtrl(this, pm);
		this.setUndecorated(true);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(360, 180);
//		this.setModal(true);
//		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		this.setResizable(false);
//		this.setTitle("Beo Player Config");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(17, 71, 106, 20));
			jLabel1.setText("使用的播放缓存");
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setUI(UIFactory.getLabelUI());
			
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(17, 40, 106, 20));
			jLabel.setText("选择播放设备");
			jLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setUI(UIFactory.getLabelUI());
			
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			
			jContentPane.setUI(UIFactory.getPanelUI());
			jContentPane.add(getJButton2(), null);
			UIFactory.setBorder(jContentPane);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	protected JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(131, 40, 207, 20));
			jComboBox.setUI(UIFactory.getComboBoxUI());
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(131, 71, 207, 20));
			jTextField.setUI(UIFactory.getTextFieldUI());
		}
		return jTextField;
	}

	/**
	 * 关闭按钮
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getJButton() {
		if (jButton == null) {
			jButton = new LabelButton(Color2.white);
			jButton.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			jButton.setBounds(new Rectangle(213, 126, 112, 19));
			jButton.setText("关闭");
			jButton.setBorder(new LineBorder(Color2.gray,1));
		}
		return jButton;
	}

	/**
	 * 确定按钮
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new LabelButton(Color2.white);
			jButton1.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			jButton1.setBounds(new Rectangle(213, 105, 112, 19));
			jButton1.setText("确定");
			jButton1.setBorder(new LineBorder(Color2.gray,1));
		}
		return jButton1;
	}


	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new LabelButton(Color2.red);
			jButton2.setBounds(new Rectangle(32, 126, 125, 19));
			jButton2.setText("删除图片数据库");
			jButton2.setBorder(new LineBorder(Color2.red,1));
			jButton2.setToolTipText("清空图片缓存，释放硬盘空间");

			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					NetImage.clearLocalImageDB();
					remove(getJButton2());
					validate();
					repaint();
				}
			});
		}
		return jButton2;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
