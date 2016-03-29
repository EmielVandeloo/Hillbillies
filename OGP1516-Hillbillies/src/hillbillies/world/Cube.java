package hillbillies.world;

import hillbillies.model.item.Droppable;

public enum Cube {
	
	// ENUM
	
	AIR (true, 0, null),
	ROCK (false, 1, null),
	WOOD (false, 2, null),
	WORKBENCH (true, 3, null);
	
	
	// FIELDS
	
	private final boolean passable;
	private final int id;
	private final Droppable dropable;
	
	
	// CONSTRUCTORS
	
	private Cube(boolean passable, int id, Droppable dropable) {
		this.passable = passable;
		this.id = id;
		this.dropable = dropable;
		
		//TODO Hoe kan het droppen van een item algemeen blijven?
	}
	
	
	// GETTERS AND SETTERS
	
	public boolean isPassable() {
		return passable;
	}
	
	public int getId() {
		return id;
	}
	
	
	// METHODS

	public void drop(Position position) {
		if (dropable != null) {
			dropable.drop(position);
		}
	}
	
	public static Cube byId(int id) throws IndexOutOfBoundsException {
		for (Cube cube : Cube.values()) {
			if (cube.getId() == id) {
				return cube;
			}
		}
		throw new IndexOutOfBoundsException();
	}

}