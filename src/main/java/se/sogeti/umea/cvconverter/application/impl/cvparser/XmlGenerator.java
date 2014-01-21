package se.sogeti.umea.cvconverter.application.impl.cvparser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import se.sogeti.umea.cvconverter.application.Acquisition;
import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Job;
import se.sogeti.umea.cvconverter.application.Language;
import se.sogeti.umea.cvconverter.application.Skill;
import se.sogeti.umea.cvconverter.application.Tag;

public class XmlGenerator {
	private static final String XML_NAMESPACE = "http://www.sogeti.se/umea/curriculumvitae";

	XMLOutputFactory xmlof;

	public XmlGenerator() {
		// CDI
	}

	@Inject
	XmlGenerator(XMLOutputFactory xmlof) {
		this.xmlof = xmlof;
	}

	public String generateXml(CurriculumVitae cv, String encoding)
			throws XMLStreamException, UnsupportedEncodingException {

		// Create an XML stream writer
		ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
		try {
			XMLStreamWriter writer = xmlof.createXMLStreamWriter(out, encoding);

			// Write XML prologue
			writer.writeStartDocument(encoding, "1.0");

			// Write root element
			writer.writeStartElement("CurriculumVitae");
			writer.writeDefaultNamespace(XML_NAMESPACE);
			ContentLanguage contLang = cv.getContentLanguage();
			String contentLanguageText = contLang
					.equals(ContentLanguage.SWEDISH) ? "sv" : "en";
			writer.writeAttribute("language", contentLanguageText);
			writer.writeAttribute("generated", cv.getPrintDate());

			// Write chapters
			writeProfile(cv, writer);
			writeDescription(cv, writer);
			writeEngagements(cv, writer);
			writeProfessionalKnowledge(cv, writer);
			writeTechnology(cv, writer);
			writeIndustryKnowledge(cv, writer);
			writeCertifications(cv, writer);
			writeForeignLanguages(cv, writer);
			writeCourses(cv, writer);
			writeOrganisations(cv, writer);
			writeEmployments(cv, writer);
			writeEducations(cv, writer);
			writeCoverImage(cv, writer);
			writeTags(cv, writer);
			writePersonalQualities(cv, writer);
			// Close document
			writer.writeEndDocument();
			writer.close();

			return out.toString(encoding);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void writePersonalQualities(CurriculumVitae cv,
			XMLStreamWriter writer) throws XMLStreamException {
		if (cv.getPersonalQualities().size() > 0) {
			writer.writeStartElement("PersonalQualities");
			for (String personalQuality : cv.getPersonalQualities()) {
				writer.writeStartElement("PersonalQuality");
				writer.writeCharacters(personalQuality);
				writer.writeEndElement();
			}
			writer.writeEndElement();
		}
	}

	private void writeTags(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (cv.getTags().size() > 0) {
			writer.writeStartElement("Tags");
			for (Tag tag : cv.getTags()) {
				writeTag(tag, writer);
			}
			writer.writeEndElement();
		}
	}

	private void writeTag(Tag tag, XMLStreamWriter writer)
			throws XMLStreamException {
		writer.writeStartElement("Tag");
		writer.writeAttribute("bold", tag.isBold() + "");
		writer.writeAttribute("upperCase", tag.isUpperCase() + "");
		writer.writeAttribute("italic", tag.isItalic() + "");

		if (tag.getTagName() != null) {
			writer.writeStartElement("TagName");
			writer.writeCharacters(tag.getTagName());
			writer.writeEndElement();
		}

		if (tag.getSize() != null) {
			writer.writeStartElement("Size");
			writer.writeCharacters(tag.getSize().toString());
			writer.writeEndElement();
		}

		if (tag.getFont() != null) {
			writer.writeStartElement("Font");
			writer.writeCharacters(tag.getFont());
			writer.writeEndElement();
		}

		writer.writeEndElement();
	}

	private void writeCoverImage(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (cv.getCoverImage() != null) {
			writer.writeStartElement("CoverImage");
			writer.writeStartElement("Name");
			writer.writeCharacters(cv.getCoverImage().getName());
			writer.writeEndElement();
			writer.writeStartElement("Url");
			writer.writeCharacters(cv.getCoverImage().getLocalUrl());
			writer.writeEndElement();
			writer.writeEndElement();
		}
	}

	private void writeProfile(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		writer.writeStartElement("Profile");

		writer.writeStartElement("Name");
		writer.writeCharacters(cv.getProfile().getName());
		writer.writeEndElement();

		if (cv.getProfile().getFirstName() != null
				&& cv.getProfile().getFirstName().length() > 0) {
			writer.writeStartElement("FirstName");
			writer.writeCharacters(cv.getProfile().getFirstName());
			writer.writeEndElement();
		}

		if (cv.getProfile().getLastName() != null
				&& cv.getProfile().getLastName().length() > 0) {
			writer.writeStartElement("LastName");
			writer.writeCharacters(cv.getProfile().getLastName());
			writer.writeEndElement();
		}

		writer.writeStartElement("Title");
		writer.writeCharacters(cv.getProfile().getTitle());
		writer.writeEndElement();

		writer.writeStartElement("DateOfBirth");
		writer.writeCharacters(cv.getProfile().getDateOfBirth());
		writer.writeEndElement();

		writer.writeStartElement("PortraitUrl");
		writer.writeCharacters(cv.getProfile().getPortrait().getLocalUrl());
		writer.writeEndElement();

		if (cv.getProfile().getPhoneNumber() != null) {
			writer.writeStartElement("PhoneNumber");
			writer.writeCharacters(cv.getProfile().getPhoneNumber());
			writer.writeEndElement();
		}

		writer.writeEndElement();
	}

	private void writeDescription(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getDescription().isEmpty()) {
			writer.writeStartElement("Description");
			writer.writeCharacters(cv.getDescription());
			writer.writeEndElement();
		}
	}

	private void writeEngagements(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getEngagements().isEmpty()) {
			writer.writeStartElement("Engagements");

			for (Job engagement : cv.getEngagements()) {
				writeJob(engagement, writer, cv.getContentLanguage());
			}

			writer.writeEndElement();
		}
	}

