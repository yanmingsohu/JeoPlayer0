// CatfoOD 2008-6-30 下午03:38:06

package jeo.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.BoxLayout;


import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;

import jeo.assistant.Color2;
import jeo.assistant.IExit;
import jeo.assistant.IPlayerDigest;
import jeo.assistant.PlayerFactory;
import jeo.assistant.SystemExit;
import jeo.assistant.Tools;
import jeo.infomation.InfoFactory;
import jeo.playlist.IPlaylistCtrl;
import jeo.playlist.PlaylistFactory;
import jeo.ui.JPanel2;
import jeo.ui.LabelButton;
import jeo.ui.LineBorder2;
import jeo.usefuhelp.UserHelper;

public class TopPanel extends JPanel2 implements IExit {

	private static final long serialVersionUID = 1L;
	private IsetPlayer iset;
	
	private final String lastitem = "LASTITEM"; 
	private IPlayerDigest currentplayer = null;
	private JPanel jPanel = null;

	/**
	 * This is the default constructor
	 */
	public TopPanel(IsetPlayer iset) {
		super();
		this.iset = iset;
		initialize();
		initLastPlayer();
		initButton();
		SystemExit.registerExit(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(70, 150);
		this.setLayout(new BorderLayout());
		this.add(getLogo(), BorderLayout.NORTH);
		this.add(getJPanel(), BorderLayout.CENTER);
	}
	
	private void initButton() {
		JPanel jp = getJPanel();
		IPlayerDigest[] ipds = PlayerFactory.getPlayers();
		for (int i=0; i<ipds.length; ++i) {
			jp.add(new PlayerButton(ipds[i]));
		}
	}

	private JLabel getLogo() {
		JLabel jLabel = new JLabel();
		jLabel.setText("");
		jLabel.setIcon( new ImageIcon(Color2.getLogoPic()) );
		jLabel.setBorder(new LineBorder2(false, true, false, false));
		jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				Tools.openWeb();
			}
		});
		return jLabel;
	}
	
	/** 自动载入上次退出时使用的player */
	private void initLastPlayer() {
		String conf = Tools.getConfig(lastitem);
		if (conf!=null) {
			try {
				IPlayerDigest ipdigs = PlayerFactory.getPlayer(conf);
				switchOtherPlayer(ipdigs);
			} catch (Exception e) {
				Tools.waring(this, "load last player fault: "+e);
			}
		}
	}
	
	private class PlayerButton extends LabelButton {
		public PlayerButton(final IPlayerDigest i) {
			this.setText(i.getName());
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					switchOtherPlayer(i);
				}
			});
		}
	}
	
	private void switchOtherPlayer(IPlayerDigest news) {
		if (news!=null && currentplayer!=news) {
			IPlaylistCtrl playlist = PlaylistFactory.getPlaylist();
			playlist.switchCurrentPlayer();
			playlist.removePlaylistListener(null);
			InfoFactory.hideInfo();

			iset.setCurrentPlayer(
					PlayerFactory.getPlayerInstance(news));
			currentplayer = news;

			UserHelper.show(2, 
					"按下 "+news.getName()+ " 按钮，然后按下 ‘列表’", 
					-70, 
					Tools.getScreenSize().height/2+89);
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
		}
		return jPanel;
	}

	@Override
	public void exit() {
		if (currentplayer!=null) {
			Tools.setConfig(lastitem, currentplayer.getClass().getName());
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}
}
