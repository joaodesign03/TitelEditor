package gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import seditor.VideoFrame;
import seditor.VideoPlayer;

import com.xuggle.xuggler.demos.*;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.Utils;

public class DecodeAndPlayAudioAndVideo {

	/**
	 * The audio line we'll output sound to; it'll be the default audio device
	 * on your system if available
	 */
	private static SourceDataLine mLine;

	/**
	 * The window we'll draw the video on.
	 * 
	 */
	public static VideoImage mScreen = null;
	
	public static VideoFrame frame;

	private static long mSystemVideoClockStartTime;

	private static long mFirstVideoTimestampInStream;
	private String filename;
	
	private IContainer container;
	private IPacket packet;
	
	// state const and var
	private int status;
	public static final int STATUS_PLAY = 0;
	public static final int STATUS_PAUSE = 1;
	public static final int STATUS_STOP = 2;
	public static final int STATUS_LOADED = 3;
	public static final int STATUS_RELOADING = 4;

	/**
	 * Takes a media container (file) as the first argument, opens it, plays
	 * audio as quickly as it can, and opens up a Swing window and displays
	 * video frames with <i>roughly</i> the right timing.
	 * 
	 * @param args
	 *            Must contain one string which represents a filename
	 */
	@SuppressWarnings("deprecation")
	//public static void main(String[] args) {
	
	public DecodeAndPlayAudioAndVideo() {
		frame = new VideoFrame();
		frame.setLayout(new GridBagLayout());
		frame.setPreferredSize(new Dimension(600, 400));
	}
	
	public DecodeAndPlayAudioAndVideo(String filename) {
		this.load(filename);
	}
	
	
	public void load(String filename) {

		this.filename = filename;
		// Let's make sure that we can actually convert video pixel formats.
		if (!IVideoResampler
				.isSupported(IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION))
			throw new RuntimeException(
					"you must install the GPL version of Xuggler (with IVideoResampler support) for this demo to work");

		// Create a Xuggler container object
		container = IContainer.make();

		// Open up the container
		if (container.open(filename, IContainer.Type.READ, null) < 0)
			throw new IllegalArgumentException("could not open file: "
					+ filename);

		// query how many streams the call to open found
		int numStreams = container.getNumStreams();

		// and iterate through the streams to find the first audio stream
		int videoStreamId = -1;
		IStreamCoder videoCoder = null;
		int audioStreamId = -1;
		IStreamCoder audioCoder = null;
		for (int i = 0; i < numStreams; i++) {
			// Find the stream object
			IStream stream = container.getStream(i);
			// Get the pre-configured decoder that can decode this stream;
			IStreamCoder coder = stream.getStreamCoder();

			if (videoStreamId == -1
					&& coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				videoStreamId = i;
				videoCoder = coder;
			} else if (audioStreamId == -1
					&& coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				audioStreamId = i;
				audioCoder = coder;
			}
		}
		if (videoStreamId == -1 && audioStreamId == -1)
			throw new RuntimeException(
					"could not find audio or video stream in container: "
							+ filename);

		/*
		 * Check if we have a video stream in this file. If so let's open up our
		 * decoder so it can do work.
		 */
		IVideoResampler resampler = null;
		if (videoCoder != null) {
			if (videoCoder.open() < 0)
				throw new RuntimeException(
						"could not open audio decoder for container: "
								+ filename);

			if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24) {
				// if this stream is not in BGR24, we're going to need to
				// convert it. The VideoResampler does that for us.
				resampler = IVideoResampler.make(videoCoder.getWidth(),
						videoCoder.getHeight(), IPixelFormat.Type.BGR24,
						videoCoder.getWidth(), videoCoder.getHeight(),
						videoCoder.getPixelType());
				if (resampler == null)
					throw new RuntimeException(
							"could not create color space resampler for: "
									+ filename);
			}
			/*
			 * And once we have that, we draw a window on screen
			 */
			//openJavaVideo();
			
			
		}

