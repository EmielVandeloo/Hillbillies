package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.world.Position;

public class EntityTest {

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
	
	public Position getDefaultPosition() {
		return new Position().getCenterPosition();
	}
	
	public Log getDefaultLog() {
		return new Log(getDefaultWorld(), getDefaultPosition());
	}

	@Test
	public void Constructor_LegalCase() {
		World world = getDefaultWorld();
		Position position = getDefaultPosition();
		Log log = new Log(world, position);
		
		assertTrue(log.getWorld().equals(world));
		assertTrue(log.getPosition().equals(position));
	}
	
	@Test(expected=NullPointerException.class)
	public void Constructor_NullPosition() {
		new Log(getDefaultWorld(), null);
	}
	
	@Test
	public void setPosition_ValidPosition() {
		World world = getDefaultWorld();
		Position position = getDefaultPosition();
		Position secPos = new Position(new int[] {1,1,1}).getCenterPosition();
		Log log = new Log(world, position);
		
		log.setPosition(secPos);
		assertTrue(log.getPosition().equals(secPos));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setPosition_InValidPosition() {
		World world = getDefaultWorld();
		Position position = getDefaultPosition();
		Position secPos = new Position(new int[] {1,-1,1}).getCenterPosition();
		Log log = new Log(world, position);
		log.setPosition(secPos);
	}
	
	@Test
	public void fallBehavior_Movement() {
		World world = getDefaultWorld();
		Position position = new Position(new int[] {1,1,1}).getCenterPosition();
		Log log = new Log(world, position);
		
		for (int i = 0; i < 10; i++) {
			log.fallBehavior(0.1);
		}
		assertTrue(log.getWorld().equals(world));
	}
	
	@Test
	public void fallBehavior_Start() {
		World world = getDefaultWorld();
		Position position = new Position(new int[] {1,2,1}).getCenterPosition();
		Log log = new Log(world, position);
		
		log.startFalling();
		log.fallBehavior(0.05);
		assertTrue(log.isFalling() == true);
		assertTrue(! log.getPosition().equals(position));
	}
	
	@Test
	public void fallBehavior_Stop() {
		World world = getDefaultWorld();
		Position position = new Position(new int[] {1,1,1}).getCenterPosition();
		Log log = new Log(world, position);
		
		for (int i = 0; i < 10; i++) {
			log.fallBehavior(0.1);
		}
		assertTrue(log.isFalling() == false);
	}
	
}
