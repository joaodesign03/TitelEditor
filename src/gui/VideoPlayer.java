package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.ShortBuffer;

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
<<<<<<< HEAD
	private static SourceDataLine mLine; // audio
	
	// source file 
	private String sourceFile;
	
	// state const and var
	private int status;
	public static final int STATUS_PLAY = 0;
	public static final int STATUS_PAUSE = 1;
	public static final int STATUS_STOP = 2;
	public static final int STATUS_LOADED = 3;
=======
	private Integer videoWidth = 590;
	private Integer videoHeight = 490;
	//private IVideoResampler videoResampler = null;
>>>>>>> changes

	public VideoPlayer() {
		frame = new MyVideoFrame();
		frame.setLayout(new GridBagLayout());
		frame.setSize(new Dimension(600, 400));
		frame.setPreferredSize(new Dimension(600, 400));
		frame.setBorder(BorderFactory.createTitledBorder("Video Preview"));
		frame.setVisible(true);
	}

	public VideoPlayer(String filename) {
<<<<<<< HEAD
		this.load(filename);
	}

	private IMediaReader reader;
	private MediaListenerAdapter adapter;

	public void load(String sourceUrl) {
	
		this.setSourceFile(sourceUrl);
		
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
				if(status == VideoPlayer.STATUS_LOADED) status = VideoPlayer.STATUS_STOP;
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
		
		this.setStatus(VideoPlayer.STATUS_LOADED);
		
		this.playLoop();
	}
	
	private void reload() {
		System.out.println("reload: "+this.getSourceFile());
		if(this.getSourceFile() != null){
			this.reader.close();
			this.reader = null;
			this.load(this.getSourceFile());
		}
	}
	
	public void playLoop() {
		if(reader != null){
			while(true) {
				if(this.getStatus() == VideoPlayer.STATUS_PLAY || this.getStatus() == VideoPlayer.STATUS_LOADED) {
					if(reader.readPacket() != null) break;
				} else {
					try{
						Thread.sleep(0);
					} catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public boolean pause() {
		if(this.getStatus() == VideoPlayer.STATUS_PAUSE || this.getStatus() == VideoPlayer.STATUS_STOP){
//			System.out.println("start");
			this.setStatus(VideoPlayer.STATUS_PLAY);
			return true;
		} else if(this.getStatus() == VideoPlayer.STATUS_PLAY){
//			System.out.println("pause");
			this.setStatus(VideoPlayer.STATUS_PAUSE);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean stop() {
		System.out.println("stop");
		if(this.getStatus() == VideoPlayer.STATUS_PLAY && this.getStatus() == VideoPlayer.STATUS_PAUSE) {
			this.reload();
			return true;
		}
		return false;
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
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
=======
		this.play(filename);
	}

	/*
	 * public static void main(String[] args) { String sourceUrl =
	 * "/Users/johann/Movies/Falling.Skies.S02E01.HDTV.x264-ASAP.mp4";
	 * 
	 * VideoPlayer videoPlayer = new VideoPlayer(); videoPlayer.play(sourceUrl);
	 * }
	 */

	
	public void play(String sourceUrl) {
		IMediaReader reader = ToolFactory.makeReader(sourceUrl);
		reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		// final MyVideoFrame frame = new MyVideoFrame();
		// frame.setSize(new Dimension(600, 500));
		// frame.setBorder(BorderFactory.createTitledBorder("Video Preview"));

		MediaListenerAdapter adapter = new MediaListenerAdapter() {
			@Override
			public void onVideoPicture(IVideoPictureEvent event) {
				frame.setImage((BufferedImage) event.getImage());
			}


		};
		reader.addListener(adapter);
		frame.setVisible(true);

		while (reader.readPacket() == null)
			do {
			} while (false);
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
				g.drawImage(image, (getWidth() - videoWidth)/2, (getHeight() - videoHeight)/2, videoWidth, videoHeight, this);
				//g.drawImage(image, 0, 0, null);
			}
		}
		
	}
>>>>>>> changes
}