package com.hometest.mybatis.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Bassam
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category   extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String nameAr;
    private String nameEn;
    private String descriptionAr;
    private String descriptionEn;
    private Integer order;

}
