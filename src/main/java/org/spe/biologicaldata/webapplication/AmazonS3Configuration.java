package org.spe.biologicaldata.webapplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@Configuration
public class AmazonS3Configuration {

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.S3.bucketName}")
    private String awsS3BucketName;

    @Bean(name = "awsAccessKey")
    public String getAWSAccessKey() {
        return awsAccessKey;
    }

    @Bean(name = "awsSecretKey")
    public String getAWSSecretKey() {
        return awsSecretKey;
    }

    @Bean(name = "awsRegion")
    public Region getAWSPollyRegion() {
        return Region.getRegion(Regions.fromName(awsRegion));
    }

    @Bean(name = "awsCredentialsProvider")
    public AWSCredentialsProvider getAWSCredentials() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.awsAccessKey, this.awsSecretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    @Bean(name = "awsS3BucketName")
    public String getAWSS3BucketName() {
        return awsS3BucketName;
    }
}
