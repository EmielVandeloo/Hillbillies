package hillbillies.world;

public class OutOfWorldException extends Exception {

	/**
	 * A variable registering the serial version of this exception.
	 */
	private static final long serialVersionUID = 1726783988197035267L;
	
	public OutOfWorldException() {}
	
	public OutOfWorldException(String message) {
		super(message);
	}

}