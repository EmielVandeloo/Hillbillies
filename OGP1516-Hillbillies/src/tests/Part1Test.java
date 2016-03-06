package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Unit;

public class Part1Test {

	private Unit validUnit;
	
	@Before
	public void setUpMutableFixture() {
		Unit validUnit = new Unit("Harry", 50, 50, 50, 50, new int[] {5,5,5}, true);
	}

}
