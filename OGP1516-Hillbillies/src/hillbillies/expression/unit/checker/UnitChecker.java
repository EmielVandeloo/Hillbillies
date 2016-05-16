package hillbillies.expression.unit.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.unit.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of unit checkers.
 * 
 * @invar  The unit expression of each checker must be a valid unit expression for any
 *         checker.
 *       | isValidExpression(getExpression())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public abstract class UnitChecker extends UnitExpression {
	
	// FIELDS

	/**
	 * Variable registering the unit expression of this checker.
	 */
	private UnitExpression expression;
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new checker with given unit expression.
	 *
	 * @param  expression
	 *         The unit expression for this new checker.
	 * @effect The unit expression of this new checker is set to
	 *         the given unit expression.
	 *       | this.setExpression(expression)
	 */
	public UnitChecker(SourceLocation sourceLocation, UnitExpression expression) 
			throws IllegalArgumentException {
		
		super(sourceLocation);
		this.setExpression(expression);
	}

	
	// GETTERS AND SETTERS

	/**
	 * Return the unit expression of this checker.
	 */
	@Basic @Raw
	public UnitExpression getExpression() {
		return this.expression;
	}

	/**
	 * Check whether the given unit expression is a valid unit expression for
	 * any checker.
	 *  
	 * @param  unit expression
	 *         The unit expression to check.
	 * @return 
	 *       | result == (expression != null)
	 */
	public static boolean isValidExpression(UnitExpression expression) {
		return (expression != null);
	}

	/**
	 * Set the unit expression of this checker to the given unit expression.
	 * 
	 * @param  expression
	 *         The new unit expression for this checker.
	 * @post   The unit expression of this new checker is equal to
	 *         the given unit expression.
	 *       | new.getExpression() == expression
	 * @throws IllegalArgumentException
	 *         The given unit expression is not a valid unit expression for any
	 *         checker.
	 *       | ! isValidExpression(getExpression())
	 */
	@Raw
	public void setExpression(UnitExpression expression) throws IllegalArgumentException {
		if (! isValidExpression(expression))
			throw new IllegalArgumentException();
		this.expression = expression;
	}

}
