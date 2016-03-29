package hillbillies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;

/**
 * A class of factions.
 * 
 * @invar  Each faction can have its world as world.
 *       | canHaveAsWorld(this.getWorld())
 * @invar  The name of each faction must be a valid name for any
 *         faction.
 *       | isValidName(getName())
 * @invar   Each faction must have proper units.
 *        | hasProperUnits()
 * 
 * @author Pieter-Jan
 *
 */
public class Faction {

	// FIELDS

	/**
	 * Variable registering the maximal amount of units allowed.
	 */
	public static final int MAX_UNITS = 50;

	/**
	 * Variable registering the world of this faction.
	 */
	private final World world;

	/**
	 * Variable registering the name of this faction.
	 */
	private String name;

	/**
	 * Variable referencing a set collecting all the units
	 * of this faction.
	 * 
	 * @invar  The referenced set is effective.
	 *       | units != null
	 * @invar  Each unit registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each unit in units:
	 *       |   ( (unit != null) &&
	 *       |     (! unit.isTerminated()) )
	 */
	private final Set<Unit> units = new HashSet<Unit>();


	// CONSTRUCTOR

	/**
	 * Initialize this new faction as a non-terminated faction with 
	 * no units yet and with a given name and world.
	 * 
	 * @param  world
	 *         The world for this new faction.
	 * @param  name
	 *         The name for this new faction.
	 * @post   The world of this new faction is equal to the given
	 *         world.
	 *       | new.getWorld() == world
	 * @post   This new faction has no units yet.
	 *       | new.getNbUnits() == 0
	 * @effect The name of this new faction is set to
	 *         the given name.
	 *       | this.setName(name)
	 * @throws IllegalArgumentException
	 *         This new faction cannot have the given world as its world.
	 *       | ! canHaveAsWorld(this.getWorld())
	 */
	public Faction(World world, String name) throws IllegalArgumentException {
		if (! canHaveAsWorld(world)) {
			throw new IllegalArgumentException();
		}
		this.world = world;

		this.setName(name);
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
	 * @return True if name is not null.
	 *       | result == (name != null)
	 */
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Set the name of this faction to the given name.
	 * 
	 * @param  name
	 *         The new name for this faction.
	 * @post   The name of this new faction is equal to
	 *         the given name.
	 *       | new.getName() == name
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any
	 *         faction.
	 *       | ! isValidName(getName())
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
	@Basic @Raw @Immutable
	public World getWorld() {
		return this.world;
	}

	/**
	 * Check whether this faction can have the given world as its world.
	 *  
	 * @param  world
	 *         The world to check.
	 * @return 
	 *       | result == (world != null) && (world.hasProperFactions());
	 */
	@Raw
	public static boolean canHaveAsWorld(World world) {
		return (world != null) && (world.hasProperFactions());
	}


	// Methods

	public String getRandomizedName() {
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

	public static boolean canFight(Unit Jef, Unit Bob) {
		return Jef.getFaction().equals(Bob.getFaction());
	}


	// UNIT-METHODS

	/**
	 * Check whether this faction has the given unit as one of its
	 * units.
	 * 
	 * @param  unit
	 *         The unit to check.
	 */
	@Basic
	@Raw
	public boolean hasAsUnit(@Raw Unit unit) {
		return units.contains(unit);
	}

	/**
	 * Check whether this faction can have the given unit
	 * as one of its units.
	 * 
	 * @param  unit
	 *         The unit to check.
	 * @return True if and only if the given unit is effective
	 *         and that unit is a valid unit for a faction.
	 *       | result ==
	 *       |   (unit != null) &&
	 *       |   Unit.isValidFaction(this) &&
	 *       |   getNbUnits() < MAX_UNITS
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		return (unit != null) && 
				(Unit.isValidFaction(this) && getNbUnits() < MAX_UNITS);
	}

	/**
	 * Check whether this faction has proper units attached to it.
	 * 
	 * @return True if and only if this faction can have each of the
	 *         units attached to it as one of its units,
	 *         and if each of these units references this faction as
	 *         the faction to which they are attached.
	 *       | for each unit in Unit:
	 *       |   if (hasAsUnit(unit))
	 *       |     then canHaveAsUnit(unit) &&
	 *       |          (unit.getFaction() == this)
	 */
	public boolean hasProperUnits() {
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
	 * @return  The total number of units collected in this faction.
	 *        | result ==
	 *        |   card({unit:Unit | hasAsUnit({unit)})
	 */
	public int getNbUnits() {
		return units.size();
	}

	/**
	 * Add the given unit to the set of units of this faction.
	 * 
	 * @param  unit
	 *         The unit to be added.
	 * @pre    The given unit is effective and already references
	 *         this faction. The amount of 
	 *       | (unit != null) && (unit.getFaction() == this)
	 * @post   This faction has the given unit as one of its units.
	 *       | new.hasAsUnit(unit)
	 * @return Whether the operation was successful or not.
	 */
	public boolean addUnit(@Raw Unit unit) {
		assert (unit != null) && (unit.getFaction() == this);
		if (getNbUnits() < MAX_UNITS) {
			units.add(unit);
			return true;
		}
		return false;
	}

	/**
	 * Remove the given unit from the set of units of this faction.
	 * 
	 * @param  unit
	 *         The unit to be removed.
	 * @pre    This faction has the given unit as one of
	 *         its units, and the given unit does not
	 *         reference any faction.
	 *       | this.hasAsUnit(unit) &&
	 *       | (unit.getFaction() == null)
	 * @post   This faction no longer has the given unit as
	 *         one of its units.
	 *       | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) {
		assert this.hasAsUnit(unit) && (unit.getFaction() == null);
		units.remove(unit);
	}

}
