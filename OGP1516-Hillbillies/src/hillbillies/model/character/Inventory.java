package hillbillies.model.character;

import java.util.ArrayList;
import java.util.List;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.ItemEntity;
import hillbillies.model.World;
import hillbillies.world.Position;

/**
 * A class of inventories.
 *
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class Inventory {

	// FIELDS
	
	/**
	 * Variable registering the maximal weight of this inventory.
	 */
	private double maximalWeight;
	
	/**
	 * Variable registering the maximal number of items this inventory can contain.
	 */
	private int maximalItemCount;
	
	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;

	/**
	 * Variable referencing the items in this inventory.
	 */
	private final List<ItemEntity> items = new ArrayList<ItemEntity>();

	// CONSTRUCTOR
	
	/**
	 * Initialize this inventory with given maximal weight and given maximal number of items.
	 * 
	 * @param  maxWeight
	 *         The maximal weight for this new inventory.
	 * @param  maxNbItems
	 *         The maximal number of items this new inventory can contain.
	 * @effect The maximal weight of this inventory is set to the given weight.
	 * @effect The maximal number of items of this inventory is set to the given number.
	 * @throws IllegalArgumentException
	 *         This inventory cannot have the given weight as its maximal weight or the given
	 *         number of items as its maximal number of items.
	 *         
	 */
	public Inventory(double maxWeight, int maxNbItems) 
			throws IllegalArgumentException {
		this.setMaximalWeight(maxWeight);
		this.setMaximalItemCount(maxNbItems);
	}
	
	/**
	 * Initialize this inventory with infinite weight and the maximal integer value as maximal
	 * number of items.
	 * 
	 * @effect This inventory is initialized as a new inventory with infinite weight and the maximal
	 *         integer value as maximal number of items.
	 */
	public Inventory() {
		this(Double.POSITIVE_INFINITY, Integer.MAX_VALUE);
	}

	// GETTERS AND SETTERS

	/**
	 * Return the maximal weight of this inventory.
	 */
	@Basic @Raw
	public double getMaximalWeight() {
		return this.maximalWeight;
	}
	
	/**
	 * Return the weight of this inventory.
	 */
	@Basic @Raw
	public int getWeight() {
		int weight = 0;
		for (int i=0 ; i<getNbItems() ; i++) {
			weight += items.get(i).getWeight();
		}
		return weight;
	}

	/**
	 * Check whether the given maximal weight is a valid maximal weight for
	 * any inventory.
	 *  
	 * @param  maxWeight
	 *         The maximal weight to check.
	 * @return True if and only if the given weight is not negative.
	 */
	public static boolean isValidMaximalWeight(double maxWeight) {
		return (maxWeight >= 0);
	}

	/**
	 * Set the maximal weight of this inventory to the given maximal weight.
	 * 
	 * @param  maxWeight
	 *         The new maximal weight for this inventory.
	 * @post   The maximal weight of this new inventory is equal to
	 *         the given maximal weight.
	 * @throws IllegalArgumentException
	 *         The given maximal weight is not a valid maximal weight for any
	 *         inventory.
	 */
	@Raw
	public void setMaximalWeight(double maxWeight) throws IllegalArgumentException {
		if (! isValidMaximalWeight(maxWeight))
			throw new IllegalArgumentException();
		this.maximalWeight = maxWeight;
	}

	/**
	 * Return the maximal item count of this inventory.
	 */
	@Basic @Raw
	public double getMaximalItemCount() {
		return this.maximalItemCount;
	}

	/**
	 * Check whether the given maximal item count is a valid maximal item count for
	 * any inventory.
	 *  
	 * @param  maximal item count
	 *         The maximal item count to check.
	 * @return True if and only if the given item count is not negative.
	 */
	public static boolean isValidMaximalItemCount(double maxNbItems) {
		return (maxNbItems >= 0);
	}

	/**
	 * Set the maximal item count of this inventory to the given maximal item count.
	 * 
	 * @param  maxNbItems
	 *         The new maximal item count for this inventory.
	 * @post   The maximal item count of this new inventory is equal to
	 *         the given maximal item count.
	 * @throws IllegalArgumentException
	 *         The given maximal item count is not a valid maximal item count for any
	 *         inventory.
	 */
	@Raw
	public void setMaximalItemCount(int maxNbItems) throws IllegalArgumentException {
		if (! isValidMaximalItemCount(maxNbItems))
			throw new IllegalArgumentException();
		this.maximalItemCount = maxNbItems;
	}

	// INVENTORY-METHODS

	/**
	 * Return all the items associated with this inventory.
	 */
	@Basic @Raw
	public List<ItemEntity> getAllItems() {
		return items;
	}
	
	/**
	 * Return the item associated with this inventory at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the item to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of items for this inventory.
	 */
	@Basic @Raw
	public ItemEntity getItemAt(int index) throws IndexOutOfBoundsException {
		return getAllItems().get(index - 1);
	}

	/**
	 * Return the number of items associated with this inventory.
	 */
	@Basic @Raw
	public int getNbItems() {
		return getAllItems().size();
	}

	/**
	 * Check whether this inventory can have the given item
	 * as one of its items.
	 * 
	 * @param  item
	 *         The item to check.
	 * @return True if and only if the given item is effective.
	 */
	@Raw
	public boolean canHaveAsItem(ItemEntity item) {
		return (item != null);
	}

	/**
	 * Check whether this inventory can have the given item
	 * as one of its items at the given index.
	 * 
	 * @param  item
	 *         The item to check.
	 * @return False if the given index is not positive or exceeds the
	 *         number of items for this inventory + 1.
	 *         Otherwise, false if this inventory cannot have the given
	 *         item as one of its items.
	 *         Otherwise, true if and only if the given item is
	 *         not registered at another index than the given index.
	 */
	@Raw
	public boolean canHaveAsItemAt(ItemEntity item, int index) {
		if ((index < 1) || (index > getNbItems() + 1))
			return false;
		if (!this.canHaveAsItem(item))
			return false;
		for (int i = 1; i < getNbItems(); i++)
			if ((i != index) && (getItemAt(i) == item))
				return false;
		return true;
	}

	/**
	 * Check whether this inventory has the given item as one of its
	 * items.
	 * 
	 * @param  item
	 *         The item to check.
	 * @return The given item is registered at some position as
	 *         an item of this inventory.
	 */
	public boolean hasAsItem(@Raw ItemEntity item) {
		return items.contains(item);
	}

	/**
	 * Add the given item to the list of items of this inventory.
	 * 
	 * @param  item
	 *         The item to be added.
	 * @pre    The given item is effective and this inventory does not yet have the given
	 *         item as one of its items.
	 * @post   The number of items of this inventory is
	 *         incremented by 1.
	 * @post   This inventory has the given item as its very last item.
	 */
	@Raw
	public void addItem(@Raw ItemEntity item) {
		assert (item != null) && (!hasAsItem(item));
		if (items.size() < getMaximalItemCount()) {
			items.add(item);
		}
	}
	
	/**
	 * Remove the given item from the list of items of this inventory.
	 *
	 * @param  item
	 *         The item to remove.
	 * @effect If this inventory contains the given item, that item is removed from this inventory.
	 */
	@Raw
	public void removeItem(@Raw ItemEntity item) {
		if (getAllItems().contains(item)) {
			getAllItems().remove(item);
		}
	}

	// SPECIFIC METHODS

	/**
	 * A method to retrieve the first item;
	 * 
	 * @return The first item in this inventory.
	 * 		   Null if this inventory is empty.
	 */
	public ItemEntity getItem() {
		if (items.isEmpty()) {
			return null;
		} else {
			return items.get(0);
		}
	}
	
	/**
	 * A method to drop an item out of this inventory
	 * into the given world at a given position.
	 * 
	 * @param  item
	 * 		   The item to drop.
	 * @param  pos
	 *         The position at which to drop the given item.
	 * @param  world
	 *         The world in which to drop the given item.
	 * @effect The position of the item is set to the center position
	 *         of the given position, the world is set to the given world,
	 *         the entity is spawned in the given world and the item is 
	 *         removed from this inventory.
	 */
	public void dropItem(ItemEntity item, Position pos, World world) {
		item.setPosition(pos.getCenterPosition());
		item.setWorld(world);
		world.addEntity(item);
		removeItem(item);
	} 
	
	/**
	 * Return whether this inventory is empty or not.
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}

	// TERMINATE

	/**
	 * Drop all items of this inventory at the given position in the given world.
	 *
	 * @param  pos
	 *         The position to drop the items at.
	 * @param  world
	 *         The world in which to drop the items.
	 * @effect Each item in this inventory is dropped at the given position in the given world.
	 */
	public void dropInventory(Position pos, World world) {
		for (ItemEntity item : items) {
			dropItem(item, pos, world);
		}
	}
	
	/**
	 * Terminate this inventory.
	 * 
	 * @post The new terminated state of this inventory is equal to true.
	 */
	public void terminate() {
		this.isTerminated = true;
	}

	/**
	 * Return a boolean indicating whether or not this inventory
	 * is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
}
