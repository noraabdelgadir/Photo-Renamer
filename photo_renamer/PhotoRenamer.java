package photo_renamer;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Graphical User Interface of the PhotoRenamer application
 * 
 * @author Nora
 * @version %I%, %G%
 *
 */
public class PhotoRenamer implements ActionListener {

	/** The Image actions are going to be performed on */
	private Image image = null;

	/** JLabel for the name of the Image */
	private JLabel imageName;

	/** Label for selecting tags */
	private JLabel selectTags = new JLabel(" Select the tags you want to rename with: ");

	/** Button for renaming */
	private JButton renameButton = new JButton("Rename");

	/** Panel for storing the tags' checkboxes */
	private JPanel checkboxPanel = new JPanel();

	/** The frame where the image is shown with tags */
	private JFrame tagFrame = new JFrame("Photo Renamer");

	/** Button for selecting an image */
	private JButton selectImage = new JButton("Select Image");

	/** JFrame for choosing an image */
	private JFrame chooseImage = new JFrame("Select Image");

	/** Button for viewing images in a directory */
	private JButton viewImagesButton = new JButton("View Images in Directory");

	/** Button for adding tags when user is finished */
	private JButton addTagButton = new JButton("Add Tags");

	/** Button for deleting tags */
	private JButton deleteTagButton = new JButton("Delete Tags");

	/** Button for when the user is done adding buttons */
	private JButton addDoneButton = new JButton("Done");

	/** Button for when the user is done reverting */
	private JButton revertDoneButton = new JButton("Done");

	/** Button to go to the revert frame */
	private JButton revertButton = new JButton("Revert Name");

	/** JTextArea where user enters tags */
	private JTextArea tags;

	/** JTextArea where the user inserts the index to revert to */
	private JTextArea index;

	/** ArrayList of checkboxes for tags */
	private ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();

	/** ArrayList of all the text contained in checkboxes */
	private ArrayList<String> checkboxText = new ArrayList<String>();

	/** To log all the information and load from previous sessions */
	private ImageLogger logger;

	/** Tags Iterator to iterate over tags */
	TagsIterator listOfTags;

	/**
	 * Constructor for PhotoRenamer
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public PhotoRenamer() throws ClassNotFoundException, IOException {
		logger = ImageLogger.getInstance();
		logger.loadInformation();
		listOfTags = new TagsIterator();

		chooseImage.setSize(500, 500);
		JPanel panel = new JPanel();
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.append("  Choose whether you want to select an image or see all images in a directory:   \n\n\n");

		// let user either select an image or view the images in a directory
		selectImage.addActionListener(this);
		viewImagesButton.addActionListener(this);
		panel.add(selectImage);
		panel.add(viewImagesButton);
		chooseImage.add(textArea, "Center");
		chooseImage.add(panel, "South");
		chooseImage.pack();
		chooseImage.setVisible(true);
	}

	/**
	 * Creates a JFrame where the Image and buttons for performing actions is
	 * shown
	 * 
	 * @return JFrame the frame where the Image is shown along with the buttons
	 *         for performing actions
	 * @throws NotImageFileException
	 */
	public JFrame imageEditor() throws NotImageFileException {
		tagFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tagFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				try {
					logger.loadInformation();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});

		// adds the actionListeners to all the buttons
		addTagButton.addActionListener(this);
		renameButton.addActionListener(this);
		revertButton.addActionListener(this);
		deleteTagButton.addActionListener(this);

		// adds the buttons to the buttonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(renameButton, "South");
		buttonPanel.add(revertButton, "South");
		buttonPanel.add(addTagButton, "South");
		buttonPanel.add(deleteTagButton, "South");
		tagFrame.add(buttonPanel, "South");

		// adding the image to the frame
		BufferedImage img = null;
		try {
			image = FileChooser.selectImage();
			img = ImageIO.read(image.getFile());
		} catch (IOException e1) {

		}

