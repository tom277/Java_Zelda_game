package model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import listeners.ProjectileListener;

public class Projectile extends Weapon {
	/**
	 * creates and handles the projectile movement
	 */
	private static final long serialVersionUID = 4051115965369158037L;
	protected Rectangle2D.Double body;
	private double speed;
	private int counter;
	private int maxDistance;
	private ProjectileListener listener;
	private BufferedImage up;
	private final int offset = 0;
	private double xVector;
	private double yVector;

	// This projectile constructor is for regular projectiles (going up, down, left, or right)
	public Projectile(int damage, Direction direction, double speed, int x, int y, int maxDistance, BufferedImage img) {
		super(damage);
		this.speed = speed;
		this.maxDistance = maxDistance;
		body = new Rectangle2D.Double(x - offset, y - offset, 12, 12);
		counter = 0;
		this.up = img;
		if (direction != null) {
			if (direction.getDirection() == Direction.Dir.RIGHT) {
				this.xVector = 1;
				this.yVector = 0;
				imageCurrent = MyUtils.rotateImageByDegrees(up, 90);
			} else if (direction.getDirection() == Direction.Dir.LEFT) {
				this.xVector = -1;
				this.yVector = 0;
				imageCurrent = MyUtils.rotateImageByDegrees(up, 270);
			} else if (direction.getDirection() == Direction.Dir.DOWN) {
				this.xVector = 0;
				this.yVector = 1;
				imageCurrent = MyUtils.rotateImageByDegrees(up, 180);
				;
			} else {
				this.xVector = 0;
				this.yVector = -1;
				imageCurrent = up;
			}
		}
	}

	// This projectile constructor is for projectiles which are diagonal in some form
	public Projectile(int damage, double xVector, double yVector, double speed, int x, int y, int maxDistance, BufferedImage img) {
		super(damage);
		this.speed = speed;
		this.xVector = xVector;
		this.yVector = yVector;
		this.maxDistance = maxDistance;
		body = new Rectangle2D.Double(x - offset, y - offset, 10, 10);
		counter = 0;
		this.up = img;
		double degrees = Math.abs(Math.toDegrees(Math.atan(xVector / yVector)));
		if (xVector > 0 && yVector > 0) {
			degrees = 180 - Math.abs(Math.toDegrees(Math.atan(xVector / yVector)));
		} else if (xVector > 0 && yVector < 0) {
			degrees = Math.abs(Math.toDegrees(Math.atan(xVector / yVector)));
		} else if (xVector < 0 && yVector > 0) {
			degrees = 180 + Math.abs(Math.toDegrees(Math.atan(xVector / yVector)));
		} else if (xVector < 0 && yVector < 0) {
			degrees = 360 - Math.abs(Math.toDegrees(Math.atan(xVector / yVector)));
		}
		imageCurrent = MyUtils.rotateImageByDegrees(up, degrees);
	}

	public Rectangle2D.Double getBody() {
		return body;
	}

	public void update() {
		body.x += xVector * speed;
		body.y += yVector * speed;

		counter += speed;
		if (counter >= maxDistance) {
			fireDeleteProjectile();
		}
	}

	public void setListener(ProjectileListener listener) {
		this.listener = listener;
	}

	private void fireDeleteProjectile() {
		if (listener != null) {
			listener.DeleteProjectile();
		}
	}

	public BufferedImage getImageCurrent() {
		return imageCurrent;
	}
}
