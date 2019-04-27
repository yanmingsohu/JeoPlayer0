
import java.io.File;

import jeo.assistant.MessageDialog;
import jeo.assistant.SystemIcon;
import jeo.assistant.Tools;
import jeo.main.PlayerWindow;
import jeo.usefuhelp.UserHelper;


// CatfoOD 2008-6-27 ����07:43:03

public class mainc {
	public static void main(String[] args) {
		MessageDialog.registerUncaughtException();
		parameterDebug(args);
		checkConfigPath();
		
		try {
			UserHelper.showCenter("��ӭʹ��JeoPlayer,����뿪�����");

			new SystemIcon();
			new PlayerWindow();
		} catch(Exception e) {
			Tools.showError(e);
			e.printStackTrace();
		}
	}
	
	public static void parameterDebug(String[] s) {
		Tools.DEBUG = checkPara(s, "debug");
	}
	
	public static boolean checkPara(String[] args, String key) {
		if (args==null) return false;
		for (int i=0; i<args.length; ++i) {
			if (args[i].equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
	
	public static void checkConfigPath() {
		File path = new File("config");
		if (!path.isDirectory()) {
			if (!path.mkdir()) {
				Tools.showError("���ܴ�������Ŀ¼��" +
						"���ֶ��ڰ�װĿ¼�д���'config'�ļ���");
			}
		}
	}
}
