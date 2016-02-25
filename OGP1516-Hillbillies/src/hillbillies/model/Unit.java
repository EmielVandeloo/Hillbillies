package hillbillies.model;
import ogp.framework.util.*;
import java.io.ObjectInputStream.GetField;
import javax.naming.InvalidNameException;
import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.ReturnTaglet;

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
	
	
	
	
	private double[] unitPosition = new double[3]; // defensief
	private double[] centrePosition = new double[3]; // tussen 0 en 50
	private double[] targetPosition = new double[3];
	// private double[] unitSpeed = new double[3];

	private String status;
	private boolean isSprinting = false;

	private double orientation = Math.PI / 2;// totaal

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
	public Unit(String name, int strength, int agility, int weight, int toughness)
			throws IllegalArgumentException {
		// TODO x,y,z defensief, exception

		setName(name);
		setInitialStrength(strength);
		setInitialAgility(agility);
		setInitialWeight(weight);
		setInitialToughness(toughness);
		setNbHitPoints(getMaxNbHitPoints());
		setNbStaminaPoints(getMaxNbStaminaPoints());
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
	public void setName(String name) 
			throws NullPointerException, IllegalArgumentException {
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
	 * @post   The number of hitpoints of this unit is equal to the given
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

///////////////////////////////////////////////////
///////////////////////////////////////////////////
///////////////////////////////////////////////////

	public double getBaseSpeed() {
		return 1.5 * (getStrength() + getAgility()) / (200d * getWeight() / 100d);
	}

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

	public double getSprintingSpeed(double z, double targZ) {
		return 2 * getWalkingSpeed(z, targZ);
	}

	public void startSprinting() {
		isSprinting = true;
	}

	public void stopSprinting() {
		isSprinting = false;
	}

	public String getMovementStatus() {
		return this.movementStatus;
	}

	public void setMovementStatus(String movementStatus) {
		this.movementStatus = movementStatus;
	}

	public void advanceTime(double deltaTime) {
		// deltaTime tussen 0 en 0.2
		// geen formele documentatie
		// defensief

		if (status.equals(Status.MOVING)) {
			if (isSprinting) {
				updatePosition(deltaTime, getSprintingSpeed(unitPosition[2], targetPosition[2]));
				// staminaDrain();
			} else {
				updatePosition(deltaTime, getWalkingSpeed(unitPosition[2], targetPosition[2]));
			}
			// hasReachedTarget();
		}
	}

	public void updatePosition(double deltaTime, double speed) {
		double[] unitSpeed = getUnitSpeed(speed);
		double[] updatedUnitPosition = new double[3];

		updatedUnitPosition[0] = unitPosition[0] + unitSpeed[0] * deltaTime;
		updatedUnitPosition[1] = unitPosition[1] + unitSpeed[1] * deltaTime;
		updatedUnitPosition[2] = unitPosition[2] + unitSpeed[2] * deltaTime;

		unitPosition = updatedUnitPosition;
	}

	public void moveToAdjacent(double[] target) {
		// defensief
		targetPosition = target;
	}

	public boolean isAdjacentTo() {

	}

	public void moveTo() {
		// multiple moveToAdjacent-steps
		// Zie algorithme
		// defensief
	}

	public double getDistance() {
		return Math.sqrt(Math.pow(targetPosition[0] - unitPosition[0], 2)
				+ Math.pow(targetPosition[1] - unitPosition[1], 2) + Math.pow(targetPosition[2] - unitPosition[2], 2));
	}

	public double[] getUnitSpeed(double speed) {
		double[] unitSpeed = new double[3];
		double distance = getDistance();

		unitSpeed[0] = speed * (targetPosition[0] - unitPosition[0]) / distance;
		unitSpeed[1] = speed * (targetPosition[1] - unitPosition[1]) / distance;
		unitSpeed[2] = speed * (targetPosition[2] - unitPosition[2]) / distance;

		return unitSpeed;
	}

	public void work() {
		// use floating point for duration and other
	}

	public void attack() {

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
