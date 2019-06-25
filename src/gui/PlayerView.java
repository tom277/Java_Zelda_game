package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Hero;
import model.MyUtils;

public class PlayerView extends JPanel implements ActionListener {
	/**
	 * This creates the view that shows the player moving in the
	 * PlayerSpriteSelectScreen
	 */
	private static final long serialVersionUID = -5528224935276192455L;
	private Timer timer;
	private BufferedImage buffer;
	private BufferedImage skin;
	private int counter;
	private ArrayList<Hero> characters;
	private int currentChar;

	public PlayerView() {
		counter = 0;
		currentChar = 0;
		timer = new Timer(20, this);
		characters = new ArrayList<Hero>();
		characters.add(new Hero("Tim", null, Hero.STEVE));
		characters.add(new Hero("Tim", null, Hero.TYPE1));
		characters.add(new Hero("Tim", null, Hero.TYPE2));
	}

	public void stopTimer() {
		timer.stop();
	}

	public void reset() {
		characters = new ArrayList<Hero>();
		characters.add(new Hero("Tim", null, Hero.STEVE));
		characters.add(new Hero("girl", null, Hero.TYPE1));
		characters.add(new Hero("girl2", null, Hero.TYPE2));

	}

	public void startTimer() {
		counter = 0;
		currentChar = 0;
		timer.start();
	}

	public void previous() {
		counter = 0;
		currentChar++;
		if (currentChar >= characters.size()) {
			currentChar = 0;
		}
	}

	public void next() {
		counter = 0;
		currentChar--;
		if (currentChar < 0) {
			currentChar = characters.size() - 1;
		}
	}

	public Hero getSelectedPlayer() {
		return characters.get(currentChar);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (buffer == null) {
			buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		}
		Graphics2D g2 = (Graphics2D) buffer.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.black);
		skin = MyUtils.toBufferedImage(characters.get(currentChar).getImageCurrent().getScaledInstance(
				characters.get(currentChar).getImageCurrent().getWidth() * 5,
				characters.get(currentChar).getImageCurrent().getHeight() * 5, BufferedImage.SCALE_AREA_AVERAGING));
		g2.fillRect(0, 0, 2000, 2000);
		g2.drawImage(skin, 90, 80, this);
		g.drawImage(buffer, 0, 0, null);
	}

	public void update() {
		if (counter % 400 == 267) {
			characters.get(currentChar).setMovingRight(false);
			characters.get(currentChar).setMovingDown(false);
		} else if (counter % 400 == 133) {
			characters.get(currentChar).setMovingRight(false);
			characters.get(currentChar).setMovingDown(true);
		} else if (counter % 400 == 0) {
			characters.get(currentChar).setMovingDown(false);
			characters.get(currentChar).setMovingRight(true);
		}

		characters.get(currentChar).update();

		counter++;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}
}
