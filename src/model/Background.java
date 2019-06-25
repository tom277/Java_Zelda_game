package model;

import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Background extends Environment implements Serializable {

	/**
	 * This class loads the background sprite and stores the position it is to be
	 * rendered
	 */

	private static final long serialVersionUID = -6355953862080980261L;

	public static final int GRASS = 0;
	public static final int DIRT = 1;
	public static final int CAVE_ENTRANCE = 2;
	public static final int CAVE_EXIT = 3;

	protected int x, y;

	public Background(int x, int y, int type) {
		this.x = x;
		this.y = y;
		if (type == GRASS) {
			loadGrass();
		} else if (type == DIRT) {
			loadDirt();
		} else if (type == CAVE_ENTRANCE) {
			loadCaveEntrance();
		} else if (type == CAVE_EXIT) {
			loadCaveExit();
		}
	}

	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	public Image getSkin() {
		return imageCurrent;

	}

	private void loadGrass() {
		try {
			imageCurrent = ImageIO.read(getClass().getResource("/Background/Grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadDirt() {
		try {
			imageCurrent = ImageIO.read(getClass().getResource("/Background/dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadCaveEntrance() {
		try {
			imageCurrent = ImageIO.read(getClass().getResource("/Background/caveEntrance.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadCaveExit() {
		try {
			imageCurrent = ImageIO.read(getClass().getResource("/Background/caveEntrance.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
