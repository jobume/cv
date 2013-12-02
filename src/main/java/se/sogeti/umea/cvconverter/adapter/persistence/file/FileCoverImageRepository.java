package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import se.sogeti.umea.cvconverter.application.CoverImageRepository;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.Repository;

@Repository
public class FileCoverImageRepository implements CoverImageRepository {

	private final static String TYPE_NAME = "coverimage";

	@Inject
	private FileRepository fileRepository;

	@Override
	public Image createCoverImage(InputStream fileInputStream, String name)
			throws IOException {
		FileRecord fileRecord = fileRepository.createFile(fileInputStream, name, TYPE_NAME);
		return new Image(fileRecord.getName(), fileRecord.getUrl(), fileRecord.getUrl());
	}

	@Override
	public List<Image> getCoverImages() throws IllegalArgumentException,
			NoSuchElementException, IOException {
		List<Image> images = new ArrayList<>();
		List<FileRecord> records = fileRepository.listFiles(TYPE_NAME);
		for (FileRecord record : records) {
			images.add(new Image(record.getName(), record.getUrl(), record
					.getUrl()));
		}
		return images;
	}

	@Override
	public void deleteCoverImage(String name) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		fileRepository.deleteFile(name, TYPE_NAME);
	}

}
