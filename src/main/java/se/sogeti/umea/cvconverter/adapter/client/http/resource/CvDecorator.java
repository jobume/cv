package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Tag;

@ApplicationScoped
public class CvDecorator {

	public enum Font {
		MYRIAD("myriad"), TREBUCHET("trebuchet"), MINION("minion");

		private String name;

		Font(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	@Inject
	private ImageDecorator imageDecorator;

	public void decorateCv(CurriculumVitae cv) {
		imageDecorator.setImageUrls(cv.getCoverImage());
		imageDecorator.setImageUrls(cv.getProfile().getPortrait());
		decorateTags(cv.getTags());
	}


	public void decorateTags(List<Tag> tags) {
		if (tags.size() > 0)
			decorateTag(tags.get(0), true, true, false, Tag.Size.LARGE,
					Font.MYRIAD.getName());
		if (tags.size() > 1)
			decorateTag(tags.get(1), false, false, true, Tag.Size.LARGE,
					Font.TREBUCHET.getName());
		if (tags.size() > 2)
			decorateTag(tags.get(2), false, false, false, Tag.Size.LARGE,
					Font.MYRIAD.getName());
		if (tags.size() > 3)
			decorateTag(tags.get(3), true, false, true, Tag.Size.SMALL,
					Font.MYRIAD.getName());
		if (tags.size() > 4)
			decorateTag(tags.get(4), true, false, false, Tag.Size.SMALL,
					Font.MYRIAD.getName());
		if (tags.size() > 5)
			decorateTag(tags.get(5), true, false, false, Tag.Size.LARGE,
					Font.MYRIAD.getName());
		if (tags.size() > 6)
			decorateTag(tags.get(6), false, false, false, Tag.Size.SMALL,
					Font.TREBUCHET.getName());
		if (tags.size() > 7)
			decorateTag(tags.get(7), false, true, false, Tag.Size.LARGE,
					Font.TREBUCHET.getName());

	}

	private Tag decorateTag(Tag tag, boolean bold, boolean upperCase,
			boolean italic, Tag.Size size, String font) {
		tag.setBold(bold);
		tag.setUpperCase(upperCase);
		tag.setItalic(italic);
		tag.setSize(size);
		tag.setFont(font);
		return tag;
	}
}
