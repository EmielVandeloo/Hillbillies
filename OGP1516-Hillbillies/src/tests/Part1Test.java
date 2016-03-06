package tests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import hillbillies.model.Unit;
import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import ogp.framework.util.Util;

public class Part1Test {

	private static Unit validUnit;
	
	@Before
	public void setUpMutableFixture() {
		validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, true);
	}

	@Test
	public void Constructor_LegalCase() {
		assertEquals("Harry", validUnit.getName());
		assertEquals(50, validUnit.getStrength());
		assertEquals(50, validUnit.getAgility());
		assertEquals(50, validUnit.getWeight());
		assertEquals(50, validUnit.getToughness());
		assertTrue(Util.fuzzyEquals(5.5, validUnit.getUnitPosition()[0]));
		assertTrue(Util.fuzzyEquals(5.5, validUnit.getUnitPosition()[1]));
		assertTrue(Util.fuzzyEquals(5.5, validUnit.getUnitPosition()[2]));
		assertTrue(validUnit.doesDefaultBehaviour());
		assertEquals(50, validUnit.getNbHitPoints());
		assertEquals(50, validUnit.getNbStaminaPoints());
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
	public void Constructor_WeightInViewOfStrengtAndAgility() {
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
	
	@Test
	public void setStrength_LegalCase() {
		validUnit.setStrength(200);
		assertEquals(200, validUnit.getStrength());
	}
	
	@Test
	public void setStrength_StrengthTooHigh() {
		validUnit.setStrength(201);
		assertEquals(200, validUnit.getStrength());
	}
	
	@Test
	public void setStrength_StrengthTooLow() {
		validUnit.setStrength(0);
		assertEquals(1, validUnit.getStrength());
	}
	
	// isValidStrength, isValidAttribute, makeValidAttribute tested in setStrength tests.
	
	@Test
	public void setAgility_LegalCase() {
		validUnit.setAgility(200);
		assertEquals(200, validUnit.getAgility());
	}
	
	@Test
	public void setAgility_AgilityTooHigh() {
		validUnit.setAgility(201);
		assertEquals(200, validUnit.getAgility());
	}
	
	@Test
	public void setAgility_AgilityTooLow() {
		validUnit.setAgility(0);
		assertEquals(1, validUnit.getAgility());
	}
	
	// isValidAgility, isValidAttribute, makeValidAttribute tested in setAgility tests.
	
	@Test
	public void setWeight_LegalCase() {
		validUnit.setWeight(60);
		assertEquals(60, validUnit.getWeight());
	}
	
	@Test
	public void setWeight_WeightTooHigh() {
		validUnit.setWeight(201);
		assertEquals(200, validUnit.getWeight());
	}
	
	@Test
	public void setWeight_WeightTooLow() {
		validUnit.setWeight(20);
		assertEquals(50, validUnit.getWeight());
	}
	
	// canHaveAsWeight, isValidAttribute, makeValidWeight tested in setWeight tests.
	
	@Test
	public void setToughness_LegalCase() {
		validUnit.setToughness(200);
		assertEquals(200, validUnit.getToughness());
	}
	
	@Test
	public void setToughness_ToughnessTooHigh() {
		validUnit.setToughness(201);
		assertEquals(200, validUnit.getToughness());
	}
	
	@Test
	public void setToughness_ToughnessTooLow() {
		validUnit.setToughness(0);
		assertEquals(1, validUnit.getToughness());
	}
	
	// isValidToughness, isValidAttribute, makeValidAttribute tested in setToughness tests.
	
	@Test
	public void isValidNbHitpoints_TrueCase() {
		assertTrue(validUnit.isValidNbHitPoints(validUnit.getNbHitPoints()));
	}
	
	@Test
	public void isValidNbHitpoints_FalseCase() {
		assertFalse(validUnit.isValidNbHitPoints(51));
	}
	
	@Test
	public void isValidNbStaminaPoints_TrueCase() {
		assertTrue(validUnit.isValidNbStaminaPoints(validUnit.getNbStaminaPoints()));
	}
	
	@Test
	public void isValidNbStaminaPoints_FalseCase() {
		assertFalse(validUnit.isValidNbStaminaPoints(51));
	}
	
	@Test
	public void testRest() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, false);
		validUnit.rest();
		
		assertEquals(true, validUnit.isResting());
		assertEquals(false, validUnit.isWorking());
		assertEquals(false, validUnit.isAttacking());
	}
	
	@Test
	public void testMovement() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, false);
		
		validUnit.moveToAdjacent(1, 1, 1);
		assertEquals(true, validUnit.isWalking());
	}
	
	@Test
	public void testStartDefaultBehaviour() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, false);
		
		validUnit.startDefaultBehaviour();
		assertEquals(true, validUnit.doesDefaultBehaviour());
	}
	
	@Test
	public void testStopDefaultBehaviour() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, true);

		validUnit.stopDefaultBehaviour();
		assertEquals(false, validUnit.doesDefaultBehaviour());
	}
	
	@Test
	public void testWork() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, false);
		validUnit.work();
		
		assertEquals(false, validUnit.isResting());
		assertEquals(true, validUnit.isWorking());
		assertEquals(false, validUnit.isAttacking());
	}
	
	@Test
	public void testAttack() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, false);
		Unit validUnit2 = new Unit("Harry", 50, 50, 50, 50, new int[] {5,6,5}, false);
		validUnit.attack(validUnit2);
		
		assertEquals(false, validUnit.isResting());
		assertEquals(false, validUnit.isWorking());
		assertEquals(true, validUnit.isAttacking());
	}
	
	@Test
	public void testMoveToAdjacent() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,4,3}, false);
		validUnit.moveToAdjacent(1, 0, -1);
		
		double speed = validUnit.getCurrentSpeed();
		double distance = Math.sqrt(2);
		double time = distance / speed;
		
		advanceTimeFor(validUnit, time, 0.05);
		assertDoublePositionEquals(6.5, 4.5, 2.5, validUnit.getUnitPosition());
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
	
	@Test
	public void testMoveTo() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,4,3}, false);
		validUnit.moveTo(new double[] {7.5, 4.5, 1.5});
		
		double speed = validUnit.getCurrentSpeed();
		double distance = Math.sqrt(2) * 2;
		double time = distance / speed;
		
		advanceTimeFor(validUnit, time, 0.05);
		assertDoublePositionEquals(7.5, 4.5, 1.5, validUnit.getUnitPosition());
	}

	@Test
	public void testStartSprinting() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, false);
		
		validUnit.startSprinting();
		assertEquals(true, validUnit.isSprinting());
	}
	
	@Test
	public void testStopSprinting() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, false);

		validUnit.stopSprinting();
		assertEquals(false, validUnit.isSprinting());
	}

}

