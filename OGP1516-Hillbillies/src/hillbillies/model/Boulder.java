package hillbillies.model;

import hillbillies.model.item.ItemEntity;
import hillbillies.world.Position;
import hillbillies.model.World;

/**
 * A class of boulders.
 */
public class Boulder extends ItemEntity {
	
	// CONSTRUCTORS
	
	public Boulder(World world, Position position) throws IllegalArgumentException{
		super(world, position);
	}

	public Boulder(World world, Position position, int weight) throws IllegalArgumentException {
		super(world, position, weight);
	}
	
	
	// OVERRIDE

	@Override
	public void drop(Position position) {
		// TODO Auto-generated method stub
		
	}
	
}