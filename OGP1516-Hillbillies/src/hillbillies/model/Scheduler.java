package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;

/**
 * A class of schedulers.
 * 
 * @invar Each scheduler has proper tasks attached to it.
 *      | hasProperTasks()
 * 
 * @author Pieter-Jan Van den Broecke: EltCw
 * 		   Emiel Vandeloo: WtkCw
 */
public class Scheduler {
	
	// FIELDS
	
	/**
	 * Variable referencing a list collecting all the unassigned tasks
	 * of this scheduler.
	 * 
	 * @invar The referenced list is effective.
	 *      | notAssigned != null
	 * @invar Each task registered in the referenced list is
	 *        effective and not yet terminated.
	 *      | for each task in notAssigned:
	 *      |   ( (task != null) &&
	 *      |     (! task.isTerminated()) )
	 * @invar No task is registered at several positions
	 *        in the referenced list.
	 *      | for each I,J in 0..notAssigned.size()-1:
	 *      |   ( (I == J) ||
	 *      |     (notAssigned.get(I) != notAssigned.get(J))
	 */
	private List<Task> notAssigned = new ArrayList<>();
	
	/**
	 * Variable referencing a list collecting all the assigned tasks
	 * of this scheduler.
	 * 
	 * @invar The referenced list is effective.
	 *      | assigned != null
	 * @invar Each task registered in the referenced list is
	 *        effective and not yet terminated.
	 *      | for each task in assigned:
	 *      |   ( (task != null) &&
	 *      |     (! task.isTerminated()) )
	 * @invar No task is registered at several positions
	 *        in the referenced list.
	 *      | for each I,J in 0..assigned.size()-1:
	 *      |   ( (I == J) ||
	 *      |     (assigned.get(I) != assigned.get(J))
	 */
	private List<Task> assigned = new ArrayList<>();
	
	/**
	 * Initialize this new scheduler with no tasks yet.
	 */
	public Scheduler() {}
	
	/**
	 * Return whether or not this scheduler can have the given task as its task.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return True if and only if the given task is effective.
	 *       | result == (task != null)
	 */
	private static boolean canHaveAsTask(Task task) {
		return (task != null);
	}
	
	/**
	 * Check whether this scheduler has proper tasks attached to it.
	 * 
	 * @return True if and only if this scheduler can have each of the
	 *         tasks attached to it as a task at the given index, and if the
	 *         set of schedulers of the task contains this scheduler as one 
	 *         of its schedulers.
	 *       | result ==
	 *       |   for each I in 1..getNbTasks()
	 *       |     (canHaveAsTaskAt(getTaskAt(I)) && getTaskAt(I).hasAsScheduler(this))
	 */
	@Model
	private boolean hasProperTasks() {
		for (int i = 1; i <= getNbTasks(); i++) {
			if (!canHaveAsTask(getTaskAt(i)) || !getTaskAt(i).hasAsScheduler(this))
				return false;
		}
		return true;
	}
	
	/**
	 * Return all assigned tasks.
	 */
	@Basic
	public List<Task> getAssignedTasks() {
		return this.assigned;
	}
	
	/**
	 * Return the number of assigned tasks of this scheduler.
	 * 
	 * @return The size of the list of assigned tasks.
	 *       | result == ...
	 */
	public int getNbAssignedTasks() {
		return getAssignedTasks().size();
	}
	
	/**
	 * Return the assigned task associated with this scheduler at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the task to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of tasks for this scheduler.
	 *       | (index < 1) || (index > getNbTasks())
	 */
	@Basic
	public Task getAssignedTaskAt(int index) throws IndexOutOfBoundsException {
		if (index < 1 || index > getNbAssignedTasks()) {
			throw new IndexOutOfBoundsException();
		}
		return getAssignedTasks().get(index-1);
	}
	
	/**
	 * Return all unassigned tasks.
	 */
	@Basic
	public List<Task> getNotAssignedTasks() {
		return this.notAssigned;
	}
	
	/**
	 * Return the number of unassigned tasks of this scheduler.
	 * 
	 * @return The size of the list of unassigned tasks.
	 *       | result == ...
	 */
	public int getNbNotAssignedTasks() {
		return getNotAssignedTasks().size();
	}
	
	/**
	 * Return the unassigned task associated with this scheduler at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the task to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of tasks for this scheduler.
	 *       | (index < 1) || (index > getNbTasks())
	 */
	@Basic
	public Task getNotAssignedTaskAt(int index) throws IndexOutOfBoundsException {
		if (index < 1 || index > getNbNotAssignedTasks()) {
			throw new IndexOutOfBoundsException();
		}
		return getNotAssignedTasks().get(index-1);
	}
	
	/**
	 * Return all tasks (assigned and unassigned) associated with this scheduler.
	 * 
	 * @return All tasks of this scheduler.
	 *       | ...
	 */
	public List<Task> getAllTasks() {
		List<Task> allTasks = new ArrayList<>();
		allTasks.addAll(getAssignedTasks());
		allTasks.addAll(getNotAssignedTasks());
		return allTasks;
	}
	
