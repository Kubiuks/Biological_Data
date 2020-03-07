package org.spe.biologicaldata.webapplication.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import org.spe.biologicaldata.webapplication.service.AmazonS3Storage;
import org.spe.biologicaldata.webapplication.service.GoogleCloudStorage;
import org.spe.biologicaldata.webapplication.service.StorageService;
import org.spe.biologicaldata.webapplication.service.StoreToDisk;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.MBeanServerNotFoundException;

@Configuration
public class StorageServiceConfiguration {

    @Value("${service.storage.google-storage:false}")
    private Boolean googleStorage;

    @Value("${service.storage.amazon-storage:false}")
    private Boolean amazonStorage;

    @Value("${service.storage.disk-storage:true}")
    private Boolean diskStorage;

    @Bean(name = "googleStorageBoolean")
    public Boolean getGoogleStorageBoolean() {
            return googleStorage;
    }

    @Bean(name = "amazonStorageBoolean")
    public Boolean getAmazonStorageBoolean() {
        return amazonStorage;
    }

    @Bean(name = "diskStorageBoolean")
    public Boolean getDiskStorageBoolean() {
        return diskStorage;
    }


}
