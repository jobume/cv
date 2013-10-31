package se.sogeti.umea.cvconverter.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TagCloud {

	private enum Font {
		MYRIAD("myriad"), TREBUCHET("trebuchet"), MINION("minion");

		private String name;

		Font(String name) {
			this.name = name;
		}

		String getName() {
			return name;
		}
	}

	private final List<String> technologiesAndQualities = new ArrayList<>();
	private final List<Tag> tags = new ArrayList<>();

	public TagCloud(CurriculumVitae cv) {
		for (Skill skill : cv.getTechnologies()) {
			if(skill.isImportant() != null && skill.isImportant()) {				
				technologiesAndQualities.add(skill.getName());
			}
		}
		for (String word : cv.getPersonalQualities()) {
			technologiesAndQualities.add(word);
		}
	}

	public void generateTags() {
		RandomArray randomTechnologiesAndQualities = new RandomArray(
				technologiesAndQualities);
		tags.clear();
		for (int i = 0; i < 8; i++) {
			String tagName = randomTechnologiesAndQualities.getRandomElement();
			if (tagName != null) {
				tagName = stripTechnologyType(tagName);
				decorateTags(tagName, i);
			}
		}
	}
	
	private String stripTechnologyType(String tagName) {
		if(tagName.contains(":")) {
			String[] tagNameArray = tagName.split(":");
			if(tagNameArray.length > 1) {
				tagName = tagNameArray[1].trim();
			}
		}
		return tagName;
	}

	public List<Tag> getTags() {
		return tags;
	}
	
	private void decorateTags(String tagName, int tagNr) {
		switch (tagNr) {
		case 0:
			tags.add(new Tag(tagName, true, true, false, Tag.Size.LARGE,
					Font.MYRIAD.getName()));
			break;
		case 1:
			tags.add(new Tag(tagName, true, false, true, Tag.Size.LARGE,
					Font.MINION.getName()));
			break;
		case 2:
			tags.add(new Tag(tagName, false, false, false, Tag.Size.LARGE,
					Font.TREBUCHET.getName()));
			break;
		case 3:
			tags.add(new Tag(tagName, true, false, true, Tag.Size.SMALL,
					Font.MYRIAD.getName()));
			break;
		case 4:
			tags.add(new Tag(tagName, true, false, false, Tag.Size.SMALL,
					Font.MINION.getName()));
			break;
		case 5:
			tags.add(new Tag(tagName, true, false, false, Tag.Size.LARGE,
					Font.TREBUCHET.getName()));
			break;
		case 6:
			tags.add(new Tag(tagName, false, false, false, Tag.Size.SMALL,
					Font.MYRIAD.getName()));
			break;
		case 7:
			tags.add(new Tag(tagName, false, false, false, Tag.Size.LARGE,
					Font.MINION.getName()));
			break;
		default:
			break;
		}
	}

	public class RandomArray {
		private final List<String> words = new ArrayList<>();

		public RandomArray(List<String> words) {
			super();
			this.words.addAll(words);
		}

		public String getRandomElement() {
			if (words.size() <= 0)
				return null;
			int randomNumber = new Random().nextInt(words.size());
			return words.remove(randomNumber);
		}

	}
}
