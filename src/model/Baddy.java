package model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import controller.BasicAI;
import gui.SoundBoard;
import listeners.AIShootListener;

public class Baddy extends Being{
	/**
	 * This creates a mob
	 */
	private static final long serialVersionUID = 5288322387995183442L;
	public static int STANDARD_HEALTH = DifficultySettings.getBaddyHealth();
	public static int STANDARD_SPEED = DifficultySettings.getMobSpeed();
	public static final int GHOST = 0;
	public static final int FOREST_WIZARD = 1;
	public static final int BLUE_WIZARD = 2;
	

	private BasicAI ai;
	private int damage;
	private final int chance = DifficultySettings.getProbabilityOfHealthDrop();
	private BufferedImage sheet;
	
	private int type;
	

	public Baddy(BasicAI ai, int type, int x, int y) {
		super("baddy", 1, 1, null);//add body
		this.type = type;
		if(type == GHOST) {
			this.health = DifficultySettings.getBaddyHealth();
			this.speed = DifficultySettings.getMobSpeed();
			this.setBody(new Rectangle2D.Double(x, y, 25, 30));
			loadGhostImages();
		}else if(type == FOREST_WIZARD) {
			this.health = DifficultySettings.getBaddyHealth();
			this.speed = DifficultySettings.getMobSpeed() - 0.3;
			this.setBody(new Rectangle2D.Double(x, y, 25, 40));
			loadForestWizardImages();
		}else if(type == BLUE_WIZARD) {
			this.health = DifficultySettings.getBaddyHealth();
			this.speed = DifficultySettings.getMobSpeed();
			this.setBody(new Rectangle2D.Double(x, y, 30, 30));
			loadBlueWizardImages(); 
		}
		
		this.ai = ai;
		if (ai != null) {
			ai.addBaddy(this);
			ai.setAIShootListener(new AIShootListener() {
				public void shoot(double xVector, double yVector) {
					fireWeapon(xVector, yVector);
				}
			});
			ai.setSpeed(this.speed);
		}
		damage = 1;
		imageCurrent = imageSF;
	}

	private void loadBlueWizardImages() {
	}
	
	public int getType() {
		return type;
	}

