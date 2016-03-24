package tests;

import static org.junit.Assert.*;
import hillbillies.model.*;
import org.junit.*;

public class Part1Test {

	private static Unit validUnitWithDefault, validUnitWithoutDefault, veryStrongUnit,
	                    veryWeakUnit, sideLineUnit;
	
	@Before
	public void setUp() {
		validUnitWithDefault = new Unit("AAA", 50, 50, 50, 50, new int[] {5,5,5}, true);
		validUnitWithoutDefault = new Unit("BBB", 50, 50, 50, 50, new int[] {5,5,5}, false);
		veryStrongUnit = new Unit("CCC", 100, 100, 100, 100, new int[] {1,1,1}, false);
		veryStrongUnit.setWeight(200);
		veryStrongUnit.setAgility(200);
		veryStrongUnit.setStrength(200);
		veryStrongUnit.setToughness(200);
		veryWeakUnit = new Unit("DDD", 25, 25, 25, 25, new int[] {2,2,2}, false);
		veryWeakUnit.setAgility(1);
		veryWeakUnit.setStrength(1);
		veryWeakUnit.setToughness(1);
		veryWeakUnit.setWeight(1);
		sideLineUnit = new Unit("EEE", 50, 50, 50, 50, new int[] {0,0,0}, false);
	}

