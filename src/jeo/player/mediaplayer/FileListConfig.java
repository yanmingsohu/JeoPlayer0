// CatfoOD 2008-7-2 ÏÂÎç09:43:36

package jeo.player.mediaplayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import jeo.assistant.Tools;
import jeo.playlist.IListItem;


public class FileListConfig {
	public static final String listfile = "config/list.conf";
		
	public static void save(IListItem[] list) {
		save(list, listfile);
	}
	
	public static void save(IListItem[] list, String filename) {
		try {
			FileWriter out = new FileWriter(filename);
			for (int i=0; i<list.length; ++i) {
				out.write(list[i].getSaveString());
				out.append('\n');
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			Tools.waring("Save list fault: "+e);
		}
	}
	
	public static IListItem[] load() {
		ArrayList<IListItem> list = new ArrayList<IListItem>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(listfile));
			String r = in.readLine();
			while (r!=null) {
				File f = new File(r);
				if (f.isFile()) {
					list.add( new FileItemPack(f) );
				}
				r = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			Tools.waring("Load list fault: "+e);
		}
		return list.toArray(new IListItem[0]);
	}
}
