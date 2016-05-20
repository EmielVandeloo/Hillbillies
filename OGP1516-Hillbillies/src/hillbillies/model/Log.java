package hillbillies.model;

import hillbillies.model.World;
import be.kuleuven.cs.som.annotate.Basic;
import hillbillies.model.ItemEntity;
import hillbillies.world.Position;

/**
 * A class of logs.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class Log extends ItemEntity {
	
	//FIELDS
	
	/**
	 * Field representing the identification of this entity.
	 */
	public static final String ENTITY_ID = "item_entity:log";
	
	@Override @Basic
	public String getEntityId() {
		return ENTITY_ID;
	}
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new log in the given world at the given position.
	 * 
	 * @param  world
	 *         The world to initialize this new log in.
	 * @param  position
	 *         The position to initialize this new log at.
	 * @effect This log is initialized as a new item entity in the given world
	 *         at the given position.
	 */
	public Log(World world, Position position) throws IllegalArgumentException {
		super(world, position);
	}
	
	
	// METHODS
	
	/**
	 * Drop a log in the given world at the given position.
	 * 
	 * @param  world
	 *         The world to drop a log in.
	 * @param  position
	 *         The position to drop a log at.
	 * @effect If an item entity will be dropped, a new log is added
	 *         to the given world at the given position.
	 */
	public static void drop(World world, Position position) {
		if (ItemEntity.willDrop()) {
			new Log(world, position).spawn();;
		}
	}
}