// CatfoOD 2009-6-3 下午05:50:17

package jeo.test;

public class testTrim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = " a b c ";
		String b = " a    1    2     4    5     6        ";
		pl( trim(a) );
		pl( trim(b) );
	}

	/**
	 * 删除输入的字符串中的所有空格
	 * 并返回他
	 */
	public static String trim(String in) {
		final char TRIM = ' ';
		
		char[] ch = new char[in.length()];
		char[] read = in.toCharArray();
		int len = 0;
		
		for (int i=0; i<read.length; ++i) {
			if (read[i]!=TRIM) {
				ch[len++] = read[i];
			}
		}
		return new String(ch, 0, len);
	}
	
	public static void pl(Object o) {
		System.out.println(o);
	}
}
