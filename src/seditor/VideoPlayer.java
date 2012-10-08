package seditor;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.Utils;

public class VideoPlayer {
	public VideoFrame frame;
	public StaticImageMediaTool imageMediaTool;
	private static SourceDataLine mLine; // audio
	private IStreamCoder audioCoder = null;
	private IStreamCoder videoCoder = null;
	private IVideoPicture image = null;

	private static long mFirstVideoTimestampInStream = Global.NO_PTS;
	private static long mSystemVideoClockStartTime = 0;

	// source file
	private String sourceFile;

	// state const and var
	private int status;
	public static final int STATUS_PLAY = 0;
	public static final int STATUS_PAUSE = 1;
	public static final int STATUS_STOP = 2;
	public static final int STATUS_LOADED = 3;
	public static final int STATUS_RELOADING = 4;

	private int videoStreamID = -1;
	private int audioStreamID = -1;

	public VideoPlayer() {
		frame = new VideoFrame();
		frame.setLayout(new GridBagLayout());
		// frame.setSize(new Dimension(600, 400));
		frame.setPreferredSize(new Dimension(600, 400));
		// frame.setBorder(BorderFactory.createTitledBorder("Video Preview"));
		// frame.setVisible(true);
	}

	public VideoPlayer(String filename) {
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

		reader.open();

		for (int i = 0; i < reader.getContainer().getNumStreams(); i++) {
			IStream stream = reader.getContainer().getStream(i);
			IStreamCoder coder = stream.getStreamCoder();

			if (videoStreamID == -1
					&& coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				videoStreamID = i;
				videoCoder = coder;
			} else if (audioStreamID == -1
					&& coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				audioStreamID = i;
				audioCoder = coder;
			}
		}
		try {
			openJavaSound(audioCoder);
		} catch (LineUnavailableException ex) {
			System.out.println("No audio line for this video.");
		}

		adapter = new MediaListenerAdapter() {
			@Override
			public void onVideoPicture(IVideoPictureEvent event) {
				if (status == VideoPlayer.STATUS_LOADED)
					status = VideoPlayer.STATUS_STOP;
				image = event.getPicture();
				long delay = millisecondsUntilTimeToDisplay(image);
				// if there is no audio stream; go ahead and hold up the
				// main thread. We'll end
				// up caching fewer video pictures in memory that way.
				try {
					System.out.println("DELAY: " + delay);
					if (delay > 0) {
						Thread.sleep(delay);
						System.out.println("DELAY DELAY DELAY");
					}
				} catch (InterruptedException e) {
					return;
				}
				// frame.setImage(event.getImage());
				frame.setImage(Utils.videoPictureToImage(image));
			}

			@Override
			public void onAudioSamples(IAudioSamplesEvent event) {
				playJavaSound(event.getAudioSamples());
			}
		};
		reader.addListener(adapter);

		this.setStatus(VideoPlayer.STATUS_LOADED);
		this.playLoop();
	}

	private boolean reload() {
		System.out.println("reload: " + this.getSourceFile());
		this.setStatus(VideoPlayer.STATUS_RELOADING);

		// wait
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.reader.close();
		this.reader.open();
		this.setStatus(VideoPlayer.STATUS_LOADED);
		return true;
	}

