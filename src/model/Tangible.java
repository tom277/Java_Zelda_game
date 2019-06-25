package model;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;

public abstract class Tangible implements Serializable {
	/**
	 * Tangible is the class all things in the game inherit from stores the sprite
	 * and location to be rendered
	 */
	private static final long serialVersionUID = -167035364225314750L;
	protected BufferedImage imageCurrent;
	protected int offsetX;
	protected int offsetY;
	protected Rectangle2D.Double body;// check consistency of the body
	protected boolean hasCollision;

	public Tangible() {
		offsetX = 0;
		offsetY = 0;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public boolean isHasCollision() {
		return hasCollision;
	}

	public boolean hasColision() {
		return hasCollision;
	}

	public BufferedImage getImageCurrent() {
		return imageCurrent;
	}

	public Rectangle2D.Double getBody() {
		return body;
	}

	protected void setImageCurrent(BufferedImage imageCurrent) {
		this.imageCurrent = imageCurrent;
	}

	public int getX() {
		return (int) body.getX();
	}

	protected void setX(int x) {
		this.body.x = x;
	}

	protected void setWidth(int width) {
		this.body.width = width;
	}

	public int getY() {
		return (int) body.getY();
	}

	protected void setY(int y) {
		this.body.y = y;
	}

	protected void setHeight(int height) {
		this.body.height = height;
	}

	public static Image getAnImage(String path) {
		ImageIcon ii = new ImageIcon(path);
		Image tmp = ii.getImage();
		return tmp;
	}
}