// CatfoOD 2008-6-30 ÏÂÎç06:34:04

package jeo.assistant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SystemExit extends Thread {
	private static List<ExitCompare> exit = new ArrayList<ExitCompare>();
	private static boolean exited = false;
	
	private static int showdownTimeout = 9000;
	
	private SystemExit() {
		Runtime.getRuntime().addShutdownHook(this);
	}
	
	public static void registerExit(IExit i) {
		exit.add(new ExitCompare(i));
	}
	
	public static void unregisterExit(Object o) {
		for (int i=0; i<exit.size(); ++i) {
			if (exit.get(i).equals(o)) {
				exit.remove(i);
				return;
			}
		}
	}
	
	public static Thread exit() {
		if (exited) return null;
		exited = true;
		Thread t = new Thread() {
			public void run() {
				Collections.sort((List)exit);
				for (int i=0; i<exit.size(); ++i) {
					exit.get(i).exit();
				}
				Tools.waring("\nSystem exit..");
				System.exit(0);
			}
		};
		t.start();
		return t;
	}
	
	public void run() {
		Thread t = exit();
		if (t!=null) {
			try {
				t.join(showdownTimeout);
			} catch (InterruptedException e) {
			}
		}
	}
}

class ExitCompare implements Comparable<IExit>, IExit {
	private IExit i;
	private boolean exited = false;
	
	public ExitCompare(IExit i) {
		this.i = i;
	}

	public int compareTo(IExit o) {
		return i.getPriority()- o.getPriority();
	}
	
	public boolean equals(Object o) {
		return o == i;
	}
	
	public void exit() {
		if (exited) return;
		exited = true;
		Tools.p(i.getClass());
		i.exit();
		Tools.waring(" exited. ("+i.getPriority()+")");
	}

	public int getPriority() {
		return i.getPriority();
	}
}