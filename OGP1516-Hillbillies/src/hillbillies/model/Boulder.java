package hillbillies.model;

import hillbillies.model.World;
import hillbillies.world.Position;
import hillbillies.model.item.Item;

/**
 * A class of boulders.
 */
public class Boulder extends Item {

	// CONSTRUCTORS

	public Boulder() {
		super();
	}

	public Boulder(int weight) {
		super(weight);
	}


	// METHODS
	
	public static void drop(World world, Position position) {
		if (dropChance()) {
			new Boulder().fall(world, position);
		}
	}

}