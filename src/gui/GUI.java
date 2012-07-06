package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Main class for all GUI elements
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
	JFrame frame = new JFrame("Titeleditor");
	JPanel panel_video_big, panel_effects, panel_video, panel_settings, setting_timeline, start_panel;
	JMenuBar menu;
	JMenu file, options, help;
	JMenuItem open, save, exit, about;
	JButton start, stopp, pause;
	
	GridBagConstraints gbc = new GridBagConstraints();
	

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
		
		panel_effects = new JPanel(new GridBagLayout());
		//panel_effects.setBackground(new Color(255, 0, 0));
		panel_effects.setPreferredSize(new Dimension(600, 100));
		panel_effects.setBorder(BorderFactory.createTitledBorder("Video Effects"));
		
		panel_video = new JPanel(new GridBagLayout());
		panel_video.setPreferredSize(new Dimension(600, 400));
		panel_video.setBorder(BorderFactory.createTitledBorder("Video Preview"));
		
		panel_settings = new JPanel(new GridBagLayout());
		panel_settings.setPreferredSize(new Dimension(200, 500));
		panel_settings.setBorder(BorderFactory.createTitledBorder("Text Styles"));
		panel_settings.setSize(400, 400);
		
		setting_timeline = new JPanel(new GridBagLayout());
		setting_timeline.setPreferredSize(new Dimension(600, 100));
		setting_timeline.setBorder(BorderFactory.createTitledBorder("Timeline"));
		
		start_panel = new JPanel(new GridBagLayout());
		start_panel.setPreferredSize(new Dimension(600, 50));
		start_panel.setBorder(BorderFactory.createTitledBorder("Start/Stop"));
		
		panel_video_big = new JPanel();
		//panel_video_big.setPreferredSize(new Dimension(600, 400));
		panel_video_big.setLayout(new BoxLayout(panel_video_big, BoxLayout.PAGE_AXIS));
		panel_video_big.add(panel_effects);
		panel_video_big.add(panel_video);
		panel_video_big.add(start_panel);
		
		frame.add(panel_video_big,BorderLayout.CENTER);
		frame.add(panel_settings,BorderLayout.EAST);
		frame.add(setting_timeline, BorderLayout.PAGE_END);
		
		frame.setSize(800, 600);
		
		
		start = new JButton("start");
		start.setPreferredSize(new Dimension(30,30));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(start, gbc);
		
		stopp = new JButton("stopp");
		//start.setPreferredSize(new Dimension(30,30));
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(stopp, gbc);
		
		pause = new JButton("pause");
		//start.setPreferredSize(new Dimension(30,30));
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(pause, gbc);
		
		//frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
	}


	
	public void actionPerformed(ActionEvent object) {
		//close application
		if (object.getSource() == exit){
			System.exit(0);
       }
		
	}
}
