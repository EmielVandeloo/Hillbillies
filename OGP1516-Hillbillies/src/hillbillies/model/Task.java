package hillbillies.model;

import java.util.HashSet;
import java.util.Set;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.statement.Statement;
import hillbillies.world.Position;

/**
 * A class of tasks.
 * 
 * @invar  The name of each task must be a valid name for any
 *         task.
 *       | isValidName(getName())
 * @invar  The priority of each task must be a valid priority for any
 *         task.
 *       | isValidPriority(getPriority())
 * @invar  The activity of each task must be a valid activity for any
 *         task.
 *       | isValidActivity(getActivity())
 * @invar  The selected position of each task must be a valid selected position for any
 *         task.
 *       | isValidSelectedPosition(getSelectedPosition())
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 3: 20/05/2016
 * 
 * https://github.com/EmielVandeloo/Hillbillies.git
 */
public class Task implements Comparable<Task> {

	// FIELDS

	/**
	 * Variable registering whether this task is terminated.
	 */
	private boolean isTerminated;
	
	/**
	 * Variable registering the name of this task.
	 */
	private String name;

	/**
	 * Variable registering the priority of this task.
	 */
	private int priority;

	/**
	 * Variable registering the statement of this task.
	 */
	private Statement statement;

	/**
	 * Variable registering the selected position of this task.
	 */
	private Position selectedPosition;
	
	/**
	 * Variable referencing the executing unit of this task.
	 */
	private Unit unit;
	
	/**
	 * Variable referencing the set of schedulers to which this task is attached.
	 */
	private Set<Scheduler> schedulers = new HashSet<>();

	// CONSTRUCTORS
	
	/**
	 * Initialize this new task with given name, priority, activity 
	 * and selected position.
	 *
	 * @param  name
	 *         The name for this new task.
	 * @param  priority
	 *         The priority for this new task.
	 * @param  activity
	 *         The activity for this new task.
	 * @param  selectedPosition
	 *         The selected position for this new task.
	 * @effect The name of this new task is set to
	 *         the given name.
	 *       | setName(name)
	 * @effect The priority of this new task is set to
	 *         the given priority.
	 *       | setPriority(priority)
	 * @effect The activity of this new task is set to
	 *         the given activity.
	 *       | setActivity(activity)
	 * @effect The selected position of this new task is set to
	 *         the given selected position.
	 *       | setSelectedPosition(selectedPosition)
	 */
	@Raw
	public Task(String name, int priority, Statement statement, Position selectedPosition) 
			throws IllegalArgumentException {		
		setName(name);
		setPriority(priority);
		setStatement(statement);
		setSelectedPosition(selectedPosition);
	}
	
	/**
	 * Initialize this new task with given name, priority and activity.
	 *
	 * @param  name
	 *         The name for this new task.
	 * @param  priority
	 *         The priority for this new task.
	 * @param  activity
	 *         The activity for this new task.
	 * @param  selectedPosition
	 *         The selected position for this new task.
	 * @effect This new task was created.
	 *       | this(name, priority, statement, null)
	 */
	@Raw
	public Task(String name, int priority, Statement statement) throws IllegalArgumentException {
		this(name, priority, statement, null);
	}
	
	/**
	 * Return the terminated state of this task.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this task.
	 * 
	 * @post   If this task is not already terminated, this task is terminated.
	 *       | if (!isTerminated())
	 *       |   then new.IsTerminated() == true
	 * @effect If this task is not already terminated and if there is a scheduler that has this task
	 *         as one of its tasks, this task is removed from that scheduler.
	 *       | if (!isTerminated())
	 *       |   then foreach scheduler in getSchedulers()
	 *       |          scheduler.removeFromAssignedTasks(this)
	 *       |          scheduler.removeFromNotAssignedTasks(this)
	 */
	public void terminate() {
		if (!isTerminated()) {
			this.isTerminated = true;
			
			for (Scheduler scheduler : getSchedulers()) {
				try {
					scheduler.removeFromAssignedTasks(this);
				} catch (IllegalStateException e) {}
				try {
					scheduler.removeFromNotAssignedTasks(this);
				} catch (IllegalStateException e) {}
			}
		}
	}

