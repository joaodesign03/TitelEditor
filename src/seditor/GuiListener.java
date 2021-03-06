package seditor;


import gui.DecodeAndPlayAudioAndVideo;

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
						+ " ausgew�hlt.");
				filename = chooser.getSelectedFile().getPath();

				Thread playThread = new Thread() {
					public void run() {
						gui.getDecodeVideo().load(filename);
						//gui.getVideo().load(filename);
					}
				};
				
				playThread.start();

			} // end if.approved

		} 
		if(object.getActionCommand() == "player_start"){
			//if(this.gui.getVideo().pause()){
			//	if(this.gui.getVideo().getStatus() == VideoPlayer.STATUS_PAUSE) this.gui.setPaused();
			//	if(this.gui.getVideo().getStatus() == VideoPlayer.STATUS_PLAY) this.gui.setPlaying();
			if (this.gui.getDecodeVideo().pause()) {
				if(this.gui.getDecodeVideo().getStatus() == DecodeAndPlayAudioAndVideo.STATUS_PAUSE) this.gui.setPaused();
				if(this.gui.getDecodeVideo().getStatus() == DecodeAndPlayAudioAndVideo.STATUS_PLAY) this.gui.setPlaying();
			}
		}
		if(object.getActionCommand() == "player_stop"){
			//if (this.gui.getVideo().getSourceFile() == null) {
			if(this.gui.getDecodeVideo().getSourceFile() == null) {
				System.out.println("No video loaded.");
			}
			else {
				/*if(this.gui.getVideo().stop()) {
					this.gui.setPaused();*/
				if(this.gui.getDecodeVideo().stop()) {
					this.gui.setPaused();
				}
			}
			
		}

	}// end action performed
}
