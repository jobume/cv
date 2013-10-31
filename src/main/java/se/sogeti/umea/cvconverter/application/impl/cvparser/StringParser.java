package se.sogeti.umea.cvconverter.application.impl.cvparser;

import java.util.ArrayList;
import java.util.List;

import se.sogeti.umea.cvconverter.application.Acquisition;
import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.Job;
import se.sogeti.umea.cvconverter.application.Language;
import se.sogeti.umea.cvconverter.application.Profile;
import se.sogeti.umea.cvconverter.application.Skill;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.AcquisitionImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.JobImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.LanguageImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.ProfileImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.SkillImpl;

// TODO sort methods

class StringParser {
	private static final String LN = System.getProperty("line.separator");
	private static final String TAB = "\t"; // RegExp for tab
	private ContentLanguage cvLanguage; // TODO this dependency could be removed
										// if

	// there is logic to scan for swedish and
	// english chapter names

	CurriculumVitaeImpl parseString(ContentLanguage contentLanguage,
			String printDate, String portraitUrl, StringBuilder cvText) {

		CurriculumVitaeImpl cv = new CurriculumVitaeImpl();

		this.cvLanguage = contentLanguage;
		cv.setContentLanguage(contentLanguage);

		cv.setPrintDate(printDate);

		// Parse profile
		Profile profile = parseProfile(cvText);
		cv.setProfile(profile);
		Image portrait = new Image();
		portrait.setUrl(portraitUrl);
		portrait.setLocalUrl(portraitUrl);
		cv.getProfile().setPortrait(portrait);

		// Parse description
		String description = getSectionText(cvText, Chapter.DESCRIPTION);
		cv.setDescription(description);

		// Parse engagements
		List<Job> engagements = parseJobs(cvText, Chapter.ENGAGEMENTS);
		cv.setEngagements(engagements);

		// Parse professional knowledge
		List<Skill> professionalKnowledge = parseSkills(cvText,
				Chapter.PROFESSIONAL_KNOWLEDGE);
		cv.setProfessionalKnowledge(professionalKnowledge);

		// Parse technology
		List<Skill> technologies = parseSkills(cvText, Chapter.TECHNOLOGY);
		cv.setTechnologies(technologies);

		// Parse industry knowledge
		List<Skill> industryKnowledge = parseSkills(cvText,
				Chapter.INDUSTRY_KNOWLEDGE);
		cv.setIndustryKnowledge(industryKnowledge);

		// Parse certifications
		List<Skill> certifications = parseSkills(cvText, Chapter.CERTIFICATIONS);
		cv.setCertifications(certifications);

		// Parse foreign languages
		List<Language> foreignLanguages = parseForeignLanguages(cvText);
		cv.setForeignLanguages(foreignLanguages);

		// Parse courses
		List<Acquisition> courses = parseAcquisitions(cvText, Chapter.COURSES);
		cv.setCourses(courses);

		// Parse organizations
		List<String> organisations = parseOrganisations(cvText);
		cv.setOrganisations(organisations);

		// Parse employments
		List<Job> employments = parseJobs(cvText, Chapter.EMPLOYMENT);
		cv.setEmployments(employments);

		// Parse educations CV text
		List<Acquisition> educations = parseAcquisitions(cvText,
				Chapter.EDUCATION);
		cv.setEducations(educations);

		return cv;
	}

	private Profile parseProfile(StringBuilder cvText) {
		Chapter chapter = Chapter.DESCRIPTION;
		String chapterTag = cvLanguage.equals(ContentLanguage.SWEDISH) ? chapter
				.toSwedish() : chapter.toEnglish();
		int profileEndIndex = cvText.indexOf(chapterTag);
		String profileText = cvText.substring(0, profileEndIndex);

		Profile profile = new ProfileImpl();

		String[] lines = profileText.split(LN);
		profile.setName(lines[0]);
		if (lines.length == 2) {
			profile.setDateOfBirth(lines[1]);
		} else if (lines.length == 3) {
			profile.setTitle(lines[1]);
			profile.setDateOfBirth(lines[2]);
		}

		return profile;
	}

	private String getSectionText(StringBuilder cvText, Chapter chapter) {
		// Set selected chapter name according to current language in CV
		String selectedChapter = cvLanguage == ContentLanguage.SWEDISH ? chapter
				.toSwedish() : chapter.toEnglish();

		// Get start index of selected section in CV text
		int sectionStartIndex = cvText.indexOf(LN + selectedChapter + LN);
		if (sectionStartIndex == -1) {
			return "";
		}
		sectionStartIndex += selectedChapter.length() + LN.length() * 2;

		// Get stop index of selected section in CV text
		int sectionStopIndex = cvText.length();
		List<Chapter> followingChapters = Chapter.getFollowingChapters(Chapter
				.get(selectedChapter));
		for (Chapter followingChapter : followingChapters) {
			String followingChapterName = cvLanguage == ContentLanguage.SWEDISH ? followingChapter
					.toSwedish() : followingChapter.toEnglish();
			String chapterTag = followingChapterName + LN;
			int chapterIndex = cvText.indexOf(chapterTag, sectionStartIndex);
			if (chapterIndex == -1) {
				continue;
			} else {
				sectionStopIndex = chapterIndex;
				break;
			}
		}

		return cvText.substring(sectionStartIndex, sectionStopIndex).trim();
	}

