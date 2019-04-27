// CatfoOD 2008-6-30 ����07:40:30

package jeo.player.radio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jeo.assistant.Color2;
import jeo.assistant.IExit;
import jeo.assistant.Parabola;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;
import jeo.core.IPositionListener;
import jeo.core.IVSTPlus;
import jeo.core.VSTFactory;
import jeo.decoder.DecoderException;
import jeo.decoder.WmaPlayer;
import jeo.infomation.DefaultInfo;
import jeo.main.IMainPanelCtrl;
import jeo.main.IinsertMenu;
import jeo.player.mediaplayer.NetImage;
import jeo.playlist.IListItem;
import jeo.playlist.IPlaylistCtrl;
import jeo.playlist.PlaylistFactory;
import jeo.playlist.PlaylistListener;
import jeo.ui.LabelButton;
import jeo.ui.VolumeFactory;
import jeo.ui.VolumeLabel;
import jeo.usefuhelp.UserHelper;


public class RadioPlayer implements IMainPanelCtrl, PlaylistListener, IExit {
	private IPlaylistCtrl playlist;
	private WmaPlayer wma;
	private IVSTPlus vst;
	private TimeThread TT;
	private int currentvol = 74;
	private boolean muted = false;
	private VolumeLabel vol = null;
	
	public RadioPlayer() {
		if (!initWma()) return;
		
		vst = VSTFactory.get();
		TT = new TimeThread();
		vol = VolumeFactory.get();
		playlist = PlaylistFactory.getPlaylist();
		playlist.addPlaylistListener(this);
		playlist.setList( initImage(AutoDownRadioList.getFileMms()) );
		playlist.autoPlayListSelect();
		SystemExit.registerExit(this);
	}
	
	/** �ɹ�����true; */
	private boolean initWma() {
		try {
			wma = new WmaPlayer();
			return true;
		} catch (DecoderException e) {
			wma = null;
			Tools.showError("ϵͳ��֧��������룬�޷�ʹ������������.");
			e.printStackTrace();
			return false;
		}
	}
	
	public void play(final IListItem itm) {
		if (wma==null||itm==null) return;
		if (playthread!=null) playthread.stopplay();
		playthread = new PlayThread(itm);
	}
	
	private PlayThread playthread = null;
	
	private class PlayThread extends Thread {
		private IListItem itm;
		private boolean stoped = false;
		public PlayThread(IListItem item) {
			setDaemon(true);
			itm = item;
			start();
		}
		public void run() {
			String errorString = "";
			try {
				itm.setExtendInfo("�Ե�...");
				boolean res = wma.play(itm.getObject().toString());
				
				if (res) { 
					errorString = "������...";
				} else { 
					errorString = "���ŷ������������ԣ���̨";
				}
			
			} catch (Error e) {
				errorString = "�����쳣:" + e.getMessage();
			}
			if (!stoped)
				itm.setExtendInfo(errorString);
		}
		/** �ȴ����Ž��� */
		public void stopplay() {
			stoped = true;
			try {
				wma.stop();
				join();
			} catch (Exception e) {
			}
			itm.setExtendInfo("");
		}
	}
	
	public void downKey() {
		play(playlist.moveOffset(1));
	}

	
	public void leftKey() {
		play(playlist.moveOffset(-5));
	}
	
	public void play() {
		play(playlist.moveOffset(0));
	}

	
	public void rightKey() {
		play(playlist.moveOffset(5));
	}

	
	public void stop() {
		if (playthread!=null) {
			playthread.stopplay();
		}
	}

	
	public void upKey() {
		play(playlist.moveOffset(-1));
	}

	
	public void volumedown() {
		wma.setVolume(getCurrentVolume(-1));
	}

	
	public void volumeup() {
		wma.setVolume(getCurrentVolume(1));
	}

	public void mute() {
		if (muted) {
			wma.setVolume(getCurrentVolume(0));
			muted = false;
		} else {
			wma.setVolume(0);
			muted = true;
		}
		vol.setMute(muted);
	}
	

