package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statement.Void;
import hillbillies.world.Position;

public class SchedulerTest {

	private Scheduler defaultScheduler;
	private Task defaultTask, assigned, notAssigned;
	SourceLocation src;
	Unit defaultUnit;
	
	@Before
	public void setup() {
		src = new SourceLocation(5, 5);
		defaultScheduler = new Scheduler();
		defaultTask = new Task("Task", 5, new Void(src));
		assigned = new Task("Task", 0, new Void(src));
		notAssigned = new Task("Task", 0, new Void(src));
		defaultScheduler.addToAssignedTasks(assigned);
		defaultScheduler.addToNotAssignedTasks(notAssigned);
		defaultUnit = new Unit(new Position(0,0,0).convertToIntegerArray(), "Unit", 50, 
				50, 50, 50, false);
	}
	
	@Test
	public void constructor() {
		Scheduler scheduler = new Scheduler();
		assertTrue(scheduler.getNbTasks() == 0);
	}
	
	@Test
	public void getAllTasks() {
		assertTrue(defaultScheduler.getAllTasks().contains(notAssigned));
		assertTrue(defaultScheduler.getAllTasks().contains(assigned));
		assertTrue(defaultScheduler.getNbTasks() == 2);
	}
	
	@Test
	public void hasAsAssignedTask_TrueCase() {
		assertTrue(defaultScheduler.hasAsAssignedTask(assigned));
	}
	
	@Test
	public void hasAsAssignedTask_FalseCase() {
		assertFalse(defaultScheduler.hasAsAssignedTask(notAssigned));
	}
	
	@Test
	public void hasAsNotAssignedTask_TrueCase() {
		assertTrue(defaultScheduler.hasAsNotAssignedTask(notAssigned));
	}
	
	@Test
	public void hasAsNotAssignedTask_FalseCase() {
		assertFalse(defaultScheduler.hasAsNotAssignedTask(assigned));
	}
	
	@Test
	public void hasAsTask() {
		assertTrue(defaultScheduler.hasAsTask(assigned));
		assertTrue(defaultScheduler.hasAsTask(notAssigned));
		assertFalse(defaultScheduler.hasAsTask(new Task("Task", 0, new Void(src))));
	}
	
	@Test
	public void areTasksPartOf_TrueCase() {
		java.util.List<Task> tasks = new ArrayList<>();
		tasks.add(notAssigned); tasks.add(assigned);
		assertTrue(defaultScheduler.areTasksPartOf(tasks));
	}
	
	@Test
	public void areTasksPartOf_FalseCase() {
		java.util.List<Task> tasks = new ArrayList<>();
		tasks.add(defaultTask);
		tasks.add(new Task("Task", 0, new Void(src)));
		assertFalse(defaultScheduler.areTasksPartOf(tasks));
	}
	
	@Test
	public void addToNotAssignedTasks_PossibleCase() {
		defaultScheduler.addToNotAssignedTasks(defaultTask);
		assertTrue(defaultScheduler.hasAsNotAssignedTask(defaultTask));
		assertEquals(defaultTask, defaultScheduler.getNotAssignedTaskAt(1));
	}
	
	@Test (expected = IllegalStateException.class)
	public void addToNotAssignedTasks_ImpossibleCase() {
		defaultScheduler.addToNotAssignedTasks(notAssigned);
	}
	
	@Test
	public void addToAssignedTasks_PossibleCase() {
		defaultScheduler.addToAssignedTasks(defaultTask);
		assertTrue(defaultScheduler.hasAsAssignedTask(defaultTask));
	}
	
	@Test (expected = IllegalStateException.class)
	public void addToAssignedTasks_ImpossibleCase() {
		defaultScheduler.addToAssignedTasks(assigned);
	}
	
	@Test
	public void removeFromNotAssignedTasks_PossibleCase() {
		defaultScheduler.removeFromNotAssignedTasks(notAssigned);
		assertFalse(defaultScheduler.hasAsNotAssignedTask(notAssigned));
		assertFalse(notAssigned.hasAsScheduler(defaultScheduler));
	}
	
	@Test (expected = IllegalStateException.class)
	public void removeFromNotAssignedTasks_ImpossibleCase() {
		defaultScheduler.removeFromNotAssignedTasks(assigned);
	}
	
	@Test
	public void removeFromAssignedTasks_PossibleCase() {
		defaultScheduler.removeFromAssignedTasks(assigned);
		assertFalse(defaultScheduler.hasAsAssignedTask(assigned));
		assertFalse(assigned.hasAsScheduler(defaultScheduler));
	}
	
	@Test (expected = IllegalStateException.class)
	public void removeFromAssignedTasks_ImpossibleCase() {
		defaultScheduler.removeFromAssignedTasks(notAssigned);
	}
	
	@Test
	public void replace_PossibleCase() {
		defaultScheduler.replace(notAssigned, defaultTask);
		assertFalse(defaultScheduler.hasAsTask(notAssigned));
		assertTrue(defaultScheduler.hasAsNotAssignedTask(defaultTask));
	}
	
	@Test (expected = IllegalStateException.class)
	public void replace_ImpossibleCase() {
		defaultScheduler.replace(notAssigned, null);
	}
	
	@Test
	public void isTaskAvailable_TrueCase() {
		assertTrue(defaultScheduler.isTaskAvailable());
	}
	
	@Test
	public void isTaskAvailable_FalseCase() {
		defaultScheduler.removeFromNotAssignedTasks(notAssigned);
		assertFalse(defaultScheduler.isTaskAvailable());
	}
	
	@Test
	public void getTopPriorityTask() {
		defaultScheduler.addToNotAssignedTasks(new Task("Task", -1, new Void(src)));
		assertEquals(notAssigned, defaultScheduler.getTopPriorityTask());
	}
	
	@Test
	public void assignTopPriorityTask_PossibleCase() {
		defaultScheduler.assignTopPriorityTask(defaultUnit);
		assertTrue(defaultUnit.getTask().equals(notAssigned));
		assertFalse(defaultScheduler.hasAsNotAssignedTask(notAssigned));
		assertTrue(defaultScheduler.hasAsAssignedTask(notAssigned));
	}
	
	@Test
	public void assignTopPriorityTask_ImpossibleCase() {
		defaultScheduler.removeFromNotAssignedTasks(notAssigned);
		defaultScheduler.assignTopPriorityTask(defaultUnit);
		assertFalse(defaultUnit.hasTask());
	}
	
	@Test
	public void transferUnfinishedTask_PossibleCase() {
		int priority = assigned.getPriority();
		defaultScheduler.transferUnfinishedTask(assigned);
		assertFalse(defaultScheduler.hasAsAssignedTask(assigned));
		assertTrue(defaultScheduler.hasAsNotAssignedTask(assigned));
		assertTrue(assigned.getPriority() < priority);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void transferUnfinishedTask_ImpossibleCase() {
		defaultScheduler.transferUnfinishedTask(notAssigned);
	}
	
}
