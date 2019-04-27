// CatfoOD 2008-7-4 ����11:05:49

package jeo.infomation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.BorderFactory;

import jeo.assistant.Color2;
import jeo.ui.UIFactory;

public class FileChooserInfo extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
	private JList jList = null;
	private JPanel jPanel1 = null;
	private JComboBox jComboBox = null;
	private JButton jButton = null;
	private JPanel jPanel2 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	
	private IHide info;
	private FileSystemView fsv = 
		FileSystemView.getFileSystemView();  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public FileChooserInfo(File f) {
		super();
		// ��ʼ���ȴ����
		WaitPanel wp = new WaitPanel();
		info = InfoFactory.showInfo(wp);
		initialize();
		
		// �Ѵ���Ĳ���������ǰ��Ŀ¼���������Ĳ������ļ�
		// ��ȡ���ļ���Ŀ¼
		if (f!=null) {
			if (f.isFile()) f = f.getParentFile();
			setCurrentSelectFile(f);
		}
		
		info.waitingForShow();
		wp.add(this);
		wp.validate();
	}
	
	// ------------------------- API ----------------------------
	private File[] selectedFiles = new File[0];
	
	/**
	 * �ȴ��û�ѡ��
	 * �������߳�
	 */
	public void watiForWxit() {
		info.waitForOK();
	}
	
	/**
	 * ����ѡ����ļ�����,�����п��ܰ����ļ�/Ŀ¼
	 * @return ���û��ѡ���ļ��򷵻� ����Ϊ0������
	 */
	public File[] getSelectedFiles() {
		return selectedFiles;
	}
	
	/**
	 * ����״̬�����ı�
	 */
	public void setStateField(String text) {
		getStateField().setText(text);
	}
	
	/**
	 * ��״̬������ΪĬ������
	 */
	public void setDefaultStateText() {
		setStateField("��ѡ�������ļ�(�ļ���)��ӵ�ѡ���б�,��OK������ѡ����ļ���ӵ������б�.");
	}
	
	/**
	 * �رմ��ڵı�ݷ���
	 */
	private void close() {
		InfoFactory.hideInfo();
	}
	
	// ------------------------- GUI ----------------------------
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 800);
		this.setLayout(new BorderLayout());
		this.add(getJSplitPane(), BorderLayout.CENTER);
		this.add(getStateField(), BorderLayout.SOUTH);
		this.setUI(UIFactory.getPanelUI());
		this.setBorder(BorderFactory.createEmptyBorder(130, 30, 50, 70));
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
			jSplitPane.setLeftComponent(getJPanel());
			jSplitPane.setResizeWeight(0.45);
			jSplitPane.setRightComponent(getJScrollPane());
			jSplitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setUI(UIFactory.getPanelUI());
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel.add(getJPanel1(), BorderLayout.NORTH);
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.getViewport().setBackground(Color2.black);
			jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane1.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color2.lightGray));
			jScrollPane1.setViewportView(getJTable());
			jScrollPane1.getVerticalScrollBar().setUI(UIFactory.getBeoScrollBarUI());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setDefaultRenderer(FileTableMode.class, MCR);
			jTable.setUI(UIFactory.getTableUI());
			jTable.setRowSelectionAllowed(false);
			jTable.setShowGrid(false);
			jTable.setTableHeader(null);
			jTable.addMouseListener(TableML);
			jTable.addMouseListener(new MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) {
					setStateField("˫���ļ�ֱ�����(����Add to list���ѡ����ļ���)");
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList(selectedListModel);
			jList.setBackground(Color2.black);
			jList.setCellRenderer(MCR);
			jList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (e.getClickCount()<2) return;
					Object value = jList.getSelectedValue();
					selectedListModel.removeElement(value);
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
					setStateField("˫��ɾ��ѡ����ļ�");
				}
			});
		}
		return jList;
	}
	
	private DefaultListModel selectedListModel = new DefaultListModel();

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(25);
			flowLayout.setHgap(10);
			jPanel1 = new JPanel();
			jPanel1.setUI(UIFactory.getPanelUI());
			jPanel1.setLayout(flowLayout);
			jPanel1.add(getJComboBox(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton4(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.addActionListener(ComboxAL);
			jComboBox.setEditable(false);
			jComboBox.setMaximumRowCount(10);
			jComboBox.setRenderer(MCR);
			jComboBox.setUI(UIFactory.getComboBoxUI());
			
			File[] fs = File.listRoots();
			for (int i=0; i<fs.length; ++i) {
				jComboBox.addItem(fs[i]);
			}
			if (fs.length>0) {
				jComboBox.setSelectedIndex(0);
				setCurrentSelectFile(fs[0]);
			}
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("[ ���� ]");
			jButton.setUI(UIFactory.getButtonUI());
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setCurrentSelectFile(parentDir);
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
			flowLayout1.setAlignment(FlowLayout.RIGHT);
			flowLayout1.setHgap(15);
			flowLayout1.setVgap(20);
			jPanel2 = new JPanel();
			jPanel2.setUI(UIFactory.getPanelUI());
			jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel2.setLayout(flowLayout1);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton1(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("ȡ��");
			jButton1.setUI(UIFactory.getButtonUI());
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
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
			jButton2.setText("ȷ��");
			jButton2.setUI(UIFactory.getButtonUI());
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setStateField("���ڷ����б�...");
					int size = selectedListModel.getSize();
					selectedFiles = new File[size];
					for (int i=0; i<size; ++i) {
						selectedFiles[i] = (File) selectedListModel.elementAt(i);
					}
					close();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("��ӵ��б�");
			jButton3.setUI(UIFactory.getButtonUI());
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					File f = getSelectFile();
					addtoSelectList(f);
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("[ ���ظ�Ŀ¼ ]");
			jButton4.setUI(UIFactory.getButtonUI());
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					File f = (File) jComboBox.getSelectedItem();
					setCurrentSelectFile(f);
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes stateField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getStateField() {
		if (stateField == null) {
			stateField = new JTextField();
			stateField.setEditable(false);
			stateField.setUI(UIFactory.getTextFieldUI());
			stateField.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color2.lightGray));
			stateField.setForeground(Color2.red);
			setDefaultStateText();
		}
		return stateField;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
			UIFactory.setJScrollPane(jScrollPane);
		}
		return jScrollPane;
	}

	/** �б�ͱ����ļ���Ⱦ�� */
	private MyCellRenderer MCR = new MyCellRenderer();
	
	// ----------------------- Event -------------------------
	/**
	 * չ��Ŀ¼fΪ��ǰ��Ŀ¼�б�,���f==null��ִ�в���
	 */
	private void setCurrentSelectFile(File f) {
		if (f==null) return;
		
		setStateField("���Ե�...");
		File[] fs = f.listFiles();
		if (fs!=null) {
			MCR.clearList();
			parentDir = f.getParentFile();
			getJTable().setModel(new FileTableMode(fs));
		}
		setDefaultStateText();
	}
	/** ������һ��Ŀ¼ */
	private File parentDir;  //  @jve:decl-index=0:
	
	/**
	 * �� Ŀ¼/�ļ� ��ӵ�ѡ���б����fΪnull��ִ�в���
	 */
	private void addtoSelectList(File f) {
		if (f==null) return;
		if (!selectedListModel.contains(f))
			selectedListModel.addElement(f);
	}
	
	/**
	 * ���ļ�ѡ���б��еõ���ǰѡ����ļ�/�ļ���
	 * ���û��ѡ�񷵻� null
	 */
	private File getSelectFile() {
		int c = jTable.getSelectedColumn();
		int r = jTable.getSelectedRow();
		if (c<0 || r<0) return null;
		File f = (File)jTable.getValueAt(r, c);
		return f;
	}
	
	/**
	 * ���ļ�ѡ�������
	 */
	private ActionListener ComboxAL = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			File f = (File)jComboBox.getSelectedItem();
			setCurrentSelectFile(f);
		}
	};
	
	// �ļ�ѡ���б����˫���¼�
	public MouseAdapter TableML = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount()<2) return;
			File f = getSelectFile();
			if (f==null) return;
			if (f.isDirectory()) {
				setCurrentSelectFile(f);
			} 
			else if (f.isFile()){
				addtoSelectList(f);
			}
		}
	};  //  @jve:decl-index=0:
	private JTextField stateField = null;
	private JScrollPane jScrollPane = null;
	
	/**
	 * �ļ����洢ģ��
	 */
	private class FileTableMode extends AbstractTableModel {
		private static final long serialVersionUID = 6280388434903915228L;
		public ArrayList<File> list = new ArrayList<File>();

		public FileTableMode(File[] f) {
			for (int i=0; i<f.length; ++i) {
				list.add(f[i]);
			}
		}
		public int getColumnCount() {
			return 3;
		}
		public int getRowCount() {
			int colc = getColumnCount();
			if (colc==0) return 0;
			return list.size()/colc+1;
		}
		public Object getValueAt(int rowIndex, int columnIndex) {
			int index = rowIndex* getColumnCount()+ columnIndex;
			if (index>=list.size()) return null;
			return list.get(index);
		}
		public Class<?> getColumnClass(int columnIndex) {
			return FileTableMode.class;
		}
	}
	
	/** ����б����Ⱦ�� */
	private class MyCellRenderer implements 
	ListCellRenderer, TableCellRenderer 
	{
		 private static final int MAPSIZE = 500;
		 private HashMap<File, JLabel> fmap;
		 
	     public MyCellRenderer() {
	    	 fmap = new HashMap<File, JLabel>(MAPSIZE);
	     }

		 public Component getTableCellRendererComponent(
				 JTable table, Object value, boolean isSelected, 
				 boolean hasFocus, int row,int column) 
		 {
			 return getRenderer((File)value, isSelected, hasFocus);
		 }
		 
	     public Component getListCellRendererComponent(
	    		 JList list,Object value,int index, 
	    		 boolean isSelected,boolean cellHasFocus) 
	     {
	    	 return getRenderer((File)value, isSelected, cellHasFocus);
	     }
	     
	     private Component getRenderer(File f, boolean isSelected, boolean hasFocus) {
	    	 if (f==null) return null;
	    	 JLabel jl = getLabel(f);
	    	 if (jl==null) return null;
	    	 
	         Color background;
	         Color foreground;
	         if (hasFocus) {
	             background = Color2.red.darker();
	             foreground = Color2.white;
	         // check if this cell is selected
	         } else if (isSelected) {
	             background = Color2.gray;
	             foreground = Color2.WHITE;
	         // unselected, and not the DnD drop location
	         } else {
	             background = Color2.BLACK;
	             foreground = Color2.LIGHT_GRAY;
	         };
	         jl.setBackground(background);
	         jl.setForeground(foreground);
	         return jl;
	     }
	     
	     private JLabel getLabel(File f) {
	    	 JLabel label = fmap.get(f);
	    	 if (label!=null) return label;
	    	 
	    	 label = new JLabel();
    		 if (f.canRead()) {
    			 label.setText(fsv.getSystemDisplayName(f));
    			 label.setIcon(fsv.getSystemIcon(f));
    		 } else { 
    			 label.setText(f.getName());
    		 }
    		 label.setMinimumSize(new java.awt.Dimension(0, 25));
    		 label.setOpaque(true);
    		 fmap.put(f, label);
	         return label;
	     }
	     
	     /** ��ֹ�ڴ���������������Ч�� */
	     public void clearList() {
	    	 if (fmap.size()>2000) {
	    		 fmap.clear();
	    	 }
	     }
	 }
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
