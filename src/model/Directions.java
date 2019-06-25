package model;

import java.io.Serializable;

public class Directions  implements Serializable{
	/**
	 * Holds the directions
	 */
	private static final long serialVersionUID = 2734521965339730253L;
	private boolean upKeyPressed;
	private boolean downKeyPressed; 
	private boolean rightKeyPressed; 
	private boolean leftKeyPressed;
	
	public Directions(boolean upKeyPressed, boolean downKeyPressed, boolean rightKeyPressed, boolean leftKeyPressed) {
		super();
		this.upKeyPressed = upKeyPressed;
		this.downKeyPressed = downKeyPressed;
		this.rightKeyPressed = rightKeyPressed;
		this.leftKeyPressed = leftKeyPressed;
	}

	public boolean isUpKeyPressed() {
		return upKeyPressed;
	}

	public boolean isDownKeyPressed() {
		return downKeyPressed;
	}

	public boolean isRightKeyPressed() {
		return rightKeyPressed;
	}

	public boolean isLeftKeyPressed() {
		return leftKeyPressed;
	}

	
}
