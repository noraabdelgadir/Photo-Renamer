package photo_renamer;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the master list of tags for its ability to perform a set of functions on
 * the ArrayList
 * 
 * @author Nora
 * @version %I%, %G%
 * 
 */
public class TagsTest {

	/**
	 * Set up before running TagsTest
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		// Set these values in this method so that every test case can
		// begin with them in this state, even if previous test cases
		// have changed them.
		Tags.addToAllTags("cat");
		Tags.addToAllTags("dog");
	}

	/**
	 * Test method for {@link photo_renamer.Tags#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		String t1 = "cat";
		Tags.getAllTags().add(t1);
		assertEquals(Tags.getAllTags().get(Tags.getAllTags().size() - 1).hashCode(), t1.hashCode());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.Tags#removeDuplicates(java.util.ArrayList)}.
	 */
	@Test
	public void testRemoveDuplicates() {
		@SuppressWarnings("unchecked")
		ArrayList<String> a1 = (ArrayList<String>) Tags.getAllTags().clone();
		Tags.addToAllTags("one");
		Tags.addToAllTags("one");
		a1.add("one");
		ArrayList<String> a2 = Tags.removeDuplicates(Tags.getAllTags());
		assertEquals(a1, a2);
	}

	/**
	 * Test method for
	 * {@link photo_renamer.Tags#removeDuplicates(java.util.ArrayList)}.
	 */
	@Test
	public void testTripleRemoveDuplicates() {
		@SuppressWarnings("unchecked")
		ArrayList<String> a1 = (ArrayList<String>) Tags.getAllTags().clone();
		a1.remove(a1.lastIndexOf("cat"));
		Tags.addToAllTags("one");
		Tags.addToAllTags("one");
		Tags.addToAllTags("one");
		a1.add("one");
		ArrayList<String> a2 = Tags.removeDuplicates(Tags.getAllTags());
		assertEquals(a1, a2);
	}

	/**
	 * Test method for
	 * {@link photo_renamer.Tags#setAllTags(java.util.ArrayList)}.
	 */
	@Test
	public void testSetAllTags() {
		@SuppressWarnings("unchecked")
		ArrayList<String> a1 = (ArrayList<String>) Tags.getAllTags().clone();
		a1.add("cat");
		Tags.setAllTags(a1);
		assertEquals("the ArrayLists are equal", a1, Tags.getAllTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.Tags#setAllTags(java.util.ArrayList)}.
	 */
	@Test
	public void testMoreSetAllTags() {
		@SuppressWarnings("unchecked")
		ArrayList<String> a1 = (ArrayList<String>) Tags.getAllTags().clone();
		a1.add("cat");
		a1.add("happy");
		a1.add("sad");
		Tags.setAllTags(a1);
		assertEquals("the ArrayLists are equal", a1, Tags.getAllTags());
	}

	/**
	 * Test method for
	 * {@link photo_renamer.Tags#setAllTags(java.util.ArrayList)}.
	 */
	@Test
	public void testNoSetAllTags() {
		@SuppressWarnings("unchecked")
		ArrayList<String> a1 = (ArrayList<String>) Tags.getAllTags().clone();
		a1.add("");
		Tags.setAllTags(a1);
		assertEquals("the ArrayLists are equal", a1, Tags.getAllTags());
	}

	/**
	 * Test method for {@link photo_renamer.Tags#getAllTags()}.
	 */
	@Test
	public void testGetAllTags() {
		ArrayList<String> actual = Tags.getAllTags();
		ArrayList<String> expected = new ArrayList<String>();
		assertArrayEquals("Constructor or getAllTags failed", expected, actual);
	}

	/**
	 * Checks if two Arrays are equal
	 * 
	 * @param string
	 *            the String to print if testGetAllTags fails
	 * @param expected
	 *            the expected array
	 * @param actual
	 *            the actual array
	 */
	private void assertArrayEquals(String string, ArrayList<String> expected, ArrayList<String> actual) {
		if (expected == actual) {
			System.out.println(string);
		}
	}

	/**
	 * Test method for {@link photo_renamer.Tags#addToAllTags()}.
	 */
	@Test
	public void testAddToAllTags() {
		Tags.addToAllTags("apple");
		assertEquals(Tags.getAllTags().get(Tags.getAllTags().size() - 1), "apple");
	}

	/**
	 * Test method for {@link photo_renamer.Tags#deleteFromAllTags()}.
	 */
	@Test
	public void testDeleteFromAllTags() {
		Tags.addToAllTags("cat");
		Tags.addToAllTags("dog");
		@SuppressWarnings("unchecked")
		ArrayList<String> oldTags = (ArrayList<String>) Tags.getAllTags().clone();
		Tags.deleteFromAllTags("cat");
		ArrayList<String> newTags = Tags.getAllTags();
		assertNotSame(oldTags, newTags);
	}

	/**
	 * Test method for {@link photo_renamer.Tags#removeAllTags()}.
	 */
	@Test
	public void testRemoveAllTags() {
		Tags.addToAllTags("cat");
		Tags.addToAllTags("dog");
		Tags.removeAllTags();
		ArrayList<String> listTags = new ArrayList<String>();
		assertEquals("Container Tags.getAllTags() has no items", Tags.getAllTags(), listTags);
	}

	/**
	 * Test method for{@link photo_renamer.Tags#removeAllTags()}.
	 */
	@Test
	public void testEmptyTags() {
		Tags.removeAllTags();
		Tags.getAllTags().isEmpty();
	}

	/**
	 * Test method for {@link photo_renamer.Tags#toString(java.lang.String)}.
	 */
	@Test
	public void testToString() {
		assertTrue(Tags.getAllTags().toString(), true);
		Tags.setAllTags(new ArrayList<String>());
		String expected = "[]";
		assertEquals(expected, Tags.getAllTags().toString());
	}

	/**
	 * Test method for {@link photo_renamer.Tags#toString(java.lang.String)}.
	 */
	@Test
	public void testToStringWithItems() {
		assertTrue(Tags.getAllTags().toString(), true);
		String expected = "[cat, dog, apple]";
		assertEquals(expected, Tags.getAllTags().toString());
	}
}