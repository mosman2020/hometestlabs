package com.hometest.model.req;

import com.hometest.utils.ErrorCodes;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddToCartRequest {
    @NotNull(message = ErrorCodes.MANDATORY_FIELD)
    Long productId;
    @Min(1)
    @NotNull(message = ErrorCodes.MANDATORY_FIELD)
    Integer qty;
}
