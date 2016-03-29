package hillbillies.model.item;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Entity;
import hillbillies.world.Position;
import hillbillies.model.World;

/**
 * A class of item entities.
 * 
 * @invar  Each item entity can have its weight as weight.
 *       | canHaveAsWeight(this.getWeight())
 */
public abstract class ItemEntity extends Entity implements Droppable {
	
	// FIELDS
	
	/**
	 * Variable registering the weight of this item entity.
	 */
	private final int weight;
	
	/**
	 * Variable registering the maximal weight of this item entity.
	 */
	public static final int MAX_WEIGHT = 50;
	
	/**
	 * Variable registering the minimal weight of this item entity.
	 */
	public static final int MIN_WEIGHT = 10;
	

	// CONSTRUCTORS
	
	public ItemEntity(World world, Position position) throws IllegalArgumentException {
		this(world, position, getRandomWeight());
	}

	/**
	 * Initialize this new item entity with given position and weight.
	 * 
	 * @param  world
	 * 		   The world for this new item entity.
	 * @param  position
	 * 		   The position for this new item entity.
	 * @param  weight
	 *         The weight for this new item entity.
	 * @post   The weight of this new item entity is equal to the given
	 *         weight.
	 *       | new.getWeight() == weight
	 * @throws IllegalArgumentException
	 *         This new item entity cannot have the given weight as its weight.
	 *       | ! canHaveAsWeight(this.getWeight())
	 * @throws IllegalArgumentException
	 * 		   This new item entity cannot have the given world or position.
	 */
	public ItemEntity(World world, Position position, int weight) throws IllegalArgumentException {
		super(world, position);
		
		if (! canHaveAsWeight(weight)) {
			throw new IllegalArgumentException();
		}
		
		this.weight = weight;
	}


	// GETTERS AND SETTERS

	/**
	 * Return the weight of this item entity.
	 */
	@Basic @Raw @Immutable
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Check whether this item entity can have the given weight as its weight.
	 *  
	 * @param  weight
	 *         The weight to check.
	 * @return 
	 *       | result == (weight >= 10) && (weight <= 50)
	 */
	@Raw
	public boolean canHaveAsWeight(int weight) {
		return (weight >= 10) && (weight <= 50);
	}
	
	
	// METHODS
	
	public void advanceTime(double deltaTime) {
		fallBehavior(deltaTime);
	}
	
	private static int getRandomWeight() {
		Random random = new Random();
		return random.nextInt((MAX_WEIGHT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
	}

}