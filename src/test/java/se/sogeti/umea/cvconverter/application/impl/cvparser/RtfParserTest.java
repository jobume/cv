package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.*;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.rtf.RTFEditorKit;

import org.junit.Test;

import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.impl.cvparser.RtfParser;


public class RtfParserTest {
	private static final String LN = System.getProperty("line.separator");

	@Test
	public void canBeCreated() {
		RtfParser p = new RtfParser(null);
		assertNotNull(p);
	}

	@Test(expected = IllegalArgumentException.class)
	public void canDetectInvalidRtf() throws Exception {
		RTFEditorKit kit = new RTFEditorKit();
		RtfParser p = new RtfParser(kit);
		p.parseRtf("Im not valid RTF!");
	}

	@Test
	public void canParseSimpleRtf() throws Exception {

		RTFEditorKit kit = new RTFEditorKit();
		RtfParser p = new RtfParser(kit);
		String rtfCvInput = "{\\rtf1\\ansi\\ansicpg1252\\deff0\\deflang1053"
				+ "{\\fonttbl{\\f0\\fnil\\fcharset0 Calibri;}}{\\*\\generator "
				+ "Msftedit 5.41.21.2510;}\\viewkind4\\uc1\\pard\\sa200\\sl276\\"
				+ "slmult1\\lang29\\f0\\fs22 hello\\par}";
		DefaultStyledDocument doc = p.parseRtf(rtfCvInput);

		Element element = doc.getParagraphElement(0);
		int startOffset = element.getStartOffset();
		int endOffset = element.getEndOffset() - 1; // -1 to exclude line break
		String actualText = doc.getText(startOffset, endOffset);

		assertEquals("hello", actualText);
	}

	@Test
	public void canParsePortraitUrl() throws Exception {
		String expectedUrl = "http://applications.sogeti.capgemini.se/Applications/Skills//NE/Photo-Sogeti//rekblom.jpg";

		RTFEditorKit kit = new RTFEditorKit();
		RtfParser p = new RtfParser(kit);
		String input = "\\par }{\\i\\f1\\fs16 07-08-1974"
				+ LN
				+ "\\par }{\\cell }"
				+ LN
				+ ""
				+ LN
				+ "\\pard \\qr \\li0\\ri0\\sl360\\slmult1\\widctlpar\\intbl\\nooverflow\\faroman\\rin0\\lin0 "
				+ LN
				+ "{\\field{\\*\\fldinst {\\fs16  INCLUDEPICTURE \\\\d \"http://applications.sogeti.capgemini.se/Applications/Skills//NE/Photo-Sogeti//rekblom.jpg\" \\\\* MERGEFORMAT }}"
				+ LN
				+ "{\\fldrslt {\\fs16 }}}{\\fs16 \\cell }"
				+ LN
				+ ""
				+ LN
				+ "\\pard "
				+ LN
				+ "\\ql \\li0\\ri0\\widctlpar\\intbl\\aspalpha\\aspnum\\faauto\\adjustright\\rin0\\lin0 {\\fs16 \\trowd \\trleft-8\\trftsWidth1\\trautofit1\\trpaddfl3\\trpaddfr3 \\clvertalt\\clbrdrt\\brdrnone \\clbrdrl\\brdrnone \\clbrdrb\\brdrnone \\clbrdrr\\brdrnone "
				+ LN
				+ "\\cltxlrtb\\clftsWidth3\\clwWidth3276 \\cellx2810\\clvertalt\\clbrdrt\\brdrnone \\clbrdrl\\brdrnone \\clbrdrb\\brdrnone \\clbrdrr\\brdrnone \\cltxlrtb\\clftsWidth3\\clwWidth3484 \\cellx5121\\clvertalt\\clbrdrt\\brdrnone \\clbrdrl\\brdrnone \\clbrdrb\\brdrnone \\clbrdrr\\brdrnone "
				+ LN
				+ "\\cltxlrtb\\clftsWidth3\\clwWidth2318 \\cellx9456\\row }";
		String actualUrl = p.parsePortraitUrl(input);

		assertEquals(expectedUrl, actualUrl);
	}

