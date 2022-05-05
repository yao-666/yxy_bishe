package com.ruoyi.neuqer.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 订单列表视图对象 neuq_order
 *
 * @author yxy
 * @date 2022-04-18
 */
@Data
@ApiModel("订单列表视图对象")
@ExcelIgnoreUnannotated
public class NeuqOrderVo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @ExcelProperty(value = "订单id")
    @ApiModelProperty("订单id")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderSn;

    /**
     * 订单状态：0=待发货；1=已发货；2=已完成；3=申请退款；4=已退款；5=拒绝退款；
     */
    @ExcelProperty(value = "订单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_order_status")
    @ApiModelProperty("订单状态：0=待发货；1=已发货；2=已完成；3=申请退款；4=已退款；5=拒绝退款；")
    private String orderStatus;

    /**
     * 结算时间
     */
    @ExcelProperty(value = "结算时间")
    @ApiModelProperty("结算时间")
    private Date paymentTime;

    /**
     * 交易完成时间
     */
    @ExcelProperty(value = "交易完成时间")
    @ApiModelProperty("交易完成时间")
    private Date accomplishTime;

    /**
     * 订单总金额
     */
    @ExcelProperty(value = "订单金额")
    @ApiModelProperty("订单总金额")
    private BigDecimal orderAmount;

    /**
     * 订单类型：0=普通订单，1=拼团订单，2=秒杀订单
     */
    @ExcelProperty(value = "订单类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_order_type")
    @ApiModelProperty("订单类型：0=普通订单，1=拼团订单，2=秒送订单")
    private String orderType;

    /**
     * 配送方式：0->自提；1->同校送货
     */
    @ExcelProperty(value = "配送方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_shipping_mode")
    @ApiModelProperty("配送方式：0->自提；1->同校送货")
    private String shippingMethod;


    /**
     * 收货人姓名
     */
    @ExcelProperty(value = "收货人姓名")
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    /**
     * 收货人电话
     */
    @ExcelProperty(value = "手机")
    @ApiModelProperty("收货人电话")
    private String receiverPhone;

    /**
     * 收货人地址
     */
    @ExcelProperty(value = "地址")
    @ApiModelProperty("收货人地址")
    private String receiverDetailAddress;

    /**
     * 给卖家留言
     */
    @ExcelProperty(value = "给卖家留言")
    @ApiModelProperty("给卖家留言")
    private String buyerMessage;

    /**
     * 配送价格/运费
     */
    @ExcelProperty(value = "配送价格/运费")
    @ApiModelProperty("配送价格/运费")
    private BigDecimal shippingPrice;

    /**
     * 配送人id
     */
    @ExcelProperty(value = "配送人id")
    @ApiModelProperty("配送人id")
    private String shippingManId;

    /**
     * 取货地址
     */
    @ExcelProperty(value = "取货地址")
    @ApiModelProperty("取货地址")
    private String pickUpAddress;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    @ApiModelProperty("订单备注")
    private String note;

    ///**
    // * 删除状态：0->未删除；1->已删除
    // */
    //@ExcelProperty(value = "删除状态：0->未删除；1->已删除")
    //@ApiModelProperty("删除状态：0->未删除；1->已删除")
    //private Integer deleteStatus;

}
