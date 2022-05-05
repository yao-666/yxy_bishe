package com.ruoyi.neuqer.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单明细导入缓冲区业务对象 neuq_order_item_buffer
 *
 * @author yxy
 * @date 2022-04-21
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("订单明细导入缓冲区业务对象")
public class NeuqOrderItemBufferBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    @TableId(value = "id",type = IdType.AUTO)
    private String id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderSn;

    /**
     * 拼单团单号
     */
    @ApiModelProperty(value = "拼单团单号", required = true)
    private String groupId;

    /**
     * 订单状态(0=待发货，1=待自提，2=已发货，3=已自提，4=售后中，5=交易完成)
     */
    @ApiModelProperty(value = "订单状态(0=待发货，1=待自提，2=已发货，3=已自提，4=售后中，5=交易完成)", required = true)
    @NotBlank(message = "订单状态(0=待发货，1=待自提，2=已发货，3=已自提，4=售后中，5=交易完成)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderStatus;

    /**
     * 售后状态(0=申请退款，1=申请退货，2=退款完成，3=退货成功)
     */
    @ApiModelProperty(value = "售后状态(0=申请退款，1=申请退货，2=退款完成，3=退货成功)", required = true)
    private String afterStatus;

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
    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
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
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer amount;

    /**
     * 小计
     */
    @ApiModelProperty(value = "小计", required = true)
    @NotNull(message = "小计不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal subtotal;

    /**
     * 单次数量
     */
    @ApiModelProperty(value = "单次数量", required = true)
    private Integer singleNumber;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量", required = true)
    private Long totalQuantity;

    /**
     * 已取数量
     */
    @ApiModelProperty(value = "已取数量", required = true)
    private Long takeNumber;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额", required = true)
    private BigDecimal refundAmount;

    /**
     * 会员折扣金额
     */
    @ApiModelProperty(value = "会员折扣金额", required = true)
    private BigDecimal memberDiscount;

    /**
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注", required = true)
    private String note;

    /**
     * 会员账号
     */
    @ApiModelProperty(value = "会员账号", required = true)
    private String memberAccount;

    /**
     * 会员姓名
     */
    @ApiModelProperty(value = "会员姓名", required = true)
    @NotNull(message = "会员姓名不能为空")
    private String memberName;

    /**
     * 附加表单
     */
    @ApiModelProperty(value = "附加表单", required = true)
    private String additionForm;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", required = true)
    private String address;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", required = true)
    private String phone;

    /**
     * 给卖家留言
     */
    @ApiModelProperty(value = "给卖家留言", required = true)
    private String buyerMessage;

    /**
     * 配送方式(0=自提，1=同城配送)
     */
    @ApiModelProperty(value = "配送方式(0=自提，1=同城配送)", required = true)
    private String deliveryMode;

    /**
     * 配送价格/运费
     */
    @ApiModelProperty(value = "配送价格/运费", required = true)
    private BigDecimal deliveryPrice;

    /**
     * 运单编号
     */
    @ApiModelProperty(value = "运单编号", required = true)
    private String deliveryNum;

    /**
     * 发货时间
     */
    @ApiModelProperty(value = "发货时间", required = true)
    private Date deliveryTime;

    /**
     * 预计送达时间
     */
    @ApiModelProperty(value = "预计送达时间", required = true)
    private Date estimatedTime;

    /**
     * 取货地址
     */
    @ApiModelProperty(value = "取货地址", required = true)
    private String pickUpAddress;

    /**
     * 自提地点
     */
    @ApiModelProperty(value = "自提地点", required = true)
    private String pickUpSite;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    @ApiModelProperty(value = "删除状态：0=未删除；2=已删除", required = true)
    @TableLogic
    private Integer delFLag;

}
