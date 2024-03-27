package com.trend.util;

import com.trend.exception.InvalidImageFileException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {

    private static boolean isImageFile(MultipartFile file) {
        MediaType fileType = MediaType.parseMediaType(file.getContentType());
        return fileType.isCompatibleWith(MediaType.IMAGE_JPEG) ||
                fileType.isCompatibleWith(MediaType.IMAGE_PNG) ||
                fileType.isCompatibleWith(MediaType.IMAGE_GIF);
    }

    public static void validateImageFile(MultipartFile file) {
        if (!isImageFile(file)) {
            throw new InvalidImageFileException("이미지 파일만 업로드 가능합니다.");
        }
    }
}
