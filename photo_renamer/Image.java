package photo_renamer;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Stores information about images like their name(s), location, file, and tags.
 * This class includes the Images to be renamed (or not).
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */
public class Image implements Serializable {

	/** Image class' default serial ID */
	private static final long serialVersionUID = 1L;

	/** A List of the Image's tags */
	private ArrayList<String> tags;

	/** The location of the Image in the User's OS. */
	private String location;

	/** The name of the Image. */
	private String name;

	/** The file associated with the Image. */
	private File file;

	/** A List of all the names of the Image. */
	private ArrayList<String> allNames = new ArrayList<String>();

	/** List of all Images the user has selected */
	private static ArrayList<Image> images = new ArrayList<Image>();

	/** Date the Image was edited */
	private Date editDate;

	/**
	 * Constructor method for Image
	 *
	 * @param file
	 *            the image file
	 */
	public Image(File file) {
		this.file = file;
		this.name = file.getName();
		this.location = file.getAbsolutePath();
		this.tags = new ArrayList<String>();
		Tags.getAllTags().addAll(this.tags);
		allNames.add(this.name);
		editDate = new Date();

		// adds all the previous names to the Image
		for (Image image : images)
			if (image.getAllNames().contains(this.name))
				this.allNames = image.getAllNames();

		if (!images.contains(this))
			images.add(this);
	}

	/**
	 * Gets the list of all Images
	 * 
	 * @return the list of all Images
	 */
	public static ArrayList<Image> getImages() {
		return images;
	}

	/**
	 * Sets the list of all Images
	 * 
	 * @param images
	 *            the list of Images for all Images to be set to
	 */
	public static void setImages(ArrayList<Image> images) {
		Image.images = images;
	}

	/**
	 * Gets the tags of a specific Image
	 * 
	 * @return this.tags, the tags associated with a specific Image
	 */
	public ArrayList<String> getTags() {
		return this.tags;
	}

	/**
	 * Sets the tags of an Image based on the parameter tags
	 * 
	 * @param tags
	 *            the tags the tag list should be set to
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	/**
	 * Sets the location of an Image based on the parameter location
	 * 
	 * @param location
	 *            the location the Image's location should be set to
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Sets the name of an Image based on the parameter name
	 * 
	 * @param name
	 *            the name the Image should be set to
	 */
	public void setName(String name) {
		
		// adds to all names
		if (!allNames.contains(name))
			allNames.add(name);

		// renames the file too
		String path = this.file.getAbsolutePath();
		String newName = path.substring(0, path.length() - this.name.length()) + name;
		this.file.renameTo(new File(newName));
		this.name = name;

		// changes the date it was edited
		this.editDate = new Date();
	}

	/**
	 * Gets the date the image was edited
	 * 
	 * @return the date/time the Image was edited
	 */
	public Date getEditDate() {
		return editDate;
	}

	/**
	 * Gets the file of a specific Image
	 * 
	 * @return this.file, the file associated with a specific Image
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * Adds new tag to Image and master list of tags
	 * 
	 * @param tag
	 *            the tag to be added to the Image
	 */
	public void addNewTag(String tag) {

		// make sure the tag doesn't already exist
		if (!this.tags.contains(tag)) {
			Tags.addToAllTags(tag);
			this.tags.add(tag);
		}
	}

	/**
	 * Deletes tag from Image
	 * 
	 * @param tag
	 *            the tag to be deleted from the Image
	 */
	public void deleteTag(String tag) {
		this.tags.remove(tag.toLowerCase());
	}

	/**
	 * Gets the name of the Image
	 * 
	 * @return this.name the current name of the Image
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the location of the Image
	 * 
	 * @return this.location the current location of the Image
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * Gets one of the old names of the Image from a specific index in case one
	 * wants to revert to an older version
	 * 
	 * @param index
	 *            the index of the name desired
	 * @return the name at that index from allNames
	 */
	public String getOldName(int index) {

		// gets the old name from the index
		return allNames.get(index);
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Lists all the names of the Image in a format where the index is followed
	 * by the name so that if the user wants to select a name, they know which
	 * index it belongs to.
	 * 
	 * @return names in a nice format
	 */
	public String getAllNamesString() {
		String names = "";
		for (String name : allNames)

			// shows the index corresponding to each image
			names += allNames.indexOf(name) + ": " + name + "\n";

		// gets rid of the last new line character
		return names.trim();
	}

	/**
	 * Gets all the names of an Image
	 * 
	 * @return allNames all the names of the Image
	 */
	public ArrayList<String> getAllNames() {
		return allNames;
	}

	/**
	 * Sets allNames of the Image
	 * 
	 * @param allNames
	 *            all the names of the Image
	 */
	public void setAllNames(ArrayList<String> allNames) {
		this.allNames = allNames;
	}

	/**
	 * Returns an ArrayList of all Images in a directory that contain the tag in
	 * the parameter.
	 * 
	 * @param tag
	 *            the tag we want to see if an Image contains
	 * @return withTag ArrayList of images that contain the tag
	 */
	public static ArrayList<Image> imagesWithTag(String tag) {
		ArrayList<Image> withTag = new ArrayList<Image>();

		// if img contains the tag, add it to the ArrayList
		for (Image img : images)
			if (img.getTags().contains(tag))
				withTag.add(img);
		return withTag;
	}
}