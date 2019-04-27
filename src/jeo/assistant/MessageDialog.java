// CatfoOD 2008.2.7

package jeo.assistant;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URLEncoder;

/**
 * 便捷显示消息的类,建立后调用 show(Title, Text) 方法.
 * @author CatfoOD
 */
public class MessageDialog extends Dialog 
	implements Thread.UncaughtExceptionHandler {
	
	private Label  label  = new Label();
	private Button button = new Button("     确定     ");
	private Button send   = new Button("发送错误报告");
	private Button about  = new Button(" 详细信息 ");
	private final WA wa = new WA(); 
	
	public static void registerUncaughtException() {
		Thread.setDefaultUncaughtExceptionHandler(new MessageDialog());
	}
	
	/**
	 * 建立一个消息对话框
	 */
	public MessageDialog() {
		this(null);
	}
	
	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		show(arg0, arg1);
	}
	
	/**
	 * 使用顶层窗口初始化有模式的MessageDialog
	 * @param f - 上层窗口
	 */
	public MessageDialog(Frame f) {
		super(f,false);
		setResizable(false);
		addWindowListener(wa);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int width = dim.width/3;
		int height= dim.height/7;
		int x = (int)( (dim.width-width)/2 );
		int y = (int)( (dim.height-height)/2);
		setBounds(x, y, width, height);
		
		button.addActionListener(wa);
		about.addActionListener(wa);
		send.addActionListener(wa);
		
		Panel pan = new Panel(new FlowLayout(FlowLayout.CENTER,8,10));
		pan.add(button);
		pan.add(about);
		pan.add(send);
		label.setAlignment(Label.CENTER);
		setLayout(new BorderLayout());
		add(label, 	BorderLayout.CENTER);
		add(pan,	BorderLayout.SOUTH);
	}
	
	/**
	 * 显示对话框
	 * @param title - 标题String
	 * @param message - 消息String,长度>20,会打开详细消息对话框
	 */
	public void show(String title, String message) {
		mge = message;
		int enter = message.indexOf('\n');
		if (enter<=0 || enter>20) enter = 20;
		if (message.length()>enter ) {
			message = message.substring(0, enter)+"        . . .";
			about.setVisible(true);
		}else{
			about.setVisible(false);
		}
		setTitle(title);
		label.setText(message);
		validate();
		setVisible(true);
	}
	
	public void show(String message) {
		show("Message.",message);
	}
	
	public void show(Thread t, Throwable e) {
		StackTraceElement[] list = e.getStackTrace();
		StringBuffer buff = new StringBuffer();
		
		buff.append("Thread: "+t.getName());
		buff.append(" [ID:"+t.getId()+"]");
		buff.append("\n\n");
		buff.append(e.getMessage());
		buff.append("\n");
		buff.append(e.toString());
		for (int i=0; i<list.length; ++i) {
			buff.append("\n\tat");
			buff.append(list[i]);
		}
		show("程序出现一个错误 - -!", buff.toString());
	}
	
	private void sendmail() {
		try {
			String body = URLEncoder.encode(mge, "UTF-8");
			String mail = "mailto:yanming-sohu@sohu.com" +
							"?subject=Beoplayer_Report&body="+body;
			URI mailtoURI = new URI(mail);
			Desktop desk = Desktop.getDesktop();
			desk.mail(mailtoURI);
		} catch (Exception e) {
			Tools.show("发送失败: "+e.getMessage());
		}
	}
	
	private class WA extends WindowAdapter implements ActionListener {
		public void windowClosing(WindowEvent e) {
			setVisible(false);
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==button) {
				setVisible(false);
			}
			else if (e.getSource()==send) {
				sendmail();
			}
			else if (e.getSource()==about) {
				showMore();
			}
		}
	}
	
	public void showMore() {
		md.show(mge);
	}
	private String mge;
	private MoreDialog md = new MoreDialog(this);
}

class MoreDialog extends Dialog {
	private TextArea t = new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
	
	MoreDialog(Dialog d) {
		super(d, "详细信息", true);
		setResizable(false);
		WA wa = new WA();
		addWindowListener(wa);
		Button b = new Button("关闭");
		b.addActionListener(wa);
		t.setEditable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)(dim.width/2.3f);
		int height= (int)(dim.height/3.5f);
		int x = (int)( (dim.width-width)/2 );
		int y = (int)( (dim.height-height)/2);
		setBounds(x, y, width, height);
		
		setLayout(new BorderLayout());
		add(t, BorderLayout.CENTER);
		add(b, BorderLayout.SOUTH);
	}
	public void show(String text) {
		t.setText(text);
		validate();
		setVisible(true);
	}
	private class WA extends WindowAdapter implements ActionListener {
		public void windowClosing(WindowEvent e) {
			setVisible(false);
		}
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	public int getColumns() {
		return t.getColumns();
	}
}

