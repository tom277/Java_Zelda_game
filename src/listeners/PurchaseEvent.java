package listeners;

import model.Weapon;

public class PurchaseEvent {
	int cost;
	Weapon weapon;
	public PurchaseEvent(int cost, Weapon weapon) {
		super();
		this.cost = cost;
		this.weapon = weapon;
	}
	public int getCost() {
		return cost;
	}
	public Weapon getWeapon() {
		return weapon;
	}
}
