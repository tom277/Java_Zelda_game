package model;

import java.awt.geom.Rectangle2D;
import java.util.Random;

import listeners.ProjectileListener;

public class Map2 extends Map {

	/**
	 * This creates the map to the right of the start map
	 */
	private static final long serialVersionUID = -2264899651808858163L;
	private static final int mapNumber = 2;
	private MapRandomGenerator generator;
	private Random rand;

	public Map2(int width, int height, Hero hero) {
		super(width, height, hero, mapNumber);
		rand = new Random();
		this.hero = hero;
		generator = new MapRandomGenerator(this, hero, new Rectangle2D.Double(0, 224 - 70, 200, 260),
				new Rectangle2D.Double(200, 224 - 70, 350, 70));
		generator.generateWater(20, 50);
		generator.generateWater(20, 50);
		generator.randomObstructions();
		generator.randBaddyPos();
		generator.makeBaddies();

		for (int i = 0; i < rand.nextInt(12) + 6; i++) {
			generator.generateObstructionBundles(rand.nextInt(20) + 3, rand.nextInt(280) + 30);
		}

		for (int i = 0; i < 32; i++) {
			obstructions.add(new Obstruction(i * 32, 0, Obstruction.BUSH));
		}
		for (int i = 0; i < 32; i++) {
			obstructions.add(new Obstruction(i * 32, 22 * 32, Obstruction.BUSH));
		}
		for (int i = 1; i < 22; i++) {
			if (i < 7 || i > 10) {
				obstructions.add(new Obstruction(0, i * 32, Obstruction.BUSH));
			} else {
				MapChangeInteraction ic2 = new MapChangeInteraction(0, -35, i * 32, 990, -1);
				mapChanges.add(ic2);
			}
		}
		for (int i = 1; i < 22; i++) {
			obstructions.add(new Obstruction(31 * 32, i * 32, Obstruction.BUSH));

		}
		obstructions.addAll(generator.getObstructions());
		this.height = height;
		this.width = width;

		for (int i = 0; i < 32 + 1; i++) {// x direction
			for (int j = 0; j < 23 + 1; j++) {// y direction
				background.add(new Background(i * 32, j * 32, Background.GRASS));
			}
		}
		addBaddies();
	}

	private void addBaddies() {
		generator.randBaddyPos();// generate random positions
		generator.makeBaddies();// make new baddies
		baddies = this.generator.getBaddies();// get the baddies
		for (Baddy tmpBaddy : baddies) {
			tmpBaddy.setIsMoving(true);
			tmpBaddy.setDeathListener(new ProjectileListener() {
				public void DeleteProjectile() {
					baddyDeathEvent(tmpBaddy);
				}
			});
		}
		this.updateHero(hero);
	}

	public void setBaddyDeathEventListener(ProjectileListener listener) {
		this.listener = listener;
	}

	public void reset() {
		addBaddies();
	}
}