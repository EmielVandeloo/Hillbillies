package hillbillies.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import hillbillies.model.World;
import hillbillies.world.Coordinate;
import hillbillies.world.Position;

public class PathFinder {

	public static Path findPath(World world, Position start, Position end) {
		return aStar(world,
				new Node(start.convertToCoordinate()), 
				new Node(end.convertToCoordinate()));
	}

	private static Path aStar(World world, Node start, Node end) {
		Map<Coordinate, Node> closedSet = new HashMap<>();
		ArrayList<Node> openSet = new ArrayList<>();
		Map<Node, Node> cameFrom = new HashMap<>();		
		openSet.add(start);
		start.setGCost(0);
		start.setFCost(Node.getDistanceBetween(start, end));
		while (! openSet.isEmpty()) {
			Node current = getMostPromising(openSet);	
			if (current.equals(end)) {
				return reconstructPath(cameFrom, current, world.getWorldVersion());
			}			
			openSet.remove(current);
			closedSet.put(current.getCoordinate(), current);			
			for (Coordinate coordinate : current.getCoordinate().getAllNeighbours()) {				
				if (! world.isPassable(coordinate.toCenter())) {
					continue;
				} else if (! world.isValidPosition(coordinate.toCenter())) {
					continue;
				} else if (! world.hasSolidNeighbour(coordinate.toCenter())) {
					continue;
				}				
				if (closedSet.containsKey(coordinate)) {
					continue;
				}				
				Node neighbour = new Node(coordinate);
				double tentativeGCost = current.getGCost() + Node.getDistanceBetween(current, neighbour);				
				if (! isCornerAllowed(world, current, neighbour)) {
					continue;
				}				
				if (! openSet.contains(neighbour)) {
					openSet.add(neighbour);
				}
				else if (tentativeGCost >= neighbour.getGCost()) {
					continue;
				}				
				cameFrom.put(neighbour, current);
				neighbour.setGCost(tentativeGCost);
				neighbour.setFCost(neighbour.getGCost() + Node.getDistanceBetween(neighbour, end));
			}
		}		
		return new Path(world.getWorldVersion());
	}

	private static Path reconstructPath(Map<Node, Node> cameFrom, Node current, int worldVersion) {
		ArrayList<Position> totalPath = new ArrayList<>();
		totalPath.add(current.getCoordinate().toCenter());
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			totalPath.add(current.getCoordinate().toCenter());
		}		
		totalPath.remove(totalPath.size() - 1);
		return new Path(totalPath, worldVersion);
	}

	private static Node getMostPromising(ArrayList<Node> list) {
		Collections.sort(list);
		return list.get(0);
	}

	public static boolean isCornerAllowed(World world, Node start, Node end) {
		int[] directions = getDirections(start.getCoordinate(), end.getCoordinate());
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					int[] dir = start.getCoordinate().convertToIntegerArray();
					if (i == 1) dir[0] += directions[0];
					if (j == 1) dir[1] += directions[1];
					if (k == 1) dir[2] += directions[2];
					if (! world.isPassable(new Coordinate(dir).toCenter())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static int[] getDirections(Coordinate start, Coordinate end) {
		int[] directions = new int[3];
		for (int i = 0; i < 3; i++) {
			directions[i] = end.getAt(i) - start.getAt(i);
		}
		return directions;
	}

}
