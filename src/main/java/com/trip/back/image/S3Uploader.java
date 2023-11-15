package com.trip.back.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {
	@Value("${com.upload.path}")
	private String uploadPath;
		
	
	private final AmazonS3 s3;
	private final String bucketName = "imagebucket";
	private final String folderName = "images";
	
	public void setAccess() {
		try {
		    // get the current ACL
		    AccessControlList accessControlList = s3.getBucketAcl(bucketName);

		    // add read permission to anonymous
		    accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);

		    s3.setBucketAcl(bucketName, accessControlList);
		} catch (AmazonS3Exception e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
		
	}
	public String uploadMy() {
		setAccess();
		System.out.println(1);
		String objectName = "images/test.jpg";
		String filePath = uploadPath+"/springboot.png";
		System.out.println(2);
		File file = new File(filePath);
		System.out.println(file.getAbsolutePath());
		System.out.println(file.length());
		try {
//		    s3.putObject(bucketName, objectName, new File(filePath));
		    System.out.format("Object %s has been created.\n", objectName);
		} catch (AmazonS3Exception e) {
		    e.printStackTrace();
		} catch(SdkClientException e) {
		    e.printStackTrace();
		}
		System.out.println(3);
		return s3.getUrl(bucketName, objectName).toString();
	}
	public String upload(MultipartFile multipartFile) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile);
    }

    private String upload(File uploadFile) {
        String fileName = folderName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        s3.putObject(
                new PutObjectRequest(bucketName, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return s3.getUrl(bucketName, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
       System.out.println(file.getOriginalFilename());
       try (FileOutputStream fos = new FileOutputStream(convertFile)) {
           fos.write(file.getBytes());
       }
       return Optional.of(convertFile);
//        if(convertFile.createNewFile()) {
//           
//        }
//        return Optional.empty();
    }
}
