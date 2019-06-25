package model;

import java.io.Serializable;

public class Direction  implements Serializable{
	/**
	 * represents the direction something is facing
	 */
	private static final long serialVersionUID = -2338299990948631562L;

	public static enum Dir {
		UP, DOWN, LEFT, RIGHT
	}
	Dir direction;
	
	public Direction(Dir direction) {
		this.direction = direction;
	}
	
	public Dir getDirection() {
		return direction;
	}

}
