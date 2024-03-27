package com.trend.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString
@Builder
@Alias("prim")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    private String uuid;

    private String fileName;

    private int ord;


    // 기본값 = false  상품 등록 후에 true 변경
    private boolean temporary;



    // req 이미지 받을때 사용
    private List<MultipartFile> files;
}
