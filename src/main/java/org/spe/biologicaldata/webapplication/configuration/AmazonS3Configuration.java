package org.spe.biologicaldata.webapplication.configuration;

import com.amazonaws.auth.BasicSessionCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@Configuration
@ConditionalOnExpression("not ${service.storage.google-storage:true} and ${service.storage.amazon-storage:false}")
public class AmazonS3Configuration {

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.S3.bucketName}")
    private String awsS3BucketName;

    @Value("${aws.session.token}")
    private String awsSessionToken;

    @Value("${aws.S3.galleryPath}")
    private String awsS3GalleryPath;

    @Value("${aws.localTemporaryStorageDirectory}")
    private String localTemporaryStoragePath;

    @Bean(name = "awsAccessKey")
    public String getAWSAccessKey() {
        return awsAccessKey;
    }

    @Bean(name = "awsSecretKey")
    public String getAWSSecretKey() {
        return awsSecretKey;
    }

    @Bean(name = "awsS3GalleryPath")
    public String getAWSS3GalleryPath() {
        return awsS3GalleryPath;
    }

    @Bean(name = "localTemporaryStoragePath")
    public String getLocalTemporaryStoragePath() {
        return localTemporaryStoragePath;
    }

    @Bean(name = "awsRegion")
    public Region getAWSPollyRegion() {
        return Region.getRegion(Regions.fromName(awsRegion));
    }

    @Bean(name = "awsSessionToken")
    public String getAwsSessionToken(){return awsSessionToken;}

    @Bean(name = "awsCredentialsProvider")
    public AWSCredentialsProvider getAWSCredentials() {
        BasicSessionCredentials awsSessionCredentials = new BasicSessionCredentials(this.awsAccessKey,this.awsSecretKey,this.awsSessionToken);
        return new AWSStaticCredentialsProvider(awsSessionCredentials);
    }

    @Bean(name = "awsS3BucketName")
    public String getAWSS3BucketName() {
        return awsS3BucketName;
    }
}
