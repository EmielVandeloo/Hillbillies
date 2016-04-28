package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.world.Position;
import ogp.framework.util.ModelException;

public class LogTest {

	private Facade facade;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	private World world;
	
	@Before
	public void setUp() throws ModelException {
		int[][][] types = new int[5][5][5];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_TREE;
		types[1][1][2] = TYPE_WORKSHOP;
		this.facade = new Facade();
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
	}
	
	@Test
	public void constructor_LegalCase() {
		Log log = new Log(world, new Position(0,0,0));
		assertEquals(world, log.getWorld());
		assertEquals(new Position(0.5,0.5,0.5), log.getPosition());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void constructor_IllegalCase() {
		new Log(world, new Position(-1,0,0));
	}
	
	@Test
	public void drop() {
		for (int i = 0 ; i < 20 ; i++) {
			Log.drop(world, new Position(0,0,0));
		}
		assertTrue(world.getEntitiesAt(new Position(0,0,0), Log.ENTITY_ID).size() > 0);
	}
	
}
