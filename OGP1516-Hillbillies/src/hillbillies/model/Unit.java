package hillbillies.model;

import java.math.BigDecimal;
import java.util.Random;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.character.JobStat;
import hillbillies.world.Position;
import hillbillies.world.WorldStat;

/**
 * A class of units involving a name, a weight, a strength, an agility, a toughness,
 * a number of hitpoints, a number of stamina points and a certain position in the game world.
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
 * @invar The number of hitpoints of each unit must be a valid number of hitpoints for any
 *        unit.
 *      | isValidNbHitPoints(getNbHitPoints())
 * @invar The number of stamina points of each unit must be a valid number of stamina points for any
 *        unit.
 *      | isValidNbStaminaPoints(getNbStaminaPoints())
 * @invar The position of each unit must be a valid position for any
 *        unit.
 *      | isValidPosition(getUnitPosition())
 *      
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 1: 06/03/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */

public class Unit {

	/**
	 * Variable registering the game time passed.
	 */
	private static BigDecimal gameTime = new BigDecimal(0);
	
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
	 * Variable registering the number of hitpoints of this unit.
	 */
	private int nbHitPoints;
	
	/**
	 * Variable registering the number of stamina points of this unit.
	 */
	private int nbStaminaPoints;
	
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
	 * Variable referencing an array assembling the position coordinates of this unit.
	 */
	private Position unitPosition;
	
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
	 * Variable registering whether or not this unit is currently being attacked.
	 */
	private boolean defending = false;
	
	/**
	 * Variable registering whether or not this unit is resting.
	 */
	private boolean resting = false;
	
	/**
	 * Variable registering whether or not the default behaviour of this unit is enabled.
	 */
	private boolean doesDefaultBehaviour = false;
	
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
	 * Initialize this unit with given name, given strength, given agility, given weight, given toughness,
	 * the maximum value of the number of hitpoints and the number of stamina points, given initial
	 * position and a possibility to enable the unit's default behaviour.
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
	 * 		   Whether or not the default behaviour of this unit is enabled.
	 * @post   The initial name of this new unit is equal to the given name.
	 *       | new.getName() == name
	 * @post   The initial strength of this new unit is equal to the given strength.
	 *       | new.getStrength() == strength
	 * @post   The initial agility of this new unit is equal to the given agility.
	 *       | new.getAgility() == agility
	 * @post   The initial weight of this new unit is equal to the given weight.
	 *       | new.getWeight() == weight
	 * @post   The initial toughness of this new unit is equal to the given toughness.
	 *       | new.getToughness() == toughness
	 * @post   The initial number of hitpoints of this new unit is equal to the maximum number of hitpoints.
	 *       | new.getNbHitPoints() == getMaxNbHitPoints()
	 * @post   The initial number of stamina points of this new unit is equal to 
	 *         the maximum number of stamina points.
	 *       | new.getNbStaminaPoints() == getMaxNbStaminaPoints()
	 * @post   The initial position of this new unit is equal to the given position.
	 *       | new.getUnitPosition() == position
	 * @post   The possibility for a unit to perform its default behaviour is equal to the given flag.
	 *       | new.doesdefaultBehaviour == enableDefaultBehaviour.
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any unit.
	 *       | (! isValidName(name))
	 * @throws IllegalArgumentException
	 *         The given initial strength is not a valid initial strength for any unit.
	 *       | (! isValidInitialStrength(strength))
	 * @throws IllegalArgumentException
	 *         The given initial agility is not a valid initial agility for any unit.
	 *       | (! isValidInitialAgility(agility))
	 * @throws IllegalArgumentException
	 *         The given initial weight is not a valid initial weight for any unit.
	 *       | (! isValidInitialWeight(weight))
	 * @throws IllegalArgumentException
	 *         The given initial toughness is not a valid initial toughness for any unit.
	 *       | (! isValidInitialToughness(toughness))
	 * @throws IllegalArgumentException
	 *         The given initial position is not a valid position for any unit.
	 *       | (! isValidPosition(position))
	 */
	@Raw
	public Unit(String name, int strength, int agility, int weight, int toughness, 
				   int[] position, boolean enableDefaultBehaviour) throws IllegalArgumentException {
		setName(name);
		setInitialStrength(strength);
		setInitialAgility(agility);
		setInitialWeight(weight);
		setInitialToughness(toughness);
		setUnitPosition(Position.getCenterPosition(position));
		setNbHitPoints(getMaxNbHitPoints());
		setNbStaminaPoints(getMaxNbStaminaPoints());
		this.doesDefaultBehaviour = enableDefaultBehaviour;
	}	
		
