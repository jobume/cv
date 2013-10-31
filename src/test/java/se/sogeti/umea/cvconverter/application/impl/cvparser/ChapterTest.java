package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import se.sogeti.umea.cvconverter.application.impl.cvparser.Chapter;

public class ChapterTest {
	@Test
	public void canBeCreated() throws Exception {
		assertNotNull(Chapter.DESCRIPTION);
	}

	@Test
	public void canGetChapterByEnglishName() throws Exception {
		Chapter chapter = Chapter.get("Engagements");
		assertEquals(Chapter.ENGAGEMENTS, chapter);

		chapter = Chapter.get("Courses");
		assertEquals(Chapter.COURSES, chapter);
	}

	@Test
	public void canGetChapterBySwedishName() throws Exception {
		Chapter chapter = Chapter.get("Uppdrag");
		assertEquals(Chapter.ENGAGEMENTS, chapter);

		chapter = Chapter.get("Kurser");
		assertEquals(Chapter.COURSES, chapter);
	}

	@Test
	public void canGetFollowingChapters() throws Exception {
		List<Chapter> followingChapters = Chapter
				.getFollowingChapters(Chapter.ENGAGEMENTS);
		assertEquals(9, followingChapters.size());
		assertEquals(Chapter.PROFESSIONAL_KNOWLEDGE, followingChapters.get(0));
		assertEquals(Chapter.EDUCATION, followingChapters.get(8));

		followingChapters = Chapter.getFollowingChapters(Chapter.COURSES);
		assertEquals(3, followingChapters.size());
		assertEquals(Chapter.ORGANISATIONS, followingChapters.get(0));
		assertEquals(Chapter.EDUCATION, followingChapters.get(2));
	}
}
