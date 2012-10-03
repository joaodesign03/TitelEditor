package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IStreamCoder;

public class VideoPlayer {
	public MyVideoFrame frame;// = new MyVideoFrame();
	private static SourceDataLine mLine; // audio
	private boolean paused;

	public VideoPlayer() {
		frame = new MyVideoFrame();
		frame.setLayout(new GridBagLayout());
		frame.setSize(new Dimension(600, 400));
		frame.setPreferredSize(new Dimension(600, 400));
		frame.setBorder(BorderFactory.createTitledBorder("Video Preview"));
		frame.setVisible(true);
	}

	public VideoPlayer(String filename) {
		this.play(filename);
	}

	/*
	 * public static void main(String[] args) { String sourceUrl =
	 * "/Users/johann/Movies/Falling.Skies.S02E01.HDTV.x264-ASAP.mp4";
	 * 
	 * VideoPlayer videoPlayer = new VideoPlayer(); videoPlayer.play(sourceUrl);
	 * }
	 */
	private IMediaReader reader;
	private MediaListenerAdapter adapter;

	public void play(String sourceUrl) {
		
		this.setPaused(false);
		
		System.out.println("play:init");
		reader = ToolFactory.makeReader(sourceUrl);
		reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		reader.setAddDynamicStreams(true);
		reader.setQueryMetaData(false);

		// final MyVideoFrame frame = new MyVideoFrame();
		// frame.setSize(new Dimension(600, 500));
		// frame.setBorder(BorderFactory.createTitledBorder("Video Preview"));

		adapter = new MediaListenerAdapter() {
			@Override
			public void onVideoPicture(IVideoPictureEvent event) {
				frame.setImage(event.getImage());
			}

			@Override
			public void onAudioSamples(IAudioSamplesEvent event) {
				event.getAudioSamples();

			}
		};
		reader.addListener(adapter);
		// reader.addListener(ToolFactory.makeViewer(true));
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		while(true) {
			if(!this.isPaused()) {
				if(reader.readPacket() != null) break;
			}
		}
	}

	@SuppressWarnings("serial")
	public class MyVideoFrame extends JPanel {
		Image image;

		public void setImage(final Image image) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					MyVideoFrame.this.image = image;
					repaint();
				}
			});
		}

		@Override
		public synchronized void paint(Graphics g) {
			if (image != null) {
				g.drawImage(image, 0, 0, null);
			}
		}
	}

	
	// Getter Setter

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}