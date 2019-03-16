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

import com.document.store.simpledocumentstore.awsdocumentstore.AWSS3DocumentStoreServiceImpl;
import com.document.store.simpledocumentstore.documentstore.DocumentStoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwsDocumentStoreTest {
	
	@TestConfiguration
    static class AWSS3DocumentStoreImplTestContextConfiguration {
  
        @Bean
        public DocumentStoreService documentStoreService() {
            return new AWSS3DocumentStoreServiceImpl();
        }
    }
	
	@Autowired
	private DocumentStoreService awsStoreService;
	
	@Test
	public void whenAUploadSuccess_thenFileNameShouldMatch() {
		
		String fileName = "tsar_logo.png";
		
		try {
			File file = ResourceUtils.getFile("classpath:sample_files/"+fileName);
			assertThat( awsStoreService.saveDocument(fileName, file) ).isEqualTo(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void whenAAUploadSuccess_thenFileNameShouldMatch() {
		
		String fileName = "Desert.jpg";
		
		try {
			File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\"+fileName);
			assertThat( awsStoreService.saveDocument(fileName, file) ).isEqualTo(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenABDocumentSizeSuccess_thenIsNotblank() {
		
		String fileName = "Desert.jpg";
		
		try {
			assertThat( awsStoreService.documentSize(fileName) ).isNotBlank();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenACDocumentCrationTimeSuccess_thenIsNotblank() {
		
		String fileName = "Desert.jpg";
		
		try {
			assertThat( awsStoreService.documentCreationDate(fileName) ).isNotBlank();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void whenADDeleteSuccess_thenFileNameShouldMatch() {
		
		String fileName = "Desert.jpg";
		
		try {
			assertThat( awsStoreService.deleteDocument(fileName) ).isEqualTo("Successfully deleted");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenBDownloadSuccess_thenFileNameShouldMatch() {
		
		String fileName = "tsar_logo.png";
		
		try {
			assertThat( awsStoreService.openDocument(fileName) ).isEqualTo("Successfully downloaded");	
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
			assertThat( awsStoreService.copyDocument(fileName, destination) ).isEqualTo("Successfully copied");	
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
			assertThat( awsStoreService.moveDocument(fileName, destination) ).isEqualTo("Successfully moved");	
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
			assertThat( awsStoreService.renameDocument(fileName, newFilename) ).isEqualTo("Successfully renamed");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
