package hillbillies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import hillbillies.model.character.JobSelector;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.character.Inventory;
import hillbillies.model.character.JobStat;
import hillbillies.path.Path;
import hillbillies.path.PathFinder;
import hillbillies.program.Program;
import hillbillies.world.Position;

/**
 * A class of units involving a name, a weight, a strength, an agility, a toughness,
 * a number of hit points, a number of stamina points and a certain position in the game world.
 * 
 * @invar The name of each unit must be a valid name for any unit. 
 *      | isValidName(getName())
 * @invar The weight of each unit must be a valid weight for any unit.
 *      | isValidWeight(getWeight())
 * @invar The strength of each unit must be a valid strength for any
 *        unit.
 *        isValidStrength(getStrength())
 * @invar The agility of each unit must be a valid agility for any
 *        unit.
 *      | isValidAgility(getAgility())
 * @invar The toughness of each unit must be a valid toughness for any
 *        unit.
 *      | isValidToughness(getToughness())
 * @invar The number of hit points of each unit must be a valid number of hit points for any
 *        unit.
 *      | isValidNbHitPoints(getNbHitPoints())
 * @invar The number of stamina points of each unit must be a valid number of stamina points for any
 *        unit.
 *      | isValidNbStaminaPoints(getNbStaminaPoints())
 * @invar The position of each unit must be a valid position for any
 *        unit.
 *      | isValidPosition(getPosition())
 * @invar The inventory of each unit must be a valid inventory for any
 *        unit.
 *      | isValidInventory(getInventory())
 *      
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 2: 10/04/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */

public class Unit extends Entity {

	/**
	 * Variable registering the name of this unit.
	 */
	private String name;

	/**
	 * Variable registering the minimum value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private final static int MIN_ATTRIBUTE_VALUE = 1;

	/**
	 * Variable registering the maximum value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private final static int MAX_ATTRIBUTE_VALUE = 200;

	/**
	 * Variable registering the minimum initial value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private final static int MIN_INITIAL_ATTRIBUTE_VALUE = 25;

	/**
	 * Variable registering the maximum initial value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private final static int MAX_INITIAL_ATTRIBUTE_VALUE = 100;

	/**
	 * Variable registering the weight of this unit.
	 */
	private int weight;

	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength;

	/**
	 * Variable registering the agility of this unit.
	 */
	private int agility;

	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;

	/**
	 * Variable registering the number of hit points of this unit.
	 */
	private int nbHitPoints;

	/**
	 * Variable registering the number of stamina points of this unit.
	 */
	private int nbStaminaPoints;

	/**
	 * Variable registering the number of experience points of this unit.
	 */
	private int nbExperiencePoints = 0;

	/**
	 * Variable registering the orientation of this unit. 
	 */
	private double orientation = Math.PI/2;

	/**
	 * Variable registering the current speed of this unit.
	 */
	private double currentSpeed;

	// Positions

	/**
	 * Variable referencing an array storing the (intermediate) target position of this moving unit.
	 */
	private Position targetPosition = null;

	/**
	 * Variable referencing an array storing the (final) objective position of this moving unit.
	 */
	private Position objectivePosition = null;

	/**
	 * Variable referencing an array storing the start position of this moving unit.
	 */
	private Position startPosition;

	/**
	 * Variable referencing the position where this unit started falling.
	 */
	private Position startFallingPosition;

	/**
	 * Variable referencing the position on which this unit is performing a work.
	 */
	private Position workPosition;

	/**
	 * Variable registering whether or not this unit is moving.
	 */
	private boolean moving = false;

	/**
	 * Variable registering whether or not this unit is sprinting.
	 */
	private boolean sprinting = false;

	/**
	 * Variable registering whether or not this unit is working.
	 */
	private boolean working = false;

	/**
	 * Variable registering whether or not this unit is currently attacking another unit.
	 */
	private boolean	attacking = false;

	/**
	 * Variable registering whether or not this unit is resting.
	 */
	private boolean resting = false;

	/**
	 * Variable registering whether or not the default behavior of this unit is enabled.
	 */
	private boolean doesDefaultBehavior = false;

	/**
	 * Variable registering the job time of a unit's current job.
	 * 	  The job time is used to keep updates of a unit's status with different time steps
	 *    in line with the time whereupon a certain action has to be performed on or by the unit.
	 */
	private float jobTime;

	/**
	 * Variable registering the sprint time of a unit.
	 *    The sprint time is used to determine when to lower a unit's stamina points.
	 */
	private float sprintTime = JobStat.SPRINT;

	/**
	 * Variable registering an opposing unit.
	 */
	private Unit opponent;

	/**
	 * Variable referencing the faction to which this unit is attached.
	 */
	private Faction faction;

	/**
	 * Variable referencing the path this unit currently follows.
	 */
	private Path path;

	/**
	 * Variable referencing a list containing all current attackers of this unit.
	 */
	private List<Unit> attackers = new ArrayList<Unit>();

	/**
	 * Field representing the identification of this entity.
	 */
	public static final String ENTITY_ID = "entity:unit";
	
	/**
	 * Variable registering the inventory of this unit.
	 */
	private Inventory inventory = new Inventory();
	
	/**
	 * Variable referencing the program to which this unit is linked.
	 */
	private Program program;
	
	/**
	 * Variable referencing the task of this unit.
	 */
	private Task task;

	/**
	 * Initialize this unit with given name, given strength, given agility, given weight, given toughness,
	 * the maximum value of the number of hit points and the number of stamina points, given initial
	 * position and a possibility to enable the unit's default behavior.
	 * 
	 * @param  name
	 *         The name for this new unit.
	 * @param  weight
	 *         The weight for this new unit.
	 * @param  strength
	 *         The strength for this new unit.
	 * @param  agility
	 *         The agility for this new unit.
	 * @param  toughness
	 *         The toughness for this new unit.
	 * @param  position
	 * 		   The position for this new unit.
	 * @param  enableDefaultBehaviour
	 * 		   Whether or not the default behavior of this unit is enabled.
	 * @effect The unit is initialized as a new entity with the given position
	 *         and a non-effective world.
	 *       | super(null, position)
	 * @effect The initial name of this new unit is set to the given name.
	 *       | setName(name)
	 * @effect The initial strength of this new unit is set to the given strength.
	 *       | setInitialStrength(strength)
	 * @effect The initial agility of this new unit is set to the given agility.
	 *       | setInitialAgility(agility)
	 * @effect The initial weight of this new unit is set to the given weight.
	 *       | setInitialWeight(weight)
	 * @effect The initial toughness of this new unit is set to the given toughness.
	 *       | setInitialToughness(toughness)
	 * @effect The initial number of hit points of this new unit is set to the maximum number of hit points.
	 *       | setNbHitPoints(getMaxNbHitPoints())
	 * @effect The initial number of stamina points of this new unit is set to 
	 *         the maximum number of stamina points.
	 *       | setNbStaminaPoints(getMaxNbStaminaPoints())
	 * @effect The inventory of this new unit is set to a newly initialized inventory.
	 *       | setInventory(new Inventory())
	 * @post   The possibility for a unit to perform its default behavior is equal to the given flag.
	 *       | new.doesdefaultBehaviour == enableDefaultBehaviour.
	 */
	@Raw
	public Unit(int[] position, String name, int strength, int agility, int weight,
			int toughness, boolean enableDefaultBehavior) throws IllegalArgumentException {
		super(null, Position.convertToPosition(position));
		setName(name);
		setInitialStrength(strength);
		setInitialAgility(agility);
		setInitialToughness(toughness);
		setInitialWeight(weight);
		setNbHitPoints(getMaxNbHitPoints());
		setNbStaminaPoints(getMaxNbStaminaPoints());
		setInventory(new Inventory());
		this.doesDefaultBehavior = enableDefaultBehavior;
	}

	/**
	 * Terminate this unit.
	 * 
	 * @post This unit is terminated.
	 *     | new.isTerminated()
	 * @post This unit carries no longer any items in its inventory.
	 *     | new.getInventory().getNbItems() == 0
	 * @post This unit no longer references a world as the world
	 *       to which it is attached.
	 *     | new.getWorld() == null
	 * @post This unit no longer references a faction as the faction
	 *       to which it is attached.
	 *     | new.getFaction() == null
	 * @post This unit's inventory is terminated.
	 *     | (new getInventory).isTerminated() == true
	 * @post If this unit was not already terminated, the world to 
	 *       which this unit was attached no longer references this
	 *       unit as an active unit of the game world.
	 *     | if (!isTerminated())
	 *     |   then (!(new getWorld()).hasAsEntity(this))
	 * @post If this unit was not already terminated, the faction to
	 *       which this unit was attached no longer references this
	 *       unit as an active unit of the faction.
	 *     | if (!isTerminated())
	 *     |   then (!(new getFaction()).hasAsUnit(this))
	 */
	@Override
	public void terminate() {
		if (!isTerminated()) {
			getInventory().dropInventory(getPosition(), getWorld());
			Faction formerFaction = getFaction();
			super.terminate();
			setFaction(null);
			getInventory().terminate();
			setInventory(null);
			formerFaction.removeUnit(this);
		}
	}

	// GETTERS AND SETTERS

	/**
	 * Return the entity id of this unit.
	 */
	@Override @Basic
	public String getEntityId() {
		return ENTITY_ID;
	}

	/**
	 * Return the name of this unit.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}

	/**
	 * Check whether the given name is a valid name for any unit.
	 * 
	 * @param  name
	 *         The name to check.
	 * @return True if and only if the given name is at least two characters
	 *         long, if the name starts with an uppercase letter and if no other
	 *         symbols than letters (both uppercase and lowercase), quotes (both
	 *         single and double) and spaces occur in the name. 
	 *       | result == (name.length() >= 2) && (Character.isUpperCase(name.charAt(0)))
	 *       |              && (name.matches("^[A-Za-z'\" ]*$"))
	 */
	@Model
	private static boolean isValidName(String name) {
		return (name.length() >= 2) && (Character.isUpperCase(name.charAt(0)))
				&& (name.matches("^[A-Za-z'\" ]*$"));
	}

	/**
	 * Set the name of this unit to the given name.
	 * 
	 * @param  name
	 *         The new name for this unit.
	 * @post   The new name of this unit is equal to the given name.
	 *       | new.getName() == name
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any unit.
	 *       | ! (isValidName(name))
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || !isValidName(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	/**
	 * Return the strength of this unit.
	 */
	@Basic @Raw
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Check whether the given strength is a valid strength for any unit.
	 *  
	 * @param  strength
	 *         The strength to check.
	 * @return True if and only if the given strength is a valid attribute.
	 *       | result == isValidAttribute(strength)
	 */
	@Model
	private static boolean isValidStrength(int strength) {
		return isValidAttribute(strength);
	}

