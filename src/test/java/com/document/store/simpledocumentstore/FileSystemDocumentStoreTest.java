package com.document.store.simpledocumentstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.document.store.simpledocumentstore.documentstore.DocumentStoreService;
import com.document.store.simpledocumentstore.filesystemdocumentstore.FileSystemDocumentStoreServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileSystemDocumentStoreTest {
	
	@TestConfiguration
    static class FileSystemDocumentStoreImplTestContextConfiguration {
  
        @Bean
        public DocumentStoreService documentStoreService() { 
            return new FileSystemDocumentStoreServiceImpl();
        }
    }
	
	@Autowired
	private DocumentStoreService fileSystemStoreService;
	
	@Test
	public void whenAUploadSuccess_thenFileNameShouldMatch() {
		
		String fileName = "tsar_logo.png";
		
		try {
			File file = ResourceUtils.getFile("classpath:sample_files/"+fileName);
			assertThat( fileSystemStoreService.saveDocument(fileName, file) ).isEqualTo(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenAAUploadSuccess_thenFileNameShouldMatch() {
		
		String fileName = "Koala.jpg";
		
		try {
			File file = new File("C:\\Users\\surajs\\Pictures\\ICONS\\"+fileName);
			assertThat( fileSystemStoreService.saveDocument(fileName, file) ).isEqualTo(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenABDocumentSizeSuccess_thenIsNotblank() {
		
		String fileName = "Koala.jpg";
		
		try {
			assertThat( fileSystemStoreService.documentSize(fileName) ).isNotBlank();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenACDocumentCrationTimeSuccess_thenIsNotblank() {
		
		String fileName = "Koala.jpg";
		
		try {
			assertThat( fileSystemStoreService.documentCreationDate(fileName) ).isNotBlank();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void whenADDeleteSuccess_thenFileNameShouldMatch() {
		
		String fileName = "Koala.jpg";
		
		try {
			assertThat( fileSystemStoreService.deleteDocument(fileName) ).isEqualTo("Successfully deleted");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenBDownloadSuccess_thenFileNameShouldMatch() {
		
		String fileName = "tsar_logo.png";
		
		try {
			assertThat( fileSystemStoreService.openDocument(fileName) ).isEqualTo("Successfully downloaded");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenCCopySuccess_thenFileNameShouldMatch() {
		
		String fileName = "tsar_logo.png";
		String destination = "copied_files";
		
		try {
			assertThat( fileSystemStoreService.copyDocument(fileName, destination) ).isEqualTo("Successfully copied");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void whenEMoveSuccess_thenFileNameShouldMatch() {
		
		String fileName = "tsar_logo1.png";
		String destination = "moved_files";
		
		try {
			assertThat( fileSystemStoreService.moveDocument(fileName, destination) ).isEqualTo("Successfully moved");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void whenDRenameSuccess_thenFileNameShouldMatch() {
		
		String fileName = "tsar_logo.png";
		String newFilename = "tsar_logo1.png";
		
		try {
			assertThat( fileSystemStoreService.renameDocument(fileName, newFilename) ).isEqualTo("Successfully renamed");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
