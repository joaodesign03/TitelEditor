package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import bl.VideoPreview;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.demos.VideoImage;
<<<<<<< HEAD
import gui.VideoPlayer;
=======
>>>>>>> changes

/**
 * Main class for all GUI elements
 * 
 * @author Johann Houszka 0625523
 * 
 */
public class GUI extends JFrame implements ActionListener {

	/**
	 * random serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiate user interface variables
	 */
	gui.VideoPlayer vid_panel;
	JFrame frame = new JFrame("Titeleditor");
	JPanel panel_video_big, panel_effects, panel_video, panel_settings,
			setting_timeline, start_panel;
	JMenuBar menu;
	JMenu file, options, help;
	JMenuItem open, save, exit, about;
<<<<<<< HEAD
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

	private String filename;
=======
	JButton start, stopp, pause, back, forw, volume;
	JButton cent, right, left, ital, bol, under;

	// image icons for video preview
	ImageIcon play = new ImageIcon("img/play_24.png");
	ImageIcon stop = new ImageIcon("img/stop_24.png");
	ImageIcon paused = new ImageIcon("img/pause_24.png");
	ImageIcon backward = new ImageIcon("img/backward_24.png");
	ImageIcon forward = new ImageIcon("img/forward_24.png");
	ImageIcon vol = new ImageIcon("img/volume_24.png");

	// image icons for text effects
	ImageIcon center = new ImageIcon("img/center_24.png");
	ImageIcon rightbound = new ImageIcon("img/rightbound_24.png");
	ImageIcon leftbound = new ImageIcon("img/leftbound_24.png");
	ImageIcon italic = new ImageIcon("img/italic1_24.png");
	ImageIcon bold = new ImageIcon("img/bold1_24.png");
	ImageIcon underline = new ImageIcon("img/underline1_24.png");

	GridBagConstraints gbc = new GridBagConstraints();

	//private VideoPreview vid = new VideoPreview();
	//private DecodeAndPlayAudioAndVideo p = null;
	//private VideoImage ImgComp = new VideoImage();
>>>>>>> changes

