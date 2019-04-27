// CatfoOD 2008-7-16 下午12:48:18

package jeo.player.mediaplayer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;

import jeo.assistant.IExit;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;
import jeo.playlist.FileButtonItem;


/**
 * 本地图片数据库
 */
public class LocalImageDB implements IExit {
	private ImageDBIndexFile indexFile = new ImageDBIndexFile();
	private ImageDBDataFile dbFile = new ImageDBDataFile();
	private static LocalImageDB my = new LocalImageDB();
	
	public static LocalImageDB getThis() {
		return my;
	}
	
	private LocalImageDB() {
		SystemExit.registerExit(this);
	}
	
	/**
	 * 搜索关键字引用的图片
	 * @param key - 关键字
	 * @return - 找不到返回null
	 * @throws IOException
	 */
	public synchronized final Image load(String key) {
		try {
			FilePosition fp = indexFile.getPositon(key);
			if (fp==null) return null;
			
			return dbFile.loadImage(fp);
		} catch(Exception e) {
			Tools.waring(this, e);
			return null;
		}
	}
	
	/**
	 * 保存关键字引用的图片
	 * @param key - 关键字
	 * @param img - 图片
	 */
	public synchronized final void save(String key, Image img) {
		try {
			if (key==null || img==null) return;
			
			FilePosition fp = dbFile.save(img);
			indexFile.save(key, fp);
		} catch(Exception e) {
			Tools.waring(this, e);
		}
	}
	
	public synchronized final void clear() throws IOException {
		indexFile.clear();
		dbFile.clear();
	}

	@Override
	public void exit() {
		indexFile.close();
		dbFile.close();
	}

	@Override
	public int getPriority() {
		return 90;
	}
}


/** 图片数据索引 */
class ImageDBIndexFile {
	public final static String f = ("config/imageDBIndex.db");
	private RandomAccessFile file = null;
	
	public void save(String key, FilePosition fp) throws IOException {
		initFile();
		file.seek(file.length());
		
		file.writeUTF(key);
		file.writeLong(fp.start);
		file.writeLong(fp.length);
	}
	
	public FilePosition getPositon(String key) throws IOException {
		initFile();
		file.seek(0);
		long start = 0;
		long len = 0;
		String nkey = null;
		
	try {
		while (true) {
			nkey = file.readUTF();
			start = file.readLong();
			len = file.readLong();
			
			if (key.equals(nkey)) {
				return new FilePosition(start, len);
			}
		}
		}catch(EOFException eof) {
			return null;
		}
	}
	
	private void initFile() throws IOException {
		if (file==null) 
		file = new RandomAccessFile(f, "rw");
	}
	
	protected void clear() throws IOException {
		initFile();
		file.setLength(0);
	}
	
	protected void close() {
		try {
			if (file==null) return;
			file.close();
		} catch (IOException e) {
		}
	}
}


/** 图片数据库 */
class ImageDBDataFile {
	public final static String f = ("config/imageDB.db");
	private RandomAccessFile file = null;
	
	/** 保存图片并返回在数据库中的位置 */
	public FilePosition save(Image img) throws IOException {
		initFile();
		
		int s = FileButtonItem.getSidecar();
		BufferedImage rend = new BufferedImage(s, s, BufferedImage.TYPE_3BYTE_BGR);
		rend.createGraphics().drawImage(img, 0, 0, null);
		
		OUTSTREAM out = new OUTSTREAM();
		ImageIO.write(rend, "JPEG", out);
		out.close();
		return out.getFilePosition();
	}
	
	private class OUTSTREAM extends OutputStream {
		long start = 0;
		long len = 0;
		public OUTSTREAM() throws IOException {
			file.seek(file.length());
			start = file.getFilePointer();
		}
		
		public void write(int b) throws IOException {
			file.write(b);
		}
		
		public void close() throws IOException {
			len = file.getFilePointer() - start;
		}
		
		public FilePosition getFilePosition() {
			return new FilePosition(start, len);
		}
	}
	
	/** 在数据库文件中的指定位置读取图片 */
	public Image loadImage(FilePosition fp) throws IOException {
		initFile();
		if (fp==null) return null;
		if (fp.start+fp.length>=file.length()) return null;
		
		return ImageIO.read(new INSTREAM(fp));
	}
	
	private class INSTREAM extends InputStream {
		private long start;
		private long len;
		private long readlen = 0;
		
		public INSTREAM(FilePosition fp) throws IOException {
			start = fp.start;
			len = fp.length;
			file.seek(start);
		}
		
		@Override
		public int read() throws IOException {
			++readlen;
			if (readlen>=len) return -1;
			return file.read();
		}
	}
	
	private void initFile() throws IOException {
		if (file==null) 
		file = new RandomAccessFile(f, "rw");
	}
	
	protected void clear() throws IOException {
		initFile();
		file.setLength(0);
	}
	

	protected void close() {
		try {
			if (file==null) return;
			file.close();
		} catch (IOException e) {
		}
	}
}


class FilePosition {
	public FilePosition(long s, long l) {
		start = s;
		length = l;
	}
	public final long start;
	public final long length;
	
	public String toString() {
		return start+" "+length;
	}
}

