package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.world.Position;
import hillbillies.model.World;

/**
 * A class of entities.
 * 
 * @invar  Each entity can have its world as world.
 * @invar  The position of each entity must be a valid position for any
 *         entity.
 *         
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 2: 10/04/2016
 */
public abstract class Entity {

	// FIELDS
	
	/**
	 * Variable registering the world of this entity.
	 */
	private World world;

	/**
	 * Variable registering the position of this entity.
	 */
	protected Position position;

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
	
	/**
	 * Variable registering whether this entity is terminated.
	 */
	private boolean isTerminated;


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
	 * @effect The position of this new entity is set to the given position.
	 * @throws IllegalArgumentException
	 *         This new entity cannot have the given world as its world or the given
	 *         position as its initial position.
	 */
	@Raw
	public Entity(World world, Position position) throws IllegalArgumentException {
		if (! canHaveAsWorld(world)) {
			throw new IllegalArgumentException();
		}
		
		this.world = world;
		setPosition(position);
	}
	
	/**
	 * Initialize this new entity with given position.
	 * 
	 * @param  position
	 *         The position for this new entity.
	 * @effect The position of this new entity is set to the given position.
	 * @throws IllegalArgumentException
	 *         This new entity cannot have the given position as its initial position.
	 */
	@Raw
	public Entity(Position position) throws IllegalArgumentException {
		setPosition(position);
	}
	
	
	// DESTRUCTOR
	
	/**
	 * Return whether this entity is terminated.
	 */
	@Raw @Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this entity.
	 * 
	 * @post This entity is terminated.
	 * @post This entity no longer references a world as the world
	 *       to which it is attached.
	 * @post If this entity was not already terminated, the world to 
	 *       which this entity was attached no longer references this
	 *       entity as an active entity of the game world.
	 */
	public void terminate() {
		if (!isTerminated()) {
			World formerWorld = getWorld();
			this.isTerminated = true;
			setWorld(null);
			formerWorld.removeEntity(this);
		}
	}
	
	// GETTERS AND SETTERS

	/**
	 * Return the world of this entity.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Set the world of this entity to the given world.
	 * 
	 * @post   The world to which this entity is attached is
	 *         equal to the given world.
	 * @throws IllegalArgumentException
	 *         The entity cannot have the given world as the world
	 *         to which it is attached.
	 */
	@Raw
	void setWorld(World world) throws IllegalArgumentException {
		if (!canHaveAsWorld(world)) {
			throw new IllegalArgumentException();
		}
		this.world = world;
	}

	/**
	 * Check whether this entity can have the given world as its world.
	 *  
	 * @param  world
	 *         The world to check.
	 * @return If this entity is terminated, true if and only if the given
	 *         world is not effective. Else, true if and only if the given
	 *         world is effective and not yet terminated.
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		if (isTerminated()) {
			return (world == null);
		} else {
			return ((world != null) && !world.isTerminated());
		}
	}
	
	/**
	 * Check whether this entity has a proper world.
	 * 
	 * @return True if and only if this entity can have its world as the
	 *         world to which it is attached, and if the world references
	 *         this entity as an active entity in the game world.
	 */
	@Raw
	public boolean hasProperWorld() {
		return (canHaveAsWorld(getWorld()) && getWorld().hasAsEntity(this));
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
	 * @param  world
	 *         The world to check the position in.
	 * @return False if the position is not a valid position in
	 *         the given world, or if the given world is not effective.
	 * @return False if the given position is not passable in the
	 *         given world.
	 * @return False if the given position has no underlying solid in the
	 *         given world.
	 * @return True otherwise.
	 */
	public boolean isValidEntityPosition(Position position, World world) {
		if (!world.isValidPosition(position) || world == null) {
			return false;
		}
		if (!world.getAt(position).isPassable()) {
			return false;
		}
		if (!world.hasUnderlyingSolid(position)) {
			return false;
		}
		return true;
	}

	/**
	 * Set the position of this entity to the given position.
	 * 
	 * @param  position
	 *         The new position for this entity.
	 * @post   The position of this entity is equal to
	 *         the given position.
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         entity.
	 */
	@Raw
	public void setPosition(Position position) throws IllegalArgumentException {
		if (!isValidEntityPosition(position, getWorld())) {
			throw new IllegalArgumentException();
		}
		this.position = position;
	}

	/**
	 * Make this entity start falling.
	 * 
	 * @post The new falling state of this entity is equal to true.
	 */
	public void startFalling() {
		this.falling = true;
	}

	/**
	 * Make this unit stop falling.
	 * 
	 * @post   The new falling state of this entity is equal to false.
	 */
	public void stopFalling() {
		this.falling = false;
	}

	/**
	 * Return whether this entity is currently falling.
	 */
	@Basic
	public boolean isFalling() {
		return falling;
	}

	// METHODS

	/**
	 * Advance the state of this entity by the given time step.
	 * 
	 * @param deltaTime
	 *        The time step, in seconds, by which to advance this entity's
	 *        state.
	 */
	public abstract void advanceTime(double deltaTime);

	/**
	 * A method to spawn a newly created entity in the game world.
	 * 
	 * @param  entity
	 * 		   The entity to spawn.
	 * @return True if the operation was successful.
	 * 		   Problems may occur when the maximum amount of 
	 * 		   this type was met.
	 */
	public abstract void spawn();
	
	public String getEntityId() {
		return getClass().getName();
	}

	/**
	 * Execute the fall behaviour of this entity according to the given time step.
	 * 
	 * @param  deltaTime
	 *         The time step by which to execute this entity's fall behaviour.
	 * @effect If this entity is currently not falling and if it currently has
	 *         no support, this entity starts falling.
	 * @effect If this entity is currently falling, its position is updated
	 *         according to the fall vector. If it reaches ground after this
	 *         update, the entity stops falling.
	 */
	public void fallBehavior(double deltaTime) {
		if (! isFalling() && hasSupport()) {
			startFalling();
		}
		
		// TODO Eerder werken met hasSupport.

		if (isFalling()) {
			updatePosition(deltaTime, World.FALL_VECTOR);
			if (hasReachedGround()) {
				stopFalling();
				setPosition(getPosition().getCenterPosition());
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
		return ! getWorld().hasUnderlyingSolid(getPosition());
	}

	/**
	 * Check whether this entity has reached ground.
	 * 
	 * @return True if and only if this entity finds itself in the bottom
	 *         half of the cube it currently occupies, and if there is an
	 *         underlying solid in the world to which this entity is attached.
	 */
	private boolean hasReachedGround() {
		if ((getPosition().z() % 1) > 0 && (getPosition().z() % 1) < 0.5 && 
			 getWorld().hasUnderlyingSolid(getPosition())) {
			return true;
		}
		return false;
	}

	/**
	 * Update the position of this entity according to the given time step and the given
	 * vector.
	 * 
	 * @param  deltaTime
	 *         The time step, in seconds, by which to update this entity's position.
	 * @param  vector
	 *         The vector by which to update this entity's position.
	 * @effect The position is set to the sum of the current position vector and the
	 *         product of the given vector with the given time step.
	 * @throws IllegalArgumentException
	 *         The given vector is not a valid vector for any entity
	 */
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
	
	/**
	 * Check whether the given vector is a valid vector.
	 * 
	 * @param  vector
	 *         The vector to check.
	 * @return False if the vector is not effective are is not of length 3.
	 * @return True otherwise.
	 */
	private static boolean isValidVector(double[] vector) {
		if (vector == null || vector.length != 3) {
			return false;
		} 
		return true;
	}
}