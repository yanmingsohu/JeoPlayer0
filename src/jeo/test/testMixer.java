package jeo.test;

// CatfoOD 2008-7-2 09:09:36

import javax.sound.sampled.*;

public class testMixer {
	public static void main(String[] s) {
		Mixer.Info[] mis = AudioSystem.getMixerInfo();
		
		p("AllMixer: {");
		for (Mixer.Info mi: mis) {
			Mixer m = AudioSystem.getMixer(mi);
			p("\tMixer: {");
			p("\t\t"+mi.getName());
			pMixer(m);
			p("\t}");
		}
		p("}");
	}
	
	public static void pMixer(Mixer mix) {
		Line.Info[] lis = mix.getSourceLineInfo();
		p("\t\tLines: {");
		for (Line.Info li : lis) {
			p("\t\t\t"+li);
			try {
				Line l = mix.getLine(li);
				pControl(l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		p("\t\t}");
	}
	
	public static void pControl(Line l) {
		Control[] cs = l.getControls();
		p("\t\t\tcontrols: {");
		for (Control c: cs) {
			p("\t\t\t\t"+c);
		}
		p("\t\t\t}");
	}
	
	public static void p(Object o) {
		System.out.println(o);
	}
}
 