package hillbillies.activity;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of print-statements.
 * 
 * @invar  The text of each print must be a valid text for any
 *         print.
 *       | isValidText(getText())
 * 
 * @author TODO
 *
 */
public class Print extends Activity {
	
	//FIELDS

	/**
	 * Variable registering the text of this print.
	 */
	private String text;
	
	
	// CONSTRUCTOR

	/**
	 * Initialize this new print with given text.
	 *
	 * @param  text
	 *         The text for this new print.
	 * @effect The text of this new print is set to
	 *         the given text.
	 *       | this.setText(text)
	 */
	public Print(String text) throws IllegalArgumentException {
		this.setText(text);
	}

	
	// GETTERS AND SETTERS

	/**
	 * Return the text of this print.
	 */
	@Basic @Raw
	public String getText() {
		return this.text;
	}

	/**
	 * Check whether the given text is a valid text for
	 * any print.
	 *  
	 * @param  text
	 *         The text to check.
	 * @return 
	 *       | result == TODO
	 */
	public static boolean isValidText(String text) {
		return false;
	}

	/**
	 * Set the text of this print to the given text.
	 * 
	 * @param  text
	 *         The new text for this print.
	 * @post   The text of this new print is equal to
	 *         the given text.
	 *       | new.getText() == text
	 * @throws IllegalArgumentException
	 *         The given text is not a valid text for any
	 *         print.
	 *       | ! isValidText(getText())
	 */
	@Raw
	public void setText(String text) throws IllegalArgumentException {
		if (! isValidText(text))
			throw new IllegalArgumentException();
		this.text = text;
	}

	
	// OVERRIDE

	@Override
	public void perform() {
		start();
		System.out.println(getText());
		finish();
	}
	
}
