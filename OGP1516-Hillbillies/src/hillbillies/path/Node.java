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
 **@invar  The hCost of each Node must be a valid hCost for any
 *         Node.
 *       | isValidHCost(getHCost())
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
	 * Variable registering the hCost of this Node.
	 */
	private double hCost = Double.POSITIVE_INFINITY;

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
		if (! canHaveAsCoordinate(coordinate))
			throw new IllegalArgumentException();
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
	 *       | result == TODO
	 */
	@Raw
	public boolean canHaveAsCoordinate(Coordinate coordinate) {
		if (coordinate == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Return the hCost of this Node.
	 */
	@Basic @Raw
	public double getHCost() {
		return this.hCost;
	}

	/**
	 * Check whether the given hCost is a valid hCost for
	 * any Node.
	 *  
	 * @param  hCost
	 *         The hCost to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidHCost(double hCost) {
		return false;
	}

	/**
	 * Set the hCost of this Node to the given hCost.
	 * 
	 * @param  hCost
	 *         The new hCost for this Node.
	 * @post   The hCost of this new Node is equal to
	 *         the given hCost.
	 *       | new.getHCost() == hCost
	 * @throws IllegalArgumentException
	 *         The given hCost is not a valid hCost for any
	 *         Node.
	 *       | ! isValidHCost(getHCost())
	 */
	@Raw
	public void setHCost(double hCost) 
			throws IllegalArgumentException {
		if (! isValidHCost(hCost))
			throw new IllegalArgumentException();
		this.hCost = hCost;
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
	 *       | result == (gCost > 0)
	 */
	public static boolean isValidGCost(double gCost) {
		return (gCost > 0);
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


	// METHODS

	public double getFCost() {
		return getGCost() + getHCost();
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(gCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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

	@Override
	public String toString() {
		return "Node [coordinate=" + coordinate + ", hCost=" + hCost + ", gCost=" + gCost + "]";
	}	

}