package com.ruoyi.neuqer.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.neuqer.domain.NeuqOrderItem;
import com.ruoyi.neuqer.domain.bo.NeuqOrderItemBo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemImportVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderVo;

import java.util.Collection;
import java.util.List;

/**
 * 订单明细Service接口
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public interface INeuqOrderItemService {

    /**
     * 查询订单明细
     *
     * @param id 订单明细主键
     * @return 订单明细
     */
    NeuqOrderItemVo queryById(String id);

    /**
     * 查询订单明细列表
     *
     * @param bo neuqOrderItembo 订单明细
     * @return 订单明细集合
     */
    TableDataInfo<NeuqOrderItemVo> queryPageList(NeuqOrderItemBo bo, PageQuery pageQuery);

    /**
     * 查询订单明细列表
     *
     * @param bo neuqOrderItemBo 订单明细
     * @return 订单明细集合
     */
    List<NeuqOrderItemVo> queryList(NeuqOrderItemBo bo);

    /**
     * 修改订单明细
     *
     * @param bo neuqOrderItemBo 订单明细
     * @return 结果
     */
    Boolean insertByBo(NeuqOrderItemBo bo);

    /**
     * 修改订单明细
     *
     * @param bo neuqOrderItemBo 订单明细
     * @return 结果
     */
    Boolean updateByBo(NeuqOrderItemBo bo);

    /**
     * 校验并批量删除订单明细信息
     *
     * @param ids 需要删除的订单明细主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    /**
     * 根据excel导入的Vo查找数据库是否有相同的订单明细记录
     *
     * @param orderItemImportVo 导入的订单明细对象
     * @return 结果
     */
    NeuqOrderItemBo selectByVo(NeuqOrderItemImportVo orderItemImportVo);

    /**
     * 批量插入NeuqOrderItem到数据库
     *
     * @param insertDataList 导入的订单明细对象
     * @return 结果
     */
    boolean saveBatch(List<NeuqOrderItem> insertDataList);

    /**
     * 批量更新OrderItem数据库记录
     * @param updateDataList 导入的订单明细对象
     * @return 结果
     */
    boolean updateBatch(List<NeuqOrderItem> updateDataList);

    /**
     * 批量更新OrderItem数据库记录
     * @param orderList List<NeuqOrderVo> 订单列表数组
     * @return List<NeuqOrderItemVo> 结果
     */
    List<NeuqOrderItemVo> selectListByOrderSn(List<NeuqOrderVo> orderList);
}