	// GETTERS AND SETTERS

	/**
	 * Return the name of this task.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}

	/**
	 * Check whether the given name is a valid name for
	 * any task.
	 *  
	 * @param  name
	 *         The name to check.
	 * @return True if and only if the given name is effective.
	 *       | result == (name != null)
	 */
	@Model
	private static boolean isValidName(String name) {
		return (name != null);
	}

	/**
	 * Set the name of this task to the given name.
	 * 
	 * @param  name
	 *         The new name for this task.
	 * @post   The name of this new task is equal to
	 *         the given name.
	 *       | new.getName() == name
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any
	 *         task.
	 *       | ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Return the priority of this task.
	 */
	@Basic @Raw
	public int getPriority() {
		return this.priority;
	}

	/**
	 * Check whether the given priority is a valid priority for
	 * any task.
	 *  
	 * @param  priority
	 *         The priority to check.
	 * @return Always true.
	 *       | result == true
	 */
	@Model
	private static boolean isValidPriority(int priority) {
		return true;
	}

	/**
	 * Set the priority of this task to the given priority.
	 * 
	 * @param  priority
	 *         The new priority for this task.
	 * @post   The priority of this new task is equal to
	 *         the given priority.
	 *       | new.getPriority() == priority
	 * @throws IllegalArgumentException
	 *         The given priority is not a valid priority for any
	 *         task.
	 *       | ! isValidPriority(getPriority())
	 */
	@Raw
	public void setPriority(int priority) throws IllegalArgumentException {
		if (! isValidPriority(priority))
			throw new IllegalArgumentException();
		this.priority = priority;
	}

	/**
	 * Return the activity of this task.
	 */
	@Basic @Raw
	public Statement getStatement() {
		return this.statement;
	}

	/**
	 * Check whether the given statement is a valid statement for
	 * any task.
	 *  
	 * @param  statement
	 *         The activity to check.
	 * @return True if and only if the given statement is effective.
	 *       | result == (statement != null)
	 */
	public static boolean isValidStatement(Statement statement) {
		return (statement != null);
	}

	/**
	 * Set the activity of this task to the given activity.
	 * 
	 * @param  activity
	 *         The new activity for this task.
	 * @post   The activity of this new task is equal to
	 *         the given activity.
	 *       | new.getActivity() == activity
	 * @throws IllegalArgumentException
	 *         The given activity is not a valid activity for any
	 *         task.
	 *       | ! isValidActivity(getActivity())
	 */
	@Raw
	public void setStatement(Statement statement) throws IllegalArgumentException {
		if (! isValidStatement(statement))
			throw new IllegalArgumentException();
		this.statement = statement;
	}

	/**
	 * Return the selected position of this task.
	 */
	@Basic @Raw
	public Position getSelectedPosition() {
		return this.selectedPosition;
	}

	/**
	 * Check whether the given selected position is a valid selected position for
	 * any task.
	 *  
	 * @param  selectedPosition
	 *         The selected position to check.
	 * @return Always true.
	 *       | result == true
	 */
	public static boolean isValidSelectedPosition(Position selectedPosition) {
		return true;
	}

	/**
	 * Set the selected position of this task to the given selected position.
	 * 
	 * @param  selectedPosition
	 *         The new selected position for this task.
	 * @post   The selected position of this new task is equal to
	 *         the given selected position.
	 *       | new.getSelectedPosition() == selectedPosition
	 * @throws IllegalArgumentException
	 *         The given selected position is not a valid selected position for any
	 *         task.
	 *       | ! isValidSelectedPosition(getSelectedPosition())
	 */
	@Raw
	public void setSelectedPosition(Position selectedPosition) throws IllegalArgumentException {
		if (! isValidSelectedPosition(selectedPosition))
			throw new IllegalArgumentException();
		this.selectedPosition = selectedPosition;
	}
	
	/**
	 * Return the executing unit of this task.
	 */
	@Basic @Raw
	public Unit getExecutingUnit() {
		return this.unit;
	}
	
