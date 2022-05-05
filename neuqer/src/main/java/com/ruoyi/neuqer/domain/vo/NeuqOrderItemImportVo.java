package com.ruoyi.neuqer.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.neuqer.convert.ExcelBigDecimalConverter;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 订单明细导入视图对象 neuq-order-item-import
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Data
@ApiModel("订单明细表导入对象")
//@ExcelIgnoreUnannotated
public class NeuqOrderItemImportVo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    private String orderSn;

    /**
     * 售后状态
     */
    @ExcelProperty(value = "售后状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_after_status")
    private String afterStatus;

    /**
     * 产品名称
     */
    @ExcelProperty(value = "产品名称")
    private String productName;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    private String className;

    /**
     * 购物选项
     */
    @ExcelProperty(value = "购物选项")
    private String shoppingOptions;

    /**
     * 单价
     */
    @ExcelProperty(value = "单价",converter = ExcelBigDecimalConverter.class)
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private Integer amount;

    /**
     * 小计
     */
    @ExcelProperty(value = "小计",converter = ExcelBigDecimalConverter.class)
    private BigDecimal subtotal;

    /**
     * 单次数量
     */
    @ExcelProperty(value = "单次数量")
    private Integer singleNumber;

    /**
     * 总数量
     */
    @ExcelProperty(value = "总数量")
    private Integer totalQuantity;

    /**
     * 已取数量
     */
    @ExcelProperty(value = "已取数量")
    private String takeNumber;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额", converter = ExcelBigDecimalConverter.class)
    private BigDecimal refundAmount;


}
