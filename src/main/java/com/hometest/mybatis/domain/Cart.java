package com.hometest.mybatis.domain;

import com.hometest.utils.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long id;
    @NotNull(message = ErrorCodes.MANDATORY_FIELD)
    @Valid
    private Long productId;
    @NotNull(message = ErrorCodes.MANDATORY_FIELD)
    private Integer qty;
    @NotNull(message = ErrorCodes.MANDATORY_FIELD)
    @Valid
    private Long userId;
}
