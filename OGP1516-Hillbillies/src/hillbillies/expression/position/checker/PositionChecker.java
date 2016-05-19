package hillbillies.expression.position.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.world.Position;

/**
 * A class to check a position.
 * 
 * @invar  The position expression of each checker must be a valid position expression for any
 *         checker.
 *       | isValidPosition(getPosition())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public abstract class PositionChecker extends Expression<Position> {
	
	// FIELDS

	/**
	 * Variable registering the position expression of this checker.
	 */
	private Expression<Position> positionExpression;
	

	// CONSTRUCTOR

	/**
	 * Initialize this new checker with given position expression.
	 *
	 * @param  position
	 *         The position expression for this new checker.
	 * @effect The position expression of this new checker is set to
	 *         the given position expression.
	 *       | this.setPosition(position)
	 */
	public PositionChecker(SourceLocation sourceLocation, Expression<Position> positionExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation);
		this.setPositionExpression(positionExpression);
	}

	
	// GETTERS AND SETTERS

	/**
	 * Return the position expression of this checker.
	 */
	@Basic @Raw
	public Expression<Position> getPositionExpression() {
		return this.positionExpression;
	}

	/**
	 * Check whether the given position expression is a valid position expression for
	 * any checker.
	 *  
	 * @param  position expression
	 *         The position expression to check.
	 * @return 
	 *       | result == (position != null)
	 */
	public static boolean isValidPositionExpression(Expression<Position> positionExpression) {
		return (positionExpression != null);
	}

	/**
	 * Set the position expression of this checker to the given position expression.
	 * 
	 * @param  position
	 *         The new position expression for this checker.
	 * @post   The position expression of this new checker is equal to
	 *         the given position expression.
	 *       | new.getPosition() == position
	 * @throws ExceptionName_Java
	 *         The given position expression is not a valid position expression for any
	 *         checker.
	 *       | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setPositionExpression(Expression<Position> positionExpression) 
			throws IllegalArgumentException {
		
		if (! isValidPositionExpression(positionExpression))
			throw new IllegalArgumentException();
		this.positionExpression = positionExpression;
	}

}