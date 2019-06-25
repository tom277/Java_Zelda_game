package controller;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import gui.SoundBoard;
import listeners.HeroMovementListener;
import model.Baddy;
import model.Hero;
import model.Map;

public class HeroMovement implements Serializable {
	/**
	 * This class receives the keyboard input and moves the player appropriately, as
	 * well as checking for collisions and also handles player behaviours like
	 * shooting
	 */
	private static final long serialVersionUID = -5822654080867013085L;
	protected Rectangle2D.Double body;
	protected Map currentMap;
	protected int xDirection;

	protected int yDirection;
	private int bashCoolDownCount;
	protected double speed;
	protected Hero hero;
	protected double defaultSpeed;
	private HeroMovementListener listener;
	private boolean upKeyPressed;
	private boolean downKeyPressed;
	private boolean rightKeyPressed;
	private boolean leftKeyPressed;

	public HeroMovement(Map map, listeners.HeroMovementListener heroMovementListener) {
		upKeyPressed = false;
		downKeyPressed = false;
		rightKeyPressed = false;
		leftKeyPressed = false;
		this.listener = heroMovementListener;
		this.currentMap = map;
		speed = 5;
		defaultSpeed = speed;
		xDirection = 1;
		yDirection = 1;
		bashCoolDownCount = 0;
	}

	public synchronized void update() {
		bashCoolDownCount++;

		checkDamage(hero.getBody());

		for (int i = 0; i < speed; i++) {
			if (upKeyPressed) {
				hero.getBody().y -= hero.getSpeed();
				if (isObjObstructed(hero.getBody())) {
					hero.getBody().y += hero.getSpeed();
				}
			}
			if (downKeyPressed) {
				hero.getBody().y += hero.getSpeed();
				if (isObjObstructed(hero.getBody())) {
					hero.getBody().y -= hero.getSpeed();
				}
			}
			if (rightKeyPressed) {
				hero.getBody().x += hero.getSpeed();
				if (isObjObstructed(hero.getBody())) {
					hero.getBody().x -= hero.getSpeed();
				}
			}
			if (leftKeyPressed) {
				hero.getBody().x -= hero.getSpeed();
				if (isObjObstructed(hero.getBody())) {
					hero.getBody().x += hero.getSpeed();
				}
			}
		}

		hero.setIsMoving(upKeyPressed || downKeyPressed || rightKeyPressed || leftKeyPressed);
		hero.setMovingUp(upKeyPressed);
		hero.setMovingDown(downKeyPressed);
		hero.setMovingLeft(leftKeyPressed);
		hero.setMovingRight(rightKeyPressed);
	}

	public boolean isObjObstructed(Rectangle2D.Double obj) {
		for (int i = 0; i < currentMap.getObstructions().size(); i++) {
			if (obj.intersects(currentMap.getObstructions().get(i).getBody().getBounds2D())) {
				PlayBashSound();
				return true;
			}

		}

		for (int i = 0; i < currentMap.getMapChanges().size(); i++) {
			if (obj.intersects(currentMap.getMapChanges().get(i).getBody().getBounds2D())) {
				int mapNum = currentMap.getMapChanges().get(i).getMap();
				if (currentMap.getMapChanges().get(i).getSpawnX() > -1) {
					hero.getBody().x = currentMap.getMapChanges().get(i).getSpawnX();
				}
				if (currentMap.getMapChanges().get(i).getSpawnY() > -1) {
					hero.getBody().y = currentMap.getMapChanges().get(i).getSpawnY();
				}
				currentMap = listener.changeMap(mapNum);// change the map
				currentMap.reset();// reset the mob locations
			}
		}

		listener.hideTextBox();
		listener.setCloseToShopFalse();
		listener.setCloseToGundrenFalse();
		for (int i = 0; i < currentMap.getIcs().size(); i++) {
			if (obj.intersects(currentMap.getIcs().get(i).getInteraction().getBounds2D())) {
				listener.interact(currentMap.getIcs().get(i).launchInteraction());
			}
		}
		while (checkPickUps(obj)) {
		}
		return false;
	}

	public Hero getHero() {
		return hero;
	}

	public boolean checkPickUps(Rectangle2D.Double obj) {
		for (int i = 0; i < currentMap.getPickUps().size(); i++) {
			if (obj.intersects(currentMap.getPickUps().get(i).getBody().getBounds2D())) {
				hero.addPickUp(currentMap.getPickUps().get(i));
				currentMap.getPickUps().remove(i);
				return true;
			}
		}
		return false;
	}

