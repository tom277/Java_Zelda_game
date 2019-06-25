package listeners;

import model.Hero;

public interface PlayerEventListener {
	public void playerUpdate(PlayerEvent event);
	public void playerUpdate(Hero hero);
}
