package photo_renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 * Chooses files/directories for doing various actions such as listing the
 * images in a directory or selecting to use in the Image class.
 * 
 * @author Nora
 * @version %I%, %G%
 * 
 */
public class FileChooser {
	/**
	 * Returns a list of all the images in a directory after iterating over all
	 * the files in it and checking which ones are images.
	 * 
	 * @return images all the images in the directory
	 * @see Image
	 */
	public static ArrayList<String> listImages() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(null);

		// empty list of Images to add the images to
		ArrayList<String> images = new ArrayList<String>();

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			// iterate over the files in that directory
			for (int i = 0; i < file.listFiles().length; i++) {

				// get the names of all the files/directories in that directory
				File[] files = file.listFiles();

				// if the file is an image, add it to the list
				if (files[i].isFile())
					if (isImage(files[i]))
						images.add(files[i].getName());
			}
		}
		return images;
	}

	/**
	 * Checks the extension of a file to make sure it is an image type.
	 * 
	 * @param file
	 *            the file to be checked
	 * @return true if the file is of type image
	 */
	public static boolean isImage(File file) {
		List<String> imageType = new ArrayList<String>();

		// add common image types to the List
		imageType.add("jpg");
		imageType.add("png");
		imageType.add("jpeg");
		imageType.add("tiff");
		imageType.add("bmp");
		imageType.add("gif");

		String name = file.getName();

		// to check the extension of the image
		int period = name.lastIndexOf(".");
		String type = name.substring(period + 1);

		// return true if the file is an image
		return (imageType.contains(type));
	}

	/**
	 * Selects and returns the image selected by the user. If the user does not
	 * choose an image file, make them try again.
	 * 
	 * @return image the Image selected by the user
	 * @throws NotImageFileException
	 * @see Image
	 */
	public static Image selectImage() throws NotImageFileException {
		Image image = null;
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			// the user selects an image
			File file = fileChooser.getSelectedFile();
			if (isImage(file))
				image = new Image(file);

			// if the file is not an image file, throw Exception
			else
				throw new NotImageFileException("This is not an image file. Try again.");
		}
		return image;
	}

}