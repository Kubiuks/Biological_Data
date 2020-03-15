package org.spe.biologicaldata.webapplication.configuration;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GoogleVisionConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(GoogleVisionConfiguration.class);

    @Value("${google.vision.credentials.classpath}")
    private String googleVisionCredentialsPath;

    @Bean(name = "googleVisionCredentialsPath")
    public String getGoogleGalleryPath(){return googleVisionCredentialsPath;}

    @Bean(name = "googleVisionCredentials")
    public Credentials getGoogleCredentials(ApplicationContext applicationContext) {
        try {
            return GoogleCredentials
                    .fromStream(new FileInputStream(this.googleVisionCredentialsPath));
        } catch (IOException e) {
            logger.error("error: [" + e + "] happened while loading Google Vision Credentials");
            SpringApplication.exit(applicationContext, () -> 1);
            return null;
        }
    }
}
