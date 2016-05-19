package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import hillbillies.expression.Expression;
import hillbillies.expression.bool.And;
import hillbillies.expression.bool.CarriesItem;
import hillbillies.expression.bool.False;
import hillbillies.expression.bool.Invert;
import hillbillies.expression.bool.IsAlive;
import hillbillies.expression.bool.IsEnemy;
import hillbillies.expression.bool.IsFriend;
import hillbillies.expression.bool.IsPassable;
import hillbillies.expression.bool.IsSolid;
import hillbillies.expression.bool.Or;
import hillbillies.expression.bool.True;
import hillbillies.expression.position.Here;
import hillbillies.expression.position.LiteralPosition;
import hillbillies.expression.position.NextTo;
import hillbillies.expression.position.PositionOf;
import hillbillies.expression.position.Selected;
import hillbillies.expression.position.Workshop;
import hillbillies.expression.unit.Any;
import hillbillies.expression.unit.Enemy;
import hillbillies.expression.unit.Friend;
import hillbillies.expression.unit.This;
import hillbillies.model.Boulder;
import hillbillies.model.Log;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;
import hillbillies.statement.Void;
import hillbillies.world.Coordinate;
import hillbillies.world.Cube;
import hillbillies.world.Position;
import ogp.framework.util.ModelException;

public class ExpressionTest {
	
	public Unit mainUnit;
	public Unit friend1;
	public Unit enemy1;
	
	public Boulder boulder1;
	public Log log1;
	
	public World world;
	public Facade facade;
	
	public SourceLocation src = new SourceLocation(5, 5);
	public Program program;

	
	@Before
	public void setUp() throws Exception {
		facade = new Facade();
		
		int[][][] types = new int[10][10][5];
		types[1][1][4] = Cube.ROCK.getId();
		types[5][5][1] = Cube.WORKBENCH.getId();
		types[7][7][1] = Cube.WORKBENCH.getId();
		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		
		mainUnit = world.createRandomUnit(false);
		mainUnit.setPosition(new Coordinate(0, 0, 0).toCenter());
		facade.addUnit(mainUnit, world);
		
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
		
		program = new Program(new Void(src));
		program.setUnit(mainUnit);
	}

	
	// BOOLEAN

	@Test
	public void true_() {
		Expression<Boolean> expression = new True(src);
		assertEquals(true, expression.evaluate(program));
	}
	
	@Test
	public void false_() {
		Expression<Boolean> expression = new False(src);
		assertEquals(false, expression.evaluate(program));
	}
	
	@Test
	public void and() {
		Expression<Boolean> expressionTrue = new True(src);
		Expression<Boolean> expressionFalse = new False(src);
		Expression<Boolean> expression;
		
		expression = new And(src, expressionTrue, expressionTrue);
		assertEquals(true, expression.evaluate(program));
		
		expression = new And(src, expressionTrue, expressionFalse);
		assertEquals(false, expression.evaluate(program));
		
		expression = new And(src, expressionFalse, expressionTrue);
		assertEquals(false, expression.evaluate(program));
		
		expression = new And(src, expressionFalse, expressionFalse);
		assertEquals(false, expression.evaluate(program));
	}
	
	@Test
	public void or() {
		Expression<Boolean> expressionTrue = new True(src);
		Expression<Boolean> expressionFalse = new False(src);
		Expression<Boolean> expression;
		
		expression = new Or(src, expressionTrue, expressionTrue);
		assertEquals(true, expression.evaluate(program));
		
		expression = new Or(src, expressionTrue, expressionFalse);
		assertEquals(true, expression.evaluate(program));
		
		expression = new Or(src, expressionFalse, expressionTrue);
		assertEquals(true, expression.evaluate(program));
		
		expression = new Or(src, expressionFalse, expressionFalse);
		assertEquals(false, expression.evaluate(program));
	}
	
	@Test
	public void invert() {
		Expression<Boolean> expressionTrue = new True(src);
		Expression<Boolean> expressionFalse = new False(src);
		Expression<Boolean> expression;
		
		expression = new Invert(src, expressionTrue);
		assertEquals(false, expression.evaluate(program));
		
		expression = new Invert(src, expressionFalse);
		assertEquals(true, expression.evaluate(program));
	}
	
	@Test
	public void bracket() {
		// TODO
	}
	
	@Test
	public void carriesItem() {
		Expression<Boolean> expression = new CarriesItem(src, new This(src));
		assertEquals(false, expression.evaluate(program));
		
		mainUnit.getInventory().addItem(new Boulder(world, new Coordinate(0, 0, 0).toCenter()));
		assertEquals(true, expression.evaluate(program));
	}
	