	/**
	 * Check whether the given strength is a valid initial strength for any unit.
	 * 
	 * @param  strength
	 *         The strength to check.
	 * @return True if and only if the given strength is a valid initial attribute.
	 *       | result == isValidInitialAttribute(strength)
	 */
	@Model
	private static boolean isValidInitialStrength(int strength) {
		return isValidInitialAttribute(strength);
	}

	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param  strength
	 *         The new strength for this unit.
	 * @effect If the given strength is not a valid strength for any unit,
	 *         the strength is made a valid attribute for this unit.
	 *       | if (! isValidStrength(strength)
	 *       |	  then makeValidAttribute(strength)
	 * @post   The new strength of this unit is equal to the given strength.
	 *       | new.getStrength() == strength
	 */
	@Raw
	public void setStrength(int strength) {
		if (! isValidAttribute(strength)) {
			strength = makeValidAttribute(strength);
		}
		this.strength = strength;
	}

	/**
	 * Set the initial strength of this unit to the given strength.
	 * 
	 * @param  strength
	 *         The new initial strength for this unit.
	 * @effect If the given strength is not a valid initial strength for any unit,
	 *         the strength is made a valid initial attribute for this unit.
	 *       | if (! isValidInitialStrength(strength)
	 *       |	  then makeValidInitialAttribute(strength)
	 * @post   The new strength of this unit is equal to the given strength.
	 *       | new.getStrength() == strength
	 */
	@Raw @Model
	private void setInitialStrength(int strength) {
		if (! isValidInitialAttribute(strength)) {
			strength = makeValidInitialAttribute(strength);
		}
		this.strength = strength;
	}

	/**
	 * Return the agility of this unit.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}

	/**
	 * Check whether the given agility is a valid agility for
	 * any unit.
	 *  
	 * @param  agility
	 *         The agility to check.
	 * @return True if and only if the given agility is a valid attribute.
	 *       | result == isValidAttribute(agility)
	 */
	@Model
	private static boolean isValidAgility(int agility) {
		return isValidAttribute(agility);
	}

	/**
	 * Check whether the given agility is a valid initial agility for
	 * any unit.
	 *  
	 * @param  agility
	 *         The agility to check.
	 * @return True if and only if the given agility is a valid initial attribute.
	 *       | result == isValidInitialAttribute(agility)
	 */
	@Model
	private static boolean isValidInitialAgility(int agility) {
		return isValidInitialAttribute(agility);
	}

	/**
	 * Set the agility of this unit to the given agility.
	 * 
	 * @param  agility
	 *         The new agility for this unit.
	 * @effect If the given agility is not a valid agility for any unit,
	 *         the agility is made a valid attribute for this unit.
	 *       | if (! isValidAgility(agility)
	 *       |	  then makeValidAttribute(agility)
	 * @post   The new agility of this unit is equal to the given agility.
	 *       | new.getAgility() == agility
	 */
	@Raw
	public void setAgility(int agility) {
		if (! isValidAttribute(agility)) {
			agility = makeValidAttribute(agility);
		}
		this.agility = agility;
	}
	/**
	 * Set the initial agility of this unit to the given agility.
	 * 
	 * @param  agility
	 *         The new initial agility for this unit.
	 * @effect If the given agility is not a valid initial agility for any unit,
	 *         the agility is made a valid initial attribute for this unit.
	 *       | if (! isValidInitialAttribute(agility)
	 *       |	  then makeValidInitialAttribute(agility)
	 * @post   The new agility of this unit is equal to the given agility.
	 *       | new.getAgility() == agility
	 */
	@Raw @Model
	private void setInitialAgility(int agility) {
		if (! isValidInitialAgility(agility)) {
			agility = makeValidInitialAttribute(agility);
		}
		this.agility = agility;
	}

	/**
	 * Return the weight of this unit.
	 */
	@Basic @Raw
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Return the effective weight of this unit.
	 */
	@Basic @Raw
	public int getEffectiveWeight() {
		return getWeight() + getInventory().getWeight();
	}
	
	/**
	 * Check whether the given weight is a valid weight for any unit.
	 * 
	 * @param  weight
	 *         The weight to check.
	 * @return True if and only if the given weight is a valid attribute, and if the weight 
	 *         is greater than or equal to the sum of the strength of this unit and the agility of this unit
	 *         divided by two.
	 *       | result == isValidAttribute(weight) && (weight >= (getStrength() + getAgility()) / 2)
	 */
	@Raw @Model
	private boolean canHaveAsWeight(int weight) {
		return isValidAttribute(weight) && (weight >= (getStrength() + getAgility()) / 2);
	}

	/**
	 * Check whether the given weight is a valid initial weight for any unit.
	 * 
	 * @param  weight
	 *         The weight to check.
	 * @return True if and only if the given weight is a valid initial attribute, and if the weight
	 *         is greater than or equal to the sum of the strength of this unit and the agility of this unit 
	 *         divided by two.
	 *       | result == (isValidInitialAttribute(weight) && 
	 *       |             (weight >= (getStrength() + getAgility()) / 2) 
	 */
	@Raw @Model
	private boolean canHaveAsInitialWeight(int weight) {
		return isValidInitialAttribute(weight) && (weight >= (getStrength() + getAgility()) / 2);
	}

	/**
	 * Set the weight of this unit to the given weight.
	 * 
	 * @param  weight
	 *         The new weight for this unit.
	 * @effect If the given weight is not a valid weight for any unit,
	 *         the weight is made a valid weight for this unit.
	 *       | if (! canHaveAsWeight(weight))
	 *       |   then makeValidWeight(weight)
	 * @post   The new weight of this unit is equal to the given weight.
	 *       | new.getWeight() == weight
	 */
	@Raw
	public void setWeight(int weight) {
		if (getInventory().isEmpty()) {
			if (! canHaveAsWeight(weight)) {
				weight = makeValidWeight(weight);
			}
		}
		this.weight = weight;
	}

	/**
	 * Set the initial weight of this unit to the given weight.
	 * 
	 * @param  weight
	 *         The new initial weight for this unit.
	 * @effect If the given weight is not a valid initial weight for any unit,
	 *         the weight is made a valid initial weight for this unit.
	 *       | if (! canHaveAsInitialWeight(weight))
	 *       |	 then makeValidInitialWeight(weight)
	 * @post   The new weight of this unit is equal to the given weight.
	 *       | new.getWeight() == weight
	 */
	@Raw @Model
	private void setInitialWeight(int weight) {
		if (! canHaveAsInitialWeight(weight)) {
			weight = makeValidInitialWeight(weight);
		}
		this.weight = weight;
	}

	/**
	 * Returns a valid value for the weight.
	 * 
	 * @param  weight
	 *         The weight to check.
	 * @return If the given weight is less than the sum of the strength of this unit and
	 *         the agility of this unit divided by two, the new value of the weight is equal to
	 *         the sum of the strength of this unit and the agility of this unit divided by two.
	 *       | if (weight < (getStrength() + getAgility()) / 2)
	 *       |	 then result == (getStrength() + getAgility()) / 2
	 * @return If the weight is greater than 200, the new weight is equal to 200.
	 *       | else if (weight > 200)
	 *       |	 then result == 200
	 * @return Otherwise, the initial weight is returned.
	 *         | else
	 *         |   result == weight
	 */
	@Raw @Model
	private int makeValidWeight(int weight) {
		if (weight < (getStrength() + getAgility()) / 2) {
			weight = (getStrength() + getAgility()) / 2;
		} else if (weight > 200) {
			weight = 200;
		}
		return weight;
	}

	/**
	 * Returns a valid initial value for the weight.
	 * 
	 * @param  weight
	 *         The weight to check.
	 * @return If the given weight is less than the sum of the strength of this unit and
	 *         the agility of this unit divided by two, the new value of the weight is equal to
	 *         the sum of the strength of this unit and the agility of this unit divided by two.
	 *       | if (weight < (getStrength() + getAgility()) / 2)
	 *       |	 then result == (getStrength() + getAgility()) / 2
	 * @return If the weight is greater than 100, the new weight is equal to 100.
	 *       | else if (weight > 100)
	 *       |	 then result == 100
	 * @return Otherwise, the initial weight is returned.
	 *       | else
	 *       |	 result == weight
	 */
	@Raw @Model
	private int makeValidInitialWeight(int weight) {
		if (weight < (getStrength() + getAgility() / 2)) {
			weight = (getStrength() + getAgility()) / 2;
		} else if (weight > 100) {
			weight = 100;
		}
		return weight;
	}

	/**
	 * Return the toughness of this unit.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
	}

	/**
	 * Check whether the given toughness is a valid toughness for any unit.
	 *  
	 * @param  toughness
	 *         The toughness to check.
	 * @return True if and only if the given toughness is a valid attribute.
	 *       | result == isValidAttribute(toughness)
	 */
	@Model
	private static boolean isValidToughness(int toughness) {
		return isValidAttribute(toughness);
	}

	/**
	 * Check whether the given toughness is a valid initial toughness for
	 * any unit.
	 *  
	 * @param  toughness
	 *         The toughness to check.
	 * @return True if and only if the given toughness is a valid initial attribute.
	 *       | result == isValidInitialAttribute(toughness)
	 */
	@Model
	private static boolean isValidInitialToughness(int toughness) {
		return isValidInitialAttribute(toughness);
	}

	/**
	 * Set the toughness of this unit to the given toughness.
	 * 
	 * @param  toughness
	 *         The new toughness for this unit.
	 * @effect If the given toughness is not a valid toughness for any unit,
	 *         the toughness is made a valid attribute for this unit.
	 *       | if (! isValidToughness(toughness))
	 *       |	 then makeValidAttribute(toughness)
	 * @post   The new toughness of this unit is equal to the given toughness.
	 *       | new.getToughness() == toughness
	 */
	@Raw
	public void setToughness(int toughness) {
		if (! isValidAttribute(toughness)) {
			toughness = makeValidAttribute(toughness);
		}
		this.toughness = toughness;
	}

	/**
	 * Set the initial toughness of this unit to the given toughness.
	 * 
	 * @param  toughness
	 *         The new initial toughness for this unit.
	 * @effect If the given toughness is not a valid initial toughness for any unit,
	 *         the toughness is made a valid initial attribute for this unit.
	 *       | if (! isValidInitialToughness(toughness))
	 *       |	 then makeValidInitialAttribute(toughness)
	 * @post The new toughness of this unit is equal to the given toughness.
	 *     | new.getToughness() == toughness
	 */
	@Raw @Model
	private void setInitialToughness(int toughness) {
		if (! isValidInitialAttribute(toughness)) {
			toughness = makeValidInitialAttribute(toughness);
		}
		this.toughness = toughness;
	}

	/**
	 * Return the minimum value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic @Immutable
	public static int getMinAttributeValue() {
		return MIN_ATTRIBUTE_VALUE;
	}

	/**
	 * Return the maximum value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic @Immutable
	public static int getMaxAttributeValue() {
		return MAX_ATTRIBUTE_VALUE;
	}

	/**
	 * Return the minimum initial value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic @Immutable
	public static int getMinInitialAttributeValue() {
		return MIN_INITIAL_ATTRIBUTE_VALUE;
	}

	/**
	 * Return the maximum initial value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic @Immutable
	public static int getMaxInitialAttributeValue() {
		return MAX_INITIAL_ATTRIBUTE_VALUE;
	}

	/**
	 * Check whether the given attribute value is a valid attribute value for any unit.
	 *  
	 * @param  attribute
	 *         The attribute value to check.
	 * @return True if and only if the given attribute value is greater than or equal to the minimum attribute
	 *         value and less than or equal to the maximum attribute value.
	 *       | result == (attribute >= getMinAttributeValue()) && (attribute <= getMaxAttributeValue())
	 */
	@Model
	private static boolean isValidAttribute(int attribute) {
		return (attribute >= getMinAttributeValue()) && (attribute <= getMaxAttributeValue());
	}

	/**
	 * Check whether the given attribute value is a valid initial attribute value for
	 * any unit.
	 *  
	 * @param  attribute
	 *         The attribute value to check.
	 * @return True if and only if the given attribute value is greater than or equal to the minimum initial
	 *         attribute value and less than or equal to the maximum initial attribute value.
	 *       | result == (attribute >= getMinInitialAttributeValue()) && 
	 *                      attribute <= getMaxInitialAttributeValue())
	 */	
	@Model
	private static boolean isValidInitialAttribute(int attribute) {
		return (attribute >= getMinInitialAttributeValue()) && (attribute <= getMaxInitialAttributeValue());
	}

	/**
	 * Returns a valid value for the given attribute.
	 * 
	 * @param  attribute
	 *         The attribute to check.
	 * @return If the value of the attribute is greater than the maximum attribute value, 
	 *         the new value of the attribute is equal to the maximum attribute value.
	 *       | if (attribute > getMaxAttributeValue())
	 *       |	 then result == getMaxAttributeValue()
	 * @return If the value of the attribute is less than the minimum attribute value, 
	 *         the new value of the attribute is equal to the minimum attribute value.
	 *       | else if (attribute < getMinAttributeValue())
	 *       |	 then result == getMinAttributeValue()
	 * @return Otherwise, the initial value is returned.
	 *       | else
	 *       |	 result == attribute
	 */
	@Raw @Model
	private int makeValidAttribute(int attribute) {
		if (attribute > getMaxAttributeValue()) {
			attribute = getMaxAttributeValue();
		} else if (attribute < getMinAttributeValue()) {
			attribute = getMinAttributeValue();
		}
		return attribute;
	}

	/**
	 * Returns a valid initial value for the given attribute.
	 * 
	 * @param  attribute
	 *         The attribute to check.
	 * @return If the value of the attribute is greater than the maximum initial attribute value, 
	 *         the new value of the attribute is equal to the maximum initial attribute value.
	 *       | if (attribute > getMaxInitialAttributeValue())
	 *       |	 then result == getMaxInitialAttributeValue
	 * @return If the value of the attribute is less than the minimum initial attribute value, 
	 *         the new value of the attribute is equal to the minimum initial attribute value.
	 *       | else if (attribute < getMinInitialAttributeValue())
	 *       |	 then result == getMinInitialAttributeValue()
	 * @return Otherwise, the initial value is returned.
	 *       | else
	 *       |	 result == attribute
	 */
	@Raw @Model
	private int makeValidInitialAttribute(int attribute) {
		if (attribute > getMaxInitialAttributeValue()) {
			attribute = getMaxInitialAttributeValue();
		} else if (attribute < getMinInitialAttributeValue()) {
			attribute = getMinInitialAttributeValue();
		}
		return attribute;
	}

	/**
	 * Return the number of hitpoints of this unit.
	 */
	@Basic @Raw
	public int getNbHitPoints() {
		return this.nbHitPoints;
	}

	/**
	 * Return the maximum number of hitpoints of this unit.
	 * 
	 * @return The maximum number of hitpoints is an integer greater than zero.
	 *       | result >= 0
	 */
	@Raw
	public int getMaxNbHitPoints() {
		return (int) Math.ceil(200.0 * (getWeight() / 100.0) * (getToughness() / 100.0));
	}

	/**
	 * Check whether the given number of hitpoints is a valid number of hitpoints for this unit.
	 *  
	 * @param  nbHitPoints
	 *         The number of hitpoints to check.
	 * @return True if and only if the number of hitpoints is greater than or equal to zero, and if
	 *         the number of hitpoints is less than or equal to the maximum number of hitpoints.
	 *       | result == (nbHitPoints >= 0) && (nbHitPoints <= getMaxNbHitPoints()
	 */
	@Raw
	public boolean isValidNbHitPoints(int nbHitPoints) {
		return (nbHitPoints <= getMaxNbHitPoints());
	}

	/**
	 * Set the number of hitpoints of this unit to the given number of hitpoints.
	 * 
	 * @param nbHitPoints
	 *        The new number of hitpoints for this unit.
	 * @pre   The given number of hitpoints must be a valid number of hitpoints for any
	 *        unit.
	 *      | isValidNbHitPoints(nbHitPoints)
	 * @post  The number of hitpoints  of this unit is equal to the given
	 *        number of hitpoints.
	 *      | new.getNbHitPoints() == nbHitPoints
	 */
	@Raw
	public void setNbHitPoints(int nbHitPoints) {
		assert isValidNbHitPoints(nbHitPoints);
		this.nbHitPoints = nbHitPoints;
	}

	/**
	 * Return the number of stamina points of this unit.
	 */
	@Basic @Raw
	public int getNbStaminaPoints() {
		return this.nbStaminaPoints;
	}

	/**
	 * Return the maximum number of stamina points of this unit.
	 * 
	 * @return The maximum number of stamina points is an integer value greater than zero.
	 *       | result >= 0
	 */
	@Raw
	public int getMaxNbStaminaPoints() {
		return (int) Math.ceil(200.0 * (getWeight() / 100.0) * (getToughness() / 100.0));
	}

	/**
	 * Check whether the given number of stamina points is a valid number of stamina points for this unit.
	 *  
	 * @param  nbStaminaPoints
	 *         The number of stamina points to check.
	 * @return True if and only if the number of stamina points is greater than or equal to zero, and if
	 *         the number of stamina points is less than or equal to the maximum number of stamina points.
	 *       | result == (nbHitPoints >= 0) && (nbHitPoints <= getMaxNbStaminaPoints()
	 */
	@Raw
	public boolean isValidNbStaminaPoints(int nbStaminaPoints) {
		return (nbStaminaPoints >= 0) && (nbStaminaPoints <= getMaxNbStaminaPoints());
	}

	/**
	 * Set the number of stamina points of this unit to the given number of stamina points.
	 * 
	 * @param nbStaminaPoints
	 *        The new number of stamina points for the unit.
	 * @pre   The given number of stamina points must be a valid number of stamina points for any
	 *        unit.
	 *      | isValidNbStaminaPoints(nbStaminaPoints)
	 * @post  The number of stamina points of this unit is equal to the given
	 *        number of stamina points.
	 *      | new.getNbStaminaPoints() == nbStaminaPoints
	 */
	@Raw
	public void setNbStaminaPoints(int nbStaminaPoints) {
		assert isValidNbStaminaPoints(nbStaminaPoints);
		this.nbStaminaPoints = nbStaminaPoints;
	}

	/**
	 * Return the number of experience points of this unit.
	 */
	@Raw @Basic
	public int getNbExperiencePoints() {
		return this.nbExperiencePoints;
	}

	/**
	 * Set the number of experience points of this unit to the given number of experience points.
	 * 
	 * @param nbExperiencePoints
	 *        The new number of experience points for this unit.
	 * @post  The number of experience points of this unit is equal to the given number of experience points.
	 *      | new.getNbExperiencePoints() == nbExperiencePoints
	 */
	@Raw
	private void setNbExperiencePoints(int nbExperiencePoints) {
		this.nbExperiencePoints = nbExperiencePoints;
	}

	/**
	 * Return the orientation of this unit in radians.
	 */
	@Basic @Raw
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * Set the orientation of this unit to the given orientation.
	 * 
	 * @param orientation
	 *        The new orientation for the unit in radians.
	 * @post  The new orientation for this unit is equal to the given orientation.
	 *      | new.getOrientation() == orientation
	 */
	@Model @Raw
	private void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	/**
	 * Return the current speed of this unit.
	 */
	@Raw
	public double getCurrentSpeed() {
		return this.currentSpeed;
	}

	/**
	 * Set the speed of this unit to the given speed.
	 * 
	 * @param currentSpeed
	 *        The new speed for the unit.
	 * @post  The new speed for this unit is equal to the given speed.
	 *      | new.getCurrentSpeed() == currentSpeed  
	 */
	@Model @Raw
	private void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	//****************************************************************//	

	// POSITIONS

	/**
	 * Check whether this unit can stand on the given position.
	 * 
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the world of this unit has a solid neighbouring
	 *         cube to the given position.
	 *       | result == getWorld().hasSolidNeighbour(position)
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for this unit.
	 *       | !isValidPosition(position)
	 */
	public boolean canStandOn(Position position) throws IllegalArgumentException {
		if (! getWorld().isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		return getWorld().isPassable(position) && getWorld().hasSolidNeighbour(position);
	}

	/**
	 * Return the target position of this unit.
	 *    The target position of a unit is the center position of one of the neighbouring 
	 *    (horizontally, vertically and diagonally) cubes of this unit.
	 */
	@Basic @Raw @Model
	private Position getTargetPosition() {
		return this.targetPosition;
	}

	/**
	 * Set the target position of this unit to the given position.
	 * 
	 * @param  position
	 *         The new target position for this unit.
	 * @post   The new target position of this unit is equal to
	 *         the given position.
	 *       | new.getTargetPosition().equals(position)
	 * @throws IllegalArgumentException
	 *         This unit cannot stand on the given position.
	 *       | !canStandOn(position)
	 */
	@Raw @Model
	private void setTargetPosition(Position position) throws IllegalArgumentException {
		if (position != null && ! canStandOn(position)) {
			throw new IllegalArgumentException();
		}
		this.targetPosition = position;
	}

	/**
	 * Return the objective position of this unit.
	 *    The objective position of a unit is the center of the cube in the game world this unit
	 *    is moving to.
	 */
	@Basic @Raw @Model
	private Position getObjectivePosition() {
		return this.objectivePosition;
	}

	/**
	 * Set the objective position of this unit to the given position.
	 * 
	 * @param  position
	 *         The new objective position of this unit.
	 * @post   The new objective position of this unit is equal to the given position.
	 *       | new.getObjectivePosition().equals(position)
	 * @throws IllegalArgumentException
	 *         This unit cannot stand on the given position.
	 *       | !canStandOn(position)
	 */
	@Raw @Model
	private void setObjectivePosition(Position position) throws IllegalArgumentException {
		if (position != null && ! canStandOn(position)) {
			throw new IllegalArgumentException();
		}
		this.objectivePosition = position;
	}

	/**
	 * Return the start position of this unit.
	 *    The start position of a unit is the center of that cube from where the unit started moving.
	 */
	@Basic @Raw @Model
	private Position getStartPosition() {
		return this.startPosition;
	}

	/**
	 * Set the start position of this unit to the given position.
	 * 
	 * @param  position
	 *         The new start position of this unit.
	 * @post   The new start position of this unit is equal to the given position.
	 *       | new.getStartPosition().equals(position)
	 * @throws IllegalArgumentException
	 *         This unit cannot stand on the given position.
	 *       | !canStandOn(position)
	 */
	@Raw @Model
	private void setStartPosition(Position position) throws IllegalArgumentException {
		if (! getWorld().isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		this.startPosition = position;
	}

	/**
	 * Return the position from which this unit started falling.
	 */
	@Raw @Basic
	public Position getStartFallingPosition() {
		return this.startFallingPosition;
	}

	/**
	 * Set the position from which this unit started falling to the given position.
	 * 
	 * @param position
	 *        The position from which this unit started falling.
	 * @post  The new position from which this unit started falling is equal to the
	 *        given position.
	 *      | new.getStartFallingPosition.equals(position)
	 */
	@Model @Raw
	private void setStartFallingPosition(Position position) {
		this.startFallingPosition = position;
	}

	//****************************************************************//

	// ADVANCE TIME

	/**
	 * Advance the state of the given unit by the given time period.
	 * 
	 * @param  deltaTime
	 *         The time period, in seconds, by which to advance the unit's state.
	 * @effect If this unit is currently falling, its fall behavior is performed.
	 *         Otherwise, if this unit currently has no solid neighbouring cube, the
	 *         position where the unit starts falling is set to the center position
	 *         of the cube this unit currently occupies and this unit's fall behavior
	 *         is performed. Else:
	 *       | ...
	 *         If this unit is currently attacking, the attack is performed
	 *       | ...
	 *         If this unit is currently defending, this unit stops moving
	 *         and the attack orientation is set according to the opponent's and
	 *         this unit's position.
	 *       | ...
	 *         If this unit is currently working, the work is performed on this unit's
	 *         current work position.
	 *       | ...
	 *         If this unit is currently resting, the rest is performed.
	 *       | ...
	 *         If this unit's current objective position is effective, the unit
	 *         walks for the given time period.
	 *       | ...
	 *         If this unit's default behavior is enabled, a random default
	 *         behavior is chosen.
	 *       | ...
	 * @effect One of this unit's attributes may be increased because of the gain
	 *         of experience points in the current time step.
	 *       | ...
	 * @effect If the current number of hit points is not positive, this unit is terminated.
	 *       | ...
	 */
	@Override
	public void advanceTime(double deltaTime) {
		int div1 = getNbExperiencePoints() / 10;
		//setWeight(getWeight() + getInventory().getWeight());
		if (isFalling()) {
			fallBehavior(deltaTime);
		} else if (!getWorld().hasSolidNeighbour(getPosition())) {
			setStartFallingPosition(getPosition().getCenterPosition());
			fallBehavior(deltaTime);
		} else {
			if (isAttacking()) {
				performAttack(deltaTime, getOpponent());
			} else if (isDefending()) {
				stopMoving();
				setAttackOrientation(this, getOpponent());
			} else if (isWorking()) {
				performWorkAt(deltaTime, getWorkPosition());
			} else if (isResting()) {
				performRest(deltaTime);
			} else if (getObjectivePosition() != null) {
				walk(deltaTime);
			} else if (hasTask()) {
				System.out.println("Perform task (same program)");
				getProgram().execute(deltaTime);
			} else if (getFaction().getScheduler().isTaskAvailable()) {
				System.out.println("\nInitialise new task from scheduler.");
				getFaction().getScheduler().assignTopPriorityTask(this);
				setProgram(new Program(getTask().getStatement()));
				getProgram().execute(deltaTime);
			} else if (doesDefaultBehavior()) {
				chooseDefaultBehavior();
			}
		int div2 = getNbExperiencePoints() / 10;
		while (div2 > div1) {
			increaseByExperience();
			div2 -= 1;
			}
		}
		if (getNbHitPoints() <= 0) {
			terminate();
		}
	}

	/**
	 * Make this unit walk for a given time period.
	 * 
	 * @param  deltaTime
	 *         The time period by which to walk.
	 * @effect If this unit does its default behavior and if it is currently not sprinting
	 *         and if its current number of stamina points is greater than zero, this unit
	 *         may start sprinting.
	 *       | if (doesDefaultBehaviour() && ! isSprinting() && getNbStaminaPoints() > 0)
	 *       |   then
	 *       |     let
	 *       |       random = new Random().nextDouble()
	 *       |     in
	 *       |       if (random < (number>0))
	 *       |         then startSprinting()
	 * @effect If the unit is currently sprinting, the position is updated in view of the
	 *         sprinting speed and the number of stamina points is updated accordingly. Else
	 *         the position is updated in view of the walking speed.
	 *       | if (isSprinting())
	 *       |   then updatePosition(deltaTime, getSprintingSpeed(getPosition().
	 *       |             getCenterPosition.z(), getTargetPosition().getCenterPosition.z()))
	 *       |   then staminaDrain(deltaTime);
	 *       | else
	 *       |   updatePosition(deltaTime, getWalkingSpeed(getPosition().getCenterPosition().
	 *       |             z(), getTargetPosition.getCenterPosition.z()))
	 * @effect If the unit has reached its target, the movement is stopped, this unit's number of
	 *         experience points is increased, and if the unit has reached its objective position, 
	 *         the unit's target position and the unit's objective position are set to null. 
	 *         Else, the unit continues pathing if it can still reach its objective.
	 *       | if (hasReachedTarget())
	 *       |   then stopMovement()
	 *       |   then setNbExperiencePoints(getNbExperiencePoints() + getNbExperiencePointsForStep())
	 *       | if (hasReachedTarget() && hasReachedObjective())
	 *       |   then setTargetPosition(null)
	 *       |   then setObjectivePosition(null)
	 *       | if (hasReachedTarget() && ! hasReachedObjective)
	 *       |   then if (getPath().needsUpdate(getWorld().getWorldVersion()))
	 *       |     then setPath(getWorld().calculatePathBetween(getPosition(), getObjectivePosition())
	 *       |   if (getPath.popNextPosition() == null)
	 *       |     then setTargetPosition(null)
	 *       |     then setObjectivePosition(null)
	 *       |   else moveToAdjacent(getPath().popNextPosition())
	 */
	@Raw @Model
	private void walk(double deltaTime) {
		if (doesDefaultBehavior() && ! isSprinting() && getNbStaminaPoints() > 0) {
			double random = new Random().nextDouble();
			if (random < deltaTime / 10.0) {
				startSprinting();
			}
		}
		double speed;
		if (isSprinting()) {
			speed = getSprintingSpeed(getPosition().getCenterPosition().z(), 
					getTargetPosition().getCenterPosition().z());
			setCurrentSpeed(speed);
			updatePosition(deltaTime, speed);
			staminaDrain(deltaTime);
		} else {
			speed = getWalkingSpeed(getPosition().getCenterPosition().z(),
					getTargetPosition().getCenterPosition().z());
			setCurrentSpeed(speed);
			updatePosition(deltaTime, speed);
		}
		if (hasReachedTarget()) {
			stopMovement();
			setNbExperiencePoints(getNbExperiencePoints() + getNbExperiencePointsForStep());
			if (hasReachedObjective()) {
				setTargetPosition(null);
				setObjectivePosition(null);
			} else {
				if (getPath().needsUpdate(getWorld().getWorldVersion())) {
					setPath(getWorld().calculatePathBetween(getPosition(), getObjectivePosition()));
				}
				Position next = getPath().popNextPosition();
				if (next == null) {
					setTargetPosition(null);
					setObjectivePosition(null);
					return;
				} else {
					moveToAdjacent(next);	
				}		
			}
		}
	}

	/**
	 * Return the number of experience points awarded for a completed movement step.
	 * 
	 * @return A positive integer value.
	 *       | result > 0
	 */
	@Model
	private static int getNbExperiencePointsForStep() {
		return 1;
	}

	/**
	 * Increase this unit's strength, agility or toughness by one.
	 * 
	 * @effect This unit's strength, agility or toughness are increased by one.
	 *       | setStrength(getStrength() + 1) || setAgility(getAgility() + 1) ||
	 *       |   setToughness(getToughness() + 1)
	 */
	@Model
	private void increaseByExperience() {
		int random = new Random().nextInt(3);
		if (random == 0) {
			setStrength(getStrength() + 1);
		} else if (random == 1) {
			setAgility(getAgility() + 1);
		} else {
			setToughness(getToughness() + 1);
		}
	}

	//****************************************************************//

	// FALL BEHAVIOUR

	/**
	 * Perform the fall behavior of this unit.
	 * 
	 * @param  deltaTime
	 *         The time step, in seconds, by which to perform this unit's fall behavior.
	 * @effect The list of all attackers is cleared.
	 *       | getAllAttackers().clear()
	 * @effect All this unit's jobs are reset and its default behavior is stopped.
	 *       | resetAllJobs()
	 *       | stopDefaultBehaviour()
	 * @effect This unit's start and target positions are set to null.
	 *       | setStartPosition(null)
	 *       | setTargetPosition(null)
	 * @effect This unit's current speed is set to 3.
	 *       | setCurrentSpeed(3)
	 * @effect The entity's fall behavior is performed.
	 *       | super.fallBehavior(deltaTime)
	 * @effect If this unit is not falling anymore, the new number of hit points is calculated
	 *         from the position from which this unit started falling, this unit's movement
	 *         is stopped and the position from which this unit starts falling is set to null.
	 *       | if (!isFalling())
	 *       |   then setNbHitPoints((int) (getNbHitPoints() - 10 * (getStartFallingPosition().z() -
	 *       |                           getPosition.z())))
	 *       |   then stopMovement
	 *       |   then setStartFallingPosition(null)
	 */
	@Override @Raw
	public void fallBehavior(double deltaTime) {
		getAllAttackers().clear();
		resetAllJobs();
		stopDefaultBehaviour();
		setStartPosition(null);
		setTargetPosition(null);
		setCurrentSpeed(3);
		super.fallBehavior(deltaTime);
		if (!isFalling()) {
			setNbHitPoints((int) (getNbHitPoints() - 10 * (getStartFallingPosition().z() - getPosition().z())));
			stopMovement();
			setStartFallingPosition(null);
		}
	}

	//****************************************************************//

	// MOVEMENT

	/**
	 * Return whether or not this unit is currently walking.
	 * 
	 * @return True if and only if this unit's target position does not reference the null object.
	 *       | result == getTargetPosition() != null
	 */
	@Raw
	public boolean isWalking() {
		return getTargetPosition() != null;
	}

	/**
	 * Return whether or not this unit is currently moving.
	 */
	@Basic @Raw
	public boolean isMoving() {
		return this.moving;
	}

	/**
	 * Make this unit start moving.
	 * 
	 * @post The new moving state of the unit is equal to true.
	 *     | new.isMoving() == true
	 */
	@Raw
	public void startMoving() {
		this.moving = true;
	}

	/**
	 * Make this unit stop moving.
	 * 
	 * @post The new moving state of the unit is equal to false.
	 *     | new.isMoving() == false
	 */
	@Raw
	public void stopMoving() {
		this.moving = false;
	}

	/**
	 * Stop the movement of this unit.
	 * 
	 * @effect The current speed of the unit is set to zero.
	 *       | setCurrentSpeed(0)
	 * @effect The unit stops moving.
	 *       | stopMoving()
	 * @effect The current unit position is set to the center position
	 *         of the current unit position.
	 *       | setPosition(getPosition().getCenterPosition())
	 * @effect The start position of the unit is set to the current unit position.
	 *       | setStartPosition(getPosition())
	 */
	@Model @Raw
	private void stopMovement() {
		setCurrentSpeed(0);
		stopMoving();
		setPosition(getPosition().getCenterPosition());
		setStartPosition(getPosition());
	}

	/**
	 * Move this unit to the objective position.
	 * 
	 * @param  objective
	 *         The objective position of this unit.
	 * @effect If the given objective is not effective or not a valid position for any entity,
	 *         no action is performed.
	 *       | if (!isValidPosition(objective))
	 *       |   then return
	 * @effect The path to follow is set to the calculated path.
	 *       | setPath(getWorld().calculatePathBetween(getPosition(), objective.getCenterPosition()))
	 * @effect If there doesn't exist a path between this unit's position and the given objective, this
	 *         unit stops its movement and no further action is performed.
	 *       | let
	 *       |   pos = getPath().popNextPosition()
	 *       | in
	 *       |   if (pos == null)
	 *       |     then stopMovement()
	 *       |     then return
	 *         Otherwise, the objective position is set to the given objective and the unit starts pathing.
	 *       | else
	 *       |   setObjectivePosition(objective.getCenterPosition())
	 *       |   moveToAdjacent(pos)
	 * @throws IllegalArgumentException
	 *         The given objective is not effective.
	 *       | objective == null
	 */
	@Raw
	public void moveTo(Position objective) throws IllegalArgumentException {
		if (objective == null) {
			throw new IllegalArgumentException();
		}
		if (!isValidPosition(objective)) {
			return;
		}
		setPath(getWorld().calculatePathBetween(getPosition(), objective.getCenterPosition()));
		Position pos = getPath().popNextPosition();
		if (pos == null) {
			stopMovement();
			return;
		}
		setObjectivePosition(objective.getCenterPosition());
		moveToAdjacent(pos);
	}

	/**
	 * Move this unit to an adjacent cube.
	 * 
	 * @param  x
	 *         The amount of cubes to move in the x-direction (-1, 0 or +1).
	 * @param  y
	 * 		   The amount of cubes to move in the y-direction (-1, 0 or +1).
	 * @param  z
	 *         The amount of cubes to move in the z-direction (-1, 0 or +1).
	 * @effect If the unit's current target position is effective, there is no effect.
	 *       | if (getTargetPosition() != null)
	 *       |   then return
	 * @effect If the sum of the current coordinate vector with the vector (x,y,z) is an invalid
	 *         position, there is no effect.
	 *       | if (!isValidUnitPosition(getPosition().x()+x, getWorld()) ||
	 *       | !isValidUnitPosition(getPosition().y()+y, getWorld()) || !isUnitValidPosition(
	 *       | getPosition().z()+z, getWorld()))
	 *       |   then return
	 * @effect Else, the  objective position of the unit is set to the sum of its current position
	 *         coordinates and x,y,z respectively.
	 *       | let
	 *       |   Position targetPosition = new Position()
	 *       |   targetPosition.setX(getPosition().x() + x)
	 *	     |   targetPosition.setY(getPosition().y() + y)
	 *	     |   targetPosition.setZ(getPosition().z() + z)
	 *       | in
	 *       |   setObjectivePosition(targetPosition)
	 * @effect If there is no corner between the unit's current position and the target position,
	 *         the unit is moved to the new objective position. Else, no action is performed.
	 *       | if (!PathFinder.isCornerAllowed(getWorld, getPosition(), targetPosition)
	 *       |   then moveToAdjacent(getObjectivePosition())
	 *       | else 
	 *       |   setObjectivePosition(null)
	 *       |   return
	 */
	@Raw
	public void moveToAdjacent(int x, int y, int z) {
		if (getTargetPosition() != null) {
			return;
		}
		Position targetPosition = new Position();
		try {
			targetPosition.setX(getPosition().x() + x);
			targetPosition.setY(getPosition().y() + y);
			targetPosition.setZ(getPosition().z() + z);
			setObjectivePosition(targetPosition);
			if (!PathFinder.isCornerAllowed(getWorld(), getPosition(), targetPosition)) {
				setObjectivePosition(null);
				return;
			}
			moveToAdjacent(targetPosition);
		} catch (IllegalArgumentException exc) {
			return;
		}
	}

	/**
	 * Move this unit to an adjacent cube.
	 * 
	 * @param  targetPosition
	 *         The new target position for the unit.
	 * @effect All jobs are reset.
	 *       | resetAllJobs()
	 * @effect The unit starts moving.
	 *       | startMoving()
	 * @effect The start position of the unit is set to the current unit position.
	 *       | setStartPosition(getPosition())
	 * @effect The target position of the unit is set to the given target position.
	 *       | setTargetPosition(targetPosition)
	 * @effect If the unit is currently sprinting, the current speed is set according
	 *         to the sprinting speed. Else, the current speed is set according to the
	 *         walking speed.
	 *       | if (isSprinting())
	 *		 |   then setCurrentSpeed(getSprintingSpeed(getPosition().z(), getTargetPosition().z()));
	 *	     | else
	 *		 |   setCurrentSpeed(getWalkingSpeed(getPosition().z(), getTargetPosition().z()));
	 */
	@Raw
	public void moveToAdjacent(Position targetPosition) throws IllegalArgumentException {
		resetAllJobs();
		startMoving();
		setStartPosition(getPosition());
		setTargetPosition(targetPosition);
		if (isSprinting()) {
			setCurrentSpeed(getSprintingSpeed(getPosition().z(), getTargetPosition().z()));
		} else {
			setCurrentSpeed(getWalkingSpeed(getPosition().z(), getTargetPosition().z()));
		}
	}

	/**
	 * Return the path of this unit.
	 */
	@Basic @Raw
	public Path getPath() {
		return this.path;
	}

	/**
	 * Set the path of this unit to the given path.
	 * 
	 * @param path
	 *        The new path for this unit.
	 * @post  The new path for this unit is equal to the given path.
	 *      | new.getPath() == path
	 */
	private void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Update the position of this unit according to the time step and its current speed.
	 * 
	 * @param  deltaTime
	 *         The time period, in seconds, by which to update the unit's position.
	 * @param  speed
	 * 	       The current speed of the unit.
	 * @effect Update the unit's position in view of the unit speed vector.
	 *       | updatePosition(deltaTime, speed, getUnitSpeed(speed))
	 */
	@Model @Raw
	private void updatePosition(double deltaTime, double speed) {
		updatePosition(deltaTime, speed, getUnitSpeed(speed));
	}

	/**
	 * Update the position of this unit according to the time step, its current speed and
	 * the unit speed vector.
	 * 
	 * @param  deltaTime
	 *         The time period, in seconds, by which to update the unit's position.
	 * @param  speed
	 *         The current speed of the unit.
	 * @param  unitSpeed
	 *         The current speed vector of the unit.
	 * @effect The unit's current speed is set to the given speed.
	 *       | setCurrentSpeed(speed)
	 * @effect The unit's walking orientation is set according to the given speed vector.
	 *       | setWalkingOrientation(unitSpeed)
	 * @effect This unit's position is set to the sum of (1) the unit's current position and 
	 *         (2) the product of the speed vector and the given time period, if possible.
	 *         Else, the unit's movement is stopped.
	 *       | let
	 *       | 	  updatedUnitPosition = getPosition() + getUnitSpeed() * deltaTime
	 *       | in
	 *       | 	 if (isValidUnitPosition(updatedUnitPosition))
	 *       |     then setPosition(updatedUnitPosition)
	 *       |   else
	 *       |     then stopMovement()
	 */
	@Model @Raw
	private void updatePosition(double deltaTime, double speed, double[] unitSpeed) {
		Position updatedUnitPosition = new Position();
		setCurrentSpeed(speed);
		setWalkingOrientation(unitSpeed);
		for (int i = 0; i < 3; i++) {
			updatedUnitPosition.setAt(i, getPosition().getAt(i) + unitSpeed[i] * deltaTime);
		}
		try {
			setPosition(updatedUnitPosition);
		} catch (IllegalArgumentException e) {
			stopMovement();
		}
	}

	/**
	 * Return whether a unit has reached its target.
	 * 
	 * @return True if and only if the distance between the start position of the unit
	 *         and the unit's current position is greater than or equal to the distance
	 *         between the start position of the unit and the target position of the unit.
	 *       | result == Position.getDistance(getStartPosition(), getPosition()) >=
	 *       |             Position.getDistance(getStartPosition(), getTargetPosition())
	 */
	@Model @Raw
	private boolean hasReachedTarget() {
		double startToUnit = Position.getDistance(getStartPosition(), getPosition());
		double startToTarget = Position.getDistance(getStartPosition(), getTargetPosition());
		return startToUnit >= startToTarget;
	}

	/**
	 * Return whether a unit has reached its objective.
	 * 
	 * @return True if and only if the unit's current position is equal to the
	 *         objective's position.
	 *       | result == getPosition().equals(getObjectivePosition())
	 */
	@Model @Raw
	private boolean hasReachedObjective() {
		return getPosition().equals(getObjectivePosition());
	}

	/**
	 * Return the base speed of this unit in meters per second.
	 * 
	 * @return The base speed of a unit is a double value greater than zero.
	 * 		 | result >= 0
	 */
	@Raw @Model
	private double getBaseSpeed() {
		return 1.5 * (getStrength() + getAgility()) / (200 * getEffectiveWeight() / 100);
	}

	/**
	 * Return the walking speed of this unit in meters per second.
	 * 
	 * @param  z
	 *         The z-coordinate of the cube the unit currently occupies.
	 * @param  targZ
	 *         The z-coordinate of the cube the unit is moving to.
	 * @return The base speed if the cube this unit currently occupies and the cube 
	 *         this unit is moving to is on the same z-level.
	 *       | if (z == targZ)
	 *       |    then result == getBaseSpeed()
	 * @return Less than the base speed if the z-level of the cube the unit currently occupies 
	 *         is one level lower than the z-level of the cube the unit is moving to.
	 *       | if (z - targZ < 0)
	 *       |    then result < getBaseSpeed()
	 * @return More than the base speed if the z-level of the cube the unit currently occupies
	 *         is one level higher than the z-level of the cube the unit is moving to.
	 *       | if (z - targZ > 0)
	 *       |    then result > getBaseSpeed()
	 */
	@Raw @Model
	private double getWalkingSpeed(double z, double targZ) {
		double walkingSpeed = getBaseSpeed();
		double delta = z - targZ;
		if (delta < 0) {
			walkingSpeed *= 0.5;
		} else if (delta > 0) {
			walkingSpeed *= 1.2;
		}
		return walkingSpeed;
	}

	/**
	 * Return the speed vector of this unit.
	 * 
	 * @param  speed
	 *         The magnitude of the speed vector in meters per second.
	 * @return The product of (1) the given speed and (2) the difference between
	 *         the unit's target position and the unit's current position, divided by the distance
	 *         between the unit's target position and the unit's current position.
	 *       | let
	 *       |	  distance = Position.getDistance(getTargetPosition(), getPosition())
	 *       | in
	 *       |	  result == speed * (getTargetPosition() - getPosition()) / distance
	 */
	@Model @Raw
	private double[] getUnitSpeed(double speed) {
		double[] unitSpeed = new double[3];
		double distance = Position.getDistance(getTargetPosition(), getPosition());
		for (int i = 0; i < unitSpeed.length; i++) {
			unitSpeed[i] = speed * (getTargetPosition().getAt(i) - getPosition().getAt(i)) / distance;
		}
		return unitSpeed;
	}

	/**
	 * Set this unit's walking orientation according to the given speed vector.
	 * 
	 * @param  speed
	 * 		   An array containing the speed components in three-dimensional space.
	 * @effect The orientation is set to the arctangent of the y-component of the given speed vector divided
	 *         by the x-component of the given speed vector.
	 *       | setOrientation(Math.atan2(speed[1], speed[0]))
	 */
	@Model @Raw
	private void setWalkingOrientation(double[] speed) {
		setOrientation(Math.atan2(speed[1], speed[0]));
	}

	//****************************************************************//

	// SPRINTING

	/**
	 * Return the sprinting speed of the unit in meters per second.
	 * 
	 * @param  z
	 *         The z-coordinate of the cube the unit currently occupies.
	 * @param  targZ
	 *         The z-coordinate of the cube the unit is moving to.
	 * @return A double with a higher value than the walking speed of this unit.
	 *       | result > getWalkingSpeed(z, targZ)
	 */
	@Raw @Model
	private double getSprintingSpeed(double z, double targZ) {
		return 2d * getWalkingSpeed(z, targZ);
	}

	/**
	 * Return whether or not this unit is currently sprinting.
	 */
	@Basic @Raw
	public boolean isSprinting() {
		return this.sprinting;
	}

	/**
	 * Enable sprinting mode for this unit.
	 * 
	 * @post The sprinting mode of this unit is equal to true.
	 *     | new.isSprinting() == true
	 */
	@Raw
	public void startSprinting() {
		this.sprinting = true;
	}

	/**
	 * Disable sprinting mode for this unit.
	 * 
	 * @post The sprinting mode of this unit is equal to false.
	 *     | new.isSprinting() == false
	 */
	@Raw
	public void stopSprinting() {
		this.sprinting = false;
	}

	/**
	 * Return the sprint time of this unit.
	 */
	@Basic @Model @Immutable @Raw
	private float getSprintTime() {
		return this.sprintTime;
	}

	/**
	 * Set the sprint time of this unit to the given sprint time.
	 * 
	 * @param sprintTime
	 *        The new sprint time for the unit.
	 * @post  The new sprint time for the unit is equal to the given sprint time.       
	 *      | new.getSprintTime() == sprintTime
	 */
	@Model @Raw
	private void setSprintTime(float sprintTime) {
		this.sprintTime = sprintTime;
	}

	/**
	 * Keep track of the amount of stamina points consumed on the time passed.
	 * 
	 * @param  deltaTime
	 * 		   The time period, in seconds, by which to update this unit's stamina.
	 * @effect The new sprint time is equal to the time left until next stamina reduction
	 * 		   or, if such a stamina reduction just occurred: the time between reductions
	 * 		   minus the overshoot.
	 * 		 | let
	 * 		 |    timeLeft = getSprintTime() - (float) deltaTime
	 * 		 | in
	 * 		 |    if (timeLeft <= 0)
	 * 		 |      then setSprintTime(JobStat.SPRINT + timeLeft)
	 * 		 |      else 
	 *       |        then setSprintTime(timeLeft)
	 * @effect If the stamina reduction occurs, the amount of stamina points is set to
	 *         the current amount of stamina points minus one.
	 * 		 | if (timeLeft <= 0)
	 * 		 |   then setNbStaminaPoints(getNbStaminaPoints() - 1)
	 * @effect If there is a stamina reduction and the stamina of this unit is already 0,
	 * 		   the unit will stop sprinting.
	 * 		 | if (timeLeft <= 0 && ! (this.getNbStaminaPoints() > 0))
	 * 		 |   then stopSprinting()
	 */
	@Model @Raw
	private void staminaDrain(double deltaTime) {		
		float timeLeft = getSprintTime() - (float) deltaTime;
		if (timeLeft <= 0) {
			if (getNbStaminaPoints() > 0) {
				setNbStaminaPoints(getNbStaminaPoints() - 1);
			} else {
				stopSprinting();
			}
			setSprintTime(JobStat.SPRINT + timeLeft);
		} else {
			setSprintTime(timeLeft);
		}
	}

	//****************************************************************//

	// WORKING

	/**
	 * Return whether or not this unit is currently working.
	 *   A unit can only be working if it stands in the center of a certain cube.
	 */
	@Basic @Raw
	public boolean isWorking(){
		if (getPosition().positionInCenter()) {
			return this.working;
		}
		return false;
	}

	/**
	 * Make the unit start working.
	 * 
	 * @post The working state of the unit is equal to true.
	 *     | new.isWorking() == true
	 */
	@Raw
	public void startWorking(){
		this.working = true;
	}

	/**
	 * Make this unit stop working.
	 * 
	 * @post The working state of the unit is equal to false.
	 *     | new.isWorking() == false
	 */
	@Raw
	public void stopWorking(){
		this.working = false;
	}

	/**
	 * Return the time this unit has to work to finish a job.
	 * 
	 * @return The work time is a positive floating point value.
	 *       | result > 0
	 */
	@Model @Raw
	private float getWorkTime(){
		return 500 / getStrength();
	}

	/**
	 * Make this unit work.
	 * 
	 * @effect If the unit has no objective and is not falling, if the unit is not attacking 
	 *         another unit, if the unit isn't already working and if the unit isn't performing 
	 *         its default behavior, then:
	 *       | if ((getObjectivePosition() == null) && !isAttacking() && !isWorking() && !doesDefaultBehaviour()
	 *       |        && !isFalling())
	 *         All jobs of the unit are reset.
	 *       | then resetAllJobs()
	 *         The unit starts working.
	 *       | then startWorking()
	 *         The job time of the unit is set to the work time.
	 *       | then setJobTime(getWorkTime())
	 */
	@Raw @Deprecated
	public void work() {
		if ((getObjectivePosition() == null) && !isAttacking() && !isWorking() && !doesDefaultBehavior()
				&& !isFalling()) {
			resetAllJobs();
			startWorking();
			setJobTime(getWorkTime());
		}
	}

	/**
	 * Make this unit work during its default behaviour.
	 * 
	 * @effect The unit starts working.
	 *       | startWorking()
	 * @effect The job time of the unit is set to the work time.
	 *       | setJobTime(getWorkTime())
	 */
	@Model @Raw @Deprecated
	private void workWithDefault() {
		// Not needed to control a possible performance of jobs nor to invoke resetAllJobs:
		// This unit can't be performing a default
		// job because default actions can't interrupt each other.
		startWorking();
		setJobTime(getWorkTime());
	}

	/**
	 * Make this unit perform its working behaviour.
	 * 
	 * @param  deltaTime
	 * 	       The time period, in seconds, by which to advance a unit's working behaviour.
	 * @effect The job time of the unit is set to the time left to finish its job or,
	 *         if it just finished a job: the time to finish a job minus a possible overshoot.
	 *       | let
	 *       |    timeLeft = getJobtime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(getWorkTime() + timeLeft)
	 *       |    else
	 *       |      then setJobTime(timeLeft)
	 * @effect If the time left for the unit to finish its job is less than or equal to zero,
	 *         it will stop working.
	 *       | let
	 *       |    timeLeft = getJobtime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then stopWorking()
	 */
	@Model @Raw @Deprecated
	private void performWork(double deltaTime) {
		float timeLeft = getJobTime() - (float) deltaTime;
		if (timeLeft <= 0) {
			setJobTime(0);
			stopWorking();

		} else {
			setJobTime(timeLeft);
		}
	}

	/**
	 * Make this unit work on a given position.
	 * 
	 * @param  x
	 *         The x-coordinate of the cube to work at.
	 * @param  y
	 *         The y-coordinate of the cube to work at.
	 * @param  z
	 *         The z-coordinate of the cube to work at.
	 * @effect If the given coordinates belong to a neighbouring cube or to the same cube that this unit
	 *         currently occupies, then:
	 *       | if (getWorld().getAllNeighboursAndSame(getPosition().getCubePosition()).contains(position))
	 *		 |   then:
	 *         If the unit has no objective and is not falling, if the unit is not attacking 
	 *         another unit, if the unit isn't already working and if the unit isn't performing 
	 *         its default behavior, then:
	 *       | if ((getObjectivePosition() == null) && !isAttacking() && !isWorking() && !doesDefaultBehaviour()
	 *       |        && !isFalling())
	 *         All jobs of the unit are reset.
	 *       | then resetAllJobs()
	 *         The work position is set to the given position coordinates.
	 *       | setWorkPosition(position)
	 *         The unit starts working.
	 *       | then startWorking()
	 *         The job time of the unit is set to the work time.
	 *       | then setJobTime(getWorkTime())
	 *         The orientation is set according to this unit's position and the coordinates
	 *         of the cube to work at.
	 *       | setOrientation(Math.atan2(getWorkPosition().y() - getPosition().getCubePosition().y(), 
						getWorkPosition().x() - getPosition().getCubePosition().x()))
	 */
	@Raw
	public void workAt(int x, int y, int z) {
		Position position = new Position(x,y,z);
		if (getWorld().getAllNeighboursAndSame(getPosition().getCubePosition()).contains(position)) {
			if ((getObjectivePosition() == null) && !isAttacking() && !isWorking() && !doesDefaultBehavior()
					&& !isFalling()) {
				resetAllJobs();
				setWorkPosition(position);
				startWorking();
				setJobTime(getWorkTime());
				setOrientation(Math.atan2(getWorkPosition().y() - getPosition().getCubePosition().y(), 
						getWorkPosition().x() - getPosition().getCubePosition().x()));
			}
		}
	}

	/**
	 * Make this unit work during its default behaviour.
	 * 
	 * @effect The work position is set to a random work position.
	 *       | setWorkPosition(selectRandomWorkPosition())
	 * @effect The unit starts working.
	 *       | startWorking()
	 * @effect The job time of the unit is set to the work time.
	 *       | setJobTime(getWorkTime())
	 */
	@Raw @Model
	private void workWithDefaultAt() {
		// Not needed to control a possible performance of jobs nor to invoke resetAllJobs:
		// This unit can't be performing a default
		// job because successive default actions can't interrupt each other.
		setWorkPosition(selectRandomWorkPosition().getCubePosition());
		startWorking();
		setJobTime(getWorkTime());
	}

	/**
	 * Make this unit perform its working behaviour on the given position.
	 * @param  deltaTime
	 * 	       The time period, in seconds, by which to advance a unit's working behaviour.
	 * @param  position
	 *         The position to work on.
	 * @effect The job time of the unit is set to the time left to finish its job or,
	 *         if it just finished a job: the time to finish a job minus a possible overshoot.
	 *       | let
	 *       |    timeLeft = getJobtime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(getWorkTime() + timeLeft)
	 *       |    else
	 *       |      then setJobTime(timeLeft)
	 * @effect If the time left for this unit to finish its job is less than or equal to zero, the
	 *         job time is set to zero, this unit stops working, this unit's work logic is performed,
	 *         this unit's work position is set to null and the number of experience points is updated.
	 *       | let
	 *       |    timeLeft = getJobtime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(0)
	 *       |      then stopWorking()
	 *       |      then workLogic(position)
	 *       |      then setWorkPosition(null)
	 *       |      then setNbExperiencePoints(getNbExperiencePoints() + getNbExperiencePointsForWork())
	 */
	@Model @Raw
	private void performWorkAt(double deltaTime, Position position) {
		float timeLeft = getJobTime() - (float) deltaTime;
		if (timeLeft <= 0) {
			setJobTime(0);
			stopWorking();
			workLogic(position);
			setWorkPosition(null);
			setNbExperiencePoints(getNbExperiencePoints() + getNbExperiencePointsForWork());
		} else {
			setJobTime(timeLeft);
		}
	}
	
	/**
	 * A method that decides which work action to perform.
	 * 
	 * @param  position
	 * 		   The position to work at. 
	 * @effect If this unit's inventory contains a boulder or a log, a boulder or a log is dropped
	 *         at the center of the cube this unit currently occupies.
	 *       | let
	 *       |   itemEntity = getInventoy.get(0)
	 *       | in
	 * 		 |   if (! getInventory().isEmpty())
	 * 		 |     then getInventory().dropItem(itemEntity, getWorkPosition(), getWorld())
	 *       |     then setWeight(getWeight() + itemEntity.getWeight)
	 * @effect If the unit is currently working on a tile containing
	 * 		   a workbench and is standing on a boulder and a log,
	 * 		   the unit will upgrade its capabilities.
	 * 		 | if ((getWorld().getAt(position).getId() == Cube.WORKBENCH.getId()) && 
	 * 		 |		(! getWorld().getEntitiesAt(position, Log.ENTITY_ID).isEmpty()) && 
	 * 		 |		(! getWorld().getEntitiesAt(position, Boulder.ENTITY_ID).isEmpty()))
	 * 		 |   then
	 *       |     logs.get(0).setWorld(null)
	 *       |     boulders.get(0).setWorld(null)
	 * 		 |     getWorld().removeEntity(logs.get(0))
	 * 		 |     getWorld().removeEntity(boulders.get(0))
	 * 		 |     upgrade();
	 * @effect If there are boulders on the work spot, the unit will pick up a boulder, adding
	 *         it to it's inventory, removing it from the world and increasing its weight.
	 * 		 | let
	 * 		 |   boulders = getWorld().getEntitiesAt(position, Boulder.getEntityId());
	 *       |   boulder = boulders.get(0)
	 * 		 | in
	 * 		 |   if (! boulders.isEmpty())
	 * 		 |     then getInventory().addItem(boulder)
	 *       |     then boulder.setWorld(null)
	 *       |     then getWorld.removeEntity(boulder)
	 *       |     then setWeight(getWeight() + boulder.getWeight())
	 * @effect If there are logs on the work spot, the unit will pick up a log, adding it to
	 *         it's inventory and removing it from the world.
	 * 		 | let
	 * 		 |   logs = getWorld().getEntitiesAt(position, Log.getEntityId())
	 *       |   log = logs.get(0)
	 * 		 | in
	 * 		 |   if (! logs.isEmpty())
	 * 		 |     then getInventory().addItem(log);
	 *       |     then log.setWorld(null)
	 *       |     then getWorld.removeEntity(log)
	 * @effect If the work spot is a solid cube, this cube will be broken.
	 * 		 | if (! getWorld().isPassable(getWorkPosition())
	 * 		 |   then getWorld().remove(position);
	 */
	private void workLogic(Position position) {
		ArrayList<Entity> logs = getWorld().getEntitiesAt(position, Log.ENTITY_ID);
		ArrayList<Entity> boulders = getWorld().getEntitiesAt(position, Boulder.ENTITY_ID);
		switch (JobSelector.getJob(getWorld(), getPosition(), getWorkPosition(), getInventory())) {
		case DROP:
			ItemEntity itemEntity = getInventory().getItem();
			getInventory().dropItem(itemEntity, getPosition(), getWorld());
			//setWeight(getWeight() - itemEntity.getWeight());
			break;
		case UPGRADE:
			logs.get(0).setWorld(null);
			boulders.get(0).setWorld(null);
			getWorld().removeEntity(logs.get(0));
			getWorld().removeEntity(boulders.get(0));
			upgrade();
			break;
		case PICK_UP_BOULDER:
			ItemEntity boulder = (Boulder) boulders.get(0);
			getInventory().addItem(boulder);
			boulder.setWorld(null);
			getWorld().removeEntity(boulder);
			//setWeight(getWeight() + boulder.getWeight());
			break;
		case PICK_UP_LOG:
			ItemEntity log = (Log) logs.get(0);
			getInventory().addItem(log);
			log.setWorld(null);
			getWorld().removeEntity(log);
			//setWeight(getWeight() + log.getWeight());
			break;
		case REMOVE_BLOCK:
			getWorld().remove(position);
			break;
		default:
			break;
		}
	}

	/**
	 * Upgrade this unit.
	 * 
	 * @effect Increase this unit's weight by one.
	 * 		 | setWeight(getWeight() + 1);
	 * @effect Increase this unit's toughness by one.
	 * 		 | setToughness(getToughness() + 1);
	 */
	private void upgrade() {
		setToughness(getToughness() + 10);
		setWeight(getWeight() + 10);
	}

	/**
	 * Return the number of experience points awarded for a completed work.
	 * 
	 * @return A positive integer value.
	 *       | result > 0
	 */
	@Model
	private static int getNbExperiencePointsForWork() {
		return 10;
	}

	/**
	 * Return the position on which this unit is performing a work.
	 */
	@Basic @Raw
	public Position getWorkPosition() {
		return this.workPosition;
	}

	/**
	 * Set the position on which this unit is performing a work to the given position.
	 * 
	 * @param position
	 *        The new work position.
	 * @post  The new work position of this unit is equal to the given position.
	 *      | new.getWorkPosition().equals(position)
	 */
	@Basic @Raw
	private void setWorkPosition(Position position) {
		this.workPosition = position;
	}

	/**
	 * Return a random work position.
	 * 
	 * @return A random position from the list of neighbouring positions and this position.
	 *       | let
	 *       |   allPositions = getWorld().getAllNeighboursAndSame(getPosition())
	 *       | in
	 *       |   result == allPositions.get(new Random().nextInt(allPositions.size()))
	 */
	@Raw @Model
	private Position selectRandomWorkPosition() {
		List<Position> allPositions = getWorld().getAllNeighboursAndSame(getPosition());
		int random = new Random().nextInt(allPositions.size());
		return allPositions.get(random);

	}

	//****************************************************************//

	// FIGHTING

	/**
	 * Return whether or not this unit is currently attacking.
	 *   A unit can only be attacking if it stands in the center of a certain cube.
	 */
	@Basic @Raw
	public boolean isAttacking() {
		if (getPosition().positionInCenter()) {
			return this.attacking;
		}
		return false;
	}

	/**
	 * Make this unit start attacking.
	 * 
	 * @post The new attacking state of the unit is equal to true.
	 *     | new.isAttacking() == true
	 */
	@Raw
	public void startAttacking() {
		this.attacking = true;
	}

	/**
	 * Make this unit stop attacking.
	 * 
	 * @post The new attacking state of the unit is equal to false.
	 *     | new.isAttacking() == false
	 */
	@Raw
	public void stopAttacking() {
		this.attacking = false;
	}

	/**
	 * Return whether or not this unit is currently being attacked.
	 * 
	 * @return True if and only if the current position of this unit is the center
	 *         position of some cube and if the list of all attackers is not empty.
	 *       | result == (getPosition().positionInCenter() && getNbAttackers() > 0)
	 */
	@Raw
	public boolean isDefending() {
		if (getPosition().positionInCenter()) {
			return getNbAttackers() > 0;
		}
		return false;
	}

	/**
	 * Return the opponent unit of this unit.
	 */
	@Model @Raw
	private Unit getOpponent() {
		return this.opponent;
	}

	/**
	 * Return whether this unit can have the given opponent as its opponent.
	 * 
	 * @param  opponent
	 *         The opponent to check.
	 * @return True if the given opponent is not effective.
	 *       | if (opponent == null)
	 *       |   then result == true
	 * @return True if this unit can fight the opponent.
	 *       | result == canFight(opponent) 
	 */
	@Raw
	public boolean canHaveAsOpponent(@Raw Unit opponent) {
		if (opponent == null) {
			return true;
		}
		return canFight(opponent);
	}

	/**
	 * Set the opponent unit of this unit to the given opponent unit.
	 * 
	 * @param  opponent
	 *         The opponent unit.
	 * @post   The new opponent unit of this unit is equal to the given opponent unit.
	 *       | new.getOpponent() == opponent
	 * @throws IllegalArgumentException
	 *         This unit cannot have the given opponent as its opponent.
	 *       | !canHaveAsOpponent(opponent)
	 */
	@Model @Raw
	private void setOpponent(@Raw Unit opponent) throws IllegalArgumentException {
		if (!canHaveAsOpponent(opponent)) {
			throw new IllegalArgumentException();
		}
		this.opponent = opponent;
	}

	/**
	 * Return the strength of the attack.
	 * 
	 * @return The strength of the attack is a non-negative integer value.
	 *       | result >= 0
	 */
	@Model @Raw
	private int getAttackStrength() {
		return getStrength() / 10;
	}

	/**
	 * Return the list of all attackers.
	 */
	@Basic @Raw
	public List<Unit> getAllAttackers() {
		return this.attackers;
	}

	/**
	 * Return the number of attackers of this unit.
	 */
	@Basic @Raw
	public int getNbAttackers() {
		return getAllAttackers().size();
	}

	/**
	 * Add the given unit to the list of attackers.
	 * 
	 * @param  unit
	 *         The unit to add.
	 * @effect The unit is added to the end of the list of attackers.
	 *       | getAllAttackers().add(unit)
	 */
	@Model @Raw
	private void addAsAttacker(Unit unit) {
		getAllAttackers().add(unit);
	}

	/**
	 * Remove the given unit from the list of attackers.
	 * 
	 * @param  unit
	 *         The unit to remove.
	 * @effect If the list of attackers contains the given unit as one of its units, 
	 *         the given unit is removed from the list of attackers.
	 *       | if (getAllAttackers().contains(unit))
	 *       |   then getAllAttackers().remove(unit)
	 */
	private void removeAsAttacker(Unit unit) {
		if (getAllAttackers().contains(unit)) {
			getAllAttackers().remove(unit);
		}
	}

	/**
	 * Attack another unit.
	 * 
	 * @param  defender
	 * 		   The unit this unit is going to attack.
	 * @effect If this unit has no objective and if it doesn't perform its default behavior and if it
	 *         currently is not attacking and if this unit and the defender are from different factions 
	 *         and if they are not falling, then:
	 *       | if ((getObjectivePosition() == null) && !doesDefaultBehaviour() && !isAttacking() &&
	 *		 |	     getFaction() != defender.getFaction() && !isFalling() && !defender.isFalling())
	 *		 |   then:
	 * @effect If this unit and the defender are in neighbouring cubes or occupy the same
	 *         cube, the attack is controlled.
	 *       | if (isAdjacentToOrSame(getPosition(), defender.getPosition())
	 *       |   then controlAttack(defender)
	 */
	@Raw
	public void attack(@Raw Unit defender) {
		if ((getObjectivePosition() == null) && !doesDefaultBehavior() && !isAttacking() &&
				canFight(defender) && !isFalling() && !defender.isFalling()) {
			if (Position.isAdjacentToOrSame(getPosition(), defender.getPosition())) {
				controlAttack(defender);
			}
		}
	}

	/**
	 * Make this unit attack during its default behaviour.
	 * 
	 * @effect A random defender is selected.
	 *       | let
	 *       |   possibleDefenders = getPossibleDefenders()
	 *       |   defender = selectRandomDefender(possibleDefenders)
	 *       | in:
	 *         If the selected defender is not effective, no action is performed.
	 *       | if (defender == null)
	 *       |   then return
	 *         While the selected defender cannot fight this unit,
	 *         a new random defender is selected from the list of possible defenders.
	 *       | ...
	 *         The attack is controlled.
	 *       | controlAttack(defender)
	 */
	@Raw @Model
	private void attackWithDefault() {
		List<Unit> possibleDefenders = getPossibleDefenders();
		if (possibleDefenders.size() < 1) {
			return;
		}
		Unit defender = selectRandomDefender(possibleDefenders);
		if (defender == null) {
			return;
		}
		while (!canFight(defender)) {
			defender = selectRandomDefender(possibleDefenders);
		}
		controlAttack(defender);
	}

	/**
	 * Control the attack for both attacker and defender.
	 * 
	 * @param  defender
	 *         The defending unit.
	 * @effect The defender's attacking state is controlled.
	 *       | defender.controlAttackingState()
	 * @effect This unit's and the defender's jobs are reset.
	 *       | resetAllJobs()
	 *       | defender.resetAllJobs()
	 * @effect This unit starts attacking and the defender adds this unit as one of its attackers.
	 * 		 | startAttacking()
	 *       | defender.addAsAttacker(this)
	 * @effect This unit's opponent is set to the given defender, the first attacker in the list
	 *         of all attackers of the defender is set to the defender's opponent, and the job 
	 *         time is set to the attack time.
	 *       | setOpponent(defender)
	 *       | defender.setOpponent(defender.getAllAttackers().get(0))
	 *       | setJobTime(JobStat.ATTACK)
	 */
	@Raw @Model
	private void controlAttack(Unit defender) {
		// Special case: attacker attacks an attacking unit.
		defender.controlAttackingState();
		resetAllJobs();
		defender.resetAllJobs();
		startAttacking();
		defender.addAsAttacker(this);
		setOpponent(defender);
		defender.setOpponent(defender.getAllAttackers().get(0));
		setJobTime(JobStat.ATTACK);
	}

	/**
	 * Control this unit's attacking state.
	 * 
	 * @effect If this unit is currently attacking another unit, the unit stops attacking, the unit's
	 *         former opponent removes this unit as one of its attackers.
	 *       | if (isAttacking())
	 *       |   then stopAttacking
	 *       |   then getOpponent().removeAsAttacker(this)
	 * @effect This unit's former opponent's opponent is set to the next opponent.
	 *       | getOpponent().setNextOpponent()
	 * @effect This unit's opponent is set to null.
	 *       | setOpponent(null)
	 */
	@Raw @Model
	private void controlAttackingState() {
		if (isAttacking()) {
			stopAttacking();
			getOpponent().removeAsAttacker(this);
			getOpponent().setNextOpponent();
			setOpponent(null);
		}
	}

	/**
	 * Make this unit perform its attacking attacking behaviour.
	 * 
	 * @param  deltaTime
	 * 	       The time period, in seconds, by which to advance a unit's attacking behaviour.
	 * @effect If this unit's position is not a neighbouring or the same position as the defending
	 *         unit's position, this unit stops attacking, this unit is removed from the defender's
	 *         list of its attackers and the defender's next opponent is set.
	 *       | if (!Position.isAdjacentToOrSame(getPosition(), defender.getPosition()))
	 *       |   then stopAttacking()
	 *       |   then defender.removeAsAttacker(this)
	 *       |   then defender.setNextOpponent()
	 * @effect The new job time is the time left to complete an attack or, if the attack just
	 *         finished in the current time step, the job time is set to zero.
	 *       | let
	 *       |    timeLeft = getJobTime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(0)
	 *       |    else
	 *       |      setJobTime(timeLeft)
	 * @effect If the time left to complete the attack is less than or equal to zero: if
	 *         the defender was not able to dodge or block the attack, damage is dealt to the
	 *         defender and the number of experience points of this unit is updated. Else, the
	 *         number of experience points of the defender is updated.
	 *       | let
	 *       |   timeLeft = getJobTime() - (float) deltaTime
	 *       | in
	 *       |   if (timeLeft <= 0)
	 *       |     then if (!(defender.defend(this)))
	 *       |            then dealdamageTo(defender)
	 *       |            then setNbExperiencePoints(getNbExperiencePoints() + getNbExperiencePointsForAttack())
	 *       |          else defender.setNbExperiencePoints(defender.getNbExperiencePoints() + getNbExperiencePointsForDefence())
	 * @effect If this unit has finished its current attack, this unit stops attacking and
	 *         the defender removes this unit from the list of its attackers and the defender's
	 *         next opponent is set.
	 *       | if getJobTime() == 0
	 *       |   then stopAttacking()
	 *       |   then defender.removeAsAttacker(this)
	 *       |   then setOpponent(null)
	 *       |   then defender.setNextOpponent()
	 */
	@Model @Raw
	private void performAttack(double deltaTime, @Raw Unit defender) {
		// Special case: the defender dodged the attack of a previous attacker and
		// is now out of reach for the current attacker.
		if (!Position.isAdjacentToOrSame(getPosition(), defender.getPosition())) {
			stopAttacking();
			defender.removeAsAttacker(this);
			defender.setNextOpponent();
		}
		float timeLeft = getJobTime() - (float) deltaTime;
		if (timeLeft <= 0) {
			if (! defender.defend(this)) {
				dealdamageTo(defender);
				setNbExperiencePoints(getNbExperiencePoints() + getNbExperiencePointsForAttack());
			} else {
				defender.setNbExperiencePoints(defender.getNbExperiencePoints() + getNbExperiencePointsForDefence());
			}
			setJobTime(0);
			stopAttacking();
			defender.removeAsAttacker(this);
			setOpponent(null);
			defender.setNextOpponent();
		} else {
			setJobTime(timeLeft);
		}
	}

	/**
	 * Return the number of experience points awarded for a successful attack.
	 * 
	 * @return A positive integer value.
	 *       | result > 0
	 */
	@Model @Raw
	private static int getNbExperiencePointsForAttack() {
		return 20;
	}

	/**
	 * Return the number of experience points awarded for a successful defence.
	 * 
	 * @return A positive integer value.
	 *       | result > 0
	 */
	@Model
	private static int getNbExperiencePointsForDefence() {
		return 20;
	}

	/**
	 * Set the next opponent of this unit.
	 * 
	 * @effect This unit's opponent is set to the first attacker in the list of all its
	 *         attackers, if any. Otherwise, this unit's opponent is set to null.
	 *       | if (getNbAttackers() > 0)
	 *       |   then setOpponent(getAllAttackers().get(0))
	 *       | else
	 *       |   setOpponent(null)
	 */
	@Model
	private void setNextOpponent() {
		try {
			setOpponent(getAllAttackers().get(0));
		} catch (IndexOutOfBoundsException e) {
			setOpponent(null);
		}
	}

	/**
	 * Return a list of possible defenders.
	 * 
	 * @return All units of the list of all units in this world that stand on the same position
	 *         or on a neighbouring position as the position that this unit currently stands on.
	 *       | let
	 *       |   possiblePositions = getWorld().getAllNeighboursAndSame(getPosition())
	 *       |   possibleUnits = possibleUnits = getWorld().getAllUnits()
	 *       |   possibleDefenders = possibleDefenders = new ArrayList<Unit>()
	 *       | in
	 *       |   for each unit in possibleUnits
	 *       |     if (possiblePositions.contains(unit.getPosition()))
	 *       |       then possibleDefenders.add(unit)
	 *       | result == possibleDefenders
	 */
	@Raw @Model
	private List<Unit> getPossibleDefenders(){
		List<Position> possiblePositions = getWorld().getAllNeighboursAndSame(getPosition());
		Set<Unit> possibleUnits = getWorld().getAllUnits();
		List<Unit> possibleDefenders = new ArrayList<Unit>();
		for (Unit unit : possibleUnits) {
			if (possiblePositions.contains(unit.getPosition())) {
				possibleDefenders.add(unit);
			}
		}
		return possibleDefenders;
	}

	/**
	 * Select a random defender of the given list of possible defenders.
	 * @param  possibleDefenders
	 *         A list containing all possible defenders.
	 * @return null if there is no unit in the list of possible defenders
	 *         that belongs to a different faction than this unit.
	 *       | let 
	 *       |   flag = false
	 *       | in
	 *       |   for each def in possibleDefenders
	 *       |     if (getFaction() != def.getFaction())
	 *       |       then flag = true
	 *       |       then break
	 *       | if (flag == false)
	 *       |   then result == null
	 * @return A random unit of the list of possible defenders, if there
	 *         is a unit in that list that belongs to a different faction
	 *         than this unit.
	 *       | let
	 *       |   nb = new Random().nextInt(possibleDefenders.size())
	 *       | in
	 *       |   result == possibleDefenders.get(nb)
	 */
	@Raw @Model
	private Unit selectRandomDefender(List<Unit> possibleDefenders) {
		boolean flag = false;
		for (Unit def : possibleDefenders) {
			if (canFight(def)) {
				flag = true;
				break;
			}
		}
		if (flag == false) {
			return null;
		}
		Random random = new Random();
		int nb = random.nextInt(possibleDefenders.size());
		return possibleDefenders.get(nb);
	}

	/**
	 * Deal damage to a defending unit.
	 * 
	 * @param  defender
	 *         The defending unit.
	 * @effect The number of hitpoints of the defender is set to the difference between the
	 *         current number of hitpoints of the defender and the strength of the attack.
	 *       | defender.setNbHitpoints(defender.getNbHitPoints() - getAttackStrength())
	 */
	@Model @Raw
	private void dealdamageTo(@Raw Unit defender) {
		defender.setNbHitPoints(defender.getNbHitPoints() - getAttackStrength());
	}

	/**
	 * Set the orientation of an attacking unit and a defending unit according to their positions.
	 * 
	 * @param  attacker
	 *         The attacking unit.
	 * @param  defender
	 *         The defending unit.
	 * @effect The attackers orientation is set to the arctangent of the quotient of (1)
	 *         the difference between the defender's y-coordinate and the attacker's y-coordinate and
	 *         (2) the difference between the defender's x-coordinate and the attacker's x-coordinate.
	 *       | attacker.setOrientation(Math.atan2((defender.getPosition[.y() - attacker.getPosition.y()),
	 *       |                         (defender.getPosition.x() - attacker.getPosition.x())))
	 * @effect The defender's orientation is set to the arctangent of the quotient of (1)
	 *         The difference between the attacker's y-coordinate and the defender's y-coordinate and
	 *         (2) the difference between the attacker's x-coordinate and the defender's x-coordinate.
	 *       | defender.setOrientation(Math.atan2((attacker.getPosition.y() - defender.getPosition.y()),
	 *       |                         (attacker.getPosition.x() - defender.getPosition.x())))
	 */
	@Model @Raw
	private void setAttackOrientation(@Raw Unit attacker, @Raw Unit defender) {
		Position posA = attacker.getPosition();
		Position posD = defender.getPosition();
		attacker.setOrientation(Math.atan2((posD.y() - posA.y()), (posD.x() - posA.x())));
		defender.setOrientation(Math.atan2((posA.y() - posD.y()), (posA.x() - posD.x())));
	}

	/**
	 * Attempt to defend against the attacker.
	 * 
	 * @param  attacker
	 * 		   The unit that you have to defend against.
	 * @effect If this unit dodged the attack: move to an accessible adjacent position
	 *         on the same z-level.
	 * 		 | if (random.nextDouble() < getChanceToDodge(attacker, this))
	 * 		 |   then moveTo(getWorld().getRandomAccessibleNeighbouringPositionOnSameZ(getPosition()))
	 * @return True if the unit could successfully dodge.
	 * 		 | if (random.nextDouble() < getChanceToDodge(attacker, this))
	 * 		 |   then result == true
	 * @return True if the unit could successfully block the attack.
	 * 		 | if (random.nextDouble() < getChanceToBlock(attacker, this))
	 * 		 |   then result == true
	 * @return False if the unit could neither dodge or block the attack.
	 *       | if ((random.nextDouble() > getChanceToDodge(attacker, this)) &&
	 *       |        (random.nextDouble() > getChanceToBlock(attacker, this)))
	 *       |   then result == false
	 */    
	@Model @Raw
	private boolean defend(@Raw Unit attacker) {
		Random random = new Random();
		double dodgeLuck = random.nextDouble();
		if (dodgeLuck < getChanceToDodge(attacker, this)) {
			moveTo(getWorld().getRandomAccessibleNeighbouringPositionOnSameZ(getPosition()));
			return true;
		}
		double blockLuck = random.nextDouble();
		if (blockLuck < getChanceToBlock(attacker, this)) {
			return true;
		}
		return false;
	}

	/**
	 * Return a defending unit's chance to dodge an attack in view of its own and of the attacker's attributes.
	 * 
	 * @param  attacker
	 *         The attacking unit.
	 * @param  defender
	 *         The defending unit.
	 * @return The chance to dodge an attack is a positive double value.
	 *       | result > 0
	 */
	@Model @Raw
	private double getChanceToDodge(@Raw Unit attacker, @Raw Unit defender){
		return 0.20 * defender.getAgility() / attacker.getAgility();
	}

	/**
	 * Return a defending unit's chance to block an attack in view of its own and the attacker's attributes.
	 * 
	 * @param  attacker
	 *         The attacking unit.
	 * @param  defender
	 *         The defending unit.
	 * @return The chance to block an attack is a positive double value.
	 *       | result > 0
	 */
	@Model @Raw
	private double getChanceToBlock(@Raw Unit attacker, @Raw Unit defender) {
		return 0.25 * (defender.getStrength() + defender.getAgility()) / (attacker.getStrength() + attacker.getAgility());
	}

	//****************************************************************//

	// RESTING

	/**
	 * Return whether or not this unit is currently resting.
	 *   A unit can only rest if it stands in the center of the cube it currently occupies.
	 */
	@Basic @Raw
	public boolean isResting(){
		if (getPosition().positionInCenter()) {
			return this.resting;
		}
		return false;
	}

	/**
	 * Make this unit start resting.
	 * 
	 * @post The resting state of the unit is equal to true.
	 *     | new.isResting() == true
	 */
	@Raw
	public void startResting() {
		this.resting = true;
	}

	/**
	 * Make this unit stop resting.
	 * 
	 * @post The resting state of the unit is equal to false.
	 *     | new.isResting() == false
	 */
	@Raw
	public void stopResting(){
		this.resting = false;
	}

	/**
	 * Return the time needed to recover one hitpoint.
	 * 
	 * @return The time needed to recover one hitpoint is greater than zero.
	 *       | result > 0
	 */
	@Model @Raw
	private float getHitPointsRestTime() {
		return (float) (200 / getToughness() * JobStat.REST);
	}

	/**
	 * Return the time needed to recover one stamina point.
	 * 
	 * @return The time needed to recover one stamina point is greater than zero.
	 *       | result > 0
	 */
	@Model @Raw
	private float getStaminaRestTime() {
		return (float) (100 / getToughness() * JobStat.REST);
	}

	/**
	 * Make this unit rest.
	 * 
	 * @effect If the unit has no objective and is not falling, if the unit is not currently 
	 *         attacking another unit, if the unit isn't already resting and if the unit isn't 
	 *         performing its default behavior, then:
	 *       | if ((getObjectivePosition() == null) && !isAttacking() &&! isResting() && !doesDefaultBehaviour()
	 *       |         && !isFalling())
	 *         The unit's other jobs are reset.
	 * 		 | then resetAllJobs()
	 *         The unit starts resting.
	 * 		 | then startResting()
	 *         If the unit's current number of hit points is less than the
	 *         unit's maximum number of hit points, the job time is set to the
	 *         time needed to recover one hit point.
	 * 		 | then if (getNbHitPoints() < getMaxNbHitPoints())
	 * 		 |        then setJobTime(getHitPointsRestTime())
	 *         If the unit's current number of stamina points is less than the
	 *         unit's maximum number of stamina, the job time is set to the
	 *         time needed to recover one stamina point.
	 * 		 | then if (getNbStaminaPoints() < getMaxNbStaminaPoints())
	 * 		 |        then setJobTime(getStaminaRestTime())
	 */
	@Raw
	public void rest() {
		if ((getObjectivePosition() == null) && !isAttacking() && !isResting() && !doesDefaultBehavior()
				&& !isFalling()) {
			resetAllJobs();
			startResting();
			if (getNbHitPoints() < getMaxNbHitPoints()) {
				setJobTime(getHitPointsRestTime());
			} else {
				setJobTime(getStaminaRestTime());
			}
		}
	}

	/**
	 * Make this unit rest during its default behaviour.
	 * 
	 * @effect The unit starts resting.
	 * 		 | startResting()
	 * @effect If this unit's current number of hit points is less than the
	 *         unit's maximum number of hit points, the job time is set to the
	 *         time needed to recover one hit point.
	 * 		 | if (getNbHitPoints() < getMaxNbHitPoints())
	 * 		 |   then setJobTime(getHitPointsRestTime())
	 * @effect Else, if this unit's current number of stamina points is less than the
	 *         unit's maximum number of stamina, the job time is set to the
	 *         time needed to recover one stamina point.
	 * 		 | else if (getNbStaminaPoints() < getMaxNbStaminaPoints())
	 * 		 |        then setJobTime(getStaminaRestTime())
	 * @effect Else, this unit stops resting.
	 */
	@Raw @Model
	private void restWithDefault() {
		// Not needed to control a possible performance of jobs nor to invoke resetAllJobs:
		// This unit can't be performing a default
		// job because default actions can't interrupt each other.
		startResting();
		if (getNbHitPoints() < getMaxNbHitPoints()) {
			setJobTime(getHitPointsRestTime());
		} else if (getNbStaminaPoints() < getMaxNbStaminaPoints()) {
			setJobTime(getStaminaRestTime());
		} else {stopResting();}
	}

	/**
	 * Make this unit perform its resting behavior.
	 * 
	 * @param deltaTime
	 * 	      The time period, in seconds, by which to advance a unit's resting behaviour.
	 * @effect The new job time is the time left until the next incrementation of the
	 *         number of hitpoints, or if such an incrementation just occurred: the
	 *         time for an incrementation of hitpoints minus a possible overshoot.
	 *       | let
	 *       |    timeLeft = getJobtime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(getHitPointsRestTime() + timeLeft)
	 *       |    else
	 *       |      setJobTime(timeLeft)
	 * @effect If the current number of hit points is less than the maximum number of
	 *         hit points, the number of hit points is incremented by one.
	 *       | if (getNbHitPoints() < getMaxNbHitPoints())
	 *       |   then setNbHitPoints(getNbHitPoints() + 1)
	 * @effect If the current number of hitpoints is equal to the maximum number of hit points,
	 *         and if the current number of stamina points is less than the maximum number of
	 *         stamina points, the number of stamina points is incremented by one.
	 *       | if (((getNbHitPoints() == getMaxNbHitPoints()) && (getNbStaminaPoints() < getMaxNbStaminaPoints()))
	 *       |   then setNbStaminaPoints(getNbStaminaPoints() + 1)
	 * @effect If the current number of hitpoints is equal to the maximum number of hitpoints,
	 *         and if the current number of stamina points is equal to the maximum number of
	 *         stamina points, the unit will stop resting.
	 *       | if (((getNbHitPoints() == getMaxNbHitPoints()) && (getNbStaminaPoints() == getMaxNbStaminaPoints()))
	 *       |   then stopResting()
	 */
	@Model @Raw
	private void performRest(double deltaTime) {
		float timeLeft = getJobTime() - (float) deltaTime;

		if (timeLeft <= 0) {
			if (getNbHitPoints() < getMaxNbHitPoints()) {
				setNbHitPoints(getNbHitPoints() + 1);
				setJobTime(getHitPointsRestTime() + timeLeft);
			} else if (getNbStaminaPoints() < getMaxNbStaminaPoints()) {
				setNbStaminaPoints(getNbStaminaPoints() + 1);
				setJobTime(getStaminaRestTime() + timeLeft);
			} else {
				stopResting();
			}
		} else {
			setJobTime(timeLeft);
		}
	}

	//****************************************************************//

	// DEFAULT BEHAVIOUR

	/**
	 * Return whether or not this unit is currently performing default behavior.
	 */
	@Basic @Raw
	public boolean doesDefaultBehavior() {
		return this.doesDefaultBehavior;
	}

	/**
	 * Make this unit start performing default behavior.
	 * 
	 * @post if the unit is currently not moving and currently has no job,
	 *       the new default behavior of the unit is equal to true.
	 *     | if (! isMoving() && ! hasJob())
	 *     |   then new.doesDefaultBehaviour() == true
	 */
	@Raw
	public void startDefaultBehaviour() {
		if (! isMoving() && ! hasJob()) {
			this.doesDefaultBehavior = true;
		}
	}

	/**
	 * Make this unit stop performing default behavior.
	 * 
	 * @effect This unit's objective position is set to this unit's
	 *         current target position.
	 *       | setObjectivePosition(getTargetPosition())
	 * @effect This unit stops sprinting.
	 *       | stopSprinting()
	 * @effect This unit stops moving.
	 *       | stopMoving()
	 * @effect All this unit's jobs are reset.
	 *       | resetAllJobs()
	 * @post   The new default behaviour for the unit is equal to false.
	 *       | new.doesDefaultBehaviour() == false
	 */
	@Raw
	public void stopDefaultBehaviour() {
		if (getObjectivePosition() != null) {
			setObjectivePosition(getTargetPosition());
		}
		stopSprinting();
		stopMoving();
		resetAllJobs();
		this.doesDefaultBehavior = false;
	}

	/**
	 * Choose a random default behavior for this unit.
	 * 
	 * @post The unit is working or resting or moving to a random position or attacking.
	 *     | new.isWorking() || new.isResting() || new.isMoving() || new.isAttacking()
	 */
	@Model @Raw
	private void chooseDefaultBehavior() {
		Random random = new Random();
		int i = random.nextInt(4);
		if (i == 0) {
			workWithDefaultAt();
		} else if (i == 1) {
			restWithDefault();
		} else if (i == 2) {
			moveTo(getWorld().getRandomReachablePositionStartingFrom(getPosition()));
		} else if (i== 3) {
			attackWithDefault();
		}
	}

	//****************************************************************//

	// JOB: GENERAL

	/**
	 * Return whether or not this unit currently has a job.
	 * 
	 * @return True if and only if the unit is currently working, attacking, or resting.
	 *       | result == isWorking() || isAttacking() || isResting()
	 */
	@Model @Raw
	private boolean hasJob() {
		return isWorking() || isAttacking() || isResting();
	}

	/**
	 * Reset all jobs for this unit.
	 * 
	 * @effect The unit stops working and stops attacking and stops resting.
	 *       | stopWorking() && stopAttacking() && stopResting()
	 * @effect The work position is set to null.
	 *       | setWorkPosition(null)
	 */
	@Model @Raw
	void resetAllJobs() {
		stopWorking();
		stopAttacking();
		stopResting();
		setWorkPosition(null);
	}

	/**
	 * Return the time this unit has to work to finish a job.
	 * 
	 * @return A positive floating point value.
	 *       | result >= 0
	 */
	@Model @Raw
	private float getJobTime(){
		return this.jobTime;
	}

	/**
	 * Set the job time of this unit to the given job time.
	 * 
	 * @param jobTime
	 *        The new jobTime for the unit.
	 * @post  The new job time of the unit is equal to the given job time.
	 *      | new.getJobTime() == jobTime
	 */
	@Model @Raw
	private void setJobTime(float jobTime) {
		this.jobTime = jobTime;
	}

	//****************************************************************//

	// FACTION

	/**
	 * Return the faction to which this unit is attached.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}

	/**
	 * Set the faction of this unit to the given faction.
	 * 
	 * @post   The new faction of this unit is equal to the given faction.
	 *       | new.getFaction() == faction
	 * @throws IllegalArgumentException
	 *         This unit cannot have the given faction as its faction. 
	 *       | !canHaveAsFaction(faction)
	 */
	@Raw
	void setFaction(@Raw Faction faction) throws IllegalArgumentException {
		if (!canHaveAsFaction(faction)) {
			throw new IllegalArgumentException();
		}
		this.faction = faction;
	}

	/**
	 * Check whether this unit can have the given faction as its faction.
	 * 
	 * @return If this unit is terminated, true if and only if the given faction
	 *         is not effective. Else, true if and only if the given faction is
	 *         effective and not yet terminated.
	 *       | if (isTerminated)
	 *       |   then result == (faction == null)
	 *       | else result == (faction != null && !faction.isTerminated())
	 */
	@Raw @Model
	private boolean canHaveAsFaction(Faction faction) {
		if (isTerminated()) {
			return (faction == null);
		} else {
			return (faction != null && !faction.isTerminated());
		}
	}

	/**
	 * Check whether this unit belongs to a proper faction.
	 * 
	 * @return True if and only if this unit can have its faction as its
	 *         faction to which it is attached, and if the faction to which
	 *         this unit is attached contains this unit as a unit of that faction.
	 *       | if (!canHaveAsFaction(getFaction()))
	 *       |   then result == false
	 *       | else result == getFaction().hasAsUnit(this)
	 */
	@Raw @Model
	private boolean hasProperFaction() {
		if (!canHaveAsFaction(getFaction())) {
			return false;
		}
		return (getFaction().hasAsUnit(this));
	}

	/**
	 * Return whether this unit can fight the given unit.
	 * 
	 * @param  unit
	 *         The unit to compare with.
	 * @return False if the given unit is not effective.
	 *       | if (unit == null)
	 *       |   then result == false
	 * @return True if and only if the units are from different factions.
	 *       | result == (getFaction() != unit.getFaction())
	 */
	@Raw
	public boolean canFight(Unit unit) {
		if (unit == null) {
			return false;
		}
		return (getFaction() != unit.getFaction());
	}

	//****************************************************************// 

	// WORLD

	// In Entity.

	//****************************************************************//

	// HELPER FUNCTIONS

	/**
	 * Generate a pseudo-random name for a unit.
	 * 
	 * @return A pseudo-random name for a unit.
	 *       | ...
	 */
	public static String getRandomizedName() {
		Random random = new Random();
		String name = "";
		String[] adjectives = {"Drunken ", "Lovely ", "Monstrous ", "Fat ", "Gross ", "Great ",
				"Agressive ", "Stoic ", "Black ", "Little ", "Greedy ", "Amazing "};
		name += adjectives[random.nextInt(adjectives.length)];
		String[] nouns = {"Bob", "Mark", "Tom", "Leo", "Bart", "Suzanne",
				"Diana", "Lily", "Anna", "Kate", "PieterJan", "Emiel"};
		name += nouns[random.nextInt(nouns.length)];
		return name;
	}

	/**
	 * Return a random value between a lower and an upper limit.
	 * 
	 * @param  lower
	 *         The limit above which to search a value.
	 * @param  upper
	 *         The limit below which to search a value.
	 * @effect If the lower limit is greater than the upper limit,
	 *         this method is invoked with the former lower limit as
	 *         upper limit and the former upper limit as lower limit.
	 *       | if (upper < lower)
	 *       |   then getRandomizedValueBetween(upper, lower)
	 * @return A random value between the lower and the upper limit.
	 *       | let
	 *       |   delta = upper - lower
	 *       |   random = new Random().nextInt(delta)+1
	 *       | in
	 *       |   result == random
	 */
	public static int getRandomizedValueBetween(int lower, int upper) {
		if (upper < lower) {
			getRandomizedValueBetween(upper, lower);
		}
		int delta = upper - lower;
		int random = new Random().nextInt(delta)+1;
		return random;
	}	

	// INVENTORY

	/**
	 * Return the inventory of this unit.
	 */
	@Basic @Raw
	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * Check whether the given inventory is a valid inventory for
	 * any unit.
	 *  
	 * @param  inventory
	 *         The inventory to check.
	 * @return Always true.
	 *       | result == true
	 */
	public static boolean isValidInventory(Inventory inventory) {
		return true;
	}

	/**
	 * Set the inventory of this unit to the given inventory.
	 * 
	 * @param  inventory
	 *         The new inventory for this unit.
	 * @post   The inventory of this new unit is equal to
	 *         the given inventory.
	 *       | new.getInventory() == inventory
	 * @throws IllegalArgumentException
	 *         The given inventory is not a valid inventory for any
	 *         unit.
	 *       | ! isValidInventory(getInventory())
	 */
	@Raw
	private void setInventory(Inventory inventory) throws IllegalArgumentException {
		if (! isValidInventory(inventory))
			throw new IllegalArgumentException();
		this.inventory = inventory;
	}
	
	/**
	 * Return whether this unit is currently carrying a boulder.
	 * 
	 * @return True if and only if this unit's inventory contains a boulder.
	 *       | for some i in getInventory().getNbItems()
	 *       |   if (getInventory().getItemAt(i) instanceof Boulder)
	 *       |     then result == true 
	 */
	@Raw
	public boolean isCarryingBoulder() {
		for (int i = 1 ; i <= getInventory().getNbItems() ; i++) {
			if (getInventory().getItemAt(i) instanceof Boulder) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return whether this unit is currently carrying a log.
	 * 
	 * @return True if and only if this unit's inventory contains a log.
	 *       | for some i in getInventory().getNbItems()
	 *       |   if (getInventory().getAt(i) instanceof Log)
	 *       |     then result == true
	 */
	@Raw
	public boolean isCarryingLog() {
		for (int i=1 ; i<=getInventory().getNbItems() ; i++) {
			if (getInventory().getItemAt(i) instanceof Log) {
				return true;
			}
		}
		return false;
	}
	
	// TASKS
	
	/**
	 * Return the current task of this unit.
	 */
	@Basic
	public Task getTask() {
		return this.task;
	}
	
	// TODO
	public boolean hasTask() {
		return getTask() != null;
	}
	
	/**
	 * Check whether the given task is a valid task for any unit.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return Always true
	 *       | result == true
	 */
	public static boolean isValidTask(Task task) {
		return true;
	}
	
	/**
	 * Set the task of this unit to the given task.
	 * 
	 * @param task
	 *        The new task of this unit.
	 * @post  The new task of this unit is equal to the given task.
	 *      | new.getTask() == task
	 */
	public void setTask(Task task) {
		this.task = task;
	}
	
	// PROGRAM
	
	/**
	 * Return the program to which this unit is linked.
	 */
	@Basic
	public Program getProgram() {
		return this.program;
	}
	
	/**
	 * Check whether the given program is a valid program for any unit.
	 * 
	 * @param  program
	 *         The program to check.
	 * @return True if and only if the given program is not effective, or
	 *         if the program has not stopped and the main statement of the
	 *         program is well formed.
	 *       | result == (program == null) || (!program.hasStopped() &&
	 *       |           program.getMainStatement().isWellFormed())
	 */
	public static boolean isValidProgram(Program program) {
		return program == null || (!program.hasStopped() && 
				program.getMainStatement().isWellFormed());
	}
	
	/**
	 * Set the program to which this unit is currently linked to the given program.
	 * 
	 * @param program
	 *        The new program for this unit.
	 * @post  The new program of this unit is equal to the given program.
	 *      | new.getProgram() == program
	 */
	private void setProgram(Program program) {
		this.program = program;
		if (program != null) program.setUnit(this);
	}
	
	// TODO
	public Scheduler getScheduler() {
		return getFaction().getScheduler();
	}
	
	public void finishTask() {
		getTask().terminate();
		setTask(null);
		setProgram(null);
	}
	
	public void interruptTask() {
		getScheduler().transferUnfinishedTask(getTask());
		setTask(null);
		setProgram(null);
	}
	
	public boolean cannotStartAction() {
		return (hasJob() || isMoving());
	}
	
}

