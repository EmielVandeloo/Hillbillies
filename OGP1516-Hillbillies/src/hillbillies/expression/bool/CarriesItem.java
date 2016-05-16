package hillbillies.expression.bool;

import hillbillies.expression.bool.checker.UnitChecker;
import hillbillies.expression.unit.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

/**
 * A class to check whether a unit carries an item.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public class CarriesItem extends UnitChecker {
	
	public CarriesItem(SourceLocation sourceLocation, UnitExpression unitExpression) 
			throws IllegalArgumentException {
		
		super(sourceLocation, unitExpression);
	}

	@Override
	public Boolean evaluate(Program program) {
		return getUnitExpression().evaluate(program).getInventory().getNbItems() > 0;
	}
	
	@Override
	public String toString() {
		return "carries item";
	}

}