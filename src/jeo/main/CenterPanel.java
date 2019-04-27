// CatfoOD 2008-6-29 下午03:04:38

package jeo.main;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Rectangle;
import javax.swing.SwingConstants;

import jeo.assistant.Color2;
import jeo.assistant.IExit;
import jeo.ui.JPanel2;
import jeo.ui.LabelButton;
import jeo.ui.VolumeFactory;


public class CenterPanel extends JPanel2 {
	private CenterPanelCtrl ctrl;
	
	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private LabelButton jLabel1 = null;
	private LabelButton jLabel2 = null;
	private LabelButton jLabel3 = null;
	private LabelButton jLabel4 = null;
	private LabelButton jLabel5 = null;
	private LabelButton jLabel6 = null;
	private LabelButton jLabel7 = null;
	private LabelButton jLabel8 = null;
	private LabelButton jLabel9 = null;
	private LabelButton jLabel11 = null;

	private MenuPaneln jPanel = null;

	/**
	 * This is the default constructor
	 */
	public CenterPanel() {
		super();
		initialize();
		this.setMaximumSize(getSize());
		this.setMinimumSize(getSize());
		ctrl = new CenterPanelCtrl(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel11 = new LabelButton(Color2.red);
		jLabel11.setBounds(new Rectangle(0, 333, 70, 15));
		jLabel11.setText("");
		jLabel11.setIcon(new RedDot());
		jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.exitbutton();
			}
		});
		// jLabel10.setBounds(new Rectangle(0, 248, 70, 15));
		
		
		jLabel9 = new LabelButton();
		jLabel9.setBounds(new Rectangle(0, 216, 70, 15));
		jLabel9.setText("");
		jLabel9.setIcon(new EmptyTriangle(EmptyTriangle.DOWN));
		jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.changeVolume(-1);
			}
			public void mouseReleased(java.awt.event.MouseEvent e) {
				ctrl.changeVolume(0);
			}
		});
		
		jLabel8 = new LabelButton(Color2.BLACK);
		jLabel8.setBounds(new Rectangle(0, 202, 70, 15));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("静音");
		jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.mute();
			}
		});
		
		jLabel7 = new LabelButton();
		jLabel7.setBounds(new Rectangle(0, 188, 70, 15));
		jLabel7.setText("");
		jLabel7.setIcon(new EmptyTriangle(EmptyTriangle.UP));
		jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.changeVolume(1);
			}
			public void mouseReleased(java.awt.event.MouseEvent e) {
				ctrl.changeVolume(0);
			}
		});
		
		jLabel6 = new LabelButton();
		jLabel6.setBounds(new Rectangle(0, 144, 70, 15));
		jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel6.setText("停止");
		jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.stop();
			}
		});
		
		jLabel4 = new LabelButton();
		jLabel4.setBounds(new Rectangle(15, 88, 40, 15));
		jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel4.setText("开始");
		jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.play();
			}
		});
		
		jLabel3 = new LabelButton();
		jLabel3.setBounds(new Rectangle(55, 88, 15, 15));
		jLabel3.setText("");
		jLabel3.setIcon(new FactTriangle(FactTriangle.RIGHT));
		jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.rightKey();
			}
		});
		
		jLabel2 = new LabelButton();
		jLabel2.setBounds(new Rectangle(0, 88, 15, 15));
		jLabel2.setText("");
		jLabel2.setIcon(new FactTriangle(FactTriangle.LEFT));
		jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.leftKey();
			}
		});
		
		jLabel1 = new LabelButton();
		jLabel1.setBounds(new Rectangle(0, 60, 70, 15));
		jLabel1.setText("");
		jLabel1.setIcon(new FactTriangle(FactTriangle.UP));
		jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				ctrl.upKey();
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(jLabel4);
		group.add(jLabel2);
		group.add(jLabel3);
		
		this.setSize(70, 356);
		this.setLayout(null);
		this.add(getVolLabel(), null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		this.add(jLabel4, null);
		this.add(getJLabel5(), null);
		this.add(jLabel6, null);
		this.add(jLabel7, null);
		this.add(jLabel8, null);
		this.add(jLabel9, null);
		this.add(jLabel11, null);
		this.add(getJPanel(), null);
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JLabel getVolLabel() {
		if (jLabel == null) {
			jLabel = VolumeFactory.get();
		}
		return jLabel;
	}

	/**
	 * This method initializes jLabel5	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private LabelButton getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new LabelButton();
			jLabel5.setText("");
			jLabel5.setBounds(new Rectangle(0, 116, 70, 15));
			jLabel5.setIcon(new FactTriangle(FactTriangle.DOWN));
			jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					ctrl.downKey();
				}
			});
		}
		return jLabel5;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new MenuPaneln();
			jPanel.setBounds(new Rectangle(0, 243, 70, 82));
		}
		return jPanel;
	}
	
	protected MenuPaneln getMenuPane() {
		return jPanel;
	}
	
	protected LabelButton getExitButton() {
		return jLabel11;
	}
	
	public IsetPlayer getIsetPlayer() {
		return ctrl;
	}
	
	/**
	 * 取得关闭菜单项的方法
	 */
	public IExit getCloseMenuCtrl() {
		return ctrl;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
