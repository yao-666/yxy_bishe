package com.ruoyi.neuqer.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表业务对象 neuq_order
 *
 * @author ruoyi
 * @date 2022-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)

@ApiModel("订单列表业务对象")
public class NeuqOrderBo extends BaseEntity {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id", required = true)
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空", groups = {AddGroup.class, EditGroup.class})
    @Pattern(regexp = "^[0-9]*$", message = "订单编号必须为数字")
    private String orderSn;

    /**
     * 订单状态：0=待发货；1=已发货；2=已完成；3=申请退款；4=已退款；5=拒绝退款；
     */
    @ApiModelProperty(value = "订单状态：0=待发货；1=已发货；2=已完成；3=申请退款；4=已退款；5=拒绝退款；", required = true)
    @NotBlank(message = "订单状态不能为空", groups = {AddGroup.class, EditGroup.class})
    private String orderStatus;

    /**
     * 结算时间
     */
    @ApiModelProperty(value = "结算时间", required = true)
    @NotNull(message = "结算时间不能为空", groups = {AddGroup.class, EditGroup.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Past(message = "必须为过去的时间")
    private Date paymentTime;

    /**
     * 交易完成时间
     */
    @ApiModelProperty(value = "交易完成时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accomplishTime;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额", required = true)
    @NotNull(message = "订单总金额不能为空", groups = {AddGroup.class, EditGroup.class})
    @Digits(integer = 10, fraction = 2, message = "订单数组过大，无法保存", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal orderAmount;

    /**
     * 订单类型：0=普通订单，1=拼团订单，2=秒杀订单
     */
    @ApiModelProperty(value = "订单类型：0=普通订单，1=拼团订单，2=秒杀订单", required = true)
    @NotBlank(message = "订单类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String orderType;

    /**
     * 配送方式：0=自提；1=同校送货
     */
    @ApiModelProperty(value = "配送方式：0=自提；1=同校送货", required = true)
    @NotBlank(message = "配送方式不能为空", groups = {AddGroup.class, EditGroup.class})
    private String shippingMethod;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名", required = true)
    @NotBlank(message = "收货人姓名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String receiverName;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "收货人电话", required = true)
    @Pattern(regexp = "\\d{11}", message = "收货人电话必须为数字，并且长度为11")
    private String receiverPhone;

    /**
     * 收货人地址
     */
    @ApiModelProperty(value = "地址", required = true)
    private String receiverDetailAddress;

    /**
     * 自提地址，根据订单配送类型决定保存自提地址还是配送地址
     */
    private String selfPickUpAddress;

    /**
     * 给卖家留言
     */
    @ApiModelProperty(value = "给卖家留言", required = true)
    private String buyerMessage;

    /**
     * 配送价格/运费
     */
    @ApiModelProperty(value = "配送价格/运费", required = true)
    private BigDecimal shippingPrice;

    /**
     * 配送人id
     */
    @ApiModelProperty(value = "配送人id", required = true)
    private String shippingManId;

    /**
     * 取货地址
     */
    @ApiModelProperty(value = "取货地址", required = true)
    private String pickUpAddress;

    /**
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注", required = true)
    private String note;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    @ApiModelProperty(value = "删除状态：0=未删除；2=已删除", required = true)
    @TableLogic
    private Integer delFLag;

}
