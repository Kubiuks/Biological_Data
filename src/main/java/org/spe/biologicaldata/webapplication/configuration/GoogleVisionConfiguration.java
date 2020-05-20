package org.spe.biologicaldata.webapplication.configuration;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class GoogleVisionConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(GoogleVisionConfiguration.class);

    @Value("${google.vision.credentials.classpath}")
    private String googleVisionCredentialsPath;

    @Bean(name = "googleVisionCredentialsPath")
    public String getGoogleCredentialsPath(){return googleVisionCredentialsPath;}

    @Bean(name = "googleVisionCredentials")
    public Credentials getGoogleCredentials(ApplicationContext applicationContext) {
        try {
            logger.info("Loading Vision Credentials");
            return GoogleCredentials.fromStream(applicationContext.getResource(this.googleVisionCredentialsPath).getInputStream());
        } catch (IOException e) {
            logger.error("error: [" + e + "] happened while loading Google Vision Credentials");
//            SpringApplication.exit(applicationContext.getContext(), () -> 1);
            return null;
        }
    }
}
