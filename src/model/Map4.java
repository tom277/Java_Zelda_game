package model;

import java.awt.geom.Rectangle2D;
import java.util.Random;

import listeners.ProjectileListener;

public class Map4 extends Map{
	
	private static final long serialVersionUID = -7990649580210459300L;
	private static final int mapNumber = 3;
	private MapRandomGenerator generator;
	private Random rand;
	
	public Map4(int width, int height, Hero hero) {
		super(width, height, hero, mapNumber);
		rand = new Random();
		generator = new MapRandomGenerator(this, hero, new Rectangle2D.Double(12 * 32, 0, 240, 200), new Rectangle2D.Double(620, 180, 35, 400));
		
		//generate random obstacles
		for(int i = 0; i < rand.nextInt(12) + 8; i++) {
			generator.generateObstructionBundles(rand.nextInt(20) + 3, rand.nextInt(280) + 30);
		}
		for(int i = 0; i < rand.nextInt(4) + 3; i++) {
			generator.randomObstructions();
		}
		//generate background
		for (int i = 0; i < 32 + 1; i++) {// x direction
			for (int j = 0; j < 23 + 1; j++) {// y direction
				background.add(new Background(i * 32, j * 32, Background.DIRT));
			}
		}
		background.add(new Background(15 * 32, 1 * 32, Background.CAVE_ENTRANCE));
		mapChanges.add( new MapChangeInteraction(2, 15 * 32, 1 * 32, 17*32, 20*32));
		// top barrier
		for (int i = 0; i < 32; i++) {

				obstructions.add(new Obstruction(i * 32, 0, Obstruction.CAVE_WALL));
		}
		// bottom barrier
		for (int i = 0; i < 32; i++) {
			obstructions.add(new Obstruction(i * 32, 22 * 32, Obstruction.CAVE_WALL));
		}
		// left barrier
		for (int i = 1; i < 22; i++) {
			obstructions.add(new Obstruction(0, i * 32, Obstruction.CAVE_WALL));
		}
		// right barrier
		for (int i = 1; i < 22; i++) {
			obstructions.add(new Obstruction(31 * 32, i * 32, Obstruction.CAVE_WALL));
		}
		obstructions.addAll(generator.getObstructions());
		addBaddies();
	}
	
	private void addBaddies() {
		generator.randBaddyPos();//generate random positions
		generator.makeBaddies();//make new baddies
		baddies = this.generator.getBaddies();//get the baddies
		generator.makeBoss();
		baddies.add(generator.getBoss());
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
		//don't reset the boss level so boss health doesn't regenerate
	}

}
