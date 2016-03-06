package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import ogp.framework.util.Util;

/**
 * A class of units involving a name, a weight, a strength, an agility, a toughness,
 * a number of hitpoints, a number of stamina points and a certain position in the game world.
 * 
 * @invar The name of each unit must be a valid name for any unit. 
 *      | isValidName(getName())
 * @invar The weight of each unit must be a valid weight for any unit.
 *      | isValidWeight(getWeight())
 * @invar The strength of each unit must be a valid strength for any
// *        unit.
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
 *      | isValidPosition(getPosition())
 *      
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 1: 06/03/2016
 */

public class Unit {

	/**
	 * Variable registering the name of this unit.
	 */
	private String name;
	
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
	 * Variable registering the minimum value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private static int minAttributeValue = 1;

	/**
	 * Variable registering the maximum value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private static int maxAttributeValue = 200;
	
	/**
	 * Variable registering the minimum initial value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private static int minInitialAttributeValue = 25;

	/**
	 * Variable registering the maximum initial value of an attribute (strength, agility, weight, toughness)
	 * of each unit.
	 */
	private static int maxInitialAttributeValue = 100;
	
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
	private double[] unitPosition = new double[3];
	
	/**
	 * Variable referencing an array storing the (intermediate) target position of this moving unit.
	 */
	private double[] targetPosition = null;
	
	/**
	 * Variable referencing an array storing the (final) objective position of this moving unit.
	 */
	private double[] objectivePosition = null;
	
	/**
	 * Variable referencing an array storing the start position of this moving unit.
	 */
	private double[] startPosition = new double[3];

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
	 * Variable registering whether or not this unit is executing default behaviour.
	 */
	private boolean defaultBehaviour;
	
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
	 * 		   Whether the default behaviour of this unit is enabled.
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
	 *       | new.defaultBehaviour == enableDefaultBehaviour.
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
		setNbHitPoints(getMaxNbHitPoints());
		setNbStaminaPoints(getMaxNbStaminaPoints());
		setUnitPosition(getCenterPosition(position));
		
