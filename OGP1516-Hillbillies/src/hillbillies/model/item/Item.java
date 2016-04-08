package hillbillies.model.item;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.item.ItemEntity;
import hillbillies.model.World;
import hillbillies.world.Position;

public abstract class Item {

	// FIELDS

	/**
	 * Variable registering the weight of this item .
	 */
	private final int weight;
	
	/**
	 * A value between 0 and 1 representing the
	 * chance to drop an item.
	 */
	public static final double dropChance = 0.25;

	/**
	 * Variable registering the maximal weight of this item .
	 */
	public static final int MAX_WEIGHT = 50;

	/**
	 * Variable registering the minimal weight of this item .
	 */
	public static final int MIN_WEIGHT = 10;


	// CONSTRUCTORS

	public Item() throws IllegalArgumentException {
		this.weight = getRandomWeight();
	}

	/**
	 * Initialize this new item with given weight.
	 * 
	 * @param  weight
	 *         The weight for this new item.
	 * @post   The weight of this new item is equal to the given
	 *         weight.
	 *       | new.getWeight() == weight
	 * @throws IllegalArgumentException
	 *         This new item cannot have the given weight as its weight.
	 *       | ! canHaveAsWeight(this.getWeight())
	 * @throws IllegalArgumentException
	 * 		   This new item cannot have the given world or position.
	 */
	public Item(int weight) throws IllegalArgumentException {
		if (! canHaveAsWeight(weight)) {
			throw new IllegalArgumentException();
		}

		this.weight = weight;
	}


	// GETTERS AND SETTERS

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
	 * @return 
	 *       | result == (weight >= MIN_WEIGHT) && (weight <= MAX_WEIGHT)
	 */
	@Raw
	public boolean canHaveAsWeight(int weight) {
		return (weight >= MIN_WEIGHT) && (weight <= MAX_WEIGHT);
	}


	// METHODS

	private static int getRandomWeight() {
		Random random = new Random();
		return random.nextInt((MAX_WEIGHT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
	}
	
	/**
	 * A method to drop this item in the world.
	 * 
	 * @param  world
	 * @param  position
	 * @return
	 */
	public void fall(World world, Position position) {
		new ItemEntity(world, position, this).spawn();
	}

	/**
	 * A method to make a cube in the world drop.
	 * 
	 * @param  world
	 * @param  position
	 * @return
	 */
	public static void drop(World world, Position position) {
		
	}

	/**
	 * A method to randomize whether to drop an item
	 * or not.
	 * 
	 * @return Whether to drop or not.
	 */
	public static boolean dropChance() {
		return dropChance(dropChance);
	}

	/**
	 * A method to randomize whether to drop an item
	 * or not.
	 * 
	 * @param  chance
	 * 		   The chance to drop.
	 * @return Whether to drop or not.
	 */
	public static boolean dropChance(double chance) {
		assert (0 <= chance) && (chance <= 1);
		Random random = new Random();

		return (random.nextDouble() < chance);
	}

}
