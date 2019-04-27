// CatfoOD 2008-7-15 下午09:23:48

package jeo.player.mediaplayer;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jeo.assistant.ByteBuffer;
import jeo.assistant.Tools;
import jeo.playlist.FileButtonItem;



public class NetImage {
	private static final int TIMEOUT = 5000;
	private static final int MAXLOADTHREAD = 4;
	private NetImage() {}
	
	private static LocalImageDB localdb = LocalImageDB.getThis();
	
	private static ArrayList<String> 
				currentProcesskey =new ArrayList<String>();
	
	private static ArrayList<IImageListener> 
				list = new ArrayList<IImageListener>();
	
	private static Map<String, Image>
				map = new HashMap<String, Image>();
	
	/** 移除所有图像加载监听器 */
	public static void removeAllListener() {
		list.clear();
		currentProcesskey.clear();
	}
	
	public static void registerImageListener(IImageListener ii) {
		list.add(ii);
		creatLoadThread();
	}
	
	private static int loadthreadcount = 0;
	private static final void creatLoadThread() {
		if (loadthreadcount<MAXLOADTHREAD) {
			new LoadImageThread();
			++loadthreadcount;
		}
	}
	
	private static class LoadImageThread extends Thread {
		public LoadImageThread() {
			this.setDaemon(true);
			this.setPriority(MIN_PRIORITY);
			start();
		}
		public void run() {
			int index = 0;
			
			while (true) {
				IImageListener ii = null;
			synchronized (list) {
					if (index>=list.size()) break;
					ii = list.get(index);
				}
				String key = ii.getKey();
				
			synchronized (currentProcesskey) {
				if (currentProcesskey.contains(key)) {
					++ index;
					continue;
				} else {
					index = 0;
					currentProcesskey.add(key);
				}
				}

				Image img = map.get(key);
				if (img==null){
					
					img = localdb.load(key);
					if (img==null) {
						
						if (key!=null && key.startsWith("http://")) {
							img = readUrlImage(key);
						} 
						
						if (img==null){
							img = searchImage(key);
						}
						
						if (img!=null) {
							map.put(key, img);
							localdb.save(key, img);
						}
						
					} else {
						map.put(key, img);
					}
				}
				
				if (img!=null) ii.setImage(img);
				list.remove(ii);
				currentProcesskey.remove(key);
			}
			--loadthreadcount;
			Tools.sleep(100);
		}
	}
	
	public static final void clearLocalImageDB() {
		try {
			localdb.clear();
		} catch (IOException e) {
			Tools.waring(NetImage.class, e);
		}
	}
	
	
	// ------------------------网络直接搜索------------------------------
	/**
	 * 搜索关键字引用的图片
	 * 找不到返回null
	 */
	public static Image searchImage(String key) {
		if (key==null || key.trim().length()<1) return null;
		
		String imgs = searchImageKey(key);
		ArrayList<String> list = findImageUrl(imgs);
		if (list==null) return null;
		for (int i=0; i<list.size(); ++i) {
			Image img = readUrlImage(list.get(i));
			if (img!=null) {
				return img;
			}
		}
		return null;
	}
	
	/**
	 * 读取urls所引用的图片
	 * 失败返回null
	 */
	public static Image readUrlImage(String urls) {
		try {
			URL url = new URL(urls);
			InputStream in = creatConnection(url);
			ByteBuffer bbu = new ByteBuffer();
			
			int r = in.read();
			while (r>=0) {
				bbu.append(r);
				r = in.read();
			}
			
			byte[] imgArr = bbu.getArray();
			Image img = Toolkit.getDefaultToolkit().createImage(imgArr);
			img = FileButtonItem.formatImageSize(img);
			
			if (Tools.waitForImage(img)) {
				return img;
			}
		} catch(Exception e) {
//			Tools.waring(e);
		}
		return null;
	}

	/**
	 * 返回网页内容的图片urls
	 */
	private static ArrayList<String> findImageUrl(String imgs) {
		if (imgs==null) return null;
		final String PROCSTR = "http://tbn";
		final String ENDCSTR = " ";
		String temp = null;
		int startIndex=0, endIndex=0;

		ArrayList<String> list = new ArrayList<String>();
		while (true) {
			startIndex = imgs.indexOf(PROCSTR, endIndex);
			endIndex = imgs.indexOf(ENDCSTR, startIndex);
			if (startIndex<0 || endIndex<0) break;
			temp = imgs.substring(startIndex, endIndex);
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回关键字搜索到的网页内容
	 */
	private static String searchImageKey(String key) {
		try {
			URL url = getKeyUrl(key);
			InputStream in = creatConnection(url);
			
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			StringBuffer buff = new StringBuffer();
			
			String te = read.readLine();
			while (te!=null) {
				buff.append(te);
				te = read.readLine();
			}
			
		return buff.toString();
		} catch(Exception e) {
//			Tools.waring(e);
		}
		return null;
	}
	
	// --------------------------------网络连接-------------------------------------
	public static InputStream creatConnection(URL url) throws IOException {
//		Tools.waring(url);
		URLConnection con = url.openConnection();
		
		con.setConnectTimeout(TIMEOUT);
		// google 防止了java的url连接，所以需要造假
		con.setRequestProperty("User-Agent", "Mozilla");
		return con.getInputStream();
	}
	
	/**
	 * 取得搜索的url, 并包装关键字
	 */
	private static URL getKeyUrl(String key) {
		final String url = 
			"http://images.google.cn/images?um=1&complete=1&" +
			"hl=zh-CN&lr=lang_zh-CN&newwindow=1&q=";
		
		final String end = "&aq=-1&oq=";
		
		try {
			key = URLEncoder.encode(key, "UTF-8");
			return new URL(url+ key+ end);
		} catch (Exception e) {
			Tools.waring(e);
		}
		return null;
	}
	
	public static final void p(Object o) {
		Tools.waring(o);
	}
}

