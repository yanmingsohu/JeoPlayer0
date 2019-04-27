// CatfoOD 2008-7-1 обнГ10:21:32

package jeo.test;

public class testString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "1000  bit";
		boolean b = s.matches("[0-9]+[\\s]{0,}bytes|bit");
		System.out.println(b);
		
		s = s.replaceAll("bit", "");
		System.out.println(s);
	}

}
