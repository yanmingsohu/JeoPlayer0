// CatfoOD 2008-6-29 上午08:04:06

package jeo.assistant;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Tools {	
	public static boolean DEBUG = true;
	private static PrintStream out;
	
	/** 核心线程优先级 */
	public static final int CORETHREADPRIORITY = Thread.MAX_PRIORITY;
	
	/** 特别版开关 */
	public static final boolean STYLE = false;
	
	private static String CONFIGFILE = "config/config.inf";
	private static Properties config = new Properties();
	
	/** 将组件居中 */
	public static void ComponentMoveCenter(Component c) {
		int w = c.getWidth();
		int h = c.getHeight();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)((d.getWidth()-w) /2);
		int y = (int)((d.getHeight()-h)/2);
		c.setLocation(x, y);
		if (c instanceof Window) {
			Window jf = (Window)c;
			jf.setAlwaysOnTop(true);
			jf.setAlwaysOnTop(false);
		}
	}
	
	public static void exit() {
		SystemExit.exit();
	}
	
	/** 显示错误对话框 */
	public static void showError(Object o) {
		JOptionPane.showMessageDialog(null, ""+o, "Error.", JOptionPane.ERROR_MESSAGE);
	}
	
	public static Image creatImage(byte[] bs) {
		return Toolkit.getDefaultToolkit().createImage(bs);
	}
	
	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public static void p(Object o) {
		if (DEBUG)
		out.print(o);
	}
	
	public static void pl(Object o) {
		p(o+"\n");
	}
	
	public static void waring(Object o) {
		if (DEBUG)
		out.println(o);
	}
	
	public static void waring(Object classs, Object text) {
		waring(classs.getClass(), text);
	}
	
	public static void waring(Class c, Object t) {
		waring(c+" : "+t);
	}
	
	/**
	 * 读取file指定的文件，以换行为分隔符，返回分隔后的数组
	 * 以'#'号作为注释，不处理
	 * @param file
	 * @return - 如果读取失败返回长度为0的数组
	 */
	public static String[] readConfig(String file) {
		final String ANNO = "#";
		ArrayList<String> buff = new ArrayList<String>();
		try {
			FileInputStream fin = new FileInputStream(file);
			BufferedReader in
			   = new BufferedReader(new InputStreamReader(fin));
			String line = in.readLine();
			while (line!=null) {
				if (!(line.startsWith(ANNO) || line.trim().length()<1)) {
					buff.add(line);
				}
				line = in.readLine();
			}
		} catch(Exception e) {
			waring(Tools.class, "read conf fault: "+e);
		}
		return buff.toArray(new String[0]);
	}
	

	/** 保存conf到f中 */
	public static void saveConfig(String conf, String f) {
		if (f==null || conf==null) return;
		try {
			FileOutputStream out = new FileOutputStream(f);
			out.write(conf.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			Tools.waring("save config fault: "+e);
		}
	}
	
	/**
	 * 可以返回null
	 */
	public static String getConfig(String key) {
		return config.getProperty(key);
	}
	
	public static void setConfig(String k, String v) {
		config.setProperty(k, v);
	}
	
	/**
	 * 如果o为null,会抛出异常
	 */
	public static final void checkNull(final Object o) {
		if (o==null) throw new NullPointerException();
	}
	
	/**
	 * 同Thread.sleeep()
	 */
	public static void sleep(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * 阻塞操作直到线程退出
	 */
	public static void waitExit(Thread t) {
		if (t==null) return;
		try {
			t.join();
		} catch(Exception e) {
		}
	}
	
	/**
	 * 指定的文件的扩展名在s中出现，返回true
	 */
	public static boolean isExpandName(File f, String[] s) {
		if (f==null || s==null) return false;
		
		String name = f.getName();
		String[] exname = name.split("[.]");
		if (exname.length<2) return false;
		
		name = exname[exname.length-1].trim();
		if (name.length()<1) return false;
		
		for (int i=0; i<s.length; ++i) {
			if ( name.equalsIgnoreCase(s[i]) ) {
				return true;
			}
		}
		return false;
	}
	
	public static void openWeb() {
		final String path = 
			//	"http://user.qzone.qq.com/412475540/blog/1219542957";
				"http://catfo0d.blog.sohu.com/129851399.html";
		URI url;
		try {
			url = new URI(path);
			Desktop desp = Desktop.getDesktop();
			desp.browse(url);
		} catch (Exception e) {
		}
	}
	
	/** 给字符串编码为ISO-8859-1字符*/
	public static final String encode(Object s) {
		try {
			if (s==null) return null;
			byte[] bs = s.toString().getBytes("ISO-8859-1");
			
			return new String(bs);
		} catch (UnsupportedEncodingException e) {
			return s.toString();
		}
	}
	
	/** 等待img加载完毕返回 */
	public static boolean waitForImage(Image img) {
		return ImageWait.waitForImage(img);
	}
	
	public static void show(Object message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	private Tools() {}
	
	private static OutputStream NOTOUT = new OutputStream() {
		public void write(int b) throws IOException {
			/*
			if (DEBUG) 
				throw new 
			NullPointerException("not use:'System.out.println()'");
			*/
		}
	};
	
	static { // init
		out = System.out;
		System.setOut(new PrintStream(NOTOUT));
		
		try {
			config.load(new FileReader(CONFIGFILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SystemExit.registerExit(new IExit() {
			public void exit() {
				try {
					FileWriter fw = new FileWriter(CONFIGFILE);
					config.store(fw, "CatfoOD 2009");
					fw.flush();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			public int getPriority() {
				return 2000;
			}
		});
	}
}


class ImageWait {
	/** 完成无错返回true */
	public static boolean waitForImage(Image img) {
		int id = getID();
		mt.addImage(img, id);
		try {
			mt.waitForID(id);
			return !mt.isErrorID(id);
		} catch (InterruptedException e) {
		} finally {
			mt.removeImage(img, id);
		}
		return false;
	}
	
	private static MediaTracker mt = new MediaTracker(new JLabel());
	private static int id = 0;
	private static int getID() {
		++id;
		if (id>=Integer.MAX_VALUE) id = 0;
		return id;
	}
}

