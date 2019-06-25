package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

public class Coin extends Object implements Serializable {

	/**
	 * creates the coin
	 */
	private static final long serialVersionUID = -2956124148230048686L;
	protected BufferedImage image1;
	protected BufferedImage image2;
	protected BufferedImage image3;
	protected BufferedImage image4;
	protected BufferedImage image5;
	protected BufferedImage image6;
	private int skinAlternator;// start this off at a random number so that the coins move different from each
								// other
	private Rectangle2D.Double body;

	public Coin(int x, int y) {
		loadImage();
		body = new Rectangle2D.Double(x, y, 30, 30);
		imageCurrent = image1;
		Random rand = new Random();
		skinAlternator = rand.nextInt(60);
	}

	private void loadImage() {

		try {
			image1 = ImageIO.read(getClass().getResource("/GoldCoinSprite/Coin1.png"));
			image2 = ImageIO.read(getClass().getResource("/GoldCoinSprite/Coin2.png"));
			image3 = ImageIO.read(getClass().getResource("/GoldCoinSprite/Coin3.png"));
			image4 = ImageIO.read(getClass().getResource("/GoldCoinSprite/Coin4.png"));
			image5 = ImageIO.read(getClass().getResource("/GoldCoinSprite/Coin5.png"));
			image6 = ImageIO.read(getClass().getResource("/GoldCoinSprite/Coin6.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Image getSkin() {// alternate skin to make it looks as if it's rotating
		if (skinAlternator % 60 == 0) {
			imageCurrent = (BufferedImage) image1;//
		} else if (skinAlternator % 60 == 10) {
			imageCurrent = (BufferedImage) image2;
		} else if (skinAlternator % 60 == 20) {
			imageCurrent = (BufferedImage) image3;
		} else if (skinAlternator % 60 == 30) {
			imageCurrent = (BufferedImage) image4;
		} else if (skinAlternator % 60 == 40) {
			imageCurrent = (BufferedImage) image5;
		} else if (skinAlternator % 60 == 50) {
			imageCurrent = (BufferedImage) image6;
		}
		skinAlternator++;
		return imageCurrent;
	}

	public Rectangle2D.Double getBody() {
		return body;
	}

	public Graphics2D render(Graphics2D g2, ImageObserver observer) {
		g2.setPaint(new Color(0, 0, 0));
		g2.drawImage(getSkin(), (int) body.x, (int) body.y, observer);
		g2.draw(body);
		return g2;
	}
}
