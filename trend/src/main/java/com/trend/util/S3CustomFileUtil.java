package com.trend.util;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.trend.config.S3Config;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class S3CustomFileUtil implements CustomFileUtil {

    @Value("${com.trend.upload.path}")
    private String uploadPath;

    private final S3Config s3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    @PostConstruct
    public void init() {

        File tempFolder = new File(uploadPath);

        if (tempFolder.exists() == false) {
            tempFolder.mkdir();
        }

        uploadPath = tempFolder.getAbsolutePath();
        log.info("-----------------------");
        log.info(uploadPath);

    }


    @Override
    public String saveFile(MultipartFile file) {

        ImageUtil.validateImageFile(file);

        String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path savePath = Paths.get(uploadPath, savedName);
        String s3Url = null; // 변수 초기화

        try {
            // 원본 파일을 로컬에 저장
            Files.copy(file.getInputStream(), savePath);

            // S3로 파일 업로드
            s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, savedName, savePath.toFile())
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            // 업로드한 파일의 S3 URL을 리스트에 추가
            s3Url = s3Config.amazonS3Client().getUrl(bucket, savedName).toString();

            // 로컬에 저장된 파일 삭제
            Files.deleteIfExists(savePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return s3Url;
    }

    @Override
    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {


        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                return new ArrayList<>();
            }

            ImageUtil.validateImageFile(file);

            String savedName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);

            try {
                // 원본 파일을 로컬에 저장
                Files.copy(file.getInputStream(), savePath);

                // S3로 파일 업로드
                s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, savedName, savePath.toFile())
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                // 업로드한 파일의 S3 URL을 리스트에 추가
                String s3Url = s3Config.amazonS3Client().getUrl(bucket, savedName).toString();
                uploadNames.add(s3Url);
                // 로컬에 저장된 파일 삭제
                Files.deleteIfExists(savePath);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return uploadNames;
    }




    /*
    S3에서 업로드 할때 URL로 저장하기 때문에 사용안함
    */
    public ResponseEntity<Resource> getFile(String fileName) {
        return ResponseEntity.ok().body(null);
    }



    public void deleteFiles(List<String> fileNames) {
        if (fileNames == null || fileNames.size() == 0 ) {
            return;
        }

        fileNames.forEach(fileName -> {
            Path filePath = Paths.get(uploadPath, fileName);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

    }
}
