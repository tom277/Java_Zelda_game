package model;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 
 * @author team Gemini
 *
 *         All environment classes can be extended from a main parent class that
 *         has a image field Everything can inherit from a thing class that has
 *         a skin field and a get skin variable + body etc...
 */

public class Obstruction extends Environment {
	private static final long serialVersionUID = -310240726604740595L;
	@Deprecated
	private Color color;
	public static final int ROCK0 = 0;
	public static final int ROCK1 = 1;
	public static final int BUSH = 2;
	public static final int TRUNK = 3;
	public static final int WATER = 4;
	public static final int HOUSE = 5;
	public static final int BRIDGE = 6;
	public static final int INVISIBLE_WALL = 7;
	public static final int BACKGROUND_WATER = 8;
	public static final int TREE1 = 9;
	public static final int TREE2 = 10;

	public static final int CAVE_WALL = 20;

	private int type;
	private int counter;
	private BufferedImage img0;
	private BufferedImage img1;
	private BufferedImage img2;
	private BufferedImage img3;
	private BufferedImage img4;
	private BufferedImage img5;
	private BufferedImage img6;
	private BufferedImage img7;
	private Random rand;
	private int delay;

	@Deprecated
	public Obstruction(int width, int height, int xCoordinate, int yCoordinate, Color color) {
		this.color = color;

		body = new Rectangle2D.Double(xCoordinate, yCoordinate, width, height);
		loadImage("/Background/rock.png");
		offsetX = -3;
	}

	public Obstruction(int xCoordinate, int yCoordinate, int type) {
		if (type == ROCK0) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 26, 24);
			loadImage("/Background/rock.png");
			this.type = ROCK0;
			offsetX = -3;
		} else if (type == ROCK1) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 20, 21);
			loadImage("/Background/rock2.png");
			this.type = ROCK1;
			offsetX = -3;
		} else if (type == BUSH) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 26, 24);
			loadImage("/Background/bush.png");
			this.type = BUSH;
			offsetX = -3;
		} else if (type == TRUNK) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 26, 24);
			loadImage("/Background/trunk.png");
			this.type = TRUNK;
			offsetX = -3;
		} else if (type == WATER) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 16, 16);
			loadWater();
			this.imageCurrent = img0;
			this.type = WATER;
			offsetX = 0;
			rand = new Random();
			delay = 8;
			counter = rand.nextInt(delay * 7);
		} else if (type == CAVE_WALL) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 32, 32);
			loadImage("/Background/caveWall.png");
			this.type = CAVE_WALL;
			offsetX = -3;
		} else if (type == BACKGROUND_WATER) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 0, 0);
			loadImage("/Background/water.gif");
			this.type = BACKGROUND_WATER;
			offsetX = 0;
		} else if (type == BRIDGE) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 58, 1);
			loadImage("/Background/bridge.png");
			this.type = BRIDGE;
			offsetX = 0;
		} else if (type == INVISIBLE_WALL) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 58, 1);
			loadImage("/Background/blank.png");
			this.type = INVISIBLE_WALL;
			offsetX = 0;
		} else if (type == HOUSE) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 70, 80);
			loadImage("/Background/house.png");
			this.type = HOUSE;
			offsetX = 0;
		} else if (type == TREE1) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 53, 74);
			loadImage("/Background/tree1.png");
			this.type = TREE1;
			offsetX = 0;
		} else if (type == TREE2) {
			body = new Rectangle2D.Double(xCoordinate, yCoordinate, 53, 74);
			loadImage("/Background/tree2.png");
			this.type = TREE2;
			offsetX = 0;
		}
	}

	private void loadWater() {
		try {
			img0 = ImageIO.read(getClass().getResource("/Background/tile000.png"));
			img1 = ImageIO.read(getClass().getResource("/Background/tile001.png"));
			img2 = ImageIO.read(getClass().getResource("/Background/tile002.png"));
			img3 = ImageIO.read(getClass().getResource("/Background/tile003.png"));
			img4 = ImageIO.read(getClass().getResource("/Background/tile004.png"));
			img5 = ImageIO.read(getClass().getResource("/Background/tile005.png"));
			img6 = ImageIO.read(getClass().getResource("/Background/tile006.png"));
			img7 = ImageIO.read(getClass().getResource("/Background/tile007.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void changeImages() {
		counter++;
		if (counter == delay) {
			imageCurrent = img0;
		} else if (counter == delay * 2) {
			imageCurrent = img1;
		} else if (counter == delay * 3) {
			imageCurrent = img2;
		} else if (counter == delay * 4) {
			imageCurrent = img3;
		} else if (counter == delay * 5) {
			imageCurrent = img4;
		} else if (counter == delay * 6) {
			imageCurrent = img5;
		} else if (counter == delay * 7) {
			imageCurrent = img6;
		} else if (counter >= delay * 8) {
			imageCurrent = img7;
			counter = 0;
		}
	}

	@Override
	public BufferedImage getImageCurrent() {
		if (type == WATER) {
			changeImages();
		}
		return super.getImageCurrent();
	}

	@Deprecated
	public Color getColor() {
		return color;
	}
}