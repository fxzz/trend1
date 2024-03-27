package com.trend.util;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomFileUtil {

    String saveFile(MultipartFile file);
    List<String> saveFiles(List<MultipartFile> files);
    ResponseEntity<Resource> getFile(String fileName);

}