	/**
	 * Build user interface
	 */
	public void initInterface() {
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new JMenuBar();
		file = new JMenu("File");
		options = new JMenu("Options");
		help = new JMenu("Help");

		open = new JMenuItem("Open");
		open.addActionListener(this);
		save = new JMenuItem("Save");
		save.addActionListener(this);
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		about = new JMenuItem("About");
		about.addActionListener(this);

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
		panel_effects.setBorder(BorderFactory
				.createTitledBorder("Video Effects"));

		// panel_video = new JPanel(new GridBagLayout());
		// panel_video.setPreferredSize(new Dimension(600, 400));
		// panel_video.setBorder(BorderFactory.createTitledBorder("Video Preview"));

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

		vid_panel = new gui.VideoPlayer();
<<<<<<< HEAD
		// DecodeAndPlayAudioAndVideo p = new DecodeAndPlayAudioAndVideo();
		// VideoImage im = p.mScreen;
		// System.out.println(vid_panel.frame.getSize());
		// System.out.println(vid_panel.frame.getBorder());

		panel_video_big = new JPanel();
		// panel_video_big.setPreferredSize(new Dimension(600, 400));
		panel_video_big.setLayout(new BoxLayout(panel_video_big,
				BoxLayout.PAGE_AXIS));
		panel_video_big.add(panel_effects);
		// panel_video_big.add(panel_video);
		panel_video_big.add(vid_panel.frame);
		// panel_video_big.add(im);
=======

		
		panel_video_big = new JPanel();
		// panel_video_big.setPreferredSize(new Dimension(600, 400));
		panel_video_big.setLayout(new BoxLayout(panel_video_big,BoxLayout.PAGE_AXIS));
		panel_video_big.add(panel_effects);
		panel_video_big.add(vid_panel.frame);
>>>>>>> changes
		panel_video_big.add(start_panel);

		frame.add(panel_video_big, BorderLayout.CENTER);
		frame.add(panel_settings, BorderLayout.EAST);
		frame.add(setting_timeline, BorderLayout.PAGE_END);

		frame.setSize(800, 600);

		// ////
		// buttons video preview
		// ////
<<<<<<< HEAD
		nav_back = new JButton(icon_backward);
		nav_back.setOpaque(false);
		nav_back.setFocusPainted(false);
		nav_back.setBorderPainted(false);
		nav_back.setContentAreaFilled(false);
		nav_back.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
=======
		back = new JButton(backward);
		back.setOpaque(false);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
>>>>>>> changes
																		// important
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		start_panel.add(nav_back, gbc);

		nav_start = new JButton(icon_play); // startimage because video will not play 
		nav_start.setPreferredSize(new Dimension(30, 30));
		nav_start.setOpaque(false);
		nav_start.setFocusPainted(false);
		nav_start.setBorderPainted(false);
		nav_start.setContentAreaFilled(false);
		nav_start.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially important
		nav_start.addActionListener(this);
		
=======
		start_panel.add(back, gbc);

		start = new JButton(play);
		start.setPreferredSize(new Dimension(30, 30));
		start.setOpaque(false);
		start.setFocusPainted(false);
		start.setBorderPainted(false);
		start.setContentAreaFilled(false);
		start.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
																		// important
>>>>>>> changes
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		start_panel.add(nav_start, gbc);

		nav_stop = new JButton(icon_stop);
		nav_stop.setPreferredSize(new Dimension(30, 30));
		nav_stop.setOpaque(false);
		nav_stop.setFocusPainted(false);
		nav_stop.setBorderPainted(false);
		nav_stop.setContentAreaFilled(false);
		nav_stop.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially important
		nav_stop.addActionListener(this);

=======
		start_panel.add(start, gbc);

		stopp = new JButton(stop);
		stopp.setPreferredSize(new Dimension(30, 30));
		stopp.setOpaque(false);
		stopp.setFocusPainted(false);
		stopp.setBorderPainted(false);
		stopp.setContentAreaFilled(false);
		stopp.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
																		// important
>>>>>>> changes
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		start_panel.add(nav_stop, gbc);

//		pause = new JButton(paused);
//		pause.setPreferredSize(new Dimension(30, 30));
//		pause.setOpaque(false);
//		pause.setFocusPainted(false);
//		pause.setBorderPainted(false);
//		pause.setContentAreaFilled(false);
//		pause.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially import
//		pause.addActionListener(this);
		
=======
		start_panel.add(stopp, gbc);

		pause = new JButton(paused);
		pause.setPreferredSize(new Dimension(30, 30));
		pause.setOpaque(false);
		pause.setFocusPainted(false);
		pause.setBorderPainted(false);
		pause.setContentAreaFilled(false);
		pause.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
																		// important
>>>>>>> changes
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
//		start_panel.add(pause, gbc);

		nav_forw = new JButton(icon_forward);
		nav_forw.setPreferredSize(new Dimension(30, 30));
		nav_forw.setOpaque(false);
		nav_forw.setFocusPainted(false);
		nav_forw.setBorderPainted(false);
		nav_forw.setContentAreaFilled(false);
		nav_forw.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
=======
		start_panel.add(pause, gbc);

		forw = new JButton(forward);
		forw.setPreferredSize(new Dimension(30, 30));
		forw.setOpaque(false);
		forw.setFocusPainted(false);
		forw.setBorderPainted(false);
		forw.setContentAreaFilled(false);
		forw.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2)); // Especially
>>>>>>> changes
																		// important
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		start_panel.add(nav_forw, gbc);

		nav_volume = new JButton(icon_vol);
		nav_volume.setPreferredSize(new Dimension(30, 30));
		nav_volume.setOpaque(false);
		nav_volume.setFocusPainted(false);
		nav_volume.setBorderPainted(false);
		nav_volume.setContentAreaFilled(false);
		nav_volume.setBorder(BorderFactory.createEmptyBorder(2, 30, 10, 2)); // Especially
