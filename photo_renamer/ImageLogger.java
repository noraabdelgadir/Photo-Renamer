package photo_renamer;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * Creates a log and serializes Images and Tags
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */
public class ImageLogger {

	/** The logger that logs all the information */
	private static final Logger logger = Logger.getLogger(ImageLogger.class.getName());

	/** The console handler used with the Logger */
	private static final Handler consoleHandler = new ConsoleHandler();

	/** ArrayList of tags */
	private ArrayList<String> arrayOfTags = new ArrayList<String>();

	/** ArrayList of Images */
	private ArrayList<Image> allImages = new ArrayList<Image>();

	/**
	 * Singleton Design Pattern because there should be one instance of
	 * ImageLogger
	 */
	private static final ImageLogger instance = new ImageLogger(
			new File("").getAbsolutePath() + "\\src\\photo_renamer.ser");

	/**
	 * Constructor for ImageLogger
	 * 
	 * @param filePath
	 *            the path of the ser file
	 */
	private ImageLogger(String filePath) {
		logger.setLevel(Level.ALL);
		consoleHandler.setLevel(Level.ALL);
		logger.addHandler(consoleHandler);

		File file = new File(filePath);

		// if the file exists, get information from it
		if (file.exists()) {
			try {
				readFromFile(filePath);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			// otherwise, make a new file
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the instance of ImageLogger
	 * 
	 * @return instance the only instance of ImageLogger
	 */
	public static ImageLogger getInstance() {
		return instance;
	}

	/**
	 * Reads existing information from previous sessions, such as tags, to
	 * restore tags, Images, renamed Images, and reverted Images.
	 * 
	 * @param path
	 *            path of the ser file serialized info will be saved to
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void readFromFile(String path) throws ClassNotFoundException {
		try {
			InputStream file = new FileInputStream(path);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);

			// deserialize tags, images, renamed and reverted images
			Tags.setAllTags(Tags.removeDuplicates((ArrayList<String>) (input.readObject())));
			Image.setImages(Tags.removeDuplicates((ArrayList<Image>) (input.readObject())));
			ImageEditor.setRenamedImages((ArrayList<Image>) input.readObject());
			ImageEditor.setRevertedImages((Map<Image, Integer>) input.readObject());
			input.close();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "Cannot read from input.", ex);
		}
	}

	/**
	 * Saves records, including Images and tags, to a ser file
	 * 
	 * @param filePath
	 *            the path of the ser file to be saved to
	 * @throws IOException
	 */
	public void saveToFile(String filePath) throws IOException {

		OutputStream file = new FileOutputStream(filePath);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);

		// Serialize the tags
		output.writeObject(Tags.getAllTags());

		// Serialize the images
		output.writeObject(Image.getImages());

		// Serialize renamed images
		output.writeObject(ImageEditor.getRenamedImages());

		// Serialize reverted images
		output.writeObject(ImageEditor.getRevertedImages());
		output.close();
	}

	/**
	 * Saves records, including Images and tag, in a txt file
	 * 
	 * @param filePath
	 *            the path of the txt file to be saved to
	 * @throws IOException
	 */
	public void saveToTXTFile(String filePath) throws IOException {

		FileWriter write = new FileWriter(filePath);
		PrintWriter writer = new PrintWriter(write);
		Date date = new Date();
		writer.write(date.toString() + "\n");

		// adds tags in a list form
		String tags = "Tags: [";
		arrayOfTags = Tags.removeDuplicates(arrayOfTags);
		for (String tag : arrayOfTags)
			tags += tag + ", ";
		if (arrayOfTags.size() > 1)
			tags = tags.substring(0, tags.length() - 2) + "]\n";
		else
			tags += "]\n";
		writer.write(tags);

		// adds images in a readable way
		String images = "Images: \n";
		allImages = Tags.removeDuplicates(allImages);
		for (Image image : allImages)
			images += image.getName() + ", " + image.getLocation() + ", " + image.getAllNamesString() + "\n";
		writer.write(images);

		// logs every time an image is renamed
		ImageEditor.setRenamedImages(Tags.removeDuplicates(ImageEditor.getRenamedImages()));
		for (Image image : ImageEditor.getRenamedImages()) {
			if (image.getAllNames().size() - 2 >= 0 && !writer.toString().contains(image.getAllNamesString())) {
				String oldName = image.getOldName(image.getAllNames().size() - 2);
				writer.write(image.getEditDate() + ": Renamed " + oldName + " to " + image.getName() + "\n");
			}
		}

		// logs every time an image is reverted back to an older name
		for (Image image : ImageEditor.getRevertedImages().keySet()) {
			if (image.getAllNames().size() > 0 && !writer.toString().contains(image.getAllNamesString())) {
				String oldName = image.getOldName(ImageEditor.getRevertedImages().get(image));
				writer.write(image.getEditDate() + ": Reverted " + oldName + " to " + image.getName() + "\n");
			}
		}
		writer.close();
	}

	@Override
	public String toString() {
		String result = "";
		for (Image image : allImages) {
			result += image.toString() + ": " + image.getAllNames() + "\n";
		}
		return result;
	}

	/**
	 * Adds Images and Tags so they can be logged
	 * 
	 * @param tags
	 *            the tags to be logged
	 * @param arrayList
	 *            the images to be logged
	 */
	public void add(ArrayList<String> tags, ArrayList<Image> arrayList) {
		arrayOfTags.addAll(tags);
		allImages.addAll(arrayList);

		// logs all the tags
		logger.log(Level.FINE, "Added tags " + arrayOfTags);

		// logs all the images
		logger.log(Level.FINE, "Added images " + allImages);
	}

	/**
	 * Loads information from previous sessions and saves information from new
	 * sessions.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void loadInformation() throws ClassNotFoundException, IOException {
		String txtPath = new File("").getAbsolutePath() + "\\src\\photo_renamer.txt";
		String path = new File("").getAbsolutePath() + "\\src\\photo_renamer.ser";

		// saves new Tags and Images
		add(Tags.getAllTags(), Image.getImages());
		saveToFile(path);

		// saves to the txt file
		saveToTXTFile(txtPath);
	}
}