	/**
	 * Check whether the given unit is a valid executing unit for this task.
	 * 
	 * @param  unit
	 *         The unit to check.
	 * @return True if and only if the given unit is not effective or if the unit
	 *         references this task as its task.
	 *       | result == (unit == null || unit.getTask() == this)
	 */
	@Raw
	public boolean isValidExecutingUnit(Unit unit) {
		return unit == null || unit.getTask() == this;
	}
	
	/**
	 * Set the executing unit of this task to the given unit.
	 * 
	 * @param  unit
	 *         The new executing unit of this task.
	 * @post   The new executing unit of this task is equal to the given unit.
	 *       | new.getExecutingUnit() == unit
	 * @throws IllegalArgumentException
	 *         The given unit is not a valid executing unit for this task.
	 *       | !isValidExecutingUnit()
	 */
	@Raw
	public void setExecutingUnit(Unit unit) throws IllegalArgumentException {
		if (!isValidExecutingUnit(unit)) {
			throw new IllegalArgumentException();
		}
		this.unit = unit;
	}
	
	/**
	 * Return the set of all schedulers to which this task is attached.
	 */
	@Basic @Raw
	public Set<Scheduler> getSchedulers() {
		return this.schedulers;
	}
	
	/**
	 * Return whether this task has the given scheduler as one of its schedulers.
	 * 
	 * @param  scheduler
	 *         The scheduler to check.
	 * @return True if and only if the set of schedulers of this task contains the
	 *         given scheduler as one of its schedulers.
	 *       | result == ...
	 */
	@Raw
	public boolean hasAsScheduler(Scheduler scheduler) {
		return getSchedulers().contains(scheduler);
	}
	
	/**
	 * Add the given scheduler to the set of schedulers to which this task is attached.
	 *  
	 * @param  scheduler
	 *         The scheduler to add.
	 * @effect The given scheduler is added to the set of schedulers to which this task
	 *         is attached.
	 *       | ...
	 * @throws IllegalStateException
	 *         This task already references the given scheduler as one of its schedulers.
	 *       | hasAsScheduler(scheduler)
	 */
	@Raw
	public void addScheduler(Scheduler scheduler) throws IllegalStateException {
		if (hasAsScheduler(scheduler))
			throw new IllegalStateException();
		getSchedulers().add(scheduler);
	}
	
	/**
	 * Remove the given scheduler from the set of schedulers to which this task is attached.
	 *  
	 * @param  scheduler
	 *         The scheduler to remove.
	 * @effect The given scheduler is removed from the set of schedulers to which this task
	 *         is attached.
	 *       | ...
	 * @throws IllegalStateException
	 *         This task does not reference the given scheduler as one of its schedulers.
	 *       | !hasAsScheduler(scheduler)
	 */
	@Raw
	public void removeScheduler(Scheduler scheduler) throws IllegalStateException {
		if (!hasAsScheduler(scheduler))
			throw new IllegalStateException();
		getSchedulers().remove(scheduler);
	}

	// OVERRIDE

	/**
	 * Compare this task to the given task.
	 * 
	 * @param  o
	 *         The task to compare with.
	 * @return 0 if the priority of this task is equal to the priority of the
	 *         given task.
	 *       | if (getPriority() == o.getPriority())
	 *       |   then result == 0
	 * @return -1 if the priority of this task is greater than the priority of the
	 *         given task.
	 *       | if (getPriority() > o.getPriority())
	 *       |   then result == 1
	 * @return 1 if the priority of this task is less than the priority of the
	 *         given task.
	 *       | if (getPriority() < o.getPriority())
	 *       |   then result == -1
	 */
	@Override @Raw
	public int compareTo(Task o) {
		if (this.getPriority() == o.getPriority()) {
			return 0;
		} else if (getPriority() > o.getPriority()) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * Return a textual representation of this task.
	 * 
	 * @return A textual representation of this task.
	 *       | result == "Task [name=" + getName() + ", priority=" + getPriority() + "]"
	 */
	@Override @Raw
	public String toString() {
		return "Task [name=" + getName() + ", priority=" + getPriority() + "]";
	}

}
