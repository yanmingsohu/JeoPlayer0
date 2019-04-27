// CatfoOD 2008-7-7 ÏÂÎç08:17:43

package jeo.test;

import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.sound.sampled.spi.FormatConversionProvider;

import sun.misc.Service;

import com.sun.media.sound.JDK13Services;

public class testProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.loadLibrary("codec/mp3spi1.9.4.jar");
		p("start.");
		
//		tstSoundProperties(FormatConversionProvider.class);
//		getProviders();
//		jdk13(FormatConversionProvider.class);
//		system();
	}

	public static void jdk13(Class c) {
		List<?> list = JDK13Services.getProviders(c);
		for (int i=0; i<list.size(); ++i) {
			p(list.get(i));
		}
	}
	
	public static void tstSoundProperties(Class c) {
		Iterator i = Service.providers(c);
		while (i.hasNext()) {
			p(i.next());
		}
	}
	
	public static void getProviders() {
		Provider[] ps = Security.getProviders();
		for (Provider p: ps) {
			p(p);
			Enumeration key = p.keys();
			while (key.hasMoreElements()) {
				p("\t"+key.nextElement());
			}
			p("");
		}
	}
	
	public static void system() {

		Properties ps = System.getProperties();
		
		Enumeration<Object> em = ps.keys();
		
		while (em.hasMoreElements()) {
			Object key = em.nextElement();
			p(key+"\t"+ps.getProperty(key.toString()) );
		}
	}
	
	public static void p(Object o) {
		System.out.println(o);
	}
}