=======
		start_panel.add(forw, gbc);

		volume = new JButton(vol);
		volume.setPreferredSize(new Dimension(30, 30));
		volume.setOpaque(false);
		volume.setFocusPainted(false);
		volume.setBorderPainted(false);
		volume.setContentAreaFilled(false);
		volume.setBorder(BorderFactory.createEmptyBorder(2, 30, 10, 2)); // Especially
>>>>>>> changes
																			// important
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		start_panel.add(nav_volume, gbc);
=======
		start_panel.add(volume, gbc);
>>>>>>> changes

		// /////
		// buttons text effects
		// ////
<<<<<<< HEAD
		text_left = new JButton(icon_leftbound);
		// left.setPreferredSize(new Dimension(30,30));
		text_left.setOpaque(false);
		text_left.setFocusPainted(false);
		text_left.setBorderPainted(false);
		text_left.setContentAreaFilled(false);
		text_left.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
=======
		left = new JButton(leftbound);
		// left.setPreferredSize(new Dimension(30,30));
		left.setOpaque(false);
		left.setFocusPainted(false);
		left.setBorderPainted(false);
		left.setContentAreaFilled(false);
		left.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
>>>>>>> changes
																		// important
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		panel_settings.add(text_left, gbc);

		text_cent = new JButton(icon_center);
		// left.setPreferredSize(new Dimension(30,30));
		text_cent.setOpaque(false);
		text_cent.setFocusPainted(false);
		text_cent.setBorderPainted(false);
		text_cent.setContentAreaFilled(false);
		text_cent.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
=======
		panel_settings.add(left, gbc);

		cent = new JButton(center);
		// left.setPreferredSize(new Dimension(30,30));
		cent.setOpaque(false);
		cent.setFocusPainted(false);
		cent.setBorderPainted(false);
		cent.setContentAreaFilled(false);
		cent.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
>>>>>>> changes
																		// important
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		panel_settings.add(text_cent, gbc);

		text_right = new JButton(icon_rightbound);
		// left.setPreferredSize(new Dimension(30,30));
		text_right.setOpaque(false);
		text_right.setFocusPainted(false);
		text_right.setBorderPainted(false);
		text_right.setContentAreaFilled(false);
		text_right.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
=======
		panel_settings.add(cent, gbc);

		right = new JButton(rightbound);
		// left.setPreferredSize(new Dimension(30,30));
		right.setOpaque(false);
		right.setFocusPainted(false);
		right.setBorderPainted(false);
		right.setContentAreaFilled(false);
		right.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
>>>>>>> changes
																		// important
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		panel_settings.add(text_right, gbc);

		text_ital = new JButton(icon_italic);
		// left.setPreferredSize(new Dimension(30,30));
		text_ital.setOpaque(false);
		text_ital.setFocusPainted(false);
		text_ital.setBorderPainted(false);
		text_ital.setContentAreaFilled(false);
		text_ital.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1)); // Especially
=======
		panel_settings.add(right, gbc);

		ital = new JButton(italic);
		// left.setPreferredSize(new Dimension(30,30));
		ital.setOpaque(false);
		ital.setFocusPainted(false);
		ital.setBorderPainted(false);
		ital.setContentAreaFilled(false);
		ital.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1)); // Especially
>>>>>>> changes
																		// important
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		panel_settings.add(text_ital, gbc);

		text_bol = new JButton(icon_bold);
		// left.setPreferredSize(new Dimension(30,30));
		text_bol.setOpaque(false);
		text_bol.setFocusPainted(false);
		text_bol.setBorderPainted(false);
		text_bol.setContentAreaFilled(false);
		text_bol.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
=======
		panel_settings.add(ital, gbc);

		bol = new JButton(bold);
		// left.setPreferredSize(new Dimension(30,30));
		bol.setOpaque(false);
		bol.setFocusPainted(false);
		bol.setBorderPainted(false);
		bol.setContentAreaFilled(false);
		bol.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
