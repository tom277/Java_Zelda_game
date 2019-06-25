package model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PickUpRing extends Object {

	/**
	 * Ring pick up
	 */
	private static final long serialVersionUID = -3539553928141770L;
	Weapon weapon;

	public PickUpRing(int x, int y) {
		body = new Rectangle2D.Double(x, y, 30, 30);
		weapon = new Weapon(Weapon.RING_1);
		loadImage();
	}

	private void loadImage() {
		try {
			imageCurrent = ImageIO.read(getClass().getResource("/Ring1.png"));
			imageCurrent = MyUtils
					.toBufferedImage(imageCurrent.getScaledInstance(((int) (imageCurrent.getWidth() * 1.5)),
							((int) (imageCurrent.getHeight() * 1.5)), BufferedImage.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Rectangle2D.Double getBody() {
		return body;
	}

	public Weapon getWeapon() {
		return weapon;
	}
}
