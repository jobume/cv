package se.sogeti.umea.cvconverter.adapter.client.http.json;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import se.sogeti.umea.cvconverter.application.Acquisition;
import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.Job;
import se.sogeti.umea.cvconverter.application.Profile;
import se.sogeti.umea.cvconverter.application.Skill;
import se.sogeti.umea.cvconverter.application.Language;
import se.sogeti.umea.cvconverter.application.Tag;
import se.sogeti.umea.cvconverter.application.TagCloud;

public class CurriculumVitaeImpl implements
		se.sogeti.umea.cvconverter.application.CurriculumVitae {

	private int id;
	private String name;

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
	private String office;

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
	@JsonDeserialize(as = ProfileImpl.class)
	public Profile getProfile() {
		return profile;
	}

	@Override
	public void setProfile(Profile profile) {
		this.profile = (ProfileImpl) profile;
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
	@JsonDeserialize(contentAs = JobImpl.class)
	public List<Job> getEngagements() {
		return engagements;
	}

	@Override
	public void setEngagements(List<Job> engagements) {
		this.engagements = engagements;
	}

	@Override
	@JsonDeserialize(contentAs = SkillImpl.class)
	public List<Skill> getProfessionalKnowledge() {
		return professionalKnowledge;
	}

	@Override
	public void setProfessionalKnowledge(List<Skill> professionalKnowledge) {
		this.professionalKnowledge = professionalKnowledge;
	}

	@Override
	@JsonDeserialize(contentAs = SkillImpl.class)
	public List<Skill> getTechnologies() {
		return technologies;
	}

	@Override
	public void setTechnologies(List<Skill> technologies) {
		this.technologies = technologies;
	}

	@Override
	@JsonDeserialize(contentAs = SkillImpl.class)
	public List<Skill> getIndustryKnowledge() {
		return industryKnowledge;
	}

	@Override
	public void setIndustryKnowledge(List<Skill> industryKnowledge) {
		this.industryKnowledge = industryKnowledge;
	}

	@Override
	@JsonDeserialize(contentAs = SkillImpl.class)
	public List<Skill> getCertifications() {
		return certifications;
	}

	@Override
	public void setCertifications(List<Skill> certifications) {
		this.certifications = certifications;
	}

	@Override
	@JsonDeserialize(contentAs = LanguageImpl.class)
	public List<Language> getForeignLanguages() {
		return foreignLanguages;
	}

	@Override
	public void setForeignLanguages(List<Language> foreignLanguages) {
		this.foreignLanguages = foreignLanguages;
	}

	@Override
	@JsonDeserialize(contentAs = AcquisitionImpl.class)
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
	@JsonDeserialize(contentAs = JobImpl.class)
	public List<Job> getEmployments() {
		return employments;
	}

	@Override
	public void setEmployments(List<Job> employments) {
		this.employments = employments;
	}

	@Override
	@JsonDeserialize(contentAs = AcquisitionImpl.class)
	public List<Acquisition> getEducations() {
		return educations;
	}

	@Override
	public void setEducations(List<Acquisition> educations) {
		this.educations = educations;
	}

	@Override
	public Image getCoverImage() {
		return this.coverImage;
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

	@Override
	public String getOffice() {
		return office;
	}

	@Override
	public void setOffice(String office) {
		this.office = office;
	}

	public void decorateTags() {
		int i = 0;
		for (Tag tag : tags) {
			TagCloud.decorateTag(tag, i);
			i++;
		}
	}
	
	

}