package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.world.Position;

public class ItemEntityTest {

	public World getDefaultWorld() {
		int[][][] array = new int[3][3][3];

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int k = 0; k < array.length; k++) {
					array[i][j][k] = 0;
				}
			}
		}	

		World defaultWorld = new World(array, new TerrainChangeListener() {

			@Override
			public void notifyTerrainChanged(int x, int y, int z) {}

		});

		return defaultWorld;
	}

	public Log getDefaultLog() {
		return new Log(getDefaultWorld(), new Position().getCenterPosition());
	}


	// CONSTRUCTOR

	@Test
	public void Constructor_LegalCase() {
		World world = getDefaultWorld();
		Position position = new Position().getCenterPosition();
		Log log = new Log(world, position);

		assertEquals(world, log.getWorld());
		assertEquals(position, log.getPosition());
	}

	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NullWorld() {
		new Log(null, new Position());
	}

	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NullPosition() {
		new Log(getDefaultWorld(), null);
	}


	// ITEM

	@SuppressWarnings("static-access")
	@Test
	public void validItemWeight() {
		Log log = getDefaultLog();

		assertTrue(log.getWeight() > log.MIN_WEIGHT);
		assertTrue(log.getWeight() < log.MAX_WEIGHT);

	}

	@Test
	public void spawn() {
		World world = getDefaultWorld();
		Log log = new Log(world, new Position().getCenterPosition());
		log.spawn();

		assertTrue(world.hasAsEntity(log));
	}

	@Test
	public void despawn() {
		World world = getDefaultWorld();
		Log log = new Log(world, new Position().getCenterPosition());
		log.spawn();
		log.despawn();

		assertTrue(! world.hasAsEntity(log));
	}

	@Test
	public void drop_OnTile() {
		World world = getDefaultWorld();
		Position position = new Position(new int[] {1,1,1});

		for (int i = 0; i < 50; i++) {
			Log.drop(world, position);
		}

		assertTrue(world.getEntitiesAt(position, Log.getEntityId()).size() > 0);
	}

	@Test
	public void drop_Amount() {
		World world = getDefaultWorld();
		Position position = new Position(new int[] {1,1,1});

		for (int i = 0; i < 50; i++) {
			Log.drop(world, position);
		}

		assertTrue(world.getAllEntitiesOf(Log.getEntityId()).size() > 0);
	}

	@Test
	public void advanceTime_Fall() {
		World world = getDefaultWorld();
		Position position = new Position(new int[] {1,1,1});
		Position hitPosition = position.add(Position.Z, -1);
		Log log = new Log(world, position);

		world.addEntity(log);
		for (int i = 0; i < 20; i++) {
			world.advanceTime(0.1);
		}

		assertTrue(world.getEntitiesAt(hitPosition, Log.getEntityId()).size() > 0);
	}



}
