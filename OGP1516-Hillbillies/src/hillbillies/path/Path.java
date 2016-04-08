package hillbillies.path;

import java.util.LinkedList;

import hillbillies.world.Position;

public class Path {

	private LinkedList<Position> path;
	private final int worldVersion;

	public Path(int worldVersion) {
		this.worldVersion = worldVersion;
	}

	public Path(LinkedList<Position> path, int worldVersion) {
		this.path = path;
		this.worldVersion = worldVersion;
	}

	public Position popNextPosition() {
		if (path.isEmpty()) {
			return null;
		} else {
			return path.removeFirst();
		}
	}

	public boolean needsUpdate(int worldVersion) {
		return (this.worldVersion != worldVersion);
	}

}
