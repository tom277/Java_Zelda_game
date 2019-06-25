package controller;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import model.Boss;
import model.DifficultySettings;
import model.Hero;
import model.Map;

public class BossAI extends BasicAI {
	/**
	 * This AI gives the boss it's functionality
	 */
	private static final long serialVersionUID = -7442767905403148421L;
	private int shootingRange;
	private int shootingRate;
	private int count;
	private double xDiff;
	private double yDiff;

	private int teleportCount;
	private int msToTeleport;
	private boolean isTeleporting;
	protected Boss boss;

	private double width;
	private double height;

	protected BufferedImage imageSF;

	public BossAI(Map map, Hero hero, int shootingRate) {
		super(map, hero);
		this.shootingRange = 3 * DifficultySettings.getBaddyDetectionDistance();
		this.shootingRate = shootingRate;
		this.isTeleporting = false;
		this.teleportCount = 0;

		width = 33;
		height = 47;

	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}

	public Rectangle2D.Double update(Rectangle2D.Double body) {
		if (decideToTeleport(body) == false) {
			decideToShoot(body);
		}

		if (teleportCount == msToTeleport && isTeleporting == true) {
			Teleport(body);
			teleportCount++;

		} else if (teleportCount > msToTeleport * 3) {
			isTeleporting = false;
			teleportCount = -1;
			teleportCount = 0;
		} else {
			teleportCount++;
		}

		return movePosition(body);
	}

	public Rectangle2D.Double movePosition(Rectangle2D.Double body) {
		if (body != null && hero != null) {
			baddy.setMovingUp(false);
			baddy.setMovingDown(false);
			baddy.setMovingLeft(false);
			baddy.setMovingRight(false);
			speed = 0;
		}
		return body;
	}

	private boolean decideToTeleport(Rectangle2D.Double body) {
		xDiff = -body.x + hero.getBody().getX();
		yDiff = -body.y + hero.getBody().getY();
		if ((java.lang.Math.sqrt(java.lang.Math.pow(hero.getBody().getX() - body.x, 2)
				+ java.lang.Math.pow(hero.getBody().getY() - body.y, 2))) < 600) {
			if (isTeleporting == false) {
				TeleportTimer(body);
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	private void decideToShoot(Rectangle2D.Double body) {
		if (hero != null) {
			if ((java.lang.Math.sqrt(java.lang.Math.pow(hero.getBody().getX() - body.x, 2)
					+ java.lang.Math.pow(hero.getBody().getY() - body.y, 2))) < shootingRange) {
				if (count % shootingRate == 0) {
					xDiff = -body.x + hero.getBody().getX();
					yDiff = -body.y + hero.getBody().getY();
					fireShootEvent();
					count = 0;
				}
				count++;
			}
		} else {
		}
	}

	private void Teleport(Rectangle2D.Double body) {
		generateValidLocation(body);
		while (isObjObstructed(body) == true) {
			generateValidLocation(body);
		}
		baddy.setBody(body);
	}

	public void TeleportTimer(Rectangle2D.Double body) {
		boss.setSkinAlternator(0);
		boss.setTeleportStatus(2);
		isTeleporting = true;
		msToTeleport = 65;
	}

	public void addBoss(Boss boss) {
		this.boss = boss;
	}

	private void generateValidLocation(Rectangle2D.Double body) {
		int maxX = 984;
		int minX = 40;
		int maxY = 700;
		int minY = 40;
		double xLocation = (Math.random() * ((maxX - minX) + 1)) + minX;
		double yLocation = (Math.random() * ((maxY - minY) + 1)) + minY;
		body.setRect(xLocation, yLocation, width, height);

	}

	public void fireShootEvent() {
		if (this.listener != null) {
			double tmpVectorX = xDiff / Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			double tmpVectorY = yDiff / Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			listener.shoot(tmpVectorX, tmpVectorY);
		}
	}
}