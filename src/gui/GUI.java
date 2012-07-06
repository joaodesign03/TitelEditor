package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main class for all GUI elements
 * @author Johann Houszka 0625523
 *
 */
public class GUI extends JFrame {
	
	/**
	 * random serial number
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiate user interface variables
	 */
	JFrame frame = new JFrame("Titeleditor");
	JPanel panel_video_big, panel_effects, panel_video, panel_settings, setting_timeline;
	
	

	/**
	 * Build user interface
	 */
	public void initInterface() {
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel_effects = new JPanel(new GridBagLayout());
		panel_effects.setBackground(new Color(255, 0, 0));
		panel_effects.setBorder(BorderFactory.createTitledBorder("Video Effects"));
		
		panel_video = new JPanel(new GridBagLayout());
		panel_video.setBorder(BorderFactory.createTitledBorder("Video Preview"));
		
		panel_settings = new JPanel(new GridBagLayout());
		panel_settings.setBorder(BorderFactory.createTitledBorder("Text Styles"));
		panel_settings.setSize(400, 400);
		
		setting_timeline = new JPanel(new GridBagLayout());
		setting_timeline.setBorder(BorderFactory.createTitledBorder("Timeline"));
		
		panel_video_big = new JPanel();
		panel_video_big.setLayout(new BoxLayout(panel_video_big, BoxLayout.PAGE_AXIS));
		panel_video_big.add(panel_effects);
		panel_video_big.add(panel_video);
		
		frame.add(panel_video_big,BorderLayout.CENTER);
		frame.add(panel_settings,BorderLayout.EAST);
		frame.add(setting_timeline, BorderLayout.PAGE_END);
		
		frame.setSize(800, 600);
		
		//frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
}
