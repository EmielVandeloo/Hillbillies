package hillbillies.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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
		
		Map<Coordinate, Node> closedList = new HashMap<>();
		ArrayList<Node> openList = new ArrayList<>();
		Map<Node, Node> cameFrom = new HashMap<>();

		openList.add(start);
		start.setGCost(0);
		start.setHCost(Node.getDistanceBetween(start, end));;

		while (! openList.isEmpty()) {
			Node current = getMostPromising(openList);
			if (current == end) {
				return reconstructPath(cameFrom, end, world.getWorldVersion());
			}

			openList.remove(current);
			closedList.put(current.getCoordinate(), current);

			ArrayList<Coordinate> neighbours = current.getCoordinate().getAllNeighbours();
			for (Coordinate coordinate : neighbours) {
				if (! closedList.containsKey(coordinate)) {
					Node neighbour = new Node(coordinate);

					double gCost = current.getGCost() + Node.getDistanceBetween(current, neighbour);
					if (! openList.contains(neighbour)) {
						openList.add(neighbour);
					} 
					else if (gCost < neighbour.getGCost() && isCornerAllowed(world, current, neighbour)) {
						cameFrom.put(neighbour, current);
						neighbour.setGCost(gCost);
					}
				}
			}
		}
		return new Path(world.getWorldVersion());
	}

	private static Path reconstructPath(Map<Node, Node> cameFrom, Node current, int worldVersion) {
		LinkedList<Position> totalPath = new LinkedList<>();
		totalPath.add(current.getCoordinate().toCenter());
		
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			totalPath.addFirst(current.getCoordinate().toCenter());
		}
		
		return new Path(totalPath, worldVersion);
	}

	private static Node getMostPromising(ArrayList<Node> list) {
		Collections.sort(list);
		return list.get(0);
	}
	
	private static boolean isCornerAllowed(World world, Node a, Node b) {
		int[] directions = getDirections(a.getCoordinate(), b.getCoordinate());
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					
					int[] dir = {0, 0, 0};
					if (i == 1) dir[0] = directions[0];
					if (i == 1) dir[1] = directions[1];
					if (i == 1) dir[2] = directions[2];
					
					if (! world.isPassable(new Coordinate(dir).toCenter())) {
						return false;
					}
					
				}
			}
		}
		
		return true;
	}
	
	private static int[] getDirections(Coordinate a, Coordinate b) {
		int[] directions = new int[3];
		
		for (int i = 0; i < 3; i++) {
			directions[i] = b.getAt(i) - a.getAt(i);
		}
		
		return directions;
	}

}