	/**
	 * Return the number of tasks (assigned and unassigned) associated with this scheduler.
	 * 
	 * @return The size of the list of all tasks of this scheduler
	 *       | result == ...
	 */
	public int getNbTasks() {
		return getAllTasks().size();
	}
	
	/**
	 * Return the task associated with this scheduler at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the task to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of tasks for this scheduler.
	 *       | (index < 1) || (index > getNbTasks())
	 */
	@Model
	private Task getTaskAt(int index) {
		return getAllTasks().get(index-1);
	}
	
	/**
	 * Return an iterator for all tasks currently managed by the scheduler.
	 * 
	 * @return An iterator for all tasks currently managed by the scheduler.
	 *       | result == ...
	 */
	public Iterator<Task> iterator() {
		return getAllTasks().iterator();
	}

	/**
	 * Check whether this scheduler has the given task as one of its assigned
	 * tasks.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return The given task is registered at some position as
	 *         an assigned task of this scheduler.
	 *       | for some I in 1..getNbAssignedTasks():
	 *       |   getAssignedTaskAt(I) == task
	 */
	public boolean hasAsAssignedTask(Task task) {
		return getAssignedTasks().contains(task);
	}
	
	/**
	 * Check whether this scheduler has the given task as one of its unassigned
	 * tasks.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return The given task is registered at some position as
	 *         an unassigned task of this scheduler.
	 *       | for some I in 1..getNbNotAssignedTasks():
	 *       |   getNotAssignedTaskAt(I) == task
	 */
	public boolean hasAsNotAssignedTask(Task task) {
		return getNotAssignedTasks().contains(task);
	}
	
	/**
	 * Check whether this scheduler has the given task as one of its
	 * tasks.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return The given task is registered at some position as
	 *         a task of this scheduler.
	 *       | for some I in 1..getNbTasks():
	 *       |   getTaskAt(I) == task
	 */
	public boolean hasAsTask(Task task) {
		return getAllTasks().contains(task);
	}
	
