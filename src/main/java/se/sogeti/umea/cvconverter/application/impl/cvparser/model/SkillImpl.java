package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import se.sogeti.umea.cvconverter.adapter.client.http.json.SkillDeserializer;
import se.sogeti.umea.cvconverter.application.Important;

@JsonDeserialize(using = SkillDeserializer.class)
public class SkillImpl implements se.sogeti.umea.cvconverter.application.Skill, Important {

	private String name;
	private Level level = Level.KNOWLEDGEABLE;
	private Boolean important;

	public SkillImpl() {
	}

	public SkillImpl(String name, Level level) {
		this.name = name;
		this.level = level;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public void setLevel(Level level) {
		this.level = level;
	}

	public void setLevel(String levelName) {
		level = Level.valueOf(levelName.toUpperCase());
	}

	@Override
	public String getEnglishLevelName() {
		switch (level) {
		case EXPERT:
			return "Expert";
		case EXPERIENCED:
			return "Experienced";
		case KNOWLEDGEABLE:
			return "Knowledgeable";
		case CERTIFIED:
			return "Certified";
		default:
			return null;
		}
	}

	@Override
	public String getSwedishLevelName() {
		switch (level) {
		case EXPERT:
			return "Expert";
		case EXPERIENCED:
			return "Erfaren";
		case KNOWLEDGEABLE:
			return "Baskunskap";
		case CERTIFIED:
			return "Certifierad";
		default:
			return null;
		}
	}

	@Override
	public Boolean isImportant() {
		return important;
	}

	@Override
	public void setImportant(Boolean important) {
		this.important = important;
	}

}
