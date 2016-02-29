package hillbillies.model;
import java.util.Arrays;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import ogp.framework.util.Util;

/**
 * A class of units involving a name, a weight, a strength, an agility, a toughness,
 * a number of hitpoints and a number of stamina points.
 * 
 * @invar The name of each unit must be a valid name for any unit. 
 *        | isValidName(getName())
 * @invar The weight of each unit must be a valid weight for any unit.
 *        | isValidWeight(getWeight())
 * @invar The strength of each unit must be a valid strength for any
 *        unit.
 *        | isValidStrength(getStrength())
 * @invar  The agility of each unit must be a valid agility for any
 *         unit.
 *       | isValidAgility(getAgility())
 * @invar  The toughness of each unit must be a valid toughness for any
 *         unit.
 *       | isValidToughness(getToughness())
 * @invar  The number of hitpoints of each unit must be a valid number of hitpoints for any
 *         unit.
 *       | isValidNbHitPoints(getNbHitPoints())
 * @invar  The number of stamina points of each unit must be a valid number of stamina points for any
 *         unit.
 *       | isValidNbStaminaPoints(getNbStaminaPoints())
 * @invar  The position of each unit must be a valid position for any
 *         unit.
 *       | isValidPosition(getPosition())
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
	 * Variable referencing an array assembling the position coordinates of this unit.
	 */
	private double[] unitPosition = new double[3];
	
	/**
	 * Variable referencing an array storing the (intermediate) target position of this moving unit.
	 */
	private double[] targetPosition = new double[3];
	
	/**
	 * Variable referencing an array storing the (final) objective position of this moving unit.
	 */
	private double[] objectivePosition = new double[3];
	
	/**
	 * Variable referencing an array storing the start position of this moving unit.
	 */
	private double[] startPosition = new double[3];
	
	/**
	 * Variable referencing an array storing the overshoot position of this moving unit.
	 */
	private double[] overshootPosition = new double[3];
	

	private int status;
	private boolean isSprinting = false;

	/**
	 * Initialize this unit with given name, given strength, given agility, given weight, given toughness
	 * and with the maximum value of the number of hitpoints and the number of stamina points.
	 * 
	 * @param name
	 *        The name for this new unit.
	 * @param weight
	 *        The weight for this new unit.
	 * @param strength
	 *        The strength for this new unit.
	 * @param agility
	 *        The agility for this new unit.
	 * @param toughness
	 *        The toughness for this new unit.
	 * @post The new name of this new unit is equal to the given name.
	 *       | new.getName() == name
	 * @post The new strength of this new unit is equal to the given strength.
	 *       | new.getStrength() == strength
	 * @post The new agility of this new unit is equal to the given agility.
	 *       | new.getAgility() == agility
	 * @post The new weight of this new unit is equal to the given weight.
	 *       | new.getWeight() == weight
	 * @post The new toughness of this new unit is equal to the given toughness.
	 *       | new.getToughness() == toughness
	 * @post The new number of hitpoints of this new unit is equal to the maximum number of hitpoints.
	 *       | new.getNbHitPoints() == getMaxNbHitPoints()
	 * @post The new number of stamina points of this new unit is equal to the maximum number of stamina points.
	 *       | new.getNbStaminaPoints() == getMaxNbStaminaPoints()
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for this new unit.
	 *         | (! isValidName(name))
	 * @throws 
	 */
	@Raw
	public Unit(String name, int strength, int agility, int weight, int toughness, 
				   double[] position, double orientation)
			throws IllegalArgumentException {
		// TODO x,y,z defensief, exception

		setName(name);
		setInitialStrength(strength);
		setInitialAgility(agility);
		setInitialWeight(weight);
		setInitialToughness(toughness);
		setNbHitPoints(getMaxNbHitPoints());
		setNbStaminaPoints(getMaxNbStaminaPoints());
		setUnitPosition(position);
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
	 * @param name
	 *        The name to check.
	 * @return True if and only if the given name is at least two characters
	 *         long, if the name starts with an uppercase letter and if no other
	 *         symbols than letters (both uppercase and lowercase), quotes (both
	 *         single and double) and spaces occur in the name. 
	 *         | result == (name.length() >= 2) && (Character.isUpperCase(name.charAt(0)))
	 *         && (name.matches("[A-Za-z]['][\"]+"))
	 */
	public static boolean isValidName(String name) {
		// FIXME control & space
		return (name.length() >= 2) && (Character.isUpperCase(name.charAt(0))) && 
				  (name.matches("[A-Za-z]['][\"]+"));
	}

	/**
	 * Set the name of this unit to the given name.
	 * 
	 * @param name
	 *        The new name for this unit.
	 * @post The name of this new unit is equal to
	 *       the given name.
	 *       | new.getName() == name
	 * @throws NullPointerException
	 *         The given name references the null object.
	 *         | name == null 
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any
	 *         unit.
	 *         | ! isValidName(name)
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
	 * @param strength
	 *        The strength to check.
	 * @return True if and only if the given strength is a valid attribute.
	 *         | result == isValidAttribute(strength)
	*/
	public static boolean isValidStrength(int strength) {
		return isValidAttribute(strength);
	}
	
	/**
	 * Check whether the given strength is a valid initial strength for any unit.
	 * 
	 * @param strength
	 *        The strength to check.
	 * @return True if and only if the given strength is a valid initial attribute.
	 *         | result == isValidInitialAttribute(strength)
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
	 *         | if (! isValidAttribute(strength)
	 *         |	then makeValidAttribute(strength)
	 * @post The new strength of this unit is equal to the given strength.
	 *       | new.getStrength() == strength
	 */
	@Raw @Model
	private void setStrength(int strength) {
		if (! isValidAttribute(strength)) {
			makeValidAttribute(strength);
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
	 *         | if (! isValidInitialAttribute(strength)
	 *         |	then makeValidInitialAttribute(strength)
	 * @post The new strength of this unit is equal to the given strength.
	 *       | new.getStrength() == strength
	 */
	@Raw
	public void setInitialStrength(int strength) {
		if (! isValidInitialAttribute(strength)) {
			makeValidInitialAttribute(strength);
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
	 *         | result == isValidAttribute(agility)
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
	 *         | result == isValidInitialAttribute(agility)
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
	 *         | if (! isValidAttribute(agility)
	 *         |	then makeValidAttribute(agility)
	 * @post The new agility of this unit is equal to the given agility.
	 *       | new.getAgility() == agility
	 */
	@Raw @Model
	private void setAgility(int agility) {
		if (! isValidAttribute(agility)) {
			makeValidAttribute(agility);
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
	 *         | if (! isValidInitialAttribute(agility)
	 *         |	then makeValidInitialAttribute(agility)
	 * @post The new agility of this unit is equal to the given agility.
	 *       | new.getAgility() == agility
	 */
	@Raw
	public void setInitialAgility(int agility) {
		if (! isValidInitialAttribute(agility)) {
			makeValidInitialAttribute(agility);
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
	 * @param weight
	 *        The weight to check.
	 * @return True if and only if the given weight is a valid attribute, and if the weight 
	 *         is greater than or equal to the sum of the strength of this unit and the agility of this unit
	 *         divided by two.
	 *         | result == isValidAttribute(weight) && (weight >= (getStrength() + getAgility()) / 2)
	 */
	@Raw
	public boolean canHaveAsWeight(int weight) {
		return isValidAttribute(weight) && (weight >= (getStrength() + getAgility()) / 2);
	}
	
	/**
	 * Check whether the given weight is a valid initial weight for any unit.
	 * 
	 * @param weight
	 *        The weight to check.
	 * @return True if and only if the given weight is a valid initial attribute, and if the weight
	 *         is greater than or equal to the sum of the strength of this unit and the agility of this unit 
	 *         divided by two.
	 *         | result == (isValidInitialAttribute(weight) && 
	 *         |             (weight >= (getStrength() + getAgility()) / 2) 
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
	 *         | if (! canHaveAsWeight(weight))
	 *         |	then makeValidWeight(weight)
	 * @post The new weight of this unit is equal to the given weight.
	 *       | new.getWeight() == weight
	 */
	@Raw @Model
	private void setWeight(int weight) {
		if (! canHaveAsWeight(weight)) {
			makeValidWeight(weight);
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
	 *         | if (! canHaveAsInitialWeight(weight))
	 *         |	then makeValidInitialWeight(weight)
	 * @post The new weight of this unit is equal to the given weight.
	 *       | new.getWeight() == weight
	 */
	@Raw
	public void setInitialWeight(int weight) {
		if (! canHaveAsInitialWeight(weight)) {
			makeValidInitialWeight(weight);
		}
		this.weight = weight;
	}
	
	/**
	 * Returns a valid value for the weight.
	 * 
	 * @param weight
	 *        The weight to check.
	 * @return If the value of the weight is less than the sum of the strength of this unit and
	 *         the agility of this unit divided by two, the new value of the weight is equal to
	 *         the sum of the strength of this unit and the agility of this unit divided by two.
	 *         | if (attribute > (getStrength() + getAgility()) / 2)
	 *         |	then result == (getStrength() + getAgility()) / 2
	 * @return If the weight is greater than 200, the new weight is equal to 200.
	 *         | else if (weight > 200)
	 *         |	then result == 200
	 * @return Otherwise, the initial weight is returned.
	 *         | else
	 *         |	result == weight
	 */
	@Raw
	public int makeValidWeight(int weight) {
		if (weight < (getStrength() + getAgility() / 2)) {
			weight = (getStrength() + getAgility()) / 2;
		} else if (weight > 200) {
			weight = 200;
		}
		return weight;
	}
	
	/**
	 * Returns a valid initial value for the weight.
	 * 
	 * @param weight
	 *        The weight to check.
	 * @return If the value of the weight is less than the sum of the strength of this unit and
	 *         the agility of this unit divided by two, the new value of the weight is equal to
	 *         the sum of the strength of this unit and the agility of this unit divided by two.
	 *         | if (attribute > (getStrength() + getAgility()) / 2)
	 *         |	then result == (getStrength() + getAgility()) / 2
	 * @return If the weight is greater than 100, the new weight is equal to 100.
	 *         | else if (weight > 100)
	 *         |	then result == 100
	 * @return Otherwise, the initial weight is returned.
	 *         | else
	 *         |	result == weight
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
	 * Check whether the given toughness is a valid toughness for
	 * any unit.
	 *  
	 * @param  toughness
	 *         The toughness to check.
	 * @return True if and only if the given toughness is a valid attribute.
	 *         | result == isValidAttribute(toughness)
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
	 *         | result == isValidInitialAttribute(toughness)
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
	 *         | if (! isValidAttribute(toughness))
	 *         |	then makeValidAttribute(attribute)
	 * @post The new toughness of this unit is equal to the given toughness.
	 *       | new.getToughness() == toughness
	 */
	@Raw @Model
	private void setToughness(int toughness) {
		if (! isValidAttribute(toughness)) {
			makeValidAttribute(toughness);
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
	 *         | if (! isValidInitialAttribute(toughness))
	 *         |	then makeValidInitialAttribute(attribute)
	 * @post The new toughness of this unit is equal to the given toughness.
	 *       | new.getToughness() == toughness
	 */
	@Raw
	public void setInitialToughness(int toughness) {
		if (! isValidInitialAttribute(toughness)) {
			makeValidInitialAttribute(toughness);
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
	 * Check whether the given attribute is a valid attribute for
	 * any unit.
	 *  
	 * @param  attribute
	 *         The attribute to check.
	 * @return True if and only if the given attribute is greater than or equal to the minimum attribute
	 *         value and less than or equal to the maximum attribute value.
	 *         | result == (attribute >= getMinAttributeValue()) && (attribute <= getMaxAttributeValue())
	*/
	public static boolean isValidAttribute(int attribute) {
		return (attribute >= getMinAttributeValue()) && (attribute <= getMaxAttributeValue());
	}

	/**
	 * Check whether the given attribute is a valid initial attribute for
	 * any unit.
	 *  
	 * @param  attribute
	 *         The attribute to check.
	 * @return True if and only if the given attribute is greater than or equal to the minimum initial
	 *         attribute value and less than or equal to the maximum initial attribute value.
	 *         | result == (attribute >= getMinInitialAttributeValue()) && 
	 *                        attribute <= getMaxInitialAttributeValue())
	*/	
	public static boolean isValidInitialAttribute(int attribute) {
		return (attribute >= getMinInitialAttributeValue()) && (attribute <= getMaxInitialAttributeValue());
	}
 
	/**
	 * Returns a valid value for the given attribute.
	 * 
	 * @param attribute
	 *        The attribute to check.
	 * @return If the value of the attribute is greater than the maximum attribute value, 
	 *         the new value of the attribute is equal to the maximum attribute value.
	 *         | if (attribute > getMaxAttributeValue())
	 *         |	then result == getMaxAttributeValue()
	 * @return If the value of the attribute is less than the minimum attribute value, 
	 *         the new value of the attribute is equal to the minimum attribute value.
	 *         | else if (attribute < getMinAttributeValue())
	 *         |	then result == getMinAttributeValue()
	 * @return Otherwise, the initial value is returned.
	 *         | else
	 *         |	result == attribute
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
	 * @param attribute
	 *        The attribute to check.
	 * @return If the value of the attribute is greater than the maximum initial attribute value, 
	 *         the new value of the attribute is equal to the maximum initial attribute value.
	 *         | if (attribute > getMaxInitialAttributeValue())
	 *         |	then result == getMaxInitialAttributeValue
	 * @return If the value of the attribute is less than the minimum initial attribute value, 
	 *         the new value of the attribute is equal to the minimum initial attribute value.
	 *         | else if (attribute < getMinInitialAttributeValue())
	 *         |	then result == getMinInitialAttributeValue()
	 * @return Otherwise, the initial value is returned.
	 *         | else
	 *         |	result == attribute
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
	 * @return 200 * (the weight of this unit) / 100 * (the toughness of this unit) / 100, rounded up
	 *         to the next integer.
	 *         | result == (int) Math.ceil(200.0 * (getWeight() / 100.0) * (getToughness() / 100.0))
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
	 *         | result == (nbHitPoints >= 0) && (nbHitPoints <= getMaxNbHitPoints()
	*/
	public boolean isValidNbHitPoints(int nbHitPoints) {
		return (nbHitPoints >= 0) && (nbHitPoints <= getMaxNbHitPoints());
	}
	
	/**
	 * Set the number of hitpoints of this unit to the given number of hitpoints.
	 * 
	 * @param  nbHitPoints
	 *         The new number of hitpoints for this unit.
	 * @pre    The given number of hitpoints must be a valid number of hitpoints for any
	 *         unit.
	 *       | isValidNbHitPoints(nbHitPoints)
	 * @post   The number of hitpoints  of this unit is equal to the given
	 *         number of hitpoints.
	 *       | new.getNbHitPoints() == nbHitPoints
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
	 * @return 200 * (the weight of this unit) / 100 * (the toughness of this unit) / 100, rounded up
	 *         to the next integer.
	 *         | result == (int) Math.ceil(200.0 * (getWeight() / 100.0) * (getToughness() / 100.0))
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
	 *         | result == (nbHitPoints >= 0) && (nbHitPoints <= getMaxNbStaminaPoints()
	*/
	public boolean isValidNbStaminaPoints(int nbStaminaPoints) {
		return (nbStaminaPoints >= 0) && (nbStaminaPoints <= getMaxNbStaminaPoints());
	}
	

	/**
	 * Set the number of stamina points of this unit to the given number of stamina points.
	 * 
	 * @param  nbStaminaPoints
	 *         The new number of stamina points for this unit.
	 * @pre    The given number of stamina points must be a valid number of stamina points for any
	 *         unit.
	 *       | isValidNbStaminaPoints(nbStaminaPoints)
	 * @post   The number of stamina points of this unit is equal to the given
	 *         number of stamina points.
	 *       | new.getNbStaminaPoints() == nbStaminaPoints
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
	 *        The new orientation for this unit.
	 * @post The new orientation for this unit is equal to the given orientation.
	 *       new.getOrientation() == orientation
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
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
	 *         cube coordinate of the game world and smaller than the maximum cube coordinate of
	 *         the game world.
	 *       | result == (position.length == 3) &&
	 *       | for each element in position
	 *       |   (element >= GameWorld.MIN_X) && (element < GameWorld.MAX_X)
	*/
	public static boolean isValidPosition(double[] position) {
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
	 * @post   The position of this new unit is equal to
	 *         the given position.
	 *       | new.getUnitPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         unit.
	 *       | ! isValidPosition(position)
	 */
	@Raw @Model
	private void setUnitPosition(double[] position) 
			throws IllegalArgumentException {
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
	public double[] getTargetPosition() {
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
	private void setTargetPosition(double[] position) 
			throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.targetPosition = position;
	}
	
	/**
	 * Return the objective position of this unit.
	 *    The objective position of a unit is the center of any cube in the game world this unit
	 *    is moving to.
	 */
	@Basic @Raw
	public double[] getObjectivePosition() {
		return this.objectivePosition;
	}
	
	/**
	 * Set the objective position of this unit to the given position.
	 * 
	 * @param position
	 *        The new objective position of this unit.
	 * @post The new objective position of this unit is equal to the given position.
	 *       | new.getObjectivePosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any unit.
	 *         | ! isValidPosition(position) 
	 */
	@Raw
	public void setObjectivePosition(double[] position) throws IllegalArgumentException {
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
	public double[] getStartPosition() {
		return this.startPosition;
	}
	
	/**
	 * Set the start position of this unit to the given position.
	 * 
	 * @param position
	 *        The new start position of this unit.
	 * @post The new start position of this unit is equal to the given position.
	 *       | new.getStartPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any unit.
	 *         | ! isValidPosition(position)
	 */
	@Raw @Model
	private void setStartPosition(double[] position) throws IllegalArgumentException {
		if (! isValidPosition(position)) {
			throw new IllegalArgumentException();
		}
		this.startPosition = position;
	}
	
	/**
	 * Return the overshoot position of this unit.
	 *    The overshoot position of a unit is used to check whether the unit has reached its target position.
	 */
	@Basic @Raw
	public double[] getOvershootPosition() {
		return this.overshootPosition;
	}
	
	/**
	 * Set the overshoot position of this unit to the right position.
	 * 
	 * @param start
	 *        The start position of this unit.
	 * @param target
	 *        The target position of this unit.
	 * @post The overshoot position of this unit is set to 2 times the target position of this unit
	 *       subtracted by the start position of this unit.
	 *       | new.getOvershootPosition() == 2 * getTargetPosition() - getStartPosition()
	 */
	@Raw @Model
	private void setOvershootPosition(double[] start, double[] target) {
		double[] overshootPosition = new double[3];
		for (int i = 0; i < overshootPosition.length; i++) {
			overshootPosition[i] = 2 * target[i] - start[i];
		}
		this.overshootPosition = overshootPosition;
	}

	/**
	 * Return the base speed of this unit in meters per second.
	 * 
	 * @return 1.5 times the sum of the strength of this unit and the agility of this unit, 
	 *         divided by 200 times the weight of this unit divided by 100.
	 * 		   | result == 1.5 * (getStrength() + getAgility())/(200 * getWeight() / 100)
	 */
	@Raw
	public double getBaseSpeed() {
		return 1.5 * (getStrength() + getAgility()) / (200 * getWeight() / 100);
	}

	/**
	 * Return the walking speed of this unit in meters per second.
	 * 
	 * @param z
	 *        The z-coordinate of the cube that the unit currently occupies.
	 * @param targZ
	 *        The z-coordinate of the cube the unit is moving to.
	 * @return The base speed if the current cube and the cube the unit is moving to is on the same
	 *         z-level.
	 *         | if z == targZ
	 *         |   result == getBaseSpeed()
	 * @return Half the base speed if the z-level of the current cube is one level lower than the
	 *         z-level of the cube the unit is moving to.
	 *         | if z - targZ == -1
	 *         |   result == 0.5 * getBaseSpeed()
	 * @return 1.2 times the base speed if the z-level of the current cube is one level higher than the
	 *         z-level of the cube the unit is moving to.
	 *         | if z - targZ == 1
	 *         |   result == 1.2 * getBaseSpeed()
	 */
	@Raw
	public double getWalkingSpeed(double z, double targZ) {
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
	 * Return the sprinting speed of this unit in meters per second.
	 * 
	 * @param z
	 *        The z-coordinate of the cube that the unit currently occupies.
	 * @param targZ
	 *        The z-coordinate of the cube the unit is moving to.
	 * @return Two times the walking speed of this unit.
	 *         | result == 2 * getWalkingSpeed(z, targZ)
	 */
	@Raw
	public double getSprintingSpeed(double z, double targZ) {
		return 2d * getWalkingSpeed(z, targZ);
	}


	public void startSprinting() {
		isSprinting = true;
	}


	public void stopSprinting() {
		isSprinting = false;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Move this unit to the objective position.
	 * 
	 * @param objective
	 *        The objective position of this unit.
	 * @post The new position of this unit is equal to the given objective.
	 *       | new.getUnitPosition() == objective
	 * @throws IllegalArgumentException
	 *         Objective is null or the length of objective is not equal to 3.
	 *         | ((objective == null) || (objective.length != 3)
	 * @throws IllegalArgumentException
	 *         The objective is an invalid position for any unit.
	 *         | ! isValidPosition(objective)
	 */
	@Raw
	public void moveTo(double[] objective) throws IllegalArgumentException {

		if ((objective == null) || (objective.length != 3) || (! isValidPosition(objective))) {
			throw new IllegalArgumentException();
		}
		int x,y,z;
		setObjectivePosition(objective);
		while (! Arrays.equals(getCubePosition(getUnitPosition()), getCubePosition(getObjectivePosition()))) {
			if (getCubePosition(getUnitPosition())[0] == getCubePosition(getObjectivePosition())[0]) {
				x = 0;
			} else if (getCubePosition(getUnitPosition())[0] < getCubePosition(getObjectivePosition())[0]) {
				x = 1;
			} else {
				x = -1;
			}
			if (getCubePosition(getUnitPosition())[1] == getCubePosition(getObjectivePosition())[1]) {
				y = 0;
			} else if (getCubePosition(getUnitPosition())[1] < getCubePosition(getObjectivePosition())[1]) {
				y = 1;
			} else {
				y = -1;
			}
			if (getCubePosition(getUnitPosition())[2] == getCubePosition(getObjectivePosition())[2]) {
				z = 0;
			} else if (getCubePosition(getUnitPosition())[2] < getCubePosition(getObjectivePosition())[2]) {
				z = 1;
			} else {
				z = -1;
			}
			moveToAdjacent(x,y,z);
		}
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
	 * @post The new position of this unit is equal to the sum of the coordinates of this position
	 *       and x,y,z respectively.
	 *       | new.getUnitPosition()[0] == this.getUnitPostition()[0] + x
	 *       | new.getUnitPosition()[1] == this.getUnitPostition()[1] + y
	 *       | new.getUnitPosition()[2] == this.getUnitPostition()[2] + z
	 */
	@Raw @Model
	private void moveToAdjacent(int x, int y, int z) {
		// defensief
		double[] targetPosition = new double[3];
		targetPosition[0] = getUnitPosition()[0] + x;
		targetPosition[1] = getUnitPosition()[1] + y;
		targetPosition[2] = getUnitPosition()[2] + z;
		setStartPosition(getUnitPosition());
		setTargetPosition(targetPosition);
		while (! hasReachedTarget()) {
			advanceTime(0.1);
		}
		setUnitPosition(getCenterPosition(getTargetPosition()));
	}
	
	/**
	 * Advance the state of the given unit by the given time period.
     *
	 * @param deltaTime
	 *        The time period, in seconds, by which to advance the unit's state.
	 * @post The new state of this unit is equal to this state of this unit advanced by the given time period.
	 *       | ...
	 */
	@Raw @Model
	private void advanceTime(double deltaTime) {
		// deltaTime tussen 0 en 0.2
		// defensief
		
//		if (targetPosition != null) {
//			if (isSprinting) {
//				updatePosition(deltaTime, getSprintingSpeed(unitPosition[2], targetPosition[2]));
//				// staminaDrain();
//			} else {
//				updatePosition(deltaTime, getWalkingSpeed(unitPosition[2], targetPosition[2]));
//			}
//			
//			if (hasReachedTarget()) {
//				unitPosition = getCenterPosition(targetPosition);
//				startPosition = unitPosition;
//				overshootPosition = unitPosition;
//				targetPosition = null;
//				
//				if (hasReachedObjective()) {
//					objectivePosition = null;
//				}
//			}
//		}
//		else if (objectivePosition != null) {
//			moveToNext();
//		}
		
		if (isSprinting) {
			updatePosition(deltaTime, getSprintingSpeed(getCubePosition(getUnitPosition())[2], 
					                      getCubePosition(getTargetPosition())[2]));
		} else {
			updatePosition(deltaTime, getWalkingSpeed(getCubePosition(getUnitPosition())[2], 
					                      getCubePosition(getTargetPosition())[2]));
		}
	}

	public void updatePosition(double deltaTime, double speed) {
		double[] unitSpeed = getUnitSpeed(speed);
		double[] updatedUnitPosition = new double[3];
		for (int i = 0; i < updatedUnitPosition.length; i++) {
			updatedUnitPosition[i] = getUnitPosition()[i] + unitSpeed[i] * deltaTime;
		}
		setUnitPosition(updatedUnitPosition);
	}
	
	public double getDistance(double[] a, double[] b) throws IllegalArgumentException {
		if (a.length != 3 || b.length != 3) {
			throw new IllegalArgumentException();
		}
		return Math.sqrt(Math.pow(a[0] - b[0], 2)
			           + Math.pow(a[1] - b[1], 2) 
			           + Math.pow(a[2] - b[2], 2));
	}

	public double[] getUnitSpeed(double speed) {
		double[] unitSpeed = new double[3];
		double distance = getDistance(getTargetPosition(), getUnitPosition());
		for (int i = 0; i < unitSpeed.length; i++) {
			unitSpeed[i] = speed * (targetPosition[i] - unitPosition[i]) / distance;
		}
		return unitSpeed;
	}
	
	public int[] getCubePosition(double[] position) {
		int[] cubePosition = new int[3];
		for (int i = 0; i < cubePosition.length; i++) {
			cubePosition[i] = (int) position[i];
		}
		return cubePosition;
	}
	
	public double[] getCenterPosition(double[] position) {
		int[] cubePosition = getCubePosition(position);
		double[] centerPosition = new double[3];
		for (int i = 0; i < centerPosition.length; i++) {
			centerPosition[i] = cubePosition[i] + GameWorld.CUBE_LENGTH / 2;
		}
		return centerPosition;
	}
	
	private boolean hasReachedTarget() {
		setOvershootPosition(getStartPosition(), getTargetPosition());
		double startToUnit = getDistance(getStartPosition(), getUnitPosition());
		double unitToOvershoot = getDistance(getUnitPosition(), getOvershootPosition());	
		return startToUnit >= unitToOvershoot;
	}
	
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////	
/////////////////////////////////////////////////////////////////
	
	public boolean isAdjacentTo(double[] current, double[] target) throws IllegalArgumentException {
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
	
	
	
	
	
	
	
	public void work() {
		// use floating point for duration and other
	}

	public void attack(Unit victim) {
		victim.defend();
	}

	public void defend() {

	}

	public void rest() {

	}

	public void startDefaultBehaviour() {

	}

	public void stopDefaultBehaviour() {

	}
}
