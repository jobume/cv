package se.sogeti.umea.cvconverter.application;

import java.util.List;


public interface CurriculumVitae {

	public int getId();
	
	public void setId(int id);
	
	/**
	 * The name of the CV when saved to the database.
	 * 
	 * @return the name of the CV.
	 */
	public String getName();
	
	public void setName(String name);
	
	public ContentLanguage getContentLanguage();

	public void setContentLanguage(ContentLanguage contentLanguage);

	public String getPrintDate();

	public void setPrintDate(String printDate);

	public Profile getProfile();

	public void setProfile(Profile profile);

	public String getDescription();

	public void setDescription(String description);

	public List<Job> getEngagements();

	public void setEngagements(List<Job> engagements);

	public List<Skill> getProfessionalKnowledge();

	public void setProfessionalKnowledge(List<Skill> professionalKnowledge);

	public List<Skill> getTechnologies();

	public void setTechnologies(List<Skill> technologies);

	public List<Skill> getIndustryKnowledge();

	public void setIndustryKnowledge(List<Skill> industryKnowledge);

	public List<Skill> getCertifications();

	public void setCertifications(List<Skill> certifications);

	public List<Language> getForeignLanguages();

	public void setForeignLanguages(List<Language> foreignLanguages);

	public List<Acquisition> getCourses();

	public void setCourses(List<Acquisition> educations);

	public List<String> getOrganisations();

	public void setOrganisations(List<String> organisations);

	public List<Job> getEmployments();

	public void setEmployments(List<Job> employments);

	public List<Acquisition> getEducations();

	public void setEducations(List<Acquisition> educations);
	
	public Image getCoverImage();
	
	public void setCoverImage(Image coverImage);
	
	public List<Tag> getTags();
	
	public void setTags(List<Tag>tags);
	
	public List<String> getPersonalQualities();
	
	public void setPersonalQualities(List<String> personalQualities);
	

}