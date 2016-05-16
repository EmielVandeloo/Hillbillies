package hillbillies.expression.bool.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.bool.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;

/**
 * A class of single expressions.
 * 
 * @invar  The expression of each single expression must be a valid expression for any
 *         single expression.
 *       | isValidExpression(getExpression())
 * 
 * @author Pieter-Jan
 *
 */
public abstract class BoolChecker extends BooleanExpression {
	
	// FIELDS
	
	/**
	 * Variable registering the expression of this single expression.
	 */
	private BooleanExpression expression;
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new single expression with given expression.
	 *
	 * @param  expression
	 *         The expression for this new single expression.
	 * @effect The expression of this new single expression is set to
	 *         the given expression.
	 *       | this.setExpression(expression)
	 */
	public BoolChecker(SourceLocation sourceLocation, BooleanExpression expression) 
			throws IllegalArgumentException {
		
		super(sourceLocation);
		this.setExpression(expression);
	}

	
	// GETTERS AND SETTERS

	/**
	 * Return the expression of this single expression.
	 */
	@Basic @Raw
	public BooleanExpression getExpression() {
		return this.expression;
	}

	/**
	 * Check whether the given expression is a valid expression for
	 * any single expression.
	 *  
	 * @param  expression
	 *         The expression to check.
	 * @return 
	 *       | result == (expression != null)
	 */
	public static boolean isValidExpression(BooleanExpression expression) {
		return (expression != null);
	}

	/**
	 * Set the expression of this single expression to the given expression.
	 * 
	 * @param  expression
	 *         The new expression for this single expression.
	 * @post   The expression of this new single expression is equal to
	 *         the given expression.
	 *       | new.getExpression() == expression
	 * @throws IllegalArgumentException
	 *         The given expression is not a valid expression for any
	 *         single expression.
	 *       | ! isValidExpression(getExpression())
	 */
	@Raw
	public void setExpression(BooleanExpression expression) throws IllegalArgumentException {
		if (! isValidExpression(expression))
			throw new IllegalArgumentException();
		this.expression = expression;
	}
	
}
