package hillbillies.expression.position;

import hillbillies.expression.Expression;
import hillbillies.model.Entity;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class Log extends Expression<Position> {

	public Log(SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
	}

	@Override
	public Position evaluate(Program program) {
		Entity entity = World.getClosestElement(
				program.getWorld().getAllLogs(), 
				program.getUnit().getPosition());
		
		return (entity == null ? null : entity.getPosition());
	}
	
	@Override
	public String toString() {
		return "log";
	}

}