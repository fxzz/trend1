package com.trend.service;

import com.trend.controller.form.ProductForm;
import com.trend.domain.*;
import com.trend.service.event.ProductImageStatusUpdateEvent;
import com.trend.mapper.ProductMapper;
import com.trend.util.CustomFileUtil;
import com.trend.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final CustomFileUtil fileUtil;
    private final ApplicationEventPublisher eventPublisher;
    private final TimeUtil timeUtil;

    @Override
    public List<Category> getCategoryList(Integer categoryId) {
        return productMapper.selectCategory(categoryId);
    }


    @Override
    public void saveProductImage(List<MultipartFile> file, String uuid) {


        List<String> uploadFileNames = fileUtil.saveFiles(file);

        if (uploadFileNames.size() != 0) {
            saveProductImages(uuid, uploadFileNames);

        }
    }

    @Transactional
    @Override
    public void saveProduct(ProductForm productForm, Integer accountId) {
        /*
            카테고리를 선택 안하면 최상위 기타 id로 변경
        */
        if (productForm.getCategoryId() == null
                && productForm.getMainCategoryId() == null
                && productForm.getSubCategoryId() == null) {
            int defaultCategoryId = 681;
            productForm.setMainCategoryId(defaultCategoryId);
        }


        Product product = Product.builder()
                .mainCategoryId(productForm.getMainCategoryId())
                .subCategoryId(productForm.getSubCategoryId())
                .categoryId(productForm.getCategoryId())
                .productTitle(productForm.getProductTitle())
                .productPrice(productForm.getProductPrice())
                .productQuantity(productForm.getProductQuantity())
                .productContent(productForm.getProductContent())
                .productState(productForm.getProductState())
                .uuid(productForm.getUuid())
                .accountId(accountId)
                .build();

        productMapper.insertProduct(product);
        eventPublisher.publishEvent(new ProductImageStatusUpdateEvent(productForm.getUuid()));
    }


    @Override
    public List<Product> getMainProductList(PageRequest pageRequest, String categoryKeyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", pageRequest.getOffset());
        map.put("keyword", pageRequest.getKeyword());
        map.put("pageSize", pageRequest.getPageSize());
        map.put("categoryKeyword", categoryKeyword);

        List<Product> productList = productMapper.selectMainProduct(map);


        // 1분 전 형식의 포멧으로 만듬
        for (Product product : productList) {
            String formattedTime = timeUtil.calculateTime(product.getCreatedAt());
            product.setFormattedTime(formattedTime);
        }
        return productList;
    }

    @Override
    public PageResponse pageHandler(PageRequest pageRequest, String categoryKeyword) {
        Map<String, Object> map = new HashMap();
        map.put("keyword", pageRequest.getKeyword());
        map.put("categoryKeyword", categoryKeyword);

        Integer totalCount = productMapper.searchResultCnt(map);
        return new PageResponse(null, pageRequest, totalCount);
    }

    @Override
    public Product getProductDetail(Integer productId) {
        Product product = productMapper.selectProductDetail(productId);
        String formattedTime = timeUtil.calculateTime(product.getCreatedAt());
        product.setFormattedTime(formattedTime);
        return product;
    }

    @Override
    public List<String> getMainRecommendCategory() {
        return productMapper.selectMainRecommendCategory();
    }

    @Override
    public List<Product> getCartProductList(List<Integer> cart) {
        return productMapper.selectCartProductList(cart);
    }


    private void saveProductImages(String uuid, List<String> fileNames) {
        int i = 0;
        for (String fileName : fileNames) {
            ProductImage image = ProductImage.builder()
                    .fileName(fileName)
                    .ord(i++)
                    .uuid(uuid)
                    .build();
            try {

                productMapper.insertProductImage(image);
            } catch (Exception e) {
                // 데이터베이스에 이미지 정보 삽입 중 예외 발생 시 이미지 파일 삭제
                if (fileName != null) {
                    // 파일 삭제
                }
            }
        }
    }
}
