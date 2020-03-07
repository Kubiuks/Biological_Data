package org.spe.biologicaldata.webapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("not ${service.storage.google-storage:true} and not ${service.storage.amazon-storage:false} and ${service.storage.disk-storage:false}")
public class DiskStorageConfiguration {

    @Value("${disk.storage.classpath}")
    private String diskStoragePath;

    @Bean(name = "diskStorageFolderPath")
    public String getDiskStoragePath(){
        return this.diskStoragePath;
    }

}
