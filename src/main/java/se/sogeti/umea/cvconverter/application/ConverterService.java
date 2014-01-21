package se.sogeti.umea.cvconverter.application;

import java.io.IOException;
import java.util.NoSuchElementException;

public interface ConverterService extends LayoutRepository, CoverImageRepository, JsonCvRepository {

	public CurriculumVitae parseRtf(String input)
			throws IllegalArgumentException, IOException;

	public byte[] convert(long layoutId, CurriculumVitae sourceData)
			throws IllegalArgumentException, NoSuchElementException,
			IOException, ConversionException;
	
//	public CurriculumVitae parseRtf(String rtf)
//			throws IllegalArgumentException, IOException;
//	
//	public byte[] convert(long layoutId, CurriculumVitae cv)
//			throws IllegalArgumentException, NoSuchElementException,
//			IOException;

}
