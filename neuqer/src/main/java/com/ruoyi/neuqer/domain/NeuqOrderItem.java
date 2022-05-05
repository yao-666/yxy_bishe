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

/**
 * 订单明细对象 neuq_order_item
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Data
@TableName("neuq_order_item")
@NoArgsConstructor
@AllArgsConstructor
public class NeuqOrderItem extends BaseEntity {

    private static final long serialVersionUID=1L;
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
     */
    private String orderSn;
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
     * 售后状态
     */
    private String afterStatus;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
}
