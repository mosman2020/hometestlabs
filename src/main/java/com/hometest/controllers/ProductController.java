package com.hometest.controllers;

import com.hometest.dto.ProductPageableDto;
import com.hometest.model.req.ProductPageable;
import com.hometest.mybatis.domain.Category;
import com.hometest.service.ProductService;
import com.hometest.utils.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Bassam
 */
@RestController
@RequestMapping(ProductController.API_END_POINT)
public class ProductController {

    final static String API_END_POINT = WebConfig.API_END_POINT + "/products";

    @Autowired
    ProductService productService;

    @PostMapping
    public ProductPageable findProductByCategory(@RequestBody ProductPageableDto request){
        return productService.findProductByCategory(request);

    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return productService.getAllCategories();

    }

}
