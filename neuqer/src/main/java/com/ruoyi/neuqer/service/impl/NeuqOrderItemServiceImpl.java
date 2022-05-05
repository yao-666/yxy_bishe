package com.ruoyi.neuqer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.neuqer.domain.NeuqOrderItem;
import com.ruoyi.neuqer.domain.bo.NeuqOrderItemBo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemImportVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderVo;
import com.ruoyi.neuqer.mapper.NeuqOrderItemMapper;
import com.ruoyi.neuqer.service.INeuqOrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 订单明细Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class NeuqOrderItemServiceImpl implements INeuqOrderItemService {

    private final NeuqOrderItemMapper baseMapper;

    /**
     * 查询订单明细
     *
     * @param id 订单明细主键
     * @return 订单明细
     */
    @Override
    public NeuqOrderItemVo queryById(String id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询订单明细列表
     *
     * @param bo 订单明细
     * @return 订单明细
     */
    @Override
    public TableDataInfo<NeuqOrderItemVo> queryPageList(NeuqOrderItemBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<NeuqOrderItem> lqw = buildQueryWrapper(bo);
        Page<NeuqOrderItemVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单明细列表
     *
     * @param bo 订单明细
     * @return 订单明细
     */
    @Override
    public List<NeuqOrderItemVo> queryList(NeuqOrderItemBo bo) {
        LambdaQueryWrapper<NeuqOrderItem> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<NeuqOrderItem> buildQueryWrapper(NeuqOrderItemBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<NeuqOrderItem> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderSn()), NeuqOrderItem::getOrderSn, bo.getOrderSn());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), NeuqOrderItem::getProductName, bo.getProductName());
        lqw.like(StringUtils.isNotBlank(bo.getClassName()), NeuqOrderItem::getClassName, bo.getClassName());
        lqw.eq(StringUtils.isNotBlank(bo.getShoppingOptions()), NeuqOrderItem::getShoppingOptions, bo.getShoppingOptions());
        lqw.eq(bo.getUnitPrice() != null, NeuqOrderItem::getUnitPrice, bo.getUnitPrice());
        lqw.eq(bo.getAmount() != null, NeuqOrderItem::getAmount, bo.getAmount());
        lqw.eq(bo.getSubtotal() != null, NeuqOrderItem::getSubtotal, bo.getSubtotal());
        lqw.eq(bo.getRefundAmount() != null, NeuqOrderItem::getRefundAmount, bo.getRefundAmount());
        return lqw;
    }

    /**
     * 新增订单明细
     *
     * @param bo 订单明细
     * @return 结果
     */
    @Override
    public Boolean insertByBo(NeuqOrderItemBo bo) {
        NeuqOrderItem add = BeanUtil.toBean(bo, NeuqOrderItem.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改订单明细
     *
     * @param bo 订单明细
     * @return 结果
     */
    @Override
    public Boolean updateByBo(NeuqOrderItemBo bo) {
        NeuqOrderItem update = BeanUtil.toBean(bo, NeuqOrderItem.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(NeuqOrderItem entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单明细
     *
     * @param ids 需要删除的订单明细主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

   /**
    * 查询是否存在相同的订单明细
    *
    * @author yxy
    * @Date  2022/4/27
    * @param orderItemImportVo 订单明细导入的对象
    * @returns NeuqOrderItemBo 订单明细业务对象
    **/
    @Override
    public NeuqOrderItemBo selectByVo(NeuqOrderItemImportVo orderItemImportVo) {
        LambdaQueryWrapper<NeuqOrderItem> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(orderItemImportVo.getOrderSn()), NeuqOrderItem::getOrderSn, orderItemImportVo.getOrderSn());
        lqw.like(StringUtils.isNotBlank(orderItemImportVo.getProductName()), NeuqOrderItem::getProductName, orderItemImportVo.getProductName());
        lqw.like(StringUtils.isNotBlank(orderItemImportVo.getClassName()), NeuqOrderItem::getClassName, orderItemImportVo.getClassName());
        lqw.eq(StringUtils.isNotBlank(orderItemImportVo.getShoppingOptions()), NeuqOrderItem::getShoppingOptions, orderItemImportVo.getShoppingOptions());
        return  baseMapper.selectVoOne(lqw,NeuqOrderItemBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<NeuqOrderItem> insertDataList) {
        try {
            return baseMapper.insertBatch(insertDataList);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatch(List<NeuqOrderItem> updateDataList) {
        try {
            return baseMapper.updateBatchById(updateDataList);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<NeuqOrderItemVo> selectListByOrderSn(List<NeuqOrderVo> orderList) {
        return baseMapper.selectListByOrderSn(orderList);
    }

}
