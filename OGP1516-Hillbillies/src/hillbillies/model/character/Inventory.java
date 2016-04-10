package hillbillies.model.character;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.ItemEntity;
import hillbillies.model.World;
import hillbillies.world.Position;
import hillbillies.world.WorldObject;

/**
 * A class of inventories.
 * 
 * @invar  The maximal weight of each inventory must be a valid maximal weight for any
 *         inventory.
 *       | isValidMaximalWeight(getMaximalWeight())
 * @invar  The maximal item count of each inventory must be a valid maximal item count for any
 *         inventory.
 *       | isValidMaximalItemCount(getMaximalItemCount())
 * @invar   Each inventory must have proper items.
 *        | hasProperItems()
 * @invar  The position of each inventory must be a valid position for any
 *         inventory.
 *       | isValidPosition(getPosition())
 * 
 * @author Pieter-Jan
 */
public class Inventory extends WorldObject {

	// FIELDS

	/**
	 * Variable registering the maximal weight of this inventory.
	 */
	private double maxWeight = Double.POSITIVE_INFINITY;

	/**
	 * Variable registering the maximal item count of this inventory.
	 */
	private double maxNbItems = Double.POSITIVE_INFINITY;

	/**
	 * Variable referencing a list collecting all the items
	 * of this inventory.
	 * 
	 * @invar  The referenced list is effective.
	 *       | items != null
	 * @invar  Each item registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each item in items:
	 *       |   ( (item != null) &&
	 *       |     (! item.isTerminated()) )
	 * @invar  No item is registered at several positions
	 *         in the referenced list.
	 *       | for each I,J in 0..items.size()-1:
	 *       |   ( (I == J) ||
	 *       |     (items.get(I) != items.get(J))
	 */
	private final List<ItemEntity> items = new ArrayList<ItemEntity>();

	/**
	 * Variable registering the position of this inventory.
	 */
	private Position position;


	// CONSTRUCTOR

	/**
	 * Initialize this new inventory as a non-terminated inventory with 
	 * no items yet and with given maximal weight, maximal item count and position.
	 * 
	 * @param  world
	 *         The world for this new inventory.
	 * @param  position
	 *         The position for this new inventory.
	 * @post   This new inventory has no items yet.
	 *       | new.getNbItems() == 0
	 * @effect The maximal weight of this new inventory is set to
	 *         the given maximal weight.
	 *       | this.setMaximalWeight(Double.POSITIVE_INFINITY)
	 * @effect The maximal item count of this new inventory is set to
	 *         the given maximal item count.
	 *       | this.setMaximalItemCount(1)
	 * @effect The position of this new inventory is set to
	 *         the given position.
	 *       | this.setPosition(position)
	 */
	public Inventory(World world, Position position) throws IllegalArgumentException {
		super(world);

		this.setMaximalWeight(Double.POSITIVE_INFINITY);
		this.setMaximalItemCount(1);
		this.setPosition(position);
	}

	/**
	 * Initialize this new inventory as a non-terminated inventory with 
	 * no items yet and with given maximal weight, maximal item count and position.
	 * 
	 * @param  world
	 *         The world for this new inventory.
	 * @param  position
	 *         The position for this new inventory.
	 * @param  maxWeight
	 *         The maximal weight for this new inventory.
	 * @param  maxNbItems
	 *         The maximal item count for this new inventory.
	 * @post   This new inventory has no items yet.
	 *       | new.getNbItems() == 0
	 * @effect The maximal weight of this new inventory is set to
	 *         the given maximal weight.
	 *       | this.setMaximalWeight(maxWeight)
	 * @effect The maximal item count of this new inventory is set to
	 *         the given maximal item count.
	 *       | this.setMaximalItemCount(maxNbItems)
	 * @effect The position of this new inventory is set to
	 *         the given position.
	 *       | this.setPosition(position)
	 */
	public Inventory(World world, Position position, double maxWeight, double maxNbItems) 
			throws IllegalArgumentException {
		super(world);

		this.setMaximalWeight(maxWeight);
		this.setMaximalItemCount(maxNbItems);
		this.setPosition(position);
	}


