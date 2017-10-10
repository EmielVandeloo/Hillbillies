package hillbillies.expression;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public abstract class Expression<E> {

	private SourceLocation sourceLocation;

	public Expression(SourceLocation sourceLocation) throws IllegalArgumentException {
		this.setSourceLocation(sourceLocation);
	}

	@Basic @Raw
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

	public static boolean isValidSourceLocation(SourceLocation sourceLocation) {
		return (sourceLocation != null);
	}

	@Raw
	public void setSourceLocation(SourceLocation sourceLocation) throws IllegalArgumentException {
		if (! isValidSourceLocation(sourceLocation))
			throw new IllegalArgumentException();
		this.sourceLocation = sourceLocation;
	}

	public abstract E evaluate(Program program);
	
	public boolean isWellFormed() {
		// Always true (generic class: info at compile time)
		return true;
	}
}