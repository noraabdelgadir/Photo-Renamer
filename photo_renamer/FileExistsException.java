package photo_renamer;

/**
 * Throws Exception if File that is trying to be renamed already exists.
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */
public class FileExistsException extends Exception {

	/** Default serial ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that creates a FileExistsException
	 * 
	 * @param message
	 *            the message to be displayed in case of an exception
	 */
	public FileExistsException(String message) {
		super(message);
	}

}
