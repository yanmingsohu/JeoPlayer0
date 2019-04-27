// CatfoOD 2008-6-30 上午11:46:03

package jeo.ui;

import java.awt.Color;

import javax.swing.JPanel;

import jeo.assistant.Color2;


/**
 * 黑色背景的JPanel
 */
public class JPanel2 extends JPanel {
	{
		this.setBackground(Color2.black);
	}
	
	/**
	 * 不允许改变背景色
	 */
	public void setBackground(Color c) {};
}
