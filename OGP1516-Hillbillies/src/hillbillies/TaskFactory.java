package hillbillies;

import java.util.List;
import hillbillies.expression.Expression;
import hillbillies.model.Task;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statement.Print;

public class TaskFactory implements ITaskFactory<Expression<? extends Object>, hillbillies.statement.Statement, Task> {

	@Override
	public List<Task> createTasks(String name, int priority, hillbillies.statement.Statement activity,
			List<int[]> selectedCubes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createAssignment(String variableName, Expression<? extends Object> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createWhile(Expression<? extends Object> condition,
			hillbillies.statement.Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createIf(Expression<? extends Object> condition,
			hillbillies.statement.Statement ifBody, hillbillies.statement.Statement elseBody,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createPrint(Expression<? extends Object> value,
			SourceLocation sourceLocation) {
		return new Print<>(value, sourceLocation);
	}

	@Override
	public hillbillies.statement.Statement createSequence(List<hillbillies.statement.Statement> statements,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createMoveTo(Expression<? extends Object> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createWork(Expression<? extends Object> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createFollow(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public hillbillies.statement.Statement createAttack(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createReadVariable(String variableName, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createIsSolid(Expression<? extends Object> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createIsPassable(Expression<? extends Object> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createIsFriend(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createIsEnemy(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createIsAlive(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createCarriesItem(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createNot(Expression<? extends Object> expression,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createAnd(Expression<? extends Object> left, Expression<? extends Object> right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createOr(Expression<? extends Object> left, Expression<? extends Object> right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createHerePosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createLogPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createBoulderPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createWorkshopPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createSelectedPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createNextToPosition(Expression<? extends Object> position,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createPositionOf(Expression<? extends Object> unit,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createThis(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createFriend(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createEnemy(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createAny(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createTrue(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Object> createFalse(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
