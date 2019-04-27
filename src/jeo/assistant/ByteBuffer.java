// CatfoOD 2008-7-16 上午08:06:08

package jeo.assistant;

public class ByteBuffer {
	private int DEFAULTLEN = 600;
	private byte[] buff = new byte[0];
	private int point = 0;
	
	/**
	 * 建立默认的缓冲区长度:30 
	 */
	public ByteBuffer() {
		this(30);
	}
	
	
	/**
	 * 建立一个字符缓冲区,长度为len
	 * @param len - 缓冲区的长度
	 */
	public ByteBuffer(int len) {
		buff = new byte[len];
		DEFAULTLEN = len;
	}
	
	/**
	 * 将字符添加到末尾
	 * @param c - 要添加的字符
	 */
	public void append(byte c) {		
		if (point>=buff.length) {
			reAllotArray();
		}
		buff[point++] = c;
	}
	
	public int length() {
		return point;
	}
	
	/**
	 * 将字符添加到末尾
	 */
	public void append(char c) {
		append((byte)c);
	}
	
	/**
	 * 将字符添加到缓冲区末尾
	 */
	public void append(int c) {
		append((byte)c);
	}
	
	/**
	 * 批量添加的方法
	 * @param b - 字节数组
	 * @param s - 起事索引
	 * @param e - 结束索引
	 */
	public void append(byte[] b, int s, int e) {
		if (!(s>=0 && e>s && e<=b.length)) throw new IndexOutOfBoundsException(); 
			
		for (int i=s; i<e; ++i) {
			append(b[i]);
		}
	}
	
	private void reAllotArray() {
		byte[] newbuff = new byte[buff.length*2];
		copy(newbuff, buff, buff.length);
		buff = newbuff;
		DEFAULTLEN = buff.length;
		// 垃圾回收!!
		System.gc();
	}
	
	private void copy(byte[] src, final byte[] dec, int len) {		
		for (int i=0; i<len; ++i) {
			src[i] = dec[i];
		}
	}
	
	/**
	 * 字符缓冲区的字符串表示
	 */
	public String toString() {
		return new String(buff, 0, point);
	}
	
	/**
	 * 清空缓冲区,循环利用???
	 */
	public void delete() {
		buff = new byte[DEFAULTLEN];
		point = 0;
		System.gc();
	}
	
	public byte[] getArray() {
		byte[] arr = new byte[point];
		copy(arr, buff, arr.length);
		return arr;
	}
	
	public byte[] getBytes() {
		return getArray();
	}
}
