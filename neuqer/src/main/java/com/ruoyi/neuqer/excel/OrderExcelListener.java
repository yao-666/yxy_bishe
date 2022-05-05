package com.ruoyi.neuqer.excel;

import com.alibaba.excel.read.listener.ReadListener;

/**
 * @author yxy
 * @version 1.0
 * @date 2022/4/25 17:38
 * @apiNote 订单导入监听
 */
public interface OrderExcelListener<T> extends ReadListener<T> {

    /**
     * @author yxy
     * @Date: 2022/4/25 21:03
     * @Description: 返回订单列表excel导入结果（含校验结果）
     **/
    OrderExcelResult getOrderExcelResult();
}
