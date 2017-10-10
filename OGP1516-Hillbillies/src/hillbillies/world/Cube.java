package hillbillies.world;

import hillbillies.model.Boulder;
import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.world.Position;

/**
 * A class of cubes.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public enum Cube {
	
	// ENUM
	
	AIR (true, 0),
	ROCK (false, 1),
	WOOD (false, 2),
	WORKBENCH (true, 3);
	
	// FIELDS
	
	private final boolean passable;
	private final int id;
	
	// CONSTRUCTORS
	
	private Cube(boolean passable, int id) {
		this.passable = passable;
		this.id = id;
	}
	
	// GETTERS AND SETTERS
	
	public boolean isPassable() {
		return passable;
	}
	
	public int getId() {
		return id;
	}
	
	// METHODS

	public void drop(World world, Position position) {
		if (world.isValidPosition(position)) {
			switch (this) {
			case ROCK:
				Boulder.drop(world, position);
				break;
			case WOOD:
				Log.drop(world, position);
				break;
			default:
				break;
			}
		}
	}
	
	public static Cube byId(int id) throws IndexOutOfBoundsException {
		for (Cube cube : Cube.values()) {
			if (cube.getId() == id) {
				return cube;
			}
		}
		throw new IndexOutOfBoundsException();
	}
}
