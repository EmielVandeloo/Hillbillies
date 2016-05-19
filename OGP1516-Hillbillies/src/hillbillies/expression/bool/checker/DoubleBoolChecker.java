package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of double expressions.
 * 
 * @invar  The first expression of each double expression must be a valid first expression for any
 *         double expression.
 *       | isValidFirst(getFirst())
 * @invar  The second expression of each double expression must be a valid second expression for any
 *         double expression.
 *       | isValidSecond(getSecond())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public abstract class DoubleBoolChecker extends Expression<Boolean> {

	// FIELD

	/**
	 * Variable registering the first expression of this double expression.
	 */
	private Expression<Boolean> first;

	/**
	 * Variable registering the second expression of this double expression.
	 */
	private Expression<Boolean> second;


	// CONSTRUCTOR

	/**
	 * Initialize this new double expression with given first- and second expression.
	 *
	 * @param  first
	 *         The first expression for this new double expression.
	 * @param  second
	 *         The second expression for this new double expression.
	 * @effect The first expression of this new double expression is set to
	 *         the given first expression.
	 *       | this.setFirst(first)
	 * @effect The second expression of this new double expression is set to
	 *         the given second expression.
	 *       | this.setSecond(second)
	 */
	public DoubleBoolChecker(SourceLocation sourceLocation, 
			Expression<Boolean> first, Expression<Boolean> second) throws IllegalArgumentException {
		
		super(sourceLocation);
		this.setFirst(first);
		this.setSecond(second);
	}


	// GETTERS AND SETTERS

	/**
	 * Return the first expression of this double expression.
	 */
	@Basic @Raw
	public Expression<Boolean> getFirst() {
		return this.first;
	}

	/**
	 * Check whether the given first expression is a valid first expression for
	 * any double expression.
	 *  
	 * @param  first expression
	 *         The first expression to check.
	 * @return 
	 *       | result == (first != null)
	 */
	public static boolean isValidFirst(Expression<Boolean> first) {
		return (first != null);
	}

	/**
	 * Set the first expression of this double expression to the given first expression.
	 * 
	 * @param  first
	 *         The new first expression for this double expression.
	 * @post   The first expression of this new double expression is equal to
	 *         the given first expression.
	 *       | new.getFirst() == first
	 * @throws IllegalArgumentException
	 *         The given first expression is not a valid first expression for any
	 *         double expression.
	 *       | ! isValidFirst(getFirst())
	 */
	@Raw
	public void setFirst(Expression<Boolean> first) throws IllegalArgumentException {
		if (! isValidFirst(first))
			throw new IllegalArgumentException();
		this.first = first;
	}

	/**
	 * Return the second expression of this double expression.
	 */
	@Basic @Raw
	public Expression<Boolean> getSecond() {
		return this.second;
	}

	/**
	 * Check whether the given second expression is a valid second expression for
	 * any double expression.
	 *  
	 * @param  second expression
	 *         The second expression to check.
	 * @return 
	 *       | result == (second != null)
	 */
	public static boolean isValidSecond(Expression<Boolean> second) {
		return (second != null);
	}

	/**
	 * Set the second expression of this double expression to the given second expression.
	 * 
	 * @param  second
	 *         The new second expression for this double expression.
	 * @post   The second expression of this new double expression is equal to
	 *         the given second expression.
	 *       | new.getSecond() == second
	 * @throws IllegalArgumentException
	 *         The given second expression is not a valid second expression for any
	 *         double expression.
	 *       | ! isValidSecond(getSecond())
	 */
	@Raw
	public void setSecond(Expression<Boolean> second) throws IllegalArgumentException {
		if (! isValidSecond(second))
			throw new IllegalArgumentException();
		this.second = second;
	}

}
