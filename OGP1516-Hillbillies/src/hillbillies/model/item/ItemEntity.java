package hillbillies.model.item;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Entity;
import hillbillies.model.World;
import hillbillies.model.item.Item;
import hillbillies.world.Position;

/**
 * A class of item entities.
 * 
 * @invar  Each item entity can have its item as item.
 *       | canHaveAsItem(this.getItem())
 */
public class ItemEntity extends Entity {

	// FIELDS

	/**
	 * Variable registering the item of this item entity.
	 */
	private final Item item;


	// CONSTRUCTORS
	
	/**
	 * Initialize this new item entity with given world, position and item.
	 * 
	 * @param  world
	 * 		   The world for this new item entity.
	 * @param  position
	 * 		   The position for this new item entity.
	 * @param  item
	 *         The item for this new item entity.
	 * @post   The item of this new item entity is equal to the given
	 *         item.
	 *       | new.getItem() == item
	 * @throws IllegalArgumentException
	 *         This new item entity cannot have the given item as its item.
	 *       | ! canHaveAsItem(this.getItem())
	 * @throws IllegalArgumentException
	 * 		   This new item entity cannot have the given world or position.
	 */
	public ItemEntity(World world, Position position, Item item) throws IllegalArgumentException {
		super(world, position);
		
		if (! canHaveAsItem(item)) {
			throw new IllegalArgumentException();
		}
		this.item = item;
	}


	// GETTERS AND SETTERS

	/**
	 * Return the item of this item entity.
	 */
	@Basic @Raw @Immutable
	public Item getItem() {
		return this.item;
	}

	/**
	 * Check whether this item entity can have the given item as its item.
	 *  
	 * @param  item
	 *         The item to check.
	 * @return 
	 *       | result == TODO
	 */
	@Raw
	public boolean canHaveAsItem(Item item) {
		if (item == null) {
			return false;
		}
		return true;
	}


	// METHODS

	public void advanceTime(double deltaTime) {
		fallBehavior(deltaTime);
	}

	public void spawn() {
		getWorld().addEntity(this);
	}
	
	public Item pickUp() {
		getWorld().removeEntity(this);
		return getItem();
	}
	
	
	// OVERRIDE
	
	@Override
	public String getEntityId() {
		return getItem().getClass().getName();
		
	}
	
	// TODO Hashcode and equals.
	
}