package hillbillies.world;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of coordinates.
 * 
 * @invar  The x of each position must be a valid x for any position.
 *       | isValidX(getX())
 * @invar  The y of each position must be a valid y for any position.
 *       | isValidY(getY())
 * @invar  The z of each position must be a valid z for any position.
 *       | isValidZ(getZ())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Work version: 26/03/2016
 */
public class Coordinate {

	// FIELDS

	/**
	 * Variable registering the x of this position.
	 */
	private int x;

	/**
	 * Variable registering the y of this position.
	 */
	private int y;

	/**
	 * Variable registering the z of this position.
	 */
	private int z;

	/**
	 * Variable registering the integer associated with the x-axe.
	 */
	public static final int X = 0;

	/**
	 * Variable registering the integer associated with the y-axe.
	 */
	public static final int Y = 1;

	/**
	 * Variable registering the integer associated with the z-axe.
	 */
	public static final int Z = 2;


	// CONSTRUCTORS

	/**
	 * Create a new position at the minimum coordinates.
	 *
	 * @effect The new position was created for the minimum values.
	 *       | this((double) WorldStat.MIN_X, (double) WorldStat.MIN_Y, (double) WorldStat.MIN_Z)
	 */
	public Coordinate() {
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
	 *       | this.setx(x)
	 * @effect The y of this new position is set to the given y.
	 *       | this.setY(y)
	 * @effect The z of this new position is set to
	 *         the given z.
	 *       | this.setZ(z)
	 */
	public Coordinate(int x, int y, int z) throws IllegalArgumentException {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	/**
	 * Create a new position with the given integer-coordinates.
	 * 
	 * @param  pos
	 * 		   The x, y and z-coordinates of the new position
	 * 		   contained in an integer-array.
	 * @effect The new position was created for the given coordinates.
	 * 		 | this(pos[0], pos[1], pos[2])
	 */
	public Coordinate(int[] pos) {
		this(pos[0], pos[1], pos[2]);
	}

	/**
	 * Create a new position with the given integer-coordinates.
	 * 
	 * @param  pos
	 * 		   The x, y and z-coordinates of the new position
	 * 		   contained in a double-array.
	 * @effect The new position was created for the given coordinates.
	 * 		 | this((int) pos[0], (int) pos[1], (int) pos[2])
	 */
	public Coordinate(double[] pos) {
		this((int) pos[0], (int) pos[1], (int) pos[2]);
	}

	// X-POSITION

	/**
	 * Return the x of this position.
	 */
	@Basic @Raw
	public int getX() {
		return this.x;
	}

	/**
	 * Set the x of this position to the given x.
	 * 
	 * @param  x
	 *         The new x for this position.
	 * @post   The x of this new position is equal to
	 *         the given x.
	 *       | new.getx() == x
	 * @throws IllegalArgumentException
	 *         The given x is not a valid x for any position.
	 *       | ! isValidx(getX())
	 */
	@Raw
	public void setX(int x) throws IllegalArgumentException {
		this.x = x;
	}

	// Y-POSITION

	/**
	 * Return the y of this position.
	 */
	@Basic @Raw
	public int getY() {
		return this.y;
	}

	/**
	 * Set the y of this position to the given y.
	 * 
	 * @param  y
	 *         The new y for this position.
	 * @post   The y of this new position is equal to
	 *         the given y.
	 *       | new.getY() == y
	 * @throws IllegalArgumentException
	 *         The given y is not a valid y for any position.
	 *       | ! isValidY(getY())
	 */
	@Raw
	public void setY(int y) throws IllegalArgumentException {
		this.y = y;
	}

	// Z-POSITION

	/**
	 * Return the z of this position.
	 */
	@Basic @Raw
	public int getZ() {
		return this.z;
	}

	/**
	 * Set the z of this position to the given z.
	 * 
	 * @param  z
	 *         The new z for this position.
	 * @post   The z of this new position is equal to
	 *         the given z.
	 *       | new.getZ() == z
	 * @throws IllegalArgumentException
	 *         The given z is not a valid z for any position.
	 *       | ! isValidZ(getZ())
	 */
	@Raw
	public void setZ(int z) throws IllegalArgumentException {
		this.z = z;
	}

	// SHORT-ACCESS

	/**
	 * Return the x of this position.
	 */
	@Basic @Raw
	public int x() {
		return getX();
	}

	/**
	 * Set the x of this position to the given x.
	 * 
	 * @param  x
	 *         The new x for this position.
	 * @effect The x of this new position is equal to
	 *         the given x.
	 *       | setX(x)
	 * @throws IllegalArgumentException
	 *         The given x is not a valid x for any position.
	 *       | ! isValidx(getX())
	 */
	@Raw
	public void x(int x) throws IllegalArgumentException {
		setX(x);
	}

	/**
	 * Return the y of this position.
	 */
	@Basic @Raw
	public int y() {
		return getY();
	}

	/**
	 * Set the y of this position to the given y.
	 * 
	 * @param  y
	 *         The new y for this position.
	 * @effect The y of this new position is equal to
	 *         the given y.
	 *       | setY(y)
	 * @throws IllegalArgumentException
	 *         The given y is not a valid y for any position.
	 *       | ! isValidY(getY())
	 */
	@Raw
	public void y(int y) throws IllegalArgumentException {
		setY(y);
	}

	/**
	 * Return the z of this position.
	 */
	@Basic @Raw
	public int z() {
		return getZ();
	}

	/**
	 * Set the z of this position to the given z.
	 * 
	 * @param  z
	 *         The new z for this position.
	 * @effect The z of this new position is equal to
	 *         the given z.
	 *       | setZ(z)
	 * @throws IllegalArgumentException
	 *         The given z is not a valid z for any position.
	 *       | ! isValidZ(getZ())
	 */
	@Raw
	public void z(int z) throws IllegalArgumentException {
		setZ(z);
	}


	// ABSTRACT POSITIONS

	/**
	 * A method to get the value in a certain direction.
	 * 
	 * @param  i
	 * 		   The direction to search in.
	 * @return The ith coordinate of this position
	 * 		 | if (i == 0)
	 *		 |   return getX()
	 *		 | else if (i == 1)
	 *		 |   return getY()
	 *		 | else if (i == 2)
	 *		 |   return getZ()
	 * @throws IndexOutOfBoundsException
	 * 		   The requested value doesn't exist in a 3-dimensional vector.
	 * 		 | (i < 0 || i > 2)
	 */
	public int getAt(int i) throws IndexOutOfBoundsException {
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
	 * A method to set the value in a certain direction.
	 * 
	 * @param  i
	 * 		 | The direction to set.
	 * @param  value
	 * 		 | The value to set.
	 * @throws IndexOutOfBoundsException
	 * 		   The requested value doesn't exist in a 3-dimensional vector.
	 * 		 | (i < 0 || i > 2)
	 * @throws IllegalArgumentException
	 * 		   The given value is not a valid value.
	 */
	public void setAt(int i, int value) throws IndexOutOfBoundsException, IllegalArgumentException{
		if (i == 0) {
			setX(value);
		} else if (i == 1) {
			setY(value);;
		} else if (i == 2) {
			setZ(value);;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	public Coordinate add(int i, int amount) {
		Coordinate coordinate = new Coordinate(convertToDoubleArray());
		coordinate.setAt(i, getAt(i) + amount);
		return coordinate;
	}

	// CONVERSIONS

	public double[] convertToDoubleArray() {
		return new double[] {(double) x(), (double) y(), (double) z()};
	}

	public int[] convertToIntegerArray() {
		return new int[] {x(), y(), z()};
	}
	
	public Position toPosition() {
		return new Position(convertToIntegerArray());
	}
	
	public Position toCenter() {
		return this.toPosition().getCenterPosition();
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
	 *       | result == Math.sqrt(Math.pow(a.x() - b.x(), 2)
	 *		 |         + Math.pow(a.y() - b.y(), 2) 
	 *		 |         + Math.pow(a.z() - b.z(), 2))
	 * @throws IllegalArgumentException
	 * 		   a and/or b reference the null object
	 *       | (a == null) || (b == null)
	 */
	@Model
	public static double getDistance(Coordinate a, Coordinate b) throws IllegalArgumentException {
		if (a == null || b == null) {
			throw new IllegalArgumentException();
		}
		return Math.sqrt(Math.pow(a.x() - b.x(), 2)
				+ Math.pow(a.y() - b.y(), 2) 
				+ Math.pow(a.z() - b.z(), 2));
	}	

	public ArrayList<Coordinate> getDirectNeighbours() {
		ArrayList<Coordinate> neighbours = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = -1; j < 2; j++) {
				if (j != 0) {
					try {
						neighbours.add(this.add(i, j));
					} catch (IllegalArgumentException e) {}
				}
			}
		}
		return neighbours;
	}

	public ArrayList<Coordinate> getAllNeighbours() {
		ArrayList<Coordinate> neighbours = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {

					if (i != 0 || j != 0 || k != 0) {
						try {
							neighbours.add(this.add(X, i).add(Y, j).add(Z, k));
						} catch (IllegalArgumentException e) {}
					}
				}
			}
		}
		return neighbours;
	}

	/**
	 * Find a random position in a radius around this position.
	 * 
	 * @param  radius
	 * 		   The radius around this position to search in.
	 * 		   Must be 1 or more.
	 * @return Return a random position.
	 */
	@Model @Raw
	public Position getRandomPosition(int radius) throws IllegalArgumentException {
		if (radius < 1) {
			throw new IllegalArgumentException();
		}

		Random random = new Random();
		Position spot = new Position();
		double multiplier = random.nextInt(radius - 1) + 1;
		while (true) {
			try {
				for (int i = 0; i < 3; i++) {
					spot.setAt(i, this.getAt(i) + getRandomDirection() * multiplier);
				}
				return spot;
			} catch (IllegalArgumentException e) {}
		}
	}

	/**
	 * Return a valid random direction.
	 *  
	 * @return A random integer with a value of -1, 0 or +1.
	 *       | result == -1 || result == 0 || result == 1   
	 */
	@Model @Raw
	private int getRandomDirection() {
		Random random = new Random();
		int nb = random.nextInt(3);

		if (nb == 0) {
			return X;
		} else if (nb == 1) {
			return Y;
		} else {
			return Z;
		}
	}
	
	// OVERRIDE
	
	@Override
	public Object clone() {
		return new Coordinate(x(), y(), z());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Coordinate)) {
			return false;
		}
		Coordinate other = (Coordinate) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		if (z != other.z) {
			return false;
		}
		return true;
	}
}