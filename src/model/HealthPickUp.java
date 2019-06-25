package model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class HealthPickUp extends Object implements Serializable {

	/**
	 * creates the health regeneration drop
	 */
	private static final long serialVersionUID = 3982539786095270811L;
	protected BufferedImage image1;

	public HealthPickUp(int x, int y) {
		loadImage();
		body = new Rectangle2D.Double(x, y, 30, 30);
		imageCurrent = image1;
	}

	private void loadImage() {
		try {
			image1 = ImageIO.read(getClass().getResource("/healthPotion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
