// CatfoOD 2008-7-3 ÉÏÎç12:47:55

package jeo.test;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jeo.assistant.Tools;
import jeo.infomation.FileChooserInfo;
import jeo.infomation.InfoFactory;
import jeo.ui.BeoLookAndFeel;


public class testFileDialog {
	public static void main(String[] s) {

		FileChooserInfo f = new FileChooserInfo(null);
		
//		final JFrame frame = new JFrame();
//		frame.add(f);
//		frame.setVisible(true);	
//		frame.setSize(500,300);
//		frame.setAlwaysOnTop(true);
		
//		InfoFactory.hideInfo();
		
//		new Thread() {
//			public void run() {
//				while (true) {
//				frame.setSize(800,800);
//				frame.setLocation(100,0);
//				Tools.sleep(20);
//				}
//			}
//		}.start();
		
		System.out.println("start.");
		f.watiForWxit();
		System.out.println("end.");
		f.setStateField("over.....");
		
		File fs[] = f.getSelectedFiles();
		System.out.println(fs.length);
		for (File sf: fs) {
			f.setStateField(sf.toString());
			System.out.println(sf);
		}
		System.exit(0);
	}
}
