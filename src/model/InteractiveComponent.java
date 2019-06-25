package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class InteractiveComponent extends Environment implements Serializable {

	/**
	 * Interactive components are things like the shop gundren and map, so things
	 * that have both a colision box and an interactive boxs
	 */
	private static final long serialVersionUID = -1190559610791489238L;
	protected Rectangle2D.Double interaction;
	private String interactionID;
	private BufferedImage skin;

	public static final int STORE = 0;
	public static final int NEW_MAP = 1;
	public static final int GUNDREN = 2;

	public InteractiveComponent(String interactionID, int x, int y, int interactionX, int interactionY, int bodyX,
			int bodyY, String spritePath) {
		createInteractiveComponent(interactionID, x, y, interactionX, interactionY, bodyX, bodyY, spritePath);
	}

	public InteractiveComponent(int predefineConstructorNum, int x, int y) {
		if (predefineConstructorNum == STORE) {
			createInteractiveComponent("shop", x, y, (int) (80 * 1.5), (int) (75 * 1.5), (int) (60 * 1.5),
					(int) (30 * 1.5), "/generic-rpg-vendor.png");
			offsetX = -10;
			offsetY = -10;
		} else if (predefineConstructorNum == GUNDREN) {
			createInteractiveComponent("gundren", x, y, 35, 35, 25, 40, "/sensei.png");
			offsetX = 0;
			offsetY = 0;
		}
	}

	public InteractiveComponent(int map, int predefineConstructorNum, int x, int y) {
		if (predefineConstructorNum == STORE) {
			createInteractiveComponent("shop", x, y, (int) (80 * 1.5), (int) (75 * 1.5), (int) (60 * 1.5),
					(int) (30 * 1.5), "/generic-rpg-vendor.png");
			offsetX = -10;
			offsetY = -10;
		} else if (predefineConstructorNum == NEW_MAP) {
			createInteractiveComponent("gundren", x, y, 35, 35, 25, 40, "/sensei.png");
			offsetX = 0;
			offsetY = 0;
		}
	}

	private void createInteractiveComponent(String interactionID, int x, int y, int interactionX, int interactionY,
			int bodyX, int bodyY, String path) {
		loadImage(path);
		// this.x = x;
		// this.y = y;
		body = new Rectangle2D.Double(x, y, bodyX, bodyY);
		interaction = new Rectangle2D.Double(x, y, interactionX, interactionY);
		this.interactionID = interactionID;
	}

	public Rectangle2D.Double getInteraction() {
		return interaction;
	}

	public String launchInteraction() {
		return interactionID;
	}

	protected void loadImage(String path) {
		try {
			skin = ImageIO.read(getClass().getResource(path));
			skin = MyUtils.toBufferedImage(skin.getScaledInstance(((int) (skin.getWidth() * 1.5)),
					((int) (skin.getHeight() * 1.5)), BufferedImage.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Graphics2D render(Graphics2D g2, ImageObserver observer) {
		g2.setPaint(new Color(0, 0, 0));
		g2.drawImage(skin, (int) body.x + offsetX, (int) body.y + offsetX, observer);
		// g2.fill(body);//to check the 'hit' box
		g2.draw(body);
		g2.setPaint(Color.BLUE);
		g2.draw(interaction);// hit boxes for debugging purpuses
		return g2;
	}

	public String getInteractionID() {
		return interactionID;
	}

	public BufferedImage getSkin() {
		return skin;
	}
}