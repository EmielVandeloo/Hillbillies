package hillbillies.model;

import hillbillies.model.World;
import hillbillies.model.item.Item;
import hillbillies.world.Position;

/**
 * A class of logs.
 */
public class Log extends Item {
	
	// CONSTRUCTORS

	public Log() throws IllegalArgumentException {
		super();
	}
	
	public Log(int weight) throws IllegalArgumentException {
		super(weight);
	}
	
	
	// METHODS
	
	public static void drop(World world, Position position) {
		if (dropChance()) {
			new Log().fall(world, position);
		}
	}
	
}