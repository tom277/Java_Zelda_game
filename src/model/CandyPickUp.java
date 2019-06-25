package model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class CandyPickUp extends Object implements Serializable {

	/**
	 * handles the candy pick up which speed the player up
	 */
	private static final long serialVersionUID = -2857248496864637411L;
	protected BufferedImage image1;
	private double speedBoost;

	public CandyPickUp(int x, int y) {
		loadImage();
		body = new Rectangle2D.Double(x, y, 30, 30);
		imageCurrent = image1;
		speedBoost = 1;
	}
	
	public double getSpeedBoost() {
		return speedBoost;
	}

	private void loadImage() {
		try {
			image1 = ImageIO.read(getClass().getResource("/candy.png"));
			image1 = MyUtils.toBufferedImage(image1.getScaledInstance(((int) (image1.getWidth() * 0.7)), ((int) (image1.getHeight() * 0.7)), BufferedImage.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
