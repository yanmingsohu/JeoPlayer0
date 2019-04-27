// CatfoOD 2009-5-13 上午09:37:31

package jeo.usefuhelp;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;

import javax.swing.JButton;

import jeo.assistant.Color2;
import jeo.ui.UIFactory;


class InPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JButton jButton = null;
	private JPanel jPanel1 = null;
	private JButton jButton1 = null;
	/**
	 * This is the default constructor
	 */
	public InPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(317, 150);
		this.setLayout(new BorderLayout());
		this.add(getJPanel(), BorderLayout.CENTER);
		this.add(getJPanel1(), BorderLayout.SOUTH);
		UIFactory.setBorder(this);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			jLabel = new JLabel();
			jLabel.setUI(UIFactory.getLabelUI());
			jLabel.setText("欢迎使用 JeoPlayer! java \nCatfoOD 2008-2010");
			jLabel.setHorizontalAlignment(JLabel.CENTER);
			jLabel.setName("jLabel");
			jPanel = new JPanel();
			jPanel.setUI(UIFactory.getPanelUI());
			jPanel.setLayout(gridLayout);
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setUI(UIFactory.getButtonUI());
			jButton.setText("永久关闭帮助");
			jButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent e) {
					jButton.setForeground(Color2.LIGHT_GRAY);
					jButton.setText("永久关闭帮助");
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
					jButton.setForeground(Color2.red);
					jButton.setText("初次使用请勿关闭");
				}
			});
		}
		return jButton;
	}
	
	public void setText(String text) {
		this.jLabel.setText(text);
	}
	
	public boolean isCloseButton(Object o) {
		return o==getJButton();
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			gridLayout1.setColumns(2);
			jPanel1 = new JPanel();
			jPanel1.setLayout(gridLayout1);
			jPanel1.setUI(UIFactory.getPanelUI());
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setUI(UIFactory.getButtonUI());
			jButton1.setText("确定");
		}
		return jButton1;
	}

}  //  @jve:decl-index=0:visual-constraint="0,-7"
