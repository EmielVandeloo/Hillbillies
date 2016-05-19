package hillbillies.part3.facade;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import hillbillies.program.*;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.world.Cube;
import hillbillies.world.Position;
import ogp.framework.util.ModelException;

public class Facade implements IFacade {

	@Override
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
		try {
			return new World(terrainTypes, modelListener);
		} catch (Exception e) {
			throw new ModelException();
		}
	}

	@Override
	public int getNbCubesX(World world) throws ModelException {
		return world.getSizeX();
	}

	@Override
	public int getNbCubesY(World world) throws ModelException {
		return world.getSizeY();
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		return world.getSizeZ();
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		world.advanceTime(dt);
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		return world.getAt(new Position(x,y,z)).getId();
	}

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		world.setAt(new Position(x,y,z), Cube.byId(value));
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		return world.getBorder().isSolidConnectedToBorder(x, y, z);
	}

	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		return world.createRandomUnit(enableDefaultBehavior);
	}

	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		world.addUnit(unit);
	}

	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		return world.getAllUnits();
	}

	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		return unit.isCarryingLog();
	}

	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		return unit.isCarryingBoulder();
	}

	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		return !unit.isTerminated();
	}

	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		return unit.getNbExperiencePoints();
	}

	@Override
	public void workAt(Unit unit, int x, int y, int z) throws ModelException {
		unit.workAt(x, y, z);
	}

	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		return unit.getFaction();
	}

	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		return faction.getAllUnits();
	}

	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		return world.getAllFactions();
	}

	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		return boulder.getPosition().convertToDoubleArray();
	}

	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		return world.getAllBoulders();
	}

	@Override
	public double[] getPosition(Log log) throws ModelException {
		return log.getPosition().convertToDoubleArray();
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		return world.getAllLogs();
	}

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		return new Unit(initialPosition, name, strength, agility, weight, toughness, enableDefaultBehavior);
	}

	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		return unit.getPosition().convertToDoubleArray();
	}

	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		return unit.getPosition().convertToIntegerArray();
	}

	@Override
	public String getName(Unit unit) throws ModelException {
		return unit.getName();
	}

	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		unit.setName(newName);
	}

	@Override
	public int getWeight(Unit unit) throws ModelException {
		return unit.getEffectiveWeight();
	}

	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
		unit.setWeight(newValue);
	}

	@Override
	public int getStrength(Unit unit) throws ModelException {
		return unit.getStrength();
	}

	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
		unit.setStrength(newValue);
	}

	@Override
	public int getAgility(Unit unit) throws ModelException {
		return unit.getAgility();
	}

	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
		unit.setAgility(newValue);
	}

	@Override
	public int getToughness(Unit unit) throws ModelException {
		return unit.getToughness();
	}

	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		unit.setToughness(newValue);
	}

	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		return unit.getMaxNbHitPoints();
	}

	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		return unit.getNbHitPoints();
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		return unit.getMaxNbStaminaPoints();
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		return unit.getNbStaminaPoints();
	}

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		unit.advanceTime(dt);
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		unit.moveToAdjacent(dx, dy, dz);
	}

	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getCurrentSpeed();
	}

	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
	}

	@Override
	public void startSprinting(Unit unit) throws ModelException {
		unit.startSprinting();
	}

	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		unit.stopSprinting();
	}

	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.isSprinting();
	}

	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}

	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		unit.moveTo(new Position(cube));
	}

	@Override @Deprecated
	public void work(Unit unit) throws ModelException {
		
	}

	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorking();
	}

	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		attacker.attack(defender);
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		return unit.isAttacking();
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		unit.rest();
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		if (value == true) {
			unit.startDefaultBehaviour();
		} else {unit.stopDefaultBehaviour();}
	}

	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.doesDefaultBehavior();
	}

	@Override
	public ITaskFactory<?, ?, Task> createTaskFactory() {
		return new TaskFactory();
	}

	@Override
	public boolean isWellFormed(Task task) throws ModelException {
		return task.getStatement().isWellFormed();
	}

	@Override
	public Scheduler getScheduler(Faction faction) throws ModelException {
		return faction.getScheduler();
	}

	@Override
	public void schedule(Scheduler scheduler, Task task) throws ModelException {
		scheduler.addToNotAssignedTasks(task);
	}

	@Override
	public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {
		scheduler.replace(original, replacement);
	}

	@Override
	public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
		return scheduler.areTasksPartOf(tasks);
	}

	@Override
	public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
		return scheduler.iterator();
	}

	@Override
	public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
		return task.getSchedulers();
	}

	@Override
	public Unit getAssignedUnit(Task task) throws ModelException {
		return task.getExecutingUnit();
	}

	@Override
	public Task getAssignedTask(Unit unit) throws ModelException {
		return unit.getTask();
	}

	@Override
	public String getName(Task task) throws ModelException {
		return task.getName();
	}

	@Override
	public int getPriority(Task task) throws ModelException {
		return task.getPriority();
	}

}
