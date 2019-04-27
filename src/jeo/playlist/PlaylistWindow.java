// CatfoOD 2008-7-2 下午12:42:28

package jeo.playlist;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javax.swing.BoxLayout;

import jeo.assistant.Color2;
import jeo.assistant.Tools;
import jeo.ui.UIFactory;

public class PlaylistWindow extends JFrame {
	private PlaylistWindowCtrl ctrl;

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButtonhold = null;
	private JPanel jPanel1 = null;
	private JPanel ItemPanel = null;
	
	/** 播放列表的宽度*/
	public static final int W = 400;
	private BoundedRangeModel brm = null;
	
	/**
	 * @param owner
	 */
	protected PlaylistWindow() {
		super("Beo Player list.");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setUndecorated(true);
		initialize();
		setSize(W, Tools.getScreenSize().height);
		this.setAlwaysOnTop(true);
		ctrl = new PlaylistWindowCtrl(this);
		this.setVisible(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(419, 362);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setUI(UIFactory.getLabelUI());
			jLabel.setForeground(Color2.red);
			jLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
			jLabel.setText("播放列表");
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			jContentPane = new JPanel();
			jContentPane.setUI(UIFactory.getPanelUI());
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	protected JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBorder(new EmptyBorder(0,0,0,0));
			jScrollPane.setBackground(Color2.black);
			jScrollPane.getViewport().setBackground(Color2.black);
			jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane.setViewportView(getItemPanel());
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.getVerticalScrollBar().setUI(UIFactory.getBeoScrollBarUI());
			jScrollPane.getVerticalScrollBar().setUnitIncrement(35);
			brm = jScrollPane.getVerticalScrollBar().getModel();
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(15);
			flowLayout.setVgap(35);
			flowLayout.setAlignment(FlowLayout.RIGHT);
			jPanel = new JPanel();
			jPanel.setUI(UIFactory.getPanelUI());
			jPanel.setLayout(flowLayout);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButtonHold(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			setJButton(jButton);
			jButton.setText("添加");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ctrl.addItems();
					refurbishList();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			setJButton(jButton1);
			jButton1.setText("删除当前项");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ctrl.removeSelect();
					refurbishList();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除全部");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ctrl.removeAll();
					refurbishList();
				}
			});
			setJButton(jButton2);
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getJButtonHold() {
		if (jButtonhold == null) {
			jButtonhold = new JButton();
			jButtonhold.setText("锁定 >");
			jButtonhold.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ctrl.hold();
				}
			});
			setJButton(jButtonhold);
		}
		return jButtonhold;
	}
	
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(FlowLayout.LEFT);
			flowLayout1.setHgap(20);
			jPanel1 = new JPanel();
			jPanel1.setUI(UIFactory.getPanelUI());
			jPanel1.setLayout(flowLayout1);
			jPanel1.add(jLabel, null);
		}
		return jPanel1;
	}
	
	/** 对所有按钮统一设置外观 */
	private void setJButton(JButton b) {
		b.setSize(150, 40);
		b.setOpaque(false);
		b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		b.setUI(UIFactory.getButtonUI());
	}

	/**
	 * This method initializes ItemPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getItemPanel() {
		if (ItemPanel == null) {
			ItemPanel = new JPanel();
			ItemPanel.setLayout(new BoxLayout(getItemPanel(), BoxLayout.Y_AXIS));
			ItemPanel.setUI(UIFactory.getPanelUI());
		}
		return ItemPanel;
	}

	public PlaylistWindowCtrl getCtrl() {
		return ctrl;
	}
	
	/**
	 * 返回垂直滚动条的总长度
	 */
	protected int getBarMax() {
		return brm.getMaximum();
	}
	
	protected int getBarPosition() {
		return brm.getValue();
	}
	
	protected int getExtent() {
		return brm.getExtent();
	}
	
	/**
	 * 设置滚动条的位置
	 * @category 这里有一个补丁
	 */
	protected void setBarPosition(int i) {
		//这是一个补丁
		try {
			/**
			 *  setValue 会抛出一个异常
			 *  sun.java2d.NullSurfaceData cannot be cast to 
			 *  sun.java2d.d3d.D3DSurfaceData java.lang.ClassCastException: 
			 */
			brm.setValue(i);
			jScrollPane.validate();
		} catch(Exception e) {
		}
	}
	
	protected void refurbishList() {
		getItemPanel().validate();
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
