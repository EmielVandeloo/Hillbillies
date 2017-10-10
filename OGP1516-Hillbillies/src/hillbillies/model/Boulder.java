package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import hillbillies.model.ItemEntity;
import hillbillies.world.Position;

/**
 * A class of boulders.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class Boulder extends ItemEntity {
	
	/**
	 * Field representing the identification of this entity.
	 */
	public static final String ENTITY_ID = "item_entity:boulder";
	
	@Override @Basic
	public String getEntityId() {
		return ENTITY_ID;
	}

	/**
	 * Initialize this new boulder in the given world at the given position.
	 * 
	 * @param  world
	 *         The world to initialize this new boulder in.
	 * @param  position
	 *         The position to initialize this new boulder at.
	 * @effect This boulder is initialized as a new item entity in the given world
	 *         at the given position.
	 */
	public Boulder(World world, Position position) {
		super(world, position);
	}
	
	/**
	 * Drop a boulder in the given world at the given position.
	 * 
	 * @param  world
	 *         The world to drop a boulder in.
	 * @param  position
	 *         The position to drop a boulder at.
	 * @effect If an item entity will be dropped, a new boulder is added
	 *         to the given world at the given position.
	 */
	public static void drop(World world, Position position) {
		if (ItemEntity.willDrop()) {
			new Boulder(world, position).spawn();
		}
	}
}