>>>>>>> changes
																	// important
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		panel_settings.add(text_bol, gbc);

		text_under = new JButton(icon_underline);
		// left.setPreferredSize(new Dimension(30,30));
		text_under.setOpaque(false);
		text_under.setFocusPainted(false);
		text_under.setBorderPainted(false);
		text_under.setContentAreaFilled(false);
		text_under.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
=======
		panel_settings.add(bol, gbc);

		under = new JButton(underline);
		// left.setPreferredSize(new Dimension(30,30));
		under.setOpaque(false);
		under.setFocusPainted(false);
		under.setBorderPainted(false);
		under.setContentAreaFilled(false);
		under.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Especially
>>>>>>> changes
																		// important
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
<<<<<<< HEAD
		panel_settings.add(text_under, gbc);

		// frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

	}

=======
		panel_settings.add(under, gbc);

		//frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

		//p = new DecodeAndPlayAudioAndVideo(this.ImgComp);
		//p.init();
		
	}

	GUI gui = this;
	String filename;
>>>>>>> changes
	public void actionPerformed(ActionEvent object) {
		// close application
		if (object.getSource() == exit) {
			System.exit(0);
		}
<<<<<<< HEAD
		if (object.getSource() == open) {
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new FileFilter() {
				public boolean accept(File f) {
					if (f.isDirectory())
						return true;
					return f.getName().toLowerCase().endsWith(".mp4");
				}

				public String getDescription() {
					return "Video Files";
				}
			});
			chooser.setMultiSelectionEnabled(false);
			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUI: Datei " + chooser.getSelectedFile()
						+ " ausgew�hlt.");
				filename = chooser.getSelectedFile().getPath();

				Thread playThread = new Thread() {
					public void run() {
						vid_panel.load(filename);
					}
				};
				playThread.start();

				// videoPlayer.play(filename);
				// this.play(filename);

			} // end if.approved

		} 
		if(object.getSource() == nav_start){
			if(vid_panel.pause()){
				if(vid_panel.getStatus() == VideoPlayer.STATUS_PAUSE) this.nav_start.setIcon(icon_play);
				if(vid_panel.getStatus() == VideoPlayer.STATUS_PLAY) this.nav_start.setIcon(icon_paused);
			}
		}
		if(object.getSource() == nav_stop){
			
			vid_panel.stop();
		}
		System.out.println("button: "+object.getSource().toString());
		// end actionperformend-open

	}// end action performed

=======
		if (object.getSource() == open){
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new FileFilter() {
			    public boolean accept(File f) {
			      if (f.isDirectory()) return true;
			      return f.getName().toLowerCase().endsWith(".mp4");
			    }
			    public String getDescription () { return "Video Files"; }  
			  });
			  chooser.setMultiSelectionEnabled(false);
			  if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				System.out.println ("Datei "+chooser.getSelectedFile()+" ausgew�hlt.");
				filename = chooser.getSelectedFile().toString();
				//p.getmScreen().setImage(aImage);
				Thread thread = new Thread(){
				    public void run(){
				      vid_panel.play(filename);  
				    }
				  };
				thread.start();
					//vid_panel.play(filename);
				
				
			    //videoPlayer.play(filename);
		     	//this.play(filename);
				 
				
			  } //end if.approved

		} //end if-open
	
	
	}//end action performed
	
	
>>>>>>> changes
	/**
	 * The window we'll draw the video on.
	 * 
	 */
	private static VideoImage mScreen = null;

	public static void updateJavaWindow(BufferedImage javaImage) {
		mScreen.setImage(javaImage);
	}

	/**
	 * Opens a Swing window on screen.
	 */
	public static void openJavaWindow() {
		mScreen = new VideoImage();
	}

	/**
	 * Forces the swing thread to terminate; I'm sure there is a right way to do
	 * this in swing, but this works too.
	 */
	public static void closeJavaWindow() {
		System.exit(0);
	}

	public void play(String sourceUrl) {
		IMediaReader reader = ToolFactory.makeReader(sourceUrl);
		reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

		reader.addListener(ToolFactory.makeViewer(true));

		while (reader.readPacket() == null)
			do {
			} while (false);
	}

}// end class

