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

//import com.sun.tools.example.debug.bdi.Utils;
import com.xuggle.xuggler.Utils;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.demos.VideoImage;


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
	JButton start, stopp, pause, back, forw, volume;
	JButton cent, right, left, ital, bol, under;
	
	//image icons for video preview
	ImageIcon play = new ImageIcon("img/play_24.png");
	ImageIcon stop = new ImageIcon("img/stop_24.png");
	ImageIcon paused = new ImageIcon("img/pause_24.png");
	ImageIcon backward = new ImageIcon("img/backward_24.png");
	ImageIcon forward = new ImageIcon("img/forward_24.png");
	ImageIcon vol = new ImageIcon("img/volume_24.png");
	
	//image icons for text effects
	ImageIcon center = new ImageIcon("img/center_24.png");
	ImageIcon rightbound = new ImageIcon("img/rightbound_24.png");
	ImageIcon leftbound = new ImageIcon("img/leftbound_24.png");
	ImageIcon italic = new ImageIcon("img/italic1_24.png");
	ImageIcon bold = new ImageIcon("img/bold1_24.png");
	ImageIcon underline = new ImageIcon("img/underline1_24.png");
	
	
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
        
		//////
        //panels
        //////
		panel_effects = new JPanel(new GridBagLayout());
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
		
		
		//////
		//buttons video preview
		//////
		back = new JButton(backward);
		back.setOpaque(false);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setBorder(BorderFactory.createEmptyBorder(2,2,10,2)); // Especially important
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(back, gbc);
		
		
		start = new JButton(play);
		start.setPreferredSize(new Dimension(30,30));
		start.setOpaque(false);
		start.setFocusPainted(false);
		start.setBorderPainted(false);
		start.setContentAreaFilled(false);
		start.setBorder(BorderFactory.createEmptyBorder(2,2,10,2)); // Especially important
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(start, gbc);
		
		stopp = new JButton(stop);
		stopp.setPreferredSize(new Dimension(30,30));
		stopp.setOpaque(false);
		stopp.setFocusPainted(false);
		stopp.setBorderPainted(false);
		stopp.setContentAreaFilled(false);
		stopp.setBorder(BorderFactory.createEmptyBorder(2,2,10,2)); // Especially important
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(stopp, gbc);
		
		pause = new JButton(paused);
		pause.setPreferredSize(new Dimension(30,30));
		pause.setOpaque(false);
		pause.setFocusPainted(false);
		pause.setBorderPainted(false);
		pause.setContentAreaFilled(false);
		pause.setBorder(BorderFactory.createEmptyBorder(2,2,10,2)); // Especially important
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(pause, gbc);
		
		forw = new JButton(forward);
		forw.setPreferredSize(new Dimension(30,30));
		forw.setOpaque(false);
		forw.setFocusPainted(false);
		forw.setBorderPainted(false);
		forw.setContentAreaFilled(false);
		forw.setBorder(BorderFactory.createEmptyBorder(2,2,10,2)); // Especially important
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(forw, gbc);
		
		volume = new JButton(vol);
		volume.setPreferredSize(new Dimension(30,30));
		volume.setOpaque(false);
		volume.setFocusPainted(false);
		volume.setBorderPainted(false);
		volume.setContentAreaFilled(false);
		volume.setBorder(BorderFactory.createEmptyBorder(2,30,10,2)); // Especially important
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		start_panel.add(volume, gbc);
		
		///////
		//buttons text effects
		//////
		left = new JButton(leftbound);
		//left.setPreferredSize(new Dimension(30,30));
		left.setOpaque(false);
		left.setFocusPainted(false);
		left.setBorderPainted(false);
		left.setContentAreaFilled(false);
		left.setBorder(BorderFactory.createEmptyBorder(1,1,1,1)); // Especially important
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(left, gbc);
		
		cent = new JButton(center);
		//left.setPreferredSize(new Dimension(30,30));
		cent.setOpaque(false);
		cent.setFocusPainted(false);
		cent.setBorderPainted(false);
		cent.setContentAreaFilled(false);
		cent.setBorder(BorderFactory.createEmptyBorder(1,1,1,1)); // Especially important
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(cent, gbc);
		
		right = new JButton(rightbound);
		//left.setPreferredSize(new Dimension(30,30));
		right.setOpaque(false);
		right.setFocusPainted(false);
		right.setBorderPainted(false);
		right.setContentAreaFilled(false);
		right.setBorder(BorderFactory.createEmptyBorder(1,1,1,1)); // Especially important
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(right, gbc);
		
		ital = new JButton(italic);
		//left.setPreferredSize(new Dimension(30,30));
		ital.setOpaque(false);
		ital.setFocusPainted(false);
		ital.setBorderPainted(false);
		ital.setContentAreaFilled(false);
		ital.setBorder(BorderFactory.createEmptyBorder(1,5,1,1)); // Especially important
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(ital, gbc);
		
		bol = new JButton(bold);
		//left.setPreferredSize(new Dimension(30,30));
		bol.setOpaque(false);
		bol.setFocusPainted(false);
		bol.setBorderPainted(false);
		bol.setContentAreaFilled(false);
		bol.setBorder(BorderFactory.createEmptyBorder(1,1,1,1)); // Especially important
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(bol, gbc);
		
		under = new JButton(underline);
		//left.setPreferredSize(new Dimension(30,30));
		under.setOpaque(false);
		under.setFocusPainted(false);
		under.setBorderPainted(false);
		under.setContentAreaFilled(false);
		under.setBorder(BorderFactory.createEmptyBorder(1,1,1,1)); // Especially important
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel_settings.add(under, gbc);
		
		//frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
	}


	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent object) {
		//close application
		if (object.getSource() == exit){
			System.exit(0);
       }
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
				System.out.println ("Datei "+chooser.getSelectedFile()+" ausgewählt.");
				String filename = chooser.getSelectedFile().toString();
		     	// create a media reader
	  	       // IMediaReader mediaReader = ToolFactory.makeReader(chooser.getSelectedFile().toString());
	  	         
	  	        // create a media writer
	  	       // IMediaWriter mediaWriter = ToolFactory.makeWriter(chooser.getSelectedFile().toString(), mediaReader);
	  	 
	  	        // add a writer to the reader, to create the output file
	  	       // mediaReader.addListener(mediaWriter);
	  	         
	  	        // create a media viewer with stats enabled
	  	        //IMediaViewer mediaViewer = ToolFactory.makeViewer(true);
	  	         
	  	        // add a viewer to the reader, to see the decoded media
	  	        //mediaReader.addListener(mediaViewer);
	          	// read and decode packets from the source file and
	  			// and dispatch decoded audio and video to the writer
	  	        //while (mediaReader.readPacket() == null) ;
				
				/**************
				 * START VIDEO
				 ************/
				if (!IVideoResampler.isSupported(
				        IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION))
				      throw new RuntimeException("you must install the GPL version" +
				      		" of Xuggler (with IVideoResampler support) for " +
				      		"this demo to work");

				    // Create a Xuggler container object
				    IContainer container = IContainer.make();

				    // Open up the container
				    if (container.open(filename, IContainer.Type.READ, null) < 0)
				      throw new IllegalArgumentException("could not open file: " + filename);

				    // query how many streams the call to open found
				    int numStreams = container.getNumStreams();

				    // and iterate through the streams to find the first video stream
				    int videoStreamId = -1;
				    IStreamCoder videoCoder = null;
				    for(int i = 0; i < numStreams; i++)
				    {
				      // Find the stream object
				      IStream stream = container.getStream(i);
				      // Get the pre-configured decoder that can decode this stream;
				      IStreamCoder coder = stream.getStreamCoder();

				      if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO)
				      {
				        videoStreamId = i;
				        videoCoder = coder;
				        break;
				      }
				    }
				    if (videoStreamId == -1)
				      throw new RuntimeException("could not find video stream in container: "
				          +filename);

				    /*
				     * Now we have found the video stream in this file.  Let's open up our decoder so it can
				     * do work.
				     */
				    if (videoCoder.open() < 0)
				      throw new RuntimeException("could not open video decoder for container: "
				          +filename);

				    IVideoResampler resampler = null;
				    if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24)
				    {
				      // if this stream is not in BGR24, we're going to need to
				      // convert it.  The VideoResampler does that for us.
				      resampler = IVideoResampler.make(videoCoder.getWidth(), 
				          videoCoder.getHeight(), IPixelFormat.Type.BGR24,
				          videoCoder.getWidth(), videoCoder.getHeight(), videoCoder.getPixelType());
				      if (resampler == null)
				        throw new RuntimeException("could not create color space " +
				        		"resampler for: " + filename);
				    }
				    /*
				     * And once we have that, we draw a window on screen
				     */
				    openJavaWindow();

				    /*
				     * Now, we start walking through the container looking at each packet.
				     */
				    IPacket packet = IPacket.make();
				    long firstTimestampInStream = Global.NO_PTS;
				    long systemClockStartTime = 0;
				    while(container.readNextPacket(packet) >= 0)
				    {
				      /*
				       * Now we have a packet, let's see if it belongs to our video stream
				       */
				      if (packet.getStreamIndex() == videoStreamId)
				      {
				        /*
				         * We allocate a new picture to get the data out of Xuggler
				         */
				        IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(),
				            videoCoder.getWidth(), videoCoder.getHeight());

				        int offset = 0;
				        while(offset < packet.getSize())
				        {
				          /*
				           * Now, we decode the video, checking for any errors.
				           * 
				           */
				          int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
				          if (bytesDecoded < 0)
				            throw new RuntimeException("got error decoding video in: "
				                + filename);
				          offset += bytesDecoded;

				          /*
				           * Some decoders will consume data in a packet, but will not be able to construct
				           * a full video picture yet.  Therefore you should always check if you
				           * got a complete picture from the decoder
				           */
				          if (picture.isComplete())
				          {
				            IVideoPicture newPic = picture;
				            /*
				             * If the resampler is not null, that means we didn't get the
				             * video in BGR24 format and
				             * need to convert it into BGR24 format.
				             */
				            if (resampler != null)
				            {
				              // we must resample
				              newPic = IVideoPicture.make(resampler.getOutputPixelFormat(),
				                  picture.getWidth(), picture.getHeight());
				              if (resampler.resample(newPic, picture) < 0)
				                throw new RuntimeException("could not resample video from: "
				                    + filename);
				            }
				            if (newPic.getPixelType() != IPixelFormat.Type.BGR24)
				              throw new RuntimeException("could not decode video" +
				              		" as BGR 24 bit data in: " + filename);

				            /**
				             * We could just display the images as quickly as we decode them,
				             * but it turns out we can decode a lot faster than you think.
				             * 
				             * So instead, the following code does a poor-man's version of
				             * trying to match up the frame-rate requested for each
				             * IVideoPicture with the system clock time on your computer.
				             * 
				             * Remember that all Xuggler IAudioSamples and IVideoPicture objects
				             * always give timestamps in Microseconds, relative to the first
				             * decoded item. If instead you used the packet timestamps, they can
				             * be in different units depending on your IContainer, and IStream
				             * and things can get hairy quickly.
				             */
				            if (firstTimestampInStream == Global.NO_PTS)
				            {
				              // This is our first time through
				              firstTimestampInStream = picture.getTimeStamp();
				              // get the starting clock time so we can hold up frames
				              // until the right time.
				              systemClockStartTime = System.currentTimeMillis();
				            } else {
				              long systemClockCurrentTime = System.currentTimeMillis();
				              long millisecondsClockTimeSinceStartofVideo =
				                systemClockCurrentTime - systemClockStartTime;
				              // compute how long for this frame since the first frame in the
				              // stream.
				              // remember that IVideoPicture and IAudioSamples timestamps are
				              // always in MICROSECONDS,
				              // so we divide by 1000 to get milliseconds.
				              long millisecondsStreamTimeSinceStartOfVideo =
				                (picture.getTimeStamp() - firstTimestampInStream)/1000;
				              final long millisecondsTolerance = 50; // and we give ourselfs 50 ms of tolerance
				              final long millisecondsToSleep = 
				                (millisecondsStreamTimeSinceStartOfVideo -
				                  (millisecondsClockTimeSinceStartofVideo +
				                      millisecondsTolerance));
				              if (millisecondsToSleep > 0)
				              {
				                try
				                {
				                  Thread.sleep(millisecondsToSleep);
				                }
				                catch (InterruptedException e)
				                {
				                  // we might get this when the user closes the dialog box, so
				                  // just return from the method.
				                  return;
				                }
				              }
				            }

				            // And finally, convert the BGR24 to an Java buffered image
				            @SuppressWarnings("deprecation")
							BufferedImage javaImage = Utils.videoPictureToImage(newPic);

				            // and display it on the Java Swing window
				            updateJavaWindow(javaImage);
				          }
				        }
				      }
				      else
				      {
				        /*
				         * This packet isn't part of our video stream, so we just
				         * silently drop it.
				         */
				        do {} while(false);
				      }

				    }
				    /*
				     * Technically since we're exiting anyway, these will be cleaned up by 
				     * the garbage collector... but because we're nice people and want
				     * to be invited places for Christmas, we're going to show how to clean up.
				     */
				    if (videoCoder != null)
				    {
				      videoCoder.close();
				      videoCoder = null;
				    }
				    if (container !=null)
				    {
				      container.close();
				      container = null;
				    }
				    closeJavaWindow();


				
				/**************
				 * END VIDEO
				 * ***********/
				 
				
			  } //end if.approved

       } //end actionperformend-open
	
		
	}//end action performed
	/**
	 * The window we'll draw the video on.
	 * 
	 */
	private static VideoImage mScreen = null;

	private static void updateJavaWindow(BufferedImage javaImage)
	{
	  mScreen.setImage(javaImage);
	}

	/**
	 * Opens a Swing window on screen.
	 */
	private static void openJavaWindow()
	{
	  mScreen = new VideoImage();
	}

	/**
	 * Forces the swing thread to terminate; I'm sure there is a right
	 * way to do this in swing, but this works too.
	 */
	private static void closeJavaWindow()
	{
	  System.exit(0);
	}
	
}//end class


