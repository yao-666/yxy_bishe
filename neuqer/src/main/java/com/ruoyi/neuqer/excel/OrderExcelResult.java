package com.ruoyi.neuqer.excel;

import com.ruoyi.neuqer.domain.OrderCheckMsg;

import java.util.HashMap;
import java.util.List;

/**
 * @author yxy
 * @version 1.0
 * @date 2022/4/25 0:45
 * @apiNote 订单导入excel返回对对象
 */
public interface OrderExcelResult {
    /**
     * 返回excel校验列表
     */
    List<OrderCheckMsg> getFailMsgList();

    /**
     * 导入回执
     * @return
     */
  HashMap<String, OrderCheckMsg>  getAnalysis();

    /**
     * 导入回执
     * @return
     */
    List<OrderCheckMsg> getSuccessList();

}
