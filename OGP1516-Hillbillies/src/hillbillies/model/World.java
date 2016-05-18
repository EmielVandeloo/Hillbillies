package hillbillies.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.character.JobStat;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.path.Path;
import hillbillies.path.PathFinder;
import hillbillies.util.ConnectedToBorder;
import hillbillies.world.Coordinate;
import hillbillies.world.Cube;
import hillbillies.world.Position;

/**
 * A class of game worlds.
 * 
 * @invar Each world must have proper entities.
 * @invar Each world must have proper factions.
 * 
 * @author Pieter-Jan Van den Broecke: EltCw
 * 		   Emiel Vandeloo: WtkCw
 * @version Final version Part 2: 10/04/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class World {

	// FIELDS
	
	/**
	 * Variable containing the maximum amount of factions
	 * allowed in this world.
	 */
	public static final int MAX_FACTIONS = 5;
	
	/**
	 * Variable containing the maximum amount of units allowed
	 * in this world.
	 */
	public static final int MAX_UNITS = 100;
	
	/**
	 * Variable containing the length of a side of a cube.
	 */
	public static final double CUBE_LENGTH = 1;
	
	/**
	 * A variable referencing the vector according to which an entity
	 * has to update its position when it is falling.
	 */
	public static final double[] FALL_VECTOR = new double[] {0, 0, -3};

	/**
	 * Variable referencing the size of the world in the X-direction.
	 */
	public final int sizeX;
	
	/**
	 * Variable referencing the size of the world in the Y-direction.
	 */
	public final int sizeY;
	
	/**
	 * Variable referencing the size of the world in the Z-direction.
	 */
	public final int sizeZ;

	/**
	 * Variable referencing a class containing algorithms
	 * on the subject of the world.
	 */
	private ConnectedToBorder border;
	
	/**
	 * Variable referencing a class to detect terrain changes.
	 */
	private TerrainChangeListener modelListener;
	
	/**
	 * Variable referencing the version of this game world.
	 */
	private int worldVersion;
	
	/**
	 * Variable referencing the game world.
	 */
	private Cube[][][] world;
	
	/**
	 * Variable registering the game time passed.
	 */
	private static BigDecimal gameTime = new BigDecimal(0.0);

	/**
	 * Variable referencing a set collecting all the entities
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 * @invar  Each entity registered in the referenced list is
	 *         effective and not yet terminated.
	 */
	private final Map<String, HashSet<Entity>> entities = new HashMap<>();
	
	/**
	 * Variable referencing a set collecting all the factions
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 * @invar  Each faction registered in the referenced list is
	 *         effective and not yet terminated.
	 */
	private final Set<Faction> factions = new HashSet<Faction>();
	
	/**
	 * Variable registering whether or not this world is terminated.
	 */
	private boolean isTerminated;
	
	// CONSTRUCTORS AND DESTRUCTOR 

	/**
	 * Initialize this new world as a non-terminated world with 
	 * no factions or entities yet.
	 * 
	 * @param  terrainTypes
	 * 		   An integer array containing the information needed to
	 * 		   construct the game world.
	 * @param  modelListener
	 * 		   A class to detect terrain changes.
	 * @post   modelListener is initialized as a new object of the
	 *         modelListener class. 
	 * @post   The size of the game world and the type of each cube of
	 *         the game world is initialized according to the given
	 *         terrain types.
	 * @post   This new world has no factions yet.
	 * @post   This new world has no entities yet.
	 * @post   border is initialized as a new object of the ConnectedToBorder
	 *         class.
	 * @effect The world version is set to 0.
	 * @throws IllegalArgumentException
	 *         The terrain types are not effective, or the model listener
	 *         is not effective.
	 * @throws IndexOutOfBoundsException
	 *         The terrain types contain one or more integers different
	 *         from 0,1,2 or 3.
	 */
	@Raw
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) 
			throws IllegalArgumentException, IndexOutOfBoundsException {
		
		if (terrainTypes == null) {
			throw new IllegalArgumentException();
		}
		if (modelListener == null) {
			throw new IllegalArgumentException();
		}
		
		this.modelListener = modelListener;
		this.sizeX = terrainTypes.length;
		this.sizeY = terrainTypes[0].length;
		this.sizeZ = terrainTypes[0][0].length;
		this.setWorldVersion(0);
		this.world = new Cube[sizeX][sizeY][sizeZ];
		this.border = new ConnectedToBorder(sizeX, sizeY, sizeZ);
		
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				for (int k = 0; k < sizeZ; k++) {
					world[i][j][k] = Cube.byId(terrainTypes[i][j][k]);
					if (getAt(new Position(i,j,k)).isPassable()) {
						border.changeSolidToPassable(i,j,k);
					}
				}
			}
		}
	}
	
	/**
	 * Return whether this world is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this world.
	 * 
	 * @effect Each active faction in this world is terminated.
	 * @effect Each active entity in this world is terminated.
	 */
	public void terminate() {
		if (!isTerminated()) {
			this.isTerminated = true;
			for (Faction faction : getAllFactions()) {
				faction.terminate();
			}
			for (HashSet<Entity> entityType : entities.values()) {
				for (Entity entity : entityType) {
					if (entity instanceof Unit) {
						((Unit) entity).terminate();
					} else {
						entity.terminate();
					}
				}
			}
		}
	}
	
	// GETTERS AND SETTERS

	/**
	 * Return the number of cubes in the x-direction.
	 */
	@Basic @Immutable
	public int getSizeX() {
		return this.sizeX;
	}

	/**
	 * Return the number of cubes in the y-direction.
	 */
	@Basic @Immutable
	public int getSizeY() {
		return this.sizeY;
	}
	
	/**
	 * Return the number of cubes in the z-direction.
	 */
	@Basic @Immutable
	public int getSizeZ() {
		return this.sizeZ;
	}
	
	/**
	 * Return the current version of this world.
	 */
	@Basic
	public int getWorldVersion() {
		return worldVersion;
	}
	
	/**
	 * Set the new version of this world to the given world version.
	 * 
	 * @param worldVersion
	 *        The version to set the world version to.
	 * @post  If the given world version is greater than or equal to zero, the
	 *        new world version of this world is equal to the given world version.
	 */
	@Model
	private void setWorldVersion(int worldVersion) {
		if (worldVersion >= 0) {
			this.worldVersion = worldVersion;
		}
	}

	/**
	 * Increment the version of this world by one.
	 * 
	 * @effect Set the new world version to the current world version
	 *         incremented by one.
	 */
	@Model
	private void updateWorldVersion() {
		setWorldVersion(getWorldVersion() + 1);
	}
	
	/**
	 * Return an object of the ConnectedToBorder class.
	 */
	public ConnectedToBorder getBorder() {
		return this.border;
	}

	/**
	 * Return the cube type of this world at the given position.
	 * 
	 * @param  position
	 *         The position to retrieve the cube type of.
	 * @return The cube type of this world at the given position.
	 * @throws IllegalArgumentException
	 *         The given position is not effective or not a valid position.
	 */
	public Cube getAt(Position position) {
		if (position == null) {
			throw new IllegalArgumentException();
		} else if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		
		int[] coord = position.convertToIntegerArray();
		return world[coord[0]][coord[1]][coord[2]];
	}

	/**
	 * Set the cube type of this world at the given position to the given cube type.
	 * 
	 * @param  position
	 *         The position of the cube to change the type of.
	 * @param  cube
	 *         The new cube type at the given position.
	 * @post   The cube at the given position is set to the given cube type.
	 * @effect The world version is updated.
	 * @effect The model listener is notified that the cube type has changed at the
	 *         given position.
	 * @throws IllegalArgumentException
	 *         The given position or the given cube are not effective.
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position.
	 */
	public void setAt(Position position, Cube cube) {
		if (position == null || cube == null) {
			throw new IllegalArgumentException();
		} else if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		
		Coordinate coord = position.convertToCoordinate();
		world[coord.x()][coord.y()][coord.z()] = cube;
		updateWorldVersion();
		modelListener.notifyTerrainChanged(coord.x(), coord.y(), coord.z());
	}

	// WORLD-METHODS

	/**
	 * Set the cube type of this world at the given position to the given cube type.
	 * 
	 * @param  position
	 *         The position of the cube to change the type of.
	 * @param  cube
	 *         The new cube type at the given position.
	 * @effect The cube type at the given position is set to the given cube type.
	 * @throws IllegalArgumentException
	 *         The given position or the given cube is not effective, the given cube type
	 *         is air or the current cube type at the given position is not air.
	 */
	// Not used
	public void place(Position position, Cube cube) throws IllegalArgumentException {
		if (position == null || cube == null) {
			throw new IllegalArgumentException();
		} else if (cube == Cube.AIR) {
			throw new IllegalArgumentException();
		} else if (getAt(position) != Cube.AIR) {
			throw new IllegalArgumentException();
		} else {
			setAt(position, cube);
		}
	}

	/**
	 * Remove the cube type at the given position.
	 * 
	 * @param  position
	 *         The position of the cube to change the type of.
	 * @effect The cube at the given position is dropped.
	 * @effect The cube type at the given position is set to air.
	 * @effect All cubes that are not anymore connected to the border
	 *         are removed.
	 */
	public void remove(Position position) {
		getAt(position).drop(this, position);
		setAt(position, Cube.AIR);
		List<int[]> list = 
				border.changeSolidToPassable((int) position.x(), (int) position.y(), (int) position.z());
		
		for (int[] coord : list) {
			remove(new Position(coord));
		}
	}

	/**
	 * Check whether the cube at the given position is passable.
	 * 
	 * @param  position
	 *         The position of the cube to check the type of.
	 * @return True if and only if the cube at the given position is of type
	 *         air or of type workshop.
	 */
	public boolean isPassable(Position position) {
		try {
			return getAt(position).isPassable();
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Advance the state of this world by the given time step.
	 * 
	 * @param  deltaTime
	 *         The time step by which to advance this world's state.
	 * @effect The given time step is made a valid time step.
	 * @effect The given time step is added to the game time.
	 * @effect For each entity in the list of all entities, do:
	 * @effect If the entity is not a unit, and if the entity is falling
	 *         or has no underlying solid cube, its fall behavior is executed.
	 * @effect Otherwise, if the check if the unit has to rest because three
	 *         minutes have passed yields true, the entity rests, whereafter
	 *         the entity's state is advanced by the given time step.
	 */
	public void advanceTime(double deltaTime) {
		makeValidDeltaTime(deltaTime);
		gameTime = gameTime.add(new BigDecimal(deltaTime));
		
		for (Entity entity : getAllEntities()) {
			if (!entity.isTerminated()) {
				if (!(entity instanceof Unit)) {
					if (entity.isFalling() || !hasUnderlyingSolid(entity.getPosition())) {
						entity.fallBehavior(deltaTime);
					}
				} else {
					if (checkThreeMinuteRest(deltaTime) == true) {
						((Unit) entity).rest();
					}
					entity.advanceTime(deltaTime);
				}
			}
		}
	}
	
	/**
	 * Make the given time step a valid time step.
	 * 
	 * @param deltaTime
	 *        The time step to control
	 * @post  If the given time step is negative, the time step is
	 *        set to a value greater than zero.
	 *      | if (deltaTime <= 0)
	 *      |   then deltaTime > 0
	 * @post  If the given time step is greater than 0.2, the time
	 *        step is set to 0.2.
	 *      | if (deltaTime > 0.2)
	 *      |   then deltaTime = 0.2
	 */
	private void makeValidDeltaTime(double deltaTime) {
		if (deltaTime <= 0) {
			deltaTime = 0.01;
		} else if (deltaTime > 0.2) {
			deltaTime = 0.2;
		}
	}
	
	/**
	 * Calculate a path between the given start position and the given end position.
	 * 
	 * @param  start
	 *         The start position.
	 * @param  end
	 *         The end position.
	 * @return A path between the given start position and the given end position.
	 */
	public Path calculatePathBetween(Position start, Position end) {
		return PathFinder.findPath(this, start, end);
	}
	
	/**
	 * Check whether this unit has to rest because three minutes of game time have passed.
	 * 
	 * @param  deltaTime
	 *         The time step, in seconds, by which the game time will increase.
	 * @return True if and only if the remainder of the division from the current game
	 *         time by 180 (three minutes) is greater than the remainder of the division
	 *         of the current game time incremented by the given time step by 180.
	 */
	@Model
	private boolean checkThreeMinuteRest(double deltaTime) {
		JobStat.BigDec1 = gameTime.remainder(JobStat.THREEMINUTEREST);
		JobStat.BigDec2 = gameTime.add(new BigDecimal(deltaTime)).remainder(JobStat.THREEMINUTEREST);
		return (JobStat.BigDec1.compareTo(JobStat.BigDec2) > 0);
	}

	// ENTITY METHODS

	/**
	 * Check whether this world has the given entity as one of its
	 * entities.
	 * 
	 * @param  entity
	 *         The entity to check.
	 * @return True if and only if the given entity is in the set of
	 *         all entities of the type of the given entity.
	 */
	public boolean hasAsEntity(@Raw Entity entity) {
		try {
			return getList(entity).contains(entity);
		} catch (NullPointerException e) {return false;}
	}

	/**
	 * Check whether this world can have the given entity
	 * as one of its entities.
	 * 
	 * @param  entity
	 *         The entity to check.
	 * @return True if and only if the given entity is effective
	 *         and that entity is a valid entity for a world.
	 */
	@Model
	private boolean canHaveAsEntity(Entity entity) {
		if (isTerminated()) {
			return entity == null;
		} else {
			return ((entity != null) && !entity.isTerminated());
		}
	}

	/**
	 * Check whether this world has proper entities attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *         entities attached to it as one of its entities,
	 *         and if each of these entities references this world as
	 *         the world to which they are attached.
	 */
	@Model
	private boolean hasProperEntities() {
		for (HashSet<Entity> entityType : entities.values()) {
			for (Entity entity : entityType) {
				
				if (!canHaveAsEntity(entity))
					return false;
				if (entity.getWorld() != this)
					return false;
			}
		}
		return true;
	}

	/**
	 * Return the number of active entities associated with this world.
	 *
	 * @return The size of the set of all active entities.
	 */
	@Basic
	public int getNbEntities() {
		return getAllEntities().size();
	}
	
	/**
	 * Return the number of entities of a given type.
	 * 
	 * @param  type
	 *         The entity type to search for.
	 * @return The size of the list of entities of the given type. Zero if no
	 *         such entities exist.
	 */
	@Model
	private int getNbEntitiesOf(String type) {
		if (entities.containsKey(type)) {
			return getAllEntitiesOf(type).size();
		} else {
			return 0;
		}
	}
	
	/**
	 * Return all active entities in this world.
	 */
	public HashSet<Entity> getAllEntities() {
		HashSet<Entity> allEntities = new HashSet<>();
		for (HashSet<Entity> entityType : entities.values()) {
			for (Entity entity : entityType) {
				allEntities.add(entity);
			}
		}
		return allEntities;
	}
	
	/**
	 * Return all active entities in this world of the given type. Null is
	 * returned if no such type exists.
	 */
	public HashSet<Entity> getAllEntitiesOf(String type) {
		if (! entities.containsKey(type)) {
			return null;
		} else {
			return entities.get(type);
		}
	}

	/**
	 * Add the given entity to the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be added.
	 * @post   If the maximum number of entities of the given entity type is not yet
	 *         reached and this world doesn't reference the given entity as one of its
	 *         entities, the world references the given entity as one of its entities.
	 * @throws IllegalArgumentException
	 *         The given entity is not effective or it doesn't reference this
	 *         world as the world to which it is attached.
	 */
	public void addEntity(@Raw Entity entity) throws IllegalArgumentException {
		if (entity == null || entity.getWorld() != this) {
			throw new IllegalArgumentException();
		}
		try { 
			if (getNbEntitiesOf(entity.getEntityId()) >= entity.MAX_ENTITIES) {
				return;
			} else if (entity instanceof Unit && getNbEntitiesOf(Unit.ENTITY_ID) >= MAX_UNITS) {
				return;
			} else if (! hasAsEntity(entity)) {
				getList(entity).add(entity);
			}
		} catch (NullPointerException e) {
			entities.put(entity.getEntityId(), new HashSet<Entity>());
			getList(entity).add(entity);
		}
	}

	/**
	 * Remove the given entity from the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be removed.
	 * @post   This world no longer has the given entity as
	 *         one of its entities.
	 * @throws This world doesn't have the given entity as one of its entities
	 *         or the given entity doesn't reference this world as its world.
	 */
	public void removeEntity(Entity entity) throws IllegalArgumentException {
		if (!hasAsEntity(entity) || entity.getWorld() == this) {
			throw new IllegalArgumentException();
		}
		getList(entity).remove(entity);
	}
	
	/**
	 * Return all entities that are of the same type as the given entity.
	 * 
	 * @param  entity
	 *         Object of the entity type of which to retrieve all entities.
	 * @return All entities of the same type as the given entity.
	 */
	@Model
	private HashSet<Entity> getList(Entity entity) {
		return entities.get(entity.getEntityId());
	}
	
	/**
	 * Return all entities of the given type at the given position.
	 * 
	 * @param  position
	 *         The position to search entities at.
	 * @param  type
	 *         The type to search entities of.
	 * @return A list of all entities of the given type at the given position.
	 */
	public ArrayList<Entity> getEntitiesAt(Position position, String type) {
		ArrayList<Entity> entities = new ArrayList<>();
		if (getAllEntitiesOf(type) != null) {
			for (Entity entity : getAllEntitiesOf(type)) {
				if (entity.getPosition().getCubePosition().equals(position)) {
					entities.add(entity);
				}
			}
		}
		return entities;
	}

	// FACTION-METHODS

	/**
	 * Check whether this world has the given faction as one of its
	 * factions.
	 * 
	 * @param  faction
	 *         The faction to check.
	 * @return True if and only if the set of active factions of this world
	 *         has the given faction as one of its factions.
	 */
	@Basic
	public boolean hasAsFaction(@Raw Faction faction) {
		return getAllFactions().contains(faction);
	}

	/**
	 * Check whether this world can have the given faction
	 * as one of its factions.
	 * 
	 * @param  faction
	 *         The faction to check.
	 * @return True if and only if the given faction is effective
	 *         and that faction can have this world as its world.
	 */
	@Model
	private boolean canHaveAsFaction(Faction faction) {
		return (faction != null) && (faction.canHaveAsWorld(this));
	}

	/**
	 * Check whether this world has proper factions attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *         factions attached to it as one of its factions,
	 *         and if each of these factions references this world as
	 *         the world to which they are attached.
	 */
	@Model
	private boolean hasProperFactions() {
		for (Faction faction : getAllFactions()) {
			if (!canHaveAsFaction(faction))
				return false;
			if (faction.getWorld() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of factions associated with this world.
	 *
	 * @return The size of the set of all active factions.
	 */
	@Basic
	public int getNbFactions() {
		return getAllFactions().size();
	}
	
	/**
	 * Return all active factions of this world.
	 */
	public HashSet<Faction> getAllFactions() {
		return new HashSet<>(factions);
	}

	/**
	 * Add the given faction to the set of factions of this world.
	 * 
	 * @param  faction
	 *         The faction to be added.
	 * @post   If the maximum number of active factions in this world is
	 *         not yet reached, and if this world doesn't reference the
	 *         given faction as one of its factions, this world has the 
	 *         given faction as one of its factions.
	 * @throws IllegalArgumentException
	 *         The given faction is not effective, or the world to which the
	 *         given faction is attached is not equal to this world, or this
	 *         world already references the given faction as one of its factions.
	 */
	public void addFaction(@Raw Faction faction) throws IllegalArgumentException {
		if (faction == null || faction.getWorld() != this || hasAsFaction(faction)) {
			throw new IllegalArgumentException();
		}
		if ((getNbFactions() < World.MAX_FACTIONS) && !hasAsFaction(faction)) {
			factions.add(faction);
		}
	}

	/**
	 * Remove the given faction from the set of factions of this world.
	 * 
	 * @param  faction
	 *         The faction to be removed.
	 * @post   This world no longer has the given faction as
	 *         one of its factions.
	 * @throws IllegalArgumentException
	 *         The given faction is not effective, or this world doesn't reference
	 *         the given faction as one of its factions, or the given faction still
	 *         references this world as the world to which it is attached.
	 */
	public void removeFaction(Faction faction) throws IllegalArgumentException {
		if (faction == null || !hasAsFaction(faction) || faction.getWorld() == this) {
			throw new IllegalArgumentException();
		}
		factions.remove(faction);
	}
	
	
	// USAGE-METHODS
	
	/**
	 * Return the total amount of active units in this world.
	 * 
	 * @return The size of the set of all active units.
	 */
	@Model
	private int getNbUnits() {
		return getAllUnits().size();
	}
	
	/**
	 * Return all active units of this world.
	 * 
	 * @return A set collecting all active units of this world.
	 */
	public HashSet<Unit> getAllUnits() {
		HashSet<Unit> units = new HashSet<>();
		for (Entity entity : getAllEntities()) {
			if (entity instanceof Unit) {
				units.add((Unit) entity);
			}
		}
		return units;
	}
	
	/**
	 * Return all boulders of this world.
	 * 
	 * @return A set collecting all boulders of this world.
	 */
	public HashSet<Boulder> getAllBoulders() {
		HashSet<Boulder> boulders = new HashSet<>();
		for (Entity entity : getAllEntities()) {
			if (entity instanceof Boulder) {
				boulders.add((Boulder) entity); 
			}
		}
		return boulders;
	}
	
	/**
	 * Return all logs of this world.
	 * 
	 * @return A set collecting all logs of this world.
	 */
	public HashSet<Log> getAllLogs() {
		HashSet<Log> logs = new HashSet<>();
		for (Entity entity : getAllEntities()) {
			if (entity instanceof Log) {
				logs.add((Log) entity);
			}
		}
		return logs;
	}

	/**
	 * Add a randomly initialized unit to this world.
	 * 
	 * @effect  
	 *         a randomly initialized unit is added to this world at a random position.
	 */
	@Model
	private void addUnit(boolean enableDefaultBehaviour) {
		if (!(getNbUnits() >= World.MAX_UNITS)) {
			addUnit(createRandomUnit(enableDefaultBehaviour));
		}
	}
	
	/**
	 * Add the given unit to this world.
	 * 
	 * @param  unit
	 *         The unit to add to this world.
	 * @effect If the maximum number of active units in this world is not yet reached,
	 *         the given unit references the priority faction as its faction.
	 * @effect If the maximum number of active units in this world is not yet reached,
	 *         the given unit is added as an active unit to the priority faction.
	 * @effect If the maximum number of active units in this world is not yet reached,
	 *         the given unit references this world as its world.
	 * @effect If the maximum number of active units in this world is not yet reached,
	 *         the given unit is added as an active entity of this world.
	 * @effect If the maximum number of active units in this world is not yet reached,
	 *         the priority faction references this world as the world to which it is attached.
	 * @effect If the maximum number of active units in this world is not yet reached,
	 *         and if this world doesn't reference the priority faction as one of its factions, 
	 *         this world has the priority factions as one of its factions.
	 */
	public void addUnit(@Raw Unit unit) {
		if (!(getNbUnits() >= World.MAX_UNITS)) {
			Faction faction = getPriorityFaction();
			unit.setFaction(faction);
			faction.addUnit(unit);
			unit.setWorld(this);
			addEntity(unit);
			faction.setWorld(this);
			if (!hasAsFaction(faction)) {
				addFaction(faction);
			}
		}
	}
	
	/**
	 * Initialize a unit with random initial attributes.
	 * 
	 * @param  enableDefaultBehavior
	 *         Whether or not the default behavior of the new unit is enabled.
	 * @effect A new unit with random initial attributes and a random initial
	 *         position is created, and that unit is added to this world.
	 * @return The newly initialized unit is returned.
	 */
	public Unit createRandomUnit(boolean enableDefaultBehavior) {
		int a = Unit.getMinInitialAttributeValue();
		int b = Unit.getMaxInitialAttributeValue();
		int[] position = getSpawnPosition().convertToIntegerArray();
		Unit unit = new Unit(position, Unit.getRandomizedName(), Unit.getRandomizedValueBetween(a, b), 
				Unit.getRandomizedValueBetween(a, b), Unit.getRandomizedValueBetween(a, b), 
				Unit.getRandomizedValueBetween(a, b), enableDefaultBehavior);
		addUnit(unit);
		return unit;
	}
	
	/**
	 * Return the faction a new unit has to reference as its faction.
	 * 
	 * @return A newly initialized faction if the maximum number of active factions
	 *         in this world is not yet reached.
	 * @return The faction with the smallest number of units attached to it otherwise.
	 */
	@Model
	private Faction getPriorityFaction() {
		Faction fac = null;
		int nb = Faction.MAX_UNITS;
		if (getNbFactions() < World.MAX_FACTIONS) {
			return new Faction(this, Faction.getRandomizedName());
		} else {
			for (Faction faction : getAllFactions()) {
				if (faction.getNbUnits() < nb) {
					nb = faction.getNbUnits();
					fac = faction;
				}
			}
			return fac;
		}
	}
	
	
	// POSITIONS
	
	/**
	 * Return all positions of this world.
	 */
	@Basic @Model
	private List<Position> getAllPositions() {
		List<Position> allPositions = new ArrayList<Position>();
		for (int x=0; x<getSizeX(); x++) {
			for (int y=0; y<getSizeY(); y++) {
				for (int z=0; z<getSizeZ(); z++) {
					Position pos = new Position(x,y,z);
					allPositions.add(pos);
				}
			}
		}
		return allPositions;
	}
	
	/**
	 * Return all passable positions of this world.
	 * 
	 * @return All positions of all the positions of this world that are passable.
	 */
	@Model
	private List<Position> getAllPassablePositions() {
		List<Position> allPassablePositions = new ArrayList<Position>();
		for (Position pos : getAllPositions()) {
			if (getAt(pos).isPassable()) {
				allPassablePositions.add(pos);
			}
		}
		return allPassablePositions;
	}
	
	/**
	 * Return all solid positions of this world.
	 * 
	 * @return All positions of all the positions of this world that are not passable.
	 */
	@Model
	private List<Position> getAllSolidPositions() {
		List<Position> allSolidPositions = new ArrayList<Position>();
		for (Position pos : getAllPositions()) {
			if (!getAt(pos).isPassable()) {
				allSolidPositions.add(pos);
			}
		}
		return allSolidPositions;
	}
	
	/**
	 * Return all passable positions of this world with a solid neighbouring cube.
	 * 
	 * @return All positions of all the passable positions that have a solid
	 *         neighbouring cube.
	 */
	@Model
	private List<Position> getAllPassablePositionsWithSolidNeighbour() {
		List<Position> allPassablePositionsWithSolidNeighbour = new ArrayList<Position>();
		for (Position pos : getAllPassablePositions()) {
			if (hasSolidNeighbour(pos)) {
				allPassablePositionsWithSolidNeighbour.add(pos);
			}
		}
		return allPassablePositionsWithSolidNeighbour;
	}
	
	/**
	 * Return all passable border positions of this world.
	 * 
	 * @return All positions of all the passable positions with a solid neighbouring cube
	 *         that have less than 26 neighbouring cubes.
	 */
	@Model
	private List<Position> getAllPassableBorderPositions() {
		List<Position> allPassableBorderPositions = new ArrayList<Position>();
		for (Position pos : getAllPassablePositionsWithSolidNeighbour()) {
			if (getAllNeighbours(pos).size() < 26) {
				allPassableBorderPositions.add(pos);
			}
		}
		return allPassableBorderPositions;
	}
	
	/**
	 * Return all passable positions of this world with a solid cube at an underlying z-level.
	 * 
	 * @return All positions of all passable positions with a solid neighbour that have
	 *         an underlying solid.
	 */
	@Model
	private List<Position> getAllPassablePositionsWithUnderlyingSolid() {
		List<Position> allPassablePositionsWithUnderlyingSolid = new ArrayList<Position>();
		for (Position pos : getAllPassablePositionsWithSolidNeighbour()) {
			if (getAt(pos).isPassable() && hasUnderlyingSolid(pos)) {
				allPassablePositionsWithUnderlyingSolid.add(pos);
			}
		}
		return allPassablePositionsWithUnderlyingSolid;
	}
	
	/**
	 * Return a random spawn position in this world.
	 * 
	 * @return A random position from the list of all possible spawn positions.
	 */
	@Model
	private Position getSpawnPosition() {
		List<Position> allPossibleSpawnPositions = getPossibleSpawnPositions();
		int random = new Random().nextInt(allPossibleSpawnPositions.size());
		
		return allPossibleSpawnPositions.get(random);
	}
	
	/**
	 * Return a list of all possible spawn positions in this world.
	 * 
	 * @return A list of passable cube positions that have a solid cube
	 *         on the underlying z-level or that have no underlying z-level.
	 */
	@Model
	private List<Position> getPossibleSpawnPositions() {
		return getAllPassablePositionsWithUnderlyingSolid();
	}
	
	/**
	 * Check whether the given position is a valid position of this world.
	 * 
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the position is effective, and if the position is
	 *         between the borders of this world in the x-, y- and z-direction.
	 */
	public boolean isValidPosition(Position position) {
		return  (position == null ||
				(position.x() >= 0 && position.x() < getSizeX()) &&
				(position.y() >= 0 && position.y() < getSizeY()) &&
				(position.z() >= 0 && position.z() < getSizeZ()));
	}
	
	/**
	 * Return a list of positions containing all directly neighbouring (not diagonally) positions
	 * of the given position.
	 * 
	 * @param  position
	 *         The position to search around
	 * @return A list containing all directly neighbouring (not diagonally) positions of the given position.
	 */
	@Model
	private List<Position> getDirectNeighbours(Position position) {
		List<Position> neighbours = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = -1; j < 2; j++) {
				if (j != 0) {
					try {
						neighbours.add(position.add(i, j));
					} catch (IllegalArgumentException e) {}
				}
			}
		}
		return neighbours;
	}
	
	/**
	 * Return a list of positions containing all neighbouring (also diagonally) positions
	 * of the given position.
	 * 
	 * @param  position
	 *         The position to search around.
	 * @return A list containing all neighbouring (also diagonally) positions of the given position.
	 */
	public List<Position> getAllNeighbours(Position position) {
		List<Position> neighbours = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					if (i != 0 || j != 0 || k != 0) {
						Position temp = position.add(Position.X, i).add(Position.Y, j).add(Position.Z, k);
						if (isValidPosition(temp)) {
							neighbours.add(temp);
						}
					}
				}
			}
		}
		return neighbours;
	}
	
	/**
	 * Return a list of positions containing this position and all neighbouring 
	 * (also diagonally) positions of the given position.
	 * 
	 * @param  position
	 *         The position to search around.
	 * @return A list containing this position and all neighbouring (also diagonally) 
	 * 		   positions of the given position..
	 */
	@Model
	List<Position> getAllNeighboursAndSame(Position position) {
		List<Position> neighbours = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					if (isValidPosition(position.add(Position.X, i).add(Position.Y, j).add(Position.Z, k))) {
						neighbours.add(position.add(Position.X, i).add(Position.Y, j).add(Position.Z, k));
					}	
				}
			}
		}
		return neighbours;
	}
	
	/**
	 * Return some random position of this world.
	 * 
	 * @return A random position of this world.
	 */
	@Model
	private Position getRandomPosition() {
		int x = new Random().nextInt(getSizeX());
		int y = new Random().nextInt(getSizeY());
		int z = new Random().nextInt(getSizeZ());
		
		return new Position(x,y,z);
	}
	
	/**
	 * Return a random reachable position starting from the given position.
	 * 
	 * @param  position
	 *         The position to start from.
	 * @return A random accessible position with a solid neighbouring cube that
	 *         can be reached starting from the given position.
	 * @throws IllegalArgumentException
	 *         The given position is not a valid unit position.
	 */
	@Model
	public Position getRandomReachablePositionStartingFrom(Position position) throws IllegalArgumentException {
		if (position == null || !hasSolidNeighbour(position) || !isPassable(position)) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<Position> detected = new ArrayList<>();
		ArrayList<Position> toEvaluate = new ArrayList<>();
		toEvaluate.add(position.getCenterPosition());
		
		while (! toEvaluate.isEmpty()) {
			Position candidate = toEvaluate.remove(0);
			detected.add(candidate);
			
			for (Position neighbour : getAllNeighbours(candidate)) {
				Position center = neighbour.getCenterPosition();
				
				if (isPassable(center) && hasSolidNeighbour(center)) {
					if (! detected.contains(center) && ! toEvaluate.contains(center)) {
						toEvaluate.add(center);
					}
				}
			}
		}
		
		return detected.get(new Random().nextInt(detected.size())).getCenterPosition();
	}
	
	/**
	 * Check whether there exists a path between the given start position and the given end
	 * position in this world.
	 * 
	 * @param  start
	 *         The start position.
	 * @param  end
	 *         The end position.
	 * @return True if and only if their exists a first position in the path between the given 
	 *         start position and the given end position. 
	 */
	@Deprecated
	public boolean existsPathBetween(Position start, Position end) {
		return (PathFinder.findPath(this, start, end).popNextPosition() != null);
	}
	
	/**
	 * Find a random position of this world in a given radius around a given position.
	 * 
	 * @param  Position
	 *         The position to search around.
	 * @param  radius
	 * 		   The radius around this position to search in.
	 * @return Return a random position that is no more than #radius cubes (also 
	 *         diagonally) from the given position.
	 */
	@Model @Raw
	private Position getRandomPosition(Position position, int radius) throws IllegalArgumentException {
		if (radius < 1) {
			throw new IllegalArgumentException();
		}		
		Random random = new Random();
		Position spot = new Position();
		while (true) {
			int multiplier = random.nextInt(radius) + 1;
			for (int i = 0; i < 3; i++) {
				spot.setAt(i, position.getAt(i) + World.getRandomDirection() * multiplier);
				}
			if (isValidPosition(spot)) {
				return spot;
			}
		}
	}
	
	/**
	 * Return a random neighbouring cube of this world (also diagonally) as the given position.
	 * 
	 * @param  position
	 *         The position to search next to.
	 * @return A random position of this world in a radius of one around the given position.
	 */
	@Model
	public Position getRandomNeighbouringPosition(Position position) {
		return getRandomPosition(position, 1);
	}
	
	/**
	 * Return a random accessible neighbouring cube (also diagonally) as the
	 * given position.
	 * 
	 * @param  position
	 *         The position to search next to.
	 * @return A random neighbouring position that is passable and has a solid neighbour.
	 */
	public Position getRandomAccessibleNeighbouringPosition(Position position) {
		List<Position> accessibleNeighbours = new ArrayList<>();
		for (Position pos : getAllNeighbours(position)) {
			if (isPassable(pos) && hasSolidNeighbour(pos)) {
				accessibleNeighbours.add(pos);
			}
		}
		if (accessibleNeighbours.isEmpty()) {
			return null;
		}
		return accessibleNeighbours.get(new Random().nextInt(accessibleNeighbours.size()));
	}
	
	/**
	 * Return a random accessible neighbouring cube of this world (also
	 * diagonally) on the same z-level as the given position.
	 * 
	 * @param  position
	 *         The position to search next to.
	 * @return A random accessible neighbouring position that is on the same z-level
	 *         as the given position.
	 */
	@Model
	Position getRandomAccessibleNeighbouringPositionOnSameZ(Position position) {
		while (true) {
			Position pos = getRandomAccessibleNeighbouringPosition(position);
			if (pos.getCenterPosition().z() == position.getCenterPosition().z()) {
				return pos;
			}
		}
	}
	
	/**
	 * Return whether this world features a solid cube next to (also diagonally)
	 * the given position.
	 * 
	 * @param  position
	 *         The position to search next to.
	 * @return True if and only if some position next to the given position is
	 *         not passable, or if the total amount of neighbouring cubes is less
	 *         than 26.
	 */
	public boolean hasSolidNeighbour(Position position) {
		// Corner or side cube: always support at the side of the game world.
		if (getAllNeighbours(position).size() < 26) {
			return true;
		}
		for (Position pos : getAllNeighbours(position)) {
			if (!getAt(pos).isPassable()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return whether this world features a solid cube at the underlying position of
	 * the given position.
	 * 
	 * @param  position
	 *         The position of which to retrieve the type of the cube at the
	 *         underlying z-level.
	 * @return True if and only if the given position doesn't have an underlying
	 *         cube. Else, true if and only if the underlying cube is not passable.
	 */
	public boolean hasUnderlyingSolid(Position position) {
		return (!isValidPosition(position.add(Position.Z, -1)) ||
				!getAt(position.add(Position.Z, -1)).isPassable());
	}
	
	/**
	 * Return a random direction.
	 *  
	 * @return A random integer with a value of -1, 0 or +1.
	 *       | result == -1 || result == 0 || result == 1   
	 */
	@Model
	private static int getRandomDirection() {
		Random random = new Random();
		int nb = random.nextInt(3);
		if (nb == 0) {
			return -1;
		} else if (nb == 1) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * Return a random unit.
	 */
	public Unit getRandomUnit() {
		return (Unit) getRandomElement(getAllUnits());
	}
	
	/**
	 * Return a random unit, the given one excluded.
	 */
	public Unit getRandomUnit(Unit excluded) {
		Unit unit;
		do {
			unit = getRandomUnit();
		} while (unit.equals(excluded));
		return unit;
	}
	
	/**
	 * Return a random element from the given set.
	 */
	public static Object getRandomElement(HashSet<? extends Object> set) {
		int nb = new Random().nextInt(set.size());
		int i = 0;
		
		for (Object object : set) {
			if (nb == i) {
				return object;
			}
			i++;
		}
		return null;
	}
	
	/**
	 * Return the entity of the given set that is closest to the given position.
	 * 
	 * @param  set
	 *         The set to search the entity in.
	 * @param  position
	 *         The position to compare with.
	 * @return The entity for which the distance between the position of that entity
	 *         and the given position is minimal. Null if no such entity exists or if
	 *         the given position is not effective.
	 */
	public static Entity getClosestElement(HashSet<? extends Entity> set, Position position) {
		if (position == null) {
			return null;
		}
		
		Entity closest = null;
		double distance = Double.POSITIVE_INFINITY;
		for (Entity entity : set) {
			double newDistance = Position.getDistance(position, entity.getPosition());
			
			if (newDistance < distance) {
				closest = entity;
				distance = newDistance;
			}
		}
		return closest;
	}
	
	/**
	 * Return the workshop closest to the given position.
	 * 
	 * @param  position
	 *         The position to compare with.
	 * @return The position of the cube that is of workshop type and that is
	 *         closest to the given position.
	 */
	public Position getClosestWorkshop(Position position) {
		Position closest = null;
		double distance = Double.POSITIVE_INFINITY;
		
		for (int i = 0; i < getSizeX(); i++) {
			for (int j = 0; j < getSizeY(); j++) {
				for (int k = 0; k < getSizeZ(); k++) {
					
					Position newPosition = new Position(i, j, k);
					if (getAt(newPosition).getId() == Cube.WORKBENCH.getId()) {
						double newDistance = Position.getDistance(newPosition, position);
						
						if (newDistance < distance) {
							closest = newPosition;
							distance = newDistance;
						}
					}
				}
			}
		}
		return closest;
	}
		
}