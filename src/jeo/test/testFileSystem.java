// CatfoOD 2008-7-4 ионГ10:02:17

package jeo.test;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class testFileSystem {
	private static 
	FileSystemView fsv = FileSystemView.getFileSystemView();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printfiles( fsv.getRoots() );
		p("");
		printfiles( File.listRoots());
	}

	public static void printfiles(File[] fs) {
		for (File f: fs) {
			System.out.println(fsv.getSystemDisplayName(f));
//			p(fsv.getChild(f, ""));
		}
	}
	
	public static void p(Object o) {
		System.out.println(o);
	}
}
