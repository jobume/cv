package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import se.sogeti.umea.cvconverter.adapter.client.http.json.LanguageDeserializer;

;

@JsonDeserialize(using = LanguageDeserializer.class)
public final class LanguageImpl implements
		se.sogeti.umea.cvconverter.application.Language {

	private String name;
	private Level level;

	public LanguageImpl() {
	}

	public LanguageImpl(String name, Level level) {
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
		level = Level.valueOf(levelName.toUpperCase().replaceAll(" ", "_"));
	}

	@Override
	public String getEnglishLevelName() {
		switch (level) {
		case MOTHER_TONGUE:
			return "Mother Tongue";
		case FLUENT:
			return "Fluent";
		case GOOD_COMMAND:
			return "Good Command";
		case BASIC_KNOWLEDGE:
			return "Basic Knowledge";
		default:
			return null;
		}
	}

	@Override
	public String getSwedishLevelName() {
		switch (level) {
		case MOTHER_TONGUE:
			return "Modersmål";
		case FLUENT:
			return "Flytande";
		case GOOD_COMMAND:
			return "God kunskap";
		case BASIC_KNOWLEDGE:
			return "Baskunskap";
		default:
			return null;
		}
	}

}
