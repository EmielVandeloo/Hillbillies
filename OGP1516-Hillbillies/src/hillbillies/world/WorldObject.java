package hillbillies.world;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World;

/**
 * A class of world objects.
 * 
 * @invar  Each world object can have its world as world.
 *       | canHaveAsWorld(this.getWorld())
 * 
 * @author Pieter-Jan
 */
public abstract class WorldObject {
	
	// FIELDS
	
	/**
	 * Variable registering the world of this world object.
	 */
	private final World world;
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new world object with given world.
	 * 
	 * @param  world
	 *         The world for this new world object.
	 * @post   The world of this new world object is equal to the given
	 *         world.
	 *       | new.getWorld() == world
	 * @throws IllegalArgumentException
	 *         This new world object cannot have the given world as its world.
	 *       | ! canHaveAsWorld(this.getWorld())
	 */
	public WorldObject(World world) throws IllegalArgumentException {
		if (! canHaveAsWorld(world))
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	
	// GETTERS AND SETTERS

	/**
	 * Return the world of this world object.
	 */
	@Basic @Raw @Immutable
	public World getWorld() {
		return this.world;
	}

	/**
	 * Check whether this world object can have the given world as its world.
	 *  
	 * @param  world
	 *         The world to check.
	 * @return 
	 *       | result == (world != null)
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		return (world != null);
	}

}
