package model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import controller.BasicAI;
import controller.BossAI;
import controller.ShootingAI;

public class MapRandomGenerator implements Serializable {

	/**
	 * Generates the components used in maps components 
	 * generated include: 
	 * Baddy types and positions 
	 * single obstructions 
	 * grounp obstructions
	 */
	private static final long serialVersionUID = 9128720783092620417L;
	Coordinate entranceStart;
	Coordinate entranceFinish;
	private ArrayList<Baddy> baddies;
	private Random rand;
	private int maxBaddy;
	private int maxObstructions;
	private ArrayList<Rectangle2D.Double> baddyPos;
	private ArrayList<BasicAI> AIs;
	private BossAI bossAI;
	private ArrayList<Obstruction> obstructions;
	private ArrayList<Rectangle2D.Double> tmpLocations;
	private ArrayList<Rectangle2D.Double> clearZones;
	private Map map;
	private Hero hero;
	private int safety;
	private Baddy boss;
	
	// This class generates the randomly generated obstructions and randomly 
	// generated and positioned enemies
	public MapRandomGenerator(Map map, Hero hero, Rectangle2D.Double... clearZones) {
		this.clearZones = new ArrayList<Rectangle2D.Double>();
		for (Rectangle2D.Double tmp : clearZones) {
			this.clearZones.add(tmp);
		}
		baddies = new ArrayList<Baddy>();
		baddyPos = new ArrayList<Rectangle2D.Double>();
		AIs = new ArrayList<BasicAI>();
		obstructions = new ArrayList<Obstruction>();
		rand = new Random();
		this.map = map;
		this.hero = hero;
		this.maxObstructions = 8;
	}

	public void randomObstructions() {
		int counter = 0;
		int numOfObstructions = rand.nextInt(maxObstructions - 3) + 3;
		Rectangle2D.Double tmpBody;
		boolean isObstructed = false;
		while (counter < numOfObstructions) {
			isObstructed = false;
			tmpBody = new Rectangle2D.Double(rand.nextInt(884) + 70, rand.nextInt(597) + 70, 32, 32);
			if (!(intersectsClearZones(tmpBody))) {
				for (int i = 0; i < obstructions.size(); i++) {
					if (obstructions.get(i).getBody() != null) {
						if (tmpBody.intersects(obstructions.get(i).getBody().getBounds2D())) {
							isObstructed = true;
						}
					} else {
					}
				}
			} else {
				isObstructed = true;
			}
			if (!isObstructed) {
				int c = rand.nextInt(4);
				switch (c) {
				case 0:
					obstructions.add(new Obstruction((int) tmpBody.getX(), (int) tmpBody.getY(), Obstruction.ROCK0));
					break;
				case 1:
					obstructions.add(new Obstruction((int) tmpBody.getX(), (int) tmpBody.getY(), Obstruction.ROCK1));
					break;
				case 2:
					obstructions.add(new Obstruction((int) tmpBody.getX(), (int) tmpBody.getY(), Obstruction.BUSH));
					break;
				case 3:
					obstructions.add(new Obstruction((int) tmpBody.getX(), (int) tmpBody.getY(), Obstruction.TRUNK));
					break;
				}
				counter++;
			}
		}
	}

	private boolean intersectsClearZones(Rectangle2D.Double body) {
		for (Rectangle2D.Double tmp : clearZones) {
			if (body.intersects(tmp.getBounds2D())) {
				return true;
			}
		}
		return false;
	}

	public void generateWater(int maxSize, int chance) {
		safety = 0;
		tmpLocations = new ArrayList<Rectangle2D.Double>();
		Rectangle2D.Double tmpBody = null;
		boolean validStart = false;
		Obstruction tmpO = new Obstruction(0, 0, Obstruction.WATER);
		double tmpWidth = tmpO.getBody().getWidth();
		double tmpHeight = tmpO.getBody().getHeight();
		while (!validStart) {
			tmpBody = new Rectangle2D.Double(rand.nextInt(884) + 70, rand.nextInt(597) + 70, tmpWidth, tmpHeight);
			if (!(intersectsClearZones(tmpBody)) && !intersectsObstructions(tmpBody)) {
				validStart = true;
			}
		}
		if (tmpBody != null) {
			nextIteration(tmpBody, tmpWidth, tmpHeight, maxSize, chance);
		}

		for (int i = 0; i < tmpLocations.size(); i++) {
			obstructions.add((new Obstruction((int) tmpLocations.get(i).getX(), (int) tmpLocations.get(i).getY(),
					Obstruction.WATER)));
		}
	}

