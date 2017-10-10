package hillbillies.model.character;

import java.util.ArrayList;
import hillbillies.model.Boulder;
import hillbillies.model.Entity;
import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.world.Cube;
import hillbillies.world.Position;

public enum JobSelector {
	
	DROP,
	UPGRADE,
	PICK_UP_BOULDER,
	PICK_UP_LOG,
	REMOVE_BLOCK,
	IDLE;
	
	public static JobSelector getJob(World world, Position position, Position workPosition, Inventory inventory) {
		ArrayList<Entity> logs = world.getEntitiesAt(workPosition.getCubePosition(), Log.ENTITY_ID);
		ArrayList<Entity> boulders = world.getEntitiesAt(workPosition.getCubePosition(), Boulder.ENTITY_ID);
		if (! inventory.isEmpty()) {
			return JobSelector.DROP;
		} else if ((world.getAt(position).getId() == Cube.WORKBENCH.getId()) &&
				(! logs.isEmpty()) && 
				(! boulders.isEmpty())) {
			return JobSelector.UPGRADE;
		} else if (! boulders.isEmpty()) {
			return JobSelector.PICK_UP_BOULDER;
		} else if (! logs.isEmpty()) {
			return JobSelector.PICK_UP_LOG;
		} else if (! world.isPassable(workPosition)) {
			return JobSelector.REMOVE_BLOCK;
		} else {
			return JobSelector.IDLE;
		}
	}
}