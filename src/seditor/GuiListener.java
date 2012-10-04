package seditor;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class GuiListener implements ActionListener{
	Gui gui;
	String filename;
	
	public GuiListener(Gui gui){
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent object) {
		// close application
		if (object.getActionCommand() == "menu_exit") {
			System.exit(0);
		}
		if (object.getActionCommand() == "menu_open") {
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
			if (chooser.showOpenDialog(this.gui.getFrame()) == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUI: Datei " + chooser.getSelectedFile()
						+ " ausgewählt.");
				filename = chooser.getSelectedFile().getPath();

				Thread playThread = new Thread() {
					public void run() {
						gui.getVideo().load(filename);
					}
				};
				
				playThread.start();

			} // end if.approved

		} 
		if(object.getActionCommand() == "player_start"){
			if(this.gui.getVideo().pause()){
				if(this.gui.getVideo().getStatus() == VideoPlayer.STATUS_PAUSE) this.gui.setPaused();
				if(this.gui.getVideo().getStatus() == VideoPlayer.STATUS_PLAY) this.gui.setPlaying();
			}
		}
		if(object.getActionCommand() == "player_stop"){
			if(this.gui.getVideo().stop()) {
				this.gui.setPaused();
			}
		}

	}// end action performed
}