	public void checkDamage(Rectangle2D.Double obj) {
		for (int i = 0; i < currentMap.getBaddies().size(); i++) {
			if (obj.intersects(currentMap.getBaddies().get(i).getBody().getBounds2D())) {
				if (hero.getBody().getX() > currentMap.getBaddies().get(i).getBody().getX()) {
					hero.getBody().x += 50;
					if (isObjObstructed(hero.getBody())) {
						hero.getBody().x -= 50;
					}
				} else if (hero.getBody().getX() < currentMap.getBaddies().get(i).getBody().getX()) {
					hero.getBody().x -= 50;
					if (isObjObstructed(hero.getBody())) {
						hero.getBody().x += 50;
					}
				} else if (hero.getBody().getY() < currentMap.getBaddies().get(i).getBody().getY()) {
					hero.getBody().y += 50;
					if (isObjObstructed(hero.getBody())) {
						hero.getBody().y -= 50;
					}
				} else if (hero.getBody().getY() < currentMap.getBaddies().get(i).getBody().getY()) {
					hero.getBody().y -= 50;
					if (isObjObstructed(hero.getBody())) {
						hero.getBody().y += 50;
					}
				}

				if (i < currentMap.getBaddies().size()) {
					hero.damage(currentMap.getBaddies().get(i).getDamage());
				}
			}
		}

		if (hero.getWeapon() != null) {
			if (hero.getWeapon().getProjectiles() != null) {
				while (BadyProjectileInteraction()) {
				}
			}
		}

		for (int i = 0; i < currentMap.getBaddies().size(); i++) {
			if (currentMap.getBaddies().get(i).getWeapon() != null) {
				while (HeroProjectileInteraction(currentMap.getBaddies().get(i))) {
				}
			}
		}
	}

	private boolean HeroProjectileInteraction(Baddy baddy) {
		for (int i = 0; i < baddy.getWeapon().getProjectiles().size(); i++) {
			if (baddy.getWeapon().getProjectiles().get(i).getBody().intersects(hero.getBody().getBounds2D())) {
				baddy.getWeapon().removeProjectile(baddy.getWeapon().getProjectiles().get(i));
				hero.damage(baddy.getWeapon().getDamage());
				return true;
			}
		}
		return false;
	}

	private boolean BadyProjectileInteraction() {
		for (int i = 0; i < hero.getWeapon().getProjectiles().size(); i++) {
			for (int j = 0; j < currentMap.getBaddies().size(); j++) {
				if (currentMap.getBaddies().get(j).getBody()
						.intersects(hero.getWeapon().getProjectiles().get(i).getBody().getBounds2D())) {
					hero.getWeapon().removeProjectile(hero.getWeapon().getProjectiles().get(i));// remove the
																								// projectileImg
					currentMap.getBaddies().get(j).damage(hero.getWeapon().getDamage());// damage the baddy
					return true;
				}
			}
		}
		// check for baddy friendly fire
		for (int x = 0; x < currentMap.getBaddies().size(); x++) {
			if (currentMap.getBaddies().get(x).getWeapon() != null) {
				for (int i = 0; i < currentMap.getBaddies().get(x).getWeapon().getProjectiles().size(); i++) {
					for (int j = 0; j < currentMap.getBaddies().size(); j++) {
						if (currentMap.getBaddies().get(j).getBody().intersects(currentMap.getBaddies().get(x)
								.getWeapon().getProjectiles().get(i).getBody().getBounds2D())) {
							if (!(currentMap.getBaddies().get(x).equals(currentMap.getBaddies().get(j)))) {// baddy
																											// cannot
																											// damage
																											// itself
								currentMap.getBaddies().get(x).getWeapon().removeProjectile(
										currentMap.getBaddies().get(x).getWeapon().getProjectiles().get(i));// remove
																											// the
																											// projectileImg
								currentMap.getBaddies().get(j)
										.damage(currentMap.getBaddies().get(x).getWeapon().getDamage());// damage the
																										// baddy

								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public void PlayBashSound() {
		if (bashCoolDownCount > 15) {
			SoundBoard.playSound("IMPACT02.WAV");
			bashCoolDownCount = 0;
		}
	}

	public void loadFinalMap() {
		currentMap = listener.changeMap(3);// change the map
		currentMap.reset();// reset the mob locations
	}

	public void setBody(Rectangle2D.Double body) {
		this.body = body;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;

	}

	public void setxDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	public void setyDirection(int yDirection) {
		this.yDirection = yDirection;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void setDefaultSpeed(double defaultSpeed) {
		this.defaultSpeed = defaultSpeed;
	}

	public void setListener(HeroMovementListener listener) {
		this.listener = listener;
	}

	public void setUpKeyPressed(boolean upKeyPressed) {
		this.upKeyPressed = upKeyPressed;
	}

	public void setDownKeyPressed(boolean downKeyPressed) {
		this.downKeyPressed = downKeyPressed;
	}

	public void setRightKeyPressed(boolean rightKeyPressed) {
		this.rightKeyPressed = rightKeyPressed;
	}

	public void setLeftKeyPressed(boolean leftKeyPressed) {
		this.leftKeyPressed = leftKeyPressed;
	}
}
