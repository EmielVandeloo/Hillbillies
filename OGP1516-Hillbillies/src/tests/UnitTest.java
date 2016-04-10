package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.world.Cube;
import hillbillies.world.Position;
import ogp.framework.util.ModelException;

public class UnitTest {

	private Facade facade;

	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	private static Unit terminatedUnit, validUnitInWorld, veryStrongUnitInWorld,
    veryWeakUnitInWorld, validUnitInWorldWithDefault, farAwayUnitInWorld;
	private World world;

	@Before
	public void setUp() throws ModelException {
		int[][][] types = new int[5][5][5];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_TREE;
		types[1][1][2] = TYPE_WORKSHOP;
		this.facade = new Facade();
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		terminatedUnit = new Unit(new int[] {0,0,0}, "AAA", 50, 50, 50, 50, false);
		facade.addUnit(terminatedUnit, world);
		terminatedUnit.terminate();
		validUnitInWorld = new Unit(new int[] {0,0,0}, "BBB", 50, 50, 50, 50, false);
		facade.addUnit(validUnitInWorld, world);
		veryStrongUnitInWorld = new Unit(new int[] {1,0,1}, "CCC", 100, 100, 100, 100, false);
		veryStrongUnitInWorld.setWeight(200);
		veryStrongUnitInWorld.setAgility(200);
		veryStrongUnitInWorld.setStrength(200);
		veryStrongUnitInWorld.setToughness(200);
		veryStrongUnitInWorld.setNbHitPoints(veryStrongUnitInWorld.getMaxNbHitPoints());
		veryStrongUnitInWorld.setNbHitPoints(veryStrongUnitInWorld.getMaxNbHitPoints());
		facade.addUnit(veryStrongUnitInWorld, world);
		veryWeakUnitInWorld = new Unit(new int[] {1,0,0}, "DDD", 50, 50, 50, 50, false);
		veryWeakUnitInWorld.setAgility(21);
		veryWeakUnitInWorld.setToughness(21);
		veryWeakUnitInWorld.setStrength(21);
		veryWeakUnitInWorld.setWeight(21);
		veryWeakUnitInWorld.setNbHitPoints(veryWeakUnitInWorld.getMaxNbHitPoints());
		veryWeakUnitInWorld.setNbStaminaPoints(veryWeakUnitInWorld.getMaxNbStaminaPoints());
		facade.addUnit(veryWeakUnitInWorld, world);
		validUnitInWorldWithDefault = new Unit(new int[] {1,0,0}, "EEE", 50, 50, 50, 50, true);
		farAwayUnitInWorld = new Unit(new int[] {4,4,0}, "EEE", 50, 50, 50, 50, false);
		facade.addUnit(farAwayUnitInWorld, world);
	}
	
