package hillbillies.expression;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of expressions.
 * 
 * @invar  The unit of each expression must be a valid unit for any
 *         expression.
 *       | isValidUnit(getUnit())
 * 
 * @author Pieter-Jan
 *
 */
public abstract class Expression<E> {
	
	// FIELDS

	/**
	 * Variable registering the unit of this expression.
	 */
	private Unit unit;


	// CONSTRUCTOR

	/**
	 * Initialize this new expression with given unit.
	 *
	 * @param  unit
	 *         The unit for this new expression.
	 * @effect The unit of this new expression is set to
	 *         the given unit.
	 *       | this.setUnit(unit)
	 */
	public Expression(SourceLocation sl) throws IllegalArgumentException {
		setSourceLocation(sl);
	}

	
	// GETTERS AND SETTERS
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	private void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	private SourceLocation sourceLocation;

	/**
	 * Return the unit of this expression.
	 */
	@Basic @Raw
	public Unit getUnit() {
		return this.unit;
	}

	/**
	 * Check whether the given unit is a valid unit for
	 * any expression.
	 *  
	 * @param  unit
	 *         The unit to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidUnit(Unit unit) {
		return true;
	}

	/**
	 * Set the unit of this expression to the given unit.
	 * 
	 * @param  unit
	 *         The new unit for this expression.
	 * @post   The unit of this new expression is equal to
	 *         the given unit.
	 *       | new.getUnit() == unit
	 * @throws IllegalArgumentException
	 *         The given unit is not a valid unit for any
	 *         expression.
	 *       | ! isValidUnit(getUnit())
	 */
	@Raw
	public void setUnit(Unit unit) throws IllegalArgumentException {
		if (! isValidUnit(unit))
			throw new IllegalArgumentException();
		this.unit = unit;
	}
	
	public abstract E evaluate();

}
