package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

import model.MyUtils;

public class InfoBar extends JComponent {
	/**
	 * This class displays the info bar
	 */
	private static final long serialVersionUID = 8850552405731803188L;
	private BufferedImage sheet;
	private BufferedImage heartFull;
	private BufferedImage heartHalf;
	private BufferedImage heartEmpty;
	private BufferedImage heart1;
	private BufferedImage heart2;
	private BufferedImage heart3;
	private BufferedImage greenSheet;
	private BufferedImage greenBlock;
	private BufferedImage coin;
	private int heartWidth;
	private int heartHeight;
	private int coinCount;
	private int secondsOnes;
	private int secondsTens;
	private int minutesOnes;
	private int minutesTens;
	private int secondsLeft;
	private int delay;
	private Color textColor;
	private Timer timer;

	public InfoBar() {
		secondsOnes = 0;
		secondsTens = 0;
		minutesOnes = 5;
		minutesTens = 0;
		secondsLeft = 300;
		try {
			// zeldaLike sprite sheet
			sheet = ImageIO.read(getClass().getResource("/objects.png"));
			heartFull = sheet.getSubimage(64, 2, 14, 13);
			heartHalf = sheet.getSubimage(96, 2, 14, 13);
			heartEmpty = sheet.getSubimage(128, 2, 14, 13);

			// greenUI sprite sheet
			greenSheet = ImageIO.read(getClass().getResource("/greenSheet.png"));
			greenBlock = greenSheet.getSubimage(239, 194, 49, 45);

			// coin image
			coin = ImageIO.read(getClass().getResource("/GoldCoinSprite/Coin1.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		delay = 1000; // milliseconds secondsCount = 0;
		ActionListener secondsTimerPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				secondsLeft--;
				int tempNum = secondsLeft;

				if (secondsLeft >= 0) {

					minutesOnes = secondsLeft / 60;

					while (tempNum > 60) {
						tempNum = tempNum - 60;
					}
					secondsTens = tempNum / 10;
					secondsOnes = tempNum % 10;
				}
				update();
			}
		};
		timer = new Timer(delay, secondsTimerPerformer);

		textColor = new Color(251, 208, 158);

		int heartScale = 4;
		heartWidth = 14 * heartScale;
		heartHeight = 13 * heartScale;

		UpdateHealth(6);
		coinCount = 0;
	}

	public void startTimer() {
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
	}

	public boolean isTimerRunning() {
		return timer.isRunning();
	}

	public void restartTimer() {
		secondsOnes = 0;
		secondsTens = 0;
		minutesOnes = 5;
		minutesTens = 0;
		secondsLeft = 300;
	}

	public void UpdateHealth(int health) {
		// Determine image needed for each heart
		heart1 = DetermineHeart(health, 1);
		heart2 = DetermineHeart(health, 2);
		heart3 = DetermineHeart(health, 3);
		// System.out.print("health is: " + health + "\n");
		update();
	}

	public void UpdateCoins(int coins) {
		coinCount = coins;
		update();
	}

	private BufferedImage DetermineHeart(int health, int heartNumber) {
		BufferedImage heart;
		if (health >= 2 * heartNumber) {
			heart = heartFull;
		} else if (health == (2 * heartNumber) - 1) {
			heart = heartHalf;
		} else {
			heart = heartEmpty;
		}
		return heart;
	}

	public void restart() {
		restartTimer();
		update();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Make Fonts
		Font titleFont = MyUtils.getFont(Font.BOLD, 30);
		Font formalFont = new Font("Calibri", Font.BOLD, 30);

		// Draw Titles
		g.setFont(titleFont);
		g.setColor(textColor);
		g.drawString("Health", 50, 40);
		g.drawString("Items", 300, 40);
		g.drawString("Time", 550, 40);

		// Draw tips
		g.drawString("Quest:", 750, 40);
		g.drawString("Menu:", 750, 90);
		g.drawImage(greenBlock, 900, 10, null);
		g.drawImage(greenBlock, 900, 60, null);
		g.setFont(formalFont);
		g.setColor(Color.white);
		g.drawString("Q", 914, 40);
		g.drawString("M", 912, 92);

		// Draw hearts
		g.drawImage(heart1, 50, 60, heartWidth, heartHeight, null);
		g.drawImage(heart2, 110, 60, heartWidth, heartHeight, null);
		g.drawImage(heart3, 170, 60, heartWidth, heartHeight, null);

		// Draw Items
		g.drawImage(coin, 300, 60, null);
		g.setFont(titleFont);
		g.setColor(textColor);
		g.drawString("x", 340, 85);
		// g.drawString("x", 340, 125);
		g.setFont(titleFont);
		g.drawString(Integer.toString(coinCount), 360, 85);

		// Draw items
		g.drawString(Integer.toString(secondsOnes), 620, 85);
		g.drawString(Integer.toString(secondsTens), 600, 85);
		g.drawString(":", 590, 80);
		g.drawString(Integer.toString(minutesOnes), 570, 85);
		g.drawString(Integer.toString(minutesTens), 550, 85);

	}

	public void update() {
		repaint();
	}
}
