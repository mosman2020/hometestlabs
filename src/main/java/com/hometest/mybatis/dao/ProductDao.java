package com.hometest.mybatis.dao;

import com.hometest.dto.ProductPageableDto;
import com.hometest.model.req.ProductPageable;
import com.hometest.mybatis.domain.Category;
import com.hometest.mybatis.domain.Product;
import com.hometest.mybatis.mapper.ProductMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Bassam
 */
@Component
public class ProductDao implements ProductMapper {
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<Category> getAllCategories(){
        return sqlSession.selectList("getAllCategories");
    }

    @Override
    public ProductPageable findProductByCategory(ProductPageableDto request) {

        List<Product> content = sqlSession.selectList("findProductByCategory", request);
        return ProductPageable
                    .builder()
                        .page(request.getPage())
                        .content(content)
                    .build();
    }
}
