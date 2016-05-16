package hillbillies.expression.position;

import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class Log extends PositionExpression {

	public Log(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Position evaluate(Program program) {
		return (Position) World.getClosestElement(
				program.getWorld().getAllLogs(), 
				program.getUnit().getPosition()).getPosition();
	}
	
	@Override
	public String toString() {
		return "log";
	}

}