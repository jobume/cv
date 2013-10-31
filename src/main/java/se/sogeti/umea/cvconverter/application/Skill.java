package se.sogeti.umea.cvconverter.application;

public interface Skill extends Important{
	public enum Level {
		// TODO CERTIFIED is not good here, is it really needed when all
		// Certifications only got 1 level ???
		EXPERT, EXPERIENCED, KNOWLEDGEABLE, CERTIFIED;
	}

	public String getName();

	public void setName(String name);

	public Level getLevel();

	public void setLevel(Level level);

	public String getEnglishLevelName();
	
	public String getSwedishLevelName();

}
