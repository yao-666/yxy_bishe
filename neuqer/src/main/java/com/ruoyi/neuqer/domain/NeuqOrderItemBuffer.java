package com.ruoyi.neuqer.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单明细导入缓冲区对象 neuq_order_item_buffer
 *
 * @author yxy
 * @date 2022-04-21
 */
@Data
@TableName("neuq_order_item_buffer")
public class NeuqOrderItemBuffer extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 拼单团单号
     */
    private String groupId;
    /**
     * 订单状态(0=待发货，1=待自提，2=已发货，3=已自提，4=售后中，5=交易完成)
     */
    private String orderStatus;
    /**
     * 售后状态(0=申请退款，1=申请退货，2=退款完成，3=退货成功)
     */
    private String afterStatus;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 分类名称
     */
    private String className;
    /**
     * 购物选项
     */
    private String shoppingOptions;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 小计
     */
    private BigDecimal subtotal;
    /**
     * 单次数量
     */
    private Integer singleNumber;
    /**
     * 总数量
     */
    private Long totalQuantity;
    /**
     * 已取数量
     */
    private Long takeNumber;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 会员折扣金额
     */
    private BigDecimal memberDiscount;
    /**
     * 订单备注
     */
    private String note;
    /**
     * 会员账号
     */
    private String memberAccount;
    /**
     * 会员姓名
     */
    private String memberName;
    /**
     * 附加表单
     */
    private String additionForm;
    /**
     * 姓名
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 给卖家留言
     */
    private String buyerMessage;
    /**
     * 配送方式(0=自提，1=同城配送)
     */
    private String deliveryMode;
    /**
     * 配送价格/运费
     */
    private BigDecimal deliveryPrice;
    /**
     * 运单编号
     */
    private String deliveryNum;
    /**
     * 发货时间
     */
    private Date deliveryTime;
    /**
     * 预计送达时间
     */
    private Date estimatedTime;
    /**
     * 取货地址
     */
    private String pickUpAddress;
    /**
     * 自提地点
     */
    private String pickUpSite;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