	@Test
	public void Constructor_LegalCase() {
		assertEquals(world, validUnitInWorld.getWorld());
		assertEquals("BBB", validUnitInWorld.getName());
		assertEquals(50, validUnitInWorld.getStrength());
		assertEquals(50, validUnitInWorld.getAgility());
		assertEquals(50, validUnitInWorld.getWeight());
		assertEquals(50, validUnitInWorld.getToughness());
		assertTrue(validUnitInWorld.getPosition().equals(new Position(0.5,0.5,0.5)));
		assertFalse(validUnitInWorld.doesDefaultBehaviour());
		assertTrue(validUnitInWorldWithDefault.doesDefaultBehaviour());
		assertEquals(50, validUnitInWorld.getNbHitPoints());
		assertEquals(50, validUnitInWorld.getNbStaminaPoints());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NameWithoutCapital() throws Exception {
		new Unit(new int[] {5,5,5}, "aaa", 50, 50, 50, 50, true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NameTooShort() throws Exception {
		new Unit(new int[] {5,5,5}, "A", 50, 50, 50, 50, true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NullName() throws Exception {
		new Unit(new int[] {5,5,5}, null, 50, 50, 50, 50, true);
	}
	
	@Test
	public void Constructor_NameWithQuotes() {
		Unit unit = new Unit(new int[] {5,5,5}, "A\"A\'A", 50, 50, 50, 50, true);
		assertEquals("A\"A\'A", unit.getName());
	}
	
	@Test
	public void Constructor_NameWithSpaces() {
		Unit unit = new Unit(new int[] {5,5,5}, "A A A", 50, 50, 50, 50, true);
		assertEquals("A A A", unit.getName());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NameWithIllegalCharacters() throws Exception {
		new Unit(new int[] {5,5,5}, "A@יחא", 50, 50, 50, 50, true);
	}
	
	@Test
	public void Constructor_TooLowAttributeValues() {
		Unit unit = new Unit(new int[] {5,5,5}, "AAA", 24, 10, 0, -10, true);
		assertEquals(25, unit.getStrength());
		assertEquals(25, unit.getAgility());
		assertEquals(25, unit.getWeight());
		assertEquals(25, unit.getToughness());
	}
	
	@Test
	public void Constructor_TooHighAttributeValues() {
		Unit unit = new Unit(new int[] {5,5,5}, "AAA", 101, 200, Integer.MAX_VALUE, Integer.MAX_VALUE, true);
		assertEquals(100, unit.getStrength());
		assertEquals(100, unit.getAgility());
		assertEquals(100, unit.getWeight());
		assertEquals(100, unit.getToughness());
	}
	
	@Test
	public void Constructor_WeightInViewOfStrengthAndAgility() {
		Unit unit = new Unit(new int[] {5,5,5}, "AAA", 75, 75, 25, 50, true);
		assertEquals(75, unit.getWeight());
	}
	
	@Test(expected=NullPointerException.class)
	public void Constructor_NullPosition() throws Exception {
		new Unit(null, "AAA", 50, 50, 50, 50, true);
	}
	
	@Test
	public void terminate_EffectiveUnit() {
		World formerWorld = validUnitInWorld.getWorld();
		Faction formerFaction = validUnitInWorld.getFaction();
		validUnitInWorld.terminate();
		assertTrue(validUnitInWorld.isTerminated());
	    assertFalse(formerWorld.hasAsEntity(validUnitInWorld));
	    assertFalse(formerFaction.hasAsUnit(validUnitInWorld));
		assertNull(validUnitInWorld.getWorld());
		assertNull(validUnitInWorld.getFaction());
	}
	
	@Test
	public void terminate_TerminatedUnit() {
		terminatedUnit.terminate();
		assertTrue(terminatedUnit.isTerminated());
		assertNull(terminatedUnit.getWorld());
		assertNull(terminatedUnit.getFaction());
	}
	
	@Test
	public void setName_LegalName() {
		validUnitInWorld.setName("Emiel");
		assertEquals("Emiel", validUnitInWorld.getName());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void setName_NameWithoutCapital() {
		validUnitInWorld.setName("emiel");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void setName_NameTooShort() {
		validUnitInWorld.setName("E");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void setName_NullName() {
		validUnitInWorld.setName(null);
	}
	
	@Test
	public void setName_NameWithQuotes() {
		validUnitInWorld.setName("E\"m\'i\'\"\'el");
		assertEquals("E\"m\'i\'\"\'el", validUnitInWorld.getName());
	}
	
	@Test
	public void setName_NameWithSpaces() {
		validUnitInWorld.setName("E m i e l");
		assertEquals("E m i e l", validUnitInWorld.getName());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void setName_NameWithIllegalCharacters() {
		validUnitInWorld.setName("Em@ייi#l");
	}
	
	@Test
	public void setStrength_LegalCase() {
		validUnitInWorld.setStrength(200);
		assertEquals(200, validUnitInWorld.getStrength());
	}
	
	@Test
	public void setStrength_StrengthTooHigh() {
		validUnitInWorld.setStrength(201);
		assertEquals(200, validUnitInWorld.getStrength());
	}
	
	@Test
	public void setStrength_StrengthTooLow() {
		validUnitInWorld.setStrength(0);
		assertEquals(1, validUnitInWorld.getStrength());
	}
	
	@Test
	public void setAgility_LegalCase() {
		validUnitInWorld.setAgility(200);
		assertEquals(200, validUnitInWorld.getAgility());
	}
	
	@Test
	public void setAgility_AgilityTooHigh() {
		validUnitInWorld.setAgility(201);
		assertEquals(200, validUnitInWorld.getAgility());
	}
	
	@Test
	public void setAgility_AgilityTooLow() {
		validUnitInWorld.setAgility(0);
		assertEquals(1, validUnitInWorld.getAgility());
	}
	
	@Test
	public void setWeight_LegalCase() {
		validUnitInWorld.setWeight(60);
		assertEquals(60, validUnitInWorld.getWeight());
	}
	
	@Test
	public void setWeight_WeightTooHigh() {
		validUnitInWorld.setWeight(201);
		assertEquals(200, validUnitInWorld.getWeight());
	}
	
	@Test
	public void setWeight_WeightTooLow() {
		validUnitInWorld.setWeight(20);
		assertEquals(50, validUnitInWorld.getWeight());
	}
	
	@Test
	public void setToughness_LegalCase() {
		validUnitInWorld.setToughness(200);
		assertEquals(200, validUnitInWorld.getToughness());
	}
	
	@Test
	public void setToughness_ToughnessTooHigh() {
		validUnitInWorld.setToughness(201);
		assertEquals(200, validUnitInWorld.getToughness());
	}
	
	@Test
	public void setToughness_ToughnessTooLow() {
		validUnitInWorld.setToughness(0);
		assertEquals(1, validUnitInWorld.getToughness());
	}
	
	@Test
	public void getMaxNbHitPoints() {
		assertEquals(50, validUnitInWorld.getMaxNbHitPoints());
	}
	
	@Test
	public void getMaxNbStaminaPoints() {
		assertEquals(50, validUnitInWorld.getMaxNbStaminaPoints());
	}
	
	@Test
	public void startStopMoving() {
		validUnitInWorld.startMoving();
		assertTrue(validUnitInWorld.isMoving());
		validUnitInWorld.stopMoving();
		assertFalse(validUnitInWorld.isMoving());
	}
	
	@Test
	public void moveTo_ReachablePosition() throws ModelException {
		validUnitInWorld.moveTo(new Position(2,2,2));
		assertTrue(validUnitInWorld.isMoving());
		advanceTimeFor(facade, world, 20, 0.2);
		System.out.println(validUnitInWorld.getPosition().toString());
		assertTrue(validUnitInWorld.getPosition().equals(new Position(2.5,2.5,2.5)));
		assertFalse(validUnitInWorld.isMoving());
	}
	
	@Test
	public void moveTo_UnreachablePosition() throws ModelException {
		validUnitInWorld.moveTo(new Position(2,2,3));
		advanceTimeFor(facade, world, 50, 0.2);
		assertTrue(validUnitInWorld.getPosition().equals(new Position(0.5,0.5,0.5)));
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void moveTo_UneffectivePosition() {
		validUnitInWorld.moveTo(null);
	}
	
	@Test
	public void moveToAdjacent_AccrossWorldBoundaries() throws ModelException {
		validUnitInWorld.moveToAdjacent(-1, 0, -1);
		advanceTimeFor(facade, world, 10, 0.2);
		assertTrue(validUnitInWorld.getPosition().equals(new Position(0.5,0.5,0.5)));
	}
	
	@Test
	public void moveToAdjacent_LegalCase() throws ModelException {
		validUnitInWorld.moveToAdjacent(1,0,1);
		advanceTimeFor(facade, world, 10, 0.2);
		System.out.println(validUnitInWorld.getPosition().toString());
		assertTrue(validUnitInWorld.getPosition().equals(new Position(1.5,0.5,1.5)));
	}
	
	@Test
	public void moveToAdjacent_ToSolidCube() throws ModelException {
		Position pos = new Position(1.3, 2.8, 3);
		System.out.println(pos.getCenterPosition().toString());
		validUnitInWorld.moveToAdjacent(1, 1, 0);
		advanceTimeFor(facade, world, 10, 0.2);
		assertTrue(validUnitInWorld.getPosition().equals(new Position(0.5,0.5,0.5)));
	}
	
	@Test
	public void startStopSprinting() {
		validUnitInWorld.startSprinting();
		assertTrue(validUnitInWorld.isSprinting());
		validUnitInWorld.stopSprinting();
		assertFalse(validUnitInWorld.isSprinting());
	}
	
	@Test
	public void staminaDrain() throws ModelException {
		validUnitInWorld.moveTo(new Position(2,2,2));
		validUnitInWorld.startSprinting();
		advanceTimeFor(facade, world, 20, 0.2);
		assertTrue(validUnitInWorld.getNbStaminaPoints() < validUnitInWorld.getMaxNbStaminaPoints());
	}
	
	@Test
	public void startStopWorking() {
		validUnitInWorld.startWorking();
		assertTrue(validUnitInWorld.isWorking());
		validUnitInWorld.stopWorking();
		assertFalse(validUnitInWorld.isWorking());
	}
	
	@Test
	public void workAt_Rock() throws ModelException {
		veryStrongUnitInWorld.workAt(1, 1, 0);
		assertTrue(veryStrongUnitInWorld.isWorking());
		assert veryStrongUnitInWorld.getWorld()!= null;
		advanceTimeFor(facade, world, 20, 0.2);
		assertTrue(world.getAt(new Position(1,1,0)) == Cube.byId(TYPE_AIR));
		assertTrue(world.getAt(new Position (1,1,1)) == Cube.byId(TYPE_AIR));
	}
	
	@Test
	public void workAt_Tree() throws ModelException {
		veryStrongUnitInWorld.workAt(1, 1, 1);
		advanceTimeFor(facade, world, 20, 0.2);
		assertTrue(world.getAt(new Position(1,1,1)) == Cube.byId(TYPE_AIR));
		assertTrue(world.getAt(new Position(1,1,0)) == Cube.byId(TYPE_ROCK));
	}
	
	// TODO workAt Workshop, Air
	
	@Test
	public void startStopAttacking() {
		validUnitInWorld.startAttacking();
		assertTrue(validUnitInWorld.isAttacking());
		validUnitInWorld.stopAttacking();
		assertFalse(validUnitInWorld.isAttacking());
	}
	
	@Test
	public void attack_NotInNeighbouringCubes() {
		validUnitInWorld.attack(farAwayUnitInWorld);
		assertFalse(validUnitInWorld.isAttacking());
		assertFalse(farAwayUnitInWorld.isDefending());
	}
	
//	@Test
//	public void attack_WithDamage() throws ModelException {
//		int strongUnitExpPoints = veryStrongUnitInWorld.getNbExperiencePoints();
//		int weakUnitExpPoints = veryWeakUnitInWorld.getNbExperiencePoints();
//		veryStrongUnitInWorld.attack(veryWeakUnitInWorld);
//		assertTrue(veryStrongUnitInWorld.isAttacking());
//		assertTrue(veryWeakUnitInWorld.isDefending());
//		advanceTimeFor(facade, world, 1.2, 0.2);
//		assertTrue(veryWeakUnitInWorld.getNbHitPoints() < veryWeakUnitInWorld.getMaxNbHitPoints());
//		assertTrue(veryStrongUnitInWorld.getNbExperiencePoints() > strongUnitExpPoints);
//		assertTrue(veryWeakUnitInWorld.getNbExperiencePoints() == weakUnitExpPoints);
//	}
	
//	@Test
//	public void attack_WithoutDamage() throws ModelException {
//		int strongUnitExpPoints = veryStrongUnitInWorld.getNbExperiencePoints();
//		int weakUnitExpPoints = veryWeakUnitInWorld.getNbExperiencePoints();
//		veryWeakUnitInWorld.attack(veryStrongUnitInWorld);
//		advanceTimeFor(facade, world, JobStat.ATTACK + 0.2, 0.2);
//		assertTrue(veryStrongUnitInWorld.getNbHitPoints() == veryStrongUnitInWorld.getMaxNbHitPoints());
//		assertTrue(veryStrongUnitInWorld.getNbExperiencePoints() > strongUnitExpPoints);
//		assertTrue(veryWeakUnitInWorld.getNbExperiencePoints() == weakUnitExpPoints);
//	}
	
	@Test
	public void startStopResting() {
		validUnitInWorld.startResting();
		assertTrue(validUnitInWorld.isResting());
		validUnitInWorld.stopResting();
		assertFalse(validUnitInWorld.isResting());
	}
	
	@Test
	public void rest() throws ModelException {
		veryStrongUnitInWorld.setNbStaminaPoints(veryStrongUnitInWorld.getMaxNbStaminaPoints() - 20);
		veryStrongUnitInWorld.setNbHitPoints(veryStrongUnitInWorld.getMaxNbHitPoints() - 20);
		veryStrongUnitInWorld.rest();
		advanceTimeFor(facade, world, 20, 0.2);
		assertEquals(veryStrongUnitInWorld.getMaxNbStaminaPoints(), veryStrongUnitInWorld.getNbStaminaPoints());
		assertEquals(veryStrongUnitInWorld.getMaxNbHitPoints(), veryStrongUnitInWorld.getNbHitPoints());
	}
	
	@Test
	public void startStopDefaultBehaviour() {
		validUnitInWorld.startDefaultBehaviour();
		assertTrue(validUnitInWorld.doesDefaultBehaviour());
		validUnitInWorld.stopDefaultBehaviour();
		assertFalse(validUnitInWorld.doesDefaultBehaviour());
	}
	
	@Test
	public void defaultBehaviour() throws ModelException {
		validUnitInWorld.startDefaultBehaviour();
		advanceTimeFor(facade, world, 0.2, 0.2);
		assertTrue(validUnitInWorld.isWorking() || validUnitInWorld.isMoving() || validUnitInWorld.isResting() ||
				      validUnitInWorld.isAttacking());
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
