package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import hillbillies.program.TaskFactory;
import hillbillies.model.Boulder;
import hillbillies.model.Log;
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
import ogp.framework.util.ModelException;

public class StatementTest {
	
	public Unit mainUnit;
	public Unit friend1;
	public Unit enemy1;
	
	public Boulder boulder1;
	public Log log1;
	
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
		types[1][2][0] = Cube.ROCK.getId();
		types[5][5][1] = Cube.WORKBENCH.getId();
		types[7][7][1] = Cube.WORKBENCH.getId();
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		
		mainUnit = world.createRandomUnit(false);
		mainUnit.setPosition(new Coordinate(0, 0, 0).toCenter());
		mainUnit.setStrength(200);
		mainUnit.setAgility(200);
		mainUnit.setWeight(5);
		facade.addUnit(mainUnit, world);
		
		scheduler = mainUnit.getScheduler();
		
		enemy1 = world.createRandomUnit(false);
		enemy1.setPosition(new Coordinate(5, 0, 0).toCenter());
		facade.addUnit(enemy1, world);
		
		for (int i = 0; i < 3; i++) {
			Unit unit = world.createRandomUnit(false);
			unit.setPosition(new Coordinate(9, 9, 0).toCenter());
			facade.addUnit(unit, world);
		}
		
		friend1 = world.createRandomUnit(false);
		friend1.setPosition(new Coordinate(0, 5, 0).toCenter());
		facade.addUnit(friend1, world);
		
		for (int i = 0; i < 6; i++) {
			Unit unit = world.createRandomUnit(false);
			unit.setPosition(new Coordinate(9, 9, 0).toCenter());
			facade.addUnit(unit, world);
		}
		
		boulder1 = new Boulder(world, new Coordinate(5, 5, 0).toCenter());
		world.addEntity(boulder1);
		world.addEntity(new Boulder(world, new Coordinate(7, 7, 0).toCenter()));
		
		log1 = new Log(world, new Coordinate(5, 5, 0).toCenter());
		world.addEntity(log1);
		world.addEntity(new Log(world, new Coordinate(7, 7, 0).toCenter()));
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
		// TODO
	}
	
	@Test
	public void queue() {
		List<Statement> body = new ArrayList<>();
		Task task = getDefaultTask(
				factory.createSequence(body, src));
					body.add(factory.createMoveTo(
							factory.createLiteralPosition(0, 2, 0, src), 
							src));
					body.add(factory.createMoveTo(
							factory.createLiteralPosition(1, 2, 1, src), 
							src));
		scheduler.addToNotAssignedTasks(task);
		scheduler.assignTopPriorityTask(mainUnit);
		
		try {
			advanceTimeFor(facade, world, 50, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(1, 2, 1).toCenter(), mainUnit.getPosition());
	}
	
	@Test
	public void print() {
		// TODO
	}
	
	@Test
	public void void_() {
		// TODO
	}
	
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
			advanceTimeFor(facade, world, 10, 0.1);
		} catch (ModelException e) {}
		
		assertEquals(new Coordinate(0, 0, 0).toCenter(), mainUnit.getPosition());
		// TODO Uitbreiden?
	}
	
	@Test
	public void assignment() {
		// TODO
	}
	
	
	// ACTIONS
	
	@Test
	public void attack() {
		// TODO
	}
	
	@Test
	public void follow() {
		// TODO
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
		// TODO
	}

}
