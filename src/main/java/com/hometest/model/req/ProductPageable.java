package com.hometest.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hometest.mybatis.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageable {
    private Long total;
    private List<Product> content;
    private Page page;
}
