// CatfoOD 2008-7-16 ����08:06:08

package jeo.assistant;

public class ByteBuffer {
	private int DEFAULTLEN = 600;
	private byte[] buff = new byte[0];
	private int point = 0;
	
	/**
	 * ����Ĭ�ϵĻ���������:30 
	 */
	public ByteBuffer() {
		this(30);
	}
	
	
	/**
	 * ����һ���ַ�������,����Ϊlen
	 * @param len - �������ĳ���
	 */
	public ByteBuffer(int len) {
		buff = new byte[len];
		DEFAULTLEN = len;
	}
	
	/**
	 * ���ַ���ӵ�ĩβ
	 * @param c - Ҫ��ӵ��ַ�
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
	 * ���ַ���ӵ�ĩβ
	 */
	public void append(char c) {
		append((byte)c);
	}
	
	/**
	 * ���ַ���ӵ�������ĩβ
	 */
	public void append(int c) {
		append((byte)c);
	}
	
	/**
	 * ������ӵķ���
	 * @param b - �ֽ�����
	 * @param s - ��������
	 * @param e - ��������
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
		// ��������!!
		System.gc();
	}
	
	private void copy(byte[] src, final byte[] dec, int len) {		
		for (int i=0; i<len; ++i) {
			src[i] = dec[i];
		}
	}
	
	/**
	 * �ַ����������ַ�����ʾ
	 */
	public String toString() {
		return new String(buff, 0, point);
	}
	
	/**
	 * ��ջ�����,ѭ������???
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
