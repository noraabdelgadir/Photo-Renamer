package photo_renamer;

import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterates over all the tags. Implements design pattern Iterator.
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */
public class TagsIterator implements Iterator<String> {

	/** The index of the next tag to return. */
	private int current = 0;

	/** ArrayList of all tags */
	private List<String> tags;

	/**
	 * Constructor for TagsIterator
	 */
	public TagsIterator() {
		tags = Tags.getAllTags();
	}

	/**
	 * Returns the first item in the master list of tags, tags
	 * 
	 * @param tag
	 *            the tag to be added
	 */
	public String firstTag() {
		return tags.get(0);
	}

	/**
	 * Returns the next item in the master list of tags, tags
	 * 
	 * @param currentItem
	 *            the current item in the list of tags
	 */
	public String next(String currentItem) {
		return tags.get(tags.indexOf(currentItem) + 1);
	}

	/**
	 * Returns the next item in the master list of tags, tags
	 * 
	 * @param currentItem
	 *            the current item in the list of tags
	 */
	public boolean hasNext(String currentItem) {
		return tags.get(tags.indexOf(currentItem) + 1) == null;
	}

	/**
	 * Returns whether the currentItem is the last item in the list of tags
	 * 
	 * @param currentItem
	 *            the current item in the list of tags
	 */
	public boolean isDone(String currentItem) {
		return tags.get(tags.indexOf(currentItem)) == tags.get(-1);
	}

	/**
	 * Returns whether there is another tag to return.
	 * 
	 * @return true if there is a tag to return, false otherwise
	 */
	@Override
	public boolean hasNext() {
		return current < tags.size();
	}

	/**
	 * Returns the next Tag.
	 * 
	 * @return the next Tag.
	 */
	@Override
	public String next() {
		String tag;

		// Gets the tag from a specific index and throws an Exception if the
		// index is the size of the array or greater
		try {
			tag = tags.get(current);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
		current += 1;
		return tag;
	}

	/**
	 * Removes the tag just returned. Unsupported.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Not supported.");
	}

}
