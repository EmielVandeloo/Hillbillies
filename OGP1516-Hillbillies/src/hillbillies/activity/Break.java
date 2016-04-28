package hillbillies.activity;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * 
 * 
 * @invar  The loop of each break-statement must be a valid loop for any
 *         break-statement.
 *       | isValidLoop(getLoop())
 *       
 * @author TODO
 *
 */
public class Break extends Activity {
	
	// METHODS

	/**
	 * Variable registering the loop of this break-statement.
	 */
	private Loop loop;
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new break-statement with given loop.
	 *
	 * @param  loop
	 *         The loop for this new break-statement.
	 * @effect The loop of this new break-statement is set to
	 *         the given loop.
	 *       | this.setLoop(loop)
	 */
	public Break(Loop loop) throws IllegalArgumentException {
		this.setLoop(loop);
	}
	
	
	// GETTERS AND SETTERS

	/**
	 * Return the loop of this break-statement.
	 */
	@Basic @Raw
	public Loop getLoop() {
		return this.loop;
	}

	/**
	 * Check whether the given loop is a valid loop for
	 * any break-statement.
	 *  
	 * @param  loop
	 *         The loop to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidLoop(Loop loop) {
		return false;
	}

	/**
	 * Set the loop of this break-statement to the given loop.
	 * 
	 * @param  loop
	 *         The new loop for this break-statement.
	 * @post   The loop of this new break-statement is equal to
	 *         the given loop.
	 *       | new.getLoop() == loop
	 * @throws IllegalArgumentException
	 *         The given loop is not a valid loop for any
	 *         break-statement.
	 *       | ! isValidLoop(getLoop())
	 */
	@Raw
	public void setLoop(Loop loop) throws IllegalArgumentException {
		if (! isValidLoop(loop))
			throw new IllegalArgumentException();
		this.loop = loop;
	}
	
	
	// OVERRIDE
	
	@Override
	public void perform() {
		start();
		getLoop().finish();
		finish();
	}
	
}
