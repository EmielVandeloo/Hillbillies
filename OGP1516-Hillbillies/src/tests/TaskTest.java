package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statement.Break;
import hillbillies.statement.Void;
import hillbillies.world.Position;

public class TaskTest {

	private Position pos;
	private SourceLocation src;
	private Task defaultTask;
	private Scheduler defaultScheduler;
	private Unit defaultUnit;
	
	@Before
	public void setup() {
		SourceLocation src = new SourceLocation(5, 5);
		Position pos = new Position(0,0,0);
		defaultUnit = new Unit(pos.convertToIntegerArray(), "Unit", 50, 50, 50, 50, false);
		defaultTask = new Task("Task", 0, new hillbillies.statement.Void(src), pos);
		defaultUnit.setTask(defaultTask);
		defaultScheduler = new Scheduler();
		defaultScheduler.addToNotAssignedTasks(defaultTask);
	}
	
	@Test
	public void constructor_LegalCase() {
		Task task = new Task("Task", -30, new Break(src), pos);
		assertEquals("Task", task.getName());
		assertEquals(-30, task.getPriority());
		assertTrue(task.getStatement() instanceof Break);
		assertEquals(pos, task.getSelectedPosition());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void constructor_NullName() {
		new Task(null, 0, new hillbillies.statement.Void(src), pos);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void constructor_NullStatement() {
		new Task("Task", 0, null, pos);
	}
	
	@Test
	public void terminate() {
		defaultTask.terminate();
		assertTrue(defaultTask.isTerminated());
		assertFalse(defaultScheduler.getAllTasks().contains(defaultTask));
	}
	
	// setName, setPriority, setStatement, setSelectedPosition tested in constructor tests
	
	@Test
	public void setExecutingUnit_LegalCase() {
		defaultTask.setExecutingUnit(defaultUnit);
		assertTrue(defaultTask.getExecutingUnit().equals(defaultUnit));
	}
	
	@Test
	public void setExecutingUnit_NullUnit() {
		defaultTask.setExecutingUnit(null);
		assertTrue(defaultTask.getExecutingUnit() == null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setExecutingUnit_UnitDoesNotReferenceTask() {
		defaultUnit.setTask(null);
		defaultTask.setExecutingUnit(defaultUnit);
	}
	
	@Test
	public void hasAsScheduler_TrueCase() {
		assertTrue(defaultTask.hasAsScheduler(defaultScheduler));
	}
	
	@Test
	public void hasAsScheduler_FalseCase() {
		assertFalse(defaultTask.hasAsScheduler(new Scheduler()));
	}
	
	@Test
	public void addScheduler_PossibleCase() {
		Scheduler scheduler = new Scheduler();
		defaultTask.addScheduler(scheduler);
		assertTrue(defaultTask.hasAsScheduler(scheduler));
	}
	
	@Test (expected = IllegalStateException.class)
	public void addScheduler_ImpossibleCase() {
		defaultTask.addScheduler(defaultScheduler);
	}
	
	@Test
	public void removeScheduler_PossibleCase() {
		defaultTask.removeScheduler(defaultScheduler);
		assertFalse(defaultTask.hasAsScheduler(defaultScheduler));
	}
	
	@Test (expected = IllegalStateException.class)
	public void removeScheduler_ImpossibleCase() {
		defaultTask.removeScheduler(new Scheduler());
	}
	
	@Test
	public void compareTo() {
		Task taskWithLowerPriority = new Task("Task", -1, new Void(src));
		Task taskWithHigherPriority = new Task("Task", 1, new Void(src));
		Task taskWithSamePriority = new Task("Task", 0, new Void(src));
		assertTrue(defaultTask.compareTo(taskWithLowerPriority) == -1);
		assertTrue(defaultTask.compareTo(taskWithHigherPriority) == 1);
		assertTrue(defaultTask.compareTo(taskWithSamePriority) == 0);
	}
	
	@Test
	public void toString_() {
		assertEquals("Task [name=" + "Task" + ", priority=" + 0 + "]", defaultTask.toString());
	}

}
