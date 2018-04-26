package photo_renamer;

/**
 * Throws Exception if File selected is not an image File
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */
public class NotImageFileException extends Exception {

	/** Default serial ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that creates a NotImageFileException
	 * 
	 * @param message
	 *            the message to be displayed in case of an exception
	 */
	public NotImageFileException(String message) {
		super(message);
	}
}
