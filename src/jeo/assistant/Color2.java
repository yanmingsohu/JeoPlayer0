// CatfoOD 2008-7-13 ÉÏÎç10:46:54

package jeo.assistant;

import java.awt.Color;
import java.awt.Image;

public class Color2 extends Color {
	private static final long serialVersionUID = -5784555101306894539L;
	
	public Color2(int r, int g, int b) {
		super(r, g, b);
	}
	
	public static final Color BLACK;
	public static final Color black;
	public static final Color WHITE;
	public static final Color white;
	
	private static final Image logopic;
	
	static {
		if (Tools.STYLE) {
			BLACK = Color.white;
			black = Color.white;
			WHITE = Color.black;
			white = Color.black;
			logopic = Tools.creatImage(_beo3_gif.getImage());
		} else {
			BLACK = Color.black;
			black = Color.black;
			WHITE = Color.white;
			white = Color.white;
			logopic = Tools.creatImage(_beo2_gif.getImage());
		}
		Tools.waitForImage(logopic);
	}
	
	
	public static final Image getLogoPic() {
		return logopic;
	}
}