	// GETTERS AND SETTERS

	/**
	 * Return the maximal weight of this inventory.
	 */
	@Basic @Raw
	public double getMaximalWeight() {
		return this.maxWeight;
	}

	/**
	 * Check whether the given maximal weight is a valid maximal weight for
	 * any inventory.
	 *  
	 * @param  maximal weight
	 *         The maximal weight to check.
	 * @return 
	 *       | result == (maxWeight >= 0)
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
	 *       | new.getMaximalWeight() == maxWeight
	 * @throws IllegalArgumentException
	 *         The given maximal weight is not a valid maximal weight for any
	 *         inventory.
	 *       | ! isValidMaximalWeight(getMaximalWeight())
	 */
	@Raw
	public void setMaximalWeight(double maxWeight) throws IllegalArgumentException {
		if (! isValidMaximalWeight(maxWeight))
			throw new IllegalArgumentException();
		this.maxWeight = maxWeight;
	}

	/**
	 * Return the maximal item count of this inventory.
	 */
	@Basic @Raw
	public double getMaximalItemCount() {
		return this.maxNbItems;
	}

	/**
	 * Check whether the given maximal item count is a valid maximal item count for
	 * any inventory.
	 *  
	 * @param  maximal item count
	 *         The maximal item count to check.
	 * @return 
	 *       | result == (maxNbItems >= 0)
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
	 *       | new.getMaximalItemCount() == maxNbItems
	 * @throws IllegalArgumentException
	 *         The given maximal item count is not a valid maximal item count for any
	 *         inventory.
	 *       | ! isValidMaximalItemCount(getMaximalItemCount())
	 */
	@Raw
	public void setMaximalItemCount(double maxNbItems) throws IllegalArgumentException {
		if (! isValidMaximalItemCount(maxNbItems))
			throw new IllegalArgumentException();
		this.maxNbItems = maxNbItems;
	}

	/**
	 * Return the position of this inventory.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Check whether the given position is a valid position for
	 * any inventory.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return 
	 *       | result == (position != null)
	 */
	public static boolean isValidPosition(Position position) {
		return (position != null);
	}

	/**
	 * Set the position of this inventory to the given position.
	 * 
	 * @param  position
	 *         The new position for this inventory.
	 * @post   The position of this new inventory is equal to
	 *         the given position.
	 *       | new.getPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         inventory.
	 *       | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setPosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
	}


	// INVENTORY-METHODS

	/**
	 * Return the item associated with this inventory at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the item to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of items for this inventory.
	 *       | (index < 1) || (index > getNbItems())
	 */
	@Basic
	@Raw
	public ItemEntity getItemAt(int index) throws IndexOutOfBoundsException {
		return items.get(index - 1);
	}

	/**
	 * Return the number of items associated with this inventory.
	 */
	@Basic
	@Raw
	public int getNbItems() {
		return items.size();
	}

	/**
	 * Check whether this inventory can have the given item
	 * as one of its items.
	 * 
	 * @param  item
	 *         The item to check.
	 * @return True if and only if the given item is effective
	 *         and that item can have this inventory as its inventory.
	 *       | result ==
	 *       |   (item != null)
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
	 *       | if ( (index < 1) || (index > getNbItems()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this inventory cannot have the given
	 *         item as one of its items.
	 *       | else if ( ! this.canHaveAsItem(item) )
	 *       |   then result == false
	 *         Otherwise, true if and only if the given item is
	 *         not registered at another index than the given index.
	 *       | else result ==
	 *       |   for each I in 1..getNbItems():
	 *       |     (index == I) || (getItemAt(I) != item)
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
	 * Check whether this inventory has proper items attached to it.
	 * 
	 * @return True if and only if this inventory can have each of the
	 *         items attached to it as a item at the given index,
	 *         and if each of these items references this inventory as
	 *         the inventory to which they are attached.
	 *       | result ==
	 *       |   for each I in 1..getNbItems():
	 *       |     ( this.canHaveAsItemAt(getItemAt(I))
	 */
	public boolean hasProperItems() {
		for (int i = 1; i <= getNbItems(); i++) {
			if (!canHaveAsItemAt(getItemAt(i), i))
				return false;
		}
		return true;
	}

