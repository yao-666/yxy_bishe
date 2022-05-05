package com.ruoyi.neuqer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表对象 neuq_order
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Data
@TableName("neuq_order")
@NoArgsConstructor
@AllArgsConstructor
public class NeuqOrder extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 订单id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    ///**
    // * 会员id
    // */
    //private String memberId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单状态：0->待发货；1->已发货；2->已完成；3->申请退款；4->已退款；5->拒绝退款；
     */
    private String orderStatus;
    /**
     * 结算时间
     */
    private Date paymentTime;
    /**
     * 交易完成时间
     */
    private Date accomplishTime;
    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单类型：0=普通订单，1=拼团订单，2=秒杀订单
     */
    private String orderType;

    /**
     * 配送方式：0=自提；1=同校送货
     */
    private String shippingMethod;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 收货人地址
     */
    private String receiverDetailAddress;
    /**
     * 给卖家留言
     */
    private String buyerMessage;
    /**
     * 配送价格/运费
     */
    private BigDecimal shippingPrice;
    /**
     * 配送人id
     */
    private String shippingManId;
    /**
     * 取货地址
     */
    private String pickUpAddress;
    /**
     * 订单备注
     */
    private String note;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    @TableLogic
    private Integer delFlag;

}
