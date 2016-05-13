package hillbillies.path;

import java.util.ArrayList;

import hillbillies.world.Position;

public class Path {

	private ArrayList<Position> path;
	private int worldVersion;

	public Path(int worldVersion) {
		this.worldVersion = worldVersion;
	}

	public Path(ArrayList<Position> path, int worldVersion) {
		this.path = path;
		this.worldVersion = worldVersion;
	}

	public Position popNextPosition() {
		if (path == null) {
			return null;
		} else if (path.isEmpty()) {
			return null;
		} else {
			return path.remove(path.size() - 1);
		}
	}

	public boolean needsUpdate(int worldVersion) {
		return (this.worldVersion != worldVersion);
	}
	
	public ArrayList<Position> getPath() {
		return path;
	}

}