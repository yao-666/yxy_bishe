package com.ruoyi.neuqer.mapper;


import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.neuqer.domain.NeuqOrder;
import com.ruoyi.neuqer.domain.bo.NeuqOrderBo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderVo;

import java.util.List;

/**
 * 订单列表Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public interface NeuqOrderMapper extends BaseMapperPlus<NeuqOrderMapper, NeuqOrder, NeuqOrderVo> {


    List<String> getEmptyOrderList();

    /**
     * 通过orderSn查找对应的记录
     *
     * @param orderSn String 订单编号
     * @author yxy
     * @Date 2022/4/27
     * @returns NeuqOrderBo
     **/
    NeuqOrderBo selectOrderByOrderSn(String orderSn);

    void deleteEmptyOrder(List<String> list);
}
