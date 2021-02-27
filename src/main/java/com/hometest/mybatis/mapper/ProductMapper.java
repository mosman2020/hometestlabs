package com.hometest.mybatis.mapper;

import com.hometest.dto.ProductPageableDto;
import com.hometest.model.req.ProductPageable;
import com.hometest.mybatis.domain.Category;
import com.hometest.mybatis.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Bassam
 */
@Mapper
public interface ProductMapper {
    List<Category> getAllCategories();

    ProductPageable findProductByCategory(ProductPageableDto request);

    Product findById(Long productId);
}
