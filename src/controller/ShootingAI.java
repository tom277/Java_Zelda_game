package controller;

import java.awt.geom.Rectangle2D;

import model.DifficultySettings;
import model.Hero;
import model.Map;

public class ShootingAI extends BasicAI {
	/**
	 * This class extends shooting ai by adding the shoot functionality to the
	 * baddies
	 */
	private static final long serialVersionUID = -7442767905403148421L;
	private int shootingRange;
	private int shootingRate;
	private int count;
	private double xDiff;
	private double yDiff;

	public ShootingAI(Map map, Hero hero, int shootingRate) {
		super(map, hero);
		this.shootingRange = DifficultySettings.getBaddyDetectionDistance();
		this.shootingRate = shootingRate;
	}

	public Rectangle2D.Double update(Rectangle2D.Double body) {
		decideToShoot(body);
		return movePosition(body);
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

	public void fireShootEvent() {
		if (this.listener != null) {
			double tmpVectorX = xDiff / Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			double tmpVectorY = yDiff / Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			listener.shoot(tmpVectorX, tmpVectorY);
		}
	}
}