package model;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.ArrayList;

import listeners.ProjectileListener;

public abstract class Map implements Serializable {

	/**
	 * general map class to handle some of the code needed for all maps
	 */
	private static final long serialVersionUID = -4169122161735488539L;

	protected ArrayList<Tangible> obstructions;
	protected ArrayList<Background> background;
	protected ArrayList<Baddy> baddies;
	protected ArrayList<model.Object> pickUps;
	protected ArrayList<InteractiveComponent> ics;
	protected ArrayList<MapChangeInteraction> mapChanges;
	protected Hero hero;

	protected int mapNumber;
	protected int width;
	protected int height;
	protected int startPosx;
	protected int startPosy;

	protected ProjectileListener listener;

	public Map(int width, int height, Hero hero, int mapNumber) {
		this.width = width;
		this.height = height;
		this.mapNumber = mapNumber;
		obstructions = new ArrayList<Tangible>();
		background = new ArrayList<Background>();
		baddies = new ArrayList<Baddy>();
		pickUps = new ArrayList<model.Object>();
		ics = new ArrayList<InteractiveComponent>();
		obstructions = new ArrayList<Tangible>();
		background = new ArrayList<Background>();
		mapChanges = new ArrayList<MapChangeInteraction>();
	}

	public ArrayList<MapChangeInteraction> getMapChanges() {
		return mapChanges;
	}

	public int getMapNumber() {
		return mapNumber;
	}

	public ArrayList<Baddy> getBaddies() {
		return baddies;
	}

	public ArrayList<model.Object> getPickUps() {
		return pickUps;
	}

	public ArrayList<InteractiveComponent> getIcs() {
		return ics;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<Tangible> getObstructions() {
		return obstructions;
	}

	public ArrayList<Background> getBackground() {
		return background;
	}

	public int getStartPosx() {
		return startPosx;
	}

	public int getStartPosy() {
		return startPosy;
	}

	public void setStartPosx(int posX) {

	}

	public void updateHero(Hero hero) {
		this.hero = hero;
		for (Baddy tmpBaddy : baddies) {
			if (tmpBaddy.getAI() != null) {
				tmpBaddy.getAI().setHero(this.hero);
			}
		}
	}

	public void setObstructions(ArrayList<Tangible> obstructions) {
		this.obstructions = obstructions;
	}

	public void setBackground(ArrayList<Background> background) {
		this.background = background;
	}

	public void setBaddies(ArrayList<Baddy> baddies) {
		this.baddies = baddies;
	}

	public void setPickUps(ArrayList<model.Object> pickUps) {
		this.pickUps = pickUps;
	}

	public void setIcs(ArrayList<InteractiveComponent> ics) {
		this.ics = ics;
	}

	public void setMapChanges(ArrayList<MapChangeInteraction> mapChanges) {
		this.mapChanges = mapChanges;
	}

	public void setListener(ProjectileListener listener) {
		this.listener = listener;
	}

	public void setStartPosy(int posX) {

	}

	public void setBaddyDeathEventListener(ProjectileListener listener) {
		this.listener = listener;
	}

	public void reset() {
	}

	public Graphics2D render(Graphics2D g2, ImageObserver observer) {

		return null;
	}

	protected void baddyDeathEvent(Baddy baddy) {
		if (!(baddy instanceof Boss)) {
			pickUps.add(new Coin((int) baddy.getBody().getX(), (int) baddy.getBody().getY()));
			if (baddy.isHealthDrop()) {
				pickUps.add(new HealthPickUp((int) baddy.getBody().getX() + 3, (int) baddy.getBody().getY() + 3));
			}
			if (baddy.isCandyDrop()) {
				pickUps.add(new CandyPickUp((int) baddy.getBody().getX() - 4, (int) baddy.getBody().getY() + 7));
			}
			if (baddy.getType() == Baddy.FOREST_WIZARD) {
				pickUps.add(new Coin((int) baddy.getBody().getX() + 7, (int) baddy.getBody().getY() - 4));
			}
		} else {
			pickUps.add(new OrbPickUp((int) baddy.getBody().getX(), (int) baddy.getBody().getY()));
		}
		getBaddies().remove(baddy);
		if (listener != null) {
			listener.DeleteProjectile();
		}
	}

	public void update() {
		for (int i = 0; i < getBaddies().size(); i++) {
			getBaddies().get(i).update();
		}
	}
}
