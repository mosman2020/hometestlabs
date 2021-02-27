package com.hometest.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hometest.enums.OrderStatus;
import com.hometest.enums.PaymentType;
import com.hometest.validation.annotation.DateFormat;
import com.hometest.validation.annotation.ValidEnum;
import com.hometest.validation.groups.OnCreate;
import com.hometest.validation.groups.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Bassam
 */
@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull
    private Long customerId;
    private String orderNumber;

    @DateTimeFormat
    private Date orderDate;

    private Long offerId;
    private Long packageId;

    private BigDecimal discountValue;
    private BigDecimal netAmount;
    private BigDecimal amount;
    private BigDecimal paidAmount;

    private String paymentStatus;

    @Null(groups= OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @ValidEnum(enumClazz = PaymentType.class,groups = OnUpdate.class)
    private String paymentType;

    @DateFormat
    private Date proposedDate;
    @DateTimeFormat
    private Timestamp proposedTime;
    @DateTimeFormat
    private Date expectedDelivery;
    @Null(groups= OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @ValidEnum(enumClazz = OrderStatus.class,groups = OnUpdate.class)
    private String status;
    private Long assignedBranchId;
    private Date assignedBranchReceivingDate;
    private Long assignedTechnicianId;

    @DateTimeFormat
    private Date sampleCollectionDate;
    @DateTimeFormat
    private Date customerDeliveryDate;
    @DateTimeFormat
    private Date createdDate;
    private Long createdBy;
    @DateTimeFormat
    private Date updatedDate;
    private Long updatedBy;

    private String isDeleted;
    private Date deletedDate;


}
