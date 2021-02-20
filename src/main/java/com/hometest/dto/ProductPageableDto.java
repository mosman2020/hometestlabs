package com.hometest.dto;

import com.hometest.model.req.Page;
import lombok.Data;

@Data
public class ProductPageableDto {
    private Long catId;
    private Page page;
}
