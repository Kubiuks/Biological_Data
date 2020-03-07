package org.spe.biologicaldata.webapplication.configuration;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spe.biologicaldata.webapplication.service.AmazonS3Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
@ConditionalOnExpression("${service.storage.google-storage:true}")
public class GoogleCloudConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudConfiguration.class);

    @Value("${google.credentials.classpath}")
    private String googleCredentialsPath;

    @Value("${google.projectName}")
    private String googleProjectName;

    @Value("${google.bucketName}")
    private String googleBucketName;

    @Value("${google.bucket.galleryPath}")
    private String googleBucketGalleryPath;

    @Bean(name = "googleCredentialsPath")
    public String getGoogleCredentialsPath(){return googleCredentialsPath;}

    @Bean(name = "googleBucketName")
    public String getGoogleBucketName(){return googleBucketName;}

    @Bean(name = "googleProjectName")
    public String getGoogleProjectName(){return googleProjectName;}

    @Bean(name = "googleBucketGalleryPath")
    public String getGoogleGalleryPath(){return googleBucketGalleryPath;}

    @Bean(name = "googleCredentials")
    public GoogleCredentials getGoogleCredentials() {
        try {
            return GoogleCredentials
                    .fromStream(new FileInputStream(this.googleCredentialsPath));
        } catch (IOException e) {
            logger.error("error: [" + e + "] happened while loading Google Credentials");
            System.exit(1);
            return null;
        }
    }
}
