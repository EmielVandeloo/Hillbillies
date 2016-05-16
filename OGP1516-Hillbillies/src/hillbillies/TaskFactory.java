package hillbillies;

import java.util.ArrayList;
import java.util.List;

import hillbillies.expression.Expression;
import hillbillies.expression.bool.And;
import hillbillies.expression.bool.BooleanExpression;
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
import hillbillies.expression.object.ReadVariable;
import hillbillies.expression.position.Boulder;
import hillbillies.expression.position.Here;
import hillbillies.expression.position.LiteralPosition;
import hillbillies.expression.position.Log;
import hillbillies.expression.position.NextTo;
import hillbillies.expression.position.PositionExpression;
import hillbillies.expression.position.PositionOf;
import hillbillies.expression.position.Selected;
import hillbillies.expression.position.Workshop;
import hillbillies.expression.unit.Any;
import hillbillies.expression.unit.Enemy;
import hillbillies.expression.unit.Friend;
import hillbillies.expression.unit.This;
import hillbillies.expression.unit.UnitExpression;
import hillbillies.model.Task;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statement.Assignment;
import hillbillies.statement.Break;
import hillbillies.statement.Conditional;
import hillbillies.statement.Loop;
import hillbillies.statement.Print;
import hillbillies.statement.Queue;
import hillbillies.statement.action.Attack;
import hillbillies.statement.action.Follow;
import hillbillies.statement.action.MoveTo;
import hillbillies.statement.action.Work;
import hillbillies.world.Position;

public class TaskFactory implements ITaskFactory<Expression<?>, hillbillies.statement.Statement, Task> {

	@Override
	public List<Task> createTasks(String name, int priority, hillbillies.statement.Statement activity,
			List<int[]> selectedCubes) {
		
		List<Task> list = new ArrayList<>();
		for (int[] is : selectedCubes) {
			list.add(new Task(name, priority, activity, new Position(is[0], is[1], is[2])));
		}
		return list;
	}

	@Override
	public hillbillies.statement.Statement createAssignment(String variableName, Expression<?> value,
			SourceLocation sourceLocation) {
		return new Assignment(variableName, value, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createWhile(Expression<?> condition,
			hillbillies.statement.Statement body, SourceLocation sourceLocation) {
		return new Loop((BooleanExpression) condition, body, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createIf(Expression<?> condition,
			hillbillies.statement.Statement ifBody, hillbillies.statement.Statement elseBody,
			SourceLocation sourceLocation) {
		return new Conditional((BooleanExpression) condition, ifBody, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createBreak(SourceLocation sourceLocation) {
		return new Break(sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createPrint(Expression<?> value,
			SourceLocation sourceLocation) {
		return new Print(value, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createSequence(List<hillbillies.statement.Statement> statements,
			SourceLocation sourceLocation) {
		return new Queue(statements, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createMoveTo(Expression<?> position,
			SourceLocation sourceLocation) {
		return new MoveTo((PositionExpression) position, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createWork(Expression<?> position,
			SourceLocation sourceLocation) {
		return new Work((PositionExpression) position, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createFollow(Expression<?> unit,
			SourceLocation sourceLocation) {
		return new Follow((UnitExpression) unit, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createAttack(Expression<?> unit,
			SourceLocation sourceLocation) {
		return new Attack((UnitExpression) unit, sourceLocation);
	}

	@Override
	public Expression<?> createReadVariable(String variableName, SourceLocation sourceLocation) {
		return new ReadVariable(sourceLocation, variableName);
	}

	@Override
	public Expression<?> createIsSolid(Expression<?> position,
			SourceLocation sourceLocation) {
		return new IsSolid(sourceLocation, (PositionExpression) position);
	}

	@Override
	public Expression<?> createIsPassable(Expression<?> position,
			SourceLocation sourceLocation) {
		return new IsPassable(sourceLocation, (PositionExpression) position);
	}

	@Override
	public Expression<?> createIsFriend(Expression<?> unit,
			SourceLocation sourceLocation) {
		return new IsFriend(sourceLocation, (UnitExpression) unit);
	}

	@Override
	public Expression<?> createIsEnemy(Expression<?> unit,
			SourceLocation sourceLocation) {
		return new IsEnemy(sourceLocation, (UnitExpression) unit);
	}

	@Override
	public Expression<?> createIsAlive(Expression<?> unit,
			SourceLocation sourceLocation) {
		return new IsAlive(sourceLocation, (UnitExpression) unit);
	}

	@Override
	public Expression<?> createCarriesItem(Expression<?> unit,
			SourceLocation sourceLocation) {
		return new CarriesItem(sourceLocation, (UnitExpression) unit);
	}

	@Override
	public Expression<?> createNot(Expression<?> expression,
			SourceLocation sourceLocation) {
		return new Invert(sourceLocation, (BooleanExpression) expression);
	}

	@Override
	public Expression<?> createAnd(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new And(sourceLocation, (BooleanExpression) left, (BooleanExpression) right);
	}

	@Override
	public Expression<?> createOr(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		return new Or(sourceLocation, (BooleanExpression) left, (BooleanExpression) right);
	}

	@Override
	public Expression<?> createHerePosition(SourceLocation sourceLocation) {
		return new Here(sourceLocation);
	}

	@Override
	public Expression<?> createLogPosition(SourceLocation sourceLocation) {
		return new Log(sourceLocation);
	}

	@Override
	public Expression<?> createBoulderPosition(SourceLocation sourceLocation) {
		return new Boulder(sourceLocation);
	}

	@Override
	public Expression<?> createWorkshopPosition(SourceLocation sourceLocation) {
		return new Workshop(sourceLocation);
	}

	@Override
	public Expression<?> createSelectedPosition(SourceLocation sourceLocation) {
		return new Selected(sourceLocation);
	}

	@Override
	public Expression<?> createNextToPosition(Expression<? extends Object> position,
			SourceLocation sourceLocation) {
		return new NextTo(sourceLocation, (PositionExpression) position);
	}

	@Override
	public Expression<?> createPositionOf(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		return new PositionOf(sourceLocation, (UnitExpression) unit);
	}

	@Override
	public Expression<?> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		return new LiteralPosition(sourceLocation, x, y, z);
	}

	@Override
	public Expression<?> createThis(SourceLocation sourceLocation) {
		return new This(sourceLocation);
	}

	@Override
	public Expression<?> createFriend(SourceLocation sourceLocation) {
		return new Friend(sourceLocation);
	}

	@Override
	public Expression<?> createEnemy(SourceLocation sourceLocation) {
		return new Enemy(sourceLocation);
	}

	@Override
	public Expression<?> createAny(SourceLocation sourceLocation) {
		return new Any(sourceLocation);
	}

	@Override
	public Expression<?> createTrue(SourceLocation sourceLocation) {
		return new True(sourceLocation);
	}

	@Override
	public Expression<?> createFalse(SourceLocation sourceLocation) {
		return new False(sourceLocation);
	}
	
}
