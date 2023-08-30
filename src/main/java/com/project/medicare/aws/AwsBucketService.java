package com.project.medicare.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class AwsBucketService {

    @Value("${app.aws.accessKey}")
    private String access_key;

    @Value("${app.aws.secretKey}")
    private String secret_key;

    private AmazonS3 amazonS3;

    @PostConstruct
    private void authentication(){
        AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
        this.amazonS3 = new AmazonS3Client(credentials);
        System.out.println("Authenticated");
    }

}
