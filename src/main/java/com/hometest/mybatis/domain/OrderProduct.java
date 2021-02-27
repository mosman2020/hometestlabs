package com.hometest.mybatis.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Bassam
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    private Long id;
    @NotNull
    private Long productId;
    @NotNull
    private Long orderId;
}
