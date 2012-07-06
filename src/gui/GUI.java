package gui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
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
	JPanel panel1,panel2,panel3,panel4;
	
	

	/**
	 * Build user interface
	 */
	public void initInterface() {
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1 = new JPanel(new GridBagLayout());
		panel1.setBorder(BorderFactory.createTitledBorder("Video Effects"));
		panel2 = new JPanel(new GridBagLayout());
		panel2.setBorder(BorderFactory.createTitledBorder("Video Preview"));
		panel3 = new JPanel(new GridBagLayout());
		panel3.setBorder(BorderFactory.createTitledBorder("Text Styles"));
		panel4 = new JPanel(new GridBagLayout());
		panel4.setBorder(BorderFactory.createTitledBorder("Timeline"));
		
		
		
		
		frame.add(panel1,BorderLayout.PAGE_START);
		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel3,BorderLayout.EAST);
		frame.add(panel4, BorderLayout.PAGE_END);
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		
	}
}