	@Test
	public void canParseLanguage() throws Exception {

		RTFEditorKit kit = new RTFEditorKit();
		RtfParser p = new RtfParser(kit);
		String rtfCvInput = "{\\lang1053\\langfe1033\\langnp1053 "
				+ LN
				+ "\\par }}{\\footer \\pard\\plain \\s16\\qj \\li0\\ri0\\sl360\\slmult1\\widctlpar\\brdrt\\brdrs\\brdrw10\\brsp20\\brdrcf16 \\tqc\\tx4536\\tqr\\tx9072\\nooverflow\\faroman\\rin0\\lin0\\rtlgutter\\itap0 \\f1\\fs12\\cf16\\lang1033\\langfe1033\\cgrid\\langnp1033\\langfenp1033 {\\field{\\*\\fldinst {"
				+ LN
				+ "\\cs17  TITLE  \\\\* MERGEFORMAT }}{\\fldrslt {\\cs17 \\'c5sa Gavelin}}}{\\cs17 "
				+ LN
				+ "\\par Page }{\\field{\\*\\fldinst {\\cs17  PAGE }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 3}}}{\\cs17 /}{\\field{\\*\\fldinst {\\cs17  NUMPAGES }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 3}}}{\\cs17 "
				+ LN
				+ "\\par Date of Print 2013-01-07}{"
				+ LN
				+ "\\par }}{\\footerf \\pard\\plain \\s16\\qj \\li0\\ri0\\sl360\\slmult1\\widctlpar\\brdrt\\brdrs\\brdrw10\\brsp20\\brdrcf16 \\tqc\\tx4536\\tqr\\tx9072\\nooverflow\\faroman\\rin0\\lin0\\rtlgutter\\itap0 \\f1\\fs12\\cf16\\lang1033\\langfe1033\\cgrid\\langnp1033\\langfenp1033 {\\field{\\*\\fldinst {"
				+ LN
				+ "\\cs17  TITLE  \\\\* MERGEFORMAT }}{\\fldrslt {\\cs17 \\'c5sa Gavelin}}}{\\cs17 "
				+ LN
				+ "\\par Page }{\\field{\\*\\fldinst {\\cs17  PAGE }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 1}}}{\\cs17 /}{\\field{\\*\\fldinst {\\cs17  NUMPAGES }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 1}}}{\\cs17 "
				+ LN
				+ "\\par Date of Print2013-01-07}{\\field{\\*\\fldinst {\\cs17   }}{\\fldrslt }}{"
				+ LN + "\\par }}";
		ContentLanguage actualLanguage = p.parseLanguage(rtfCvInput);

		assertEquals(ContentLanguage.ENGLISH, actualLanguage);
	}

	@Test
	public void canParsePrintDate() throws Exception {

		RTFEditorKit kit = new RTFEditorKit();
		RtfParser p = new RtfParser(kit);
		String rtfCvInput = "{\\lang1053\\langfe1033\\langnp1053 "
				+ LN
				+ "\\par }}{\\footer \\pard\\plain \\s16\\qj \\li0\\ri0\\sl360\\slmult1\\widctlpar\\brdrt\\brdrs\\brdrw10\\brsp20\\brdrcf16 \\tqc\\tx4536\\tqr\\tx9072\\nooverflow\\faroman\\rin0\\lin0\\rtlgutter\\itap0 \\f1\\fs12\\cf16\\lang1033\\langfe1033\\cgrid\\langnp1033\\langfenp1033 {\\field{\\*\\fldinst {"
				+ LN
				+ "\\cs17  TITLE  \\\\* MERGEFORMAT }}{\\fldrslt {\\cs17 \\'c5sa Gavelin}}}{\\cs17 "
				+ LN
				+ "\\par Page }{\\field{\\*\\fldinst {\\cs17  PAGE }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 3}}}{\\cs17 /}{\\field{\\*\\fldinst {\\cs17  NUMPAGES }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 3}}}{\\cs17 "
				+ LN
				+ "\\par Date of Print 2013-01-07}{"
				+ LN
				+ "\\par }}{\\footerf \\pard\\plain \\s16\\qj \\li0\\ri0\\sl360\\slmult1\\widctlpar\\brdrt\\brdrs\\brdrw10\\brsp20\\brdrcf16 \\tqc\\tx4536\\tqr\\tx9072\\nooverflow\\faroman\\rin0\\lin0\\rtlgutter\\itap0 \\f1\\fs12\\cf16\\lang1033\\langfe1033\\cgrid\\langnp1033\\langfenp1033 {\\field{\\*\\fldinst {"
				+ LN
				+ "\\cs17  TITLE  \\\\* MERGEFORMAT }}{\\fldrslt {\\cs17 \\'c5sa Gavelin}}}{\\cs17 "
				+ LN
				+ "\\par Page }{\\field{\\*\\fldinst {\\cs17  PAGE }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 1}}}{\\cs17 /}{\\field{\\*\\fldinst {\\cs17  NUMPAGES }}{\\fldrslt {\\cs17\\lang1024\\langfe1024\\noproof 1}}}{\\cs17 "
				+ LN
				+ "\\par Date of Print2013-01-07}{\\field{\\*\\fldinst {\\cs17   }}{\\fldrslt }}{"
				+ LN + "\\par }}";
		String actualPrintDate = p.parsePrintDate(rtfCvInput);

		assertEquals("2013-01-07", actualPrintDate);
	}
}
