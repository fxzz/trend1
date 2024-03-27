package com.trend.mapper;


import com.trend.domain.Category;
import com.trend.domain.Product;
import com.trend.domain.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    List<Category> selectCategory(Integer categoryId);
    void insertProductImage(ProductImage productImage);
    void insertProduct(Product product);
    void updateImageStatus(String uuid);
    List<Product> selectMainProduct(Map<String, Object> map);
    Integer searchResultCnt(Map<String, Object> map);
    Product selectProductDetail(Integer productId);
    List<String> selectMainRecommendCategory();

    List<Product> selectCartProductList(List<Integer> cart);

}
