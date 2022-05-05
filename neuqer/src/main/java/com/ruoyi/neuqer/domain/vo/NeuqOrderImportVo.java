package com.ruoyi.neuqer.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.neuqer.convert.ExcelBigDecimalConverter;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 订单列表视图对象 neuq_order-import
 *
 * @author yxy
 * @date 2022-04-18
 */
@Data
@ApiModel("订单列表视图对象")
@ExcelIgnoreUnannotated
public class NeuqOrderImportVo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    private String orderSn;

    /**
     * 订单状态：0=待发货；1=已发货；2=已完成；3=申请退款；4=已退款；5=拒绝退款；
     */
    @ExcelProperty(value = "订单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_order_status")
    private String orderStatus;

    /**
     * 结算时间
     */
    @ExcelProperty(value = "结算时间")
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private String paymentTime;

    /**
     * 交易完成时间
     */
    @ExcelProperty(value = "交易完成时间")
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private String accomplishTime;

    /**
     * 订单总金额
     */
    @ExcelProperty(value = "订单金额",converter = ExcelBigDecimalConverter.class)
    private BigDecimal orderAmount;

    /**
     * 订单类型：0=普通订单，1=拼团订单，2=秒杀订单
     */
    @ExcelProperty(value = "订单类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_order_type")
    private String orderType;

    /**
     * 配送方式：0->自提；1->同校送货
     */
    @ExcelProperty(value = "配送方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_shipping_mode")
    private String shippingMethod;


    /**
     * 收货人姓名
     */
    @ExcelProperty(value = "姓名")
    private String receiverName;

    /**
     * 收货人电话
     */
    @ExcelProperty(value = "手机")
    private String receiverPhone;

    /**
     * 收货人地址
     */
    @ExcelProperty(value = "地址")
    private String receiverDetailAddress;

    /**
     * 自提地址，根据订单配送类型决定保存自提地址还是配送地址
     */
    @ExcelProperty(value = "自提地点")
    private String selfPickUpAddress;

    /**
     * 给卖家留言
     */
    @ExcelProperty(value = "给卖家留言")
    private String buyerMessage;

    /**
     * 配送价格/运费
     */
    @ExcelProperty(value = "配送价格/运费",converter = ExcelBigDecimalConverter.class)
    private BigDecimal shippingPrice;

    /**
     * 取货地址
     */
    @ExcelProperty(value = "取货地址")
    private String pickUpAddress;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    private String note;

}
