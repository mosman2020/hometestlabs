package com.hometest.service.imp;

import com.hometest.dto.ProductPageableDto;
import com.hometest.model.req.ProductPageable;
import com.hometest.mybatis.dao.ProductDao;
import com.hometest.mybatis.domain.Category;
import com.hometest.mybatis.domain.Product;
import com.hometest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Category> getAllCategories() {
        return productDao.getAllCategories();
    }

    @Override
    public ProductPageable findProductByCategory(ProductPageableDto request){
        return productDao.findProductByCategory(request);
    }

    @Override
    public Product findById(Long productId) {
        return productDao.findById(productId);
    }
}
