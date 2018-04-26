package photo_renamer;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * Renames Images based on their tags after the user selects a group of tags.
 * Can also revert name back to an older version.
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */

public class ImageEditor implements Serializable {
	/** The ID from serializing the class */
	private static final long serialVersionUID = 4927785427840604389L;

	/** Keeps track of renamed images **/
	private static ArrayList<Image> renamedImages = new ArrayList<Image>();

	/** Keeps track of reverted images **/
	private static Map<Image, Integer> revertedImages = new HashMap<Image, Integer>();

	/**
	 * Sets renamedImages
	 * 
	 * @param renamedImages
	 *            the ArrayList of renamedImages
	 */
	public static void setRenamedImages(ArrayList<Image> renamedImages) {
		ImageEditor.renamedImages = renamedImages;
	}

	/**
	 * Sets revertedImages
	 * 
	 * @param revertedImages
	 *            the Map of revertedImages
	 */
	public static void setRevertedImages(Map<Image, Integer> revertedImages) {
		ImageEditor.revertedImages = revertedImages;
	}

	/**
	 * Gets revertedImages
	 * 
	 * @return revertedImages the Map of revertedImages
	 */
	public static Map<Image, Integer> getRevertedImages() {
		return revertedImages;
	}

	/**
	 * Gets renamedImages
	 * 
	 * @return renamedImages the ArrayList of renamedImages
	 */
	public static ArrayList<Image> getRenamedImages() {
		return renamedImages;
	}

	/**
	 * Renames the image based on the set of tags that the image contains,
	 * typically the ones selected by the user. Renames the image by using the
	 * tags and putting @ in front of each tag and a space afterwards,
	 * separating each tag.
	 * 
	 * @param image
	 *            the Image to be renamed
	 */
	public static void renameImage(Image image) throws FileAlreadyExistsException {
		if (!image.getTags().isEmpty()) {
			String newName = "";

			// uses the tags to rename
			for (String t : image.getTags()) {

				// adds the prefix and space
				newName += "@" + t + " ";
			}

			// to get the original extension of the image
			int period = image.getName().lastIndexOf(".");
			newName = newName.trim() + image.getName().substring(period);

			if (Image.getImages().contains(newName))
				throw new FileAlreadyExistsException("File already exists, try again");
			else {
				ArrayList<String> names = image.getAllNames();

				// sets the Image with its new name and location
				String location = image.getLocation().substring(0,
						image.getLocation().length() - image.getName().length());
				image.setName(newName);
				image.setLocation(location + newName);
				image.setAllNames(names);

				// adds to renamedImages
				renamedImages.add(image);
			}
		}
	}

	/**
	 * Reverts image to one of its older name based on the index provided by the
	 * user. The index is used to determine which name the image should revert
	 * to from the list of allNames it contains.
	 * 
	 * @param image
	 *            the Image to be renamed
	 * @param index
	 *            the index which determines which name it is reverted to
	 */
	public static void revertName(Image image, int index) {
		String location = image.getLocation().substring(0, image.getLocation().length() - image.getName().length());
		ArrayList<String> names = image.getAllNames();
		image.setName(image.getOldName(index));
		image.setLocation(location + image.getName());
		image.setAllNames(names);

		// adds to revertedImages
		revertedImages.put(image, index);
	}

	/**
	 * Adds the selected tags to the image to use to later rename the image
	 * based on.
	 * 
	 * @param image
	 *            the Image to add the selected tags to
	 * @param selectedTags
	 *            the ArrayList of selected tags the user has selected
	 */
	public void selectTags(Image image, ArrayList<String> selectedTags) {
		for (String tag : selectedTags)
			image.addNewTag(tag);
	}
}