	/**
	 * Check whether this inventory has the given item as one of its
	 * items.
	 * 
	 * @param  item
	 *         The item to check.
	 * @return The given item is registered at some position as
	 *         a item of this inventory.
	 *       | for some I in 1..getNbItems():
	 *       |   getItemAt(I) == item
	 */
	public boolean hasAsItem(@Raw ItemEntity item) {
		return items.contains(item);
	}

	/**
	 * Add the given item to the list of items of this inventory.
	 * 
	 * @param  item
	 *         The item to be added.
	 * @pre    The given item is effective and already references
	 *         this inventory, and this inventory does not yet have the given
	 *         item as one of its items.
	 *       | (item != null) && (item.getInventory() == this) &&
	 *       | (! this.hasAsItem(item))
	 * @post   The number of items of this inventory is
	 *         incremented by 1.
	 *       | new.getNbItems() == getNbItems() + 1
	 * @post   This inventory has the given item as its very last item.
	 *       | new.getItemAt(getNbItems()+1) == item
	 */
	public void addItem(@Raw ItemEntity item) {
		assert (item != null) && (!this.hasAsItem(item));
		if (items.size() < getMaximalItemCount()) {
			items.add(item);
			getWorld().removeEntity(item);
		}
	}

	/**
	 * Remove the given item from the list of items of this inventory.
	 * 
	 * @param  item
	 *         The item to be removed.
	 * @pre    The given item is effective, this inventory has the
	 *         given item as one of its items, and the given
	 *         item does not reference any inventory.
	 *       | (item != null) &&
	 *       | this.hasAsItem(item) &&
	 *       | (item.getInventory() == null)
	 * @post   The number of items of this inventory is
	 *         decremented by 1.
	 *       | new.getNbItems() == getNbItems() - 1
	 * @post   This inventory no longer has the given item as
	 *         one of its items.
	 *       | ! new.hasAsItem(item)
	 * @post   All items registered at an index beyond the index at
	 *         which the given item was registered, are shifted
	 *         one position to the left.
	 *       | for each I,J in 1..getNbItems():
	 *       |   if ( (getItemAt(I) == item) and (I < J) )
	 *       |     then new.getItemAt(J-1) == getItemAt(J)
	 */
	@Raw
	public void removePurchase(ItemEntity item) {
		assert (item != null) && this.hasAsItem(item);
		items.remove(item);
	}


	// SPECIFIC METHODS

	/**
	 * A method to retrieve the first item;
	 * 
	 * @return The first item in this inventory.
	 * 		   Null when empty.
	 */
	public ItemEntity getItem() {
		if (items.isEmpty()) {
			return null;
		} else {
			return items.get(0);
		}
	}

	/**
	 * A method to place the item in the world at
	 * the position of this inventory.
	 * 
	 * @param item
	 * 		  The item to place in the world.
	 */
	public void placeInWorld(ItemEntity item) {
		item.setPosition(getPosition().clone());
		item.spawn();
	}
	
	/**
	 * A method to drop an item out of this inventory
	 * into the world.
	 * 
	 * @param  item
	 * 		   The item to drop.
	 * @effect Place the item in the world.
	 * 		 | placeInWorld(item);
	 * @effect Remove the item from this inventory.
	 * 		 | removePurchase(item);
	 */
	public void dropItem(ItemEntity item) {
		placeInWorld(item);
		removePurchase(item);
	} 
	
	/**
	 * Return whether this inventory is empty or not.
	 * 
	 * @return items.isEmpty();
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}


	// TERMINATE

	/**
	 * Terminate this inventory.
	 *
	 * @post   This inventory  is terminated.
	 *       | new.isTerminated()
	 * @post   The list containing the items will be emptied.
	 * @effect All items will be dropped in the world.
	 */
	public void terminate() {
		this.isTerminated = true;

		for (ItemEntity item : items) {
			placeInWorld(item);
			items.remove(item);
		}
	}

	/**
	 * Return a boolean indicating whether or not this inventory
	 * is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;

}
