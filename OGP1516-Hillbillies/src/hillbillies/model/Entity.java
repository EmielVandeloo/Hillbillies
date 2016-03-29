package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.world.Position;
import hillbillies.model.World;

/**
 * A class of entities.
 * 
 * @invar  Each entity can have its world as world.
 *       | canHaveAsWorld(this.getWorld())
 * @invar  The position of each entity must be a valid position for any
 *         entity.
 *       | isValidPosition(getPosition())
 */
public abstract class Entity {

	// FIELDS
	
	/**
	 * Variable registering the world of this entity.
	 */
	private final World world;

	/**
	 * Variable registering the position of this entity.
	 */
	private Position position;

	/**
	 * Variable registering the whether the entity 
	 * is falling or not
	 */
	private boolean falling = false;
	
	/**
	 * Variable registering the maximal amount of this
	 * type allowed in the game world.
	 */
	public final int maxEntities = Integer.MAX_VALUE;


	// CONSTRUCTORS

	/**
	 * Initialize this new entity with given world.
	 * 
	 * @param  world
	 *         The world for this new entity.
	 * @param  position
	 * 		   The position for this new entity.
	 * @post   The world of this new entity is equal to the given
	 *         world.
	 *       | new.getWorld() == world
	 * @effect The position of this new entity is equal to the 
	 * 		   given world.
	 * 		 | setPosition(position)
	 * @throws IllegalArgumentException
	 *         This new entity cannot have the given world as its world.
	 *       | ! canHaveAsWorld(this.getWorld())
	 */
	public Entity(World world, Position position) throws IllegalArgumentException {
		if (! canHaveAsWorld(world)) {
			throw new IllegalArgumentException();
		}
		
		this.world = world;
		setPosition(position);
	}
	
	
	// GETTERS AND SETTERS

	/**
	 * Return the world of this entity.
	 */
	@Basic @Raw @Immutable
	public World getWorld() {
		return this.world;
	}

	/**
	 * Check whether this entity can have the given world as its world.
	 *  
	 * @param  world
	 *         The world to check.
	 * @return 
	 *       | result == TODO
	 */
	@Raw
	public static boolean canHaveAsWorld(World world) {
		return true;
	}

	/**
	 * Return the position of this entity.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Check whether the given position is a valid position for
	 * any entity.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return 
	 *       | result == TODO
	 */
	public boolean isValidPosition(Position position) {
		return world.getAt(position).isPassable();
	}

	/**
	 * Set the position of this log to the given position.
	 * 
	 * @param  position
	 *         The new position for this log.
	 * @post   The position of this new log is equal to
	 *         the given position.
	 *       | new.getPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         log.
	 *       | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setPosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		this.position = position;
	}

	public void startFalling() {
		this.falling = true;
	}

	public void stopFalling() {
		this.falling = false;
		setPosition(getPosition().getCenterPosition());
	}

	public boolean isFalling() {
		return falling;
	}


	// METHODS

	public abstract void advanceTime(double deltaTime);

	public void fallBehavior(double deltaTime) {
		if (! isFalling() && ! hasSupport()) {
			startFalling();
		}
		if (isFalling()) {
			updatePosition(deltaTime, World.FALL_VECTOR);

			if (hasReachedGround()) {
				stopFalling();
			}
		}
	}
	
	/**
	 * A method to check whether this entity has support or not.
	 * 
	 * @return True if the cube below the entity is not passable
	 * 		   or if it is outside the game world.
	 */
	public boolean hasSupport() {
		try {
			return !world.getAt(getPosition().add(Position.Z, -1)).isPassable();
		} catch (IllegalArgumentException e) {
			return true;
		}
	}

	private boolean hasReachedGround() {
		if ((position.z() % 1) > 0 && (position.z() % 1) < 0.5 && 
			 ! world.getAt(position.add(Position.Z, -1)).isPassable()) {
			return true;
		} else {
			return false;
		}
	}

	protected void updatePosition(double deltaTime, double[] vector) throws IllegalArgumentException {

		if (! isValidVector(vector)) {
			throw new IllegalArgumentException();
		}

		Position updatedPosition = new Position();

		for (int i = 0; i < 3; i++) {
			updatedPosition.setAt(i, getPosition().getAt(i) + vector[i] * deltaTime);
		}

		try {
			setPosition(updatedPosition);
		} catch (IllegalArgumentException e) {
			stopFalling();
		}
	}
	
	private static boolean isValidVector(double[] vector) {
		if (vector == null) {
			return false;
		} else if (vector.length != 3) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isValidWorld(World world) {
		// TODO What should a world have to be correct.
		return true;
	}

}