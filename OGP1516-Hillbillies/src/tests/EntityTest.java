package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import hillbillies.model.Boulder;
import hillbillies.model.Entity;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.world.Position;
import ogp.framework.util.ModelException;

public class EntityTest {

	private World world;
	private Facade facade;
	private Unit randomUnit;
	private Boulder randomBoulder;
	private Log randomLog;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	@Before
	public void setUp() throws ModelException {
		int[][][] types = new int[5][5][5];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_TREE;
		types[1][1][2] = TYPE_WORKSHOP;
		this.facade = new Facade();
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		randomUnit = new Unit(new int[] {0,0,0}, "BBB", 50, 50, 50, 50, false);
		facade.addUnit(randomUnit, world);
		randomBoulder = new Boulder(world, new Position(0,0,0));
		randomLog = new Log(world, new Position(0,0,0));
		world.addEntity(randomBoulder);
		world.addEntity(randomLog);
	}
	
	@Test
	public void constructor_LegalCase() {
		Entity boulder = new Boulder(world, new Position(0,0,0));
        assertEquals(world, boulder.getWorld());
        assertEquals(new Position(0.5,0.5,0.5), boulder.getPosition());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void constructor_IllegalCase() {
		new Boulder(world, new Position(-1,0,0));
	}
	
	@Test
	public void terminate() {
		randomBoulder.terminate();
		assertTrue(randomBoulder.isTerminated());
		assertNull(randomBoulder.getWorld());
		assertFalse(world.hasAsEntity(randomBoulder));
	}
	
	@Test
	public void setWorld() {
		randomLog.setWorld(null);
		assertNull(randomLog.getWorld());
	}
	
	@Test
	public void isValidPosition_TrueCase() {
		assertTrue(randomBoulder.isValidPosition(new Position(0,0,0)));
	}
	
	@Test
	public void isValidPosition_FalseCase() {
		assertFalse(randomBoulder.isValidPosition(new Position(-1,-1,-1)));
	}
	
	@Test
	public void setPosition_ValidPosition() {
		randomBoulder.setPosition(new Position(1,1,1));
		assertEquals(new Position(1,1,1), randomBoulder.getPosition());
	}
	
	@Test
	public void setPosition_NullPosition() {
		randomBoulder.setPosition(null);
		assertNull(randomBoulder.getPosition());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setPosition_InvalidPosition() {
		randomBoulder.setPosition(new Position(-1,-1,-1));
	}
	
	@Test
	public void startStopFalling() {
		randomBoulder.startFalling();
		assertTrue(randomBoulder.isFalling());
		randomBoulder.stopFalling();
		assertFalse(randomBoulder.isFalling());
	}
	
	@Test
	public void fallBehavior() throws ModelException {
		Boulder boulder = new Boulder(world, new Position(0,0,1));
		world.addEntity(boulder);
		advanceTimeFor(facade, world, 0.05, 0.05);
		assertTrue(boulder.isFalling());
		advanceTimeFor(facade, world, 5, 0.2);
		assertFalse(boulder.isFalling());
		assertEquals(new Position(0.5,0.5,0.5), boulder.getPosition());
	}
	
	/**
	 * Helper method to advance time for the given world by some time.
	 * 
	 * @param time
	 *            The time, in seconds, to advance.
	 * @param step
	 *            The step size, in seconds, by which to advance.
	 */
	private static void advanceTimeFor(IFacade facade, World world, double time, double step) throws ModelException {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			facade.advanceTime(world, step);
		facade.advanceTime(world, time - n * step);
	}
	
}
