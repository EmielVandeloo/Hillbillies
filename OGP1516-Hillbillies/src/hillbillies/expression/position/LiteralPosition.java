package hillbillies.expression.position;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

public class LiteralPosition extends Expression<Position> {

	private Position position;

	public LiteralPosition(SourceLocation sourceLocation, int x, int y, int z) {
		super(sourceLocation);
		setPosition(new Position(x, y, z).getCenterPosition());
	}

	public LiteralPosition(SourceLocation sourceLocation, Position position) throws IllegalArgumentException {
		super(sourceLocation);
		this.setPosition(position);
	}

	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}

	public static boolean isValidPosition(Position position) {
		return (position != null);
	}

	@Raw
	public void setPosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
	}

	@Override
	public Position evaluate(Program program) {
		return getPosition();
	}
	
	@Override
	public String toString() {
		return "coordinate [" + 
				getPosition().x() + ", " + 
				getPosition().y() + ", " + 
				getPosition().z() + "]";
	}
}