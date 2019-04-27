// CatfoOD 2009-5-11 下午04:55:10

package jeo.player.radio;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JList;

import javax.swing.JScrollPane;

import jeo.assistant.Color2;
import jeo.assistant.Tools;
import jeo.infomation.IHide;
import jeo.infomation.InfoFactory;
import jeo.infomation.WaitPanel;
import jeo.ui.UIFactory;

public class RadioSelect extends JPanel {
	private RadioSelectCtrl ctrl;

	private static final long serialVersionUID = 1L;
	private JSplitPane jSplitPane = null;
	private JSplitPane jSplitPane1 = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JButton jButton1 = null;
	private JList jList = null;
	private JPanel jPanel4 = null;
	private JButton jButton2 = null;
	
	private IHide info;
	
	/**
	 * This is the default constructor
	 */
	public RadioSelect() {
		super();
		WaitPanel wp = new WaitPanel();
		info = InfoFactory.showInfo(wp);
		
		initialize();
		ctrl = new RadioSelectCtrl(this);
		
		info.waitingForShow();
		wp.add(this);
		wp.validate();
	}
	
	public void watiForWait() {
		info.waitForOK();
	}
	
	/**
	 * 关闭窗口的便捷方法
	 */
	private void close() {
		InfoFactory.hideInfo();
	}
	
	public Mms[] getSelectRadio() {
		Mms[] mms = new Mms[selectedListModel.size()];
		for (int i=0; i<mms.length; ++i) {
			mms[i] = (Mms) selectedListModel.elementAt(i);
		}
		return mms;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(0);
		borderLayout.setVgap(0);
		this.setLayout(borderLayout);
		this.setSize(756, 468);
		this.add(getJSplitPane(), BorderLayout.CENTER);
		this.add(getJPanel4(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(1);
			jSplitPane.setLeftComponent(getJSplitPane1());
			jSplitPane.setResizeWeight(0.65);
			jSplitPane.setRightComponent(getJScrollPane());
			jSplitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerSize(1);
			jSplitPane1.setResizeWeight(0.55);
			jSplitPane1.setTopComponent(getJPanel());
			jSplitPane1.setBottomComponent(getJPanel2());
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
			jPanel.setUI(UIFactory.getPanelUI());
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
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout);
			jPanel1.add(getJButton(), null);
			jPanel1.setUI(UIFactory.getPanelUI());
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
			jButton.setUI(UIFactory.getButtonUI());
			jButton.setText("--] 下载最新的网络电台 . . . [--");
			
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ctrl.updata();
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
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel = new JLabel();
			jLabel.setUI(UIFactory.getLabelUI());
			jLabel.setText("电台的名字");
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout1);
			jPanel2.add(getJPanel3(), null);
			jPanel2.setUI(UIFactory.getPanelUI());
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setUI(UIFactory.getLabelUI());
			jLabel3.setForeground(Color2.red);
			jLabel3.setText("");
			jLabel2 = new JLabel();
			jLabel2.setText("JLabel");
			jLabel1 = new JLabel();
			jLabel1.setUI(UIFactory.getLabelUI());
			jLabel1.setText("电台的地址");
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			gridLayout.setColumns(2);
			jPanel3 = new JPanel();
			jPanel3.setName("jPanel3");
			jPanel3.setLayout(gridLayout);
			jPanel3.setUI(UIFactory.getPanelUI());
			jPanel3.add(jLabel, null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(getJTextField(), null);
			jPanel3.add(getJTextField1(), null);
			jPanel3.add(getJButton1(), null);
			jPanel3.add(jLabel3, null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setUI(UIFactory.getTextFieldUI());
			jTextField.setColumns(20);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setUI(UIFactory.getTextFieldUI());
		}
		return jTextField1;
	}
	
	protected String getRadioName() {
		return getJTextField().getText();
	}
	
	protected String getRadioUrl() {
		return getJTextField1().getText();
	}
	
	protected void clearText() {
		getJTextField().setText("");
		getJTextField1().setText("");
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setUI(UIFactory.getButtonUI());
			jButton1.setText("--> 加入 <--");
			
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ctrl.add();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	protected JList getJList() {
		if (jList == null) {
			jList = new JList(selectedListModel);
			jList.setBackground(Color2.black);
			jList.setForeground(Color2.GRAY);
			
			jList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (e.getClickCount()<2) return;
					Object value = jList.getSelectedValue();
					selectedListModel.removeElement(value);
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
					setInfoText("双击删除选择的文件");
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new FlowLayout());
			jPanel4.setUI(UIFactory.getPanelUI());
			jPanel4.add(getJButton2(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setUI(UIFactory.getButtonUI());
			jButton2.setText("确定");
			
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
				}
			});
		}
		return jButton2;
	}
	
	protected void setInfoText(final String t) {
		if (!textseted) {
			textseted = true;
			new Thread() {
				public void run() {
					jLabel3.setText(t);
					Tools.sleep(3000);
					jLabel3.setText("");
					textseted = false;
				}
			}.start();
		}
	}
	
	private boolean textseted = false;

	protected DefaultListModel selectedListModel = new DefaultListModel();

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JScrollPane jScrollPane = null;
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			UIFactory.setJScrollPane(jScrollPane);
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
