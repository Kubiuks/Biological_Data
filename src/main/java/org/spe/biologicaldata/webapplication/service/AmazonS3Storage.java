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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

//@Service
public class AmazonS3Storage implements StorageService {

    private String awsS3BucketName;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3Storage.class);
    String folderPath = "src/main/resources/static/images/";
    String awsS3GalleryPath;

    @Autowired
    public AmazonS3Storage(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider,
                            String awsS3BucketName, String awsS3GalleryPath)
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3BucketName = awsS3BucketName;
        this.awsS3GalleryPath = awsS3GalleryPath;
    }

    @Override //TODO maybe make it async
    public Optional<String> store(MultipartFile file, Boolean enablePublicReadAccess) {
        try {
            String fileName =  RandomString.make(10) + "." + Objects.requireNonNull(file.getContentType()).split("/")[1];
            String awsFilePath = awsS3GalleryPath + fileName;

            //Temporarily creating the file in the server
            File newFile = new File(folderPath + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3BucketName, awsFilePath, newFile);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);

            //Removing the file created in the server
            if(!newFile.delete()) {
                logger.error("error deleting file with name [" + fileName + "] created from uploaded file [" +
                                file.getOriginalFilename());
            }
            return Optional.of("https://" + awsS3BucketName + ".s3.amazonaws.com/" + awsFilePath);
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + file.getName() + "] ");
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3BucketName, fileName));
            return true;
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
            return false;
        }
    }

    @Override
    public Boolean deleteAll() {
        return false;
    }

    @Override
    public Optional<byte[]> retrieveFile(String path) {
        return Optional.empty();
    }

}
