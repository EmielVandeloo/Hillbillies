package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import hillbillies.program.TaskFactory;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statement.Statement;
import hillbillies.world.Coordinate;
import hillbillies.world.Cube;
import hillbillies.world.Position;
import ogp.framework.util.ModelException;

public class StatementTest {
	
	public Unit mainUnit;
	
	public World world;
	public Facade facade;
	public TaskFactory factory;
	public Scheduler scheduler;
	public SourceLocation src = new SourceLocation(5, 5);

	@Before
	public void setUp() throws Exception {
		facade = new Facade();
		factory = new TaskFactory();
		
		int[][][] types = new int[10][10][5];
		types[1][1][4] = Cube.ROCK.getId();
		types[1][2][2] = Cube.ROCK.getId();
		types[1][1][1] = Cube.ROCK.getId();
		types[5][5][1] = Cube.WORKBENCH.getId();
		types[7][7][1] = Cube.WORKBENCH.getId();
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		
		mainUnit = world.createRandomUnit(false);
		mainUnit.setPosition(new Coordinate(0, 0, 0).toCenter());
		mainUnit.setStrength(200);
		mainUnit.setAgility(200);
		mainUnit.setWeight(5);
		
		scheduler = mainUnit.getScheduler();
	}
	
	public Task getDefaultTask(Statement statement) {
		List<Task> tasks = factory.createTasks("Task", 20, statement, new ArrayList<int[]>());
		return tasks.get(0);
	}
	
	private static void advanceTimeFor(IFacade facade, World world, double time, double step) 
			throws ModelException {
		
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			facade.advanceTime(world, step);
		facade.advanceTime(world, time - n * step);
	}
	
	
	// STATEMENTS

	@Test
	public void conditional_true() {
		Task task = getDefaultTask(
				factory.createIf(
						factory.createTrue(src), 
						factory.createMoveTo(
								factory.createLiteralPosition(1, 0, 0, src), 
								src), 
						factory.createMoveTo(
								factory.createLiteralPosition(0, 1, 0, src), 
								src), 
						src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(1, 0, 0).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void conditional_false() {
		Task task = getDefaultTask(
				factory.createIf(
						factory.createFalse(src), 
						factory.createMoveTo(
								factory.createLiteralPosition(1, 0, 0, src), 
								src), 
						factory.createMoveTo(
								factory.createLiteralPosition(0, 1, 0, src), 
								src), 
						src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(0, 1, 0).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void loop() {
		Task task = getDefaultTask(
				factory.createWhile(
						factory.createTrue(src), 
						factory.createMoveTo(
								factory.createNextToPosition(
										factory.createHerePosition(src), 
										src), 
								src), 
						src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		Position lastPosition = mainUnit.getPosition();
		for (int i = 0; i < 50; i++) {
			try {
				advanceTimeFor(facade, world, 3, 0.1);
			} catch (ModelException e) {}
			
			assertTrue(mainUnit.getPosition() != lastPosition);
			lastPosition = mainUnit.getPosition();
		}
	}
	
	@Test
	public void queue() {
		List<Statement> body = new ArrayList<>();
		body.add(factory.createMoveTo(
				factory.createLiteralPosition(0, 2, 0, src), 
				src));
		body.add(factory.createMoveTo(
				factory.createLiteralPosition(1, 2, 1, src), 
				src));
		
		Task task = getDefaultTask(
				factory.createSequence(body, src));
	
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 50, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(1, 2, 1).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void print() {}
	
	@Test
	public void void_() {}
	
	@Test
	public void break_() {
		List<Statement> body = new ArrayList<>();
		body.add(factory.createBreak(src));
		body.add(factory.createMoveTo(
				factory.createLiteralPosition(1, 0, 0, src), src));
		
		Task task = getDefaultTask(
				factory.createWhile(
						factory.createTrue(src), 
						factory.createSequence(body, src), 
						src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 5, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(0, 0, 0).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void assignment_boolean() {
		// TODO failure
		
		List<Statement> body = new ArrayList<>();
		body.add(factory.createAssignment(
				"boolean_variable", 
				factory.createIsPassable(
						factory.createLiteralPosition(1, 0, 0, src), 
						src), 
				src));
		body.add(factory.createIf(
				factory.createReadVariable(
						"boolean_variable", 
						src), 
				factory.createMoveTo(
						factory.createLiteralPosition(1, 0, 0, src), 
						src), 
				factory.createMoveTo(
						factory.createLiteralPosition(0, 1, 0, src), 
						src), 
				src));
		
		Task task = getDefaultTask(
				factory.createSequence(body, src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(1, 0, 0).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void assignment_position() {
		// TODO failure
		
		List<Statement> body = new ArrayList<>();
		body.add(factory.createAssignment(
				"position_variable", 
				factory.createLiteralPosition(1, 0, 0, src), 
				src));
		body.add(factory.createMoveTo(
				factory.createReadVariable(
						"position_variable", 
						src), 
				src));
		
		Task task = getDefaultTask(
				factory.createSequence(body, src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(1, 0, 0).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void assignment_unit() {
		// TODO failure
		
		Unit opponent = world.createRandomUnit(false);
		opponent.setPosition(new Coordinate(1, 1, 0).toCenter());
		
		List<Statement> body = new ArrayList<>();
		body.add(factory.createAssignment(
				"unit_variable", 
				factory.createEnemy(src), 
				src));
		body.add(factory.createMoveTo(
				factory.createPositionOf(
				factory.createReadVariable(
						"unit_variable", 
						src), 
				src), src));
		
		Task task = getDefaultTask(
				factory.createSequence(body, src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(1, 1, 0).toCenter(), mainUnit.getPosition());
	}
	
	
	// ACTIONS
	
	@Test
	public void attack() throws Exception {
		// TODO error
		
		Unit opponent = world.createRandomUnit(false);
		
		opponent.setPosition(new Coordinate(1, 1, 0).toCenter());
		opponent.setStrength(5);
		opponent.setAgility(5);
		
		for (int i = 0; i < 6; i++) {
			Task task = getDefaultTask(
					factory.createAttack(
							factory.createEnemy(src), 
							src));
			scheduler.addToNotAssignedTasks(task);
			scheduler.assignTopPriorityTask(mainUnit);
			
			try {
				advanceTimeFor(facade, world, 10, 0.1);
			} catch (ModelException e) {}
		}
		
		assertTrue(opponent.getNbHitPoints() < opponent.getMaxNbHitPoints());
	}
	
	@Test
	public void follow() throws Exception {
		// TODO error
		
		Unit opponent = world.createRandomUnit(false);
		opponent.setPosition(new Coordinate(0, 4, 0).toCenter());
		
		Task task = getDefaultTask(
				factory.createFollow(
						factory.createEnemy(src), 
						src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 0.5, 0.1);
		} catch (ModelException e) {}
		assertFalse(new Coordinate(0, 0, 0).toCenter().equals(mainUnit.getPosition()));
		opponent.moveTo(new Coordinate(2, 4, 0).toCenter());
		try {
			advanceTimeFor(facade, world, 30, 0.1);
		} catch (ModelException e) {}
		assertEquals(opponent.getPosition(), mainUnit.getPosition());
	}
	
	@Test
	public void moveTo() {
		Task task = getDefaultTask(
				factory.createMoveTo(
						factory.createLiteralPosition(1, 0, 0, src), 
						src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(1, 0, 0).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void work() {
		Task task = getDefaultTask(
				factory.createWork(
						factory.createLiteralPosition(1, 1, 1, src), 
						src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		assertTrue(world.getAt(new Coordinate(1, 1, 1).toCenter()) == Cube.AIR);
	}

}