	@Test
	public void isAlive() {
		Unit maarten = world.createRandomUnit(false);
		try {
			facade.addUnit(maarten, world);
		} catch (ModelException e) {}
		program.setUnit(maarten);
		
		Expression<Boolean> expression = new IsAlive(src, new This(src));
		assertEquals(true, expression.evaluate(program));
		
		maarten.terminate();
		assertEquals(false, expression.evaluate(program));
		
		program.setUnit(mainUnit);
	}
	
	@Test
	public void isEnemy() {
		// TODO
		
		Expression<Boolean> enemyExpression = new IsEnemy(src, new Enemy(src));
		assertEquals(true, enemyExpression.evaluate(program));
		
		Expression<Boolean> friendExpression = new IsEnemy(src, new Friend(src));
		assertEquals(false, friendExpression.evaluate(program));
	}
	
	@Test
	public void isFriend() {
		Expression<Boolean> friendExpression = new IsFriend(src, new Friend(src));
		assertEquals(true, friendExpression.evaluate(program));
		
		Expression<Boolean> enemyExpression = new IsFriend(src, new Enemy(src));
		assertEquals(false, enemyExpression.evaluate(program));
	}
	
	@Test
	public void isPassable() {
		Expression<Boolean> expression;
		
		expression = new IsPassable(src, new LiteralPosition(src, new Position(1, 1, 3)));
		assertEquals(true, expression.evaluate(program));
		
		expression = new IsPassable(src, new LiteralPosition(src, new Position(1, 1, 4)));
		assertEquals(false, expression.evaluate(program));
	}
	
	@Test
	public void isSolid() {
		Expression<Boolean> expression;
		
		expression = new IsSolid(src, new LiteralPosition(src, new Position(1, 1, 3)));
		assertEquals(false, expression.evaluate(program));
		
		expression = new IsSolid(src, new LiteralPosition(src, new Position(1, 1, 4)));
		assertEquals(true, expression.evaluate(program));	}
	
	
	// POSITION
	
	@Test
	public void boulder() {
		Expression<Position> expression = new hillbillies.expression.position.Boulder(src);
		assertTrue(expression.evaluate(program) == boulder1.getPosition());
	}
	
	@Test
	public void log() {
		Expression<Position> expression = new hillbillies.expression.position.Log(src);
		assertTrue(expression.evaluate(program) == log1.getPosition());
	}
	
	@Test
	public void workshop() {
		Expression<Position> expression = new Workshop(src);
		assertTrue(expression.evaluate(program).equals(new Coordinate(5, 5, 1).toCenter()));
	}
	
	@Test
	public void here() {
		Expression<Position> expression = new Here(src);
		assertEquals(mainUnit.getPosition(), expression.evaluate(program));
	}
	
	@Test
	public void literalPosition() {
		Expression<Position> expression = new LiteralPosition(src, 1, 2, 3);
		assertEquals(new Coordinate(1, 2, 3).toCenter(), expression.evaluate(program));
	}
	
	@Test
	public void nextTo() {
		Position position = new Coordinate(1, 1, 1).toCenter();
		Expression<Position> expression = new NextTo(src, new LiteralPosition(src, position));
		
		assertTrue(world.getAllNeighbours(position).contains(expression.evaluate(program)));
	}
	
	@Test
	public void positionOf() {
		Expression<Position> expression = new PositionOf(src, new This(src));
		assertEquals(mainUnit.getPosition(), expression.evaluate(program));
	}
	
	@Test
	public void selected() {
		Task task = new Task("Tester", 50, new Void(src), new Coordinate(5, 5, 5).toCenter());
		mainUnit.getScheduler().addToNotAssignedTasks(task);
		mainUnit.getScheduler().assignTopPriorityTask(mainUnit);
		
		
		Expression<Position> expression = new Selected(src);
		assertEquals(new Coordinate(5, 5, 5).toCenter(), expression.evaluate(mainUnit.getProgram()));
	}
	
	
	// UNIT
	
	@Test
	public void any() {
		Expression<Unit> expression = new Any(src);
		assertTrue(world.getAllUnits().contains(expression.evaluate(program)));
	}
	
	@Test
	public void enemy() {
		// TODO
		Expression<Unit> expression = new Enemy(src);
		assertTrue(! mainUnit.getFaction().hasAsUnit(expression.evaluate(program)));
		assertEquals(enemy1, expression.evaluate(program));
	}
	
	@Test
	public void friend() {
		// TODO
		Expression<Unit> expression = new Friend(src);
		assertTrue(mainUnit.getFaction().hasAsUnit(expression.evaluate(program)));
		assertEquals(friend1, expression.evaluate(program));
	}
	
	@Test
	public void this_() {
		Expression<Unit> expression = new This(src);
		assertEquals(mainUnit, expression.evaluate(program));
	}
	
	// OTHER
	
	@Test
	public void readVariable() {}

}
