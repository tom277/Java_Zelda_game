package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import model.MyUtils;
import model.Tangible;

public class TextBox extends Tangible {
	private static final long serialVersionUID = 387404281065862840L;
	/**
	 * This createst the dialog that pops up when npcs talk and when you can
	 * interact with a component
	 */
	protected boolean isVisible;
	private ArrayList<String> message;

	public TextBox() {
		try {
			imageCurrent = ImageIO.read(getClass().getResource("/text-box.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		body = new Rectangle2D.Double(200, 600, 0, 0);
		message = new ArrayList<>();
		message.add("default message");
		isVisible = false;
	}

	public String getText(int index) {
		return message.get(index);
	}

	public void show(String... msgs) {
		ArrayList<String> tmpMessage = new ArrayList<String>();
		for (String i : msgs) {
			tmpMessage.add(i);
		}
		this.message = tmpMessage;
		isVisible = true;
	}

	public void hide() {
		isVisible = false;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public Graphics2D render(Graphics2D g2, ImageObserver observer) {
		if (isVisible) {
			g2.setPaint(new Color(22, 18, 15));// color of the string
			g2.drawImage(getImageCurrent(), getX(), getY(), observer);

			Font font = MyUtils.getFont(Font.BOLD, 20);
			g2.setFont(font);
			for (int i = 0; i < message.size(); i++) {
				g2.drawString(getText(i), getX() + 10, getY() + 25 + 25 * i);
			}
		}
		return g2;
	}
}