package hillbillies.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Entity;
import hillbillies.model.Unit;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import hillbillies.world.Coordinate;
import hillbillies.world.Cube;
import hillbillies.world.Position;

/**
 * 
 * @invar   Each world must have proper entities.
 *        | hasProperEntities()
 * @invar   Each world must have proper factions.
 *        | hasProperFactions()
 * 
 * @author Pieter-Jan
 */
public class World {

	// FIELDS

	/**
	 * Variable containing the maximum amount of factions
	 * allowed in one world.
	 */
	public static final int MAX_FACTIONS = 5;
	
	/**
	 * Variable containing the length of a side of a cube.
	 */
	public static final int CUBE_LENGTH = 1;
	
	/**
	 * 
	 */
	public static final double[] FALL_VECTOR = new double[] {0, 0, -3};

	/**
	 * Variable referencing the size of the world in the X-axis.
	 */
	public final int sizeX;
	
	/**
	 * Variable referencing the size of the world in the Y-axis.
	 */
	public final int sizeY;
	
	/**
	 * Variable referencing the size of the world in the Z-axis.
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
	 * Variable referencing a set collecting all the entities
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 *       | entities != null
	 * @invar  Each entity registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each entity in entities:
	 *       |   ( (entity != null) &&
	 *       |     (! entity.isTerminated()) )
	 */
	private final Map<String, HashSet<Entity>> entities = new HashMap<>();
	
	/**
	 * Variable referencing a set collecting all the factions
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 *       | factions != null
	 * @invar  Each faction registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each faction in factions:
	 *       |   ( (faction != null) &&
	 *       |     (! faction.isTerminated()) )
	 */
	private final Set<Faction> factions = new HashSet<Faction>();
	

	// CONSTURCTOR

	/**
	 */
	/**
	 * Initialize this new world as a non-terminated world with 
	 * no entities yet.
	 * 
	 */

