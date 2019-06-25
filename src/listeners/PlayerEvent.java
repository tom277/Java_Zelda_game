package listeners;

public class PlayerEvent {
private int coins;
private int enemiesKilled;
private int moneyInWeapons;
private int health;
private boolean didWin;
public PlayerEvent(int coins, int enemiesKilled, int health, int moneyInWeapons, boolean didWin) {
	super();
	this.coins = coins;
	this.enemiesKilled = enemiesKilled;
	this.moneyInWeapons = moneyInWeapons;
	this.health = health;
	this.didWin = didWin;
}
public int getCoins() {
	return coins;
}
public int getHealth() {
	return health;
}
public int getEnemiesKilled() {
	return enemiesKilled;
}
public int getWeponsOwned() {
	return moneyInWeapons;
}
public boolean isWin() {
	return didWin;
}
}
