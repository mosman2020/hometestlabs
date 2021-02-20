package com.hometest.mybatis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bassam
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product   extends Audit  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String nameEn;
    private String nameAr;
    private String summaryAr;
    private String summaryEn;
    private BigDecimal price;
    @JsonIgnore
    private Date createdDate;
    @JsonIgnore
    private Long createdBy;
    @JsonIgnore
    private Long updatedBy;
    @JsonIgnore
    private Date updatedDate;
    @JsonIgnore
    private boolean isDeleted;
    @JsonIgnore
    private Date deletedDate;
}
