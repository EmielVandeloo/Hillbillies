package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Entity;
import hillbillies.model.World;
import hillbillies.world.Position;

/**
 * A class of item entities.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public abstract class ItemEntity extends Entity {
	
	/**
	 * Variable registering the weight of this item entity.
	 */
	private final int weight;
		
	/**
	 * A value between 0 and 1 representing the
	 * chance to drop an item entity.
	 */
	public static final double dropChance = 0.25;

	/**
	 * Variable registering the maximal weight of this item entity.
	 */
	public static final int MAX_WEIGHT = 50;

	/**
	 * Variable registering the minimal weight of this item entity.
	 */
	public static final int MIN_WEIGHT = 10;
	
	/**
	 * Field representing the identification of this entity.
	 */
	public static final String ENTITY_ID = "item_entity";
	
	/**
	 * Initialize this new item entity with given world, position and weight.
	 * 
	 * @param  world
	 * 		   The world for this new item entity.
	 * @param  position
	 * 		   The position for this new item entity.
	 * @param  weight
	 *         The weight for this new item entity.
	 * @effect This item entity is initialized as a new entity with the given world
	 *         and the given position.
	 * @effect This new item entity's weight is equal to the given weight.
	 * @throws IllegalArgumentException
	 *         This new item entity cannot have the given weight as its weight.
	 */
	private ItemEntity(World world, Position position, int weight) throws IllegalArgumentException {
		super(world, position);
		if (!canHaveAsWeight(weight)) {
			throw new IllegalArgumentException();
		}
		this.weight = weight;
	}
	
	/**
	 * Initialize this new item entity with given world, given position and random weight.
	 * 
	 * @param world
	 *        The world for this new item entity.
	 * @param position
	 *        The position for this new item entity.
	 */
	public ItemEntity(World world, Position position) {
		this(world, position, getRandomWeight());
	}
	
	/**
	 * Return the weight of this item .
	 */
	@Basic @Raw @Immutable
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Check whether this item  can have the given weight as its weight.
	 *  
	 * @param  weight
	 *         The weight to check.
	 * @return True if and only if the given weight is greater than or equal to
	 *         the minimal weight and less than or equal to the maximal weight
	 *         for any item entity.
	 */
	@Raw
	public static boolean canHaveAsWeight(int weight) {
		return (weight >= MIN_WEIGHT) && (weight <= MAX_WEIGHT);
	}

	/**
	 * Return a random weight between the minimal and the maximal value of the weight
	 * for any item entity.
	 * 
	 * @return A random integer value between the minimal and the maximal value of the 
	 *         weight for any item entity.
	 */
	private static int getRandomWeight() {
		Random random = new Random();
		return random.nextInt((MAX_WEIGHT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
	}

	/**
	 * Return whether an item entity will be dropped or not.
	 * 
	 * @return True if and only if a randomly generated value between 0 and 1
	 *         is less than the drop chance.
	 */
	public static boolean willDrop() {
		return new Random().nextDouble() < dropChance;
	}

	/**
	 * Return whether this item entity can stand on the given position.
	 * 
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the given position has an underlying solid cube.
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any entity.
	 */
	@Override
	public boolean hasSupport(Position position) throws IllegalArgumentException {
		if (!isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		return getWorld().hasUnderlyingSolid(position) && super.hasSupport(position);
	}

	/**
	 * Advance the state of this item entity by the given time step.
	 * 
	 * @param  deltaTime
	 *         The time step, in seconds, by which to advance this item entity's state.
	 * @effect If this item entity is currently falling, its fall behavior is performed.
	 * @effect Otherwise, if this item entity cannot stand on its current position, its
	 *         fall behavior is performed.
	 * @throws IllegalArgumentException
	 * 		   The item entity does not have proper world or position.
	 */
	public void advanceTime(double deltaTime) {
		fallBehavior(deltaTime);
	}
	
	/**
	 * Add this item entity to the world.
	 */
	public void spawn() throws IllegalArgumentException {
		if (getWorld() == null) {
			throw new IllegalArgumentException();
		} 
		if (getPosition() == null || ! isValidPosition(getPosition())) {
			throw new IllegalArgumentException();
		}
		getWorld().addEntity(this);
	}
	
	/**
	 * Remove the item entity from the world.
	 */
	public void despawn() {
		World formerWorld = getWorld();
		setWorld(null);
		formerWorld.removeEntity(this);
	}
}