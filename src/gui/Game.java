package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;

import listeners.SpecialInteractionEvent;
import listeners.SpecialInteractionListener;
import model.Hero;
import model.Map;

public class Game extends JComponent {

	/**
	 * This is the panel where the game is played and shown
	 */
	private static final long serialVersionUID = -3457005030667715950L;

	private BufferedImage buffer;
	private Hero hero;
	private Map currentMap;
	private TextBox textBox;

	private boolean closeToShop;
	private boolean closeToGundren;
	private boolean shopIsOpen;

	private SpecialInteractionListener interactionListener;

	public Game() {

		this.currentMap = null;

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				buffer = null;
				super.componentResized(arg0);
			}
		});

		Cursor hiddenCursor = getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "");
		setCursor(hiddenCursor);
		textBox = new TextBox();

		shopIsOpen = false;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (buffer == null) {
			buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		}
		Graphics2D g2 = (Graphics2D) buffer.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// background
		for (int i = 0; i < currentMap.getBackground().size(); i++) {
			g2.drawImage(currentMap.getBackground().get(i).getSkin(), currentMap.getBackground().get(i).getX(),
					currentMap.getBackground().get(i).getY(), this);
		}
		// obstructions
		for (int i = 0; i < currentMap.getObstructions().size(); i++) {
			g2.drawImage(currentMap.getObstructions().get(i).getImageCurrent(),
					(int) currentMap.getObstructions().get(i).getBody().x
							+ currentMap.getObstructions().get(i).getOffsetX(),
					(int) currentMap.getObstructions().get(i).getBody().y
							+ currentMap.getObstructions().get(i).getOffsetX(),
					this);
		}

		for (int i = 0; i < currentMap.getBaddies().size(); i++) {
			g2.drawImage(currentMap.getBaddies().get(i).getImageCurrent(),
					(int) currentMap.getBaddies().get(i).getBody().x, (int) currentMap.getBaddies().get(i).getBody().y,
					this);
			// g2.draw(currentMap.getBaddies().get(i).getBody());
			if (currentMap.getBaddies().get(i).getWeapon() != null) {
				for (int j = 0; j < currentMap.getBaddies().get(i).getWeapon().getProjectiles().size(); j++) {
					// g2.setColor(Color.BLUE);
					g2.drawImage(currentMap.getBaddies().get(i).getWeapon().getProjectiles().get(j).getImageCurrent(),
							(int) currentMap.getBaddies().get(i).getWeapon().getProjectiles().get(j).getBody().x,
							(int) currentMap.getBaddies().get(i).getWeapon().getProjectiles().get(j).getBody().y, this);
				}
			}
		}

		textBox.render(g2, this);

		// interactive component
		for (int i = 0; i < currentMap.getIcs().size(); i++) {
			g2.drawImage(currentMap.getIcs().get(i).getSkin(),
					(int) currentMap.getIcs().get(i).getBody().x + currentMap.getIcs().get(i).getOffsetX(),
					(int) currentMap.getIcs().get(i).getBody().y + currentMap.getIcs().get(i).getOffsetY(), this);
		}

		// render the hero and hero projectiles
		g2.drawImage(hero.getImageCurrent(), (int) hero.getBody().x + hero.getOffsetX(),
				(int) hero.getBody().y + hero.getOffsetY(), this);
		if (hero.getWeapon() != null) {
			for (int i = 0; i < hero.getWeapon().getProjectiles().size(); i++) {
				g2.drawImage(hero.getWeapon().getProjectiles().get(i).getImageCurrent(),
						(int) hero.getWeapon().getProjectiles().get(i).getBody().x,
						(int) hero.getWeapon().getProjectiles().get(i).getBody().y, this);
			}
		}

		// render pick ups
		g2.setPaint(new Color(0, 0, 0));
		for (int i = 0; i < currentMap.getPickUps().size(); i++) {
			g2.drawImage(currentMap.getPickUps().get(i).getSkin(), (int) currentMap.getPickUps().get(i).getBody().x,
					(int) currentMap.getPickUps().get(i).getBody().y, this);
		}

		g2.drawImage(hero.getRedScreen(), 0, 0, this);

		g.drawImage(buffer, 0, 0, null);
	}

	public void KeyXPressed() {
		hero.fireWeapon();
	}

	public void KeyYPressed() {
		if (closeToShop) {
			fireInteractionEvent(new SpecialInteractionEvent(this, "shop"));
			if (shopIsOpen == false) {
				SoundBoard.UpdateMusic("shop.wav", false);
				shopIsOpen = true;
			} else {
				SoundBoard.UpdateMusic("Countryside5.wav", false);
				shopIsOpen = false;
			}

		} else if (closeToGundren) {
			textBox.show("Hello " + hero.getName() + ", I am Gundren, the keeper of the",
					"Great Orb. It has been stolen from me, and I need your help!",
					"Ezufaris the wizard has used the orb to summon many evil", "creatures, take the ring to survive!");
			SoundBoard.playSound("Speech6.wav");
		}
	}

	public synchronized void update() {
		for (int i = 0; i < currentMap.getBaddies().size(); i++) {
			currentMap.getBaddies().get(i).update();
		}

		hero.update();
	}

	public Hero getHero() {
		return hero;
	}

	public void interactions(String interactionID) {
		if (interactionID.equals("shop")) {
			textBox.show("Welcome to the shop", "Press the 'E' key to shop");
			closeToShop = true;
		} else if (interactionID.equals("gundren")) {
			textBox.show("Press the 'E' key to talk");
			closeToGundren = true;
		} else {
			closeToShop = false;
			closeToGundren = false;
		}
	}

	public void checkDamage(Rectangle2D.Double obj) {
		for (int i = 0; i < currentMap.getBaddies().size(); i++) {
			if (obj.intersects(currentMap.getBaddies().get(i).getBody().getBounds2D())) {
				hero.getBody().x += 50;

				// if (i < currentMap.getBaddies().size() && i > 0) {//problem why would index
				// ever be out of bounds?
				hero.damage(currentMap.getBaddies().get(i).getDamage());
				// }
			}
		}

		if (hero.getWeapon() != null) {
			if (hero.getWeapon().getProjectiles() != null) {
				try {
					while (BadyProjectileInteraction()) {
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean BadyProjectileInteraction()
			throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		for (int i = 0; i < hero.getWeapon().getProjectiles().size(); i++) {
			for (int j = 0; j < currentMap.getBaddies().size(); j++) {
				if (currentMap.getBaddies().get(j).getBody()
						.intersects(hero.getWeapon().getProjectiles().get(i).getBody().getBounds2D())) {
					hero.getWeapon().removeProjectile(hero.getWeapon().getProjectiles().get(i));
					currentMap.getBaddies().get(j).damage(5);// hero.getWeapon().getDamage()
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void fireInteractionEvent(SpecialInteractionEvent sie) {
		if (interactionListener != null) {
			interactionListener.eventOccured(sie);
		}
	}

	public void addSpecialInteractionListener(SpecialInteractionListener listener) {
		this.interactionListener = listener;
	}

	public void resetHero() {
		hero.setMovingDown(false);
		hero.setMovingLeft(false);
		hero.setMovingRight(false);
		hero.setMovingUp(false);
	}

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	public Map getMap() {
		return currentMap;
	}

	public void setTextBox(TextBox textBox) {
		this.textBox = textBox;
	}

	public boolean isCloseToShop() {
		return closeToShop;
	}

	public void setCloseToShop(boolean closeToShop) {
		this.closeToShop = closeToShop;
	}

	public void setCloseToGundren(boolean closeToGundren) {
		this.closeToGundren = closeToGundren;
	}
}