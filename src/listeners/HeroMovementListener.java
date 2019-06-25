package listeners;

import model.Map;

public interface HeroMovementListener {
	public Map changeMap(int num);
	public void interact(String string);
	public void setCloseToShopFalse();
	public void setCloseToGundrenFalse();
	public void hideTextBox();
}
