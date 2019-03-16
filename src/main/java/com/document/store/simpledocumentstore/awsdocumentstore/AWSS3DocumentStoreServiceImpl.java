package com.document.store.simpledocumentstore.awsdocumentstore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.document.store.simpledocumentstore.documentstore.DocumentStoreService;

public class AWSS3DocumentStoreServiceImpl implements DocumentStoreService {
	
	private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
    	BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey); 
    	s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds))
    			.withRegion("us-east-1")
    			.build();
    }

    @Override
    public String saveDocument(String fileName, File file) {
    	ObjectMetadata metadata = new ObjectMetadata();
    	metadata.addUserMetadata("size", file.length()+"");
    	metadata.addUserMetadata("created_date", (new Date()).getTime() + "");
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file).withMetadata(metadata));
        return fileName;
    }

    @Override
    public String deleteDocument(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }
    
    @Override
    public String openDocument(String fileMame) {
    	//final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    	try {
    	    S3Object o = s3client.getObject(bucketName, fileMame);
    	    S3ObjectInputStream s3is = o.getObjectContent();
    	    FileOutputStream fos = new FileOutputStream(new File("E:\\downloaded_files\\"+fileMame));
    	    byte[] read_buf = new byte[1024];
    	    int read_len = 0;
    	    while ((read_len = s3is.read(read_buf)) > 0) {
    	        fos.write(read_buf, 0, read_len);
    	    }
    	    s3is.close();
    	    fos.close();
    	} catch (AmazonServiceException e) {
    	    System.err.println(e.getErrorMessage());
    	    System.exit(1);
    	    return null;
    	} catch (FileNotFoundException e) {
    	    System.err.println(e.getMessage());
    	    System.exit(1);
    	    return null;
    	} catch (IOException e) {
    	    System.err.println(e.getMessage());
    	    System.exit(1);
    	    return null;
    	}
    	
    	return "Successfully downloaded";

    }

	@Override
	public String copyDocument(String fileName, String destination) {
		try {
			s3client.copyObject(bucketName, fileName, bucketName, destination+"/"+fileName);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		    return null;
		}
		return "Successfully copied";
	}

	@Override
	public String renameDocument(String fileName, String newFilename) {
		try {
			s3client.copyObject(bucketName, fileName, bucketName, newFilename);
			deleteDocument(fileName);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		    return null;
		}
		return "Successfully renamed";
	}

	@Override
	public String moveDocument(String fileName, String newLocation) {
		try {
			s3client.copyObject(bucketName, fileName, bucketName, newLocation+"/"+fileName);
			deleteDocument(fileName);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		    return null;
		}
		return "Successfully moved";
	}

	@Override
	public String documentSize(String fileName) {
		S3Object o = s3client.getObject(bucketName, fileName);
	    ObjectMetadata metadata = o.getObjectMetadata();
	    System.out.println("size: " + metadata.getUserMetadata().get("size"));
		return metadata.getUserMetadata().get("size");
	}

	@Override
	public String documentCreationDate(String fileName) {
		S3Object o = s3client.getObject(bucketName, fileName);
	    ObjectMetadata metadata = o.getObjectMetadata();
	    System.out.println("created_date: " + metadata.getUserMetadata().get("created_date"));
		return metadata.getUserMetadata().get("created_date");
	}
	
	
    
    

}