	private void writeProfessionalKnowledge(CurriculumVitae cv,
			XMLStreamWriter writer) throws XMLStreamException {
		if (!cv.getProfessionalKnowledge().isEmpty()) {
			writer.writeStartElement("ProfessionalKnowledge");

			for (Skill knowledge : cv.getProfessionalKnowledge()) {
				writeSkill(knowledge, writer, cv.getContentLanguage());
			}

			writer.writeEndElement();
		}
	}

	private void writeTechnology(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getTechnologies().isEmpty()) {
			writer.writeStartElement("Technology");

			for (Skill skill : cv.getTechnologies()) {
				writeSkill(skill, writer, cv.getContentLanguage());
			}

			writer.writeEndElement();
		}
	}

	private void writeIndustryKnowledge(CurriculumVitae cv,
			XMLStreamWriter writer) throws XMLStreamException {
		if (!cv.getIndustryKnowledge().isEmpty()) {
			writer.writeStartElement("IndustryKnowledge");

			for (Skill skill : cv.getIndustryKnowledge()) {
				writeSkill(skill, writer, cv.getContentLanguage());
			}

			writer.writeEndElement();
		}
	}

	private void writeCertifications(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getCertifications().isEmpty()) {
			writer.writeStartElement("Certifications");

			for (Skill skill : cv.getCertifications()) {
				writeSkill(skill, writer, cv.getContentLanguage());
			}

			writer.writeEndElement();
		}
	}

	private void writeForeignLanguages(CurriculumVitae cv,
			XMLStreamWriter writer) throws XMLStreamException {
		if (!cv.getForeignLanguages().isEmpty()) {
			writer.writeStartElement("ForeignLanguages");

			for (Language language : cv.getForeignLanguages()) {
				writer.writeStartElement("Language");

				writer.writeStartElement("Name");
				writer.writeCharacters(language.getName());
				writer.writeEndElement();

				writer.writeStartElement("Level");
				String level = cv.getContentLanguage().equals(
						ContentLanguage.ENGLISH) ? language
						.getEnglishLevelName() : language.getSwedishLevelName();
				writer.writeCharacters(level);
				writer.writeEndElement();

				writer.writeEndElement();
			}

			writer.writeEndElement();
		}
	}

	private void writeCourses(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getCourses().isEmpty()) {
			writer.writeStartElement("Courses");

			for (Acquisition acquisition : cv.getCourses()) {
				writeAcquisition(acquisition, writer);
			}

			writer.writeEndElement();
		}
	}

	private void writeOrganisations(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getOrganisations().isEmpty()) {
			writer.writeStartElement("Organisations");

			for (String organisation : cv.getOrganisations()) {
				writer.writeStartElement("Organisation");
				writer.writeCharacters(organisation);
				writer.writeEndElement();
			}

			writer.writeEndElement();
		}
	}

	private void writeEmployments(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getEmployments().isEmpty()) {
			writer.writeStartElement("Employments");

			for (Job employments : cv.getEmployments()) {
				writeJob(employments, writer, cv.getContentLanguage());
			}

			writer.writeEndElement();
		}
	}

	private void writeEducations(CurriculumVitae cv, XMLStreamWriter writer)
			throws XMLStreamException {
		if (!cv.getEducations().isEmpty()) {
			writer.writeStartElement("Educations");

			for (Acquisition acquisition : cv.getEducations()) {
				writeAcquisition(acquisition, writer);
			}

			writer.writeEndElement();
		}
	}

	private void writeJob(Job job, XMLStreamWriter writer,
			ContentLanguage language) throws XMLStreamException {
		writer.writeStartElement("Job");

		Boolean important = ((se.sogeti.umea.cvconverter.application.Important) job)
				.isImportant();
		writer.writeAttribute("important", important.toString());

		writer.writeStartElement("Date");
		writer.writeCharacters(job.getDate());
		writer.writeEndElement();

		writer.writeStartElement("Name");
		writer.writeCharacters(job.getName());
		writer.writeEndElement();

		writer.writeStartElement("Description");
		writer.writeCharacters(job.getDescription());
		writer.writeEndElement();
		if (job.getShortDescription() != null
				&& job.getShortDescription().length() > 0) {
			writer.writeStartElement("ShortDescription");
			writer.writeCharacters(job.getShortDescription());
			writer.writeEndElement();
		}

		writer.writeStartElement("Duration");
		writer.writeCharacters(getDurationString(job.getDuration(), language)
				.trim());
		writer.writeEndElement();

		writer.writeEndElement();
	}

	private String getDurationString(int duration, ContentLanguage language) {
		if (duration == 0) {
			String current = ContentLanguage.ENGLISH.equals(language) ? "Current"
					: "Pågår";
			return current;
		}
		String month = ContentLanguage.ENGLISH.equals(language) ? "month"
				: "månad";
		if (duration > 1) {
			month += ContentLanguage.ENGLISH.equals(language) ? "s" : "er";
		}
		return duration + " " + month;
	}

	private void writeSkill(Skill knowledge, XMLStreamWriter writer,
			ContentLanguage contentLanguage) throws XMLStreamException {
		writer.writeStartElement("Skill");

		Boolean important = ((se.sogeti.umea.cvconverter.application.Important) knowledge)
				.isImportant();
		if (important != null) {
			writer.writeAttribute("important", important.toString());
		}

		writer.writeStartElement("Name");
		writer.writeCharacters(knowledge.getName());
		writer.writeEndElement();

		writer.writeStartElement("Level");
		String level = contentLanguage.equals(ContentLanguage.ENGLISH) ? knowledge
				.getEnglishLevelName() : knowledge.getSwedishLevelName();
		writer.writeCharacters(level);
		writer.writeEndElement();

		writer.writeEndElement();
	}

	private void writeAcquisition(Acquisition acquisition,
			XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("Acquisition");

		writer.writeStartElement("Date");
		writer.writeCharacters(acquisition.getDate());
		writer.writeEndElement();

		writer.writeStartElement("Name");
		writer.writeCharacters(acquisition.getName());
		writer.writeEndElement();

		writer.writeStartElement("Location");
		writer.writeCharacters(acquisition.getLocation());
		writer.writeEndElement();

		writer.writeEndElement();
	}
}
