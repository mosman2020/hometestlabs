package com.hometest.service;

import com.hometest.dto.ProductPageableDto;
import com.hometest.model.req.ProductPageable;
import com.hometest.mybatis.domain.Category;
import com.hometest.mybatis.domain.Product;

import java.util.List;

public interface ProductService {
    List<Category> getAllCategories();

    ProductPageable findProductByCategory(ProductPageableDto request);

    Product findById(Long productId);
}