		if (audioCoder != null) {
			if (audioCoder.open() < 0)
				throw new RuntimeException(
						"could not open audio decoder for container: "
								+ filename);

			/*
			 * And once we have that, we ask the Java Sound System to get itself
			 * ready.
			 */
			try {
				openJavaSound(audioCoder);
			} catch (LineUnavailableException ex) {
				throw new RuntimeException(
						"unable to open sound device on your system when playing back container: "
								+ filename);
			}
		}
		this.setStatus(DecodeAndPlayAudioAndVideo.STATUS_LOADED);
		this.playLoop();
		/*
		 * Now, we start walking through the container looking at each packet.
		 */
		IPacket packet = IPacket.make();
		mFirstVideoTimestampInStream = Global.NO_PTS;
		mSystemVideoClockStartTime = 0;
		while (container.readNextPacket(packet) >= 0) {
			/*
			 * Now we have a packet, let's see if it belongs to our video stream
			 */
			if (packet.getStreamIndex() == videoStreamId) {
				/*
				 * We allocate a new picture to get the data out of Xuggler
				 */
				IVideoPicture picture = IVideoPicture.make(
						videoCoder.getPixelType(), videoCoder.getWidth(),
						videoCoder.getHeight());

				/*
				 * Now, we decode the video, checking for any errors.
				 */
				int bytesDecoded = videoCoder.decodeVideo(picture, packet, 0);
				if (bytesDecoded < 0)
					throw new RuntimeException("got error decoding audio in: "
							+ filename);

				/*
				 * Some decoders will consume data in a packet, but will not be
				 * able to construct a full video picture yet. Therefore you
				 * should always check if you got a complete picture from the
				 * decoder
				 */
				if (picture.isComplete()) {
					IVideoPicture newPic = picture;
					/*
					 * If the resampler is not null, that means we didn't get
					 * the video in BGR24 format and need to convert it into
					 * BGR24 format.
					 */
					if (resampler != null) {
						// we must resample
						newPic = IVideoPicture.make(
								resampler.getOutputPixelFormat(),
								picture.getWidth(), picture.getHeight());
						if (resampler.resample(newPic, picture) < 0)
							throw new RuntimeException(
									"could not resample video from: "
											+ filename);
					}
					if (newPic.getPixelType() != IPixelFormat.Type.BGR24)
						throw new RuntimeException(
								"could not decode video as BGR 24 bit data in: "
										+ filename);

					long delay = millisecondsUntilTimeToDisplay(newPic);
					// if there is no audio stream; go ahead and hold up the
					// main thread. We'll end
					// up caching fewer video pictures in memory that way.
					try {
						if (delay > 0)
							Thread.sleep(delay);
					} catch (InterruptedException e) {
						return;
					}

					// And finally, convert the picture to an image and display
					// it

					if (status == DecodeAndPlayAudioAndVideo.STATUS_LOADED)
						status = DecodeAndPlayAudioAndVideo.STATUS_STOP;
					frame.setImage(Utils.videoPictureToImage(newPic));
					//mScreen.setImage(Utils.videoPictureToImage(newPic));
				}
			} else if (packet.getStreamIndex() == audioStreamId) {
				/*
				 * We allocate a set of samples with the same number of channels
				 * as the coder tells us is in this buffer.
				 * 
				 * We also pass in a buffer size (1024 in our example), although
				 * Xuggler will probably allocate more space than just the 1024
				 * (it's not important why).
				 */
				IAudioSamples samples = IAudioSamples.make(1024,
						audioCoder.getChannels());

				/*
				 * A packet can actually contain multiple sets of samples (or
				 * frames of samples in audio-decoding speak). So, we may need
				 * to call decode audio multiple times at different offsets in
				 * the packet's data. We capture that here.
				 */
				int offset = 0;

				/*
				 * Keep going until we've processed all data
				 */
				while (offset < packet.getSize()) {
					int bytesDecoded = audioCoder.decodeAudio(samples, packet,
							offset);
					if (bytesDecoded < 0)
						throw new RuntimeException(
								"got error decoding audio in: " + filename);
					offset += bytesDecoded;
					/*
					 * Some decoder will consume data in a packet, but will not
					 * be able to construct a full set of samples yet. Therefore
					 * you should always check if you got a complete set of
					 * samples from the decoder
					 */
					if (samples.isComplete()) {
						// note: this call will block if Java's sound buffers
						// fill up, and we're
						// okay with that. That's why we have the video
						// "sleeping" occur
						// on another thread.
						playJavaSound(samples);
					}
				}
			} else {
				/*
				 * This packet isn't part of our video stream, so we just
				 * silently drop it.
				 */
				do {
				} while (false);
			}

		}
		/*
		 * Technically since we're exiting anyway, these will be cleaned up by
		 * the garbage collector... but because we're nice people and want to be
		 * invited places for Christmas, we're going to show how to clean up.
		 */
		if (videoCoder != null) {
			videoCoder.close();
			videoCoder = null;
		}
		if (audioCoder != null) {
			audioCoder.close();
			audioCoder = null;
		}
		if (container != null) {
			container.close();
			container = null;
		}
		closeJavaSound();
		closeJavaVideo();
	}

	/**
	 * Opens a Swing window on screen.
	 */
	private static void openJavaVideo() {
		mScreen = new VideoImage();
	}

	/**
	 * Forces the swing thread to terminate; I'm sure there is a right way to do
	 * this in swing, but this works too.
	 */
	private static void closeJavaVideo() {
		System.exit(0);
	}

	private static void openJavaSound(IStreamCoder aAudioCoder)
			throws LineUnavailableException {
		AudioFormat audioFormat = new AudioFormat(aAudioCoder.getSampleRate(),
				(int) IAudioSamples.findSampleBitDepth(aAudioCoder
						.getSampleFormat()), aAudioCoder.getChannels(), true, /*
																			 * xuggler
																			 * defaults
																			 * to
																			 * signed
																			 * 16
																			 * bit
																			 * samples
																			 */
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

	private static void playJavaSound(IAudioSamples aSamples) {
		/**
		 * We're just going to dump all the samples into the line.
		 */
		byte[] rawBytes = aSamples.getData()
				.getByteArray(0, aSamples.getSize());
		mLine.write(rawBytes, 0, aSamples.getSize());
	}

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

	// }

	private static long millisecondsUntilTimeToDisplay(IVideoPicture picture) {
		/**
		 * We could just display the images as quickly as we decode them, but it
		 * turns out we can decode a lot faster than you think.
		 * 
		 * So instead, the following code does a poor-man's version of trying to
		 * match up the frame-rate requested for each IVideoPicture with the
		 * system clock time on your computer.
		 * 
		 * Remember that all Xuggler IAudioSamples and IVideoPicture objects
		 * always give timestamps in Microseconds, relative to the first decoded
		 * item. If instead you used the packet timestamps, they can be in
		 * different units depending on your IContainer, and IStream and things
		 * can get hairy quickly.
		 */
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
	
	private boolean reload() {
		System.out.println("reload: " + this.getSourceFile());
		this.setStatus(VideoPlayer.STATUS_RELOADING);

		// wait
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.container.close();
		this.container.open(filename, IContainer.Type.READ, null);
		//this.reader.close();
		//this.reader.open();
		this.setStatus(DecodeAndPlayAudioAndVideo.STATUS_LOADED);
		return true;
	}

	public void playLoop() {
		while (true) {
			if (this.getStatus() != DecodeAndPlayAudioAndVideo.STATUS_RELOADING) {
				if (this.getStatus() == DecodeAndPlayAudioAndVideo.STATUS_PLAY
						|| this.getStatus() == DecodeAndPlayAudioAndVideo.STATUS_LOADED) {
					if (this.getStatus() != DecodeAndPlayAudioAndVideo.STATUS_RELOADING
							&& this.container.readNextPacket(packet) >= 0) {
						
					}
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
		if (this.getStatus() == DecodeAndPlayAudioAndVideo.STATUS_PAUSE
				|| this.getStatus() == DecodeAndPlayAudioAndVideo.STATUS_STOP) {
			// System.out.println("start");
			this.setStatus(DecodeAndPlayAudioAndVideo.STATUS_PLAY);
			return true;
		} else if (this.getStatus() == DecodeAndPlayAudioAndVideo.STATUS_PLAY) {
			// System.out.println("pause");
			this.setStatus(DecodeAndPlayAudioAndVideo.STATUS_PAUSE);
			return true;
		} else {
			return false;
		}
	}

	public boolean stop() {
		System.out.println("stop");
		if (this.getStatus() == DecodeAndPlayAudioAndVideo.STATUS_PLAY
				|| this.getStatus() == DecodeAndPlayAudioAndVideo.STATUS_PAUSE) {
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
		return filename;
	}

	public void setSourceFile(String sourceFile) {
		this.filename = sourceFile;
	}

}
