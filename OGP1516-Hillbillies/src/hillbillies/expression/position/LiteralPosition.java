package hillbillies.expression.position;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.world.Position;

/**
 * A class of coordinate expressions.
 * 
 * @invar  The position of each coordinate must be a valid position for any
 *         coordinate.
 *       | isValidPosition(getPosition())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class LiteralPosition extends Expression<Position> {

	// FIELDS

	/**
	 * Variable registering the position of this coordinate.
	 */
	private Position position;

	// CONSTRUCTORS
	
	/**
	 * Initialize this new coordinate with given coordinates.
	 * 
	 * @param  x
	 * 		   The x-coordinate for this new coordinate.
	 * @param  y
	 * 		   The x-coordinate for this new coordinate.
	 * @param  z
	 * 		   The x-coordinate for this new coordinate.
	 * @effect The position of this new coordinate is set to
	 *         the given position.
	 *       | this.setPosition(new Position(x, y, z).getCenterPosition())
	 */
	public LiteralPosition(SourceLocation sourceLocation, int x, int y, int z) {
		super(sourceLocation);
		this.setPosition(new Position(x, y, z).getCenterPosition());
	}

	/**
	 * Initialize this new coordinate with given position.
	 *
	 * @param  position
	 *         The position for this new coordinate.
	 * @effect The position of this new coordinate is set to
	 *         the given position.
	 *       | this.setPosition(position)
	 */
	public LiteralPosition(SourceLocation sourceLocation, Position position) throws IllegalArgumentException {
		super(sourceLocation);
		this.setPosition(position);
	}


	/**
	 * Return the position of this coordinate.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Check whether the given position is a valid position for
	 * any coordinate.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return 
	 *       | result == (position != null)
	 */
	public static boolean isValidPosition(Position position) {
		return (position != null);
	}

	/**
	 * Set the position of this coordinate to the given position.
	 * 
	 * @param  position
	 *         The new position for this coordinate.
	 * @post   The position of this new coordinate is equal to
	 *         the given position.
	 *       | new.getPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         coordinate.
	 *       | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setPosition(Position position) throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
	}

	// OVERRIDE

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
