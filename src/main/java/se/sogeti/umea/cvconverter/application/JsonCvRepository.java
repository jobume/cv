package se.sogeti.umea.cvconverter.application;

import java.io.IOException;
import java.util.List;


public interface JsonCvRepository {
	
	public int createCv(CurriculumVitae cv) throws IOException;
	
	public CurriculumVitae getCv(int id) throws IOException;
	
	public void updateCv(CurriculumVitae cv) throws IOException;
	
	public void deleteCv(int id) throws IOException;
	
	public List<CvOverview> listCvs() throws IOException;
	
	public List<CvOverview> listCvs(String office) throws IOException;

	public int countPortraitIds(int portraitId);
	
}