	/**
	 * Initialize this new world as a non-terminated world with 
	 * no factions or entities yet.
	 * 
	 * @param  terrainTypes
	 * 		   An integer array containing the information needed to
	 * 		   construct the game world.
	 * @param  modelListener
	 * 		   A class to detect terrain changes.
	 * @post   TODO De lengten en andere aanvullen in commentaar.
	 * @post   This new world has no factions yet.
	 *       | new.getNbFactions() == 0
	 * @post   This new world has no entities yet.
	 *       | new.getNbEntities() == 0
	 * @throws IllegalArgumentException
	 * @throws IndexOutOfBoundsException
	 */
	@Raw
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) 
			throws IllegalArgumentException, IndexOutOfBoundsException {

		if (terrainTypes == null) {
			throw new IllegalArgumentException();
		}

		sizeX = terrainTypes.length;
		sizeY = terrainTypes[0].length;
		sizeZ = terrainTypes[0][0].length;

		setWorldVersion(0);

		world = new Cube[sizeX][sizeY][sizeZ];
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				for (int k = 0; k < sizeZ; k++) {
					world[i][j][k] = Cube.byId(terrainTypes[i][j][k]);
				}
			}
		}

		if (modelListener == null) {
			throw new IllegalArgumentException();
		}

		this.border = new ConnectedToBorder(sizeX, sizeY, sizeZ);
		this.modelListener = modelListener;
	}


	// GETTERS AND SETTERS

	/**
	 * Return the current version of this world.
	 */
	@Basic
	public int getWorldVersion() {
		return worldVersion;
	}
	
	/**
	 * Set the new version of this world.
	 * 
	 * @param worldVersion
	 */
	@Raw
	private void setWorldVersion(int worldVersion) {
		this.worldVersion = worldVersion;
	}

	/**
	 * Increment the version of this world.
	 * 
	 * @effect Set the new world version a bit higher.
	 */
	private void updateWorldVersion() {
		setWorldVersion(getWorldVersion() + 1);
	}

	public Cube getAt(Position position) {
		int[] coord = position.convertToIntegerArray();
		return world[coord[0]][coord[1]][coord[2]];
	}

	private void setAt(Position position, Cube cube) {
		Coordinate coord = position.convertToCoordinate();
		world[coord.x()][coord.y()][coord.z()] = cube;

		updateWorldVersion();
		modelListener.notifyTerrainChanged(coord.x(), coord.y(), coord.z());
	}


	// WORLD-METHODS

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

	public void remove(Position position) {
		getAt(position).drop(position);
		setAt(position, Cube.AIR);

		List<int[]> list = 
				border.changeSolidToPassable((int) position.x(), (int) position.y(), (int) position.z());
		for (int[] coord : list) {
			remove(new Position(coord));
		}
	}

	public boolean isPassable(Position position) {
		return getAt(position).isPassable();
	}


	// ENTITY-METHODS

	/**
	 * Check whether this world has the given entity as one of its
	 * entities.
	 * 
	 * @param  entity
	 *         The entity to check.
	 */
	@Basic
	@Raw
	public boolean hasAsEntity(@Raw Entity entity) {
		return getList(entity).contains(entity);
	}

	/**
	 * Check whether this world can have the given entity
	 * as one of its entities.
	 * 
	 * @param  entity
	 *         The entity to check.
	 * @return True if and only if the given entity is effective
	 *         and that entity is a valid entity for a world.
	 *       | result == TODO
	 *       |   (entity != null) &&
	 *       |   Entity.isValidWorld(this)
	 */
	@Raw
	public boolean canHaveAsEntity(Entity entity) {
		return (entity != null) && (Entity.isValidWorld(this));
	}

	/**
	 * Check whether this world has proper entities attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *         entities attached to it as one of its entities,
	 *         and if each of these entities references this world as
	 *         the world to which they are attached.
	 *       | for each entity in entities:
	 *       |   if (hasAsEntity(entity))
	 *       |     then canHaveAsEntity(entity) &&
	 *       |          (entity.getWorld() == this)
	 */
	public boolean hasProperEntities() {
		for (HashSet<Entity> list : entities.values()) {
			for (Entity entity : list) {
				if (!canHaveAsEntity(entity))
					return false;
				if (entity.getWorld() != this)
					return false;
			}
		}
		return true;
	}

	/**
	 * Return the number of entities associated with this world.
	 *
	 * @return  The total number of entities collected in this world.
	 *        | result ==
	 *        |   card({entity:Entity | hasAsEntity({entity)})
	 */
	public int getNbEntities() {
		int nb = 0;
		for (HashSet<Entity> list : entities.values()) {
			nb += list.size();
		}
		return nb;
	}

	/**
	 * Add the given entity to the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be added.
	 * @pre    The given entity is effective and already references
	 *         this world.
	 *       | (entity != null) && (entity.getWorld() == this)
	 * @post   This world has the given entity as one of its entities.
	 *       | new.hasAsEntity(entity)
	 * @Return Whether the addition was successful or not.
	 */
	public boolean addEntity(@Raw Entity entity) {
		assert (entity != null) && (entity.getWorld() == this);
		
		if (getList(entity).size() < entity.maxEntities) {
			getList(entity).add(entity);
			return true;
		}
		return false;
		// TODO De beperking voor unit nog aanpassen.
	}

	/**
	 * Remove the given entity from the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be removed.
	 * @pre    This world has the given entity as one of
	 *         its entities, and the given entity does not
	 *         reference any world.
	 *       | this.hasAsEntitie(entity) &&
	 *       | (entity.getWorld() == null)
	 * @post   This world no longer has the given entity as
	 *         one of its entities.
	 *       | ! new.hasAsEntitie(entity)
	 */
	@Raw
	public void removeEntity(Entity entity) {
		assert this.hasAsEntity(entity) && (entity.getWorld() == null);
		entities.remove(entity);
	}
	
	private HashSet<Entity> getList(Entity entity) {
		return entities.get(entity.getClass().getName());
	}


	// FACTION-METHODS

	/**
	 * Check whether this world has the given faction as one of its
	 * factions.
	 * 
	 * @param  faction
	 *         The faction to check.
	 */
	@Basic
	@Raw
	public boolean hasAsFaction(@Raw Faction faction) {
		return factions.contains(faction);
	}

	/**
	 * Check whether this world can have the given faction
	 * as one of its factions.
	 * 
	 * @param  faction
	 *         The faction to check.
	 * @return True if and only if the given faction is effective
	 *         and that faction is a valid faction for a world.
	 *       | result ==
	 *       |   (faction != null) &&
	 *       |   Faction.isValidWorld(this)
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return (faction != null) && (Faction.canHaveAsWorld(this));
	}

	/**
	 * Check whether this world has proper factions attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *         factions attached to it as one of its factions,
	 *         and if each of these factions references this world as
	 *         the world to which they are attached.
	 *       | for each faction in Faction:
	 *       |   if (hasAsFaction(faction))
	 *       |     then canHaveAsFaction(faction) &&
	 *       |          (faction.getWorld() == this)
	 */
	public boolean hasProperFactions() {
		for (Faction faction : factions) {
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
	 * @return  The total number of factions collected in this world.
	 *        | result ==
	 *        |   card({faction:Faction | hasAsFaction({faction)})
	 */
	public int getNbFactions() {
		return factions.size();
	}

	/**
	 * Add the given faction to the set of factions of this world.
	 * 
	 * @param  faction
	 *         The faction to be added.
	 * @pre    The given faction is effective and already references
	 *         this world.
	 *       | (faction != null) && (faction.getWorld() == this)
	 * @post   This world has the given faction as one of its factions.
	 *       | new.hasAsFaction(faction)
	 */
	public void addFaction(@Raw Faction faction) {
		assert (faction != null) && (faction.getWorld() == this);
		factions.add(faction);
	}

	/**
	 * Remove the given faction from the set of factions of this world.
	 * 
	 * @param  faction
	 *         The faction to be removed.
	 * @pre    This world has the given faction as one of
	 *         its factions, and the given faction does not
	 *         reference any world.
	 *       | this.hasAsFaction(faction) &&
	 *       | (faction.getWorld() == null)
	 * @post   This world no longer has the given faction as
	 *         one of its factions.
	 *       | ! new.hasAsFaction(faction)
	 */
	@Raw
	public void removeFaction(Faction faction) {
		assert this.hasAsFaction(faction) && (faction.getWorld() == null);
		factions.remove(faction);
	}
	
	
	// USAGE-METHODS
	
	public void addUnit() {
		addUnit(createRandomUnit());
	}
	
	public void addUnit(Unit unit) {
		Faction faction = getPriorityFaction();
		
		faction.addUnit(unit);
		this.addEntity(unit);
	}
	
	private Unit createRandomUnit() {
		// TODO Hoe kan je dit het beste doen? (Misschien onderbrengen in Unit?)
		//Unit unit = new Unit(name, strength, agility, weight, toughness, position, enableDefaultBehaviour)
		return null;
	}
	
	private Faction getPriorityFaction() {
		// TODO Nalezen hoe de prioriteit van factions werkt.
		
		//faction.canHaveAsUnit(unit);
		return null;
	}

}