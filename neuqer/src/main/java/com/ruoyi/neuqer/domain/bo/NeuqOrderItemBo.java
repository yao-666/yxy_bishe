package com.ruoyi.neuqer.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 订单明细业务对象 neuq_order_item
 *
 * @author ruoyi
 * @date 2022-04-18
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("订单明细业务对象")
public class NeuqOrderItemBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    @Length(max = 19, min = 1, message = "订单编号的长度必须大于等于1或小于等于19")
    private String orderSn;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称", required = true)
    @NotBlank(message = "产品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称", required = true)
    private String className;

    /**
     * 购物选项
     */
    @ApiModelProperty(value = "购物选项", required = true)
    private String shoppingOptions;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价", required = true)
    @NotNull(message = "单价不能为空", groups = { AddGroup.class, EditGroup.class })
    @Digits(integer=10,fraction = 2,message = "单价数值过大，无法保存")
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    @Min(value = 1, message = "数量必须大于等于1")
    private Integer amount;

    /**
     * 小计
     */
    @ApiModelProperty(value = "小计", required = true)
    @NotNull(message = "小计不能为空", groups = {AddGroup.class, EditGroup.class })
    @Digits(integer=10,fraction = 2,message = "小计数组过大，无法保存")
    private BigDecimal subtotal;

    /**
     * 单次数量
     */
    @ApiModelProperty(value = "单次数量", required = true)
    @NotNull(message = "单次数量不能为空", groups = { AddGroup.class, EditGroup.class })
    @Min(value = 1, message = "单次数量必须大于等于1")
    private Integer singleNumber;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量", required = true)
    @NotNull(message = "总数量不能为空", groups = { AddGroup.class, EditGroup.class })
    @Min(value = 1, message = "总数量必须大于等于1")
    private Long totalQuantity;

    /**
     * 已取数量
     */
    @ApiModelProperty(value = "已取数量", required = true)
    private Long takeNumber;

    /**
     * 售后状态
     */
    @ApiModelProperty(value = "售后状态", required = true)
    private String afterStatus;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额", required = true)
    private BigDecimal refundAmount;

    /**
     * 订单明细备注
     */
    @ApiModelProperty(value = "订单明细备注", required = true)
    private String note;
}
