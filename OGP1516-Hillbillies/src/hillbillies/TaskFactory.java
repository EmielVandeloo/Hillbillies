package hillbillies;

import java.util.List;
import hillbillies.expression.Expression;
import hillbillies.expression.bool.BooleanExpression;
import hillbillies.expression.position.PositionExpression;
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

public class TaskFactory implements ITaskFactory<Expression<?>, hillbillies.statement.Statement, Task> {

	@Override
	public List<Task> createTasks(String name, int priority, hillbillies.statement.Statement activity,
			List<int[]> selectedCubes) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsSolid(Expression<?> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsPassable(Expression<?> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsFriend(Expression<?> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsEnemy(Expression<?> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsAlive(Expression<?> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createCarriesItem(Expression<?> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createNot(Expression<?> expression,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createAnd(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createOr(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createHerePosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createLogPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createBoulderPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createWorkshopPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createSelectedPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createNextToPosition(Expression<? extends Object> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createPositionOf(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createThis(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createFriend(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createEnemy(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createAny(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createTrue(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createFalse(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
