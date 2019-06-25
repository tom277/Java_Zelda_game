package model;

import java.awt.Image;
import java.io.Serializable;

public abstract class Object extends Tangible  implements Serializable{
	/**
	 * Object class
	 */
	private static final long serialVersionUID = -118821463452421528L;

	public Image getSkin() {
		return imageCurrent;
	}
}
