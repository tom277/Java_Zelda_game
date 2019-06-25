package model;

public class Map1 extends Map {

	/**
	 * This class creates the start map
	 */
	private static final long serialVersionUID = -593585747413342260L;
	private static final int mapNumber = 1;

	public Map1(int width, int height, Hero hero) {
		super(width, height, hero, mapNumber);
		for (int i = 0; i < 32; i++) {
			obstructions.add(new Obstruction(i * 32, 0, Obstruction.BUSH));
		}
		for (int i = 0; i < 32; i++) {
			if (i < 7 || i > 10) {
				obstructions.add(new Obstruction(i * 32, 22 * 32, Obstruction.BUSH));
				obstructions.add(new Obstruction(i * 32, 21 * 32, Obstruction.BUSH));
			} else {
				MapChangeInteraction ic2 = new MapChangeInteraction(2, i * 32, 22 * 32 + 20, -1, 15);
				mapChanges.add(ic2);
			}
		}
		for (int i = 1; i < 22; i++) {
			obstructions.add(new Obstruction(0, i * 32, Obstruction.BUSH));
		}
		for (int i = 1; i < 22; i++) {
			if (i < 7 || i > 10) {
				obstructions.add(new Obstruction(31 * 32, i * 32, Obstruction.BUSH));
			} else {
				MapChangeInteraction ic2 = new MapChangeInteraction(1, 31 * 32 + 25, i * 32, 15, -1);
				mapChanges.add(ic2);
			}
		}
		
		makeRiverSegment(30, 0, 32, 18, true);
		makeRiverSegment(30, 19, 32, 22, false);
		makeRiverSegment(30, 23, 32, 50, true);
		
		obstructions.add(new Obstruction(5 * 26, 5 * 24, Obstruction.ROCK0));
		obstructions.add(new Obstruction(14 * 26, 20 * 24, Obstruction.ROCK1));
		
		obstructions.add(new Obstruction(467, 300, Obstruction.BRIDGE));
		obstructions.add(new Obstruction(467, 350, Obstruction.INVISIBLE_WALL));
		
		obstructions.add(new Obstruction(650, 350, Obstruction.HOUSE));
		obstructions.add(new Obstruction(800, 550, Obstruction.HOUSE));
		obstructions.add(new Obstruction(650, 550, Obstruction.HOUSE));
		obstructions.add(new Obstruction(800, 350, Obstruction.HOUSE));
		obstructions.add(new Obstruction(100, 550, Obstruction.HOUSE));
		
		obstructions.add(new Obstruction(150, 400, Obstruction.TREE1));
		
		obstructions.add(new Obstruction(400, 20, Obstruction.TREE2));
		obstructions.add(new Obstruction(540, 20, Obstruction.TREE1));
		obstructions.add(new Obstruction(400, 90, Obstruction.TREE1));
		obstructions.add(new Obstruction(540, 90, Obstruction.TREE2));
		obstructions.add(new Obstruction(400, 160, Obstruction.TREE2));
		obstructions.add(new Obstruction(540, 160, Obstruction.TREE1));
		obstructions.add(new Obstruction(400, 230, Obstruction.TREE1));
		obstructions.add(new Obstruction(540, 230, Obstruction.TREE2));
		obstructions.add(new Obstruction(700, 50, Obstruction.TREE2));
		
		obstructions.add(new Obstruction(735, 400, Obstruction.TREE1));
		this.height = height;
		this.width = width;

		for (int i = 0; i < 32 + 1; i++) {// x direction
			for (int j = 0; j < 23 + 1; j++) {// y direction
				background.add(new Background(i * 32, j * 32, Background.GRASS));
			}
		}

		InteractiveComponent ic = new InteractiveComponent(InteractiveComponent.STORE, 900, 10);
		ics.add(ic);
		obstructions.add(ic);
		ic = new InteractiveComponent(InteractiveComponent.GUNDREN, 80, 250);
		ics.add(ic);
		obstructions.add(ic);

		pickUps.add(new PickUpRing(100, 100));
	}

	// Add part of the river
	private void makeRiverSegment(int startingXBlock, int startingYBlock, int targetXBlock, int targetYBlock, boolean isObstruction) {
		int currentXBlock = startingXBlock;
		int currentYBlock = startingYBlock;
		while (currentYBlock != targetYBlock) {
			if (currentXBlock == targetXBlock) {
				currentXBlock = startingXBlock;
				currentYBlock++;
			}
			determineWaterTileType(currentXBlock, currentYBlock, isObstruction);
			currentXBlock++;
		}
		determineWaterTileType(currentXBlock, currentYBlock, isObstruction);
	}
	
	private void determineWaterTileType(int currentXBlock, int currentYBlock, boolean isObstruction) {
		if (isObstruction) {
			obstructions.add(new Obstruction(16 * currentXBlock, 16 * currentYBlock, Obstruction.WATER));
		} else {
			obstructions.add(new Obstruction(16 * currentXBlock, 16 * currentYBlock, Obstruction.BACKGROUND_WATER));
		}
	}
	

}