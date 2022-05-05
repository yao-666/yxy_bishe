package com.ruoyi.neuqer.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.neuqer.convert.ExcelBigDecimalConverter;
import com.ruoyi.neuqer.convert.ExcelLongConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 订单明细导入缓冲区视图对象 neuq_order_item_buffer
 *
 * @author yxy
 * @date 2022-04-21
 */
@Data
@ApiModel("订单明细导入缓冲区视图对象")
@ExcelIgnoreUnannotated
public class NeuqOrderItemBufferVo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderSn;

    /**
     * 拼单团单号
     */
    @ExcelProperty(value = "拼单团单号")
    @ApiModelProperty("拼单团单号")
    private String groupId;

    /**
     * 订单状态(0=待发货，1=待自提，2=已发货，3=已自提，4=售后中，5=交易完成)
     */
    @ExcelProperty(value = "订单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_order_status")
    @ApiModelProperty("订单状态(0=待发货，1=待自提，2=已发货，3=已自提，4=售后中，5=交易完成)")
    private String orderStatus;

    /**
     * 售后状态(0=申请退款，1=申请退货，2=退款完成，3=退货成功)
     */
    @ExcelProperty(value = "售后状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_after_state")
    @ApiModelProperty("售后状态(0=申请退款，1=申请退货，2=退款完成，3=退货成功)")
    private String afterStatus;

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
    @ExcelProperty(value = "单价",converter = ExcelBigDecimalConverter.class)
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量",converter = ExcelLongConverter.class)
    @ApiModelProperty("数量")
    private Integer amount;

    /**
     * 小计
     */
    @ExcelProperty(value = "小计",converter = ExcelBigDecimalConverter.class)
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
    private Long totalQuantity;

    /**
     * 已取数量
     */
    @ExcelProperty(value = "已取数量")
    @ApiModelProperty("已取数量")
    private Long takeNumber;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额")
    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;

    /**
     * 会员折扣金额
     */
    @ExcelProperty(value = "会员折扣金额")
    @ApiModelProperty("会员折扣金额")
    private BigDecimal memberDiscount;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    @ApiModelProperty("订单备注")
    private String note;

    /**
     * 会员账号
     */
    @ExcelProperty(value = "会员账号")
    @ApiModelProperty("会员账号")
    private String memberAccount;

    /**
     * 会员姓名
     */
    @ExcelProperty(value = "会员姓名")
    @ApiModelProperty("会员姓名")
    private String memberName;

    /**
     * 附加表单
     */
    @ExcelProperty(value = "附加表单")
    @ApiModelProperty("附加表单")
    private String additionForm;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址")
    @ApiModelProperty("地址")
    private String address;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    @ApiModelProperty("手机号码")
    private String phone;

    /**
     * 给卖家留言
     */
    @ExcelProperty(value = "给卖家留言")
    @ApiModelProperty("给卖家留言")
    private String buyerMessage;

    /**
     * 配送方式(0=自提，1=同城配送)
     */
    @ExcelProperty(value = "配送方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "neuq_shipping_mode")
    @ApiModelProperty("配送方式(0=自提，1=同城配送)")
    private String deliveryMode;

    /**
     * 配送价格/运费
     */
    @ExcelProperty(value = "配送价格/运费")
    @ApiModelProperty("配送价格/运费")
    private BigDecimal deliveryPrice;

    /**
     * 运单编号
     */
    @ExcelProperty(value = "运单编号")
    @ApiModelProperty("运单编号")
    private String deliveryNum;

    /**
     * 发货时间
     */
    @ExcelProperty(value = "发货时间")
    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    /**
     * 预计送达时间
     */
    @ExcelProperty(value = "预计送达时间")
    @ApiModelProperty("预计送达时间")
    private Date estimatedTime;

    /**
     * 取货地址
     */
    @ExcelProperty(value = "取货地址")
    @ApiModelProperty("取货地址")
    private String pickUpAddress;

    /**
     * 自提地点
     */
    @ExcelProperty(value = "自提地点")
    @ApiModelProperty("自提地点")
    private String pickUpSite;


}
