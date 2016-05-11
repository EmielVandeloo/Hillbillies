package hillbillies.statement;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.bool.BooleanExpression;

/**
 * 
 * 
 * @invar  The boolean expression of each loop must be a valid boolean expression for any
 *         loop.
 *       | isValidBooleanExpression(getBooleanExpression())
 * @invar  The activity of each loop must be a valid activity for any
 *         loop.
 *       | isValidActivity(getActivity())
 * 
 * @author TODO
 *
 */
public class Loop extends Activity {

	/**
	 * Variable registering the boolean expression of this loop.
	 */
	private BooleanExpression booleanExpression;

	/**
	 * Variable registering the activity of this loop.
	 */
	private Activity activity;


	/**
	 * Initialize this new loop with given activity.
	 *
	 * @param  booleanExpression
	 *         The boolean expression for this new loop.
	 * @param  activity
	 *         The activity for this new loop.
	 * @effect The boolean expression of this new loop is set to
	 *         the given boolean expression.
	 *       | this.setBooleanExpression(booleanExpression)
	 * @effect The activity of this new loop is set to
	 *         the given activity.
	 *       | this.setActivity(activity)
	 */
	public Loop(BooleanExpression booleanExpression, Activity activity) throws IllegalArgumentException {
		this.setBooleanExpression(booleanExpression);
		this.setActivity(activity);
	}


	/**
	 * Return the boolean expression of this loop.
	 */
	@Basic @Raw
	public BooleanExpression getBooleanExpression() {
		return this.booleanExpression;
	}

	/**
	 * Check whether the given boolean expression is a valid boolean expression for
	 * any loop.
	 *  
	 * @param  boolean expression
	 *         The boolean expression to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidBooleanExpression(BooleanExpression booleanExpression) {
		return false;
	}

	/**
	 * Set the boolean expression of this loop to the given boolean expression.
	 * 
	 * @param  booleanExpression
	 *         The new boolean expression for this loop.
	 * @post   The boolean expression of this new loop is equal to
	 *         the given boolean expression.
	 *       | new.getBooleanExpression() == booleanExpression
	 * @throws ExceptionName_Java
	 *         The given boolean expression is not a valid boolean expression for any
	 *         loop.
	 *       | ! isValidBooleanExpression(getBooleanExpression())
	 */
	@Raw
	public void setBooleanExpression(BooleanExpression booleanExpression) throws IllegalArgumentException {
		if (! isValidBooleanExpression(booleanExpression))
			throw new IllegalArgumentException();
		this.booleanExpression = booleanExpression;
	}


	/**
	 * Return the activity of this loop.
	 */
	@Basic @Raw
	public Activity getActivity() {
		return this.activity;
	}

	/**
	 * Check whether the given activity is a valid activity for
	 * any loop.
	 *  
	 * @param  activity
	 *         The activity to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidActivity(Activity activity) {
		return false;
	}

	/**
	 * Set the activity of this loop to the given activity.
	 * 
	 * @param  activity
	 *         The new activity for this loop.
	 * @post   The activity of this new loop is equal to
	 *         the given activity.
	 *       | new.getActivity() == activity
	 * @throws IllegalArgumentException
	 *         The given activity is not a valid activity for any
	 *         loop.
	 *       | ! isValidActivity(getActivity())
	 */
	@Raw
	public void setActivity(Activity activity) throws IllegalArgumentException {
		if (! isValidActivity(activity))
			throw new IllegalArgumentException();
		this.activity = activity;
	}

	@Override
	public void perform() {
		if (! isStarted() && ! getBooleanExpression().getResult()) {
			finish();
		}
		start();

		if (! isFinished()) {
			getActivity().perform();
		}
		
		if (getActivity().isFinished()) {
			getActivity().revertPerform();
			revertStart();
		}
	}

	@Override
	public void revertPerform() {
		super.revertPerform();
		getActivity().revertPerform();
	}

}
