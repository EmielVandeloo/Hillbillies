package hillbillies.expression.bool;

import hillbillies.expression.Expression;
import hillbillies.expression.bool.checker.UnitChecker;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

/**
 * A class to check whether a unit is an enemy or not.
 * 
 * @invar  The other unit of each enemy comparator must be a valid other unit for any
 *         enemy comparator.
 *       | isValidOther(getOther())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public class IsEnemy extends UnitChecker {

	// CONSTRUCTOR
	
	public IsEnemy(SourceLocation sourceLocation, Expression<Unit> unitExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, unitExpression);
	}
	
	
	// OVERRIDE

	@Override
	public Boolean evaluate(Program program) {
		return ! program.getUnit().getFaction()
				.equals(getUnitExpression().evaluate(program).getFaction());
	}
	
	@Override
	public String toString() {
		return getUnitExpression().toString() + " is enemy";
	}

}
