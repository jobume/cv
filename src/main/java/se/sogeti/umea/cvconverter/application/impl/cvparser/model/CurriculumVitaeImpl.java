package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

import java.util.ArrayList;
import java.util.List;

import se.sogeti.umea.cvconverter.application.Acquisition;
import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.Job;
import se.sogeti.umea.cvconverter.application.Profile;
import se.sogeti.umea.cvconverter.application.Skill;
import se.sogeti.umea.cvconverter.application.Language;
import se.sogeti.umea.cvconverter.application.Tag;

public class CurriculumVitaeImpl implements
		se.sogeti.umea.cvconverter.application.CurriculumVitae {

	private String name;
	private int id;
	
	private ContentLanguage contentLanguage;
	private String printDate;
	private Profile profile;
	private String description;
	private List<Job> engagements = new ArrayList<>();
	private List<Skill> professionalKnowledge = new ArrayList<>();
	private List<Skill> technologies = new ArrayList<>();
	private List<Skill> industryKnowledge = new ArrayList<>();
	private List<Skill> certifications = new ArrayList<>();
	private List<Language> foreignLanguages = new ArrayList<>();
	private List<Acquisition> courses = new ArrayList<>();
	private List<String> organisations = new ArrayList<>();
	private List<Job> employments = new ArrayList<>();
	private List<Acquisition> educations = new ArrayList<>();
	private Image coverImage;
	private List<Tag> tags = new ArrayList<>();
	private List<String> personalQualities = new ArrayList<>();

	@Override
	public ContentLanguage getContentLanguage() {
		return contentLanguage;
	}

	@Override
	public void setContentLanguage(ContentLanguage contentLanguage) {
		this.contentLanguage = contentLanguage;
	}

	@Override
	public String getPrintDate() {
		return printDate;
	}

	@Override
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	@Override
	public Profile getProfile() {
		return profile;
	}

	@Override
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public List<Job> getEngagements() {
		return engagements;
	}

	@Override
	public void setEngagements(List<Job> engagements) {
		this.engagements = engagements;
	}

	@Override
	public List<Skill> getProfessionalKnowledge() {
		return professionalKnowledge;
	}

	@Override
	public void setProfessionalKnowledge(List<Skill> professionalKnowledge) {
		this.professionalKnowledge.addAll(professionalKnowledge);
	}

	@Override
	public List<Skill> getTechnologies() {
		return technologies;
	}

	@Override
	public void setTechnologies(List<Skill> technologies) {
		this.technologies = technologies;
	}

	@Override
	public List<Skill> getIndustryKnowledge() {
		return industryKnowledge;
	}

	@Override
	public void setIndustryKnowledge(List<Skill> industryKnowledge) {
		this.industryKnowledge = industryKnowledge;
	}

	@Override
	public List<Skill> getCertifications() {
		return certifications;
	}

	@Override
	public void setCertifications(List<Skill> certifications) {
		this.certifications = certifications;
	}

	@Override
	public List<Language> getForeignLanguages() {
		return foreignLanguages;
	}

	@Override
	public void setForeignLanguages(List<Language> foreignLanguages) {
		this.foreignLanguages = foreignLanguages;
	}

	@Override
	public List<Acquisition> getCourses() {
		return courses;
	}

	@Override
	public void setCourses(List<Acquisition> educations) {
		this.courses = educations;
	}

	@Override
	public List<String> getOrganisations() {
		return organisations;
	}

	@Override
	public void setOrganisations(List<String> organisations) {
		this.organisations = organisations;
	}

	@Override
	public List<Job> getEmployments() {
		return employments;
	}

	@Override
	public void setEmployments(List<Job> employments) {
		this.employments = employments;
	}

	@Override
	public List<Acquisition> getEducations() {
		return educations;
	}

	@Override
	public void setEducations(List<Acquisition> educations) {
		this.educations = educations;
	}
	
	@Override
	public Image getCoverImage() {
		return coverImage;
	}

	@Override
	public void setCoverImage(Image coverImage) {
		this.coverImage = coverImage;
	}

	@Override
	public List<Tag> getTags() {
		return tags;
	}

	@Override
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	

	@Override
	public List<String> getPersonalQualities() {
		return personalQualities;
	}

	@Override
	public void setPersonalQualities(List<String> personalQualities) {
		this.personalQualities = personalQualities;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
		
}
