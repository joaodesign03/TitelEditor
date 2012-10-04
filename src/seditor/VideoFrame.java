package seditor;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * 
 * Ausgelagerte Klasse fŸr Erstellung des VideoFrames.
 * In diesem wird das Video angezeigt.
 * @author johann
 *
 */
public class VideoFrame extends JPanel {
	
	private static final long serialVersionUID = 1L;

	Image image;

	int border_left, border_top, img_width, img_height, new_width,
			new_height, percent;
	int[] new_bounds = new int[2];

	public void setImage(final Image image) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VideoFrame.this.image = image;
				repaint();
			}
		});
	}

	@Override
	public synchronized void paint(Graphics g) {
		if (image != null) {
			// System.out.println(0+", "+0+" x "+videoWidth+", "+videoHeight+" | "+this.getWidth()+" | "+this.getHeight());

			this.resizeImage();
			g.drawImage(image, (this.getWidth() - new_bounds[0]) / 2,
					(this.getHeight() - new_bounds[1]) / 2, new_bounds[0],
					new_bounds[1], this);
		}
	}

	private void resizeImage() {
		img_width = image.getWidth(this);
		img_height = image.getHeight(this);

		if (img_width * this.getHeight() > img_height * this.getWidth()) {
			new_width = this.getWidth();
			percent = this.getWidth() * 100 / img_width;
			new_height = Math.round(percent * img_height / 100);
		} else if (img_width * this.getHeight() < img_height
				* this.getWidth()) {
			new_height = this.getHeight();
			percent = new_height * 100 / img_height;
			new_width = Math.round(percent * img_width / 100);
		} else {
			new_width = img_width;
			new_height = img_height;
		}
		new_bounds[0] = new_width;
		new_bounds[1] = new_height;
	}
}