		ImageIcon icon = new ImageIcon(img);
		JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);

		imageName = new JLabel("  " + image.getName());

		// checkbox panel for the tags
		checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
		checkboxPanel.add(imageName);
		checkboxPanel.add(selectTags, "North");

		// create checkboxes with the tags
		while (listOfTags.hasNext()) {
			JCheckBox name = new JCheckBox(listOfTags.next());
			if (!checkboxes.contains(name)) {
				checkboxPanel.add(name);
				checkboxText.add(name.getText());
				checkboxes.add(name);
			}
		}

		JScrollPane tagPane = new JScrollPane(checkboxPanel);
		tagFrame.add(tagPane, "East");
		tagFrame.add(imageLabel);
		tagFrame.pack();
		return tagFrame;
	}

	/**
	 * Creates a JFrame for addition of tags
	 * 
	 * @return addTag the JFrame for adding tags
	 */
	public JFrame addTag() {
		JFrame addTag = new JFrame("Add Tags");
		JLabel enterTags = new JLabel("Add tags, separated by commas: \n\n");
		tags = new JTextArea(10, 5);
		JScrollPane pane = new JScrollPane(tags);
		addTag.add(enterTags, "North");
		addTag.add(pane, "Center");
		addDoneButton.addActionListener(this);
		addTag.add(addDoneButton, "South");
		addTag.pack();
		return addTag;
	}

	/**
	 * Creates a JFrame for reverting Image's name
	 * 
	 * @return revertFrame the JFrame for reverting
	 */
	public JFrame revertFrame() {
		JFrame revertFrame = new JFrame("Revert Name");
		JPanel revert = new JPanel();
		JTextArea imageNames = new JTextArea();
		imageNames.setEditable(false);
		imageNames.append("All of image's names: \n");

		// include all the previous names of the Image
		imageNames.append(image.getAllNamesString());
		JScrollPane scrollPane = new JScrollPane(imageNames);
		revert.add(scrollPane, "Center");
		JLabel enterIndex = new JLabel(
				"Enter the index of the name you wish to revert to \n (Make sure to enter a valid index) \n:");
		index = new JTextArea(2, 4);
		JScrollPane pane = new JScrollPane(index);
		revert.add(enterIndex, "North");
		revert.add(pane, "South");
		revertFrame.add(revert, "North");
		revertDoneButton.addActionListener(this);
		revertFrame.add(revertDoneButton, "South");
		revertFrame.pack();
		return revertFrame;
	}

	/**
	 * Creates a JFrame where the Images in a specific directory are showing in
	 * a list form
	 * 
	 * @return viewImages the frane with the list of Images
	 */
	public JFrame viewImages() {
		JFrame viewImages = new JFrame("Images in Directory");
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.append("List of Images in this directory: \n");
		for (String image : FileChooser.listImages())
			textArea.append(image + "\n");
		JScrollPane scrollPane = new JScrollPane(textArea);
		viewImages.add(scrollPane, "Center");
		viewImages.pack();
		return viewImages;
	}

	/** Action performed method where the actions are performed */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == renameButton) {
			image.setTags(new ArrayList<String>());
			for (JCheckBox cb : checkboxes)

				// if the checkbox is selected, add it to the tags to rename
				// with
				if (cb.isSelected() && !image.getTags().contains(cb.getText()))
					image.addNewTag(cb.getText());
			try {

				// rename Image
				ImageEditor.renameImage(image);
			} catch (FileAlreadyExistsException e1) {
				e1.printStackTrace();
			}
			checkboxPanel.removeAll();

			// remove the panel's contents to add new name
			imageName = new JLabel("  " + image.getName());
			checkboxPanel.add(imageName);
			checkboxPanel.add(selectTags);
			for (JCheckBox cb : checkboxes) {
				checkboxPanel.add(cb);
			}

			tagFrame.revalidate();
			tagFrame.repaint();
		}

		else if (e.getSource() == revertButton)
			revertFrame().setVisible(true);

		else if (e.getSource() == selectImage)
			try {

				// if an image is selected, show imageEditor frame
				imageEditor().setVisible(true);
			} catch (NotImageFileException e1) {
				e1.printStackTrace();
			}
		else if (e.getSource() == viewImagesButton)
			viewImages().setVisible(true);

		else if (e.getSource() == addTagButton)
			addTag().setVisible(true);

		else if (e.getSource() == revertDoneButton) {

			// get the user's integer
			int i = Integer.parseInt(index.getText().trim());
			if (i < image.getAllNames().size()) {
				if (image.getName() != image.getOldName(i)) {

					// revert the name of the image
					ImageEditor.revertName(image, i);
					checkboxPanel.removeAll();

					// change the image name on the frame
					imageName = new JLabel("  " + image.getName());

					// add everything to the panel again
					checkboxPanel.add(imageName);
					checkboxPanel.add(selectTags);
					for (JCheckBox cb : checkboxes)
						checkboxPanel.add(cb);
					tagFrame.revalidate();
				}
			} else
				throw new IndexOutOfBoundsException();
		}

		else if (e.getSource() == deleteTagButton) {
			for (JCheckBox cb : checkboxes) {
				if (cb.isSelected()) {

					// delete from the panel, the image, checkboxText, and all
					// tags
					checkboxPanel.remove(cb);
					image.deleteTag(cb.getText());
					checkboxText.remove(cb.getText());
					Tags.deleteFromAllTags(cb.getText());
				}
			}

			// create new ArrayList of JCheckBoxes and populate
			checkboxes = new ArrayList<JCheckBox>();
			while (listOfTags.hasNext()) {
				JCheckBox name = new JCheckBox(listOfTags.next());
				checkboxes.add(name);
				checkboxPanel.add(name);
			}

			// refreshes the frame
			tagFrame.revalidate();
			tagFrame.repaint();
		}

		else if (e.getSource() == addDoneButton) {

			// creates new tags based on user's input
			String[] imageTag = tags.getText().split(",");
			for (String tag : imageTag) {
				tag = tag.trim();

				// make sure the tags isn't already included in master list of
				// tags
				if (!image.getTags().contains(tag) && !(tag.length() == 0))
					image.addNewTag(tag);
			}

			// create new checkbox(es) for the new tags

			while (listOfTags.hasNext()) {
				String tag = listOfTags.next();
				if (!checkboxText.contains(tag)) {
					JCheckBox name = new JCheckBox(tag);
					checkboxPanel.add(name);
					checkboxText.add(name.getText());
					checkboxes.add(name);
				}
			}
			tagFrame.revalidate();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new PhotoRenamer();
	}

}
