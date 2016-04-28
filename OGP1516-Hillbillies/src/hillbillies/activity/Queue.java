package hillbillies.activity;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * 
 * 
 * @invar  The list of each Queue must be a valid list for any
 *         Queue.
 *       | isValidList(getList()) 
 *
 * @author 
 *
 */
public class Queue extends Activity {

	/**
	 * Variable registering the list of this Queue.
	 */
	private ArrayList<Activity> list;

	/**
	 * Initialize this new Queue with given list.
	 *
	 * @param  list
	 *         The list for this new Queue.
	 * @effect The list of this new Queue is set to
	 *         the given list.
	 *       | this.setList(list)
	 */
	public Queue(ArrayList<Activity> list) throws IllegalArgumentException {
		this.setList(list);
	}


	/**
	 * Return the list of this Queue.
	 */
	@Basic @Raw
	public ArrayList<Activity> getList() {
		return this.list;
	}

	/**
	 * Check whether the given list is a valid list for
	 * any Queue.
	 *  
	 * @param  list
	 *         The list to check.
	 * @return 
	 *       | result == (list != null) && (list.size() >= 1)
	 */
	public static boolean isValidList(ArrayList<Activity> list) {
		return (list != null) && (list.size() >= 1);
	}

	/**
	 * Set the list of this Queue to the given list.
	 * 
	 * @param  list
	 *         The new list for this Queue.
	 * @post   The list of this new Queue is equal to
	 *         the given list.
	 *       | new.getList() == list
	 * @throws ExceptionName_Java
	 *         The given list is not a valid list for any
	 *         Queue.
	 *       | ! isValidList(getList())
	 */
	@Raw
	public void setList(ArrayList<Activity> list) throws IllegalArgumentException {
		if (! isValidList(list))
			throw new IllegalArgumentException();
		this.list = list;
	}
	
	//TODO commentaar en defensief
	public int currentAt = 0;


	@Override
	public void perform() {
		start();
		
		getList().get(currentAt).perform();
		if (getList().get(currentAt).isFinished()) {
			currentAt++;
			if (currentAt >= getList().size()) {
				finish();
			}
		}
	}
	
	@Override
	public void revertPerform() {
		super.revertPerform();
		
		currentAt = 0;
		for (Activity activity : list) {
			activity.revertPerform();
		}
	}

}
