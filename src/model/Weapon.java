package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import gui.SoundBoard;
import listeners.ProjectileListener;

public class Weapon extends Object {
	/**
	 * Weapon creates the different rings with the different behaviours
	 */
	private static final long serialVersionUID = -7620603105641761332L;
	private int damage;
	// weapon needs a rate at which to shoot projectiles
	int counter;
	int maxNumOfProjectiles;
	int price;
	int shootingCoolDownCount;

	private ArrayList<Projectile> projectiles;

	public static final int RING_1 = 0;
	public static final int RING_2 = 1;
	public static final int RING_3 = 2;
	public static final int RING_4 = 3;
	public static final int RING_5 = 4;
	public static final int RING_6 = 5;

	BufferedImage projectileImg;

	private String name;
	private String description;
	private String attackSoundString;
	private double speed;
	private int range;

	// Instantiates the weapon types
	public Weapon(int type) {
		shootingCoolDownCount = 10000;
		if (type == RING_1) {
			try {
				projectileImg = ImageIO.read(getClass().getResource("/bullets/1.png"));
			} catch (IOException e) {
			}
			this.damage = 2;
			projectiles = new ArrayList<>();
			maxNumOfProjectiles = 1;
			loadImage("/Ring1.png");
			price = 5;
			name = "\"Pea-shooter\" ring";
			description = "You already have this ring";
			attackSoundString = "whoosh.wav";
			speed = 4;
			range = 200;
		} else if (type == RING_2) {
			try {
				projectileImg = ImageIO.read(getClass().getResource("/bullets/5.png"));
			} catch (IOException e) {
			}
			this.damage = 3;
			projectiles = new ArrayList<>();
			maxNumOfProjectiles = 1;
			loadImage("/Ring3.png");
			name = "Ring of Embers";
			attackSoundString = "whoosh.wav";
			price = 10;
			speed = 5;
			range = 250;
			description = "A more powerful ring for amateur spellcasters";
		} else if (type == RING_3) {
			try {
				projectileImg = ImageIO.read(getClass().getResource("/bullets/4.png"));
			} catch (IOException e) {
			}
			this.damage = 5;
			projectiles = new ArrayList<>();
			maxNumOfProjectiles = 1;
			loadImage("/Ring2.png");
			name = "Cursed Ring";
			attackSoundString = "whoosh.wav";
			price = 40;
			range = 300;
			description = "A very powerful ring for master spellcasters";
			speed = 6;
		} else if (type == RING_4) {
			try {
				projectileImg = ImageIO.read(getClass().getResource("/bullets/8.png"));
				projectileImg = MyUtils
						.toBufferedImage(projectileImg.getScaledInstance((int) (projectileImg.getWidth() * 1.2),
								(int) (projectileImg.getHeight() * 1.2), BufferedImage.SCALE_AREA_AVERAGING));
			} catch (IOException e) {
			}
			this.damage = 10;
			projectiles = new ArrayList<>();
			maxNumOfProjectiles = 1;
			loadImage("/Ring4.png");
			name = "Ring of Destruction";
			attackSoundString = "Fire_ImpactF1.wav";
			description = "With great power, comes great responsibility";
			price = 100;
			range = 550;
			speed = 7;
		} else if (type == RING_5) {
			try {
				projectileImg = ImageIO.read(getClass().getResource("/bullets/3.png"));
			} catch (IOException e) {
			}
			this.damage = 1;
			projectiles = new ArrayList<>();
			maxNumOfProjectiles = 1;
			loadImage("/Ring1.png");
			name = "";
			attackSoundString = "whoosh.wav";
			description = "";
			price = 1;
			range = DifficultySettings.getBaddyWeaponRange();
			speed = DifficultySettings.getMobProjectileSpeed();
		} else if (type == RING_6) { // Ring 6 is for the boss
			try {
				projectileImg = ImageIO.read(getClass().getResource("/bullets/2.png"));
				projectileImg = MyUtils
						.toBufferedImage(projectileImg.getScaledInstance((int) (projectileImg.getWidth() * 1.5),
								(int) (projectileImg.getHeight() * 1.5), BufferedImage.SCALE_AREA_AVERAGING));
			} catch (IOException e) {
			}
			this.damage = 1;
			projectiles = new ArrayList<>();
			maxNumOfProjectiles = 1;
			loadImage("/Ring1.png");
			name = "";
			description = "";
			attackSoundString = "Fire_ImpactF1.wav";
			price = 1;
			range = DifficultySettings.getBaddyWeaponRange();
			speed = DifficultySettings.getMobProjectileSpeed();
		}
	}

	public int getMaxNumOfProjectiles() {
		return maxNumOfProjectiles;
	}

	public String getName() {
		return name;
	}

	public double getSpeed() {
		return speed;
	}

	public int getRange() {
		return range;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public String getWeaponName() {
		return name;
	}

	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public int getDamage() {
		return damage;
	}

	public void launchProjectile(int x, int y, Direction direction, double speed) {
		if (shootingCoolDownCount > 40) {
			shootingCoolDownCount = 0;
			SoundBoard.playSound(attackSoundString);
			Projectile pj = new Projectile(1, direction, this.speed + speed, x, y, (int) (range + speed * 4),
					projectileImg);
			pj.setListener(new ProjectileListener() {
				public void DeleteProjectile() {
					projectiles.remove(pj);
				}
			});
			projectiles.add(pj);
		}
	}

	public void launchProjectile(int x, int y, double xVector, double yVector) {
		if (projectiles.size() < maxNumOfProjectiles) {
			SoundBoard.playSound(attackSoundString);
			Projectile pj = new Projectile(1, xVector, yVector, this.speed, x, y, (int) (range), projectileImg);
			pj.setListener(new ProjectileListener() {
				public void DeleteProjectile() {
					projectiles.remove(pj);
				}
			});
			projectiles.add(pj);
		}
	}

	public void update() {
		shootingCoolDownCount++;
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
	}

	private void loadImage(String path) {
		try {
			super.imageCurrent = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}