package com.justdo.plug.blog.global.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    // 파일 확장자 가져오기
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    // 고유한 파일명 생성
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 여러 개의 이미지 업로드
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
        List<String> fileList = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream,
                    objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
                fileList.add(amazonS3Client.getUrl(bucket, fileName).toString());
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "파일 업로드에 실패했습니다.");
            }
        }
        return fileList;
    }

    // 하나의 이미지 업로드
    public String uploadFile(MultipartFile file) {

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream,
                objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "파일 업로드에 실패했습니다.");
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 이미지 삭제
    public void deleteFile(String fileName) {
        try {
            String s3File = extractImageFromUrl(fileName);
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, s3File));
        } catch (AmazonS3Exception e) {
            throw new ApiException(ErrorStatus._S3_IMAGE_NOT_FOUND);
        }
    }

    // S3 주소에 이미지 URL 추출
    private String extractImageFromUrl(String imgUrl) {
        String bucketPrefix = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        if (imgUrl.startsWith(bucketPrefix)) {
            return imgUrl.substring(bucketPrefix.length());
        } else {
            throw new ApiException(ErrorStatus._S3_IMAGE_NOT_FOUND);
        }
    }
}
