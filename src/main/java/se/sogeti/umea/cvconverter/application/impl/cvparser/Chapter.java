package se.sogeti.umea.cvconverter.application.impl.cvparser;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This enum represents all chapters that may occur in a CV RTF file. The order
 * is important since it's used by RtfParse when parsing each chapter to create
 * a data structure.
 */
enum Chapter {
	DESCRIPTION("Description", "Sammanfattning"), ENGAGEMENTS("Engagements",
			"Uppdrag"), PROFESSIONAL_KNOWLEDGE("Professional Knowledge",
			"Professional Knowledge"), TECHNOLOGY("Technology", "Tekniker"), INDUSTRY_KNOWLEDGE(
			"Industry Knowledge", "Industry Knowledge"), CERTIFICATIONS(
			"Certifications", "Certifieringar"), FOREIGN_LANGUAGES(
			"Foreign Languages", "Språk"), COURSES("Courses", "Kurser"), ORGANISATIONS(
			"Organisations", "Medlem i"), EMPLOYMENT("Employment",
			"Anställningar"), EDUCATION("Education", "Utbildning");

	private final String englishName;
	private final String swedishName;
	private static final SortedMap<String, Chapter> englishChapterMap = new TreeMap<String, Chapter>();
	private static final SortedMap<String, Chapter> swedishChapterMap = new TreeMap<String, Chapter>();
	private static final List<Chapter> orderedChaptersList = new ArrayList<Chapter>();

	static {
		for (Chapter c : values()) {
			englishChapterMap.put(c.toEnglish(), c);
			swedishChapterMap.put(c.toSwedish(), c);
			orderedChaptersList.add(c);
		}
	}

	private Chapter(String englishName, String swedishName) {
		this.englishName = englishName;
		this.swedishName = swedishName;
	}

	static List<Chapter> getFollowingChapters(Chapter chapter) {
		int followingChapterIndex = orderedChaptersList.lastIndexOf(chapter) + 1;
		return orderedChaptersList.subList(followingChapterIndex,
				orderedChaptersList.size());
	}

	static Chapter get(String name) {
		Chapter chapter = englishChapterMap.get(name);
		if (chapter == null) {
			chapter = swedishChapterMap.get(name);
		}
		return chapter;
	}

	String toEnglish() {
		return englishName;
	}

	String toSwedish() {
		return swedishName;
	}
}
