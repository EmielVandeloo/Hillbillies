package hillbillies.activity;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expression.bool.BooleanExpression;

/**
 * 
 * 
 * @invar  The expression of each conditional must be a valid expression for any
 *         conditional.
 *       | isValidExpression(getExpression())
 * @invar  The if-activity of each conditional must be a valid if-activity for any
 *         conditional.
 *       | isValidIfActivity(getIfActivity())
 * @invar  The else-activity of each conditional must be a valid else-activity for any
 *         conditional.
 *       | isValidElseActivity(getElseActivity())
 * 
 * @author TODO
 *
 */
public class Conditional extends Activity {

	// FIELDS

	/**
	 * Variable registering the expression of this conditional.
	 */
	private BooleanExpression expression;

	/**
	 * Variable registering the if-activity of this conditional.
	 */
	private Activity ifActivity;

	/**
	 * Variable registering the else-activity of this conditional.
	 */
	private Activity elseActivity;
	
	/**
	 * Variable registering the calculated result of this conditional.
	 */
	private boolean result = false;


	// CONSTRUCTORS
	
	/**
	 * Initialize this new conditional with given expression,
	 * if-activity and else-activity.
	 *
	 * @param  expression
	 *         The expression for this new conditional.
	 * @param  ifActivity
	 *         The if-activity for this new conditional.
	 * @effect The expression of this new conditional is set to
	 *         the given expression.
	 *       | this.setExpression(expression)
	 * @effect The if-activity of this new conditional is set to
	 *         the given if-activity.
	 *       | this.setIfActivity(ifActivity)
	 * @effect The else-activity of this new conditional is set to
	 *         the given else-activity.
	 *       | this.setElseActivity(new Void())
	 */
	public Conditional(BooleanExpression expression, Activity ifActivity) 
			throws IllegalArgumentException {
		this(expression, ifActivity, new Void());
	}

	/**
	 * Initialize this new conditional with given expression,
	 * if-activity and else-activity.
	 *
	 * @param  expression
	 *         The expression for this new conditional.
	 * @param  ifActivity
	 *         The if-activity for this new conditional.
	 * @param  elseActivity
	 *         The else-activity for this new conditional.
	 * @effect The expression of this new conditional is set to
	 *         the given expression.
	 *       | this.setExpression(expression)
	 * @effect The if-activity of this new conditional is set to
	 *         the given if-activity.
	 *       | this.setIfActivity(ifActivity)
	 * @effect The else-activity of this new conditional is set to
	 *         the given else-activity.
	 *       | this.setElseActivity(elseActivity)
	 */
	public Conditional(BooleanExpression expression, Activity ifActivity, Activity elseActivity) 
			throws IllegalArgumentException {
		this.setExpression(expression);
		this.setIfActivity(ifActivity);
		this.setElseActivity(elseActivity);
	}


	// GETTERS AND SETTERS

	/**
	 * Return the expression of this conditional.
	 */
	@Basic @Raw
	public BooleanExpression getExpression() {
		return this.expression;
	}

	/**
	 * Check whether the given expression is a valid expression for
	 * any conditional.
	 *  
	 * @param  expression
	 *         The expression to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidExpression(BooleanExpression expression) {
		return false;
	}

	/**
	 * Set the expression of this conditional to the given expression.
	 * 
	 * @param  expression
	 *         The new expression for this conditional.
	 * @post   The expression of this new conditional is equal to
	 *         the given expression.
	 *       | new.getExpression() == expression
	 * @throws ExceptionName_Java
	 *         The given expression is not a valid expression for any
	 *         conditional.
	 *       | ! isValidExpression(getExpression())
	 */
	@Raw
	public void setExpression(BooleanExpression expression) throws IllegalArgumentException {
		if (! isValidExpression(expression))
			throw new IllegalArgumentException();
		this.expression = expression;
	}


	/**
	 * Return the if-activity of this conditional.
	 */
	@Basic @Raw
	public Activity getIfActivity() {
		return this.ifActivity;
	}

	/**
	 * Check whether the given if-activity is a valid if-activity for
	 * any conditional.
	 *  
	 * @param  if-activity
	 *         The if-activity to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidIfActivity(Activity ifActivity) {
		return false;
	}

	/**
	 * Set the if-activity of this conditional to the given if-activity.
	 * 
	 * @param  ifActivity
	 *         The new if-activity for this conditional.
	 * @post   The if-activity of this new conditional is equal to
	 *         the given if-activity.
	 *       | new.getIfActivity() == ifActivity
	 * @throws IllegalArgumentException
	 *         The given if-activity is not a valid if-activity for any
	 *         conditional.
	 *       | ! isValidIfActivity(getIfActivity())
	 */
	@Raw
	public void setIfActivity(Activity ifActivity) throws IllegalArgumentException {
		if (! isValidIfActivity(ifActivity))
			throw new IllegalArgumentException();
		this.ifActivity = ifActivity;
	}

	/**
	 * Return the else-activity of this conditional.
	 */
	@Basic @Raw
	public Activity getElseActivity() {
		return this.elseActivity;
	}

	/**
	 * Check whether the given else-activity is a valid else-activity for
	 * any conditional.
	 *  
	 * @param  else-activity
	 *         The else-activity to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidElseActivity(Activity elseActivity) {
		return false;
	}

	/**
	 * Set the else-activity of this conditional to the given else-activity.
	 * 
	 * @param  elseActivity
	 *         The new else-activity for this conditional.
	 * @post   The else-activity of this new conditional is equal to
	 *         the given else-activity.
	 *       | new.getElseActivity() == elseActivity
	 * @throws IllegalArgumentException
	 *         The given else-activity is not a valid else-activity for any
	 *         conditional.
	 *       | ! isValidElseActivity(getElseActivity())
	 */
	@Raw
	public void setElseActivity(Activity elseActivity) throws IllegalArgumentException {
		if (! isValidElseActivity(elseActivity))
			throw new IllegalArgumentException();
		this.elseActivity = elseActivity;
	}
	

	// OVERRIDE

	@Override
	public void perform() {
		if (! isStarted()) {
			result = getExpression().getResult();
		}
		start();

		if (result) {
			getIfActivity().perform();
		} else {
			getElseActivity().perform();
		}
		
		if (getIfActivity().isFinished() || getElseActivity().isFinished()) {
			finish();
		}
	}

	@Override
	public void revertPerform() {
		super.revertPerform();
		result = false;
		
		getIfActivity().revertPerform();
		getElseActivity().revertPerform();
	}

}
