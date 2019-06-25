package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.BossAI;
import gui.SoundBoard;
import listeners.AIShootListener;

public class Boss extends Baddy {
	/**s
	 * Boss class
	 */
	private static final long serialVersionUID = 5288322387995183442L;
	public static int STANDARD_HEALTH = DifficultySettings.getBaddyHealth();
	public static int STANDARD_SPEED = DifficultySettings.getMobSpeed();

	private BossAI ai;
	private int damage;

	private BufferedImage imageS1;
	private BufferedImage imageS2;
	private BufferedImage imageS3;
	private BufferedImage imageS4;
	private BufferedImage imageD1;
	private BufferedImage imageD2;
	private BufferedImage imageD3;
	private BufferedImage imageD4;
	private BufferedImage imageD5;
	private BufferedImage imageD6;
	private BufferedImage imageD7;

	private int teleportStatus = 1;
	public static final int POSING = 1;
	public static final int DISAPPEARING = 2;
	public static final int APPEARING = 3;

	public Boss(BossAI ai, int type, int x, int y) {
		super(ai, type, x, y);// add body
		this.ai = ai;
		if (ai != null) {
			ai.addBoss(this);
			ai.setAIShootListener(new AIShootListener() {
				public void shoot(double xVector, double yVector) {
					fireWeapon(xVector, yVector);
				}
			});
			ai.setSpeed(this.speed);
		}
		damage = 1;
		health = 10;
		teleportStatus = POSING;
		loadBossImages();
		imageCurrent = imageS1;
		this.setWeapon(new Weapon(Weapon.RING_6), false);
	}

