package hillbillies.expression.position.checker;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.world.Position;

/**
 * A class with unitExpressions
 * 
 * @invar  The unit expression of each unit checker must be a valid unit expression for any
 *         unit checker.
 *       | isValidUnitExpression(getUnitExpression())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public abstract class UnitChecker extends Expression<Position> {
	
	// METHODS
	
	/**
	 * Variable registering the unit expression of this unit checker.
	 */
	private Expression<Unit> unitExpression;
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new unit checker with given unit expression.
	 *
	 * @param  unitExpression
	 *         The unit expression for this new unit checker.
	 * @effect The unit expression of this new unit checker is set to
	 *         the given unit expression.
	 *       | this.setUnitExpression(unitExpression)
	 */
	public UnitChecker(SourceLocation sourceLocation, Expression<Unit> unitExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation);
		this.setUnitExpression(unitExpression);
	}

	
	// GETTERS AND SETTERS

	/**
	 * Return the unit expression of this unit checker.
	 */
	@Basic @Raw
	public Expression<Unit> getUnitExpression() {
		return this.unitExpression;
	}

	/**
	 * Check whether the given unit expression is a valid unit expression for
	 * any unit checker.
	 *  
	 * @param  unit expression
	 *         The unit expression to check.
	 * @return 
	 *       | result == (unitExpression != null)
	 */
	public static boolean isValidUnitExpression(Expression<Unit> unitExpression) {
		return (unitExpression != null);
	}

	/**
	 * Set the unit expression of this unit checker to the given unit expression.
	 * 
	 * @param  unitExpression
	 *         The new unit expression for this unit checker.
	 * @post   The unit expression of this new unit checker is equal to
	 *         the given unit expression.
	 *       | new.getUnitExpression() == unitExpression
	 * @throws ExceptionName_Java
	 *         The given unit expression is not a valid unit expression for any
	 *         unit checker.
	 *       | ! isValidUnitExpression(getUnitExpression())
	 */
	@Raw
	public void setUnitExpression(Expression<Unit> unitExpression) throws IllegalArgumentException {
		if (! isValidUnitExpression(unitExpression))
			throw new IllegalArgumentException();
		this.unitExpression = unitExpression;
	}

}