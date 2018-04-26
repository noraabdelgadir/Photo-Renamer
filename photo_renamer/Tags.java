package photo_renamer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores Images and their tags in a Map and stores the master list of tags.
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */
public class Tags implements Serializable {

	/** Default Serial ID of the Tags class */
	private static final long serialVersionUID = 1L;

	/** The master list of tags from all Images */
	private static ArrayList<String> allTags = new ArrayList<String>();

	/**
	 * Sets the allTags variable of the MapTag class, setter method.
	 * 
	 * @param allTags
	 *            the master list of tags
	 */
	public static void setAllTags(ArrayList<String> allTags) {
		Tags.allTags = allTags;
	}

	/**
	 * Gets the ArrayList of all tags from every Image
	 * 
	 * @return allTags the master list of all tags
	 */
	public static ArrayList<String> getAllTags() {
		return allTags;
	}

	/**
	 * Adds a tag to the master list of tags, allTags
	 * 
	 * @param tag
	 *            the tag to be added
	 */
	public static void addToAllTags(String tag) {
		if (!allTags.contains(tag))
			allTags.add(tag);
	}

	/**
	 * Deletes a specific tag from the master list, allTags.
	 * 
	 * @param tag
	 *            the tag to be removed
	 */
	public static void deleteFromAllTags(String tag) {
		allTags.remove(tag);
	}

	/**
	 * Removes all the tags from all the Images and the master list, allTags, of
	 * tags
	 */
	public static void removeAllTags() {

		// set tag ArrayLists to empty ones
		allTags = new ArrayList<String>();
		for (Image image : Image.getImages())
			image.setTags(new ArrayList<String>());
	}

	/**
	 * Takes an ArrayList and removes any duplicates within it.
	 * 
	 * @param <I>
	 *            the type of items in the ArrayList
	 * 
	 * @param arrayList
	 *            the list of I
	 * @return noDuplicates the list of I with no duplicates
	 */
	public static <I> ArrayList<I> removeDuplicates(ArrayList<I> arrayList) {
		ArrayList<I> noDuplicates = new ArrayList<I>();
		for (I tag : arrayList)
			if (!noDuplicates.contains(tag))
				noDuplicates.add(tag);
		return noDuplicates;
	}
}
