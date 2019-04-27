// CatfoOD 2009-5-11 下午07:28:53

package jeo.player.radio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jeo.assistant.ByteBuffer;
import jeo.assistant.Tools;
import jeo.player.mediaplayer.FileListConfig;
import jeo.player.mediaplayer.NetImage;
import jeo.playlist.IListItem;


public class AutoDownRadioList {
	private static String root = 
		//"http://user.qzone.qq.com/412475540/blog/1242040990";
		"http://catfo0d.blog.sohu.com/116170185.html";
	
	private static String mr = 
		"\\{n=\\\"(.*?)\\\"\\s+u=\\\"(.*?)\\\"\\s+p=\\\"(.*?)\\\"\\}";
	
	private static String listFile = "config/radiolist.conf";
	
	private static Pattern pm = Pattern.compile(mr, Pattern.DOTALL);
	
	/**
	 * 取得网络收音机地址
	 * 失败返回null
	 */
	public static Mms[] getNetMms() {
		return getmms(getNetContext(root), false);
	}
	
	/**
	 * 取得本地文件的收音机列表
	 */
	public static Mms[] getFileMms() {
		ByteBuffer buff = new ByteBuffer();
		byte[] b = new byte[256];
		FileInputStream in;
		try {
			in = new FileInputStream(listFile);
			int len = in.read(b);
			while (len>0) {
				buff.append(b, 0, len);
				len = in.read(b);
			}
			String s = new String(buff.getBytes());
			return getmms(s, true);
		} catch (IOException e) {
			Tools.pl("Can't open conf file: "+e.getMessage());
		}
		return null;
	}
	
	public static void saveMmsList(IListItem[] mms) {
		FileListConfig.save(mms, listFile);
	}
	
	public static Mms[] getmms(String body, boolean notFilter) {
		String s = body;
		if (s!=null) {
			if (!notFilter)
				s = filterContext(s);
			if (s!=null) {
				s = replaceInterpunction(s);
				Matcher ms = pm.matcher(s);
				ArrayList<Mms> list = new ArrayList<Mms>();
				while (ms.find()) {
					Mms mms = new Mms(ms.group(1), ms.group(2), ms.group(3));
					list.add(mms);
				}
				return list.toArray(new Mms[0]);
			}
		}
		return null;
	}
	
	private static String getNetContext(String url) {
		try {
			InputStream in = NetImage.creatConnection(new URL(url));
			ByteBuffer buff = new ByteBuffer();
			byte[] bb = new byte[1024];
			int len = in.read(bb);
			while (len>0) {
				buff.append(bb, 0, len);
				len = in.read(bb);
			}
			return new String(buff.getBytes(), "gbk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String rt = "\\{beoplayer\\}(.*)\\{/beoplayer\\}";
	private static Pattern pf = Pattern.compile(rt, Pattern.DOTALL);
	
	private static String filterContext(String body) {
		Matcher m = pf.matcher(body);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
	private static String replaceInterpunction(String body) {
		body = body.replaceAll("&mdash;", "—");
		return body.replaceAll("&quot;", "\"");
	}
	
}
