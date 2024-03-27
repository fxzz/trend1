package com.trend.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductForm {
    //메인 카테고리
    private Integer mainCategoryId;
    // 서브 카테고리
    private Integer subCategoryId;
    // 서브 서브 카테고리
    private Integer categoryId;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String productTitle;

    @Pattern(regexp = "^[0-9]{1,8}$", message = "숫자만 입력 가능하고 최소 1 이상 최대 8자리까지 입력가능 합니다.")
    private String  productPrice;

    @Pattern(regexp = "^[0-9]{1,8}$", message = "숫자만 입력 가능하고 최소 1 이상 최대 8자리까지 입력가능 합니다.")
    private String productQuantity;

    @NotBlank(message = "상품을 설명해주세요.")
    private String productContent;

    private String productState;

    private String uuid;


}
