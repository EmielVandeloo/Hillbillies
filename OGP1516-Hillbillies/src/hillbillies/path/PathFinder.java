package hillbillies.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import hillbillies.world.Coordinate;
import hillbillies.world.Position;
import hillbillies.model.World;

public class PathFinder {

	public Path findPath(World world, Position start, Position end) {
		return aStar(world,
				new Node(start.convertToCoordinate()), 
				new Node(end.convertToCoordinate()));
	}

	private Path aStar(World world, Node start, Node end) {
		
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
					else if (gCost < neighbour.getGCost()) {
						cameFrom.put(neighbour, current);
						neighbour.setGCost(gCost);
					}
				}
			}
		}
		return new Path(world.getWorldVersion());
	}

	private Path reconstructPath(Map<Node, Node> cameFrom, Node current, int worldVersion) {
		LinkedList<Position> totalPath = new LinkedList<>();
		totalPath.add(current.getCoordinate().toCenter());
		
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			totalPath.addFirst(current.getCoordinate().toCenter());
		}
		
		return new Path(totalPath, worldVersion);
	}

	private Node getMostPromising(ArrayList<Node> list) {
		Collections.sort(list);
		return list.get(0);
	}

}