package com.ruoyi.neuqer.service;

import com.ruoyi.neuqer.domain.NeuqOrderItemBuffer;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemBufferVo;
import com.ruoyi.neuqer.domain.bo.NeuqOrderItemBufferBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单明细导入缓冲区Service接口
 *
 * @author yxy
 * @date 2022-04-21
 */
public interface INeuqOrderItemBufferService {

    /**
     * 查询订单明细导入缓冲区
     *
     * @param id 订单明细导入缓冲区主键
     * @return 订单明细导入缓冲区
     */
    NeuqOrderItemBufferVo queryById(Long id);

    /**
     * 查询订单明细导入缓冲区列表
     *
     * @param neuqOrderItemBuffer 订单明细导入缓冲区
     * @return 订单明细导入缓冲区集合
     */
    TableDataInfo<NeuqOrderItemBufferVo> queryPageList(NeuqOrderItemBufferBo bo, PageQuery pageQuery);

    /**
     * 查询订单明细导入缓冲区列表
     *
     * @param neuqOrderItemBuffer 订单明细导入缓冲区
     * @return 订单明细导入缓冲区集合
     */
    List<NeuqOrderItemBufferVo> queryList(NeuqOrderItemBufferBo bo);

    /**
     * 修改订单明细导入缓冲区
     *
     * @param neuqOrderItemBuffer 订单明细导入缓冲区
     * @return 结果
     */
    Boolean insertByBo(NeuqOrderItemBufferBo bo);

    /**
     * 修改订单明细导入缓冲区
     *
     * @param neuqOrderItemBuffer 订单明细导入缓冲区
     * @return 结果
     */
    Boolean updateByBo(NeuqOrderItemBufferBo bo);

    /**
     * 校验并批量删除订单明细导入缓冲区信息
     *
     * @param ids 需要删除的订单明细导入缓冲区主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean saveBatch(List<NeuqOrderItemBuffer> list);
}
