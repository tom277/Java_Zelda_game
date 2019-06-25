package model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class MapChangeInteraction extends Environment implements Serializable {

	/**
	 * interaction that causes the map to change when collinding with the hero
	 */
	private static final long serialVersionUID = 5007454295052902937L;
	protected Rectangle2D.Double interaction;
	private String interactionID;
	private int map;
	private int spawnX;
	private int spawnY;

	public MapChangeInteraction(int map, int x, int y, int spawnX, int spawnY) {
		createInteractiveComponent(map, x, y, 35, 35, 35, 35, "/Background/Grass.png");
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public int getMap() {
		return map;
	}

	private void createInteractiveComponent(int map, int x, int y, int interactionX, int interactionY, int bodyX,
			int bodyY, String path) {
		loadImage(path);
		body = new Rectangle2D.Double(x, y, bodyX, bodyY);
		interaction = new Rectangle2D.Double(x, y, interactionX, interactionY);
		this.map = map;
	}

	public Rectangle2D.Double getInteraction() {
		return interaction;
	}

	public String launchInteraction() {
		return interactionID;
	}
}