	public void generateObstructionBundles(int maxSize, int chance) {
		int type = rand.nextInt(5);
		Obstruction tmpO;
		if (type == 0) {
			tmpO = new Obstruction(0, 0, Obstruction.BUSH);
		} else if (type == 1) {
			tmpO = new Obstruction(0, 0, Obstruction.ROCK0);
		} else if (type == 2) {
			tmpO = new Obstruction(0, 0, Obstruction.ROCK1);
		} else if (type == 3) {
			tmpO = new Obstruction(0, 0, Obstruction.TRUNK);
		} else {
			tmpO = new Obstruction(0, 0, Obstruction.WATER);
		}
		safety = 0;
		tmpLocations = new ArrayList<Rectangle2D.Double>();
		Rectangle2D.Double tmpBody = null;
		boolean validStart = false;

		double tmpWidth = tmpO.getBody().getWidth();
		double tmpHeight = tmpO.getBody().getHeight();
		while (!validStart) {
			tmpBody = new Rectangle2D.Double(rand.nextInt(884) + 70, rand.nextInt(597) + 70, tmpWidth, tmpHeight);
			if (!(intersectsClearZones(tmpBody)) && !intersectsObstructions(tmpBody)) {
				validStart = true;
			}
		}
		if (tmpBody != null) {
			nextIteration(tmpBody, tmpWidth, tmpHeight, maxSize, chance);
		}

		for (int i = 0; i < tmpLocations.size(); i++) {
			if (type == 0) {
				obstructions.add((new Obstruction((int) tmpLocations.get(i).getX(), (int) tmpLocations.get(i).getY(),
						Obstruction.BUSH)));
			} else if (type == 1) {
				obstructions.add((new Obstruction((int) tmpLocations.get(i).getX(), (int) tmpLocations.get(i).getY(),
						Obstruction.ROCK0)));
			} else if (type == 2) {
				obstructions.add((new Obstruction((int) tmpLocations.get(i).getX(), (int) tmpLocations.get(i).getY(),
						Obstruction.ROCK1)));
			} else if (type == 3) {
				obstructions.add((new Obstruction((int) tmpLocations.get(i).getX(), (int) tmpLocations.get(i).getY(),
						Obstruction.TRUNK)));
			} else {
				obstructions.add((new Obstruction((int) tmpLocations.get(i).getX(), (int) tmpLocations.get(i).getY(),
						Obstruction.WATER)));
			}
		}
	}

	private boolean intersectsObstructions(Rectangle2D.Double tmpBody) {
		for (int j = 0; j < obstructions.size(); j++) {
			if (tmpBody.intersects(obstructions.get(j).getBody().getBounds2D())) {
				return true;
			}
		}
		return false;
	}

	private void nextIteration(Rectangle2D.Double parent, double width, double height, int maxSize, int chance) {
		safety++;

		tmpLocations.add(parent);
		ArrayList<Rectangle2D.Double> tmp = new ArrayList<Rectangle2D.Double>();
		Rectangle2D.Double tmpBody;
		boolean isObstructed = false;
		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				tmpBody = new Rectangle2D.Double(parent.getX(), parent.getY() - height, width, height);// 0
			} else if (i == 1) {
				tmpBody = new Rectangle2D.Double(parent.getX() + width, parent.getY(), width, height);// 1
			} else if (i == 2) {
				tmpBody = new Rectangle2D.Double(parent.getX(), parent.getY() + height, width, height);// 2
			} else {
				tmpBody = new Rectangle2D.Double(parent.getX() - width, parent.getY(), width, height);// 3
			}

