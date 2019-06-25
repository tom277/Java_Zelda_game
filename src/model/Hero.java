package model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.HeroMovement;
import gui.SoundBoard;
import listeners.PlayerEvent;
import listeners.PlayerEventListener;
import listeners.PlayerListener;

public class Hero extends Being {

	/**
	 * Creates the hero and holds hero related information
	 */
	private static final long serialVersionUID = 6499516924637910453L;
	public static int STANDARD_HEALTH = 6;
	public static int STANDARD_SPEED = 1;
	public static int STEVE = 0;
	public static int TYPE1 = 1;
	public static int TYPE2 = 2;
	int coins;
	int enemiesKilled;
	private PlayerListener playerListener;
	private PlayerEventListener playerUpdateListener;
	private boolean allowDamage;
	private int dmgCounter;
	private int timeBetweenDamage;
	private HeroMovement movement;
	private BufferedImage redScreen;
	private BufferedImage tmpRedScreen;
	private int count;
	private int initialHealth;
	private int speedCounter;
	private boolean speedUp;
	private double speedBoost;

	public Hero(String name, HeroMovement movement, int type) {
		super(name, 6, 0.7, new Rectangle2D.Double(200, 200, 25, 28));
		initialHealth = 6;
		this.health = initialHealth;
		this.movement = movement;
		speedUp = false;
		if (movement != null) {
			movement.setHero(this);
		}
		if (type == STEVE) {
			loadImage();
		} else {
			loadImageType1(type);
		}

		weapons = new ArrayList<Weapon>();
		coins = 0;
		imageCurrent = imageSF;
		offsetX = -2;
		offsetY = -2;
		timeBetweenDamage = DifficultySettings.getInvinsibilityTime();
		allowDamage = true;
		try {
			tmpRedScreen = ImageIO.read(getClass().getResource("/redScreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		redScreen = tmpRedScreen;
	}

	public BufferedImage getRedScreen() {
		return redScreen;
	}

	public void setHeroMovement(HeroMovement movement) {
		this.movement = movement;
		if (movement != null) {
			movement.setHero(this);
		}
	}

	public void setTimeBetweenDamage() {
		timeBetweenDamage = DifficultySettings.getInvinsibilityTime();
	}

	public void decrementCoins(int price) {
		coins -= price;
		firePlayerEvent();
	}

	public int getCoins() {
		return coins;
	}

	public void incrementCoins() {
		coins++;
		firePlayerEvent();
		SoundBoard.playSound("bleep03.wav");
	}

	public void get1000Coins() {
		coins = 1000;
		firePlayerEvent();
	}

	public void addOrb() {
		firePlayerVictoryEvent();
		SoundBoard.UpdateMusic("Happily ever After.wav", false);
	}

	private void loadImage() {
		try {
			imageSF = ImageIO.read(getClass().getResource("/georgeSF.png"));
			imageSB = ImageIO.read(getClass().getResource("/georgeSB.png"));
			imageSL = ImageIO.read(getClass().getResource("/georgeSL.png"));
			imageSR = ImageIO.read(getClass().getResource("/georgeSR.png"));
			imageWB1 = ImageIO.read(getClass().getResource("/georgeWB1.png"));
			imageWB2 = ImageIO.read(getClass().getResource("/georgeWB2.png"));
			imageWF1 = ImageIO.read(getClass().getResource("/georgeWF1.png"));
			imageWF2 = ImageIO.read(getClass().getResource("/georgeWF2.png"));
			imageWL1 = ImageIO.read(getClass().getResource("/georgeWL1.png"));
			imageWL2 = ImageIO.read(getClass().getResource("/georgeWL2.png"));
			imageWR1 = ImageIO.read(getClass().getResource("/georgeWR2.png"));
			imageWR2 = ImageIO.read(getClass().getResource("/georgeWR1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadImageType1(int type) {
		String folder = "Hero1";
		if (type == TYPE1) {
			folder = "Hero1";
		} else if (type == TYPE2) {
			folder = "Hero2";
		}
		try {
			imageSF = ImageIO.read(getClass().getResource("/" + folder + "/SF.png"));
			imageSB = ImageIO.read(getClass().getResource("/" + folder + "/SB.png"));
			imageSL = ImageIO.read(getClass().getResource("/" + folder + "/SL.png"));
			imageSR = ImageIO.read(getClass().getResource("/" + folder + "/SR.png"));
			imageWB1 = ImageIO.read(getClass().getResource("/" + folder + "/WB1.png"));
			imageWB2 = ImageIO.read(getClass().getResource("/" + folder + "/WB2.png"));
			imageWF1 = ImageIO.read(getClass().getResource("/" + folder + "/WF1.png"));
			imageWF2 = ImageIO.read(getClass().getResource("/" + folder + "/WF2.png"));
			imageWL1 = ImageIO.read(getClass().getResource("/" + folder + "/WL1.png"));
			imageWL2 = ImageIO.read(getClass().getResource("/" + folder + "/WL2.png"));
			imageWR1 = ImageIO.read(getClass().getResource("/" + folder + "/WR2.png"));
			imageWR2 = ImageIO.read(getClass().getResource("/" + folder + "/WR1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void firePlayerDeathEvent() {
		if (playerListener != null) {
			int moneyInWeapons = 0;
			for (Weapon tmpWeapon : weapons) {
				moneyInWeapons += tmpWeapon.getPrice();
			}
			playerListener.playerDeath(new PlayerEvent(coins, enemiesKilled, health, moneyInWeapons, false));
		}
	}

	private void firePlayerVictoryEvent() {
		if (playerListener != null) {
			int moneyInWeapons = 0;
			for (Weapon tmpWeapon : weapons) {
				moneyInWeapons += tmpWeapon.getPrice();
			}
			playerListener.playerWin(new PlayerEvent(coins, enemiesKilled, health, moneyInWeapons, true));
			;
		}
	}

	public void EnterEndGame() {
		// give the player all the means to finish the game quickly

		this.setWeapon(new Weapon(Weapon.RING_4), false);
		this.initialHealth = 1000;
		makeInvincible();
		get1000Coins();

		// load the last map
		movement.loadFinalMap();

		// teleport them to the last map
		this.getBody().x = 480;
		this.getBody().y = 80;
	}

	public void addEnemiesKilled() {
		enemiesKilled++;
	}

	public void setDeathEventListener(PlayerListener event) {
		this.playerListener = event;
	}

	@Override
	public void damage(int damage) {
		if (allowDamage) {
			health -= damage;
			if (health <= 0) {
				// fire death event
				firePlayerDeathEvent();
			}
			firePlayerEvent();
			allowDamage = false;
			SoundBoard.playSound("Roblox-death-sound.wav");
			count = 0;
			redScreen = tmpRedScreen;
		}
	}

	public void healthPickedUp(int health) {
		if (this.health < this.initialHealth) {
			this.health += health;
		} else {
			this.health = initialHealth;
		}
		firePlayerEvent();
	}

	private void firePlayerEvent() {
		if (playerUpdateListener != null) {
			playerUpdateListener.playerUpdate(new PlayerEvent(coins, enemiesKilled, health, 0, false));
		}
	}

	public void setUpdateEventListener(PlayerEventListener event) {
		this.playerUpdateListener = event;
	}

	public void addPickUp(Object pickUp) {
		if (pickUp instanceof Coin) {
			incrementCoins();
		} else if (pickUp instanceof PickUpRing) {
			PickUpRing tmp = (PickUpRing) pickUp;
			setWeapon(tmp.getWeapon(), true);
		} else if (pickUp instanceof HealthPickUp) {
			healthPickedUp(1);
		} else if (pickUp instanceof OrbPickUp) {
			addOrb();
		} else if (pickUp instanceof CandyPickUp) {
			if (!speedUp) {
				this.speedCounter = 0;
				this.speedUp = true;
				this.speedBoost = ((CandyPickUp) pickUp).getSpeedBoost();
				this.speed = speed + speedBoost;
				SoundBoard.playSound("running_feet.wav");
			} else {
				this.speedCounter = 0;
			}
		}

	}

	public void update() {
		super.update();
		if (redScreen != null) {
			count++;
			if (count > 10) {
				redScreen = null;
			}
		}
		if (!allowDamage) {
			dmgCounter++;
			if (dmgCounter % timeBetweenDamage == 0) {
				dmgCounter = 0;
				allowDamage = true;
			}
		}
		if (movement != null) {
			movement.update();
		}
		if (speedUp) {
			speedCounter++;
			if (speedCounter > 250) {
				speedCounter = 0;
				speedUp = false;
				speed = speed - speedBoost;
			}
		}
	}
}