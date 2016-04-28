package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import hillbillies.model.Boulder;
import hillbillies.model.Entity;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.world.Cube;
import hillbillies.world.Position;
import ogp.framework.util.ModelException;

public class WorldTest {

	private World world;
	private Facade facade;
	private Unit randomUnit;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	@Before
	public void setup() throws ModelException {
		int[][][] types = new int[5][5][5];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_TREE;
		types[1][1][2] = TYPE_WORKSHOP;
		this.facade = new Facade();
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		randomUnit = new Unit(new int[] {0,0,0}, "AAA", 50, 50, 50, 50, false);
		facade.addUnit(randomUnit, world);
	}
	
	@Test
	public void terminate() {
		Faction formerFaction = randomUnit.getFaction();
		world.terminate();
		assertTrue(world.isTerminated());
		assertTrue(randomUnit.isTerminated());
		assertTrue(formerFaction.isTerminated());
		assertNull(randomUnit.getWorld());
		assertNull(randomUnit.getFaction());
		assertNull(formerFaction.getWorld());
		assertFalse(formerFaction.hasAsUnit(randomUnit));
		assertFalse(world.hasAsEntity(randomUnit));
		assertFalse(world.hasAsFaction(formerFaction));
	}
	
	@Test
	public void getAt() {
		assertTrue(world.getAt(new Position(1,1,0)) == Cube.ROCK);
		assertTrue(world.getAt(new Position(1,1,1)) == Cube.WOOD);
		assertTrue(world.getAt(new Position(1,1,2)) == Cube.WORKBENCH);
		assertTrue(world.getAt(new Position(0,0,0)) == Cube.AIR);
	}
	
	@Test
	public void setAt() {
		world.setAt(new Position(0,0,0), Cube.ROCK);
		assertTrue(world.getAt(new Position(0,0,0)) == Cube.ROCK);
		world.setAt(new Position(0,0,0), Cube.WOOD);
		assertTrue(world.getAt(new Position(0,0,0)) == Cube.WOOD);
		world.setAt(new Position(0,0,0), Cube.WORKBENCH);
		assertTrue(world.getAt(new Position(0,0,0)) == Cube.WORKBENCH);
		world.setAt(new Position(0,0,0), Cube.AIR);
		assertTrue(world.getAt(new Position(0,0,0)) == Cube.AIR);
	}
	