	private List<Job> parseJobs(StringBuilder cvText, Chapter chapter) {
		List<Job> jobs = new ArrayList<Job>();

		String sectionText = getSectionText(cvText, chapter);

		List<List<String>> linesWithTabSeparatedText = splitTextByLinesAndTabs(sectionText);

		for (int i = 0; i < linesWithTabSeparatedText.size(); i++) {
			List<String> line = linesWithTabSeparatedText.get(i);

			// Skip this line if it does not contain date and name (since it
			// must be a description that has already been handled)
			if (line.size() == 1) {
				continue;
			}

			String date = line.get(0);
			String name = line.get(1);
			String description = "";

			// Check next line and set as description if it only consists of one
			// string
			if (i + 1 < linesWithTabSeparatedText.size()) {
				List<String> nextLine = linesWithTabSeparatedText.get(i + 1);
				if (nextLine.size() == 1) {
					description = nextLine.get(0);
				}
			}

			Job job = new JobImpl();
			job.setDate(date);
			job.setName(name);
			job.setDescription(description);
			((JobImpl) job).setImportant(false);

			jobs.add(job);
		}

		return jobs;
	}

	/**
	 * @param text
	 *            the text to be splitted by lines and tabs
	 * @return list a list of lines containing a list with text that were
	 *         separated by a tab
	 */
	private List<List<String>> splitTextByLinesAndTabs(String text) {
		List<List<String>> linesWithTabSeparatedText = new ArrayList<List<String>>();

		if (text.isEmpty()) {
			return linesWithTabSeparatedText;
		}

		String[] lines = text.trim().split(LN);
		for (String line : lines) {
			List<String> trimmedTabSeparatedParts = new ArrayList<String>();
			String[] tabSeparatedParts = line.split(TAB);
			for (String part : tabSeparatedParts) {
				trimmedTabSeparatedParts.add(part.trim());
			}
			linesWithTabSeparatedText.add(trimmedTabSeparatedParts);
		}

		return linesWithTabSeparatedText;
	}

	private List<Skill> parseSkills(StringBuilder cvText, Chapter chapter) {
		List<Skill> skills = new ArrayList<>();

		String sectionText = getSectionText(cvText, chapter);

		List<List<String>> linesWithTabSeparatedText = splitTextByLinesAndTabs(sectionText);

		for (int i = 0; i < linesWithTabSeparatedText.size(); i += 2) {
			SkillImpl skill = new SkillImpl();
			String name = linesWithTabSeparatedText.get(i).get(0);
			skill.setName(name);
			String level = linesWithTabSeparatedText.get(i + 1).get(0);
			skill.setLevel(parseSkillLevel(level));
			skills.add(skill);
		}

		return skills;
	}

	private Skill.Level parseSkillLevel(String level) {
		switch (level.toLowerCase()) {
		case "expert":
			return Skill.Level.EXPERT;
		case "experienced":
		case "erfaren":
			return Skill.Level.EXPERIENCED;
		case "knowledgeable":
		case "baskunskap":
			return Skill.Level.KNOWLEDGEABLE;
		case "certified":
		case "certifierad":
			return Skill.Level.CERTIFIED;
		default:
			return null;
		}
	}

	private List<Language> parseForeignLanguages(StringBuilder cvText) {
		List<Language> languages = new ArrayList<>();

		String foreignLanguages = getSectionText(cvText,
				Chapter.FOREIGN_LANGUAGES);

		List<List<String>> linesWithTabSeparatedText = splitTextByLinesAndTabs(foreignLanguages);

		for (int i = 0; i < linesWithTabSeparatedText.size(); i += 2) {
			LanguageImpl language = new LanguageImpl();
			String name = linesWithTabSeparatedText.get(i).get(0);
			language.setName(name);
			String level = linesWithTabSeparatedText.get(i + 1).get(0);
			language.setLevel(parseLanguageLevel(level));
			languages.add(language);
		}

		return languages;
	}

	private Language.Level parseLanguageLevel(
			String level) {
		switch (level.toLowerCase()) {
		case "mother tongue":
		case "modersmål":
			return Language.Level.MOTHER_TONGUE;
		case "fluent":
		case "flytande":
			return Language.Level.FLUENT;
		case "good command":
		case "god kunskap":
			return Language.Level.GOOD_COMMAND;
		case "basic knowledge":
		case "baskunskap":
			return Language.Level.BASIC_KNOWLEDGE;
		default:
			return null;
		}
	}

	private List<Acquisition> parseAcquisitions(StringBuilder cvText,
			Chapter chapter) {
		List<Acquisition> acquisitions = new ArrayList<>();

		String sectionText = getSectionText(cvText, chapter);

		List<List<String>> linesWithTabSeparatedText = splitTextByLinesAndTabs(sectionText);

		for (List<String> line : linesWithTabSeparatedText) {
			String first = line.get(0);
			String second = line.size() > 1 ? line.get(1) : "";
			String third = line.size() > 2 ? line.get(2) : "";

			if (first.matches("^[0-9][0-9\\-\\s]*")) { // Match date
				acquisitions.add(new AcquisitionImpl(first, second, third));
			} else {
				acquisitions.add(new AcquisitionImpl("", first, second));
			}
		}

		return acquisitions;
	}

	private List<String> parseOrganisations(StringBuilder cvText) {
		List<String> organisations = new ArrayList<String>();

		String organisationsText = getSectionText(cvText, Chapter.ORGANISATIONS);

		List<List<String>> linesWithTabSeparatedText = splitTextByLinesAndTabs(organisationsText);

		for (List<String> line : linesWithTabSeparatedText) {
			organisations.add(line.get(0));
		}

		return organisations;
	}

}