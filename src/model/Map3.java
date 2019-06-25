package model;

import java.awt.geom.Rectangle2D;
import java.util.Random;

import listeners.ProjectileListener;

public class Map3 extends Map {

	/**
	 * This creates the map below the start map
	 */
	private static final long serialVersionUID = -7990649580210459300L;
	private static final int mapNumber = 3;
	private MapRandomGenerator generator;
	private Random rand;

	public Map3(int width, int height, Hero hero) {
		super(width, height, hero, mapNumber);
		rand = new Random();
		generator = new MapRandomGenerator(this, hero, new Rectangle2D.Double(150, 0, 260, 200),
				new Rectangle2D.Double(150, 200, 35, 550), new Rectangle2D.Double(185, 630, 210, 35), new Rectangle2D.Double(12 * 32, 18*32, 240, 240));
		
		//generate random obstacles
		for(int i = 0; i < rand.nextInt(12) + 8; i++) {
			generator.generateObstructionBundles(rand.nextInt(20) + 3, rand.nextInt(280) + 30);
		}
		for(int i = 0; i < rand.nextInt(4) + 3; i++) {
			generator.randomObstructions();
		}
		//background generation
		for (int i = 0; i < 32 + 1; i++) {// x direction
			for (int j = 0; j < 23 + 1; j++) {// y direction
				background.add(new Background(i * 32, j * 32, Background.GRASS));
			}
		}
		background.add(new Background(15 * 32, 21 * 32, Background.CAVE_ENTRANCE));
		mapChanges.add( new MapChangeInteraction(3, 15 * 32, 21 * 32, 15 * 32, 3 * 32));
		// top barrier
		for (int i = 0; i < 32; i++) {

			if (i < 7 || i > 10) {
				obstructions.add(new Obstruction(i * 32, 0, Obstruction.BUSH));
			} else {
				MapChangeInteraction ic2 = new MapChangeInteraction(0, i * 32, -30, -1, 690);
				mapChanges.add(ic2);
			}
		}
		// bottom barrier
		for (int i = 0; i < 32; i++) {
			obstructions.add(new Obstruction(i * 32, 22 * 32, Obstruction.BUSH));
		}
		// left barrier
		for (int i = 1; i < 22; i++) {
			obstructions.add(new Obstruction(0, i * 32, Obstruction.BUSH));
		}
		// right barrier
		for (int i = 1; i < 22; i++) {
			obstructions.add(new Obstruction(31 * 32, i * 32, Obstruction.BUSH));
		}
		
		
		obstructions.addAll(generator.getObstructions());
		addBaddies();
	}
	
	private void addBaddies() {
		generator.randBaddyPos();//generate random positions
		generator.makeBaddies();//make new baddies
		baddies = this.generator.getBaddies();//get the baddies
		//Baddy tmpBaddy;
		for(Baddy tmpBaddy : baddies) {
			tmpBaddy.setIsMoving(true);
			tmpBaddy.setDeathListener(new ProjectileListener() {
				public void DeleteProjectile() {
					baddyDeathEvent(tmpBaddy);
				}
			});
		}
		this.updateHero(hero);
	}
	
	public void reset() {
		addBaddies();
	}
}