			isObstructed = false;
			if (!(intersectsClearZones(tmpBody)) && tmpBody.getX() < 950 && tmpBody.getX() > 40 && tmpBody.getY() < 700
					&& tmpBody.getY() > 40 && !this.intersectsObstructions(tmpBody)) {
				if (rand.nextInt(1000) > (chance * safety)) {//chance of a new item being placed decreases as the number of items added increases 
					for (int j = 0; j < tmpLocations.size(); j++) {
						if (tmpBody.intersects(tmpLocations.get(j).getBounds2D())) {
							isObstructed = true;
						}
					}
				} else {
					isObstructed = true;
				}
			} else {
				isObstructed = true;
			}
			if (!isObstructed) {
				tmp.add(tmpBody);
			}
		}

		if (safety > maxSize) {
			return;
		}

		for (int i = 0; i < tmp.size(); i++) {
			nextIteration(tmp.get(i), width, height, maxSize, chance);
		}
	}

	public ArrayList<Obstruction> getObstructions() {
		return obstructions;
	}

	public ArrayList<Rectangle2D.Double> getClearZone() {
		return clearZones;
	}

	public void randBaddyPos() {
		maxBaddy = DifficultySettings.getMaxNumberOfMobs();
		int count = 0;
		Coordinate cor;
		baddyPos = new ArrayList<Rectangle2D.Double>();
		Rectangle2D.Double tmpBody;

		int hitboxWidth = 24;
		int hitboxHeight = 32;

		while (count < maxBaddy) {
			cor = new Coordinate(rand.nextInt(884) + 70, rand.nextInt(597) + 70);
			tmpBody = new Rectangle2D.Double(cor.getX(), cor.getY(), hitboxWidth, hitboxHeight);
			if (!(intersectsClearZones(tmpBody)) && !intersectsObstructions(tmpBody)
					&& !checkInterseptsBaddy(tmpBody, baddyPos)) {
				count++;
				baddyPos.add(new Rectangle2D.Double(cor.getX(), cor.getY(), 30, 30));
			}
		}

		for (int i = 0; i < baddyPos.size(); i++) {
			if (rand.nextInt(100) < DifficultySettings.getProbabilityOfShootingAI()) {
				int tmp = rand.nextInt(10) + DifficultySettings.getFiringRate();// tied with level
				AIs.add(new ShootingAI(map, hero, tmp));
			} else {
				AIs.add(new BasicAI(map, hero));
			}
		}
	}

	public boolean checkInterseptsBaddy(Rectangle2D.Double tmpBody, ArrayList<Rectangle2D.Double> list) {
		for (int j = 0; j < list.size(); j++) {
			if (tmpBody.intersects(list.get(j).getBounds2D())) {
				return true;
			}
		}
		return false;
	}

	public void makeBaddies() {
		baddies = new ArrayList<Baddy>();
		for (int i = 0; i < baddyPos.size(); i++) {
			if (AIs.get(i) instanceof ShootingAI) {
				baddies.add(new Baddy(AIs.get(i), Baddy.FOREST_WIZARD, (int) baddyPos.get(i).getX(),
						(int) baddyPos.get(i).getY()));
			} else {
				baddies.add(
						new Baddy(AIs.get(i), Baddy.GHOST, (int) baddyPos.get(i).getX(), (int) baddyPos.get(i).getY()));
			}
		}
		for (int i = 0; i < baddies.size(); i++) {
			baddies.get(i).setWeapon(new Weapon(Weapon.RING_5), false);
		}
	}

	public void makeBoss() {
		int tmp = rand.nextInt(10) + DifficultySettings.getFiringRate();// tied with level
		bossAI = new BossAI(map, hero, tmp);
		int startXPos = (int) makeCoordinates().getX();
		int startYPos = (int) makeCoordinates().getY();

		boss = new Boss(bossAI, Baddy.BLUE_WIZARD, startXPos, startYPos);
	}

	public Baddy getBoss() {
		return boss;
	}

	public Rectangle2D.Double makeCoordinates() {
		maxBaddy = DifficultySettings.getMaxNumberOfMobs();
		Coordinate cor = new Coordinate(0, 0);
		baddyPos = new ArrayList<Rectangle2D.Double>();
		Rectangle2D.Double tmpBody;
		boolean isValid = false;

		while (isValid == false) {
			cor = new Coordinate(rand.nextInt(884) + 70, rand.nextInt(597) + 70);
			tmpBody = new Rectangle2D.Double(cor.getX(), cor.getY(), 30, 30);
			if (!(!(intersectsClearZones(tmpBody))) && !intersectsObstructions(tmpBody)
					&& !checkInterseptsBaddy(tmpBody, baddyPos)) {
				isValid = true;
			}
		}

		return new Rectangle2D.Double(cor.getX(), cor.getY(), 30, 30);
	}

	public ArrayList<Baddy> getBaddies() {
		return baddies;
	}

	public Coordinate getEntranceStart() {
		return entranceStart;
	}

	public void setEntranceStart(Coordinate entranceStart) {
		this.entranceStart = entranceStart;
	}

	public Coordinate getEntranceFinish() {
		return entranceFinish;
	}

	public void setEntranceFinish(Coordinate entranceFinish) {
		this.entranceFinish = entranceFinish;
	}
}
