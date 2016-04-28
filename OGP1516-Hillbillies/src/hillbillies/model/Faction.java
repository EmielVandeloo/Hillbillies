package hillbillies.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;

/**
 * A class of factions.
 * 
 * @invar   Each faction can have its world as world.
 * @invar   The name of each faction must be a valid name for any
 *          faction.
 * @invar   Each faction must have proper units.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 2: 10/04/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class Faction {

	// FIELDS

	/**
	 * Variable registering the maximal amount of units allowed in a faction.
	 */
	public static final int MAX_UNITS = 50;

	/**
	 * Variable registering the world of this faction.
	 */
	private World world;

	/**
	 * Variable registering the name of this faction.
	 */
	private String name;
	
	/**
	 * Variable registering whether this faction is terminated.
	 */
	private boolean isTerminated;

	/**
	 * Variable referencing a set collecting all the units
	 * of this faction.
	 * 
	 * @invar  The referenced set is effective.
	 * @invar  Each unit registered in the referenced list is
	 *         effective and not yet terminated.
	 */
	private final Set<Unit> units = new HashSet<Unit>();

	// CONSTRUCTOR AND DESTRUCTOR

	/**
	 * Initialize this new faction as a non-terminated faction with 
	 * no units yet and with a given name and world.
	 * 
	 * @param  world
	 *         The world for this new faction.
	 * @param  name
	 *         The name for this new faction.
	 * @post   This new faction has no units yet.
	 * @post   This world of this new faction is equal to the given world.
	 * @effect The name of this new faction is set to
	 *         the given name.
	 * @throws IllegalArgumentException
	 *         This new faction cannot have the given world as its world.
	 */
	@Raw
	public Faction(World world, String name) throws IllegalArgumentException {
		if (! canHaveAsWorld(world)) {
			throw new IllegalArgumentException();
		}
		this.world = world;
		setName(name);
	}
	
	/**
	 * Check whether this faction is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this faction and its associated units, if any.
	 * 
	 * @post This faction is terminated.
	 * @post This faction no longer references a world as the world to which
	 *       it is attached.
	 * @post If this faction was not already terminated, the world to which
	 *       this faction was attached no longer references this faction as
	 *       an active faction in the world.
	 * @post The units belonging to this faction, if any, are terminated.
	 */
	public void terminate() {
		if (!isTerminated()) {
			World formerWorld = getWorld();
			this.isTerminated = true;
			setWorld(null);
			formerWorld.removeFaction(this);
			for (Unit unit : units) {
				unit.terminate();
			}
		}
	}

	// GETTERS AND SETTERS

	/**
	 * Return the name of this faction.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}

	/**
	 * Check whether the given name is a valid name for
	 * any faction.
	 *  
	 * @param  name
	 *         The name to check.
	 * @return True if the given name is effective.
	 */
	public static boolean isValidName(String name) {
		return (name != null);
	}

	/**
	 * Set the name of this faction to the given name.
	 * 
	 * @param  name
	 *         The new name for this faction.
	 * @post   The new name of this faction is equal to
	 *         the given name.
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any
	 *         faction.
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Return the world of this faction.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Set the world of this faction to the given world.
	 * 
	 * @post If this faction can have the given world as the world to which it
	 *       is attached, the world to which it is attached is set to the given world.
	 */
	@Raw
	public void setWorld(World world) {
		if(canHaveAsWorld(world)) {
			this.world = world;
		}
	}
	
	/**
	 * Check whether this faction can have the given world as its world.
	 *  
	 * @param  world
	 *         The world to check.
	 * @return If this faction is terminated, true if and only if the given world
	 *         is not effective. Else, true if and only if the given world is
	 *         effective and not yet terminated.
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		if (isTerminated()) {
			return (world == null);
		} else {
			return (world != null && !world.isTerminated());
		}
	}

	/**
	 * Check whether this faction has a proper world to which it is attached.
	 * 
	 * @return True if and only if this faction can have the world to which it
	 *         is attached as its world, and that world has a reference to this
	 *         faction as one of the active factions of that world.
	 */
	@Raw
	public boolean hasProperWorld() {
		return (canHaveAsWorld(getWorld()) &&
				  getWorld().getAllFactions().contains(this));
	}

	// Methods

	/**
	 * Generate a pseudo-random name.
	 * 
	 * @return A pseudo-random name.
	 */
	public static String getRandomizedName() {
		Random random = new Random();
		String name = "The ";
		String[] adjectives = {"drunken", "lovely", "monstrous", "different", "basic", "western",
				"agressive", "bureaucratic", "invisible", "tiny", "national", "amazing"};
		name += adjectives[random.nextInt(adjectives.length)];
		String[] nouns = {"athletics", "mathematicians", "knights", "fathers", "force", "collective",
				"democrats", "admireres", "drunks", "dwarves", "faction", "abominations"};
		name += nouns[random.nextInt(nouns.length)];
		return name;
	}

	// UNIT METHODS

	/**
	 * Check whether this faction has the given unit as one of its
	 * units.
	 * 
	 * @param  unit
	 *         The unit to check.
	 * @return True if and only if this faction has the given unit as
	 *         one of its units at some index.
	 */
	@Basic
	@Raw
	public boolean hasAsUnit(@Raw Unit unit) {
		return getAllUnits().contains(unit);
	}

	/**
	 * Check whether this faction can have the given unit
	 * as one of its units.
	 * 
	 * @param  unit
	 *         The unit to check.
	 * @return If this faction is terminated, true if and only if the given
	 *         unit is not effective. Else, true if and only if the given unit
	 *         is effective and not yet terminated.
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		if (isTerminated()) {
			return (unit == null);
		} else {
			return (unit != null && !unit.isTerminated());
		}
	}

	/**
	 * Check whether this faction has proper units attached to it.
	 * 
	 * @return False if the number of units attached to this faction
	 *         exceeds the maximum number of units for any faction.
	 * @return True if and only if this faction can have each of the
	 *         units attached to it as one of its units,
	 *         and if each of these units references this faction as
	 *         the faction to which they are attached.
	 */
	public boolean hasProperUnits() {
		if (getNbUnits() > MAX_UNITS) {
			return false;
		}
		for (Unit unit : units) {
			if (!canHaveAsUnit(unit))
				return false;
			if (unit.getFaction() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of units associated with this faction.
	 *
	 * @return The size of the set collecting all units attached to this faction.
	 */
	public int getNbUnits() {
		return getAllUnits().size();
	}
	
	/**
	 * Return all the units attached to this faction.
	 */
	@Raw @Basic
	public Set<Unit> getAllUnits() {
		return new HashSet<Unit>(units);
	}

	/**
	 * Add the given unit to the set of units attached to this faction.
	 * 
	 * @param  unit
	 *         The unit to be added.
	 * @pre    The given unit is effective and already references
	 *         this faction.
	 * @post   If the maximum number of units in this faction is not yet reached, 
	 *         and if the given unit isn't already registered in this faction,
	 *         this faction has the given unit as one of its units.
	 */
	public void addUnit(@Raw Unit unit) {
		assert (unit != null) && (unit.getFaction() == this);
		if ((getNbUnits() < MAX_UNITS) && !hasAsUnit(unit)) {
			units.add(unit);
		}
	}

	/**
	 * Remove the given unit from the set of units of this faction.
	 * 
	 * @param unit
	 *        The unit to be removed.
	 * @pre   This faction has the given unit as one of
	 *        its units, and the given unit does not
	 *        reference any faction.
	 * @post  This faction no longer has the given unit as
	 *        one of its units.
	 */
	@Raw
	public void removeUnit(Unit unit) {
		assert hasAsUnit(unit) && (unit.getFaction() == null);
		units.remove(unit);
	}
}
