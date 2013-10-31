package se.sogeti.umea.cvconverter.application.impl.cvparser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.configuration.InjectedConfiguration;
import se.sogeti.umea.cvconverter.application.ContentLanguage;

class RtfParser {

	private RTFEditorKit kit;

	private final static Logger LOG = LoggerFactory.getLogger(RtfParser.class);

	/**
	 * The unsafe language pattern matches Date of Print more loosely
	 */
	private final Pattern cvLanguageUnsafePattern = Pattern
			.compile(
					"^\\\\par\\s.{0,}\\s{0,1}(Date\\sof\\sPrint|Utskriftsdatum)\\s([0-9]{4}-[0-9]{2}-[0-9]{2})\\}\\{.{0,}$",
					Pattern.MULTILINE);

	private final Pattern cvLanguagePattern = Pattern
			.compile(
					"^\\\\par\\s(Date\\sof\\sPrint|Utskriftsdatum)\\s([0-9]{4}-[0-9]{2}-[0-9]{2})\\}\\{$",
					Pattern.MULTILINE);

	@Inject
	@InjectedConfiguration(key = "parser.mode", defaultValue = "SAFE")
	private String mode;

	private final static String UNSAFE_MODE = "UNSAFE";

	public RtfParser() {
		// CDI
	}

	@Inject
	RtfParser(RTFEditorKit kit) {
		this.kit = kit;
	}

	DefaultStyledDocument parseRtf(String rtfCvInput) throws IOException,
			IllegalArgumentException {

		DefaultStyledDocument rtfSource = new DefaultStyledDocument();

		Reader stringReader = new StringReader(rtfCvInput);

		try {
			kit.read(stringReader, rtfSource, 0);
		} catch (BadLocationException e) {
			// This should not happen since pos is hardcoded to 0
			e.printStackTrace();
		}

		if (rtfSource.getLength() == 0) {
			throw new IllegalArgumentException("Unable to parse input RTF");
		}

		return rtfSource;
	}

	/**
	 * Returns the portrait URL located in the input string.
	 * 
	 * The regexp (without Java string escapes) for matching the URL is:
	 * INCLUDEPICTURE\s\\\\d\s"(http:.*?)"\s\\\\\*\sMERGEFORMAT
	 * 
	 * Example text: {\field{\*\fldinst {\fs16 INCLUDEPICTURE \\d
	 * "http://applications.sogeti.capgemini.se/Applications/Skills//NE/Photo-Sogeti//rekblom.jpg"
	 * \\* MERGEFORMAT }}
	 * 
	 * Result should be: http://applications.sogeti.capgemini.
	 * se/Applications/Skills//NE/Photo-Sogeti//rekblom.jpg
	 * 
	 * @param inputRtfCv
	 *            raw RTC text
	 * @return portrait URL located in input RTF text, or empty string if not
	 *         found
	 */
	public String parsePortraitUrl(String inputRtfCv) {
		String url = "";

		Pattern portraitUrlPattern = Pattern
				.compile("INCLUDEPICTURE\\s\\\\\\\\d\\s\"(http:.*?)\"\\s\\\\\\\\\\*\\sMERGEFORMAT");
		Matcher matcher = portraitUrlPattern.matcher(inputRtfCv);
		if (matcher.find()) {
			url = matcher.group(1);
		}

		return url;
	}

	public ContentLanguage parseLanguage(String rtfCvInput) {
		ContentLanguage contentLanguage = null;

		Matcher matcher = getLanguagePattern().matcher(rtfCvInput);
		if (matcher.find()) {
			if (matcher.group(1).equals("Date of Print")) {
				contentLanguage = ContentLanguage.ENGLISH;
			} else if (matcher.group(1).equals("Utskriftsdatum")) {
				contentLanguage = ContentLanguage.SWEDISH;
			}
		}

		return contentLanguage;
	}

	public String parsePrintDate(String rtfCvInput) {
		String printDate = "";

		Matcher matcher = getLanguagePattern().matcher(rtfCvInput);
		if (matcher.find()) {
			printDate = matcher.group(2);
		}

		return printDate;
	}

	private Pattern getLanguagePattern() {
		if (UNSAFE_MODE.equalsIgnoreCase(this.mode)) {
			LOG.debug("Unsafe language pattern used for matching date of print");
			return this.cvLanguageUnsafePattern;
		}
		return this.cvLanguagePattern;
	}
}