	// GETTERS AND SETTERS
		
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
	public static boolean isValidName(String name) {
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
	 * @throws NullPointerException
	 *         The given name references the null object.
	 *         | name == null 
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any unit.
	 *         | ! (isValidName(name))
	 */
	@Raw
	public void setName(String name) throws NullPointerException, IllegalArgumentException {
		if (name == null) {
			throw new NullPointerException();
		} else if (! isValidName(name)) {
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
	public static boolean isValidStrength(int strength) {
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
	public static boolean isValidInitialStrength(int strength) {
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
	public static boolean isValidAgility(int agility) {
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
	public static boolean isValidInitialAgility(int agility) {
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
	 * Check whether the given weight is a valid weight for any unit.
	 * 
	 * @param  weight
	 *         The weight to check.
	 * @return True if and only if the given weight is a valid attribute, and if the weight 
	 *         is greater than or equal to the sum of the strength of this unit and the agility of this unit
	 *         divided by two.
	 *       | result == isValidAttribute(weight) && (weight >= (getStrength() + getAgility()) / 2)
	 */
	@Raw
	public boolean canHaveAsWeight(int weight) {
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
	@Raw
	public boolean canHaveAsInitialWeight(int weight) {
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
	 *       |    then makeValidWeight(weight)
	 * @post   The new weight of this unit is equal to the given weight.
	 *       | new.getWeight() == weight
	 */
	@Raw
	public void setWeight(int weight) {
		if (! canHaveAsWeight(weight)) {
			weight = makeValidWeight(weight);
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
	 *       |	  then makeValidInitialWeight(weight)
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
	 *       |	  then result == (getStrength() + getAgility()) / 2
	 * @return If the weight is greater than 200, the new weight is equal to 200.
	 *       | else if (weight > 200)
	 *       |	  then result == 200
	 * @return Otherwise, the initial weight is returned.
	 *         | else
	 *         |	result == weight
	 */
	@Raw
	public int makeValidWeight(int weight) {
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
	 *       |	  then result == (getStrength() + getAgility()) / 2
	 * @return If the weight is greater than 100, the new weight is equal to 100.
	 *       | else if (weight > 100)
	 *       |	  then result == 100
	 * @return Otherwise, the initial weight is returned.
	 *       | else
	 *       |	  result == weight
	 */
	@Raw
	public int makeValidInitialWeight(int weight) {
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
	public static boolean isValidToughness(int toughness) {
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
	public static boolean isValidInitialToughness(int toughness) {
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
	 *       |	  then makeValidAttribute(toughness)
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
	 *       |	  then makeValidInitialAttribute(toughness)
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
	public static boolean isValidAttribute(int attribute) {
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
	public static boolean isValidInitialAttribute(int attribute) {
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
	 *       |	  then result == getMaxAttributeValue()
	 * @return If the value of the attribute is less than the minimum attribute value, 
	 *         the new value of the attribute is equal to the minimum attribute value.
	 *       | else if (attribute < getMinAttributeValue())
	 *       |	  then result == getMinAttributeValue()
	 * @return Otherwise, the initial value is returned.
	 *       | else
	 *       |	  result == attribute
	 */
	@Raw
	public int makeValidAttribute(int attribute) {
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
	 *       |	  then result == getMaxInitialAttributeValue
	 * @return If the value of the attribute is less than the minimum initial attribute value, 
	 *         the new value of the attribute is equal to the minimum initial attribute value.
	 *       | else if (attribute < getMinInitialAttributeValue())
	 *       |	  then result == getMinInitialAttributeValue()
	 * @return Otherwise, the initial value is returned.
	 *       | else
	 *       |	  result == attribute
	 */
	@Raw
	public int makeValidInitialAttribute(int attribute) {
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
		return (nbHitPoints >= 0) && (nbHitPoints <= getMaxNbHitPoints());
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
	 * Return the position of this unit.
	 */
	@Basic @Raw
	public Position getUnitPosition() {
		return this.unitPosition;
	}
	
	/**
	 * Check whether the given position is a valid position for
	 * any unit.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the given position is effective and if 
	 *         each coordinate of the given position is a valid coordinate for any unit.
	 *       | result == (position.isValidX(position.x())) && position.isValidX(
	 *       |            position.x()) && position.isValidY(position.y()) &&
	 *       |            position.isValidZ(position.z())
	 */
	public static boolean isValidPosition(Position position) {
		if (position == null) {
			return false;
		}
		if ((! Position.isValidX(position.x())) || (! Position.isValidY(position.y())) 
		          || (! Position.isValidZ(position.z()))) {
			return false;
		}
		return true;
	}

	/**
	 * Set the position of this unit to the given position.
	 * 
	 * @param  position
	 *         The new position for this unit.
	 * @post   The new position of this unit is equal to
	 *         the given position.
	 *       | new.getUnitPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         unit.
	 *       | ! isValidPosition(position)
	 */
	@Raw @Model
	private void setUnitPosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.unitPosition = position;
	}
	
	/**
	 * Return whether or not the given position is a center position of a certain 
	 * cube of the game world.
	 * 
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the given position equals the center position of the
	 *         cube that contains the given position.
	 *       | result == position.positionEquals(position.getCenterPosition())
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any unit.
	 *       | ! isValidPosition(position)
	 */
	public static boolean positionInCenter(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		return position.equals(position.getCenterPosition());
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
	 *       | new.getTargetPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is effective and not a valid position for any
	 *         unit.
	 *       | ! isValidPosition(position) && ! (position == null)
	 */
	@Raw @Model
	private void setTargetPosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position) && ! (position == null)) {
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
	 *       | new.getObjectivePosition() == position
	 * @throws IllegalArgumentException
	 *         The given position effective and not a valid position for any 
	 *         unit.
	 *       | ! isValidPosition(position) && ! (position == null)
	 */
	@Raw @Model
	private void setObjectivePosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position) && ! (position == null)) {
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
	 *       | new.getStartPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any unit.
	 *       | ! isValidPosition(position)
	 */
	@Raw @Model
	private void setStartPosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		this.startPosition = position;
	}

	
	//****************************************************************//

	// ADVANCE TIME

	/**
	 * Advance the state of the given unit by the given time period.
	 *
	 * @param  deltaTime
	 *         The time period, in seconds, by which to advance the unit's state.
	 * @post   The given time period is added to the game time.
	 *       | ...
	 * @effect If this unit is currently attacking, the opponent's movement is
	 *         stopped, the unit's movement is stopped, the attack orientation
	 *         is set and the attack is performed.
	 *       | ...
	 * @effect If this unit is currently defending, no action is performed.
	 *       | ...
	 * @effect If this unit is currently working, the unit's movement is stopped
	 *         and the work is performed.
	 *       | ...
	 * @effect If this unit is currently resting, the unit's movement is stopped
	 *         and the rest is performed.
	 *       | ...
	 * @effect If this unit's current objective position is effective, the unit
	 *         walks for the given time period.
	 *       | ...
	 * @effect If this unit's default behaviour is enabled, a random default
	 *         behaviour is chosen.
	 *       | ...
	 */
	public void advanceTime(double deltaTime) {
		//deltaTime = setValidDeltaTime(deltaTime);
		gameTime = gameTime.add(new BigDecimal(deltaTime));
		if (getTargetPosition() != null) {
			walk(deltaTime);
		} else {
			if (isAttacking()) {
				setAttackOrientation(this, getOpponent());
				performAttack(deltaTime, getOpponent());
			} else if (isDefending()) {
				
				// TODO Check this by the number of attackers.
				// TODO PerformAttack() must decrease this amount by one when finished.
				
				// Defender must not do anything, but he can't move during the attack.
				// Therefore this clause: the unit can't jump into the last else if
				// statement to start walking if he has an effective objective.
			} else if (isWorking()) {
				performWork(deltaTime);
			} else if (isResting()) {
				performRest(deltaTime);
			} else if (getObjectivePosition() != null) {
				//Search next target in Path/Objective
				walk(deltaTime);
			} else if (doesDefaultBehaviour()) {
				chooseDefaultBehaviour();
			} 
		}
	}

	/**
	 * Make this unit walk for a given time period.
	 * 
	 * @param  deltaTime
	 *         The time period by which to walk.
	 * @effect The unit starts moving.
	 *       | startMoving()
	 * 
	 * @effect If the unit is currently sprinting, the position is updated in view of the
	 *         sprinting speed and the number of stamina points is updated accordingly. Else
	 *         the position is updated in view of the walking speed.
	 *       | if (isSprinting())
	 *       |   then updatePosition(deltaTime, getSprintingSpeed(getUnitPosition().
	 *       |             getCenterPosition.z(), getTargetPosition().getCenterPosition.z()))
	 *       |   then staminaDrain(deltaTime);
	 *       | else
	 *       |   updatePosition(deltaTime, getWalkingSpeed(getUnitPosition().getCenterPosition().
	 *       |             z(), getTargetPosition.getCenterPosition.z()))
	 * @effect If the unit has reached its target, the movement is stopped, and if the unit has 
	 *         reached its objective position, the unit's target position and the unit's objective 
	 *         position are set to null. Else, if the unit has no job, the unit continues pathing.
	 *       | if (hasReachedTarget())
	 *       |   then stopMovement()
	 *       | if (hasReachedTarget() && hasReachedObjective())
	 *       |   then setTargetPosition(null)
	 *       |   then setObjectivePosition(null)
	 *       | if (hasReachedTarget() && ! hasReachedObjective && ! hasJob())
	 *       |   then moveToAdjacent(moveToNext())
	 */
	@Raw @Model
	private void walk(double deltaTime) {
		// TODO Generates thousands of new objects (one each time step)
		// -> use gameTime to calculate a chance to start sprinting once
		// every second.
		if (doesDefaultBehaviour() && ! isSprinting() && getNbStaminaPoints() > 0) {
			double random = new Random().nextDouble();
			if (random < deltaTime / 5.0) {
				startSprinting();
			}
		}
		double speed = 0;
		if (isSprinting()) {
			speed = getSprintingSpeed(getUnitPosition().getCenterPosition().z(), 
					getTargetPosition().getCenterPosition().z());
			setCurrentSpeed(speed);
			updatePosition(deltaTime, speed);
			staminaDrain(deltaTime);
		} else {
			speed = getWalkingSpeed(getUnitPosition().getCenterPosition().z(),
					getTargetPosition().getCenterPosition().z());
			setCurrentSpeed(speed);
			updatePosition(deltaTime, speed);
		}
		if (hasReachedTarget(deltaTime)) {
			stopMovement();
			if (hasReachedObjective()) {
				setTargetPosition(null);
				setObjectivePosition(null);
			} else {
				moveToAdjacent(moveToNext());	
			}
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
	 *       | setUnitPosition(getUnitPosition().getCenterPosition())
	 * @effect The start position of the unit is set to the current unit position.
	 *       | setStartPosition(getUnitPosition())
	 */
	@Model @Raw
	private void stopMovement() {
		setCurrentSpeed(0);
		stopMoving();
		setUnitPosition(getUnitPosition().getCenterPosition());
		setStartPosition(getUnitPosition());
	}
	
	/**
	 * Move this unit to the objective position.
	 * 
	 * @param  objective
	 *         The objective position of this unit.
	 * @effect The objective position is set to the given objective.
	 *       | setObjectivePosition(objective)
	 * @effect The unit starts pathing.
	 *       | moveToAdjacent(moveToNext())
	 * @throws IllegalArgumentException
	 *         The objective is an invalid position for any unit.
	 *       | ! isValidPosition(objective)
	 */
	@Raw
	public void moveTo(Position objective) throws IllegalArgumentException {
		if (! isValidPosition(objective)) {
			throw new IllegalArgumentException();
		}
		setObjectivePosition(objective.getCenterPosition());
		moveToAdjacent(moveToNext());
	}

	/**
	 * Return the unit's next target position (i.e. the center of some neighbouring cube) in
	 * view of its current objective position.
	 *  
	 * @return For each coordinate: the center coordinate of the cube the unit currently
	 *         occupies if the unit's cube coordinate is on the same level as the corresponding
	 *         objective's cube coordinate; the center coordinate of the cube the unit currently
	 *         occupies plus one if the unit's cube coordinate is on a lower level than the
	 *         corresponding objective's cube coordinate; the center coordinate of the cube the 
	 *         unit currently occupies minus one if the unit's cube coordinate is on a higher
	 *         level than the corresponding objective's cube coordinate.
	 *       | let 
	 *       |    start = getUnitPosition().getCubePosition()
	 *       |    end = getObjectivePosition().getCubePosition()
	 *       |    center = getUnitPosition.getCenterPosition()
	 *       | in
	 *       |    for each index in (0..2)
	 *       |        if start.getAt(index) == end.getAt(index)
	 *       |           then result.getAt(index) == center.getAt(index)
	 *       |        else if start.getAt(index) < end.getAt(index)
	 *       |           then result.getAt(index) == center.getAt(index) + 1
	 *       |        else
	 *       |           then result.getAt(index) == center.getAt(index) - 1
	 */
	@Model @Raw
	private Position moveToNext() {
		int[] start = getUnitPosition().getCubePosition();
		int[] end = getObjectivePosition().getCubePosition();
		Position target = new Position();
		Position center = getUnitPosition().getCenterPosition();
		for (int i = 0; i < 3; i++) {
			if (start[i] == end[i]) {
				target.setAt(i, 0 + center.getAt(i));
			} else if (start[i] < end[i]) {
				target.setAt(i, 1 + center.getAt(i));
			} else {
				target.setAt(i, -1 + center.getAt(i));
			}
		}
		return target;
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
	 * @post   If the unit's current target position is effective, there is no effect.
	 *       | if (getTargetPosition() != null)
	 *       |   then return
	 * @effect Else, the  objective position of the unit is set to the sum of its current position
	 *         coordinates and x,y,z respectively.
	 *       | let
	 *       |   Position targetPosition = new Position()
	 *       |   targetPosition.setX(getUnitPosition().x() + x)
	 *	     |   targetPosition.setY(getUnitPosition().y() + y)
	 *	     |   targetPosition.setZ(getUnitPosition().z() + z)
	 *       | in
	 *       |   setObjectivePosition(targetPosition)
	 * @effect The unit is moved to the new objective position.
	 *       | moveToAdjacent(getObjectivePosition())
	 */
	@Raw
	public void moveToAdjacent(int x, int y, int z) throws IllegalArgumentException {
		if (getTargetPosition() != null) {
			return;
		}
		Position targetPosition = new Position();
		targetPosition.setX(getUnitPosition().x() + x);
		targetPosition.setY(getUnitPosition().y() + y);
		targetPosition.setZ(getUnitPosition().z() + z);
		setObjectivePosition(targetPosition);
		moveToAdjacent(targetPosition);
	}
	
	/**
	 * Move this unit to an adjacent cube.
	 * 
	 * @param  targetPosition
	 *         The new target position for the unit
	 * @effect All jobs are reset.
	 *       | resetAllJobs()
	 * @effect The unit starts moving.
	 *       | startMoving()
	 * @effect The start position of the unit is set to the current unit position.
	 *       | setStartPosition(getUnitPosition)
	 * @effect The target position of the unit is set to the given target position.
	 *       | setTargetPosition(targetPosition)
	 */
	@Raw
	public void moveToAdjacent(Position targetPosition) throws IllegalArgumentException {
		resetAllJobs();
		startMoving();
		setStartPosition(getUnitPosition());
		setTargetPosition(targetPosition);
		if (isSprinting()){
			setCurrentSpeed(getSprintingSpeed(getUnitPosition().z(), getTargetPosition().z()));
		} else {
			setCurrentSpeed(getWalkingSpeed(getUnitPosition().z(), getTargetPosition().z()));
		}
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
		double[] unitSpeed = getUnitSpeed(speed);
		updatePosition(deltaTime, speed, unitSpeed);
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
	 *       | 	  updatedUnitPosition = getUnitPosition() + getUnitSpeed() * deltaTime
	 *       | in
	 *       | 	 if (isValidPosition(updatedUnitPosition))
	 *       |     then setUnitPosition(updatedUnitPosition)
	 *       |   else
	 *       |     then stopMovement()
	 */
	@Model @Raw
	private void updatePosition(double deltaTime, double speed, double[] unitSpeed) {
		Position updatedUnitPosition = new Position();
		setCurrentSpeed(speed);
		setWalkingOrientation(unitSpeed);
		for (int i = 0; i < 3; i++) {
			updatedUnitPosition.setAt(i, getUnitPosition().getAt(i) + unitSpeed[i] * deltaTime);
		}
		try {
			setUnitPosition(updatedUnitPosition);
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
	 *       | result == Position.getDistance(getStartPosition(), getUnitPosition()) >=
	 *       |              Position.getDistance(getStartPosition(), getTargetPosition())
	 */
	@Model @Raw
	private boolean hasReachedTarget(double deltaTime) {
		double startToUnit = Position.getDistance(getStartPosition(), getUnitPosition());
		double startToTarget = Position.getDistance(getStartPosition(), getTargetPosition());
		return startToUnit >= startToTarget;
	}
	
	/**
	 * Return whether a unit has reached its objective.
	 * 
	 * @return True if and only if the unit's current cube position is equal to the
	 *         objective's cube position.
	 *       | result == (getUnitPosition().getCubePosition() == 
	 *       |               getObjectivePosition().getCubePosition())
	 */
	@Model @Raw
	private boolean hasReachedObjective() {
		int[] unitCube = getUnitPosition().getCubePosition();
		int[] objectiveCube = getObjectivePosition().getCubePosition();
		for (int i = 0; i < 3; i++) {
			if (unitCube[i] != objectiveCube[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Return the base speed of this unit in meters per second.
	 * 
	 * @return The base speed of a unit is a double value greater than zero.
	 * 		 | result >= 0
	 */
	@Raw @Model
	private double getBaseSpeed() {
		return 1.5 * (getStrength() + getAgility()) / (200 * getWeight() / 100);
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
	 * @return Half the base speed if the z-level of the cube the unit currently occupies 
	 *         is one level lower than the z-level of the cube the unit is moving to.
	 *       | if (z - targZ == -1)
	 *       |    then result == 0.5 * getBaseSpeed()
	 * @return 1.2 times the base speed if the z-level of the cube the unit currently occupies
	 *         is one level higher than the z-level of the cube the unit is moving to.
	 *       | if (z - targZ == 1)
	 *       |    then result == 1.2 * getBaseSpeed()
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
	 *       |	  distance = Position.getDistance(getTargetPosition(), getUnitPosition())
	 *       | in
	 *       |	  result == speed * (getTargetPosition() - getUnitPosition()) / distance
	 */
	@Model @Raw
	private double[] getUnitSpeed(double speed) {
		double[] unitSpeed = new double[3];
		double distance = Position.getDistance(getTargetPosition(), getUnitPosition());
		for (int i = 0; i < unitSpeed.length; i++) {
			unitSpeed[i] = speed * (getTargetPosition().getAt(i) - getUnitPosition().getAt(i)) / distance;
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
	 * @return Two times the walking speed of this unit.
	 *       | result == 2 * getWalkingSpeed(z, targZ)
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
	 */
	@Basic @Raw
	public boolean isWorking(){
		if (positionInCenter(getUnitPosition())) {
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
	 * @effect All jobs of the unit are reset.
	 *       | resetAllJobs()
	 * @effect If the unit's default behaviour is enabled, the unit's -
	 *         default behaviour is disabled.
	 *       | if (doesDefaultBehaviour())
	 *       |   then stopDefaultBehaviour()
	 * @effect The unit starts working.
	 *       | startWorking()
	 * @effect The job time of the unit is set to the work time.
	 *       | setJobTime(getWorkTime())
	 */
	@Raw
	public void work() {
		if (getObjectivePosition() == null) {
			resetAllJobs();
		    if (doesDefaultBehaviour) {
			    stopDefaultBehaviour();
		    }
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
	@Model @Raw
	private void workWithDefault() {
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
	@Model @Raw
	private void performWork(double deltaTime) {
		float timeLeft = getJobTime() - (float) deltaTime;
		if (timeLeft <= 0) {
			setJobTime(0);
			stopWorking();
		} else {
			setJobTime(timeLeft);
		}
	}
	
	//****************************************************************//
	
	// FIGHTING
	
	/**
	 * Return whether or not this unit is currently attacking.
	 */
	@Basic @Raw
	public boolean isAttacking() {
		if (positionInCenter(getUnitPosition())) {
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
	 */
	@Basic @Raw
	public boolean isDefending() {
		if (positionInCenter(getUnitPosition())) {
			return this.defending;
		}
		return false;
	}
	
	/**
	 * Make this unit start defending.
	 * 
	 * @post The new defending state of the unit is equal to true.
	 *     | new.isDefending() == true
	 */
	@Raw
	public void startDefending() {
		this.defending = true;
	}
	
	/**
	 * Make this unit stop defending.
	 * 
	 * @post The new defending state of the unit is equal to false.
	 *     | new.isDefending() == false
	 */
	@Raw
	public void stopDefending() {
		this.defending = false;
	}
	
	/**
	 * Return the opponent unit of this unit.
	 */
	@Model @Raw
	private Unit getOpponent() {
		return this.opponent;
	}
	
	/**
	 * Set the opponent unit of this unit to the given opponent unit.
	 * 
	 * @param  opponent
	 *         The opponent unit.
	 * @post   The new opponent unit of this unit is equal to the given opponent unit.
	 *       | new.getOpponent() == opponent
	 * @throws IllegalArgumentException
	 *         The opponent is not effective and the opponent is this unit.
	 *       | ! (opponent == null) && (opponent.equals(this))
	 */
	@Model @Raw
	private void setOpponent(@Raw Unit opponent) throws IllegalArgumentException {
		if (! (opponent == null) && opponent.equals(this)) {
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
	 * Attack another unit.
	 * 
	 * @param  defender
	 * 		   The unit this unit is going to attack.
	 * @effect If this unit and the defender are in neighbouring cubes or occupy the same
	 *         cube, this unit's and the defender's jobs are reset.
	 *       | if (isAdjacentToSame(getUnitPosition(), defender.getUnitPosition()))
	 *       |   then resetAllJobs()
	 *       |   then defender.resetAllJobs()
	 * @effect If this unit and the defender are in neighbouring cubes or occupy the same
	 *         cube, then: (1) if this unit's default behaviour is enabled, this unit's default
	 *         behaviour is disabled; (2) if the defender's default behaviour is enabled, the defender's
	 *         default behaviour is disabled.
	 *       | if (isAdjacentToSame(getUnitPosition(), defender.getUnitPosition()) &&
	 *       |       (doesDefaultBehaviour()))
	 *       |   then stopDefaultBehaviour()
	 *       | if (isAdjacentToSame(getUnitPosition(), defender.getUnitPosition()) &&
	 *       |       (defender.doesDefaultBehaviour()))
	 *       |   then defender.stopDefaultBehaviour.
	 * @effect If this unit and the defender are in neighbouring cubes or occupy the same
	 *         cube, this unit starts attacking and the defender starts defending.
	 * 		 | if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition()))
	 * 		 |   then startAttacking()
	 *       |   then defender.startDefending()
	 * @effect If this unit and the defender are in neighbouring cubes or occupy the same
	 *         cube, the defender is set as this unit's opponent, if possible, and the job
	 *         time is set to the attack time. Else, This unit stops attacking and the 
	 *       | if (isAdjacentToSame(getUnitPosition(), defender.getUnitPosition()) &&
	 *       |      ((opponent == null) || ! (opponent.equals(this))))
	 *       |   then setOpponent(defender)
	 *       |   then setJobTime(JobStat.ATTACK)
	 *       | else
	 *       |   then stopAttacking()
	 *       |   then defender.stopDefending()
	 */
	@Raw
	public void attack(@Raw Unit defender) {
		if (getObjectivePosition() == null) {
			if (isAdjacentToOrSame(getUnitPosition(), defender.getUnitPosition())) {
				resetAllJobs();
				defender.resetAllJobs();
				if (doesDefaultBehaviour()) {
					stopDefaultBehaviour();
				}
				if (defender.doesDefaultBehaviour()) {
					defender.stopDefaultBehaviour();
				}
				startAttacking();
				defender.startDefending();
				try {
					setOpponent(defender);
					setJobTime(JobStat.ATTACK);
				} catch (IllegalArgumentException e) {
					stopAttacking();
					defender.stopDefending();
				}
			}
		}
	}
	
	/**
	 * Make this unit perform its attacking attacking behaviour.
	 * 
	 * @param  deltaTime
	 * 	       The time period, in seconds, by which to advance a unit's attacking behaviour.
	 * @effect The new job time is the time left to complete an attack or, if the attack just
	 *         finished in the current time step, the job time is set to zero.
	 *       | let
	 *       |    timeLeft = getJobTime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(0)
	 *       |    else
	 *       |      setJobTime(timeLeft)
	 * @effect If the time left to complete the attack is less than or equal to zero, and if
	 *         the defender was not able to dodge or block the attack, damage is dealt to the
	 *         defender.
	 *       | let
	 *       |   timeLeft = getJobTime() - (float) deltaTime
	 *       | in
	 *       |   if ((timeLeft <= 0) && (! (defender.defend(this))))
	 *       |     then dealdamageTo(defender)
     * @effect If this unit has finished its current attack, the unit will stop attacking and
     *         the defender will stop defending and this unit's opponent is set to null.
     *       | if getJobTime() == 0
     *       |   then stopAttacking()
     *       |   then defender.stopDefending()
     *       |   then setOpponent(null)
	 */
	@Model @Raw
	private void performAttack(double deltaTime, @Raw Unit defender) {
		float timeLeft = getJobTime() - (float) deltaTime;
		
		if (timeLeft <= 0) {
			if (! defender.defend(this)) {
				dealdamageTo(defender);
			}
			setJobTime(0);
			stopAttacking();
			defender.stopDefending();
			setOpponent(null);
		} else {
			setJobTime(timeLeft);
		}
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
	 *       | attacker.setOrientation(Math.atan2((defender.getUnitPosition[.y() - attacker.getUnitPosition.y()),
	 *       |                         (defender.getUnitPosition.x() - attacker.getUnitPosition.x())))
	 * @effect The defender's orientation is set to the arctangent of the quotient of (1)
	 *         The difference between the attacker's y-coordinate and the defender's y-coordinate and
	 *         (2) the difference between the attacker's x-coordinate and the defender's x-coordinate.
	 *       | defender.setOrientation(Math.atan2((attacker.getUnitPosition.y() - defender.getUnitPosition.y()),
	 *       |                         (attacker.getUnitPosition.x() - defender.getUnitPosition.x())))
	 */
	@Model @Raw
	private void setAttackOrientation(@Raw Unit attacker, @Raw Unit defender) {
		Position posA = attacker.getUnitPosition();
		Position posD = defender.getUnitPosition();
		attacker.setOrientation(Math.atan2((posD.y() - posA.y()), (posD.x() - posA.x())));
		defender.setOrientation(Math.atan2((posA.y() - posD.y()), (posA.x() - posD.x())));
	}
	
	/**
	 * Attempt to defend against the attacker.
	 * 
	 * @param  attacker
	 * 		   The unit that you have to defend against.
	 * @effect If the unit dodged the attack: move to an adjacent position.
	 * 		 | if (random.nextDouble() < getChanceToDodge(attacker, this))
	 * 		 |   then goToRandom(2)
	 * @return True if the unit could successfully dodge.
	 * 		 | if (random.nextDouble() < getChanceToDodge(attacker, this))
	 * 		 |   then result == true
	 * @return True if the unit could successfully block the attack.
	 * 		 | if (random.nextDouble() < getChanceToBlock(attacker, this))
	 * 		 |   then result == true
	 * @return False if the unit could neither dodge or block the attack.
	 *       | if ((random.nextDouble() > getChanceToDodge(attacker, this)) &&
	 *       |        (random.nextDouble() > getChanceToBlock(attacker, this)))
	 *       | then result == false
	 */    
	@Model @Raw
	private boolean defend(@Raw Unit attacker) {
		Random random = new Random();
		double dodgeLuck = random.nextDouble();
		if (dodgeLuck < getChanceToDodge(attacker, this)) {
			goToRandom(2);
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
	 */
	@Basic @Raw
	public boolean isResting(){
		if (positionInCenter(getUnitPosition())) {
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
	
	// TODO 3 seconds
	/**
	 * Make this unit rest.
	 * 
	 * @effect The unit's other jobs are reset.
	 * 		 | resetAllJobs()
	 * @effect If the unit's default behaviour is enabled, the unit's
	 *         default behaviour is disabled.
	 *       | if (doesDefaultBehaviour())
	 *       |   then stopDefaultBehaviour()
	 * @effect The unit starts resting.
	 * 		 | startResting()
	 * @effect If the unit's current number of hit points is less than the
	 *         unit's maximum number of hit points, the job time is set to the
	 *         time needed to recover one hit point.
	 * 		 | if (getNbHitPoints() < getMaxNbHitPoints())
	 * 		 |   then setJobTime(getHitPointsRestTime())
	 * @effect If the unit's current number of stamina points is less than the
	 *         unit's maximum number of stamina, the job time is set to the
	 *         time needed to recover one stamina point.
	 * 		 | if (getNbStaminaPoints() < getMaxNbStaminaPoints())
	 * 		 |   then setJobTime(getStaminaRestTime())
	 */
	@Raw
	public void rest() {
		if (getObjectivePosition() == null) {
			resetAllJobs();
			if (doesDefaultBehaviour()) {
				stopDefaultBehaviour();
			}
			startResting();
			if (getNbHitPoints() < getMaxNbHitPoints()) {
				setJobTime(getHitPointsRestTime());
			} else {
				setJobTime(getStaminaRestTime());
			}
		}
	}
	
	// TODO 3 seconds
	/**
	 * Make this unit rest during its default behaviour.
	 * 
	 * @effect The unit starts resting.
	 * 		 | startResting()
	 * @effect If the unit's current number of hit points is less than the
	 *         unit's maximum number of hit points, the job time is set to the
	 *         time needed to recover one hit point.
	 * 		 | if (getNbHitPoints() < getMaxNbHitPoints())
	 * 		 |   then setJobTime(getHitPointsRestTime())
	 * @effect If the unit's current number of stamina points is less than the
	 *         unit's maximum number of stamina, the job time is set to the
	 *         time needed to recover one stamina point.
	 * 		 | if (getNbStaminaPoints() < getMaxNbStaminaPoints())
	 * 		 |   then setJobTime(getStaminaRestTime())
	 */
	@Raw @Model
	private void restWithDefault() {
		startResting();
		if (getNbHitPoints() < getMaxNbHitPoints()) {
			setJobTime(getHitPointsRestTime());
		} else {
			setJobTime(getStaminaRestTime());
		}
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
	public boolean doesDefaultBehaviour() {
		return this.doesDefaultBehaviour;
	}
	
	/**
	 * Make this unit start performing default behavior.
	 * 
	 * @post if the unit is currently not moving and currently has no job,
	 *       the new default behaviour of the unit is equal to true.
	 *     | if (! isMoving() && ! hasJob())
	 *     |   then new.doesDefaultBehaviour() == true
	 */
	@Raw
	public void startDefaultBehaviour() {
		if (! isMoving() && ! hasJob()) {
			this.doesDefaultBehaviour = true;
		}
	}

	/**
	 * Make this unit stop performing default behavior.
	 * 
	 * @effect The unit stops moving.
	 *       | stopMoving()
	 * @effect The objective position is set to the current target position.
	 *       | setObjectivePosition(getTargetPosition())
	 * @post   The new default behaviour for the unit is equal to false.
	 *       | new.doesDefaultBehaviour() == false
	 */
	@Raw
	public void stopDefaultBehaviour() {
		stopMoving();
		setObjectivePosition(getTargetPosition());
		this.doesDefaultBehaviour = false;
	}
	
	/**
	 * Choose a random default behaviour for this unit.
	 * 
	 * @post The unit is working or resting or moving to a random position.
	 *     | new.isWorking() || new.isResting() || new.isMoving()
	 */
	@Model @Raw
	private void chooseDefaultBehaviour() {
		Random random = new Random();
		int i = random.nextInt(3);
		if (i == 0) {
			workWithDefault();
		} else if (i == 1) {
			restWithDefault();
		} else if (i == 2 && getObjectivePosition() == null) {
			goToRandom(WorldStat.MAX_X);
		}
	}
	
	/**
	 * Walk to a random location.
	 * 
	 * @param  factor
	 * 		   The amount of cubes to walk at most in any direction.
	 * @effect Move to a random location.
	 * 		 | let
	 *       |   Position spot = new Position()
	 *       |   multiplier = random.nextInt(factor - 1) + 1
	 *       | in
	 *       |   for each index in 0..2
	 *       |     spot.setAt(index, getUnitPosition().getAt(index) +
	 *       |                    getRandomDirection() * multiplier)
	 *       |   moveTo(spot)
	 */
	@Model @Raw
	private void goToRandom(int factor) {
		Random random = new Random();
		Position spot = new Position();
		double multiplier = random.nextInt(factor - 1) + 1;
		
		do {
			for (int i = 0; i < 3; i++) {
				spot.setAt(i, getUnitPosition().getAt(i) + getRandomDirection() * multiplier);
			}
		} while (! isValidPosition(spot));
		moveTo(spot);
	}
	
	/**
	 * Return a valid random direction for this unit to move in.
	 *  
	 * @return A random integer with a value of -1, 0 or +1.
	 *       | result == -1 || result == 0 || result == 1   
	 */
	@Model @Raw
	private int getRandomDirection() {
		Random random = new Random();
		int nb = random.nextInt(3);
		if (nb == 0) {
			return 0;
		} else if (nb == 1) {
			return 1;
		} else {
			return -1;
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
	 */
	@Model @Raw
	private void resetAllJobs() {
		stopWorking();
		stopAttacking();
		stopResting();
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
	
	// HELPER FUNCTIONS
	
	/**
	 * Return whether a certain position is adjacent to or the same as another position.
	 * 
	 * @param  current
	 *         An array containing the position coordinates of the first position to compare.
	 * @param  target
	 *         An array containing the position coordinates of the second position to compare.
	 * @return True if the current position is in the same cube as the target position.
	 *       | if (current.getCenterPosition().positionEquals(target.getCenterPosition()))
	 *       |   then result == true
	 * @return True if the 2 cubes differ from each other at most by one on any of the three axes.
	 * 		 | let 
	 * 		 |    currentCube = current.getCubePosition()
	 * 		 |    targetCube = target.getCubePosition()
	 * 		 | in
	 * 		 |   if ((currentCube[0] + 1 == targetCube[0] || 
	 * 		 |	  	  currentCube[0] - 1 == targetCube[0] || 
	 * 		 |		  currentCube[0] == targetCube[0]) &&
	 *		 |			  (currentCube[1] + 1 == targetCube[1] || 
	 *		 |			   currentCube[1] - 1 == targetCube[1] || 
	 *		 |			   currentCube[1] == targetCube[1]) &&
	 *		 |				  (currentCube[2] + 1 == targetCube[2] || 
	 *		 |				   currentCube[2] - 1 == targetCube[2] || 
	 *		 |				   currentCube[2] == targetCube[2]))
	 *		 |     then result == true
	 *		 |   else 
	 *       |     result == false
	 * @throws IllegalArgumentException
	 *         current or target are no valid positions.
	 *       | (! isValidPosition(current)) || (! isValidPosition(target))
	 */
	 @Raw
	 public static boolean isAdjacentToOrSame(Position current, Position target) throws IllegalArgumentException {	
	 	 if (! isValidPosition(current) || ! isValidPosition(target)) {
			 throw new IllegalArgumentException();
		 }
		 if (current.getCenterPosition().equals(target.getCenterPosition())) {
			 return true;
		 }
		 int[] currentCube = current.getCubePosition();
		 int[] targetCube = target.getCubePosition();
		 if ((currentCube[0] + 1 == targetCube[0] || currentCube[0] - 1 == targetCube[0] || currentCube[0] == targetCube[0]) &&
			 (currentCube[1] + 1 == targetCube[1] || currentCube[1] - 1 == targetCube[1] || currentCube[1] == targetCube[1]) &&
			 (currentCube[2] + 1 == targetCube[2] || currentCube[2] - 1 == targetCube[2] || currentCube[2] == targetCube[2])) {
			 return true;
		 } 
		 return false;
	 }
}

// getUnitPosition, getCubePosition in Unit (p.6)
// Niet schuin naar een ander z-level bewegen
// weight veranderen als strength/agility veranderen
// Hitpoints/stamina points veranderen als weight/toughness veranderen
// getWalkingSpeed: -1 -> <0 , 1 -> >0
// Random position na ontwijken: zelfde z-level (p.11)
// Tests in folder tests

// Rusten, aanvallen, werken op bewegende unit: geen effect
// Verdedigen op bewegende unit: effect (p.9)

// Rusten, aanvallen, verdedigen, bewegen op werkende unit: effect (p.10)
// Aanvallen, verdedigen, werken, bewegen op rustende unit: effect (p.12) 