	private static final int MAX = 74;
	private final Parabola para = new Parabola(MAX, -MAX, WmaPlayer.MAXVOLUME);
	
	/**
	 * �ı����� add>0 �������� ��֮����
	 * ���ض�ϵͳ������Χ��ӳ��
	 */
	private int getCurrentVolume(int add) {
		muted = false;
		int t = currentvol + add;
		if (t>=0 && t<=MAX) {
			currentvol = t;
		}
		vol.setVolume(currentvol);
		return WmaPlayer.MAXVOLUME - para.get(currentvol);
	}
	
	public LabelButton[] getMenus(IinsertMenu i) {
		initMenus();
		jl = new LabelButton("������");
		jl.addMouseListener(ML);
		insertMenu = i;
		return new LabelButton[]{jl};
	}

	private IinsertMenu insertMenu = null;
	private LabelButton jl = null;
	private LabelButton[] menus = null;
	private String[] menunames = {"����", "����", "�б�", "��Ч��", "��Ч:��"};
	
	public LabelButton[] initMenus() {
		if (menus==null) {
			menus = new LabelButton[menunames.length];
			for (int i=0; i<menunames.length; ++i) {
				menus[i] = new LabelButton(menunames[i]);
				menus[i].addMouseListener(ML);
			}
			if (vst.vstIsOpen()) {
				menus[4].setFontColor(Color2.green);
				menus[4].setText("��Ч:��");
			}
		}
		return menus;
	}
	
	private MouseAdapter ML = new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			final Object s = e.getSource();
			if (s==jl) {
				insertMenu.insertMenus(initMenus());
			} else
				
			if (s==menus[1]) {
				new DefaultInfo();
			} else
				
			if (s==menus[2]) {
				playlist.display();
			} else 
				
			if (s==menus[0]) {
				UserHelper.manulShow();
			} else 
				
			if (s==menus[3]) {
				vst.openEditor();
			} else 
				
			if (s==menus[4]) {
				if ( !vst.vstIsOpen() ) {
					menus[4].setFontColor(Color2.green);
					menus[4].setText("��Ч:��");
					vst.openAllVst();
				} else {
					menus[4].setFontColor(LabelButton.getDefaultColor());
					menus[4].setText("��Ч:��");
					vst.closeAllVst();
				}
			}
			
		}
	};

	@Override
	public void addPositionListener(IPositionListener i) {
		TT.addPositionListener(i);
	}

	@Override
	public void removeAll() {
		exit();
	}

	@Override
	public void removePositionListener(IPositionListener i) {
		TT.removePositionListener(i);
	}

	@Override
	public IListItem[] requireItems() {
		RadioSelect rs = new RadioSelect();
		rs.watiForWait();
		return initImage( rs.getSelectRadio() );
	}
	
	private Mms[] initImage(Mms[] mms) {
		if (mms==null) return null;
		for (int i=0; i<mms.length; ++i) {
			NetImage.registerImageListener(mms[i]);
		}
		return mms;
	}

	@Override
	public void selectChanged(IListItem i) {
		play(i);
	}

	@Override
	public void exit() {
		stop();
		AutoDownRadioList.saveMmsList(playlist.getList());
		TT.ending();
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
	private class TimeThread extends Thread {
		private int time = 0;
		private IPositionListener pl = null;
		private int waittime = 300;
		private boolean runing = true;
		
		public TimeThread() {
			this.setDaemon(true);
			this.setPriority(MIN_PRIORITY);
			this.start();
		}
		
		public void addPositionListener(IPositionListener i) {
			time = 0;
			pl = i;
		}
		
		public void removePositionListener(IPositionListener i) {
			pl = null;
		}
		
		public void run() {
			while (runing) {
				if (pl!=null) {
					if (++time>100) time = 0;
					pl.positionChange(time);
				}
				
				try {
					Thread.sleep(waittime);
				} catch (InterruptedException e) {
				}
			}
		}
		
		public void ending() {
			runing = false;
		}
	}
}


