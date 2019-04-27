// CatfoOD 2009-5-11 ÏÂÎç03:49:40

package jeo.player.radio;

import java.awt.Image;
import java.net.URL;

import jeo.assistant.Color2;
import jeo.assistant.Tools;
import jeo.player.mediaplayer.IImageListener;
import jeo.playlist.FileButtonItem;
import jeo.playlist.IListItem;


public class Mms implements IListItem, IImageListener{
	private static final Image DEFAULTPIC =
		FileButtonItem.formatImageSize(Color2.getLogoPic());
	
	static {
		Tools.waitForImage(DEFAULTPIC);
	}
	
	private String mms;
	private String name;
	private String picurl;
	private String extend = null;
	private Image pic = DEFAULTPIC;
	
	public Mms(String na, String mmsurl) {
		this(na, mmsurl, null);
	}
	
	public Mms(String na, String murl, String purl) {
		name = na;
		mms = murl;
		picurl = purl;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getObject() {
		return mms;
	}

	@Override
	public Object getType() {
		return pic;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object obj) {		
		if (obj==null) return false;
		if (obj instanceof Mms) {
			Mms m = (Mms) obj;		
			if ( m.name.equals(name) || m.mms.equals(mms) ) return true;
		}
		return false;
	}

	@Override
	public String getSaveString() {
		return String.format(saveFormat, name, mms, picurl);
	}
	
	private String saveFormat = "{n=\"%1$s\" u=\"%2$s\" p=\"%3$s\"}";

	@Override
	public String getExtendInfo() {
		return extend;
	}

	@Override
	public String getKey() {
		if (picurl!=null && picurl.length()>5) {
			try {
				URL url = new URL(picurl);
				url.openConnection();
				return picurl;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return name;
	}

	@Override
	public void setImage(Image img) {
		pic = img;
	}
	
	public void setExtendInfo(String s) {
		extend = s;
	}
}
