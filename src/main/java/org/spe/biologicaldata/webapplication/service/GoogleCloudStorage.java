package org.spe.biologicaldata.webapplication.service;

import com.google.auth.Credentials;
import com.google.cloud.storage.*;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spe.biologicaldata.webapplication.configuration.GoogleCloudConfiguration;
import org.spe.biologicaldata.webapplication.wrapper.ImagePathWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@ConditionalOnBean(GoogleCloudConfiguration.class)
public class GoogleCloudStorage implements StorageService {

    private Storage storage;
    private Bucket bucket;
    private String googleBucketGalleryPath;
    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudStorage.class);

    @Autowired
    public GoogleCloudStorage(Credentials googleCredentials, String googleProjectName,
                              String googleBucketName, String googleBucketGalleryPath){
        this.storage = StorageOptions.newBuilder().setCredentials(googleCredentials)
                .setProjectId(googleProjectName).build().getService();
        this.bucket = storage.get(googleBucketName);
        this.googleBucketGalleryPath = googleBucketGalleryPath;
    }

    @Override
    public Optional<ImagePathWrapper> store(MultipartFile file, Boolean enablePublicReadAccess) {
        try{
            String contentType = Objects.requireNonNull(file.getContentType()).split("/")[1];
            String fileName = RandomString.make(10) + "." + contentType;
            String googleFilePath = googleBucketGalleryPath + fileName;

            BlobInfo blobInfo;
            if(enablePublicReadAccess) {
                blobInfo = storage.create(
                    BlobInfo
                            .newBuilder(bucket.getName(), googleFilePath)
                            // Modify access list to allow all users with link to read file
                            .setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                            .setContentType(contentType)
                            .build(),
                    file.getBytes());
            }
            else {
                blobInfo = storage.create(
                        BlobInfo
                                .newBuilder(bucket.getName(), googleFilePath)
                                .setContentType(contentType)
                                .build(),
                        file.getBytes());
            }

            ImagePathWrapper wrapper = new ImagePathWrapper();
            wrapper.setLink(blobInfo.getMediaLink());
            wrapper.setPathId(blobInfo.getBlobId().getName());
            return Optional.of(wrapper);
        } catch(IOException | NullPointerException e){
            logger.error("error [" + e + "] occurred while uploading [" + file.getName() + "] ");
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(String path) {
        return storage.delete(
                BlobId.of(bucket.getName(), path)
        );
    }

//    @Override
//    public Boolean deleteAll() {
//        boolean accumulator = true;
//        for (Blob blob:  bucket.list().getValues()) {
//            accumulator &= storage.delete(blob.getBlobId());
//        }
//        return accumulator;
//    }

    @Override
    public Optional<byte[]> retrieveFile(String path) {
        try {
            Blob blob = storage.get(
                    BlobId.of(bucket.getName(), path)
            );
            return Optional.of(blob.getContent());
        } catch (NullPointerException | StringIndexOutOfBoundsException e) {
            logger.error("error [" + e + "] occurred while retrieving [" + path + "] ");
            return Optional.empty();
        }
    }

    private String getFileNameFromPath(String path){
        return path.substring(path.lastIndexOf("%") + 3, path.lastIndexOf("%") + 17);
    }

}
