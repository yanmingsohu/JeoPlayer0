// CatfoOD 2008-6-30 下午01:06:31

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
			Tools.showError("找不到音频设备，不能播放音乐文件");
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
			// 制造一个假的自然停止事件,达到跳过不能播放文件的目的
			stop(this.getClass());
		}
	}
	
	/**
	 * 双击延时，当达到指定的延时时间返回true,否则返回false，确保播放列表对双击的响应
	 * @param f - 判断是否需要延时
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
	// ----------------------- 主控制面板响应 ------------------	
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

	/** 播放项目 */
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

	// ---------------------- 面板按钮 ----------------------
	public LabelButton[] getMenus(final IinsertMenu i) {
		initButton();
		LabelButton set = new LabelButton("音乐");
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
			conf = new LabelButton("设置");
			conf.addMouseListener(ML);
			randomb = new LabelButton("随机");
			randomb.addMouseListener(ML);
			find = new LabelButton("列表");
			find.addMouseListener(ML);
			info = new LabelButton("关于");
			info.addMouseListener(ML);
			help = new LabelButton("帮助");
			help.addMouseListener(ML);
			showvst = new LabelButton("音效器");
			showvst.addMouseListener(ML);
			
			vstsw = new LabelButton("音效:关");
			vstsw.addMouseListener(ML);
			
			if (vst.vstIsOpen()) {
				vstsw.setFontColor(Color2.green);
				vstsw.setText("音效:开");
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
					vstsw.setText("音效:开");
				} else {
					vst.closeAllVst();
					vstsw.setFontColor(LabelButton.getDefaultColor());
					vstsw.setText("音效:关");
				}
			}
		}
	};

	// ---------------------- 播放列表 ----------------------
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
	
	/** 遍历目录树 */
	private void ransackFileDir(File f, ArrayList<IListItem> list, FileChooserInfo fc) {
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (int i=0; i<fs.length; ++i) {
				ransackFileDir(fs[i], list, fc);
			}
		}
		else if (f.isFile()) {
			if (DecodeFactory.canDecode(f)) {
				fc.setStateField("添加文件: "+f);
				list.add(new FileItemPack(f));
			} else {
				fc.setStateField("无效的音频文件: "+f);
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
