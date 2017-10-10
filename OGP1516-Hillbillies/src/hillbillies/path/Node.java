package hillbillies.path;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.world.Coordinate;

public class Node implements Comparable<Node> {

	private final Coordinate coordinate;
	private double fCost = Double.POSITIVE_INFINITY;
	private double gCost = Double.POSITIVE_INFINITY;

	public Node(Coordinate coordinate) throws IllegalArgumentException {
		if (! canHaveAsCoordinate(coordinate)) {
			throw new IllegalArgumentException();
		}
		this.coordinate = coordinate;
	}

	@Basic @Raw @Immutable
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	@Raw
	public boolean canHaveAsCoordinate(Coordinate coordinate) {
		return (coordinate != null);
	}

	@Basic @Raw
	public double getGCost() {
		return this.gCost;
	}

	public static boolean isValidGCost(double gCost) {
		return (gCost >= 0);
	}

	@Raw
	public void setGCost(double gCost) throws IllegalArgumentException {
		if (! isValidGCost(gCost))
			throw new IllegalArgumentException();
		this.gCost = gCost;
	}

	@Basic @Raw @Immutable
	public double getFCost() {
		return this.fCost;
	}

	@Raw
	public void setFCost(double fCost) throws IllegalArgumentException {
		if (! isValidGCost(fCost))
			throw new IllegalArgumentException();
		this.fCost = fCost;
	}

	@Raw
	public boolean canHaveAsFCost(double fCost) {
		return (fCost >= 0);
	}

	public static double getDistanceBetween(Node start, Node end) {
		return Coordinate.getDistance(start.getCoordinate(), end.getCoordinate());
	}

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