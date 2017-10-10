package hillbillies.world;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World;
import ogp.framework.util.Util;

/**
 * A class of positions.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class Position {

	// FIELDS

	/**
	 * Variable registering the x-coordinate of this position.
	 */
	private double x;

	/**
	 * Variable registering the y-coordinate of this position.
	 */
	private double y;

	/**
	 * Variable registering the z-coordinate of this position.
	 */
	private double z;
	
	/**
	 * Variable registering the integer associated with the x-axis.
	 */
	public static final int X = 0;
	
	/**
	 * Variable registering the integer associated with the y-axis.
	 */
	public static final int Y = 1;
	
	/**
	 * Variable registering the integer associated with the z-axis.
	 */
	public static final int Z = 2;

	// CONSTRUCTORS

	/**
	 * Create a new position at the minimum coordinates.
	 *
	 * @effect The new position was created for the minimum values.
	 */
	public Position() {
		//Do nothing.
	}

	/**
	 * Initialize this new position with given x, y and z.
	 *
	 * @param  x
	 *         The x for this new position.
	 * @param  y
	 *         The y for this new position.
	 * @param  z
	 *         The z for this new position.                 
	 * @effect The x of this new position is set to the given x.
	 * @effect The y of this new position is set to the given y.
	 * @effect The z of this new position is set to
	 *         the given z.
	 */
	public Position(double x, double y, double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	/**
	 * Create a new position with the given integer coordinates.
	 * 
	 * @param  pos
	 * 		   The x, y and z-coordinates of the new position
	 * 		   contained in an integer array.
	 * @effect The new position is created for the given coordinates.
	 */
	public Position(int[] pos) {
		this((double) pos[0], (double) pos[1], (double) pos[2]);
	}
	
	/**
	 * Create a new position with the given integer coordinates.
	 * 
	 * @param  pos
	 * 		   The x, y and z-coordinates of the new position
	 * 		   contained in a double array.
	 * @effect The new position is created for the given coordinates.
	 */
	public Position(double[] pos) {
		this(pos[0], pos[1], pos[2]);
	}

	// X-POSITION

	/**
	 * Return the x-coordinate of this position.
	 */
	@Basic @Raw
	public double getX() {
		return this.x;
	}

	/**
	 * Set the x of this position to the given x.
	 * 
	 * @param  x
	 *         The new x for this position.
	 * @post   The x of this new position is equal to
	 *         the given x.
	 */
	@Raw
	public void setX(double x) {
		this.x = x;
	}

	// Y-POSITION

	/**
	 * Return the y-coordinate of this position.
	 */
	@Basic @Raw
	public Double getY() {
		return this.y;
	}

	/**
	 * Set the y of this position to the given y.
	 * 
	 * @param  y
	 *         The new y for this position.
	 * @post   The y of this new position is equal to
	 *         the given y.
	 */
	@Raw
	public void setY(double y) {
		this.y = y;
	}

	// Z-POSITION

	/**
	 * Return the z-coordinate of this position.
	 */
	@Basic @Raw
	public double getZ() {
		return this.z;
	}

	/**
	 * Set the z of this position to the given z.
	 * 
	 * @param  z
	 *         The new z for this position.
	 * @post   The z of this new position is equal to
	 *         the given z.
	 */
	@Raw
	public void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * Check whether this position is valid.
	 * 
	 * @return This position is effective.
	 */
	@Raw
	public boolean isValid() {
		return this != null;
	}

	// SHORT-ACCESS

	/**
	 * Return the x-coordinate of this position.
	 */
	@Basic @Raw
	public double x() {
		return getX();
	}

	/**
	 * Set the x of this position to the given x.
	 * 
	 * @param  x
	 *         The new x for this position.
	 * @effect The x of this new position is set to
	 *         the given x.
	 */
	@Raw
	public void x(double x) {
		setX(x);
	}

	/**
	 * Return the y-coordinate of this position.
	 */
	@Basic @Raw
	public double y() {
		return getY();
	}

	/**
	 * Set the y of this position to the given y.
	 * 
	 * @param  y
	 *         The new y for this position.
	 * @effect The y of this new position is set to
	 *         the given y.
	 */
	@Raw
	public void y(double y) {
		setY(y);
	}

	/**
	 * Return the z-coordinate of this position.
	 */
	@Basic @Raw
	public double z() {
		return getZ();
	}

	/**
	 * Set the z of this position to the given z.
	 * 
	 * @param  z
	 *         The new z for this position.
	 * @effect The z of this new position is equal to
	 *         the given z.
	 */
	@Raw
	public void z(double z) {
		setZ(z);
	}

	// ABSTRACT POSITIONS

	/**
	 * Get the coordinate value in a certain direction.
	 * 
	 * @param  i
	 * 		   The direction to search in.
	 * @return The i-th coordinate of this position.
	 * @throws IndexOutOfBoundsException
	 * 		   The requested value doesn't exist in a 3-dimensional vector.
	 */
	public double getAt(int i) throws IndexOutOfBoundsException {
		if (i == 0) {
			return getX();
		} else if (i == 1) {
			return getY();
		} else if (i == 2) {
			return getZ();
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Set the coordinate value in a certain direction.
	 * 
	 * @param  i
	 * 		   The direction to set.
	 * @param  value
	 * 		   The value to set.
	 * @throws IndexOutOfBoundsException
	 * 		   The requested value doesn't exist in a 3-dimensional vector.
	 */
	public void setAt(int i, double value) throws IndexOutOfBoundsException {
		if (i == 0) {
			setX(value);
		} else if (i == 1) {
			setY(value);
		} else if (i == 2) {
			setZ(value);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/**
	 * Add a given amount to this position in the given direction.
	 * 
	 * @param  i
	 *         The direction in which to add.
	 * @param  amount
	 *         The amount to add.
	 * @return A position containing the current coordinates, with the given
	 *         amount added in the given direction.
	 */
	public Position add(int i, double amount) {
		Position position = new Position(convertToDoubleArray());
		position.setAt(i, getAt(i) + amount);
		return position;
	}

	// CONVERSIONS
	
	/**
	 * Convert this position to an array of doubles.
	 */
	public double[] convertToDoubleArray() {
		return new double[] {x(), y(), z()};
	}
	
	/**
	 * Convert this position to an array of integer.
	 */
	public int[] convertToIntegerArray() {
		return new int[] {(int) x(), (int) y(), (int) z()};
	}
	
	/**
	 * Convert this position to a coordinate object.
	 */
	public Coordinate convertToCoordinate() {
		return new Coordinate(convertToIntegerArray());
	}
	
	/**
	 * Convert the given integer array to a position.
	 * 
	 * @param  position
	 *         The integer array to convert to a position.
	 */
	public static Position convertToPosition(int[] position) {
		return new Position(position);
	}

	// METHODS

	/**
	 * Return the distance between two points in the three-dimensional space.
	 * 
	 * @param  a
	 * 		   The coordinates of the first point, as a position.
	 * @param  b
	 *  	   The coordinates of the second point, as position. 
	 * @return The square root of the sum of the squares of the differences of the respective coordinates.
	 * @throws IllegalArgumentException
	 * 		   a and/or b are not effective.
	 */
	@Model
	public static double getDistance(Position a, Position b) throws IllegalArgumentException {
		if (a == null || b == null) {
			throw new IllegalArgumentException();
		}
		return Math.sqrt(Math.pow(a.x() - b.x(), 2)
				+ Math.pow(a.y() - b.y(), 2) 
				+ Math.pow(a.z() - b.z(), 2));
	}
	
	/**
	 * Return the position of the center of the cube the position occupies.
	 * 
	 * @param  position
	 *         An position containing the position coordinates.
	 * @return The center of the cube that contains the given position.
	 */
	@Model
	public Position getCenterPosition() {
		int[] cubePosition = getCubePosition().convertToIntegerArray();
		Position centerPosition = new Position(
				cubePosition[0] + (double) World.CUBE_LENGTH / 2,
				cubePosition[1] + (double) World.CUBE_LENGTH / 2,
				cubePosition[2] + (double) World.CUBE_LENGTH / 2);
		return centerPosition;
	}
	
	/**
	 * Check whether or not the given position is a center position of a certain 
	 * cube of the game world.
	 * 
	 * @return True if and only if this position equals the center position of the
	 *         cube that contains this position.
	 */
	public boolean positionInCenter() {
		return equals(getCenterPosition());
	}
	
	/**
	 * Return the position of the cube the position occupies.
	 * 
	 * @param  position
	 *         An array containing the position coordinates.
	 * @return The given position coordinates, rounded down to integer values.
	 */
	public Position getCubePosition() {
		return Position.convertToPosition(convertToIntegerArray());
	}
	
	/**
	 * Check whether this position equals a given object.
	 * 
	 * @param  o
	 *         The object to compare with.
	 * @return False if the given object is not effective or is no position.
	 * @return True if and only if each coordinate of the position to test
	 *         is equal to the respective coordinate of the position object to compare with.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Position pos = (Position) o;
		return Util.fuzzyEquals(x(), pos.x()) && Util.fuzzyEquals(y(), pos.y()) &&
				Util.fuzzyEquals(z(), pos.z());
	}
	
	/**
	 * Return a textual representation of this position.
	 */
	@Override
	public String toString() {
		return this.x() + " " + this.y() + " " + this.z();
	}
	
	/**
	 * Return a clone of this position.
	 */
	@Override
	public Position clone() {
		return new Position(convertToDoubleArray());
	}

	/**
	 * Return whether a certain position is adjacent to or the same as another position.
	 * 
	 * @param  current
	 *         An array containing the position coordinates of the first position to compare.
	 * @param  target
	 *         An array containing the position coordinates of the second position to compare.
	 * @return True if the current position is in the same cube as the target position.
	 * @return True if the 2 cubes differ from each other at most by one on any of the three axes.
	 * @throws IllegalArgumentException
	 *         current or target are no valid positions.
	 */
	 public static boolean isAdjacentToOrSame(Position current, Position target) throws IllegalArgumentException {	
	 	 if (! current.isValid() || ! target.isValid()) {
			 throw new IllegalArgumentException();
		 }
		 if (current.getCenterPosition().equals(target.getCenterPosition())) {
			 return true;
		 }
		 int[] currentCube = current.getCubePosition().convertToIntegerArray();
		 int[] targetCube = target.getCubePosition().convertToIntegerArray();
		 if ((currentCube[0] + 1 == targetCube[0] || currentCube[0] - 1 == targetCube[0] || currentCube[0] == targetCube[0]) &&
			 (currentCube[1] + 1 == targetCube[1] || currentCube[1] - 1 == targetCube[1] || currentCube[1] == targetCube[1]) &&
			 (currentCube[2] + 1 == targetCube[2] || currentCube[2] - 1 == targetCube[2] || currentCube[2] == targetCube[2])) {
			 return true;
		 } 
		 return false;
	 }
}