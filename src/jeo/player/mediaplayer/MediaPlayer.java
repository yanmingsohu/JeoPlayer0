// CatfoOD 2008-6-30 ����01:06:31

package jeo.player.mediaplayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;

import jeo.assistant.Color2;
import jeo.assistant.IExit;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;
import jeo.config.ConfigDialog;
import jeo.core.IPositionListener;
import jeo.core.IStopListener;
import jeo.core.IVSTPlus;
import jeo.core.PlayerMachine;
import jeo.core.VSTFactory;
import jeo.decoder.DecodeFactory;
import jeo.infomation.DefaultInfo;
import jeo.infomation.FileChooserInfo;
import jeo.main.IMainPanelCtrl;
import jeo.main.IMenus;
import jeo.main.IconFactory;
import jeo.main.IinsertMenu;
import jeo.playlist.IListItem;
import jeo.playlist.IPlaylistCtrl;
import jeo.playlist.PlaylistFactory;
import jeo.playlist.PlaylistListener;
import jeo.ui.LabelButton;
import jeo.usefuhelp.UserHelper;


public class MediaPlayer 
implements IMainPanelCtrl, PlaylistListener, IStopListener, IExit
{
	private PlayerMachine pm;
	private File previoufile;
	private IPlaylistCtrl playlist;
	private IVSTPlus vst;
	
	public MediaPlayer() {
		try {
			this.pm = new PlayerMachine();
		} catch (LineUnavailableException e) {
			Tools.showError("�Ҳ�����Ƶ�豸�����ܲ��������ļ�");
			throw new RuntimeException(e);
		}
		Tools.checkNull(pm);
		vst = VSTFactory.get();
		pm.addStopListener(this);
		playlist = PlaylistFactory.getPlaylist();
		playlist.addPlaylistListener(this);
		playlist.setList(FileListConfig.load());
		playlist.autoPlayListSelect();
		SystemExit.registerExit(this);
	}
	
	public void play(File f) {
		if (!clickDelay(f)) return;
		
		try {
			AudioInputStream in = DecodeFactory.creatBufferStream(pm, f);
			previoufile = f;
			pm.play( vst.getOutput(in) );
		} catch (Exception e) {
			Tools.waring(this, e);
			e.printStackTrace();
			Tools.pl("");
			// ����һ���ٵ���Ȼֹͣ�¼�,�ﵽ�������ܲ����ļ���Ŀ��
			stop(this.getClass());
		}
	}
	
	/**
	 * ˫����ʱ�����ﵽָ������ʱʱ�䷵��true,���򷵻�false��ȷ�������б��˫������Ӧ
	 * @param f - �ж��Ƿ���Ҫ��ʱ
	 */
	private final boolean clickDelay(final File f) {
		if (previoufile==f) {
			if ( currentDelay+ DELAY> System.currentTimeMillis() ) {
				return false;
			}
		} else {
			currentDelay = System.currentTimeMillis();
		}
		return true;
	}
	
	private final int DELAY = 5000;
	private long currentDelay = 0;
	// ----------------------- �����������Ӧ ------------------	
	public void mute() {
		pm.mute();
	}

	public void play() {
		if (pm.isPlay()) {
			pm.pause();
		}
		else if (pm.isPause()) {
			pm.play();
		}
		else {
			downKey();
		}
	}

	public void downKey() {
		play(playlist.moveOffset(1));
	}

	public void leftKey() {
		play(playlist.moveOffset(-5));
	}
	
	public void rightKey() {
		play(playlist.moveOffset(5));
	}

	public void stop() {
		pm.stop();
	}

	public void upKey() {
		play(playlist.moveOffset(-1));
	}

	/** ������Ŀ */
	private void play(IListItem i) {
		if (i==null) return;
		play((File)i.getObject());
	}
	
	public void volumedown() {
		pm.volumeFall();
	}

	
	public void volumeup() {
		pm.volumeHoist();
	}

	// ---------------------- ��尴ť ----------------------
	public LabelButton[] getMenus(final IinsertMenu i) {
		initButton();
		LabelButton set = new LabelButton("����");
		set.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				i.insertMenus(new IMenus() {
					public LabelButton[] getMenus(IinsertMenu i) {
						return new LabelButton[]{
								randomb, help, find, vstsw, init2page(i)};
					}
				});
			}
		});
		return new LabelButton[]{set};
	}
	
	private LabelButton init2page(final IinsertMenu i) {
		LabelButton	page = new LabelButton();
		page.setIcon( IconFactory.creatFactTriangle(IconFactory.DOWN) );
		page.addMouseListener( new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				i.insertMenus(new IMenus() {
					public LabelButton[] getMenus(IinsertMenu i) {
						return new LabelButton[]{info, conf, showvst};
					}
				});
			}
		} );
		return page;
	}
	
	private void initButton() {
		if (conf==null) {
			conf = new LabelButton("����");
			conf.addMouseListener(ML);
			randomb = new LabelButton("���");
			randomb.addMouseListener(ML);
			find = new LabelButton("�б�");
			find.addMouseListener(ML);
			info = new LabelButton("����");
			info.addMouseListener(ML);
			help = new LabelButton("����");
			help.addMouseListener(ML);
			showvst = new LabelButton("��Ч��");
			showvst.addMouseListener(ML);
			
			vstsw = new LabelButton("��Ч:��");
			vstsw.addMouseListener(ML);
			
			if (vst.vstIsOpen()) {
				vstsw.setFontColor(Color2.green);
				vstsw.setText("��Ч:��");
			}
		}
	}

	private LabelButton conf = null;
	private LabelButton randomb = null;
	private LabelButton find = null;
	private LabelButton info = null;
	private LabelButton help = null;
	private LabelButton showvst = null;
	private LabelButton vstsw = null;
	private boolean random = false;
	
	private MouseAdapter ML = new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			if (e.getSource()==conf) {
				new ConfigDialog(pm);
			} 
			else if (e.getSource()==randomb) {
				random = !random;
				if (random) {
					randomb.setFontColor(Color2.green);
				} else {
					randomb.setFontColor(LabelButton.getDefaultColor());
				}
			}
			else if (e.getSource()==find) {
				playlist.display();
			}
			else if (e.getSource()==info) {
				new DefaultInfo();
			}
			else if (e.getSource()==help) {
				UserHelper.manulShow();
			}
			else if (e.getSource()==showvst) {
				vst.openEditor();
			} 
			else if (e.getSource()==vstsw) {
				boolean open = !vst.vstIsOpen();
				if (open) {
					vst.openAllVst();
					vstsw.setFontColor(Color2.green);
					vstsw.setText("��Ч:��");
				} else {
					vst.closeAllVst();
					vstsw.setFontColor(LabelButton.getDefaultColor());
					vstsw.setText("��Ч:��");
				}
			}
		}
	};

	// ---------------------- �����б� ----------------------
	@Override
	public IListItem[] requireItems() {
		FileChooserInfo fc = new FileChooserInfo(previoufile);
		fc.watiForWxit();
		
		ArrayList<IListItem>list = new ArrayList<IListItem>();
		File[] files = fc.getSelectedFiles();
		for (int i=0; i<files.length; ++i) {
			ransackFileDir(files[i], list, fc);
		}
		return list.toArray(new IListItem[0]);
	}
	
	/** ����Ŀ¼�� */
	private void ransackFileDir(File f, ArrayList<IListItem> list, FileChooserInfo fc) {
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (int i=0; i<fs.length; ++i) {
				ransackFileDir(fs[i], list, fc);
			}
		}
		else if (f.isFile()) {
			if (DecodeFactory.canDecode(f)) {
				fc.setStateField("����ļ�: "+f);
				list.add(new FileItemPack(f));
			} else {
				fc.setStateField("��Ч����Ƶ�ļ�: "+f);
			}
		}
	}

	@Override
	public void removeAll() {
		exit();
	}

	@Override
	public void selectChanged(IListItem i) {
		play(i);
	}

	@Override
	public void stop(Object o) {
		if (random) {
			play(playlist.random());
		} else {
			play(playlist.moveOffset(1));
		}
	}

	@Override
	public void addPositionListener(IPositionListener i) {
		pm.addPositionListener(i);
	}

	@Override
	public void removePositionListener(IPositionListener i) {
		pm.removePositionListener(i);
	}

	@Override
	public void exit() {
		pm.stop();
		pm.release();
		vst.closeAllVst();
		NetImage.removeAllListener();
		FileListConfig.save(playlist.getList());
	}

	@Override
	public int getPriority() {
		return 1;
	}

}
