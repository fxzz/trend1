package com.trend.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@ToString
@Builder
@Alias("Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer productId;

    //메인 카테고리
    private Integer mainCategoryId;
    // 서브 카테고리
    private Integer subCategoryId;
    // 서브 서브 카테고리
    private Integer categoryId;

    private String productTitle;

    private String productPrice;

    private String productQuantity;

    private String productContent;

    private String productState;

    private String uuid;

    private Date createdAt;

    private Integer accountId;





    private String nickname;

    private String categoryName;

    private String subCategoryName;

    private String mainCategoryName;

    private String fileName;

    private List<String> detailImage;

    private String formattedTime;


}
