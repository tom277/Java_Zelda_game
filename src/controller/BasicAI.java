package controller;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Random;

import listeners.AIShootListener;
import model.Baddy;
import model.Hero;
import model.Map;

public class BasicAI implements Serializable {
	/**
	 * This class give Baddies basic move abilities
	 */
	private static final long serialVersionUID = 5576961222292631336L;
	protected Rectangle2D.Double body;
	protected Map map;
	protected int xDirection;
	protected int yDirection;
	protected double speed;
	protected Hero hero;
	protected double defaultSpeed;
	protected Baddy baddy;
	protected AIShootListener listener;
	protected Random random;
	protected int randomDirection;

	public BasicAI(Map map, Hero hero) {
		this.map = map;
		speed = 1.5;
		defaultSpeed = speed;
		xDirection = 1;
		yDirection = 1;
		this.hero = hero;
		random = new Random();
		movePosition(body);
	}

	public Map getMap() {
		return map;
	}

	public Hero getHero() {
		return hero;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
		this.defaultSpeed = speed;
	}

	public void addBaddy(Baddy baddy) {
		this.baddy = baddy;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	// moving logic
	public Rectangle2D.Double movePosition(Rectangle2D.Double body) {
		if (hero != null) {
			if ((java.lang.Math.sqrt(java.lang.Math.pow(hero.getBody().getX() - body.x, 2)
					+ java.lang.Math.pow(hero.getBody().getY() - body.y, 2))) < 200) {// Hero is in range
				baddy.setMovingUp(false);
				baddy.setMovingDown(false);
				baddy.setMovingLeft(false);
				baddy.setMovingRight(false);
				speed = defaultSpeed * 1.2;
				if (hero.getBody().getX() > body.getX() + 2) {
					xDirection = 1;
					body.x += xDirection * speed;
					if (isObjObstructed(body)) {
						body.x -= xDirection * speed;
					}
					baddy.setMovingRight(true);
				} else if (hero.getBody().getX() < body.getX() - 2) {
					xDirection = -1;
					body.x += xDirection * speed;
					if (isObjObstructed(body)) {
						body.x -= xDirection * speed;
					}
					baddy.setMovingLeft(true);
				}

				if (hero.getBody().getY() > body.getY() + 2) {
					yDirection = 1;
					body.y += yDirection * speed;
					if (isObjObstructed(body)) {
						body.y -= yDirection * speed;
					}
					baddy.setMovingDown(true);
				} else if (hero.getBody().getY() < body.getY() - 2) {
					yDirection = -1;
					body.y += yDirection * speed;
					if (isObjObstructed(body)) {
						body.y -= yDirection * speed;
					}
					baddy.setMovingUp(true);
				}
				baddy.setIsMoving(true);
			} else {// Hero is not in range
				baddy.setMovingUp(false);
				baddy.setMovingDown(false);
				baddy.setMovingLeft(false);
				baddy.setMovingRight(false);
				speed = defaultSpeed;
				// body.x += xDirection * speed;
				int tmp = random.nextInt(100);
				boolean stationary = false;
				if (random.nextInt(100) < 10) {
					stationary = random.nextBoolean();
				}

				if (!stationary) {// change direction
					tmp = random.nextInt(100);
					if (tmp < 3) {
						randomDirection = random.nextInt(4);
						randomDirection = 4;
					}
					if (randomDirection == 0) {
						xDirection = 1;
						body.x += xDirection * speed;
						if (isObjObstructed(body)) {
							body.x -= xDirection * speed;
							randomDirection = 1;
						}
						baddy.setMovingRight(true);
					} else if (randomDirection == 2) {
						xDirection = -1;
						body.x += xDirection * speed;
						if (isObjObstructed(body)) {
							body.x -= xDirection * speed;
							randomDirection = 0;
						}
						baddy.setMovingLeft(true);
					} else if (randomDirection == 3) {
						yDirection = 1;
						body.y += yDirection * speed;
						if (isObjObstructed(body)) {
							body.y -= yDirection * speed;
							randomDirection = 4;
						}
						baddy.setMovingDown(true);
					} else {
						yDirection = -1;
						body.y += yDirection * speed;
						if (isObjObstructed(body)) {
							body.y -= yDirection * speed;
							randomDirection = 3;
						}
						baddy.setMovingUp(true);
					}
					baddy.setIsMoving(true);
				} else {
					baddy.setIsMoving(false);
				}
			}

			// richochet of the wall
			if (body.x > map.getWidth()) {
				randomDirection = 0;
			} else if (body.x < 0) {
				randomDirection = 1;
			}
			if (body.y > map.getHeight()) {
				randomDirection = 4;
			} else if (body.y < 0) {
				randomDirection = 3;
			}
		}
		return body;
	}

	public Rectangle2D.Double update(Rectangle2D.Double body) {
		return movePosition(body);
	}

	public boolean isObjObstructed(Rectangle2D.Double obj) {
		for (int i = 0; i < map.getObstructions().size(); i++) {
			if (obj.intersects(map.getObstructions().get(i).getBody().getBounds2D())) {
				return true;
			}
		}

		for (int i = 0; i < map.getBaddies().size(); i++) {
			if (!(map.getBaddies().get(i).equals(baddy))) {
				if (obj.intersects(map.getBaddies().get(i).getBody().getBounds2D())) {
					return true;
				}
			}
		}
		return false;
	}

	public void setAIShootListener(AIShootListener listener) {
		this.listener = listener;
	}
}