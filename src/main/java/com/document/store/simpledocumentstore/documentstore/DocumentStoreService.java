package com.document.store.simpledocumentstore.documentstore;

import java.io.File;

public interface DocumentStoreService {
	
	/**
	 * Save the document to specified location.
	 * @param fileUrl
	 * @param file
	 * @return
	 */
	public String saveDocument(String fileUrl, File file);
	
	/**
	 * Delete file from specified Location
	 * @param fileUrl
	 * @return
	 */
	public String deleteDocument(String fileUrl);
	
	/**
	 * Open File from specified Location.
	 * @param fileName
	 * @return
	 */
	public String openDocument(String fileUrl);
	
	/**
	 * Copy file to destination Location.
	 * @param fileName
	 * @param destination
	 * @return
	 */
	public String copyDocument(String fileUrl, String destinationUrl);
	
	/**
	 * Rename file to new filename.
	 * @param fileUrl
	 * @param newFilename
	 * @return
	 */
	public String renameDocument(String fileUrl, String newFilename);
	
	/**
	 * Move Document to new Location.
	 * @param fileUrl
	 * @param newfileUrl
	 * @return
	 */
	public String moveDocument(String fileUrl, String newfileUrl);
	
	/**
	 * Retrieves Document Size.
	 * @param fileUrl
	 * @return
	 */
	public String documentSize(String fileUrl);
	
	/**
	 * Retrieves Document Size.
	 * @param fileUrl
	 * @return
	 */
	public String documentCreationDate(String fileUrl);

}
         