package model;

import java.io.Serializable;

public class Coordinate  implements Serializable{
	/**
	 * Simplifies the x and y coordinates
	 */
	private static final long serialVersionUID = -4107671193285398763L;
	int x;
	int y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
}