	public void loadBossImages() {
		try {
			double s = 1.2;
			imageS1 = ImageIO.read(getClass().getResource("/BlueWizard_S1.png"));
			imageS1 = MyUtils.toBufferedImage(imageS1.getScaledInstance((int)(imageS1.getWidth() * s), (int)(imageS1.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageS2 = ImageIO.read(getClass().getResource("/BlueWizard_S2.png"));
			imageS2 = MyUtils.toBufferedImage(imageS2.getScaledInstance((int)(imageS2.getWidth() * s), (int)(imageS2.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageS3 = ImageIO.read(getClass().getResource("/BlueWizard_S3.png"));
			imageS3 = MyUtils.toBufferedImage(imageS3.getScaledInstance((int)(imageS3.getWidth() * s), (int)(imageS3.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageS4 = ImageIO.read(getClass().getResource("/BlueWizard_S4.png"));
			imageS4 = MyUtils.toBufferedImage(imageS4.getScaledInstance((int)(imageS4.getWidth() * s), (int)(imageS4.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageD1 = ImageIO.read(getClass().getResource("/BlueWizard_D1.png"));
			imageD1 = MyUtils.toBufferedImage(imageD1.getScaledInstance((int)(imageD1.getWidth() * s), (int)(imageD1.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageD2 = ImageIO.read(getClass().getResource("/BlueWizard_D2.png"));
			imageD2 = MyUtils.toBufferedImage(imageD2.getScaledInstance((int)(imageD2.getWidth() * s), (int)(imageD2.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageD3 = ImageIO.read(getClass().getResource("/BlueWizard_D3.png"));
			imageD3 = MyUtils.toBufferedImage(imageD3.getScaledInstance((int)(imageD3.getWidth() * s), (int)(imageD3.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageD4 = ImageIO.read(getClass().getResource("/BlueWizard_D4.png"));
			imageD4 = MyUtils.toBufferedImage(imageD4.getScaledInstance((int)(imageD4.getWidth() * s), (int)(imageD4.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageD5 = ImageIO.read(getClass().getResource("/BlueWizard_D5.png"));
			imageD5 = MyUtils.toBufferedImage(imageD5.getScaledInstance((int)(imageD5.getWidth() * s), (int)(imageD5.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageD6 = ImageIO.read(getClass().getResource("/BlueWizard_D6.png"));
			imageD6 = MyUtils.toBufferedImage(imageD6.getScaledInstance((int)(imageD6.getWidth() * s), (int)(imageD6.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
			imageD7 = ImageIO.read(getClass().getResource("/BlueWizard_D7.png"));
			imageD7 = MyUtils.toBufferedImage(imageD7.getScaledInstance((int)(imageD7.getWidth() * s), (int)(imageD7.getHeight() * s), BufferedImage.SCALE_AREA_AVERAGING));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// @override
	public Image alternateSkin() {
		// alternate the skin to make look like it is moving the staff up and down
		if (teleportStatus == DISAPPEARING) {
			int framesElapsed = 66;
			int numOfImages = 11;
			int iterator = framesElapsed / numOfImages;
			// alternate the skin to make look like it moving in the desired direction
			if (getSkinAlternator() % framesElapsed == iterator * 0) {
				imageCurrent = imageS1;//
			} else if (getSkinAlternator() % framesElapsed == iterator * 1) {
				imageCurrent = imageS2;
			} else if (getSkinAlternator() % framesElapsed == iterator * 2) {
				imageCurrent = imageS3;
			} else if (getSkinAlternator() % framesElapsed == iterator * 3) {
				imageCurrent = imageS4;
			} else if (getSkinAlternator() % framesElapsed == iterator * 4) {
				imageCurrent = imageD1;
			} else if (getSkinAlternator() % framesElapsed == iterator * 5) {
				imageCurrent = imageD2;
			} else if (getSkinAlternator() % framesElapsed == iterator * 6) {
				imageCurrent = imageD3;
			} else if (getSkinAlternator() % framesElapsed == iterator * 7) {
				imageCurrent = imageD4;
			} else if (getSkinAlternator() % framesElapsed == iterator * 8) {
				imageCurrent = imageD5;
			} else if (getSkinAlternator() % framesElapsed == iterator * 9) {
				imageCurrent = imageD6;
			} else if (getSkinAlternator() % framesElapsed == iterator * 10) {
				imageCurrent = imageD7;
				setSkinAlternator(0);
				teleportStatus = APPEARING;
			}
			setSkinAlternator(getSkinAlternator() + 1);
			if (getSkinAlternator() == framesElapsed) {
				setSkinAlternator(0);
			}
		}
		if (teleportStatus == APPEARING) {
			int framesElapsed = 66;
			int numOfImages = 11;
			int iterator = framesElapsed / numOfImages;
			// alternate the skin to make look like it moving in the desired direction
			if (getSkinAlternator() % framesElapsed == iterator * 0) {
				imageCurrent = imageD7;//
			} else if (getSkinAlternator() % framesElapsed == iterator * 1) {
				imageCurrent = imageD6;
			} else if (getSkinAlternator() % framesElapsed == iterator * 2) {
				imageCurrent = imageD5;
			} else if (getSkinAlternator() % framesElapsed == iterator * 3) {
				imageCurrent = imageD4;
			} else if (getSkinAlternator() % framesElapsed == iterator * 4) {
				imageCurrent = imageD3;
			} else if (getSkinAlternator() % framesElapsed == iterator * 5) {
				imageCurrent = imageD2;
			} else if (getSkinAlternator() % framesElapsed == iterator * 6) {
				imageCurrent = imageD1;
			} else if (getSkinAlternator() % framesElapsed == iterator * 7) {
				imageCurrent = imageS4;
			} else if (getSkinAlternator() % framesElapsed == iterator * 8) {
				imageCurrent = imageS3;
			} else if (getSkinAlternator() % framesElapsed == iterator * 9) {
				imageCurrent = imageS2;
			} else if (getSkinAlternator() % framesElapsed == iterator * 10) {
				imageCurrent = imageS1;
				teleportStatus = POSING;
				setSkinAlternator(0);
			}
			setSkinAlternator(getSkinAlternator() + 1);
			if (getSkinAlternator() == framesElapsed) {
				setSkinAlternator(0);
			}
		} else if (teleportStatus == POSING) { // if doing normal stationary animation
			// set the stationary skin
			int framesElapsed = 70;
			int numOfImages = 7;
			int iterator = framesElapsed / numOfImages;
			if (getSkinAlternator() % framesElapsed == iterator * 0) {
				imageCurrent = imageS1;
			} else if (getSkinAlternator() % framesElapsed == iterator * 1) {
				imageCurrent = imageS2;
			} else if (getSkinAlternator() % framesElapsed == iterator * 2) {
				imageCurrent = imageS3;
			} else if (getSkinAlternator() % framesElapsed == iterator * 3) {
				imageCurrent = imageS4;
			} else if (getSkinAlternator() % framesElapsed == iterator * 4) {
				imageCurrent = imageS3;
			} else if (getSkinAlternator() % framesElapsed == iterator * 5) {
				imageCurrent = imageS2;
			} else if (getSkinAlternator() % framesElapsed == iterator * 6) {
				imageCurrent = imageS1;
			}
			setSkinAlternator(getSkinAlternator() + 1);
			if (getSkinAlternator() == framesElapsed) {
				setSkinAlternator(0);
			}
		}
		return imageCurrent;
	}
	
	public void setTeleportStatus(int status) {
		teleportStatus = status;
	}

	public BossAI getAI() {
		return ai;
	}

	public void addAI(BossAI ai) {
		this.ai = ai;
		if (ai != null) {
			ai.addBoss(this);
			ai.setAIShootListener(new AIShootListener() {
				public void shoot(double xVector, double yVector) {
					fireWeapon(xVector, yVector);
				}
			});
			ai.setSpeed(speed);
		}
	}

	public int getDamage() {
		return damage;
	}

	public void update() {
		if (ai != null) {
			ai.update(getBody());
		}
		if (weapon != null) {
			weapon.update();
		}
		alternateSkin();
	}

	@Override
	public void damage(int damage) {
		health -= damage;
		if (health <= 0) {
			// fire death event
			fireDeathEvent();
		}
		SoundBoard.playSound("Arcane_ImpactF1.wav");
	}
}