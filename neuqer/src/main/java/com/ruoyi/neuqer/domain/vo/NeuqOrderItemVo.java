package com.ruoyi.neuqer.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 订单明细视图对象 neuq_order_item
 *
 * @author yxy
 * @date 2022-04-18
 */
@Data
@ApiModel("订单明细视图对象")
@ExcelIgnoreUnannotated
public class NeuqOrderItemVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderSn;

    /**
     * 产品名称
     */
    @ExcelProperty(value = "产品名称")
    @ApiModelProperty("产品名称")
    private String productName;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    @ApiModelProperty("分类名称")
    private String className;

    /**
     * 购物选项
     */
    @ExcelProperty(value = "购物选项")
    @ApiModelProperty("购物选项")
    private String shoppingOptions;

    /**
     * 单价
     */
    @ExcelProperty(value = "单价")
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    @ApiModelProperty("数量")
    private Integer amount;

    /**
     * 小计
     */
    @ExcelProperty(value = "小计")
    @ApiModelProperty("小计")
    private BigDecimal subtotal;

    /**
     * 单次数量
     */
    @ExcelProperty(value = "单次数量")
    @ApiModelProperty("单次数量")
    private Integer singleNumber;

    /**
     * 总数量
     */
    @ExcelProperty(value = "总数量")
    @ApiModelProperty("总数量")
    private Integer totalQuantity;

    /**
     * 已取数量
     */
    @ExcelProperty(value = "已取数量")
    @ApiModelProperty("已取数量")
    private Integer takeNumber;

    /**
     * 售后状态
     */
    @ExcelProperty(value = "售后状态")
    @ApiModelProperty("售后状态")
    private String afterStatus;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额")
    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;

    /**
     * 订单明细备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("订单明细备注")
    private BigDecimal note;
}
