package hillbillies.expression;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

/**
 * A class of expressions.
 * 
 * @invar  The source location of each expression must be a valid source location for any
 *         expression.
 *       | isValidSourceLocation(getSourceLocation())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 */
public abstract class Expression<E> {
	
	// FIELDS

	/**
	 * Variable registering the source location of this expression.
	 */
	private SourceLocation sourceLocation;

	// CONSTRUCTOR

	/**
	 * Initialize this new expression with given source location.
	 *
	 * @param  sourceLocation
	 *         The source location for this new expression.
	 * @effect The source location of this new expression is set to
	 *         the given source location.
	 *       | this.setSourceLocation(sourceLocation)
	 */
	public Expression(SourceLocation sourceLocation) throws IllegalArgumentException {
		this.setSourceLocation(sourceLocation);
	}

	
	// GETTERS AND SETTERS

	/**
	 * Return the source location of this expression.
	 */
	@Basic @Raw
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

	/**
	 * Check whether the given source location is a valid source location for
	 * any expression.
	 *  
	 * @param  source location
	 *         The source location to check.
	 * @return 
	 *       | result == (sourceLocation != null)
	 */
	public static boolean isValidSourceLocation(SourceLocation sourceLocation) {
		return (sourceLocation != null);
	}

	/**
	 * Set the source location of this expression to the given source location.
	 * 
	 * @param  sourceLocation
	 *         The new source location for this expression.
	 * @post   The source location of this new expression is equal to
	 *         the given source location.
	 *       | new.getSourceLocation() == sourceLocation
	 * @throws IllegalArgumentException
	 *         The given source location is not a valid source location for any
	 *         expression.
	 *       | ! isValidSourceLocation(getSourceLocation())
	 */
	@Raw
	public void setSourceLocation(SourceLocation sourceLocation) throws IllegalArgumentException {
		if (! isValidSourceLocation(sourceLocation))
			throw new IllegalArgumentException();
		this.sourceLocation = sourceLocation;
	}
	
	
	// ABSTRACT METHODS

	public abstract E evaluate(Program program);

}