	@Test
	public void Constructor_LegalCase() {
		assertEquals("AAA", validUnitWithDefault.getName());
		assertEquals(50, validUnitWithDefault.getStrength());
		assertEquals(50, validUnitWithDefault.getAgility());
		assertEquals(50, validUnitWithDefault.getWeight());
		assertEquals(50, validUnitWithDefault.getToughness());
		assertTrue(validUnitWithDefault.getUnitPosition().positionEquals(new Position(5.5,5.5,5.5)));
		assertTrue(validUnitWithDefault.doesDefaultBehaviour());
		assertFalse(validUnitWithoutDefault.doesDefaultBehaviour());
		assertEquals(50, validUnitWithDefault.getNbHitPoints());
		assertEquals(50, validUnitWithDefault.getNbStaminaPoints());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NameWithoutCapital() throws Exception {
		new Unit("aaa", 50, 50, 50, 50, new int[] {5,5,5}, true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NameTooShort() throws Exception {
		new Unit("A", 50, 50, 50, 50, new int[] {5,5,5}, true);
	}
	
	@Test(expected=NullPointerException.class)
	public void Constructor_NullName() throws Exception {
		new Unit(null, 50, 50, 50, 50, new int[] {5,5,5}, true);
	}
	
	@Test
	public void Constructor_NameWithQuotes() {
		Unit unit = new Unit("A\"A\'A", 50, 50, 50, 50, new int[] {5,5,5}, true);
		assertEquals("A\"A\'A", unit.getName());
	}
	
	@Test
	public void Constructor_NameWithSpaces() {
		Unit unit = new Unit("A A A", 50, 50, 50, 50, new int[] {5,5,5}, true);
		assertEquals("A A A", unit.getName());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_NameWithIllegalCharacters() throws Exception {
		new Unit("A@יחא", 50, 50, 50, 50, new int[] {5,5,5}, true);
	}
	
	@Test
	public void Constructor_TooLowAttributeValues() {
		Unit unit = new Unit("AAA", 24, 10, 0, -10, new int[] {5,5,5}, true);
		assertEquals(25, unit.getStrength());
		assertEquals(25, unit.getAgility());
		assertEquals(25, unit.getWeight());
		assertEquals(25, unit.getToughness());
	}
	
	@Test
	public void Constructor_TooHighAttributeValues() {
		Unit unit = new Unit("AAA", 101, 200, Integer.MAX_VALUE, Integer.MAX_VALUE, new int[] {5,5,5}, true);
		assertEquals(100, unit.getStrength());
		assertEquals(100, unit.getAgility());
		assertEquals(100, unit.getWeight());
		assertEquals(100, unit.getToughness());
	}
	
	@Test
	public void Constructor_WeightInViewOfStrengthAndAgility() {
		Unit unit = new Unit("AAA", 75, 75, 25, 50, new int[] {5,5,5}, true);
		assertEquals(75, unit.getWeight());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_TooHighPositionCoordinate() throws Exception {
		new Unit("AAA", 50, 50, 50, 50, new int[] {5,5,50}, true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Constructor_TooLowPositionCoordinate() throws Exception {
		new Unit("AAA", 50, 50, 50, 50, new int[] {-5,5,5}, true);
	}
	
	@Test(expected=NullPointerException.class)
	public void Constructor_NullPosition() throws Exception {
		new Unit("AAA", 50, 50, 50, 50, null, true);
	}
	
	// isValidName, setName, isValidInitialStrength, isValidInitialAgility, canHaveAsInitialWeight,
	// isValidInitialToughness, isValidInitialAttribute, makeValidInitialAttribute,
	// isValidPosition, makeValidInitialWeight, getMaxNbHitPoints, getMaxNbStaminaPoints
	// tested in constructor tests.
	
	@Test
	public void setStrength_LegalCase() {
		validUnitWithDefault.setStrength(200);
		assertEquals(200, validUnitWithDefault.getStrength());
	}
	
	@Test
	public void setStrength_StrengthTooHigh() {
		validUnitWithDefault.setStrength(201);
		assertEquals(200, validUnitWithDefault.getStrength());
	}
	
	@Test
	public void setStrength_StrengthTooLow() {
		validUnitWithDefault.setStrength(0);
		assertEquals(1, validUnitWithDefault.getStrength());
	}
	
	// isValidStrength, isValidAttribute, makeValidAttribute tested in setStrength tests.
	
	@Test
	public void setAgility_LegalCase() {
		validUnitWithDefault.setAgility(200);
		assertEquals(200, validUnitWithDefault.getAgility());
	}
	
	@Test
	public void setAgility_AgilityTooHigh() {
		validUnitWithDefault.setAgility(201);
		assertEquals(200, validUnitWithDefault.getAgility());
	}
	
	@Test
	public void setAgility_AgilityTooLow() {
		validUnitWithDefault.setAgility(0);
		assertEquals(1, validUnitWithDefault.getAgility());
	}
	
	// isValidAgility, isValidAttribute, makeValidAttribute tested in setAgility tests.
	
	@Test
	public void setWeight_LegalCase() {
		validUnitWithDefault.setWeight(60);
		assertEquals(60, validUnitWithDefault.getWeight());
	}
	
	@Test
	public void setWeight_WeightTooHigh() {
		validUnitWithDefault.setWeight(201);
		assertEquals(200, validUnitWithDefault.getWeight());
	}
	
	@Test
	public void setWeight_WeightTooLow() {
		validUnitWithDefault.setWeight(20);
		assertEquals(50, validUnitWithDefault.getWeight());
	}
	
	// canHaveAsWeight, isValidAttribute, makeValidWeight tested in setWeight tests.
	
	@Test
	public void setToughness_LegalCase() {
		validUnitWithDefault.setToughness(200);
		assertEquals(200, validUnitWithDefault.getToughness());
	}
	
	@Test
	public void setToughness_ToughnessTooHigh() {
		validUnitWithDefault.setToughness(201);
		assertEquals(200, validUnitWithDefault.getToughness());
	}
	
	@Test
	public void setToughness_ToughnessTooLow() {
		validUnitWithDefault.setToughness(0);
		assertEquals(1, validUnitWithDefault.getToughness());
	}
	
	// isValidToughness, isValidAttribute, makeValidAttribute tested in setToughness tests.
	
	@Test
	public void isValidNbHitpoints_TrueCase() {
		assertTrue(validUnitWithDefault.isValidNbHitPoints(validUnitWithDefault.getNbHitPoints()));
	}
	
	@Test
	public void isValidNbHitpoints_FalseCase() {
		assertFalse(validUnitWithDefault.isValidNbHitPoints(51));
	}
	
	@Test
	public void isValidNbStaminaPoints_TrueCase() {
		assertTrue(validUnitWithDefault.isValidNbStaminaPoints(validUnitWithDefault.getNbStaminaPoints()));
	}
	
	@Test
	public void isValidNbStaminaPoints_FalseCase() {
		assertFalse(validUnitWithDefault.isValidNbStaminaPoints(51));
	}
	
	// MOVEMENT
	
	@Test
	public void startStopMoving() {
		validUnitWithDefault.startMoving();
		assertTrue(validUnitWithDefault.isMoving());
		validUnitWithDefault.stopMoving();
		assertFalse(validUnitWithDefault.isMoving());
	}
	
	@Test
	public void moveTo_SameZ() {
		validUnitWithoutDefault.moveTo(new Position(7,3,5));
		assertTrue(validUnitWithoutDefault.isMoving());
		double speed = validUnitWithoutDefault.getCurrentSpeed();
		System.out.println("1... " + speed);
		double distance = Math.sqrt(8);
		double time = distance / speed;
		advanceTimeFor(validUnitWithoutDefault, time, 0.05);
		System.out.println("1... " + validUnitWithoutDefault.getUnitPosition().toString());
		assertTrue(validUnitWithoutDefault.getUnitPosition().positionEquals(new Position(7.5,3.5,5.5)));
		assertFalse(validUnitWithoutDefault.isMoving());
	}
	
	@Test
	public void moveTo_DifferentZ() {
		validUnitWithoutDefault.moveTo(new Position(2,5,7));
		double speed = validUnitWithoutDefault.getCurrentSpeed();
		System.out.println("2... " + speed);
		double distance = Math.sqrt(13);
		double time = distance / speed;
		advanceTimeFor(validUnitWithoutDefault, time, 0.05);
		System.out.println("2... " + validUnitWithoutDefault.getUnitPosition().toString());
		assertTrue(validUnitWithoutDefault.getUnitPosition().positionEquals(new Position(2.5,5.5,7.5)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void moveTo_IllegalPosition() {
		validUnitWithoutDefault.moveTo(new Position(50,5,5));
	}
	
	@Test
	public void moveToAdjacent_LegalCase() {
		validUnitWithoutDefault.moveToAdjacent(1, 0, -1);
		double speed = validUnitWithoutDefault.getCurrentSpeed();
		System.out.println("3... " + speed);
		double distance = Math.sqrt(2);
		double time = distance / speed;
		advanceTimeFor(validUnitWithoutDefault, time, 0.05);
		System.out.println("3... " + validUnitWithoutDefault.getUnitPosition().toString());
		assertTrue(validUnitWithoutDefault.getUnitPosition().positionEquals(new Position(6.5,5.5,4.5)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void moveToAdjacent_IllegalCase() {
		sideLineUnit.moveToAdjacent(-1, -1, -1);
	}
	
	// SPRINTING
	
	@Test
	public void startStopSprinting() {
		validUnitWithoutDefault.startSprinting();
		assertTrue(validUnitWithoutDefault.isSprinting());
		validUnitWithoutDefault.stopSprinting();
		assertFalse(validUnitWithoutDefault.isSprinting());
	}
	
	@Test
	public void staminaDrain() {
		validUnitWithoutDefault.startSprinting();
		validUnitWithoutDefault.moveToAdjacent(1, 1, 1);
		double speed = validUnitWithoutDefault.getCurrentSpeed();
		double distance = Math.sqrt(3);
		double time = distance / speed;
		advanceTimeFor(validUnitWithoutDefault, time, 0.05);
		assertNotEquals(validUnitWithoutDefault.getMaxNbStaminaPoints(), 
	                    validUnitWithoutDefault.getNbStaminaPoints());
		assertNotEquals(0, validUnitWithoutDefault.getNbStaminaPoints());
		validUnitWithoutDefault.moveTo(new Position(26,6,6));
		speed = validUnitWithoutDefault.getCurrentSpeed();
		distance = 20;
		time = distance / speed;
		advanceTimeFor(validUnitWithoutDefault, time, 0.05);
		assertEquals(0, validUnitWithoutDefault.getNbStaminaPoints());
	}
	
	// WORKING
	
	@Test
	public void startStopWorking() {
		validUnitWithoutDefault.startWorking();
		assertTrue(validUnitWithoutDefault.isWorking());
		validUnitWithoutDefault.stopWorking();
		assertFalse(validUnitWithoutDefault.isWorking());
	}
	
	@Test
	public void work() {
		validUnitWithoutDefault.work();
		assertFalse(validUnitWithoutDefault.isMoving());
		assertFalse(validUnitWithoutDefault.isResting());
		assertTrue(validUnitWithoutDefault.isWorking());
		assertFalse(validUnitWithoutDefault.isAttacking());
	}
	
	// ATTACKING
	
	@Test
	public void startStopAttacking() {
		validUnitWithoutDefault.startAttacking();
		assertTrue(validUnitWithoutDefault.isAttacking());
		validUnitWithoutDefault.stopAttacking();
		assertFalse(validUnitWithoutDefault.isAttacking());
	}
	
	@Test
	public void attack_LegalCase() {
		int veryStrongUnitNbHitPoints = veryStrongUnit.getNbHitPoints();
		int veryWeakUnitNbHitPoints = veryWeakUnit.getNbHitPoints();
		veryStrongUnit.attack(veryWeakUnit);
		assertFalse(veryStrongUnit.isMoving());
		assertFalse(veryWeakUnit.isMoving());
		assertFalse(veryStrongUnit.isResting());
		assertFalse(veryWeakUnit.isResting());
		assertFalse(veryStrongUnit.isWorking());
		assertFalse(veryWeakUnit.isWorking());
		assertTrue(veryStrongUnit.isAttacking());
		advanceTimeFor(veryStrongUnit, JobStat.ATTACK, 0.05);
		advanceTimeFor(veryWeakUnit, JobStat.ATTACK, 0.05);
		assertNotEquals(veryWeakUnitNbHitPoints, veryWeakUnit.getNbHitPoints());
		veryWeakUnit.attack(veryStrongUnit);
		advanceTimeFor(veryStrongUnit, JobStat.ATTACK, 0.05);
		advanceTimeFor(veryWeakUnit, JobStat.ATTACK, 0.05);
		assertEquals(veryStrongUnitNbHitPoints, veryStrongUnit.getNbHitPoints());
	}
	
	@Test
	public void attack_NotInAdjacentCubes() {
		veryStrongUnit.attack(validUnitWithDefault);
		assertFalse(veryStrongUnit.isAttacking());
	}
	
	// RESTING
	
	@Test
	public void startStopResting() {
		validUnitWithoutDefault.startResting();
		assertTrue(validUnitWithoutDefault.isResting());
		validUnitWithoutDefault.stopResting();
		assertFalse(validUnitWithoutDefault.isResting());
	}
	
	@Test
	public void rest() {
		int validUnitWithoutDefaultNbStaminaPoints = validUnitWithoutDefault.getNbStaminaPoints();
		validUnitWithoutDefault.startSprinting();
		validUnitWithoutDefault.moveToAdjacent(1, 1, 1);
		double speed = validUnitWithoutDefault.getCurrentSpeed();
		double distance = Math.sqrt(3);
		double time = distance / speed;
		advanceTimeFor(validUnitWithoutDefault, time, 0.05);
		assertNotEquals(validUnitWithoutDefaultNbStaminaPoints, validUnitWithoutDefault.getNbStaminaPoints());
		validUnitWithoutDefault.rest();
		advanceTimeFor(validUnitWithoutDefault, JobStat.REST * validUnitWithoutDefaultNbStaminaPoints, 0.05);
		assertEquals(validUnitWithoutDefaultNbStaminaPoints, validUnitWithoutDefault.getNbStaminaPoints());
	}
	
	// DEFAULT BEHAVIOUR
	
	@Test
	public void startStopDefaultBehaviour() {
		validUnitWithDefault.startDefaultBehaviour();
		assertTrue(validUnitWithDefault.doesDefaultBehaviour());
		validUnitWithDefault.stopDefaultBehaviour();
		assertFalse(validUnitWithDefault.doesDefaultBehaviour());
	}
	
	@Test
	public void defaultBehaviour() {
		advanceTimeFor(validUnitWithDefault, 1.0, 0.05);
		assertTrue(validUnitWithDefault.isMoving() || validUnitWithDefault.isWorking() ||
				validUnitWithDefault.isResting());
	}
	
	@Test
	public void isAdjacentToOrSame_SameCube() {
		assertTrue(Unit.isAdjacentToOrSame(validUnitWithDefault.getUnitPosition(), 
				            validUnitWithoutDefault.getUnitPosition()));
	}
	
	@Test
	public void isAdjacentToOrSame_NeighbouringCube() {
		assertTrue(Unit.isAdjacentToOrSame(veryStrongUnit.getUnitPosition(), 
				            veryWeakUnit.getUnitPosition()));
	}
	
	@Test
	public void isAdjacentToOrSame_FalseCase() {
		assertFalse(Unit.isAdjacentToOrSame(veryWeakUnit.getUnitPosition(), 
				            sideLineUnit.getUnitPosition()));
	}
	
	/**
	 * Helper method to advance time for the given unit by some time.
	 * 
	 * @param time
	 *            The time, in seconds, to advance.
	 * @param step
	 *            The step size, in seconds, by which to advance.
	 */
	private static void advanceTimeFor(Unit unit, double time, double step) {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++) {
			unit.advanceTime(step);
		}
		unit.advanceTime(time - n * step);
	}
}

