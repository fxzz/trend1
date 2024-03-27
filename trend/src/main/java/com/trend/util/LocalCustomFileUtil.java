package com.trend.util;

import com.trend.config.S3Config;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("!prod")
public class LocalCustomFileUtil implements CustomFileUtil {

    @Value("${com.trend.upload.path}")
    private String uploadPath;



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

        String savedName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
        Path savePath = Paths.get(uploadPath, savedName);
        try {
            Files.copy(file.getInputStream(), savePath); //원본 파일 업로드
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return savedName;
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
                Files.copy(file.getInputStream(), savePath); //원본 파일 업로드


                uploadNames.add(savedName);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

        if (!resource.isReadable()) {
           return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().headers(headers).body(resource);
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