	private void loadForestWizardImages() {
		try {
			int sheetImageWidth = 50;
			int sheetImageHeight = 90;

			sheet = ImageIO.read(getClass().getResource("/ForestWizard.png"));
			imageSF = ImageIO.read(getClass().getResource("/ForestWizard1.png"));

			imageSF = MyUtils.toBufferedImage(imageSF.getScaledInstance((int) (sheetImageWidth * 0.5),
					(int) (sheetImageHeight * 0.5), BufferedImage.SCALE_AREA_AVERAGING));

			imageSB = sheet.getSubimage(370, 6, 50, 90);

			imageSB = MyUtils.toBufferedImage(imageSB.getScaledInstance((int) (sheetImageWidth * 0.5),
					(int) (sheetImageHeight * 0.5), BufferedImage.SCALE_AREA_AVERAGING));

			imageSL = sheet.getSubimage(180, 100, 50, 80);

			imageSL = MyUtils.toBufferedImage(imageSL.getScaledInstance((int) (sheetImageWidth * 0.5),
					(int) (sheetImageHeight * 0.5), BufferedImage.SCALE_AREA_AVERAGING));

			imageSR = sheet.getSubimage(250, 190, 50, 80);

			imageSR = MyUtils.toBufferedImage(imageSR.getScaledInstance((int) (sheetImageWidth * 0.5),
					(int) (sheetImageHeight * 0.5), BufferedImage.SCALE_AREA_AVERAGING));

			imageWB1 = sheet.getSubimage(370, 6, 50, 90);

			imageWB1 = MyUtils.toBufferedImage(imageWB1.getScaledInstance((int) (sheetImageWidth * 0.5),
					(int) (sheetImageHeight * 0.5), BufferedImage.SCALE_AREA_AVERAGING));

			imageWB2 = sheet.getSubimage(427, 6, 50, 90);

			imageWB2 = MyUtils.toBufferedImage(imageWB2.getScaledInstance((int) (sheetImageWidth * 0.5),
					(int) (sheetImageHeight * 0.5), BufferedImage.SCALE_AREA_AVERAGING));

			imageWF1 = ImageIO.read(getClass().getResource("/ForestWizardWF2.png"));

			imageWF1 = MyUtils.toBufferedImage(imageWF1.getScaledInstance((int) (sheetImageWidth * 0.5), (int) (sheetImageHeight * 0.5),
					BufferedImage.SCALE_AREA_AVERAGING));

			imageWF2 = ImageIO.read(getClass().getResource("/ForestWizardWF3.png"));

			imageWF2 = MyUtils.toBufferedImage(imageWF2.getScaledInstance((int) (sheetImageWidth * 0.5), (int) (sheetImageHeight * 0.5),
					BufferedImage.SCALE_AREA_AVERAGING));

			imageWL1 = sheet.getSubimage(180, 100, 50, 80);

			imageWL1 = MyUtils.toBufferedImage(imageWL1.getScaledInstance((int) (sheetImageWidth * 0.5), (int) (sheetImageHeight * 0.5),
					BufferedImage.SCALE_AREA_AVERAGING));

			imageWL2 = sheet.getSubimage(365, 100, 50, 80);

			imageWL2 = MyUtils.toBufferedImage(imageWL2.getScaledInstance((int) (sheetImageWidth * 0.5), (int) (sheetImageHeight * 0.5),
					BufferedImage.SCALE_AREA_AVERAGING));

			imageWR1 = sheet.getSubimage(250, 190, 50, 80);

			imageWR1 = MyUtils.toBufferedImage(imageWR1.getScaledInstance((int) (sheetImageWidth * 0.5), (int) (sheetImageHeight * 0.5),
					BufferedImage.SCALE_AREA_AVERAGING));

			imageWR2 = sheet.getSubimage(70, 190, 50, 80);

			imageWR2 = MyUtils.toBufferedImage(imageWR2.getScaledInstance((int) (sheetImageWidth * 0.5), (int) (sheetImageHeight * 0.5),
					BufferedImage.SCALE_AREA_AVERAGING));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadGhostImages() {
		try {
			int sheetImageWidth = 12;
			int sheetImageHeight = 16;

			sheet = ImageIO.read(getClass().getResource("/characters.png"));

			imageSF = sheet.getSubimage(98, 64, 12, 16);
			imageSF = MyUtils.toBufferedImage(imageSF.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageSB = sheet.getSubimage(98, 112, 12, 16);
			imageSB = MyUtils.toBufferedImage(imageSB.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageSL = sheet.getSubimage(98, 80, 12, 16);
			imageSL = MyUtils.toBufferedImage(imageSL.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageSR = sheet.getSubimage(98, 96, 12, 16);
			imageSR = MyUtils.toBufferedImage(imageSR.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWB1 = sheet.getSubimage(98, 112, 12, 16);
			imageWB1 = MyUtils.toBufferedImage(imageWB1.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWB2 = sheet.getSubimage(114, 112, 12, 16);
			imageWB2 = MyUtils.toBufferedImage(imageWB2.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWF1 = sheet.getSubimage(98, 64, 12, 16);
			imageWF1 = MyUtils.toBufferedImage(imageWF1.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWF2 = sheet.getSubimage(114, 64, 12, 16);
			imageWF2 = MyUtils.toBufferedImage(imageWF2.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWL1 = sheet.getSubimage(98, 80, 12, 16);
			imageWL1 = MyUtils.toBufferedImage(imageWL1.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWL2 = sheet.getSubimage(114, 80, 12, 16);
			imageWL2 = MyUtils.toBufferedImage(imageWL2.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWR1 = sheet.getSubimage(98, 96, 12, 16);
			imageWR1 = MyUtils.toBufferedImage(imageWR1.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
			imageWR2 = sheet.getSubimage(114, 96, 12, 16);
			imageWR2 = MyUtils.toBufferedImage(imageWR2.getScaledInstance(sheetImageWidth * 2, sheetImageHeight * 2,
					BufferedImage.SCALE_AREA_AVERAGING));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BasicAI getAI() {
		return ai;
	}

	public void addAI(BasicAI ai) {
		this.ai = ai;
		if (ai != null) {
			ai.addBaddy(this);
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

	public boolean isHealthDrop() {
		Random random = new Random();
		int tmp = random.nextInt(100);// chance of getting a drop
		if (tmp < chance) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isCandyDrop() {
		Random random = new Random();
		int tmp = random.nextInt(100);// chance of getting a drop
		if (tmp < chance/3) {
			return true;
		} else {
			return false;
		}
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
	
	public void fireWeapon(double xVector, double yVector) {
		if (weapon != null) {
				weapon.launchProjectile((int) getBody().getX(), (int) getBody().getY(), xVector, yVector);
		}
	}

	@Override
	public void damage(int damage) {
		health -= damage;
		// fire decrement health event to update the info bar
		// System.out.println(damage);
		if (health <= 0) {
			// fire death event
			fireDeathEvent();
		}
		SoundBoard.playSound("Arcane_ImpactF1.wav");
	}
}