// CatfoOD 2009-5-20 ����08:46:38

package jeo.assistant;

public class Parabola {
	private float w, p, my, sx;
	/**
	 * ����������
	 * @param x1 >=0  
	 * @param x2 >x1
	 * @param Y y�����ֵ >0�������� <0�������� 
	 */
	public Parabola(float X1, float X2, float Y) {
		if (X1<X2) {
			w = Math.abs((X2-X1)/2);
			sx = X1;
		} else {
			w = Math.abs((X1-X2)/2);
			sx = X2;
		}
		my = Y;
		p = (w*w) / -my;
	}
	
	/**
	 * ����x��y ���ص�|y|<=��ʼ��Y�����ֵ
	 * @param x X1>x<X2
	 */
	public float get(float x) {
		x = x-sx-w;
		return x*x/(p) + my;
	}
	
	public int get(int x) {
		return (int) get((float)x);
	}

	public static void main(String[] args) {
		int s,d;
		s = -10;
		d = 30;
		Parabola p = new Parabola(s, d, -10);
		for (int i=s; i<d; ++i) {
			Tools.pl("( "+i+" : "+p.get(i)+" )  ");
		}
	}
}
