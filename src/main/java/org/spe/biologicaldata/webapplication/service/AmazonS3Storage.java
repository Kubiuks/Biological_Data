package org.spe.biologicaldata.webapplication.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

//@Service
public class AmazonS3Storage implements StorageService {

    private String awsS3BucketName;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3Storage.class);
    String folderPath = "src/main/resources/static/images/";

    @Autowired
    public AmazonS3Storage(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3BucketName)
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3BucketName = awsS3BucketName;
    }

    @Override  //TODO Make it @Async
    public String store(MultipartFile file, Boolean enablePublicReadAccess) {

        String fileName = RandomString.make(10) + "." + Objects.requireNonNull(file.getContentType()).split("/")[1];

        try {
            //Temporarily creating the file in the server
            File newFile = new File(folderPath + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3BucketName, fileName, newFile);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);

            //Removing the file created in the server
            if(!newFile.delete()) {
                logger.error("error deleting file with name [" + fileName + "] created from uploaded file [" +
                                file.getOriginalFilename());
            }
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }

        //amazonS3.getObject(new GetObjectRequest(awsS3BucketName, fileName)).getKey();
        //https://bucket-biodata.s3.amazonaws.com/ngnl1.jpg
        return "https://" + amazonS3.getRegionName() + ".amazonaws.com/" + awsS3BucketName + "/" + fileName;
    }

    @Override   @Async
    public void delete(String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3BucketName, fileName));
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
        }
    }

    @Override   @Async
    public void deleteAll() {
    }

}
