package model;

import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Environment extends Tangible  implements Serializable{

	/**
	 * Environment class
	 */
	private static final long serialVersionUID = 6024338390037500677L;

	protected void loadImage(String path) {
		try {
			imageCurrent = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}