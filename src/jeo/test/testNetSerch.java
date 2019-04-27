// CatfoOD 2008-7-15 œ¬ŒÁ07:13:24

package jeo.test;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jeo.assistant.Tools;
import jeo.player.mediaplayer.NetImage;



public class testNetSerch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tools.DEBUG = true;
		Image i = NetImage.searchImage("“Ù¿÷");
		
		JFrame win = new JFrame();
		win.setLayout(new BorderLayout());
		final JLabel jl = new JLabel();
		jl.setIcon(new ImageIcon(i));
		final JButton bu = new JButton("ok");
		final JTextField tx = new JTextField();
		
		bu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long s = System.currentTimeMillis();
				Image img = NetImage.searchImage(tx.getText());
				jl.setIcon(new ImageIcon(img));
				Tools.waring("use: "+(System.currentTimeMillis()-s));
			}
		});
		
		win.add(jl);
		win.add(bu, BorderLayout.NORTH);
		win.add(tx, BorderLayout.SOUTH);
		win.setSize(300, 300);
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