		this.defaultBehaviour = enableDefaultBehaviour;
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
	 *       |              && (name.matches("[A-Za-z]['][\"]+"))
	 */
	public static boolean isValidName(String name) {
		return (name.length() >= 2) && (Character.isUpperCase(name.charAt(0)));
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
	@Raw @Model
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
	@Raw
	private void setInitialToughness(int toughness) {
		if (! isValidInitialAttribute(toughness)) {
			toughness = makeValidInitialAttribute(toughness);
		}
		this.toughness = toughness;
	}
	
	/**
	 * Return the minimum value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic 
	public static int getMinAttributeValue() {
		return Unit.minAttributeValue;
	}
	
	/**
	 * Return the maximum value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic
	public static int getMaxAttributeValue() {
		return Unit.maxAttributeValue;
	}
	
	/**
	 * Return the minimum initial value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic
	public static int getMinInitialAttributeValue() {
		return Unit.minInitialAttributeValue;
	}
	
	/**
	 * Return the maximum initial value of an attribute (strength, agility, weight, toughness) of each unit.
	 */
	@Basic
	public static int getMaxInitialAttributeValue() {
		return Unit.maxInitialAttributeValue;
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
	public static int makeValidAttribute(int attribute) {
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
	public static int makeValidInitialAttribute(int attribute) {
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
	@Raw @Model
	private void setNbHitPoints(int nbHitPoints) {
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
	@Raw @Model
	private void setNbStaminaPoints(int nbStaminaPoints) {
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
	private void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Return the current speed of this unit.
	 */
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
	private void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	/**
	 * Return the position of this unit.
	 */
	@Basic @Raw
	public double[] getUnitPosition() {
		return this.unitPosition;
	}
	
	/**
	 * Check whether the given position is a valid position for
	 * any unit.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the length of the position array is equal to 3, 
	 *         and if each element of the position array is greater than or equal to the minimum
	 *         cube coordinate of the game world and less than the maximum cube coordinate of
	 *         the game world.
	 *       | result == (position.length == 3) &&
	 *       | for each element in position
	 *       |    (element >= GameWorld.MIN_X) && (element < GameWorld.MAX_X)
	 */
	public static boolean isValidPosition(double[] position) {
		if (position == null) {
			return false;
		}
		if (position.length != 3) {
			return false;
		}
		for (double value : position) {
			if (value < GameWorld.MIN_X || value >= GameWorld.MAX_X) {
				return false;
			}
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
	private void setUnitPosition(double[] position) throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.unitPosition = position;
	}
	
	/**
	 * Return the target position of this unit.
	 *    The target position of a unit is the center position of one of the neighbouring 
	 *    (horizontally, vertically and diagonally) cubes of this unit.
	 */
	@Basic @Raw
	private double[] getTargetPosition() {
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
	 *         The given position is not a valid position for any
	 *         unit.
	 *       | ! isValidPosition(position)
	 */
	@Raw @Model
	private void setTargetPosition(double[] position) throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.targetPosition = position;
	}
	
	/**
	 * Return the objective position of this unit.
	 *    The objective position of a unit is the center of the cube in the game world this unit
	 *    is moving to.
	 */
	@Basic @Raw
	private double[] getObjectivePosition() {
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
	 *         The given position is not a valid position for any unit.
	 *       | ! isValidPosition(position) 
	 */
	@Raw
	private void setObjectivePosition(double[] position) throws IllegalArgumentException {
		if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		this.objectivePosition = position;
	}
	
	/**
	 * Return the start position of this unit.
	 *    The start position of a unit is the center of that cube from where the unit started moving.
	 */
	@Basic @Raw
	private double[] getStartPosition() {
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
	private void setStartPosition(double[] position) throws IllegalArgumentException {
		if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		this.startPosition = position;
	}

	/**
	 * Return the base speed of this unit in meters per second.
	 * 
	 * @return The base speed of a unit is a double value greater than zero.
	 * 		 | result >= 0
	 */
	@Raw
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
	 *       | if z == targZ
	 *       |    then result == getBaseSpeed()
	 * @return Half the base speed if the z-level of the cube the unit currently occupies 
	 *         is one level lower than the z-level of the cube the unit is moving to.
	 *       | if z - targZ == -1
	 *       |    then result == 0.5 * getBaseSpeed()
	 * @return 1.2 times the base speed if the z-level of the cube the unit currently occupies
	 *         is one level higher than the z-level of the cube the unit is moving to.
	 *       | if z - targZ == 1
	 *       |    then result == 1.2 * getBaseSpeed()
	 */
	@Raw
	private double getWalkingSpeed(double z, double targZ) {
		double walkingSpeed = getBaseSpeed();
		double delta = z - targZ;
		
		if (Util.fuzzyEquals(delta, -1)) {
			walkingSpeed *= 0.5;
		} else if (Util.fuzzyEquals(delta, 1)) {
			walkingSpeed *= 1.2;
		}
		return walkingSpeed;
	}

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
	@Raw
	private double getSprintingSpeed(double z, double targZ) {
		return 2d * getWalkingSpeed(z, targZ);
	}

	/**
	 * Return whether or not this unit is currently sprinting.
	 */
	public boolean isSprinting() {
		return this.sprinting;
	}
	
	/**
	 * Enable sprinting mode for this unit.
	 * 
	 * @post The sprinting mode of this unit is equal to true.
	 *     | new.isSprinting() == true
	 */
	public void startSprinting() {
		this.sprinting = true;
	}

	/**
	 * Disable sprinting mode for this unit.
	 * 
	 * @post The sprinting mode of this unit is equal to false.
	 *     | new.isSprinting() == false
	 */
	public void stopSprinting() {
		this.sprinting = false;
	}

	/**
	 * Move this unit to the objective position.
	 * 
	 * @param  objective
	 *         The objective position of this unit.
	 * @post   The new position of this unit is equal to the center of the cube that
	 *         contains the given objective.
	 *       | new.getUnitPosition() == getCenterPosition(objective)
	 * @throws IllegalArgumentException
	 *         The objective is an invalid position for any unit.
	 *       | ! isValidPosition(objective)
	 */
	@Raw
	public void moveTo(double[] objective) throws IllegalArgumentException {
		
		if (! isValidPosition(objective)) {
			throw new IllegalArgumentException();
		}
		
		setObjectivePosition(objective);
		moveToAdjacent(moveToNext());
	}

	/**
	 * Return an array containing the coordinates of this unit's next target position
	 * (i.e. some neighbouring cube) in view of its current objective position.
	 *  
	 * @return For each coordinate the center coordinate of the cube the unit currently
	 *         occupies if the unit's cube coordinate is on the same level as the corresponding
	 *         objective's cube coordinate, the center coordinate of the cube the unit currently
	 *         occupies plus one if the unit's cube coordinate is on a lower level than the
	 *         corresponding objective's cube coordinate, the center coordinate of the cube the 
	 *         unit currently occupies minus one if the unit's cube coordinate is on a higher
	 *         level than the corresponding objective's cube coordinate.
	 *       | let 
	 *       |    start = getCubePosition(getUnitPosition())
	 *       |    end = getCubePosition(getObjectivePosition())
	 *       |    center = getCenterPosition(getUnitPosition())
	 *       | in
	 *       |    for each index in (0..2)
	 *       |        if start[index] == end[index]
	 *       |           then result[index] == center[index]
	 *       |        else if start[index] < end[index]
	 *       |           then result[index] == center[index] + 1
	 *       |        else
	 *       |           then result[index] == center[index] - 1
	 */
	private double[] moveToNext() {
		int[] start = getCubePosition(getUnitPosition());
		int[] end = getCubePosition(getObjectivePosition());
		
		double[] target = new double[3];
		double[] center = getCenterPosition(getUnitPosition());
		
		for (int i = 0; i < target.length; i++) {
			if (start[i] == end[i]) {
				target[i] = 0 + center[i];
			} else if (start[i] < end[i]) {
				target[i] = 1 + center[i];
			} else {
				target[i] = -1 + center[i];
			}
		}
		return target;
	}

	/**
	 * Move this unit to an adjacent cube.
	 * 
	 * @param x
	 *        The amount of cubes to move in the x-direction (-1, 0 or +1).
	 * @param y
	 * 		  The amount of cubes to move in the y-direction (-1, 0 or +1).
	 * @param z
	 *        The amount of cubes to move in the z-direction (-1, 0 or +1).
	 * @post  The new position of the unit is equal to the sum of its current position
	 *        coordinates and x,y,z respectively.
	 *      | new.getUnitPosition()[0] == this.getUnitPostition()[0] + x
	 *      | new.getUnitPosition()[1] == this.getUnitPostition()[1] + y
	 *      | new.getUnitPosition()[2] == this.getUnitPostition()[2] + z
	 */
	@Raw @Model
	public void moveToAdjacent(int x, int y, int z) {
		// defensief
		
		if (getTargetPosition() != null) {
			return;
		}
		
		double[] targetPosition = new double[3];
		
		targetPosition[0] = getUnitPosition()[0] + x;
		targetPosition[1] = getUnitPosition()[1] + y;
		targetPosition[2] = getUnitPosition()[2] + z;
		
		moveToAdjacent(targetPosition);
	}
	
	/**
	 * Move this unit to an adjacent cube.
	 * 
	 * @param  targetPosition
	 *         The new target position for the unit
	 * @effect The start position of the unit is set to the current unit position.
	 *       | new.getStartPosition() == getUnitPosition()
	 * @effect The target position of the unit is set to the given target position.
	 *       | new.getTargetPosition() == targetPosition
	 */
	public void moveToAdjacent(double[] targetPosition) throws IllegalArgumentException {
		setStartPosition(getUnitPosition());
		setTargetPosition(targetPosition);
	}
	
	/**
	 * Advance the state of the given unit by the given time period.
	 * 
	 * If enabled the method will choose a random behavior for a unit that is idle.
	 * After this, the method will check whether the unit is moving.
	 * If so, the position will be updated accordingly.
	 * If not, any behaviour (working, resting, attacking another unit) will be executed.
     *
	 * @param deltaTime
	 *        The time period, in seconds, by which to advance the unit's state.
	 */
	@Raw @Model
	public void advanceTime(double deltaTime) {
		// deltaTime tussen 0 en 0.2
		// defensief
		
		//Select default behaviour
		if (doesDefaultBehaviour() && ! hasJob()) {
			chooseDefaultBehaviour();
		}
		
		//Perform job
		if (isWorking() && ! isWalking()) {
			performWork(deltaTime);
		}
		if (isResting() && ! isWalking()) {
			performRest(deltaTime);
		}
		if (isAttacking() && ! isWalking()) {
			performAttack(deltaTime, getOpponent());
		}
		
		//Movement
		if (isWalking()) {
			resetAllJobs();
			
			if (isSprinting()) {
				updatePosition(deltaTime, getSprintingSpeed(unitPosition[2], targetPosition[2]));
				staminaDrain(deltaTime);
				
			} else {
				updatePosition(deltaTime, getWalkingSpeed(unitPosition[2], targetPosition[2]));
				
			}
				
			if (hasReachedTarget()) {
						
				stopMovement();
				
				if (getObjectivePosition() != null) {
					if (hasReachedObjective()) {
						this.objectivePosition = null;
					} else {
						moveToAdjacent(moveToNext());
					}
					
				}
			} 
		}
	}

	/**
	 * Update the position of this unit according to the time step and its current speed.
	 * 
	 * @param  deltaTime
	 *         The time period, in seconds, by which to update this unit's position.
	 * @param  speed
	 * 	       The current speed of the unit.
	 * @effect This unit's position is set to the sum of (1) the unit's current position and 
	 *         (2) the product of the speed vector and the given time period.
	 *       | let
	 *       | 	  updatedUnitPosition = getUnitPosition() + getUnitSpeed() * deltaTime
	 *       | in
	 *       | 	  setUnitPosition(updatedUnitPosition)
	 */
	private void updatePosition(double deltaTime, double speed) {
		double[] unitSpeed = getUnitSpeed(speed);
		double[] updatedUnitPosition = new double[3];
		
		setCurrentSpeed(speed);
		setWalkingOrientation(unitSpeed);
		
		for (int i = 0; i < updatedUnitPosition.length; i++) {
			updatedUnitPosition[i] = getUnitPosition()[i] + unitSpeed[i] * deltaTime;
		}
		
		try {
			setUnitPosition(updatedUnitPosition);
		} catch (IllegalArgumentException e) {
			stopMovement();
		}
	}
	
	/**
	 * Return the distance between two points in the three-dimensional space.
	 * 
	 * @param  a
	 * 		   The coordinates of the first point, as an array of doubles {x,y,z}.
	 * @param  b
	 *  	   The coordinates of the second point, as an array of doubles {x,y,z}. 
	 * @return The square root of the sum of the squares of the differences of the respective coordinates.
	 *       | result == Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2) 
			 |               + Math.pow(a[2] - b[2], 2));
	 * @throws IllegalArgumentException
	 *         The length of a or b is not equal to 3.
	 *       | (a.length != 3) || (b.length != 3)
	 */
	private double getDistance(double[] a, double[] b) throws IllegalArgumentException {
		if (a.length != 3 || b.length != 3) {
			throw new IllegalArgumentException();
		}
		return Math.sqrt(Math.pow(a[0] - b[0], 2)
			           + Math.pow(a[1] - b[1], 2) 
			           + Math.pow(a[2] - b[2], 2));
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
	 *       |	  distance = getDistance(getTargetPosition(), getUnitPosition())
	 *       | in
	 *       |	  result == speed * (targetPosition() - unitPosition()) / distance
	 */
	private double[] getUnitSpeed(double speed) {
		double[] unitSpeed = new double[3];
		double distance = getDistance(getTargetPosition(), getUnitPosition());
		
		for (int i = 0; i < unitSpeed.length; i++) {
			unitSpeed[i] = speed * (targetPosition[i] - unitPosition[i]) / distance;
		}
		return unitSpeed;
	}
	
	/**
	 * Return the position of the cube this unit currently occupies.
	 * 
	 * @param  position
	 *         An array containing the position coordinates.
	 * @return The given position coordinates, rounded down to integer values.
	 *       | result == (int) position
	 */
	public int[] getCubePosition(double[] position) {
		int[] cubePosition = new int[3];
		
		for (int i = 0; i < cubePosition.length; i++) {
			cubePosition[i] = (int) position[i];
		}
		return cubePosition;
	}
	
	/**
	 * Return the position of the center of the cube this unit currently occupies.
	 * 
	 * @param  position
	 *         An array of doubles containing the position coordinates.
	 * @return The center of the cube that contains the given position.
	 *       | result == getCenterPosition(getCubePosition(position))
	 */
	private double[] getCenterPosition(double[] position) {
		int[] cubePosition = getCubePosition(position);
		return getCenterPosition(cubePosition);
	}
	
	/**
	 * Return the position of the center of the cube this unit currently occupies.
	 * 
	 * @param position
	 *        An array of integers containing the position coordinates.
	 * @return The sum of (1) this given position coordinates and
	 *         (2) [for each coordinate] the length of the side of a cube divided by two.
	 *         | for each index in (0..position.length)
	 *         |	result[index] = position[index] + GameWorld.CUBE_LENGTH / 2
	 */
	public double[] getCenterPosition(int[] position) {
		double[] centerPosition = new double[3];
		
		for (int i = 0; i < centerPosition.length; i++) {
			centerPosition[i] = position[i] + (double) GameWorld.CUBE_LENGTH / 2;
		}
		return centerPosition;
	}
	
	/**
	 * Return whether a unit has reached its target.
	 * 
	 * @return True if and only if the distance between the start position of the unit
	 *         and the unit's current position is greater than or equal to the distance
	 *         between the start position of the unit and the target position of the unit.
	 *       | result == getDistance(getStartPosition(), getUnitPosition()) >=
	 *       |              getDistance(getStartPosition(), getTargetPosition())
	 */
	private boolean hasReachedTarget() {
		double startToUnit = getDistance(getStartPosition(), getUnitPosition());
		double startToTarget = getDistance(getStartPosition(), getTargetPosition());
		return startToUnit >= startToTarget;
	}
	
	/**
	 * Return whether a unit has reached its objective.
	 * 
	 * @return True if and only if the unit's current cube position is equal to the
	 *         objective's cube position.
	 *       | result == (getCubePosition(getUnitPosition()) == 
	 *       |              getCubePosition(getObjectivePosition()))
	 */
	private boolean hasReachedObjective() {
		int[] unitCube = getCubePosition(getUnitPosition());
		int[] objectiveCube = getCubePosition(getObjectivePosition());
		
		for (int i = 0; i < 3; i++) {
			if (unitCube[i] != objectiveCube[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Return whether or not this unit is currently moving.
	 */
	public boolean isMoving() {
		
		return this.moving;
	}
	
	/**
	 * Stop the movement of this unit.
	 * 
	 * @effect The current speed of the unit is set to zero.
	 *       | setCurrentSpeed(0)
	 * @effect The current unit position is set to the center position
	 *         of the current unit position.
	 *       | setUnitPosition(getCenterPosition(getUnitPosition()))
	 * @effect The start position of the unit is set to the current unit position.
	 *       | setStartPosition(getUnitPosition())
	 * @post   The new target position of the unit is equal to null.
	 *       | new.getTargetPosition() == null
	 */
	private void stopMovement() {
		
		setCurrentSpeed(0);
		setUnitPosition(getCenterPosition(getUnitPosition()));
		setStartPosition(getUnitPosition());
		targetPosition = null;
		
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
	private void setWalkingOrientation(double[] speed) {
		setOrientation(Math.atan2(speed[1], speed[0]));
	}
	
	/**
	 * Return whether a certain position is adjacent to another position.
	 * 
	 * @param  current
	 *         An array containing the position coordinates of the first position to compare.
	 * @param  target
	 *         An array containing the position coordinates of the second position to compare.
	 * @return False if the cube of the first coordinate equals the cube of the second coordinate.
	 * 		 | let 
	 * 		 |    currentCube = getCubePosition(current)
	 * 		 |    targetCube = getCubePosition(target)
	 * 		 | in
	 * 		 |   if ((currentCube[0] == targetCube[0]) &&
	 *		 |     (currentCube[1] == targetCube[1]) &&
	 *		 |     (currentCube[2] == targetCube[2]))
	 *		 |       then result == false
	 * @return True if the 2 cubes differ from each other at most by one on any of the three axis.
	 * 		 | let 
	 * 		 |    currentCube = getCubePosition(current)
	 * 		 |    targetCube = getCubePosition(target)
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
	 *		 |     else result == false
	 * @throws IllegalArgumentException
	 */
	private boolean isAdjacentTo(double[] current, double[] target) throws IllegalArgumentException {
		// defensief
		
		if (! isValidPosition(current) || ! isValidPosition(target)) {
			throw new IllegalArgumentException();
		}
		
		int[] currentCube = getCubePosition(current);
		int[] targetCube = getCubePosition(target);
		
		if ((currentCube[0] == targetCube[0]) &&
			(currentCube[1] == targetCube[1]) &&
			(currentCube[2] == targetCube[2])) {
			return false;
		}
		
		if ((currentCube[0] + 1 == targetCube[0] || currentCube[0] - 1 == targetCube[0] || currentCube[0] == targetCube[0]) &&
			(currentCube[1] + 1 == targetCube[1] || currentCube[1] - 1 == targetCube[1] || currentCube[1] == targetCube[1]) &&
			(currentCube[2] + 1 == targetCube[2] || currentCube[2] - 1 == targetCube[2] || currentCube[2] == targetCube[2])) {
			return true;
		} 
		
		return false;
	}
	
	/**
	 * Keep track of the amount of stamina consumed on the time passed.
	 * 
	 * @param  deltaTime
	 * 		   The time period, in seconds, by which to update this unit's stamina.
	 * @effect The new sprint time will be the time left until next stamina reduction
	 * 		   or if such a stamina reduction just occurred: the time between reductions
	 * 		   minus the overshoot.
	 * 		 | let
	 * 		 |    timeLeft = getSprintTime() - (float) deltaTime
	 * 		 | in
	 * 		 |    if (timeLeft <= 0)
	 * 		 |      then setSprintTime(JobStat.SPRINT + timeLeft)
	 * 		 |      else setSprintTime(timeLeft)
	 * @post   If the stamina reduction occurs, the amount of stamina points will be reduced
	 * 		   by one.
	 * 		 | if (timeLeft <= 0)
	 * 		 |   then new.getNbStaminaPoints == this.getNbStaminaPoints - 1
	 * @effect If there is a stamina reduction and the stamina of this unit is already 0
	 * 		   the unit will stop sprinting.
	 * 		 | if (timeLeft <= 0 && ! this.getNbStaminaPoints() > 0)
	 * 		 |   then stopSprinting()
	 */
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
	
	//----------------------------------------------//
	
	/**
	 * Return whether or not this unit is currently working.
	 */
	public boolean isWorking(){
		return this.working;
	}
	
	/**
	 * Make the unit start working.
	 * 
	 * @post The working state of the unit is equal to true.
	 *     | new.isWorking() == true
	 */
	public void startWorking(){
		this.working = true;
	}
	
	/**
	 * Return the time this unit has to work to finish a job.
	 * 
	 * @return The work time is a positive floating point value.
	 *       | result > 0
	 */
	private float getWorkTime(){
		return 500 / getStrength();
	}
	
	/**
	 * Make this unit stop working.
	 * 
	 * @post The working state of the unit is equal to false.
	 *     | new.isWorking() == false
	 */
	public void stopWorking(){
		this.working = false;
	}
	
	/**
	 * Return the time this unit has to work to finish a job.
	 * 
	 * @return A positive floating point value.
	 *       | result >= 0
	 */
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
	private void setJobTime(float jobTime) {
		this.jobTime = jobTime;
	}
	
	/**
	 * Return the sprint time of this unit.
	 */
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
	private void setSprintTime(float sprintTime) {
		this.sprintTime = sprintTime;
	}
	
	
	
	
	
	//----------------------------------------------//
	
	
	
	
	// FIGHTING
	
	/**
	 * Attack another unit.
	 * 
	 * @param  defender
	 * 		   The unit you are going to attack.
	 * @effect Make the opponents face each other.
	 * 		 | if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition()))
	 * 		 |   then setAttackOrientation(this, defender)
	 * @effect Make sure the defender is not moving.
	 * 		 | if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition()))
	 * 		 |   then defender.stopMovement()
	 * @effect This unit must stop doing any other jobs.
	 * 		 | if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition()))
	 * 		 |   then resetAllJobs()
	 * @effect This unit can start attacking.
	 * 		 | if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition()))
	 * 		 |   then startAttacking
	 * @effect Defender will be set as this unit's opponent
	 * 		 | if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition()))
	 * 		 |   then setOpponent(defender)
	 * @effect The job timer must be set to the time to attack another unit.
	 * 		 | if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition()))
	 * 		 |   then setJobTime(JobStat.ATTACK)
	 */
	public void attack(Unit defender) {
		// 1 seconde wachten voor aanval
		// controleer of de defender dichtbij is
		
		if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition())) {
			setAttackOrientation(this, defender);
			defender.stopMovement();
			
			resetAllJobs();
			startAttacking();
			setOpponent(defender);
			setJobTime(JobStat.ATTACK);
		}
	}
	
	/**
	 * Make this unit perform its resting attacking behaviour.
	 * 
	 * @param deltaTime
	 * 	      The time period, in seconds, by which to advance a unit's attacking behaviour.
	 * @effect The new job time is the time left to complete an attack or, if the attack just
	 *         finished in the current time step, the job time is set to 0.
	 *       | let
	 *       |    timeLeft = getJobtime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(0)
	 *       |    else
	 *       |      setJobTime(timeLeft)
	 * @effect If the current number of hitpoints is less than the maximum number of
	 *         hitpoints, the number of hitpoints is incremented by one.
	 *       | if (getNbHitPoints() < getMaxNbHitPoints())
	 *       |   then setNbHitPoints(getNbHitPoints() + 1)
	 * @effect If the attacker and the defender are in adjacent cubes, and if the defender cannot
	 *         dodge or block the attack, damage is done to the defender.
	 *       | if ((isAdjacentTo(getUnitPosition(), defender.getUnitPosition())) &&
	 *       |         ! (defender.defend(this)))
	 *       |   then dealDamageTo(defender)   
     * @effect If the unit has finished its current attack, the unit will stop attacking.
     *       | if getJobTime() == 0
     *       |   then stopAttacking()
	 */
	private void performAttack(double deltaTime, Unit defender) {
		float timeLeft = getJobTime() - (float) deltaTime;
		
		if (timeLeft <= 0) {
			if (isAdjacentTo(getUnitPosition(), defender.getUnitPosition())) {
				if (! defender.defend(this)) {
					dealdamageTo(defender);
				}
				
				setJobTime(0);
				stopAttacking();
				//setOpponent(null);
			}
		} else {
			setJobTime(timeLeft);
		}
	}
	
	/**
	 * Make this unit start attacking.
	 * 
	 * @post The attacking state of the unit is equal to true.
	 *     | new.isAttacking() == true
	 */
	public void startAttacking() {
		this.attacking = true;
	}
	
	/**
	 * Make this unit stop attacking.
	 * 
	 * @post The attacking state of the unit is equal to false.
	 *     | new.isAttacking() == false
	 */
	public void stopAttacking() {
		this.attacking = false;
	}

	/**
	 * Return whether or not this unit is currently attacking.
	 */
	public boolean isAttacking() {
		return this.attacking;
	}
	
	/**
	 * Attempt to defend against the attacker.
	 * 
	 * @param  attacker
	 * 		   The unit that you have to defend against.
	 * @effect If the unit dodged the attack: move to an adjacent position.
	 * 		 | if (dodgeLuck < getChanceToDodge(attacker, this))
	 * 		 |   then goToRandom(2)
	 * @return True if the unit could successfully dodge.
	 * 		 | if (random.nextDouble() < getChanceToDodge(attacker, this))
	 * 		 |   then result == true
	 * @return True if the unit could successfully block the attack.
	 * 		 | if (random.nextDouble() < getChanceToBlock(attacker, this))
	 * 		 |   then result == true
	 */
	private boolean defend(Unit attacker) {
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
	private double getChanceToDodge(Unit attacker, Unit defender){
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
	 */
	private double getChanceToBlock(Unit attacker, Unit defender) {
		return 0.25 * (defender.getStrength() + defender.getAgility()) / (attacker.getStrength() + attacker.getAgility());
	}
	
	/**
	 * Return the strength of the attack.
	 * 
	 * @return The strength of this unit divided by 10.
	 *       | result == getStrength() / 10
	 */
	private int getAttackStrength() {
		return getStrength() / 10;
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
	private void dealdamageTo(Unit defender) {
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
	 *       | attacker.setOrientation(Math.atan2((defender.getUnitPosition[1] - attacker.getUnitPosition[1]),
	 *                                (defender.getUnitPosition[0] - attacker.getUnitPosition[0])))
	 * @effect The defender's orientation is set to the arctangent of the quotient of (1)
	 *         The difference between the attacker's y-coordinate and the defender's y-coordinate and
	 *         (2) the difference between the attacker's x-coordinate and the defender's x-coordinate.
	 *       | defender.setOrientation(Math.atan2((attacker.getUnitPosition[1] - defender.getUnitPosition[1]),
	 *                                (attacker.getUnitPosition[0] - defender.getUnitPosition[0])))
	 */
	private void setAttackOrientation(Unit attacker, Unit defender) {
		double[] posA = attacker.getUnitPosition();
		double[] posD = defender.getUnitPosition();
		
		attacker.setOrientation(Math.atan2((posD[1] - posA[1]), (posD[0] - posA[0])));
		defender.setOrientation(Math.atan2((posA[1] - posD[1]), (posA[0] - posD[0])));
	}
	
	/**
	 * Start walking to a random location (at most factor cubes in any direction).
	 * 
	 * @param  factor
	 * 		   The amount of cubes to walk at most in any direction.
	 * @effect Start walking to a random location.
	 * 		 | moveTo(spot)
	 */
	private void goToRandom(int factor) {
		Random random = new Random();
		double[] spot = new double[3];
		double multiplier = random.nextInt(factor - 1) + 1;
		
		do {
			for (int i = 0; i < spot.length; i++) {
				spot[i] = getUnitPosition()[i] + getRandomDirection() * multiplier;
			}
		} while (! isValidPosition(spot));
		
		moveTo(spot);
	}
	
	/**
	 * Return a valid random direction for this unit to move in.
	 *  
	 * @return A random integer with a value of -1, 0 or +1.
	 *       | result == -1 || 0 || 1   
	 */
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
	
	/**
	 * Return the opponent unit of this unit.
	 */
	private Unit getOpponent() {
		return this.opponent;
	}
	

	/**
	 * Set the opponent unit of this unit to the given opponent unit.
	 * 
	 * @param opponent
	 *        The opponent unit.
	 * @post  The new opponent unit of this unit is equal to the given opponent unit.
	 *      | new.getOpponent() == opponent
	 */
	private void setOpponent(Unit opponent) {
		this.opponent = opponent;
	}
	
	
	//----------------------------------------------//
	
	
	
		// WORKING
	
	/**
	 * Make this unit work.
	 * 
	 * @effect All jobs of the unit are reset.
	 *       | resetAllJobs()
	 * @effect The unit is made start working.
	 *       | startWorking()
	 * @effect The job time of the unit is set to the work time.
	 *       | setJobTime(getWorkTime())
	 */
	public void work() {
		resetAllJobs();
		startWorking();
		setJobTime(getWorkTime());
	}
	
	/**
	 * Make this unit perform its working behaviour.
	 * 
	 * @param deltaTime
	 * 	      The time period, in seconds, by which to advance a unit's working behaviour.
	 * @effect The new job time is the time left until the next incrementation of the
	 *         number of hitpoints, or if such an incrementation just occurred: the time to
	 *         finish a job
	 *       | let
	 *       |    timeLeft = getJobtime() - (float) deltaTime
	 *       | in
	 *       |    if (timeLeft <= 0)
	 *       |      then setJobTime(getHitPointsRestTime() + timeLeft)
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
	private void performWork(double deltaTime) {
		float timeLeft = getJobTime() - (float) deltaTime;
		
		if (timeLeft <= 0) {
			//work done
			setJobTime(getWorkTime() + timeLeft);
			stopWorking();
		} else {
			setJobTime(timeLeft);
		}
	}

	
		// RESTING
	
	/**
	 * Make this unit rest.
	 * 
	 * @effect Start resting.
	 * 		 | startResting()
	 * @effect Stop all other jobs.
	 * 		 | resetAllJobs()
	 * @effect Initialize the job timer to recover hit points.
	 * 		 | if (getNbHitPoints() < getMaxNbHitPoints())
	 * 		 |   then setJobTime(getHitPointsRestTime())
	 * @effect Initialize the job timer to recover stamina points.
	 * 		 | else if (getNbStaminaPoints() < getMaxNbStaminaPoints())
	 * 		 |   setJobTime(getStaminaRestTime())
	 * @effect Stop resting when already completely full on points.
	 * 		 | else
	 * 		 |   stopResting()
	 */
	public void rest() {
		resetAllJobs();
		startResting();
		
		if (getNbHitPoints() < getMaxNbHitPoints()) {
			setJobTime(getHitPointsRestTime());
		} else {
			setJobTime(getStaminaRestTime());
		}
	}
	
	/**
	 * Return whether or not this unit is currently resting.
	 */
	public boolean isResting(){
		return this.resting;
	}
	
	/**
	 * Make this unit start resting.
	 * 
	 * @post The resting state of the unit is equal to true.
	 *     | new.isResting() == true
	 */
	private void startResting() {
		this.resting = true;
	}
	
	/**
	 * Make this unit stop resting.
	 * 
	 * @post The resting state of the unit is equal to false.
	 *     | new.isResting() == false
	 */
	private void stopResting(){
		this.resting = false;
	}
	
	/**
	 * Make this unit perform its resting behaviour.
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
	 * @effect If the current number of hitpoints is less than the maximum number of
	 *         hitpoints, the number of hitpoints is incremented by one.
	 *       | if (getNbHitPoints() < getMaxNbHitPoints())
	 *       |   then setNbHitPoints(getNbHitPoints() + 1)
	 * @effect If the current number of hitpoints is equal to the maximum number of hitpoints,
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
	
	/**
	 * The time needed to recover one hit point.
	 */
	private float getHitPointsRestTime() {
		return (float) (200 / getToughness() * JobStat.REST);
	}
	
	/**
	 * The time needed to recover one stamina point.
	 */
	private float getStaminaRestTime() {
		return (float) (100 / getToughness() * JobStat.REST);
	}
	
	
		// DEFAULT

	/**
	 * Make this unit start performing default behaviour.
	 * 
	 * @post The new default behaviour of this unit is equal to true.
	 *     | new.doesDefaultBehaviour() == true
	 */
	public void startDefaultBehaviour() {
		this.defaultBehaviour = true;
	}

	/**
	 * Make this unit stop performing default behaviour.
	 * 
	 * @post The new default behaviour of this unit is equal to false.
	 *     | new.doesDefaultBehaviour() == false
	 */
	public void stopDefaultBehaviour() {
		this.defaultBehaviour = false;
	}
	
	/**
	 * Return whether or not this unit is currently performing default behaviour.
	 */
	public boolean doesDefaultBehaviour() {
		return this.defaultBehaviour;
	}
	
	/**
	 * Choose a random default behaviour for this unit.
	 * 
	 * @post The unit is working or resting or moving to a random position.
	 *     | isWorking() || isResting() || isMoving()
	 */
	private void chooseDefaultBehaviour() {
		Random random = new Random();
		
		int i = random.nextInt(4);
		if (i == 0) {
			work();
		} else if (i == 1) {
			rest();
		} else if (i == 2 && getObjectivePosition() == null) {
			goToRandom(GameWorld.MAX_X);
		} else if (i == 3 && isMoving()) {
			startSprinting();
		}
	}
	
	/**
	 * Reset all jobs for this unit.
	 * 
	 * @effect The unit stops working and stops attacking and stops resting.
	 *       | stopWorking() && stopAttacking() && stopResting()
	 */
	private void resetAllJobs() {
		stopWorking();
		stopAttacking();
		stopResting();
	}
	
	/**
	 * Return whether or not this unit is currently walking.
	 * 
	 * @return True if and only if this unit's target position does not reference the null object.
	 *       | result == getTargetPosition() != null
	 */
	public boolean isWalking() {
		return getTargetPosition() != null;
	}
	
	/**
	 * Return whether or not this unit currently has a job.
	 * 
	 * @return True if and only if the unit is currently working, attacking, or resting.
	 *       | result == isWorking() || isAttacking() || isResting()
	 */
	private boolean hasJob() {
		return isWorking() || isAttacking() || isResting();
	}
}

