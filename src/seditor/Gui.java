package seditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Gui extends JFrame  {

	private static final long serialVersionUID = 8961611986268624022L;
	
	/**
	 * Instantiate user interface variables
	 */
	VideoPlayer vid_panel;
	JFrame frame = new JFrame("Titeleditor");
	JPanel panel_video_big, panel_effects, panel_video, panel_settings,
			setting_timeline, start_panel;
	JMenuBar menu;
	JMenu file, options, help;
	JMenuItem open, save, exit, about;
	JButton nav_start, nav_stop, nav_back, nav_forw, nav_volume;
	JButton text_cent, text_right, text_left, text_ital, text_bol, text_under;

	// image icons for video preview
	ImageIcon icon_play = new ImageIcon("img/play_24.png");
	ImageIcon icon_stop = new ImageIcon("img/stop_24.png");
	ImageIcon icon_paused = new ImageIcon("img/pause_24.png");
	ImageIcon icon_backward = new ImageIcon("img/backward_24.png");
	ImageIcon icon_forward = new ImageIcon("img/forward_24.png");
	ImageIcon icon_vol = new ImageIcon("img/volume_24.png");

	// image icons for text effects
	ImageIcon icon_center = new ImageIcon("img/center_24.png");
	ImageIcon icon_rightbound = new ImageIcon("img/rightbound_24.png");
	ImageIcon icon_leftbound = new ImageIcon("img/leftbound_24.png");
	ImageIcon icon_italic = new ImageIcon("img/italic1_24.png");
	ImageIcon icon_bold = new ImageIcon("img/bold1_24.png");
	ImageIcon icon_underline = new ImageIcon("img/underline1_24.png");

	GridBagConstraints gbc = new GridBagConstraints();
	
	GuiListener guiListener;
	
	/**
	 * Build user interface
	 */
	public void initInterface() {
		guiListener = new GuiListener(this);
		
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new JMenuBar();
		file = new JMenu("File");
		options = new JMenu("Options");
		help = new JMenu("Help");

		open = new JMenuItem("Open");
		open.setActionCommand("menu_open");
		open.addActionListener(guiListener);
		save = new JMenuItem("Save");
		save.setActionCommand("menu_save");
		save.addActionListener(guiListener);
		exit = new JMenuItem("Exit");
		exit.setActionCommand("menu_exit");
		exit.addActionListener(guiListener);
		about = new JMenuItem("About");
		about.setActionCommand("menu_about");
		about.addActionListener(guiListener);

		menu.add(file);
		menu.add(options);
		menu.add(help);

		file.add(open);
		file.add(save);
		file.add(exit);
		help.add(about);

		frame.add(menu, BorderLayout.NORTH);

		// ////
		// panels
		// ////
		panel_effects = new JPanel(new GridBagLayout());
		panel_effects.setPreferredSize(new Dimension(600, 100));
		panel_effects.setBorder(BorderFactory.createTitledBorder("Video Effects"));

		panel_settings = new JPanel(new GridBagLayout());
		panel_settings.setPreferredSize(new Dimension(200, 500));
		panel_settings.setBorder(BorderFactory
				.createTitledBorder("Text Styles"));
		panel_settings.setSize(400, 400);

		setting_timeline = new JPanel(new GridBagLayout());
		setting_timeline.setPreferredSize(new Dimension(600, 100));
		setting_timeline
				.setBorder(BorderFactory.createTitledBorder("Timeline"));

		start_panel = new JPanel(new GridBagLayout());
		start_panel.setPreferredSize(new Dimension(600, 50));
		start_panel.setBorder(BorderFactory.createTitledBorder("Start/Stop"));

		vid_panel = new VideoPlayer();

		panel_video_big = new JPanel();
		panel_video_big.setLayout(new BoxLayout(panel_video_big, BoxLayout.PAGE_AXIS));
		panel_video_big.add(panel_effects);
		panel_video_big.add(vid_panel.frame);
		panel_video_big.add(start_panel);

		frame.add(panel_video_big, BorderLayout.CENTER);
		frame.add(panel_settings, BorderLayout.EAST);
		frame.add(setting_timeline, BorderLayout.PAGE_END);

		frame.setSize(800, 600);

		// ////
		// buttons video preview
		// ////
		nav_back = new JButton(icon_backward);
		nav_back.setOpaque(false);
		nav_back.setFocusPainted(false);
		nav_back.setBorderPainted(false);
		nav_back.setContentAreaFilled(false);
		nav_back.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
																		// important
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(nav_back, gbc);

		nav_start = new JButton(icon_play); // startimage because video will not play 
		nav_start.setPreferredSize(new Dimension(30, 30));
		nav_start.setOpaque(false);
		nav_start.setFocusPainted(false);
		nav_start.setBorderPainted(false);
		nav_start.setContentAreaFilled(false);
		nav_start.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially important
		nav_start.setActionCommand("player_start");
		nav_start.addActionListener(guiListener);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(nav_start, gbc);

		nav_stop = new JButton(icon_stop);
		nav_stop.setPreferredSize(new Dimension(30, 30));
		nav_stop.setOpaque(false);
		nav_stop.setFocusPainted(false);
		nav_stop.setBorderPainted(false);
		nav_stop.setContentAreaFilled(false);
		nav_stop.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially important
		nav_stop.setActionCommand("player_stop");
		nav_stop.addActionListener(guiListener);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(nav_stop, gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;

		nav_forw = new JButton(icon_forward);
		nav_forw.setPreferredSize(new Dimension(30, 30));
		nav_forw.setOpaque(false);
		nav_forw.setFocusPainted(false);
		nav_forw.setBorderPainted(false);
		nav_forw.setContentAreaFilled(false);
		nav_forw.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
																		// important
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(nav_forw, gbc);

		nav_volume = new JButton(icon_vol);
		nav_volume.setPreferredSize(new Dimension(30, 30));
		nav_volume.setOpaque(false);
		nav_volume.setFocusPainted(false);
		nav_volume.setBorderPainted(false);
		nav_volume.setContentAreaFilled(false);
		nav_volume.setBorder(BorderFactory.createEmptyBorder(2, 30, 10, 2)); // Especially
																			// important
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(nav_volume, gbc);

		// /////
		// buttons text effects
		// ////
		text_left = new JButton(icon_leftbound);
		// left.setPreferredSize(new Dimension(30,30));
		text_left.setOpaque(false);
		text_left.setFocusPainted(false);
		text_left.setBorderPainted(false);
		text_left.setContentAreaFilled(false);
		text_left.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
																		// important
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(text_left, gbc);

		text_cent = new JButton(icon_center);
		// left.setPreferredSize(new Dimension(30,30));
		text_cent.setOpaque(false);
		text_cent.setFocusPainted(false);
		text_cent.setBorderPainted(false);
		text_cent.setContentAreaFilled(false);
		text_cent.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
																		// important
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(text_cent, gbc);

		text_right = new JButton(icon_rightbound);
		// left.setPreferredSize(new Dimension(30,30));
		text_right.setOpaque(false);
		text_right.setFocusPainted(false);
		text_right.setBorderPainted(false);
		text_right.setContentAreaFilled(false);
		text_right.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
																		// important
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(text_right, gbc);

		text_ital = new JButton(icon_italic);
		text_ital.setOpaque(false);
		text_ital.setFocusPainted(false);
		text_ital.setBorderPainted(false);
		text_ital.setContentAreaFilled(false);
		text_ital.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1)); // Especially
																		// important
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(text_ital, gbc);

		text_bol = new JButton(icon_bold);
		// left.setPreferredSize(new Dimension(30,30));
		text_bol.setOpaque(false);
		text_bol.setFocusPainted(false);
		text_bol.setBorderPainted(false);
		text_bol.setContentAreaFilled(false);
		text_bol.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
																	// important
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(text_bol, gbc);

		text_under = new JButton(icon_underline);
		// left.setPreferredSize(new Dimension(30,30));
		text_under.setOpaque(false);
		text_under.setFocusPainted(false);
		text_under.setBorderPainted(false);
		text_under.setContentAreaFilled(false);
		text_under.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
																		// important
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(text_under, gbc);

		// frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

	}
	
	public VideoPlayer getVideo() { return vid_panel; }
	public JFrame getFrame() { return frame; }
	
	public void setPaused() {
		this.nav_start.setIcon(icon_play);
	}
	
	public void setPlaying() {
		this.nav_start.setIcon(icon_paused);
	}
}