	/**
	 * Check whether the given collection of tasks is part of this scheduler.
	 * 
	 * @param  tasks
	 *         The collection of tasks to check.
	 * @return True if and only if this scheduler has each task of the given collection of tasks as
	 *         one of its tasks.
	 *       | for each task of tasks
	 *       |   if (hasAsTask(task))
	 *       |     result == true
	 */
	public boolean areTasksPartOf(Collection<Task> tasks) {
		for (Task task : tasks) {
			if (!hasAsTask(task)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Add the given task to the list of unassigned tasks of this scheduler.
	 * 
	 * @param  task
	 *         The task to add.
	 * @effect The given task is added to the list of unassigned tasks.
	 *       | getNotAssignedTasks().add(task)
	 * @effect The list of unassigned tasks is sorted.
	 *       | sort()
	 * @throws IllegalArgumentException
	 *         The given task is an invalid task for each scheduler, or this scheduler
	 *         already references the given task as one of its tasks.
	 *       | !canHaveAsTask(task) || hasAsTask(task)
	 */
	public void addToNotAssignedTasks(Task task) throws IllegalArgumentException {
		if (!canHaveAsTask(task) || hasAsTask(task)) {
			throw new IllegalArgumentException();
		}
		getNotAssignedTasks().add(task);
		sort();
	}
	
	/**
	 * Remove the given task from the list of unassigned tasks of this scheduler.
	 * 
	 * @param  task
	 *         The task to remove.
	 * @effect The given task is removed from the list of unassigned tasks.
	 *       | getNotAssignedTasks().remove(task)
	 * @throws IllegalArgumentException
	 *         The scheduler does not have the given task as one of its unassigned tasks.
	 *       | !hasAsNotAssignedTask(task)
	 */
	public void removeFromNotAssignedTasks(Task task) throws IllegalArgumentException {
		if (!hasAsNotAssignedTask(task)) {
			throw new IllegalArgumentException();
		}
		getNotAssignedTasks().remove(task);
	}
	
	/**
	 * Add the given task to the list of assigned tasks of this scheduler.
	 * 
	 * @param  task
	 *         The task to add.
	 * @effect The given task is added to the list of assigned tasks.
	 *       | getAssignedTasks().add(task)
	 * @throws IllegalArgumentException
	 *         The given task is an invalid task for each scheduler, or this scheduler
	 *         already references the given task as one of its tasks.
	 *       | !canHaveAsTask(task) || hasAsTask(task)
	 */
	public void addToAssignedTasks(Task task) throws IllegalArgumentException {
		if (!canHaveAsTask(task) || hasAsTask(task)) {
			throw new IllegalArgumentException();
		}
		getAssignedTasks().add(task);
	}
	
	/**
	 * Remove the given task from the list of assigned tasks of this scheduler.
	 * 
	 * @param  task
	 *         The task to remove.
	 * @effect The given task is removed from the list of assigned tasks.
	 *       | getAssignedTasks().remove(task)
	 * @throws IllegalArgumentException
	 *         The scheduler does not have the given task as one of its assigned tasks.
	 *       | !hasAsAssignedTask(task)
	 */
	public void removeFromAssignedTasks(Task task) throws IllegalArgumentException {
		if (!hasAsAssignedTask(task)) {
			throw new IllegalArgumentException();
		}
		getAssignedTasks().remove(task);
	}
	
	/**
	 * Replace the given old task with the given new task.
	 * 
	 * @param  oldTask
	 *         The task to replace.
	 * @param  newTask
	 *         The task to replace the old task with.
	 * @effect The old task is removed from the list of unassigned tasks.
	 *       | removeFromNotAssignedTasks(oldTask)
	 * @effect The new task is added to the list of unassigned tasks.
	 *       | addToNotAssignedTasks(newTask)
	 */
	public void replace(Task oldTask, Task newTask) throws IllegalArgumentException {
		removeFromNotAssignedTasks(oldTask);
		try {
			addToNotAssignedTasks(newTask);
		} catch (IllegalArgumentException e) {
			addToNotAssignedTasks(oldTask);
			throw e;
		}
	}
	
	/**
	 * Sort the list of unassigned tasks.
	 */
	public void sort() {
		Collections.sort(getNotAssignedTasks());
	}
	
	/**
	 * Check whether there is an unassigned task available in this scheduler.
	 * 
	 * @return True if and only if the number of unassigned tasks of this scheduler is greater than zero.
	 *       | result == getNbNotAssignedTasks > 0
	 */
	public boolean isTaskAvailable() {
		return getNbNotAssignedTasks() > 0;
	}
	
	/**
	 * A method to return the most important task.
	 * 
	 * @return The first task in the list of unassigned tasks. Null if there is no such task.
	 *       | if (getNbNotAssignedTasks() > 0)
	 *       |   then result == getNotAssignedTaskAt(1)
	 *       | else result == null
	 */
	public Task getTopPriorityTask() {
		if (getNbNotAssignedTasks() < 1) {
			return null;
		}
		return getNotAssignedTaskAt(1);
	}
	
	/**
	 * Assign the given task to the given unit.
	 *  
	 * @param  task
	 *         The task to assign.
	 * @param  unit
	 *         The unit to assign the task to.
	 * @effect The given task is removed from the list of unassigned tasks.
	 *       | removeFromNotAssignedTasks(task)
	 * @effect The given task is added to the list of assigned tasks.
	 *       | addToAssignedTasks(task)
	 * @effect The task of the given unit is set to the given task.
	 *       | unit.setTask(task)
	 * @effect The executing unit of the given task is set to the given unit.
	 *       | task.setExecutingUnit(unit)
	 */
	public void assign(Task task, Unit unit) throws IllegalArgumentException {
		removeFromNotAssignedTasks(task);
		addToAssignedTasks(task);
		unit.setTask(task);
		task.setExecutingUnit(unit);
	}
	
	/**
	 * Assign the most important task of this scheduler to the given unit.
	 * 
	 * @param  unit
	 *         The unit to assign the most important task to.
	 * @effect If the number of unassigned tasks is positive, the top priority
	 *         task is assigned to the given unit.
	 *       | if (getNbNotAssignedTasks() > 0)
	 *       |   then assign(getTopPriorityTask(), unit) 
	 */
	public void assignTopPriorityTask(Unit unit) {
		if (getNbNotAssignedTasks() > 0) {
			assign(getTopPriorityTask(), unit);
		}
	}
	
	/**
	 * Transfer an unfinished task from the list of assigned tasks to the list of unassigned
	 * tasks of this scheduler.
	 * 
	 * @param  task
	 * 		   The task to transfer.
	 * @effect The executing unit of the given task is set to null.
	 *       | task.setExecutingUnit(null)
	 * @post   The priority of the given task is reduced.
	 *       | (new task).getPriority() < task.getPriority()
	 * @effect The given task is removed from the list of assigned tasks.
	 *       | removeFromAssignedTasks(task)
	 * @effect The given task is added to the list of unassigned tasks.
	 *       | addToNotAssignedTasks(task)
	 * @throws IllegalArgumentException
	 *         This scheduler does not have the given task as one of its assigned tasks.
	 *       | !hasAsNotAssignedTask(task)
	 */
	public void transferUnfinishedTask(Task task) throws IllegalArgumentException {
		if (!hasAsNotAssignedTask(task)) {
			throw new IllegalArgumentException();
		}
		task.setExecutingUnit(null);
		task.setPriority(task.getPriority() * 3/4);
		removeFromAssignedTasks(task);
		addToNotAssignedTasks(task);
	}

}
