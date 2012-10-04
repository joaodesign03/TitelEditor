package seditor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.MediaToolAdapter;

public class StaticImageMediaTool extends MediaToolAdapter {

	private BufferedImage logoImage;

	public StaticImageMediaTool(String imageFile) {

		try {
			logoImage = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not open file");
		}

	}
}
