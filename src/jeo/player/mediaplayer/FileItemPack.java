// CatfoOD 2008-7-2 下午07:21:00

package jeo.player.mediaplayer;

import java.awt.Image;
import java.io.File;

import jeo.assistant.Color2;
import jeo.assistant.Tools;
import jeo.decoder.DecodeFactory;
import jeo.decoder.IDecode;
import jeo.playlist.FileButtonItem;
import jeo.playlist.IListItem;


public class FileItemPack implements IListItem, IImageListener{
	private static final Image DEFAULTPIC =
			FileButtonItem.formatImageSize(Color2.getLogoPic());
	
	static {
		Tools.waitForImage(DEFAULTPIC);
	}
	
	private Object type = DEFAULTPIC;
	private File f;
	
	/**
	 * 不检查文件的有效性
	 */
	public FileItemPack(String f) {
		this(new File(f));
	}

	/**
	 * 不检查文件的有效性
	 */
	public FileItemPack(File f) {
		this.f = f;
		loadImage(f);
	}
	
	private void loadImage(final File f) {
		NetImage.registerImageListener(this);
	}
	
	@Override
	public String getName() {
		return f.getName();
	}
	
	@Override
	public Object getObject() {
		return f;
	}
	
	@Override
	public Object getType() {
		return type;
	}		
	
	/**
	 * 文件列表的保存依赖于这个方法的实现
	 */
	public final String toString() {
		return f.getPath();
	}

	@Override
	public void setImage(Image img) {
		type = img;
	}

	@Override
	public String getSaveString() {
		return f.getPath();
	}

	@Override
	public String getExtendInfo() {
		return null;
	}

	@Override
	public String getKey() {
		return MusicFileKey.getKey(f);
	}

	@Override
	public void setExtendInfo(String s) {
	}
}

class MusicFileKey {
	/** 取得文件描述的关键字 */
	public static String getKey(File f) {
		if (f==null) return null;
		
		IDecode id = DecodeFactory.getDecode(f);
		if (id==null) return null;
		
		String key = id.makeKey(f);
		key = Tools.encode(key);
		key = formatkey(key);

		if (key==null || key.trim().length()<2) {
			key = f.getName();
			int exn = key.indexOf('.');
			if (exn>0) key = key.substring(0, exn);
			key = formatkey(key);
		}

		return key;
	}
	
	private static final String formatkey(String key) {
		if (key==null) return null;
		return key.replaceAll("[0-9\\p{Punct}]", " ").trim();
	}
	
}