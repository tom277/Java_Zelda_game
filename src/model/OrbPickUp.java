package model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class OrbPickUp extends Object implements Serializable {

	/**
	 * creates the Orb object that finishes the game when collected
	 */
	private static final long serialVersionUID = 3982539786095270811L;
	protected BufferedImage image1;

	public OrbPickUp(int x, int y) {
		loadImage();
		body = new Rectangle2D.Double(x, y, 30, 30);
		imageCurrent = image1;
	}

	private void loadImage() {
		try {
			image1 = ImageIO.read(getClass().getResource("/orb.png"));
			image1 = MyUtils.toBufferedImage(image1.getScaledInstance(((int) (image1.getWidth() * 0.5)),
					((int) (image1.getHeight() * 0.5)), BufferedImage.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}