package com.document.store.simpledocumentstore.filesystemdocumentstore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.beans.factory.annotation.Value;

import com.document.store.simpledocumentstore.documentstore.DocumentStoreService;

public class FileSystemDocumentStoreServiceImpl implements DocumentStoreService {
	
	 @Value("${fileSystemProperties.storePath}")
	 String storePath;

	@Override
	public String saveDocument(String fileName, File file) {
		file.renameTo(new File(storePath+fileName));
		return fileName;
	}

	@Override
	public String deleteDocument(String fileUrl) {
		File file = new File(storePath+fileUrl);
		file.delete();
		return "Successfully deleted";
	}

	@Override
	public String openDocument(String fileName) {
		File file = new File(storePath+fileName);
		file.renameTo(new File(storePath+fileName));
		return "Successfully downloaded";
	}

	@Override
	public String copyDocument(String fileName, String destination) {
		File file = new File(storePath+fileName);
		file.renameTo(new File(storePath+destination+"\\"+fileName));
		return "Successfully copied";
	}

	@Override
	public String renameDocument(String fileName, String newFilename) {
		File file = new File(storePath+fileName);
		file.renameTo(new File(storePath+newFilename));
		return "Successfully renamed";
	}

	@Override
	public String moveDocument(String fileName, String newLocation) {
		File file = new File(storePath+fileName);
		file.renameTo(new File(storePath+newLocation+"\\"+fileName));
		deleteDocument(storePath+fileName);
		return "Successfully moved";
	}

	@Override
	public String documentSize(String filename) {
		File file = new File(storePath+filename);
		return file.length()+"";
	}

	@Override
	public String documentCreationDate(String filename) {
		File file = new File(storePath+filename);
		BasicFileAttributes attr = null;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return attr.creationTime().toMillis()+"";
	}

}
