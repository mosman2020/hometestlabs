package com.hometest.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data

public class Address  extends Audit implements Serializable {

    private static final long serialVersionUID = -4107565211226801900L;

    private Long id;
    private String addressLabel;
    private String addressLine1;
    private String addressLine2;
    private String district;
    private String city;
    private String region;
    private String zipCode;
    private String deliveryInstructions;
    private String geoLocationX;
    private String geoLocationY;
    private Object isDefault;
    @NotNull
    @JsonIgnore
    private Long profileId;
}
