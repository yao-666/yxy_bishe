package com.ruoyi.neuqer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 订单导入校验信息类
 * @author yxy
 * @version 1.0
 * @date 2022/4/24 20:56
 * @apiNote
 */
@Data
@AllArgsConstructor
public class OrderCheckMsg {

    /**
     * 记录订单编号
     **/
    private String orderSn;
    /**
     * Excel表格名称
     */
    private String sheetName;

    /**
     * Excel错误位置
     */
    private String errLocation;

    /**
     * Excel错误信息
     */
    private String errMessage;

    /**
     * Excel错误信息
     */
    private Integer count;

}
