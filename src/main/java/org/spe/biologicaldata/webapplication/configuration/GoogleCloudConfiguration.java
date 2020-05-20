package org.spe.biologicaldata.webapplication.configuration;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public Credentials getGoogleCredentials(ApplicationContext applicationContext) {
        try {
            logger.info("Loading Storage Credentials");
            return GoogleCredentials.fromStream(applicationContext.getResource(this.googleCredentialsPath).getInputStream());
        } catch (Exception e) {
            logger.error("error: [" + e + "] happened while loading Google Credentials");
//            SpringApplication.exit(applicationContext, () -> 1);
            return null;
        }
    }
}
