package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.util.List;

public interface FileRecordRepository {

	public FileRecord createFileRecord(String name, String type, String url);
	
	public List<FileRecord> listFileRecords(String type);
	
	public void deleteFileRecord(String name, String type);
}