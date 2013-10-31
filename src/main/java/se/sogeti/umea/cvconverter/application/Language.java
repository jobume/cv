package se.sogeti.umea.cvconverter.application;

public interface Language {
	
	public enum Level {
		MOTHER_TONGUE, FLUENT, GOOD_COMMAND, BASIC_KNOWLEDGE;
	}

	public String getName();

	public void setName(String name);

	public Level getLevel();

	public void setLevel(Level level);

	public String getEnglishLevelName();
	
	public String getSwedishLevelName();
	
}
