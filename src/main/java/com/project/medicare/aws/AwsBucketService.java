package com.project.medicare.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class AwsBucketService {

    @Value("${app.aws.accessKey}")
    private String access_key;

    @Value("${app.aws.secretKey}")
    private String secret_key;

    private AmazonS3 amazonS3;

    @Value("${app.aws.endpoint}")
    private String endpoint;

    @Value("${app.aws.bucketName}")
    private String bucket_name;

    @PostConstruct
    private void authentication(){
        AWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
        this.amazonS3 = new AmazonS3Client(credentials);
    }

    public String uploadFile(MultipartFile multipartFile,String filename) throws MalformedURLException {
        String fileURL = "";
        try {
            File file = convertMultipartFileToFile(multipartFile);
            String fileName = filename;
            fileURL = endpoint + fileName;
            uploadFileToBucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileURL;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private void uploadFileToBucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucket_name, fileName, file));
    }

    public String deleteFileFromBucket(String fileName) {
        amazonS3.deleteObject(bucket_name,fileName);
        return "Deletion Successful";
    }
}
