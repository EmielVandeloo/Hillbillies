package hillbillies.model;

import hillbillies.model.item.ItemEntity;
import hillbillies.world.Position;
import hillbillies.model.World;

/**
 * A class of logs.
 */
public class Log extends ItemEntity {
	
	// CONSTRUCTORS

	public Log(World world, Position position) throws IllegalArgumentException {
		super(world, position);
	}
	
	public Log(World world, Position position, int weight) throws IllegalArgumentException {
		super(world, position, weight);
	}
	
	
	// OVERRIDE

	@Override
	public void drop(Position position) {
		// TODO
		//Log.spawn(new Log(getWorld(), getPosition()));
	}

}