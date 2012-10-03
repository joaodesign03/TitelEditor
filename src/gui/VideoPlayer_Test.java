package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

public class VideoPlayer_Test
{
	//public MyVideoFrame frame;// = new MyVideoFrame();
	
	/*public VideoPlayer_Test() {
		frame = new MyVideoFrame();
		frame.setLayout(new GridBagLayout());
		frame.setSize(new Dimension(600,400));
		frame.setPreferredSize(new Dimension(600, 400));
	    frame.setBorder(BorderFactory.createTitledBorder("Video Preview"));
	    frame.setVisible(true);
	} 
	public VideoPlayer_Test(String filename) {
	    this.play(filename);
	}*/
  public static void main(String[] args)
  {
    String sourceUrl = "/Users/matthias/Movies/Doctor.Who.2005.7x04.The.Power.Of.Three.HDTV.x264-FoV.mp4";
    
    GUI g = new GUI();
    g.initInterface();
    
    VideoPlayer_Test videoPlayer = new VideoPlayer_Test();
    videoPlayer.play(sourceUrl);

  }

  public void play(String sourceUrl)
  {
    IMediaReader reader = ToolFactory.makeReader(sourceUrl);
    reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
    
    final MyVideoFrame frame = new MyVideoFrame();
    frame.setSize(new Dimension(600, 500));
    //frame.setBorder(BorderFactory.createTitledBorder("Video Preview"));
    
    MediaListenerAdapter adapter = new MediaListenerAdapter()
    {
      @Override
      public void onVideoPicture(IVideoPictureEvent event)
      {
        frame.setImage((BufferedImage) event.getImage());
      }
    };
    reader.addListener(adapter);
    reader.addListener(ToolFactory.makeViewer(true));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
    while (reader.readPacket() == null)
      do {} while(false);
  }
  
  @SuppressWarnings("serial")
  public class MyVideoFrame extends JFrame
  {
    Image image;
    
    public void setImage(final Image image)
    {
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          MyVideoFrame.this.image = image;
          repaint();
        }
      });
    }
  
    @Override
    public synchronized void paint(Graphics g)
    {
      if (image != null)
      {
        g.drawImage(image, 0, 0, null);
      }
    }
  }
}