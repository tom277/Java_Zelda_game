package model;

public class DifficultySettings {

	/*
	 * Adjustst the difficulty settings
	 */

	private static int maxNumberOfMobs = 4;
	private static int mobSpeed = 1;
	private static int mobProjectileSpeed = 3;
	private static int invinsibilityTime = 60;
	private static int probabilityOfShootingAI = 30;
	private static int probabilityOfHealthDrop = 30;
	private static int baddyWeaponRange = 300;
	private static int firingRate = 40;
	private static int baddyDetectionDistance = 200;
	private static int baddyHealth = 6;
	private static int difficultyMultiplier = 1;

	public static int getBaddyHealth() {
		return baddyHealth;
	}

	public static enum Difficulty {
		EASY, MEDIUM, HARD, IMPOSSIBLE;
	}

	public static void setDifficulty(Difficulty diff) {
		switch (diff) {
		case EASY:
			maxNumberOfMobs = 4;
			mobSpeed = 1;
			mobProjectileSpeed = 2;
			invinsibilityTime = 60;
			probabilityOfShootingAI = 10;
			probabilityOfHealthDrop = 70;
			baddyWeaponRange = 300;
			firingRate = 40;
			baddyDetectionDistance = 200;
			baddyHealth = 4;
			difficultyMultiplier = 1;
			break;
		case MEDIUM:
			maxNumberOfMobs = 4;
			mobSpeed = 1;
			mobProjectileSpeed = 3;
			invinsibilityTime = 50;
			probabilityOfShootingAI = 30;
			probabilityOfHealthDrop = 30;
			baddyWeaponRange = 300;
			firingRate = 35;
			baddyDetectionDistance = 200;
			baddyHealth = 6;
			difficultyMultiplier = 2;
			break;
		case HARD:
			maxNumberOfMobs = 5;
			mobSpeed = 1;
			mobProjectileSpeed = 4;
			invinsibilityTime = 30;
			probabilityOfShootingAI = 50;
			probabilityOfHealthDrop = 15;
			baddyWeaponRange = 300;
			firingRate = 40;
			baddyDetectionDistance = 200;
			baddyHealth = 6;
			difficultyMultiplier = 3;
			break;
		case IMPOSSIBLE:
			maxNumberOfMobs = 8;
			mobSpeed = 2;
			mobProjectileSpeed = 5;
			invinsibilityTime = 30;
			probabilityOfShootingAI = 70;
			probabilityOfHealthDrop = 15;
			baddyWeaponRange = 400;
			firingRate = 20;
			baddyDetectionDistance = 200;
			baddyHealth = 10;
			difficultyMultiplier = 4;
			break;
		}
	}

	public static int getDifficultyMultiplier() {
		return difficultyMultiplier;
	}

	public static int getBaddyDetectionDistance() {
		return baddyDetectionDistance;
	}

	public static int getFiringRate() {
		return firingRate;
	}

	public static int getBaddyWeaponRange() {
		return baddyWeaponRange;
	}

	public static int getMaxNumberOfMobs() {
		return maxNumberOfMobs;
	}

	public static int getMobSpeed() {
		return mobSpeed;
	}

	public static int getMobProjectileSpeed() {
		return mobProjectileSpeed;
	}

	public static int getInvinsibilityTime() {
		return invinsibilityTime;
	}

	public static int getProbabilityOfShootingAI() {
		return probabilityOfShootingAI;
	}

	public static int getProbabilityOfHealthDrop() {
		return probabilityOfHealthDrop;
	}
}
