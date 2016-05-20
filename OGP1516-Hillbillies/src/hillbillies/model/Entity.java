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
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public abstract class Entity {

	// FIELDS
	
	/**
	 * Field representing the identification of this entity.
	 */
	public static final String ENTITY_ID = "entity";
	
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
	public final int MAX_ENTITIES = Integer.MAX_VALUE;
	
	/**
	 * Variable registering whether this entity is terminated.
	 */
	private boolean isTerminated;

	
	// CONSTRUCTORS

	/**
	 * Initialize this new entity with given world and given position.
	 * 
	 * @param  world
	 *         The world for this new entity.
	 * @param  position
	 * 		   The position for this new entity.
	 * @effect The world of this new entity is set to the given
	 *         world.
	 * @effect The position of this new entity is set to the given position.
	 */
	@Raw
	public Entity(World world, Position position) throws IllegalArgumentException {
		setWorld(world);
		setPosition(position.getCenterPosition());
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
			formerWorld.removeEntity(this);
			setWorld(null);
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
	public void setWorld(World world) throws IllegalArgumentException {
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
	 * @return True if and only if the given position is not effective or if the given
	 *         position is a valid position in the world to which this entity is attached.
	 */
	public boolean isValidPosition(Position position) {
		return position == null || getWorld().isValidPosition(position);
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
		if (getWorld() !=  null && !isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		this.position = position;
	}

	/**
	 * Return whether this entity is currently falling.
	 */
	@Basic
	public boolean isFalling() {
		return falling;
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
	 * Whether the entity can be on a position or not.
	 * 
	 * @param  position
	 * 		   The position to check.
	 * @return True if the position is supported.
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any entity.
	 */
	public boolean hasSupport(Position position) throws IllegalArgumentException {
		if (!isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		return getWorld().isPassable(position);
	}
	
	/**
	 * Return the entity ID of an entity.
	 */
	@Basic
	public String getEntityId() {
		return ENTITY_ID;
	}

	/**
	 * Execute the fall behavior of this entity according to the given time step.
	 * 
	 * @param  deltaTime
	 *         The time step by which to execute this entity's fall behavior.
	 * @effect Update the position according to the given time step and the
	 *         fall vector.
	 * @effect If this entity has reached ground, the entity stops falling and
	 *         the position is set to the center position of the cube this entity
	 *         currently occupies.
	 */
	public void fallBehavior(double deltaTime) {
		if (getPosition() == null || getWorld() == null) {
			return;
		}
		
		if (! hasSupport(getPosition()) && ! isFalling()) {
			startFalling();
		}
		if (isFalling()) {
			updatePosition(deltaTime, World.FALL_VECTOR);
			
			if (hasReachedGround()) {
				stopFalling();
				setPosition(getPosition().getCenterPosition());
			}
		}
	}

	/**
	 * Check whether this entity has reached ground.
	 * 
	 * @return True if and only if this entity finds itself in the bottom
	 *         half of the cube it currently occupies, and if there is an
	 *         underlying solid in the world to which this entity is attached.
	 */
	private boolean hasReachedGround() {
		return (getPosition().z() % 1) > 0 && (getPosition().z() % 1) < 0.5 && 
			 getWorld().hasUnderlyingSolid(getPosition());
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
			setPosition(getPosition().getCenterPosition());
		}
	}
	
	/**
	 * Check whether the given vector is a valid vector.
	 * 
	 * @param  vector
	 *         The vector to check.
	 * @return True if and only if the given vector is effective and has length 3.
	 */
	private static boolean isValidVector(double[] vector) {
		return (vector != null && vector.length == 3);
	}
	
}