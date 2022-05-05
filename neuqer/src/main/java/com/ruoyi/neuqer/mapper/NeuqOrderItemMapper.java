package com.ruoyi.neuqer.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.neuqer.domain.NeuqOrderItem;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderVo;

import java.util.List;

/**
 * 订单明细Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public interface NeuqOrderItemMapper extends BaseMapperPlus<NeuqOrderItemMapper, NeuqOrderItem, NeuqOrderItemVo> {

    List<NeuqOrderItemVo> selectListByOrderSn(List<NeuqOrderVo> orderList);
}
