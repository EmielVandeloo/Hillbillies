package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.bool.BooleanExpression;
import hillbillies.expression.unit.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class on double unit checkers.
 * 
 * @invar  The first expression of each checker must be a valid first expression for any
 *         checker.
 *       | isValidFirst(getFirst())
 * @invar  The second expression of each checker must be a valid second expression for any
 *         checker.
 *       | isValidSecond(getSecond())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public abstract class DoubleUnitChecker extends BooleanExpression {

	// FIELDS

	/**
	 * Variable registering the first expression of this checker.
	 */
	private UnitExpression first;

	/**
	 * Variable registering the second expression of this checker.
	 */
	private UnitExpression second;


	// CONSTRUCTOR
	
	/**
	 * Initialize this new checker with given first and second expression.
	 *
	 * @param  first
	 *         The first expression for this new checker.
	 * @param  second
	 *         The second expression for this new checker.
	 * @effect The first expression of this new checker is set to
	 *         the given first expression.
	 *       | this.setFirst(first)
	 * @effect The second expression of this new checker is set to
	 *         the given second expression.
	 *       | this.setSecond(second)
	 */
	public DoubleUnitChecker(SourceLocation sourceLocation, UnitExpression first, UnitExpression second) 
			throws IllegalArgumentException {
		
		super(sourceLocation);
		this.setFirst(first);
		this.setSecond(second);
	}


	// GETTERS AND SETTERS

	/**
	 * Return the first expression of this checker.
	 */
	@Basic @Raw
	public UnitExpression getFirst() {
		return this.first;
	}

	/**
	 * Check whether the given first expression is a valid first expression for
	 * any checker.
	 *  
	 * @param  first expression
	 *         The first expression to check.
	 * @return 
	 *       | result == (first != null)
	 */
	public static boolean isValidFirst(UnitExpression first) {
		return (first != null);
	}

	/**
	 * Set the first expression of this checker to the given first expression.
	 * 
	 * @param  first
	 *         The new first expression for this checker.
	 * @post   The first expression of this new checker is equal to
	 *         the given first expression.
	 *       | new.getFirst() == first
	 * @throws ExceptionName_Java
	 *         The given first expression is not a valid first expression for any
	 *         checker.
	 *       | ! isValidFirst(getFirst())
	 */
	@Raw
	public void setFirst(UnitExpression first) 
			throws IllegalArgumentException {
		if (! isValidFirst(first))
			throw new IllegalArgumentException();
		this.first = first;
	}

	/**
	 * Return the second expression of this checker.
	 */
	@Basic @Raw
	public UnitExpression getSecond() {
		return this.second;
	}

	/**
	 * Check whether the given second expression is a valid second expression for
	 * any checker.
	 *  
	 * @param  second expression
	 *         The second expression to check.
	 * @return 
	 *       | result == (second != null)
	 */
	public static boolean isValidSecond(UnitExpression second) {
		return (second != null);
	}

	/**
	 * Set the second expression of this checker to the given second expression.
	 * 
	 * @param  second
	 *         The new second expression for this checker.
	 * @post   The second expression of this new checker is equal to
	 *         the given second expression.
	 *       | new.getSecond() == second
	 * @throws IllegalArgumentException
	 *         The given second expression is not a valid second expression for any
	 *         checker.
	 *       | ! isValidSecond(getSecond())
	 */
	@Raw
	public void setSecond(UnitExpression second) throws IllegalArgumentException {
		if (! isValidSecond(second))
			throw new IllegalArgumentException();
		this.second = second;
	}

}