	@Test
	public void place_LegalCase() {
		world.place(new Position(0,0,0), Cube.ROCK);
		assertTrue(world.getAt(new Position(0,0,0)) == Cube.ROCK);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void place_CubeNotAir() {
		world.place(new Position(1,1,1), Cube.ROCK);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void place_PlaceAir() {
		world.place(new Position(0,0,0), Cube.AIR);
	}
	
	@Test
	public void remove() {
		world.remove(new Position(1,1,0));
		assertTrue(world.getAt(new Position(1,1,0)) == Cube.AIR);
		assertTrue(world.getAt(new Position(1,1,1)) == Cube.AIR);
	}
	
	@Test
	public void isPassable_TrueCase() {
		assertTrue(world.getAt(new Position(0,0,0)).isPassable());
	}
	
	@Test
	public void isPassable_FalseCase() {
		assertFalse(world.getAt(new Position(1,1,0)).isPassable());
	}
	
	@Test
	public void hasAsEntity_TrueCase() {
		assertTrue(world.hasAsEntity(randomUnit));
	}
	
	@Test
	public void hasAsEntity_FalseCase() {
		assertFalse(world.hasAsEntity(new Unit(new int[] {1,1,1}, "BBB", 50, 50, 50, 50, false)));
	}
	
	@Test
	public void getAllEntities() {
		Set<Entity> entitySet = new HashSet<>();
		entitySet.add(randomUnit);
		assertEquals(entitySet, world.getAllEntities());
	}
	
	public void getAllEntitiesOf_EntitiesExist() {
		Set<Entity> entitySet = new HashSet<>();
		entitySet.add(randomUnit);
		assertEquals(entitySet, world.getAllEntitiesOf(Unit.ENTITY_ID));
	}
	
	@Test
	public void getAllEntitiesOf_EntityDoesNotExist() {
		assertNull(world.getAllEntitiesOf(Boulder.ENTITY_ID));
	}
	
	@Test
	public void addEntity() {
		Boulder boulder = new Boulder(world, new Position(0,0,0));
		world.addEntity(boulder);
		assertTrue(world.hasAsEntity(boulder));
	}
	
	@Test
	public void removeEntity_PossibleCase() {
		randomUnit.setWorld(null);
		world.removeEntity(randomUnit);
		assertFalse(world.hasAsEntity(randomUnit));
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void removeEntity_ImpossibleCase() {
		world.removeEntity(new Boulder(world, new Position(0,0,0)));
	}
	
	@Test
	public void getEntitiesAt_ExistsEntity() {
		List<Entity> entities = new ArrayList<>();
		entities.add(randomUnit);
		assertEquals(entities, world.getEntitiesAt(new Position(0,0,0), Unit.ENTITY_ID));
	}
	
	@Test
	public void getEntitiesAt_ExistsNoEntity() {
		List<Entity> entities = new ArrayList<>();
		assertEquals(entities, world.getEntitiesAt(new Position(0,0,0), Log.ENTITY_ID));
	}
	
	@Test
	public void hasAsFaction() {
		assertTrue(world.hasAsFaction(randomUnit.getFaction()));
	}
	
	@Test
	public void getNbFactions() {
		assertEquals((int) 1, (int) world.getNbFactions(), 0);
	}
	
	@Test
	public void getAllFactions() {
		Set<Faction> allFactions = new HashSet<>();
		allFactions.add(randomUnit.getFaction());
		assertEquals(allFactions, world.getAllFactions());
	}
	
	@Test
	public void addFaction_PossibleCase() {
		Faction faction = new Faction(world, "BBB");
		world.addFaction(faction);
		assertTrue(world.hasAsFaction(faction));
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void addFaction_ImpossibleCase2() {
		Faction faction = randomUnit.getFaction();
		world.addFaction(faction);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void removeFaction_ImpossibleCase1() {
		world.removeFaction(randomUnit.getFaction());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void removeFaction_ImpossibleCase2() {
		Faction faction = new Faction(world, "CCC");
		world.removeFaction(faction);
	}
	
	@Test
	public void getAllUnits() {
		Set<Unit> units = new HashSet<>();
		units.add(randomUnit);
		assertEquals(units, world.getAllUnits());
	}
	
	@Test
	public void getAllBoulders() {
		Set<Boulder> boulders = new HashSet<>();
		assertEquals(boulders, world.getAllBoulders());
	}
	
	@Test
	public void getAllLogs() {
		Set<Log> logs = new HashSet<>();
		assertEquals(logs, world.getAllLogs());
	}
	
	@Test
	public void addUnit() throws ModelException {
		Unit unit = new Unit(new int[] {0,0,0}, "BBB", 50, 50, 50, 50, false);
		facade.addUnit(unit, world);
		assertTrue(world.hasAsEntity(unit));
		assertTrue(unit.getWorld() == world);
	}
	
	@Test
	public void createRandomUnit() {
		Unit unit = world.createRandomUnit(true);
		assertTrue(unit.doesDefaultBehavior());
	}
	
	@Test
	public void isValidPosition_TrueCase() {
		assertTrue(world.isValidPosition(new Position(0,0,0)));
	}
	
	@Test
	public void isValidPosition_NullPosition() {
		assertTrue(world.isValidPosition(null));
	}
	
	@Test
	public void isValidPosition_FalseCase() {
		assertFalse(world.isValidPosition(new Position(-1,0,0)));
	}
	
	@Test
	public void existsPathBetween_TrueCase() {
		assertTrue(world.existsPathBetween(new Position(0,0,0), new Position(2,0,0)));
	}
	
	@Test
	public void existsPathBetween_FalseCase() {
		assertFalse(world.existsPathBetween(new Position(0,0,0), new Position(3,3,3)));
	}
	
	@Test
	public void hasSolidNeighbour_TrueCase() {
		assertTrue(world.hasSolidNeighbour(new Position(0,0,0)));
	}
	
	@Test
	public void hasSolidNeighbour_FalseCase() {
		assertFalse(world.hasSolidNeighbour(new Position(3,3,3)));
	}
	
	@Test
	public void hasUnderlyingSolid_TrueCase() {
		assertTrue(world.hasUnderlyingSolid(new Position(0,0,0)));
	}
	
	@Test
	public void hasUnderlyingSolid_FalseCase() {
		assertFalse(world.hasUnderlyingSolid(new Position(1,1,4)));
	}
}
