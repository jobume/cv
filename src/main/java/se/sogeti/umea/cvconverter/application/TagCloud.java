package se.sogeti.umea.cvconverter.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TagCloud {

	private final List<String> technologiesAndQualities = new ArrayList<>();
	private final List<Tag> tags = new ArrayList<>();

	public TagCloud(CurriculumVitae cv) {
		for (Skill skill : cv.getTechnologies()) {
			if (skill.isImportant() != null && skill.isImportant()) {
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
			if (tagName != null && tagName.length() > 0) {
				Tag tag = new Tag(tagName);
				decorateTag(tag, i);
				tags.add(tag);
			}
		}
	}

	private static String stripTechnologyType(String tagName) {
		if (tagName.contains(":")) {
			String[] tagNameArray = tagName.split(":");
			if (tagNameArray.length > 1) {
				tagName = tagNameArray[1].trim();
			}
		}
		return tagName;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public static void decorateTag(Tag tag, int tagNr) {
		tag.setTagName(stripTechnologyType(tag.getTagName()));
		switch (tagNr) {
		case 0:
			tag.setBold(true);
			tag.setUpperCase(true);
			tag.setItalic(false);
			tag.setSize(Tag.Size.LARGE);
			tag.setFont(Tag.Font.TREBUCHET.getName());
			break;
		case 1:
			tag.setBold(false);
			tag.setUpperCase(false);
			tag.setItalic(true);
			tag.setSize(Tag.Size.LARGE);
			tag.setFont(Tag.Font.TREBUCHET.getName());
			break;
		case 2:
			tag.setBold(false);
			tag.setUpperCase(false);
			tag.setItalic(false);
			tag.setSize(Tag.Size.LARGE);
			tag.setFont(Tag.Font.MYRIAD.getName());
			break;
		case 3:
			tag.setBold(true);
			tag.setUpperCase(false);
			tag.setItalic(true);
			tag.setSize(Tag.Size.SMALL);
			tag.setFont(Tag.Font.MYRIAD.getName());
			break;
		case 4:
			tag.setBold(true);
			tag.setUpperCase(false);
			tag.setItalic(false);
			tag.setSize(Tag.Size.SMALL);
			tag.setFont(Tag.Font.MYRIAD.getName());
			break;
		case 5:
			tag.setBold(true);
			tag.setUpperCase(false);
			tag.setItalic(false);
			tag.setSize(Tag.Size.LARGE);
			tag.setFont(Tag.Font.MYRIAD.getName());
			break;
		case 6:
			tag.setBold(false);
			tag.setUpperCase(false);
			tag.setItalic(false);
			tag.setSize(Tag.Size.SMALL);
			tag.setFont(Tag.Font.TREBUCHET.getName());
			break;
		case 7:
			tag.setBold(false);
			tag.setUpperCase(true);
			tag.setItalic(false);
			tag.setSize(Tag.Size.LARGE);
			tag.setFont(Tag.Font.TREBUCHET.getName());
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

	@Override
	public String toString() {
		return "TagCloud [tags=" + tags + "]";
	}

}