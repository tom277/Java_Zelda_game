package model;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gui.SoundBoard;
import listeners.ProjectileListener;

public abstract class Being extends Tangible {

	/**
	 * Parent to hero and baddy and handles the general functionality of a being
	 */
	private static final long serialVersionUID = -769347017176164022L;

	protected BufferedImage imageSF;
	protected BufferedImage imageSB;
	protected BufferedImage imageSR;
	protected BufferedImage imageSL;
	protected BufferedImage imageWF1;
	protected BufferedImage imageWF2;
	protected BufferedImage imageWB1;
	protected BufferedImage imageWB2;
	protected BufferedImage imageWR1;
	protected BufferedImage imageWR2;
	protected BufferedImage imageWL1;
	protected BufferedImage imageWL2;

	protected double speed;
	private Rectangle2D.Double body;
	protected int health;
	protected boolean isMoving;

	protected boolean movingRight;
	protected boolean movingLeft;
	protected boolean movingUp;
	protected boolean movingDown;

	protected String name;

	private int skinAlternator;
	protected boolean wasMovingUp;// these are used to select the stationary image in the direction the being was
									// moving
	protected boolean wasMovingDown;
	protected boolean wasMovingRight;
	protected boolean wasMovingLeft;

	private ProjectileListener deathListener;
	Direction direction;
	protected Weapon weapon;
	protected ArrayList<Weapon> weapons;
	protected static int skinChangeDelay = 20;

	public Being(String name, int health, double speed, Rectangle2D.Double body) {
		weapons = new ArrayList<Weapon>();
		this.name = name;
		this.health = health;
		this.speed = speed;
		this.setBody(body);
		setSkinAlternator(0);
		this.isMoving = false;
	}

	public Image alternateSkin() {
		if (movingDown) {
			// alternate the skin to make look like it moving in the desired direction
			if (getSkinAlternator() % skinChangeDelay == 0) {
				imageCurrent = imageWF1;//
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 4) {
				imageCurrent = imageSF;
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 2) {
				imageCurrent = imageWF2;
			} else if (getSkinAlternator() % skinChangeDelay == (skinChangeDelay / 4) * 3) {
				imageCurrent = imageSF;
			}
			setSkinAlternator(getSkinAlternator() + 1);
			direction = new Direction(Direction.Dir.DOWN);
		} else if (movingUp) {
			if (getSkinAlternator() % skinChangeDelay == 0) {
				imageCurrent = imageWB1;
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 4) {
				imageCurrent = imageSB;
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 2) {
				imageCurrent = imageWB2;
			} else if (getSkinAlternator() % skinChangeDelay == (skinChangeDelay / 4) * 3) {
				imageCurrent = imageSB;
			}
			setSkinAlternator(getSkinAlternator() + 1);
			direction = new Direction(Direction.Dir.UP);
		} else if (movingRight) {
			if (getSkinAlternator() % skinChangeDelay == 0) {
				imageCurrent = imageWR1;
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 4) {
				imageCurrent = imageSR;
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 2) {
				imageCurrent = imageWR2;
			} else if (getSkinAlternator() % skinChangeDelay == (skinChangeDelay / 4) * 3) {
				imageCurrent = imageSR;
			}
			setSkinAlternator(getSkinAlternator() + 1);
			direction = new Direction(Direction.Dir.RIGHT);
		} else if (movingLeft) {
			if (getSkinAlternator() % skinChangeDelay == 0) {
				imageCurrent = imageWL1;
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 4) {
				imageCurrent = imageSL;
			} else if (getSkinAlternator() % skinChangeDelay == skinChangeDelay / 2) {
				imageCurrent = imageWL2;
			} else if (getSkinAlternator() % skinChangeDelay == (skinChangeDelay / 4) * 3) {
				imageCurrent = imageSL;
			}
			setSkinAlternator(getSkinAlternator() + 1);
			direction = new Direction(Direction.Dir.LEFT);
		} else {
			// set the stationary skin
			if (wasMovingUp) {
				imageCurrent = imageSB;
				direction = new Direction(Direction.Dir.UP);
			} else if (wasMovingDown) {
				imageCurrent = imageSF;
				direction = new Direction(Direction.Dir.DOWN);
			} else if (wasMovingLeft) {
				imageCurrent = imageSL;
				direction = new Direction(Direction.Dir.LEFT);
			} else if (wasMovingRight) {
				imageCurrent = imageSR;
				direction = new Direction(Direction.Dir.RIGHT);
			}

			setSkinAlternator(0);
		}
		return imageCurrent;
	}

	public void setWeapon(Weapon weapon, boolean justPurchased) {
		weapons.add(weapon);
		this.weapon = weapon;
		if (justPurchased == true) {
			SoundBoard.playSound("Whoosh quick 2.wav");
		}
	}

	public void update() {
		if (weapon != null) {
			weapon.update();
		}
		alternateSkin();
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		if (!movingRight && this.movingRight) {
			wasMovingLeft = false;
			wasMovingRight = true;
			wasMovingUp = false;
			wasMovingDown = false;
		}
		this.movingRight = movingRight;
	}

	public void fireWeapon() {
		if (weapon != null) {
			if (isMoving) {
				weapon.launchProjectile((int) getBody().getX(), (int) getBody().getY(), direction, speed);
			} else {
				weapon.launchProjectile((int) getBody().getX(), (int) getBody().getY(), direction, 0);
			}

		}
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setMovingLeft(boolean movingLeft) {
		if (!movingLeft && this.movingLeft) {
			wasMovingLeft = true;
			wasMovingRight = false;
			wasMovingUp = false;
			wasMovingDown = false;
		}
		this.movingLeft = movingLeft;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		if (!movingUp && this.movingUp) {
			wasMovingLeft = false;
			wasMovingRight = false;
			wasMovingUp = true;
			wasMovingDown = false;
		}
		this.movingUp = movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		if (!movingDown && this.movingDown) {
			wasMovingLeft = false;
			wasMovingRight = false;
			wasMovingUp = false;
			wasMovingDown = true;
		}
		this.movingDown = movingDown;
	}

	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void damage(int damage) {
		health -= damage;
		if (health < 0) {
			fireDeathEvent();
		}
	}

	public void makeInvincible() {
		health = 10000;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public double getSpeed() {
		return speed;
	}

	public Rectangle2D.Double getBody() {
		return body;
	}

	protected void fireDeathEvent() {
		if (deathListener != null) {
			deathListener.DeleteProjectile();
		}
	}

	public void setDeathListener(ProjectileListener deathListener) {
		this.deathListener = deathListener;
	}

	public void setBody(Rectangle2D.Double body) {
		this.body = body;
	}

	public int getSkinAlternator() {
		return skinAlternator;
	}

	public void setSkinAlternator(int skinAlternator) {
		this.skinAlternator = skinAlternator;
	}
}