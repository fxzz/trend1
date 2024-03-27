package com.trend.service;

import com.trend.controller.form.ProductForm;
import com.trend.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<Category> getCategoryList(Integer categoryId);

    void saveProductImage(List<MultipartFile> file, String uuid);

    void saveProduct(ProductForm productForm, Integer accountId);

    List<Product> getMainProductList(PageRequest pageRequest, String categoryKeyword);

    PageResponse pageHandler(PageRequest pageRequest, String categoryKeyword);

    Product getProductDetail(Integer productId);

    List<String> getMainRecommendCategory();

    List<Product> getCartProductList(List<Integer> cart);
}
