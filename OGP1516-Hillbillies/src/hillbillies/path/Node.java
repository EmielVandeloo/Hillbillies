package hillbillies.path;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.world.Coordinate;

/**
 * @invar  Each node can have its coordinate as coordinate.
 *       | canHaveAsCoordinate(this.getCoordinate())
 * @invar  The gCost of each Node must be a valid gCost for any
 *         Node.
 *       | isValidGCost(getGCost())
 * @invar  Each node can have its fCost as fCost.
 *       | canHaveAsFCost(this.getFCost())
 *       
 * @author Pieter-Jan
 */
public class Node implements Comparable<Node> {

	// FIELDS

	/**
	 * Variable registering the coordinate of this node.
	 */
	private final Coordinate coordinate;

	/**
	 * Variable registering the fCost of this node.
	 */
	private double fCost = Double.POSITIVE_INFINITY;

	/**
	 * Variable registering the gCost of this Node.
	 */
	private double gCost = Double.POSITIVE_INFINITY;

	// CONSTRUCTOR

	/**
	 * Initialize this new node with given coordinate.
	 * 
	 * @param  coordinate
	 *         The coordinate for this new node.
	 * @post   The coordinate of this new node is equal to the given
	 *         coordinate.
	 *       | new.getCoordinate() == coordinate
	 * @effect Calculate the new gCost.
	 * 		 | updateGCost(start)
	 * @effect Calculate the new hCost.
	 * 		 | updateHCost(end)
	 * @throws IllegalArgumentException
	 *         This new node cannot have the given coordinate as its coordinate.
	 *       | ! canHaveAsCoordinate(this.getCoordinate())
	 */
	public Node(Coordinate coordinate) throws IllegalArgumentException {
		if (! canHaveAsCoordinate(coordinate)) {
			throw new IllegalArgumentException();
		}
		this.coordinate = coordinate;
	}

	// GETTERS AND SETTERS

	/**
	 * Return the coordinate of this node.
	 */
	@Basic @Raw @Immutable
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	/**
	 * Check whether this node can have the given coordinate as its coordinate.
	 *  
	 * @param  coordinate
	 *         The coordinate to check.
	 * @return 
	 *       | result == (coordinate != null)
	 */
	@Raw
	public boolean canHaveAsCoordinate(Coordinate coordinate) {
		return (coordinate != null);
	}

	/**
	 * Return the gCost of this Node.
	 */
	@Basic @Raw
	public double getGCost() {
		return this.gCost;
	}

	/**
	 * Check whether the given gCost is a valid gCost for
	 * any Node.
	 *  
	 * @param  gCost
	 *         The gCost to check.
	 * @return 
	 *       | result == (gCost >= 0)
	 */
	public static boolean isValidGCost(double gCost) {
		return (gCost >= 0);
	}

	/**
	 * Set the gCost of this Node to the given gCost.
	 * 
	 * @param  gCost
	 *         The new gCost for this Node.
	 * @post   The gCost of this new Node is equal to
	 *         the given gCost.
	 *       | new.getGCost() == gCost
	 * @throws IllegalArgumentException
	 *         The given gCost is not a valid gCost for any
	 *         Node.
	 *       | ! isValidGCost(getGCost())
	 */
	@Raw
	public void setGCost(double gCost) throws IllegalArgumentException {
		if (! isValidGCost(gCost))
			throw new IllegalArgumentException();
		this.gCost = gCost;
	}
	
	/**
	 * Return the fCost of this node.
	 */
	@Basic @Raw @Immutable
	public double getFCost() {
		return this.fCost;
	}

	/**
	 * Set the fCost of this Node to the given fCost.
	 * 
	 * @param  fCost
	 *         The new fCost for this Node.
	 * @post   The gCost of this new Node is equal to
	 *         the given fCost.
	 *       | new.getFCost() == fCost
	 * @throws IllegalArgumentException
	 *         The given gCost is not a valid fCost for any
	 *         Node.
	 *       | ! isValidFCost(getFCost())
	 */
	@Raw
	public void setFCost(double fCost) throws IllegalArgumentException {
		if (! isValidGCost(fCost))
			throw new IllegalArgumentException();
		this.fCost = fCost;
	}

	/**
	 * Check whether this node can have the given fCost as its fCost.
	 *  
	 * @param  fCost
	 *         The fCost to check.
	 * @return 
	 *       | result == (fCost >= 0)
	 */
	@Raw
	public boolean canHaveAsFCost(double fCost) {
		return (fCost >= 0);
	}

	// METHODS

	public static double getDistanceBetween(Node start, Node end) {
		return Coordinate.getDistance(start.getCoordinate(), end.getCoordinate());
	}

	// OVERRIDE

	@Override
	public int compareTo(Node o) {
		if (this.getFCost() == o.getFCost()) {
			return 0;
		} else if (this.getFCost() > o.getFCost()) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return "Node\n  " + coordinate + ",\n  fCost=" + fCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
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
		if (!(obj instanceof Node)) {
			return false;
		}
		Node other = (Node) obj;
		if (coordinate == null) {
			if (other.coordinate != null) {
				return false;
			}
		} else if (!coordinate.equals(other.coordinate)) {
			return false;
		}
		return true;
	}
}
