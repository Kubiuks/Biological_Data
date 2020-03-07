package org.spe.biologicaldata.webapplication.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spe.biologicaldata.webapplication.configuration.AmazonS3Configuration;
import org.spe.biologicaldata.webapplication.wrapper.ImagePathWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@ConditionalOnBean(AmazonS3Configuration.class)
public class AmazonS3Storage implements StorageService {

    private String awsS3BucketName;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3Storage.class);
    private String folderPath;
    private String awsS3GalleryPath;

    @Autowired
    public AmazonS3Storage(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider,
                            String awsS3BucketName, String awsS3GalleryPath, String localTemporaryStoragePath)
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3BucketName = awsS3BucketName;
        this.awsS3GalleryPath = awsS3GalleryPath;
        this.folderPath = localTemporaryStoragePath;
    }

    @Override
    //TODO test again
    public Optional<ImagePathWrapper> store(MultipartFile file, Boolean enablePublicReadAccess) {
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
            PutObjectResult res = this.amazonS3.putObject(putObjectRequest);
            //Removing the file created in the server
            if(!newFile.delete()) {
                logger.error("error deleting file with name [" + fileName + "] created from uploaded file [" +
                                file.getOriginalFilename());
            }
            ImagePathWrapper wrapper = new ImagePathWrapper();
            wrapper.setLink("https://" + awsS3BucketName + ".s3.amazonaws.com/" + awsFilePath);
            wrapper.setPathId(awsFilePath);
            return Optional.of(wrapper);
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + file.getName() + "] ");
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(String path) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3BucketName, path));
            return true;
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + path + "] ");
            return false;
        }
    }

//    @Override
//    public Boolean deleteAll() {
//       return null;
//    }

    @Override
    public Optional<byte[]> retrieveFile(String path) {
        try {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(awsS3BucketName, path));
            return Optional.of(s3Object.getObjectContent().readAllBytes());
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + path + "] ");
            return Optional.empty();
        }
    }

    private String getFilenameFromPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }


}
