package com.trip.back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


@Configuration
public class S3Configuration {
	final String endPoint = "https://kr.object.ncloudstorage.com";
	final String regionName = "kr-standard";
	@Value("${api.s3.id}")
	private String accessKey;
	@Value("${api.s3.secret}")
	private String secretKey;
	
	@Bean
	public AmazonS3 amazonS3Client() {
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
		 .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
		    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
		    .build();
		
		 return s3;
	}
		    

}