	public void playLoop() {
		while (true) {
			if (this.getStatus() != VideoPlayer.STATUS_RELOADING) {
				if (this.getStatus() == VideoPlayer.STATUS_PLAY
						|| this.getStatus() == VideoPlayer.STATUS_LOADED) {
					if (this.getStatus() != VideoPlayer.STATUS_RELOADING
							&& reader.readPacket() != null)
						break;
				} else {
					try {
						Thread.sleep(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} // else reader will be refreshed
		}
	}

	public boolean pause() {
		if (this.getStatus() == VideoPlayer.STATUS_PAUSE
				|| this.getStatus() == VideoPlayer.STATUS_STOP) {
			// System.out.println("start");
			this.setStatus(VideoPlayer.STATUS_PLAY);
			return true;
		} else if (this.getStatus() == VideoPlayer.STATUS_PLAY) {
			// System.out.println("pause");
			this.setStatus(VideoPlayer.STATUS_PAUSE);
			return true;
		} else {
			return false;
		}
	}

	public boolean stop() {
		System.out.println("stop");
		if (this.getStatus() == VideoPlayer.STATUS_PLAY
				|| this.getStatus() == VideoPlayer.STATUS_PAUSE) {
			return this.reload();
			// System.out.println(String.format("Trying to reset video play to %d",
			// this.reader.getContainer().getStream(videoStreamID).getStartTime()));
			// this.reader.
			// if(this.reader.getContainer().seekKeyFrame(videoStreamID,
			// this.reader.getContainer().getStream(videoStreamID).getStartTime(),
			// IContainer.SEEK_FLAG_ANY | IContainer.SEEK_FLAG_FRAME) < 0) {
			// System.out.println(String.format("Could not reset audio play position to %d",
			// this.reader.getContainer().getStartTime()));
			// }
			// return true;
		}
		return false;
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

	/**
	 * 
	 * Open Java SoundLine for playing the audio
	 * 
	 * @param aAudioCoder
	 * @throws LineUnavailableException
	 */
	private static void openJavaSound(IStreamCoder aAudioCoder)
			throws LineUnavailableException {
		AudioFormat audioFormat = new AudioFormat(aAudioCoder.getSampleRate(),
				(int) IAudioSamples.findSampleBitDepth(aAudioCoder
						.getSampleFormat()), aAudioCoder.getChannels(), true,
				false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		mLine = (SourceDataLine) AudioSystem.getLine(info);
		/**
		 * if that succeeded, try opening the line.
		 */
		mLine.open(audioFormat);
		/**
		 * And if that succeed, start the line.
		 */
		mLine.start();
	}

	/**
	 * 
	 * Play audio stream from video file
	 * 
	 * @param aSamples
	 */
	private static void playJavaSound(IAudioSamples aSamples) {
		/**
		 * We're just going to dump all the samples into the line.
		 */
		byte[] rawBytes = aSamples.getData()
				.getByteArray(0, aSamples.getSize());
		mLine.write(rawBytes, 0, aSamples.getSize());
	}

	/**
	 * Close Java Sound
	 */
	private static void closeJavaSound() {
		if (mLine != null) {
			/*
			 * Wait for the line to finish playing
			 */
			mLine.drain();
			/*
			 * Close the line.
			 */
			mLine.close();
			mLine = null;
		}
	}

	/**
	 * 
	 * Synchronizing Video and Audio
	 * 
	 * @param picture
	 * @return
	 */
	private static long millisecondsUntilTimeToDisplay(IVideoPicture picture) {
		long millisecondsToSleep = 0;

		if (mFirstVideoTimestampInStream == Global.NO_PTS) {
			// This is our first time through
			mFirstVideoTimestampInStream = picture.getTimeStamp();
			// get the starting clock time so we can hold up frames
			// until the right time.
			mSystemVideoClockStartTime = System.currentTimeMillis();
			millisecondsToSleep = 0;
		} else {
			long systemClockCurrentTime = System.currentTimeMillis();
			long millisecondsClockTimeSinceStartofVideo = systemClockCurrentTime
					- mSystemVideoClockStartTime;
			// compute how long for this frame since the first frame in the
			// stream.
			// remember that IVideoPicture and IAudioSamples timestamps are
			// always in MICROSECONDS,
			// so we divide by 1000 to get milliseconds.
			long millisecondsStreamTimeSinceStartOfVideo = (picture
					.getTimeStamp() - mFirstVideoTimestampInStream) / 1000;
			final long millisecondsTolerance = 50; // and we give ourselfs 50 ms
													// of tolerance
			millisecondsToSleep = (millisecondsStreamTimeSinceStartOfVideo - (millisecondsClockTimeSinceStartofVideo + millisecondsTolerance));
		}
		return millisecondsToSleep;
	}

}