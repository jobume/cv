package se.sogeti.umea.cvconverter.application;

import java.io.IOException;
import java.util.List;


public interface JsonCvRepository {
	
	public int createCv(String name, String office, String jsonCv) throws IOException;
	
	public String getCv(int id) throws IOException;
	
	public void updateCv(int id, String name, String jsonCv) throws IOException;
	
	public void deleteCv(int id) throws IOException;
	
	public List<CvOverview> listCvs() throws IOException;
	
	public List<CvOverview> listCvs(String office) throws IOException;
	
}
